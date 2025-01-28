package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.adt.MyStack;
import model.state.PrgState;
import model.type.IType;

public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        MyStack<IStatement> stack = new MyStack<>();

        PrgState newPrgState = new PrgState(this.statement,stack,
                state.getSymTable().deepCopy(),state.getOutputList(),state.getFileTable(),state.getHeap(),state.getSempahoreTable() );

        return newPrgState;
    }


    @Override
    public IStatement deepCopy() {
        return new ForkStatement(this.statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork("+this.statement.toString()+")";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try {
            this.statement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } catch (Exception e) {
            throw new StatementException("ForkStatement typecheck failed: " + e.getMessage());
        }
    }

}

