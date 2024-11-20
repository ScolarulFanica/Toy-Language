package controller;

import exceptions.ControllerException;
import model.adt.MyIStack;
import model.state.PrgState;
import model.statements.IStatement;
import model.value.IValue;
import repository.GarbageCollector;
import repository.IRepository;

import java.util.Collection;
import java.util.Map;

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

    public void allStep() throws ControllerException{
        try {
            PrgState prg = repo.getCrtPrg();
            //display the program state before execution
            this.repo.logPrgState();
            while (!prg.getExecStack().isEmpty()) {
                this.oneStep(prg);
                performGarbageCollection(prg);
                //display the program state after execution
                this.repo.logPrgState();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void performGarbageCollection(PrgState prg){
        try {
            Collection<IValue> symTableValues = prg.getSymTable().getContent().values();
            Map<Integer, IValue> cleanedHeap = GarbageCollector.safeGarbageCollector(symTableValues, prg.getHeap());
            prg.getHeap().setContent(cleanedHeap);
        } catch (Exception e) {
            System.out.println("Error during garbage collection:" + e.getMessage());
        }
    }

    public void addState(PrgState state){
        this.repo.addPrgState(state);
    }
}
