package repository;

import model.state.PrgState;
import model.value.IValue;
import model.value.RefValue;

import java.util.*;
import java.util.stream.Collectors;

public class GarbageCollector {
    public static List<Integer> getAddressesFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream().filter(val->val instanceof RefValue).map(val->((RefValue) val).getAddress()).collect(Collectors.toList());
    }
    public static List<Integer> getHeapAddresses(Map<Integer, IValue> heap, List<Integer> initialAddresses) {
        Set<Integer> validAddresses = new HashSet<>(initialAddresses);
        boolean changed;
        do{
            changed = false;
            for(Integer address : new HashSet<>(validAddresses)){
                try {
                    IValue val = heap.get(address);
                    if(val instanceof RefValue refValue) {
                        int refAddress = refValue.getAddress();
                        if(validAddresses.add(refAddress)){
                            changed = true;
                        }
                    }
                } catch (Exception e)
                {
                    System.err.println("Error accessing heap address" + e.getMessage());
                }

            }
        }while(changed);
        return new ArrayList<>(validAddresses);
    }
    public static Map<Integer, IValue> safeGarbageCollector(Collection<IValue> symTableValues, Map<Integer, IValue> heap) {
        List<Integer> symTableAddresses = getAddressesFromSymTable(symTableValues);
        List<Integer> validAddresses = getHeapAddresses(heap, symTableAddresses);
        return heap.entrySet().stream().filter(entry->validAddresses.contains(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddresses, Map<Integer,IValue> heap) {
        return heap.entrySet().stream().filter(e->symTableAddresses.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static void conservativeGarbageCollector(List<PrgState> prgList) {
        List<IValue> symTableValues = prgList.stream()
                .flatMap(prg -> prg.getSymTable().getContent().values().stream())
                .toList();

        Map<Integer, IValue> newHeap = GarbageCollector.safeGarbageCollector(
                symTableValues,
                prgList.get(0).getHeap().getContent()
        );

        prgList.forEach(prg -> prg.getHeap().setContent(newHeap));
    }


}



