package com.example.iegui.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;


/**
 * Easy method to display graphical error messages.
 */
public class Alerts { // TODO Error Only Executable In Event Thread
    /**
     * Displays warning alert
     * @param message Header message
     */
    public static void Warning(String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(message);

                alert.showAndWait();
            }
        });

    }

    /**
     * Displays error alert
     * @param message Header message
     */
    public static void Error(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(message);

                alert.showAndWait();
            }
        });

    }
}
