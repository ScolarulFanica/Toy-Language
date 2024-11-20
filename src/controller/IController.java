package controller;

import exceptions.ControllerException;
import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.state.PrgState;
import repository.IRepository;
import repository.Repository;

public interface IController {
    PrgState oneStep(PrgState state) throws ControllerException;
    void allStep () throws ControllerException;
    void addState(PrgState state);
}
