package com.example.iegui.CustomNodes;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MethodSettingWindow {
    private HBox hBox = new HBox();

    public void setHBox(HBox hBox, DoubleProperty downscale) {
        // TODO downscale numberfield
        this.hBox = hBox;
    }

    public HBox getHBox() {
        return hBox;
    }
}
