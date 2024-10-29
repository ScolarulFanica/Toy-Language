package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.value.IValue;
import model.state.PrgState;

public interface IExpression {
    IValue evaluate(MyIDictionary<String, IValue> symTbl) throws ExpressionException;
    IExpression deepCopy();
}