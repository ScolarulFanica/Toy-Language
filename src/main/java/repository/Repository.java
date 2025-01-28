package repository;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.value.IValue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> list;
    private String logFilePath;

    public Repository(String logFilePath) {
        list = new ArrayList<>();
        this.logFilePath = logFilePath;
    }

    public Repository(List<PrgState> list, String logFilePath) {
        this.list = list;
        this.logFilePath = logFilePath;
    }

    @Override
    public void logPrgState(PrgState prg) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            pw.println(prg);
            pw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<PrgState> getPrgList(){
        return this.list;
    }

    @Override
    public void setPrgList(List<PrgState> list) {
        this.list = list;
    }

    @Override
    public IHeap getHeap() {
        return this.getPrgList().get(0).getHeap();
    }

    @Override
    public MyIDictionary<String, IValue> getSymbolTable() {
        return this.getPrgList().get(0).getSymTable();
    }
}
