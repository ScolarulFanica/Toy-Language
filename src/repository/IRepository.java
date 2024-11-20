package repository;

import exceptions.RepoException;
import model.state.PrgState;

public interface IRepository {
    PrgState getCrtPrg();
    void setPrgState(PrgState prgState);
    void addPrgState(PrgState state);
    void logPrgState() throws RepoException;
}
