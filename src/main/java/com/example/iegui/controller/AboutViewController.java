package com.example.iegui.controller;

import com.example.iegui.MainApplication;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AboutViewController extends Controller {
    @FXML
    Button ok;
    @FXML
    BorderPane aboutPane;
    @FXML
    Text header;

    @FXML
    VBox imageBox;

    @Override
    public void setContext(Context context) {
        super.setContext(context);

        ok.textProperty().bind(context.getTextName("ok"));
        header.textProperty().bind(context.getTextName("ieguiTeam"));


        HBox imageHBox = new HBox();
        imageBox.getChildren().add(imageHBox);

        imageHBox.setAlignment(Pos.CENTER);
        imageHBox.setSpacing(10);


        ImageView woldas = new ImageView();
        ImageView icon = new ImageView();


        woldas.setPreserveRatio(true);
        icon.setPreserveRatio(true);

        woldas.setFitHeight(200);
        icon.setFitHeight(200);
        try {
            woldas.setImage(new Image((getClass().getResourceAsStream("/images/woldas.png"))));
            icon.setImage(new Image((getClass().getResourceAsStream("/images/icon.png"))));
        } catch (Exception e) {
            Alerts.Error(e.getMessage());
        }
        imageHBox.getChildren().addAll(woldas,icon);


        String hyperlink = "https://github.com/mrtngamper/IEGUI/";

        Hyperlink link = new Hyperlink(hyperlink);
        link.setText(hyperlink);

        link.setOnAction((ActionEvent event) -> {
            MainApplication.hostServices.showDocument(hyperlink);
            event.consume();
        });
        imageBox.getChildren().add(link);

    }

    public void close(ActionEvent actionEvent) {
        ((Stage)( aboutPane.getScene().getWindow())).close();
    }
}
