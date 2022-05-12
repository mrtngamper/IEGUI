package com.example.iegui.util;

import javafx.stage.Stage;

/**
 * Context which is given to every Controller. It contains shared data between elements of the UI.
 */
public class Context {
    /**
     * The stage of the main window
     */
    protected Stage stage;

    public  Context(Stage stage){
        this.stage=stage;
    }
}
