package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements IStatement {
    private IExpression expression;
    private String varName;
    public ReadFileStatement(IExpression expression, String varName) {
        this.expression = expression;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if (!state.getSymTable().contains(varName)) {
            throw new StatementException("The variable is not defined!");
        }
        if (!state.getSymTable().get(varName).getType().equals(new IntType())) {
            throw new StatementException("The variable is not an integer type!");
        }
        IValue evalExpression = expression.evaluate(state.getSymTable(), state.getHeap());
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
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFileStatement(expression.deepCopy(), varName);
    }

    @Override
    public String toString(){
        return "Read file: "+expression.toString();
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try {
            IType exprType = this.expression.typecheck(typeEnv);
            if (!exprType.equals(new StringType())) {
                throw new StatementException("ReadFileStatement expression is not of type StringType.");
            }
            IType varType = typeEnv.get(varName);
            if (!varType.equals(new IntType())) {
                throw new StatementException("Variable " + varName + " is not of type IntType.");
            }
            return typeEnv;
        } catch (Exception e)
        {
            throw new StatementException(e.getMessage());
        }
    }


}
