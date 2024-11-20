package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.PrgState;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStatement {
    private IExpression expression;
    public CloseRFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        IValue evalExpression = expression.evaluate(state.getSymTable(), state.getHeap());
        if(evalExpression.getType().equals(new String())) {
            throw new ExpressionException("The expression's value must be String");
        }
        BufferedReader bufferedReader = state.getFileTable().get((StringValue) evalExpression);
        if(bufferedReader == null) {
            throw new ExpressionException("There is no entry in the FileTable for the value");
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try {
            state.getFileTable().remove((StringValue) evalExpression);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFile(expression.deepCopy());
    }

    @Override
    public String toString(){
        return "Closing file: "+expression.toString();
    }
}
