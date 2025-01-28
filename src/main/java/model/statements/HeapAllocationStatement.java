package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class HeapAllocationStatement implements IStatement{

    private String variableName;
    private IExpression expression;

    public HeapAllocationStatement(String variableName, IExpression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        var symTable = state.getSymTable();
        var heap = state.getHeap();

        if (!symTable.contains(variableName)) {
            throw new StatementException("There is no variable " + variableName + " in the symbol table");
        }

        IValue variableValue = symTable.get(this.variableName);

        if (!(variableValue.getType() instanceof RefType)) {
            throw new StatementException("Variable is not of Ref type");
        }

        IValue value = expression.evaluate(symTable, heap);

        if (!value.getType().equals(((RefType) variableValue.getType()).getInner())) {
            throw new StatementException("The expression is of a different type than the referenced type.");
        }

        int address = heap.allocate(value);

        symTable.insert(variableName, new RefValue(address, ((RefType) variableValue.getType()).getInner()));

        return null;
    }



    @Override
    public String toString(){
        return "new(" + variableName + "," + expression + ")";
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try{
            IType typevar = typeEnv.get(variableName);
            IType typexp = expression.typecheck(typeEnv);
            if(typevar.equals(new RefType(typexp))){
                return typeEnv;
            } else throw new StatementException("Heap alloc stmt: right hand side and left hand side have different types");
        }catch (Exception e){
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public IStatement deepCopy() {
        return new HeapAllocationStatement(this.variableName,this.expression);
    }
}
