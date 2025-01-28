package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.value.IValue;
import model.value.RefValue;

public class PrintStatement implements IStatement {
    private IExpression expression;

    public PrintStatement(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws ExpressionException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();
        IValue value = expression.evaluate(symTable, heap);
        while (value instanceof RefValue) {
            int address = ((RefValue) value).getAddress();
            if (!heap.getHeap().contains(address)) {
                throw new ExpressionException("Invalid address in heap: " + address);
            }

            value = heap.get(address);
        }
        state.getOutputList().add(value.toString());
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }

    @Override
    public String toString() {
        return "print(" + this.expression.toString() + ")";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try {
            expression.typecheck(typeEnv);
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        return typeEnv;
    }
}
