package com.example.iegui.util;

import javafx.scene.control.Alert;


/**
 * Easy method to display graphical error messages.
 */
public class Alerts {
    public static void Warning(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(message);

        alert.showAndWait();
    }

    public static void Error(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);

        alert.showAndWait();
    }
}