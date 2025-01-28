package com.example.a7.tableentries;

import javafx.beans.property.SimpleStringProperty;
import model.value.IValue;

public class HeapTableEntry {
    private final int address;
    private final IValue value;

    public HeapTableEntry(int address, IValue value) {
        this.address = address;
        this.value = value;
    }

    public int getAddress() {
        return this.address;
    }

    public IValue getValue() {
        return this.value;
    }

    public SimpleStringProperty addressProperty() {
        return new SimpleStringProperty(String.valueOf(this.address));
    }

    public SimpleStringProperty valueProperty() {
        return new SimpleStringProperty(this.value.toString());
    }
}
