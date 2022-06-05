package com.example.iegui.CustomNodes;

import javafx.beans.property.SimpleStringProperty;

/**
 * Wraps a SimpleStringProperty and overrides the toString method to return its value
 */
public class SimpleStringPropertyWrapper {
    private SimpleStringProperty property;

    public SimpleStringPropertyWrapper(SimpleStringProperty property){
        this.property = property;
    }

    public SimpleStringProperty getProperty(){
        return  property;
    }

    @Override
    public String toString() {
        return this.property.getValue();
    }
}
