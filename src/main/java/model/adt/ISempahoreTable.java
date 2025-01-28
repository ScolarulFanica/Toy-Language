package model.adt;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface ISempahoreTable {
    int add(int value);
    void update(int address, Pair<Integer, List<Integer>> value);
    Pair<Integer,List<Integer>> get(int address);
    boolean contains(int address);
    Map<Integer, Pair<Integer,List<Integer>>> getContent();
    void setContent(Map<Integer, Pair<Integer,List<Integer>>> content);
}
