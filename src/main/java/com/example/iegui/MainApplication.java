package com.example.iegui;


import com.example.iegui.AI.ImageEnhanceMethod;
import com.example.iegui.AI.SwinIR;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import com.example.iegui.util.Language;
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
import java.util.Locale;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Context context = new Context(stage,"Settings/settings.yml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("finished-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 1024, 768);

        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.US);
        if (os.equals("mac os x")) {
            root.setStyle("-fx-font-family: Arial");
        }

        Controller controller = fxmlLoader.getController();
        controller.setContext(context);

        //stage.setFullScreen(true);
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setResizable(false);

        // context.getMethods().get(0).start("/home/martin/ImageEnhance/mixedillWB2/results/output2_WB.png", "/home/martin/Downloads/output3.png"); // For Test purposes SwinIR
        // context.getMethods().get(1).start("/home/martin/Downloads/bild6.jpg", "/home/martin/Downloads/output.png"); // For Test purposes Low light
        //context.getMethods().get(2).start("/home/martin/Downloads/output2.png","/home/martin/Downloads/output4.png"); // For Testing purposes white balance

        stage.setTitle("IEGUI");
        stage.setScene(scene);
       // stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}