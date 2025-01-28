package model.statements;

import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.type.IType;

public class VarDeclStatement implements IStatement{
    private String name;
    private IType type;

    public VarDeclStatement(String name, IType type) {
        this.name = name;
        this.type = type;
    }

    public PrgState execute(PrgState state) throws StatementException {
        if(state.getSymTable().contains(this.name)) throw new StatementException("A variable with the same name already exists!\n");
        state.getSymTable().insert(this.name, this.type.getDefaultValue());
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new VarDeclStatement(this.name, this.type);
    }

    public String toString() {
        return type +" "+ name;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        typeEnv.insert(this.name, this.type);
        return typeEnv;
    }


}
