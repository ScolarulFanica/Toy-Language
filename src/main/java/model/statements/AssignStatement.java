package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.value.IValue;

public class AssignStatement implements IStatement {
    private String id;
    private IExpression expression;
    public AssignStatement(String id, IExpression expression) {
        this.id = id;
        this.expression = expression;
    }
    public String toString(){
        return id + "=" + expression;
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try{
            IType typevar = typeEnv.get(id);
            IType typexp = expression.typecheck(typeEnv);
            if(typevar.equals(typexp))
                return typeEnv;
            else
                throw new ExpressionException("Assignment: right hand side and left hand side have different types");
        }
        catch(ExpressionException e){
            throw new StatementException(e.getMessage());
        }
    }

    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        if(!state.getSymTable().contains(id))
            throw new StatementException("The variable wasnt declared previously!");
        IValue val = expression.evaluate(state.getSymTable(), state.getHeap());
        if(!val.getType().equals(state.getSymTable().get(id).getType()))
            throw new StatementException("The types did not match!");
        state.getSymTable().insert(id,val);
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(id, expression.deepCopy());
    }

}
