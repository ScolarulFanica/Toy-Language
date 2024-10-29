package model.statements;

import model.state.PrgState;

public class NopStatement implements IStatement {
    public NopStatement() {};

    @Override
    public PrgState execute(PrgState state) {
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }

    @Override
    public String toString() {
        return "NopStatement";
    }
}