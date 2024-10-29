package controller;

import exceptions.ControllerException;
import exceptions.EmptyStackException;
import exceptions.ExpressionException;
import exceptions.StatementException;
import model.adt.MyIStack;
import model.state.PrgState;
import model.statements.IStatement;
import repository.IRepository;
import repository.Repository;

public class Controller implements IController {
    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) throws ControllerException {
        MyIStack<IStatement> stack = state.getExecStack();
        if (stack.isEmpty())
            throw new ControllerException("Stack is empty!");
        try {
            IStatement statement = stack.pop();
            return statement.execute(state);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void allStep(){
        try {
            PrgState prg = repo.getCrtPrg();
            //display the program state before execution
            this.repo.logPrgState();
            while (!prg.getExecStack().isEmpty()) {
                this.oneStep(prg);
                //display the program state after execution
                this.repo.logPrgState();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
