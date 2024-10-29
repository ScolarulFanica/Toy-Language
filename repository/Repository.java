package repository;

import exceptions.RepoException;
import model.adt.MyIList;
import model.expressions.ArithExpression;
import model.expressions.ArithmeticalOperation;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.state.PrgState;
import model.statements.*;
import model.type.IntType;
import model.value.IntValue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> list;
    private String logFilePath;
    private int currentPrgState;

    public Repository() {
        list = new ArrayList<PrgState>();
        //this.logFilePath = logFilePath;
        currentPrgState = 0;
    }
    public PrgState getCrtPrg(){
        return list.get(currentPrgState);
    }

    @Override
    public void addPrgState(PrgState state) {
        list.add(currentPrgState, state);
    }

    @Override
    public void logPrgState() throws RepoException {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            pw.println(this.getCrtPrg());
            pw.close();
        } catch (IOException e) {
            throw new RepoException(e.getMessage());
        }
    }
}
