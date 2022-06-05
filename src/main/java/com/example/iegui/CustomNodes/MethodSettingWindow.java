package com.example.iegui.CustomNodes;

import com.example.iegui.util.Context;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MethodSettingWindow extends HBox {

    DoubleProperty downscale;

    public MethodSettingWindow(){
        this.setAlignment(Pos.CENTER);
    }

    public void setDownscale(DoubleProperty downscale, Context context) {
        Double[] availableDownscales = new Double[] {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        ChoiceBox<Double> downscaleSelector = new ChoiceBox<>(FXCollections.observableArrayList(availableDownscales));
        downscaleSelector.setValue(1.0);

        super.getChildren().add(new DescribedNode(context.getTextName("downscaleFactor"),downscaleSelector));
        this.downscale=downscale;
        super.setSpacing(20.0);
        super.setAlignment(Pos.CENTER);

        downscaleSelector.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue) -> downscale.set(availableDownscales[(int) newValue]));
    }

}
