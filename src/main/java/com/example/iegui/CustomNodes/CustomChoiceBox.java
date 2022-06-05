package com.example.iegui.CustomNodes;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * ChoiceBox which automatically binds a property to the selected field
 */
public class CustomChoiceBox extends ChoiceBox<SimpleStringPropertyWrapper> {

    public CustomChoiceBox(HashMap<SimpleStringProperty,String> choices, SimpleStringProperty bindingString){
        ObservableList<SimpleStringPropertyWrapper> list = FXCollections.observableArrayList();


        for (SimpleStringProperty i: choices.keySet() ) {
            {
                list.add(new SimpleStringPropertyWrapper(i));
            }
        }
        list.sort(Comparator.comparing(o -> o.getProperty().getValue()));
        this.setItems(list);
        this.getSelectionModel().selectedItemProperty().addListener((observableValue, simpleStringProperty, t1) -> {
            bindingString.setValue(choices.get(t1.getProperty()));
        });

        this.getSelectionModel().select(0);

    }
}
