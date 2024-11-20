package model.adt;

import exceptions.ExpressionException;
import model.value.IValue;

import java.util.Map;

public interface IHeap {
    Integer allocate(IValue name);
    IValue get(Integer address) throws ExpressionException;
    boolean exists(Integer address);
    Integer getFreeLocation();
    void set(Integer address, IValue value) throws ExpressionException;
    MyIDictionary getHeap();
    Map<Integer,IValue> getContent();
    void setContent(Map<Integer,IValue> content);
}