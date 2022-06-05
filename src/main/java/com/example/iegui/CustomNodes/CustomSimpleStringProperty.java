package com.example.iegui.CustomNodes;

import javafx.beans.property.SimpleStringProperty;

/**
 * SimpleStringProperty which toString method returns the value
 */
public class CustomSimpleStringProperty extends SimpleStringProperty {
    @Override
    public String toString() {
        return this.getValue();
    }
}
