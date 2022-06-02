package com.example.iegui;


import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Context context = new Context(stage, "Settings/settings.yml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 768, 500);

        Controller controller = fxmlLoader.getController();
        controller.setContext(context);


            stage.setResizable(false);


        //context.getMethods().get(0).start("/home/martin/Downloads/output12.png", "/home/martin/Downloads/output13.png"); // For Test purposes SwinIR
        //context.getMethods().get(1).start("/home/martin/Downloads/bild6.jpg", "/home/martin/Downloads/output.png"); // For Test purposes Low light
        //context.getMethods().get(2).start("/home/martin/Downloads/output11.png","/home/martin/Downloads/output12.png"); // For Testing purposes white balance
        //context.getMethods().get(3).start("/home/martin/Downloads/input2.png","/home/martin/Downloads/output11.png"); // For Testing purposes white balance
        //context.getMethods().get(4).start("/home/martin/Downloads/IMG_20220525_214007.jpg","/home/martin/Downloads/output14.png"); // For Testing purposes white balance

        stage.setTitle("IEGUI");
        stage.setScene(scene);
        stage.show();
    }
}