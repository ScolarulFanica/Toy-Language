package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.state.PrgState;
import model.type.RefType;
import model.value.IValue;

import model.expressions.IExpression;
import model.value.RefValue;

public class HeapAllocationStatement implements IStatement{

    private String variableName;
    private IExpression expression;

    public HeapAllocationStatement(String variableName, IExpression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {

        var symTable = state.getSymTable();
        var heap = state.getHeap();
        if(!symTable.contains(variableName)){
            throw new StatementException("There is no variable " + variableName + " in the sym table");
        }

        IValue variableValue = symTable.get(this.variableName);

        if(!(variableValue.getType() instanceof RefType)){
            throw new StatementException("Variable is not of Ref type");
        }

        IValue value = expression.evaluate(symTable,heap);

        if (!value.getType().equals(((RefType) variableValue.getType()).getInner())) {
            throw new StatementException("The expression is of a different type than the referenced type.");
        }

        int address = heap.allocate(value);

        symTable.insert(variableName,new RefValue(address,((RefValue) variableValue).getLocationType()));
        return state;
    }

    @Override
    public String toString(){
        return "new(" + variableName + "," + expression + ")";
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocationStatement(this.variableName,this.expression);
    }
}