package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStatement {
    private IExpression expression;
    public OpenRFile(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        IValue evalExpression = expression.evaluate(state.getSymTable(), state.getHeap());
        if (!evalExpression.getType().equals(new StringType())) {
            throw new StatementException("The evaluated expression is not a string");
        }
        StringValue key = (StringValue) evalExpression;
        if(state.getFileTable().contains(key)){
            throw new StatementException("The key already exists");
        }

        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(key.getValue()));
            state.getFileTable().insert(key,bufferedReader);
            System.out.println(state);
        }
        catch(IOException e){
            throw new StatementException("Error reading file");
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new OpenRFile(expression.deepCopy());
    }
    @Override
    public String toString() {
        return "Open statement: " + expression.toString();
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try {
            IType exprType = this.expression.typecheck(typeEnv);
            if (!exprType.equals(new StringType())) {
                throw new StatementException("OpenRFile expression is not of type StringType.");
            }
            return typeEnv;
        } catch (Exception e) {
            throw new StatementException(e.getMessage());
        }
    }

}

