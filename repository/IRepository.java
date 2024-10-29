package repository;

import exceptions.RepoException;
import model.state.PrgState;

public interface IRepository {
    PrgState getCrtPrg();
    void addPrgState(PrgState state);
    void logPrgState() throws RepoException;
}
