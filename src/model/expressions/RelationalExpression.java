package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.adt.IHeap;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;

public class RelationalExpression implements IExpression {
    private IExpression left;
    private IExpression right;
    private RelationalOperation operation;

    public RelationalExpression(IExpression left, IExpression right,
                           RelationalOperation operation){
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl, IHeap heap) throws ExpressionException {
        IValue leftValue = left.evaluate(symTbl, heap);
        IValue rightValue = right.evaluate(symTbl, heap);

        if(!leftValue.getType().equals(new IntType())
                || !rightValue.getType().equals(new IntType())){
            throw new ExpressionException("Invalid expressions");
        }

        int intLeftValue = ((IntValue) leftValue).getValue();
        int intRightValue = ((IntValue) rightValue).getValue();

        switch(operation){
            case SMALLER -> {
                return new BoolValue(intLeftValue<intRightValue);
            }

            case SMALLER_OR_EQUAL -> {
                return new BoolValue(intLeftValue<=intRightValue);
            }

            case EQUAL -> {
                return new BoolValue(intLeftValue==intRightValue);
            }

            case DIFFERENT -> {
                return new BoolValue(intLeftValue!=intRightValue);
            }

            case GREATER -> {
                return new BoolValue(intLeftValue>intRightValue);
            }

            case GREATER_OR_EQUAL -> {
                return new BoolValue(intLeftValue>=intRightValue);
            }

            default -> {
                throw new ExpressionException("Invalid operation");
            }
        }
    }

    @Override
    public IExpression deepCopy() {
        return new RelationalExpression(left.deepCopy(), right.deepCopy(), operation) ;
    }

    @Override
    public String toString() {
        return left.toString() + " " + operation.name() + " " + right.toString();
    }
}
