package model.adt;

import exceptions.EmptyStackException;

public interface MyIStack<T> {
    T pop() throws EmptyStackException;
    void push(T t);
    boolean isEmpty();
    int size();
}