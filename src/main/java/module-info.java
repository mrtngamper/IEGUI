module com.example.iegui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.yaml.snakeyaml;
    requires java.desktop;


    opens com.example.iegui.controller to javafx.fxml;
    opens com.example.iegui to javafx.fxml;
    opens com.example.iegui.AI to javafx.fxml;
    exports com.example.iegui;
    opens com.example.iegui.CustomNodes to javafx.fxml;
}