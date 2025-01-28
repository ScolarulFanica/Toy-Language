package model.adt;

import exceptions.EmptyStackException;

import java.util.*;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;
    public MyStack() {
        stack = new Stack<>();
    }

    @Override
    public T pop() throws EmptyStackException {
        if(stack.isEmpty()){
            throw new EmptyStackException("Stack is empty\n");
        }
        return this.stack.pop();
    }

    @Override
    public void push(T t) {
        this.stack.push(t);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public List<T> getAll() {
        return List.copyOf(stack);
    }

    @Override
    public Collection<T> reversed() {
        List<T> reversed = new ArrayList<>(this.stack);
        Collections.reverse(reversed);
        return reversed;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for(T el: this.stack){
            str.append(el);
            str.append("\n");
        }


        return "Stack contains: " + str.toString();
    }
}
