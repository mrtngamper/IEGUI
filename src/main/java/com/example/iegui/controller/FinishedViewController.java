package com.example.iegui.controller;

import com.example.iegui.util.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class FinishedViewController extends Controller implements Initializable {
    public ImageView ImageViewOld;
    public ImageView ImageViewNew;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setImage("Planning/Images/SettingView.png",ImageViewNew);
        setImage("Planning/Images/SettingView.png",ImageViewOld);
    }

    public void onButtonOKClick(ActionEvent actionEvent) {

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
