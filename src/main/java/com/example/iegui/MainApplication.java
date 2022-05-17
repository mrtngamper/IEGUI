package com.example.iegui;


import com.example.iegui.AI.ImageEnhanceMethod;
import com.example.iegui.AI.SwinIR;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Context context = new Context(stage,"Settings/settings.yml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);


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