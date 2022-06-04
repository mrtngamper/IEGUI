package com.example.iegui.CustomNodes;

import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MethodSettingWindow extends HBox{

    DoubleProperty downscale;

    public void setDownscale(DoubleProperty downscale) {
        // TODO downscale numberfield
        this.downscale=downscale;
    }

}
