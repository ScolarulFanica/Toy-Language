package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.IHeap;
import model.value.IValue;

public class VariableExpression implements IExpression{
    private String variable;

    public VariableExpression(String variable)
    {
        this.variable = variable;

    }
    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl, IHeap heap) throws ExpressionException {
        return symTbl.get(this.variable);
    }

    @Override
    public IExpression deepCopy() {
        return new VariableExpression(variable);
    }

    @Override
    public String toString()
    {
        return this.variable;
    }
}