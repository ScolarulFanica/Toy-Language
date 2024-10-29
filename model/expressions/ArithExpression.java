package model.expressions;

import exceptions.ExpressionException;
import model.adt.MyIDictionary;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;

public class ArithExpression implements IExpression {

    private IExpression left;
    private IExpression right;
    private ArithmeticalOperation operation;

    public ArithExpression(IExpression left, IExpression right,
                                  ArithmeticalOperation operation){
        this.left = left;
        this.right = right;
        this.operation = operation;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl) throws ExpressionException {

        IValue leftValue = left.evaluate(symTbl);
        IValue rightValue = right.evaluate(symTbl);

        if(!leftValue.getType().equals(new IntType())
                || !rightValue.getType().equals(new IntType())){
            throw new ExpressionException("Invalid expressions");
        }

        int intLeftValue = ((IntValue) leftValue).getValue();
        int intRightValue = ((IntValue) rightValue).getValue();

        switch(operation){
            case PLUS -> {
                return new IntValue(intLeftValue+intRightValue);
            }

            case MINUS -> {
                return new IntValue(intLeftValue-intRightValue);
            }

            case MULTIPLY -> {
                return new IntValue(intLeftValue*intRightValue);
            }

            case DIVIDE -> {

                if(intRightValue == 0){
                    throw new ExpressionException("Division by zero");
                }

                return new IntValue(intLeftValue/intRightValue);
            }

            default -> {
                throw new ExpressionException("Invalid operation");
            }
        }


    }

    @Override
    public IExpression deepCopy() {
        return new ArithExpression(left.deepCopy(), right.deepCopy(), operation);
    }

    public String enumToString(){
        switch(this.operation){
            case PLUS -> {
                return "+";
            }

            case MINUS -> {
                return "-";
            }

            case MULTIPLY -> {
                return "*";
            }

            case DIVIDE -> {
                return "/";
            }

            default -> {
                return "";
            }
        }
    }

    @Override
    public String toString() {
        return this.left.toString() + enumToString() + this.right.toString();
    }
}