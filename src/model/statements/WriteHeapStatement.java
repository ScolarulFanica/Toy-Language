package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.adt.IHeap;
import model.state.PrgState;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class WriteHeapStatement implements IStatement {
    private String varName;
    private IExpression expression;
    public WriteHeapStatement(String varName, IExpression expression) {
        this.varName = varName;
        this.expression = expression;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if(!state.getSymTable().contains(varName))
            throw new StatementException("Variable '" + varName + "' not found");
        IValue varValue = state.getSymTable().get(varName);
        if(!(varValue instanceof RefType))
            throw new StatementException("Variable '" + varName + "' is not of type RefType");
        RefValue refValue = (RefValue)varValue;
        int address = refValue.getAddress();

        IHeap heap = state.getHeap();
        if(!heap.getHeap().contains(address)){
            throw new StatementException("Address '" + address + "' not found");
        }
        IValue evalValue = expression.evaluate(state.getSymTable(),heap);
        if (!evalValue.getType().equals(((RefType) varValue.getType()).getInner())) {
            throw new StatementException("The expression is of a different type than the variable's referenced type.");
        }

        heap.set(address, evalValue);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(varName,expression.deepCopy());
    }
}
