module com.example.iegui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.iegui.controller to javafx.fxml;
    opens com.example.iegui to javafx.fxml;
    exports com.example.iegui;
}