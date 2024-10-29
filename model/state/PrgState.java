package model.state;
import model.adt.*;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.util.Dictionary;

public class PrgState {
    private MyIStack<IStatement> execStack;
    private MyIDictionary<String, IValue>  symTable;
    private MyIList<String> outputList;
    private IStatement originalStatement;
    private MyIDictionary<StringValue, BufferedReader> fileTable;

    public PrgState(IStatement initState, MyIStack<IStatement> execStack, MyIDictionary<String, IValue> symTable,MyIDictionary<StringValue, BufferedReader> fileTable ) {
        this.execStack = execStack;
        this.symTable = symTable;
        outputList = new MyList<>();
        originalStatement = initState.deepCopy();
        execStack.push(originalStatement);
        this.fileTable = fileTable;
    }

    public MyIStack<IStatement> getExecStack() {
        return execStack;
    }
    public MyIDictionary<String, IValue> getSymTable() {
        return symTable;
    }
    public MyIList<String> getOutputList() {
        return outputList;
    }
    public MyIDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        return execStack.toString() + "\n" + symTable.toString() + "\n"
                + outputList.toString() + "\n" + toStringFile();
    }
    public String toStringFile(){
        String s="File table:\n" ;
        for(StringValue v: fileTable.keys()){
            s+=v.getValue()+ "\n";

        }
        return s;
    }
}