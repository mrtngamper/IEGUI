package com.example.iegui.util;

import javafx.scene.control.Alert;


/**
 * Fast Method to display graphical error messages.
 */
public class Alerts {
    public static void Warning(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(message);

        alert.showAndWait();
    }
}
