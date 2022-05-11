module com.example.iegui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.iegui to javafx.fxml;
    exports com.example.iegui;
    exports com.example.iegui.util;
    opens com.example.iegui.util to javafx.fxml;
    exports com.example.iegui.controller;
    opens com.example.iegui.controller to javafx.fxml;
}