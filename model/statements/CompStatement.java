package model.statements;

import model.state.PrgState;

public class CompStatement implements IStatement {
    private IStatement first;
    private IStatement second;

    public CompStatement(IStatement first, IStatement second) {
        this.first = first;
        this.second = second;
    }

    public PrgState execute(PrgState state) {
        state.getExecStack().push(second);
        state.getExecStack().push(first);
        return state;
    }

    public String toString() {
        return first.toString() + ";" + second.toString();
    }

    public IStatement deepCopy(){
        return new CompStatement(first.deepCopy(), second.deepCopy());
    }
}