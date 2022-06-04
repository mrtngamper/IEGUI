package com.example.iegui.CustomNodes;

import com.example.iegui.AI.ImageEnhanceMethod;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

public class SettingsView extends BorderPane {

    private ImageEnhanceMethod currentMethod = null;
    private Context context;

    public SettingsView(Context context, EnhanceMethodListView parent, BorderPane pane, SimpleStringProperty openedFile){
        this.context=context;
        HBox bottomBox = new HBox();
        HBox topBox = new HBox();
        Button back = new Button("X");
        Button start = new Button();
        start.textProperty().bind(context.getTextName("start"));


        back.setOnAction(actionEvent -> pane.setCenter(parent));
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(openedFile.getValue().equals("")){
                    Alerts.Error(context.getTextName("noFileSelected").getValue());
                }else{
                    String output = "";
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle(context.getTextName("saveFile").getValue());
                    fileChooser.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter(context.getTextName("imageFile").getValue(), "*.png"));
                    File selectedFile = fileChooser.showSaveDialog(context.getStage());
                    output= selectedFile.getAbsolutePath();

                    currentMethod.start(openedFile.getValue(), output);

                    pane.setCenter(parent);
                }
            }
        });

        bottomBox.setAlignment(Pos.CENTER_RIGHT);
        topBox.setAlignment(Pos.CENTER_RIGHT);


        topBox.setId("settingsTopBox");
        bottomBox.setId("settingsBottomBox");

        topBox.getChildren().add(back);
        bottomBox.getChildren().add(start);

        this.setTop(topBox);
        this.setBottom(bottomBox);
    }

    public void setEnhanceMethod(ImageEnhanceMethod method){
        this.currentMethod=method;

        ScrollPane scrollPane = new ScrollPane();
        VBox vbox = new VBox();
        scrollPane.setContent(vbox);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);


        Text header = new Text();
        header.textProperty().bind(context.getTextName(currentMethod.getName()));

        Text description_short = new Text();
        description_short.textProperty().bind(context.getTextName(currentMethod.getDescription()));

        Text description_long = new Text();
        description_long.textProperty().bind(context.getTextName(currentMethod.getLong_description()));

        header.setStyle("-fx-font-family: Arial; -fx-font-weight: bold; -fx-font-size: 30");
        description_short.setStyle("-fx-font-family: Arial; -fx-font-size: 20; -fx-font-style: italic");
        description_long.setStyle("-fx-font-family: Arial; -fx-font-size: 14");

        scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1) {
                description_long.wrappingWidthProperty().setValue(t1.getWidth()*0.9);
                description_short.wrappingWidthProperty().setValue(t1.getWidth()*0.9);
                header.wrappingWidthProperty().setValue(t1.getWidth()*0.9);
            }
        });


        vbox.setSpacing(5);
        vbox.setPadding(new Insets(0, 0, 0, 8));
        vbox.getChildren().add(header);
        vbox.getChildren().add(description_short);
        vbox.getChildren().add(description_long);

        this.setCenter(scrollPane);
    }
}
