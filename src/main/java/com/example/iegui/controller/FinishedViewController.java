package com.example.iegui.controller;

import com.example.iegui.MainApplication;
import com.example.iegui.util.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FinishedViewController extends Controller implements Initializable {
    public ImageView ImageViewOld;
    public ImageView ImageViewNew;
    public VBox vbox;
    public Text textBefore;
    public Text textAfter;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setImage("Planning/Images/SettingView.png",ImageViewNew);
        setImage("Planning/Images/SettingView.png",ImageViewOld);
    }

    public void onButtonOKClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root,1024, 768);
            Stage stage = new Stage();
            stage.setScene(scene);
            Controller controller = fxmlLoader.getController();
            controller.setContext(context);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage thisStage = ((Stage)vbox.getScene().getWindow());
        thisStage.close();

    }

    public void setImage(String url, ImageView imageView){
        try{
            FileInputStream inputstream = new FileInputStream(url);
            Image image = new Image(inputstream);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
        }catch(Exception e){
            e.getMessage();
        }
    }

}
