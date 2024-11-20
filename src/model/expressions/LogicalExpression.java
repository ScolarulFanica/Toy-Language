package model.expressions;
import exceptions.ExpressionException;
import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.value.IValue;
import model.type.BoolType;
import model.value.BoolValue;

public class LogicalExpression implements IExpression{
    private IExpression left;
    private IExpression right;
    private LogicalOperation operation;
    public LogicalExpression(IExpression left, LogicalOperation operation, IExpression right) {
        this.left = left;
        this.operation = operation;
        this.right = right;
    }
    @Override
    public String toString(){
        return left.toString() + " " + operation.toString().toLowerCase() + " " + right.toString();
    }
    public IValue evaluate(MyIDictionary<String, IValue> symTable, IHeap heap) throws ExpressionException {
        IValue left = this.left.evaluate(symTable,heap);
        IValue right = this.right.evaluate(symTable,heap);
        if(!(left.getType().equals(new BoolType()) && right.getType().equals(new BoolType()))){
            throw new ExpressionException("The values are not boolean!");
        }

        Boolean leftValue = ((BoolValue)(left)).getValue();
        Boolean rightValue = ((BoolValue)(right)).getValue();
        if(operation == LogicalOperation.AND){
            return new BoolValue(leftValue && rightValue);
        }
        else {
            return new BoolValue(leftValue || rightValue);
        }

    }

    public IExpression deepCopy(){
        return new LogicalExpression(left.deepCopy(), operation, right.deepCopy());
    }

}