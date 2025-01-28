package model.expressions;

import exceptions.ExpressionException;
import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class ReadHeap implements IExpression{
    private IExpression expression;
    public ReadHeap(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symTbl, IHeap heap) throws ExpressionException {
        IValue evalValue = expression.evaluate(symTbl, heap);
        if(!(evalValue instanceof RefValue))
            throw new ExpressionException("Evaluated value is not a RefValue");
        RefValue refValue = (RefValue)evalValue;
        int address = refValue.getAddress();
        if(!heap.getHeap().contains(address)){
            throw new ExpressionException("Heap does not contain address " + address);
        }
        try {
            return heap.get(address);
        }catch (Exception e) {
            throw new ExpressionException("Could not get address " + address);
        }
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeap(expression.deepCopy());
    }

    @Override
    public IType typecheck(MyIDictionary<String, IType> typeEnv) throws ExpressionException {

            IType typ = expression.typecheck(typeEnv);
            if(typ instanceof RefType){
                RefType reft  = (RefType) typ;
                return reft.getInner();
            } else throw new ExpressionException("The rH argument is not a RefType");
    }


    @Override
    public String toString(){
        return expression.toString();
    }
}

