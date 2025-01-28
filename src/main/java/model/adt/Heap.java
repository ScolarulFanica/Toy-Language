package model.adt;

import exceptions.ExpressionException;
import model.value.IValue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Heap implements IHeap{

    private MyIDictionary<Integer, IValue> map;
    private Integer freeLocation;

    public Heap(){
        map = new MyDictionary<>();
        freeLocation = 1;
    }

    @Override
    public Integer allocate(IValue name) {
        map.insert(freeLocation++, name);
        return freeLocation-1;
    }

    @Override
    public IValue get(Integer address) throws ExpressionException {
        return map.get(address);
    }

    @Override
    public boolean exists(Integer address) {
        return map.contains(address);
    }

    @Override
    public Integer getFreeLocation() {
        return freeLocation;
    }

    @Override
    public void set(Integer address, IValue value) throws ExpressionException {
        if (!map.contains(address)) {
            throw new ExpressionException("Address " + address + " not found in the heap.");
        }
        map.insert(address, value);
    }


    @Override
    public MyIDictionary getHeap() {
        return map;
    }

    @Override
    public Map<Integer,IValue> getContent(){
        return new HashMap<>(map.getContent());
    }

    @Override
    public void setContent(Map<Integer,IValue> content) {
        map = new MyDictionary<>();
        content.forEach(map::insert);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Heap:\n");
        map.keys().forEach(key-> {
            try {
                builder.append(key.toString() + "->" + map.get(key).toString() + "\n");
            } catch (ExpressionException e) {
                builder.append("");
            }
        });
        return builder.toString();
    }

    @Override
    public Set<Integer> keys() {
        return new HashSet<>(this.map.keys());
    }
}