package model.statements;

import exceptions.ExpressionException;
import exceptions.StatementException;
import javafx.scene.image.ImageView;
import model.adt.MyIDictionary;
import model.expressions.IExpression;
import model.state.PrgState;
import model.type.IType;
import model.value.IValue;

public class SwitchStatement implements IStatement {
    private IExpression exp;
    private IExpression exp1;
    private IExpression exp2;
    private IStatement stmt1;
    private IStatement stmt2;
    private IStatement defaultStmt;
    public SwitchStatement(IExpression exp, IExpression exp1, IStatement stmt1, IExpression exp2, IStatement stmt2, IStatement defaultStmt) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.defaultStmt = defaultStmt;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException, ExpressionException {
        MyIDictionary<String, IValue> symTable = state.getSymTable();
        IValue expVal = this.exp.evaluate(symTable, state.getHeap());
        IValue expVal1 = this.exp1.evaluate(symTable, state.getHeap());
        IValue expVal2 = this.exp2.evaluate(symTable, state.getHeap());

        if(expVal.equals(expVal1)){
            state.getExecStack().push(this.stmt1);
        } else if(expVal.equals(expVal2)){
            state.getExecStack().push(this.stmt2);
        } else {
            state.getExecStack().push(this.defaultStmt);
        }
        return null;
    }

    @Override
    public IStatement deepCopy() {
        return new SwitchStatement(exp.deepCopy(),exp1.deepCopy(),stmt1.deepCopy(),exp2.deepCopy(),stmt2.deepCopy(),defaultStmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, IType> typecheck(MyIDictionary<String, IType> typeEnv) throws StatementException {
        try {
            IType expType = this.exp.typecheck(typeEnv);
            IType expType1 = this.exp1.typecheck(typeEnv);
            IType expType2 = this.exp2.typecheck(typeEnv);

            if(!expType.equals(expType1) || !expType1.equals(expType2)){
                throw new StatementException("Switch expression and case expression must have the same type");
            }

            this.stmt1.typecheck(typeEnv.deepCopy());
            this.stmt2.typecheck(typeEnv.deepCopy());
            this.defaultStmt.typecheck(typeEnv.deepCopy());
            return typeEnv;

        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    public String toString(){
        return "Switch(" + this.exp + ")" +
                "(case " + this.exp1 + ": "+ this.stmt1 + ") "+
                "(case " + this.exp2 + ": "+ this.stmt2 + ") "+
                "(default: " + this.defaultStmt + ")";
    }
}
