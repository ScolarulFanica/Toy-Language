package controller;

import exceptions.ControllerException;
import model.state.PrgState;
import model.value.IValue;
import model.value.RefValue;
import repository.GarbageCollector;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    public IRepository repo;
    private ExecutorService exec = Executors.newFixedThreadPool(2);

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void allStep() throws ControllerException {
        try {
            exec = Executors.newFixedThreadPool(2);
            List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

            while (prgList.size() > 0) {
                GarbageCollector.conservativeGarbageCollector(prgList);
                oneStepForAll(prgList);
                prgList = removeCompletedPrg(repo.getPrgList());
            }

            exec.shutdownNow();
            repo.setPrgList(prgList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> states){
        return states.stream().filter(p->p.isNotCompleted()).collect(Collectors.toList());
    }

    public void oneStepForAll(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(p -> repo.logPrgState(p));
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> p.oneStep()))
                .collect(Collectors.toList());
        List<PrgState> newPrgList = exec.invokeAll(callList).stream().map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        ).filter(p->p!=null).collect(Collectors.toList());
        prgList.addAll(newPrgList);
        prgList.stream()
                .forEach(p -> repo.logPrgState(p));
        repo.setPrgList(prgList);

    }

    public IRepository getRepo(){
        return this.repo;
    }

    public ExecutorService getExecutor() {
        return exec;
    }

    public Map<Integer, IValue> garbageCollector(List<Integer> symbolTableAddresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(entry -> {
                    for (IValue value : heap.values()) {
                        if (value instanceof RefValue referenceValue) {
                            if (referenceValue.getAddress() == entry.getKey()) {
                                return true;
                            }
                        }
                    }
                    return symbolTableAddresses.contains(entry.getKey());
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddressesFromSymbolTable(Collection<IValue> symbolTableValues) {
        return symbolTableValues.stream()
                .filter(value -> value instanceof RefValue)
                .map(value -> ((RefValue) value).getAddress())
                .toList();
    }
}

