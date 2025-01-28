package model.adt;


import java.util.Collection;
import java.util.List;

public interface MyIList<T> {

    void add(T element);
    List<T> getAll();
    Collection<T> values();
}
