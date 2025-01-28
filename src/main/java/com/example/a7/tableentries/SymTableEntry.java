package com.example.a7.tableentries;

import javafx.beans.property.SimpleStringProperty;
import model.value.IValue;

public class SymTableEntry {
    private final String id;
    private final IValue val;

    public SymTableEntry(String id, IValue val) {
        this.id = id;
        this.val = val;
    }

    public String getId() {
        return this.id;
    }

    public IValue getVal() {
        return this.val;
    }

    public SimpleStringProperty idProperty() {
        return new SimpleStringProperty(this.id);
    }

    public SimpleStringProperty valProperty() {
        return new SimpleStringProperty(this.val.toString());
    }

}
