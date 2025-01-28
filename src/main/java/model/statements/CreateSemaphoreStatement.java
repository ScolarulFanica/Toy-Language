package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.ISempahoreTable;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

public class CreateSemaphoreStatement implements IStatement {
    private String var;
    private IExpression exp;

    public CreateSemaphoreStatement(String var, IExpression exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        ISempahoreTable sempahoreTable = state.getSempahoreTable();
        IValue expValue = exp.evaluate(symTable, state.getHeap());
        if(!(expValue instanceof IValue)){
            throw new StatementException("Expression in createSemaphore must be evaluated to an integer");
        }
        int nr = ((IntValue) expValue).getValue();
        int newSemaphoreIndex = sempahoreTable.add(nr);
        if(!symTable.contains(var)){
            throw new StatementException("Variable " + var + " is not declared");
        }
        IValue varValue = symTable.get(var);
        if(!(varValue instanceof IValue)){
            throw new StatementException("Variable " + var + " must be of type int");
        }
        symTable.insert(var,new IntValue(newSemaphoreIndex));
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new CreateSemaphoreStatement(var, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try {
            IType varType = typeEnv.get(var);
            if (varType == null || !varType.equals(new IntType())){
                throw new StatementException("Variable " + var + " must be of type int");
            }
            IType expType = exp.typecheck(typeEnv);
            if(!expType.equals(new IntType())){
                throw new StatementException("Expression in createSemaphore must be evaluated to an integer");
            }
            return typeEnv;
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public String toString(){
        return "createSemaphore(" + var + ", " + exp + ")";
    }
}
