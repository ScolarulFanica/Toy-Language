package model.expressions;

import exceptions.ExpressionException;
import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.type.IType;
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
    public IValue evaluate(MyIDictionary<String, IValue> symTbl, IHeap heap) throws ExpressionException {

        IValue leftValue = left.evaluate(symTbl,heap);
        IValue rightValue = right.evaluate(symTbl,heap);

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

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {
        IType t1,t2;
        t1 = left.typecheck(typeEnv);
        t2 = right.typecheck(typeEnv);
        if(t1.equals(new IntType())){
            if(t2.equals(new IntType())){
                return new IntType();
            } else throw new ExpressionException("Second operand is not an int");
        } else throw new ExpressionException("First operand is not an int");
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
