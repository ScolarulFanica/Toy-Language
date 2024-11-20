package model.state;
import model.adt.*;
import model.expressions.IExpression;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private MyIStack<IStatement> execStack;
    private MyIDictionary<String, IValue>  symTable;
    private MyIList<String> outputList;
    private IStatement originalStatement;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private IHeap heap;


    public PrgState(IStatement initState, MyIStack<IStatement> execStack, MyIDictionary<String, IValue> symTable,MyIDictionary<StringValue, BufferedReader> fileTable, IHeap heap ) {
        this.execStack = execStack;
        this.symTable = symTable;
        outputList = new MyList<>();
        originalStatement = initState.deepCopy();
        execStack.push(originalStatement);
        this.fileTable = fileTable;
        this.heap = heap;
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
    public IHeap getHeap() {return heap;}

    @Override
    public String toString() {
        return execStack.toString() + "\n" + symTable.toString() + "\n"
                + outputList.toString() + "\n" + toStringFile() + "\n" + this.heap.toString() + "\n";
    }
    public String toStringFile(){
        String s="File table:\n" ;
        for(StringValue v : fileTable.keys()){
            s+=v.getValue()+ "\n";
        }
        return s;
    }
}