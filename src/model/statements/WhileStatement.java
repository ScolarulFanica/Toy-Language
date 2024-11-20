package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.adt.MyIStack;
import model.expressions.IExpression;
import model.adt.IHeap;
import model.state.PrgState;
import model.value.BoolValue;
import model.value.IValue;

public class WhileStatement implements IStatement {
    private IExpression expression;
    private IStatement statement;
    WhileStatement(IExpression expression , IStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }
    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        MyIStack<IStatement> stack = state.getExecStack();
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IHeap heap = state.getHeap();
        IValue val = expression.evaluate(symTable, heap);
        if(val != null){
            stack.push( new WhileStatement(expression,statement));
            stack.push(statement);
        }
        if (!(val instanceof BoolValue)) {
            throw new StatementException("The condition in the while statement is not a boolean.");
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
}
