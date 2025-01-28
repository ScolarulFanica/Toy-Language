package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.BoolType;
import model.type.IType;
import model.value.BoolValue;
import model.value.IValue;

public class WhileStatement implements IStatement {
    private IExpression expression;
    private IStatement statement;
    public WhileStatement(IExpression expression , IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        MyIStack<IStatement> stack = state.getExecStack();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        IValue val = expression.evaluate(symTable, heap);
        if (!(val instanceof BoolValue)) {
            throw new StatementException("The condition in the while statement is not a boolean.");
        }
        BoolValue boolVal = (BoolValue) val;
        if(boolVal.getValue()) {
            stack.push(new WhileStatement(expression, statement));
            stack.push(statement);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "While(" + this.expression + "){" + this.statement + "}";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try {
            IType conditionType = this.expression.typecheck(typeEnv);
            if (!conditionType.equals(new BoolType())) {
                throw new StatementException("WhileStatement condition is not of type BoolType.");
            }
            this.statement.typecheck(typeEnv.deepCopy());
            return typeEnv;
        } catch (Exception e)
        {
            throw new StatementException("WhileStatement expression could not be computed properly.");
        }
    }

}

