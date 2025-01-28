package model.adt;

import exceptions.EmptyDictionaryException;
import exceptions.ExpressionException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K, V> {
    void insert(K key, V value) ;
    void remove(K key) throws ExpressionException, EmptyDictionaryException;
    boolean contains(K key);
    V get(K key) throws ExpressionException;
    Set<K> keys();
    void update(K key, V value) throws ExpressionException;
    Map<K, V> getContent();
    MyIDictionary<K, V> deepCopy();
    Collection<V> getAllValues();
    Collection<V> values();
}
