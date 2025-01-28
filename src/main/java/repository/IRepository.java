package repository;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.state.PrgState;
import model.value.IValue;

import java.util.List;

public interface IRepository {
    void logPrgState(PrgState prg);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
    IHeap getHeap();
    MyIDictionary<String, IValue> getSymbolTable();
}
