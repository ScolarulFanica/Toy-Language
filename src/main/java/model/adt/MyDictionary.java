package model.adt;

import exceptions.EmptyDictionaryException;
import exceptions.ExpressionException;

import java.util.*;

public class MyDictionary <K, V> implements MyIDictionary<K, V> {
    Map<K, V> map;

    public MyDictionary() {
        map = new HashMap<>();
    }

    public MyDictionary(Map<K, V> map) {
        this.map = map;
    }
    @Override
    public void insert(K key, V value){
        this.map.put(key, value);
    }

    @Override
    public void remove(K key) throws ExpressionException, EmptyDictionaryException {
        if(this.map.isEmpty())
            throw new EmptyDictionaryException("Dictionary is empty");
        if(this.map.containsKey(key))
            this.map.remove(key);
        else
            throw new ExpressionException("Key not found");
    }

    @Override
    public boolean contains(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public V get(K key) throws ExpressionException {
        if(this.map.containsKey(key))
            return this.map.get(key);
        else
            throw new ExpressionException("Key not found");
    }


    public String toString() {
        StringBuilder st = new StringBuilder();
        this.map.forEach((k,v)->{
            st.append(k).append(" -> ").append(v).append("\n");
        });
        return "Dictionary contains: " + st.toString();

    }

    @Override
    public void update(K key, V value) throws ExpressionException {
        if(!this.map.containsKey(key)){
            throw new ExpressionException("Key not found");
        }
        this.map.put(key, value);
    }

    @Override
    public Map<K,V> getContent(){
        return map;
    }

    @Override
    public Set<K> keys() {
        return map.keySet();
    }

    @Override
    public MyIDictionary<K, V> deepCopy() {
        return new MyDictionary<>(this.map);
    }

    @Override
    public Collection<V> getAllValues() {
        return map.values();
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (K key : this.map.keySet()) {
            values.add(this.map.get(key));
        }
        return values;
    }

}
