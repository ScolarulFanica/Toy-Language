package model.adt;

import exceptions.EmptyDictionaryException;
import exceptions.ExpressionException;

import java.util.List;
import java.util.Set;

public interface MyIDictionary<K, V> {
    void insert(K key, V value) ;
    void remove(K key) throws ExpressionException, EmptyDictionaryException;
    boolean contains(K key);
    public V get(K key) throws ExpressionException;
    public Set<K> keys();
}