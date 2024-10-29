package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement {
    private IExpression expression;
    private String varName;
    public ReadFile(IExpression expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }

    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if (!state.getSymTable().contains(varName)) {
            throw new StatementException("The variable is not defined!");
        }
        if (!state.getSymTable().get(varName).getType().equals(new IntType())) {
            throw new StatementException("The variable is not an integer type!");
        }
        IValue evalExpression = expression.evaluate(state.getSymTable());
        if (!evalExpression.getType().equals(new StringType())) {
            throw new StatementException("The evaluated expression is not a string");
        }
        BufferedReader bufferedReader = state.getFileTable().get((StringValue) evalExpression);
        try {
            String fileLine = bufferedReader.readLine();
            if(fileLine == null) {
                fileLine = "0";
            }
            int intFileLine = Integer.parseInt(fileLine);
            state.getSymTable().insert(varName, new IntValue(intFileLine));
        } catch(IOException e) {
            throw new StatementException("Error reading file");
        }
        return state;
    }

    @Override
    public IStatement deepCopy() {
        return null;
    }

}
