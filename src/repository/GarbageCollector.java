package repository;

import model.adt.IHeap;
import model.adt.MyIDictionary;
import model.value.IValue;
import model.value.RefValue;

import java.util.*;
import java.util.stream.Collectors;

public class GarbageCollector {
    public static List<Integer> getAddressesFromSymTable(Collection<IValue> symTableValues) {
        return symTableValues.stream().filter(val->val instanceof RefValue).map(val->((RefValue) val).getAddress()).collect(Collectors.toList());
    }
    public static List<Integer> getHeapAddresses(IHeap heap, List<Integer> initialAddresses) {
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
                    System.out.println(e);
                }

            }
        }while(changed);
        return new ArrayList<>(validAddresses);
    }
    public static Map<Integer,IValue> safeGarbageCollector(Collection<IValue> symTableValues, IHeap heap) {
        List<Integer> symTableAddresses = getAddressesFromSymTable(symTableValues);
        List<Integer> validAddresses = getHeapAddresses(heap, symTableAddresses);

        return heap.getContent().entrySet().stream().filter(entry->validAddresses.contains(entry.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
