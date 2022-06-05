package com.example.iegui.controller;

import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AboutViewController extends Controller {
    @FXML
    Button ok;
    @FXML
    BorderPane pane;
    @FXML
    Text header;
    @FXML
    ImageView imageView;
    @FXML
    VBox imageBox;

    @Override
    public void setContext(Context context) {
        super.setContext(context);

        ok.textProperty().bind(context.getTextName("ok"));
        header.textProperty().bind(context.getTextName("ieguiTeam"));

        try {
            imageView.setImage(new Image((getClass().getResourceAsStream("/images/about.jpg"))));
        } catch (Exception e) {
            Alerts.Error(e.getMessage());
        }


    }

    public void close(ActionEvent actionEvent) {
        ((Stage)( pane.getScene().getWindow())).close();
    }
}
