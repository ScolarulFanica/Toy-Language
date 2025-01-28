package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import javafx.util.Pair;
import model.adt.ISempahoreTable;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.type.IType;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStatement implements IStatement {
    private String var;
    private static Lock lock = new ReentrantLock();

    public ReleaseStatement(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        ISempahoreTable semaphoreTable = state.getSempahoreTable();
        if(!symTable.contains(var)){
            throw new StatementException("Variable " + var + " does not exist");
        }
        IValue varValue = symTable.get(var);
        if(!(varValue instanceof IntValue)){
            throw new StatementException("Variable " + var + " must be of type int");
        }
        int semaphoreIndex = ((IntValue)varValue).getValue();
        if(!semaphoreTable.contains(semaphoreIndex)){
            throw new StatementException("Semaphore index " + semaphoreIndex + " does not exist");
        }
        lock.lock();
        try{
            var entry = semaphoreTable.get(semaphoreIndex);
            int maxPermits = entry.getKey();
            List<Integer> threadsIds = entry.getValue();
            if(threadsIds.contains(state.getId())){
                threadsIds.remove(Integer.valueOf(state.getId()));
                semaphoreTable.update(semaphoreIndex, new Pair<>(maxPermits,threadsIds));
            }
        } finally {
            lock.unlock();
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReleaseStatement(var);
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try {
            IType varType = typeEnv.get(var);
            if(!varType.equals(new IntType())){
                throw new StatementException("Variable " + var + " must be of type int");
            }
            return typeEnv;
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public String toString(){
        return "release(" + var + ")";
    }
}
