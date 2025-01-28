package model.adt;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreTable implements ISempahoreTable{
    private final Map<Integer, Pair<Integer, List<Integer>>> table;
    private int freeLocation;
    private final Lock lock;

    public SemaphoreTable() {
        this.table = new HashMap<>();
        this.freeLocation = 1;
        this.lock = new ReentrantLock();
    }

    @Override
    public synchronized int add(int value) {
        lock.lock();
        try{
            int currentLocation = freeLocation++;
            table.put(currentLocation,new Pair<>(value,new ArrayList<>()));
            return currentLocation;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public synchronized void update(int address, Pair<Integer, List<Integer>> value) {
        lock.lock();
        try{
            if(!table.containsKey(address)){
                throw new RuntimeException("Semaphore address not found" + address);
            }
            table.put(address,value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public synchronized Pair<Integer, List<Integer>> get(int address) {
        lock.lock();
        try {
            if (!table.containsKey(address)) {
                throw new RuntimeException("Semaphore address not found" + address);
            }
            return table.get(address);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public synchronized boolean contains(int address) {
        lock.lock();
        try{
            return table.containsKey(address);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, List<Integer>>> getContent() {
        lock.lock();
        try{
            return new HashMap<>(table);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public synchronized void setContent(Map<Integer, Pair<Integer, List<Integer>>> content) {
        lock.lock();
        try{
            table.clear();
            table.putAll(content);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("SemaphoreTable:\n");
        table.forEach((k,v)->builder.append(k).append("->").append(v).append("\n"));
        return builder.toString();
    }
}
