package com.example.iegui;


import com.example.iegui.AI.ImageEnhanceMethod;
import com.example.iegui.AI.SwinIR;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import com.example.iegui.util.Language;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.Locale;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Context context = new Context(stage,"Settings/settings.yml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 500, 350);

        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.US);
        if (os.equals("mac os x")) {
            root.setStyle("-fx-font-family: Arial");
        }

        Controller controller = fxmlLoader.getController();
        controller.setContext(context);

        stage.setTitle("IEGUI");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}