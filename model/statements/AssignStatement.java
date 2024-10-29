package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.PrgState;
import model.value.IValue;

public class AssignStatement implements IStatement {
    private String id;
    private IExpression expression;
    public AssignStatement(String id, IExpression expression) {
        this.id = id;
        this.expression = expression;
    }
    public String toString(){
        return id + "=" + expression;
    }
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if(!state.getSymTable().contains(id))
            throw new StatementException("The variable wasnt declared previously!");
        IValue val = expression.evaluate(state.getSymTable());
        if(!val.getType().equals(state.getSymTable().get(id).getType()))
            throw new StatementException("The types did not match!");
        state.getSymTable().insert(id,val);
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(id, expression.deepCopy());
    }

}