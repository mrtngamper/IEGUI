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
import javafx.stage.Modality;
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
        stage.setMinHeight(480);
        stage.setMinWidth(640);

        Controller controller = fxmlLoader.getController();
        controller.setContext(context);

        context.getMethods().get(0).start("\\\\DC10\\ordnerumleitung$\\stgammar2\\Downloads\\kamel.jpg", "\\\\DC10\\ordnerumleitung$\\stgammar2\\Downloads\\kamel2.jpg"); // For Test purposes SwinIR
        //context.getMethods().get(1).start("/home/martin/Downloads/bild6.jpg", "/home/martin/Downloads/output.png"); // For Test purposes Low light
        //context.getMethods().get(2).start("/home/martin/Downloads/output14.png","/home/martin/Downloads/output15.png"); // For Testing purposes white balance
       // context.getMethods().get(3).start("/home/martin/Downloads/IMG-20220601-WA0000.jpg","/home/martin/Downloads/output13.png"); // For Testing purposes white balance
       // context.getMethods().get(4).start("/home/martin/Downloads/output.png","/home/martin/Downloads/output14.png"); // For Testing purposes white balance

        stage.setTitle("IEGUI");
        stage.setScene(scene);
        stage.show();


        if(context.isOpenWelcomeView()){
            Stage stage2 = new Stage();
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("welcome-view.fxml"));
            Parent root2 = loader2.load();
            Scene scene2 = new Scene(root2, 768, 700);
            stage2.setMinHeight(480);
            stage2.setMinWidth(640);

            stage2.setScene(scene2);
            stage2.titleProperty().bind(context.getTextName("welcome"));
            Controller controller2 =  loader2.getController();
            controller2.setContext(context);
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.show();
        }
    }
}