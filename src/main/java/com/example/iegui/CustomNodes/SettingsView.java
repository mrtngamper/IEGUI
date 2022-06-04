package com.example.iegui.CustomNodes;

import com.example.iegui.AI.ImageEnhanceMethod;
import com.example.iegui.MainApplication;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import javafx.application.HostServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.net.ssl.HostnameVerifier;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Settings View which is displayed when a image enhance method is selected from the main view.
 */
public class SettingsView extends BorderPane {


    private ImageEnhanceMethod currentMethod = null;
    private Context context;

    private final SimpleStringProperty output= new SimpleStringProperty();

    public SettingsView(Context context, EnhanceMethodListView parent, BorderPane pane, SimpleStringProperty openedFile){
        this.context=context;
        BorderPane bottomBox = new BorderPane();
        HBox topBox = new HBox();

        final double ARROW_SIZE = 5;

        Path arrowUp = new Path();
        arrowUp.getElements().addAll(new MoveTo(-ARROW_SIZE, -ARROW_SIZE), new LineTo(ARROW_SIZE, ARROW_SIZE),new MoveTo(-ARROW_SIZE,ARROW_SIZE),
                new LineTo(ARROW_SIZE, -ARROW_SIZE));
        Button back = new Button();
        back.setGraphic(arrowUp);
        back.setPrefWidth(85);

        Text outputPath = new Text();
        outputPath.textProperty().bind(output);

        Button selectOutput = new Button();
        selectOutput.textProperty().bind(context.getTextName("select"));
        selectOutput.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle(context.getTextName("saveFile").getValue());
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter(context.getTextName("imageFile").getValue(), "*.png"));
            File selectedFile = fileChooser.showSaveDialog(context.getStage());
            if(selectedFile!=null){
                output.setValue(selectedFile.getAbsolutePath());
            }
        });

        Button start = new Button();
        start.textProperty().bind(context.getTextName("start"));


        back.setOnAction(actionEvent -> pane.setCenter(parent));
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(openedFile.getValue().equals("")){
                    Alerts.Error(context.getTextName("noFileSelected").getValue());
                }else{
                    currentMethod.start(openedFile.getValue(), output.getValue());
                    pane.setCenter(parent);
                }
            }
        });

        topBox.setAlignment(Pos.CENTER_RIGHT);


        topBox.setId("settingsTopBox");
        bottomBox.setId("settingsBottomBox");


        topBox.getChildren().add(back);

        HBox left = new HBox();
        left.setSpacing(10);
        left.setAlignment(Pos.CENTER);
        left.getChildren().addAll(selectOutput,outputPath);
        BorderPane.setAlignment(left,Pos.CENTER);
        BorderPane.setAlignment(start,Pos.CENTER);

        bottomBox.setLeft(left);
        bottomBox.setRight(start);

        this.setTop(topBox);
        this.setBottom(bottomBox);
    }

    /**
     * Changes the ImageEnhanceMethod of which the information is displayed
     * @param method The new ImageEnhanceMethod
     */
    public void setEnhanceMethod(ImageEnhanceMethod method){
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            output.setValue(System.getenv("HOMEPATH")+"\\output.png");
        } else {
            output.setValue(System.getenv("HOME")+"/output.png");
        }
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
                description_long.wrappingWidthProperty().setValue(t1.getWidth()*0.95);
                description_short.wrappingWidthProperty().setValue(t1.getWidth()*0.95);
                header.wrappingWidthProperty().setValue(t1.getWidth()*0.95);
            }
        });


        vbox.setSpacing(5);
        vbox.setPadding(new Insets(4, 0, 0, 8));

        if(currentMethod.getSettingWindow()!=null){
            vbox.getChildren().add(currentMethod.getSettingWindow());
        }

        vbox.getChildren().add(header);
        vbox.getChildren().add(description_short);
        vbox.getChildren().add(description_long);


        for (String image: currentMethod.getExamples().keySet() ) {
            HBox hbox = new HBox();

            try {
                ImageView input = Context.loadImage(Context.independent(currentMethod.getLocation()+"/Config/"+ image));
                ImageView output = Context.loadImage(Context.independent(currentMethod.getLocation()+"/Config/"+currentMethod.getExamples().get(image)));
                HBox.setHgrow(hbox, Priority.ALWAYS);

                input.fitWidthProperty().bind(scrollPane.widthProperty().divide(2.5));
                output.fitWidthProperty().bind(scrollPane.widthProperty().divide(2.5));

                hbox.setPadding(new Insets(10,0,10,0));
                hbox.setSpacing(5);
                hbox.setAlignment(Pos.CENTER);
                hbox.getChildren().addAll(input,output);
                vbox.getChildren().add(hbox);
            } catch (FileNotFoundException e) {
                Alerts.Error(e.getMessage());
                continue;
            }
        }

        Hyperlink link = new Hyperlink(currentMethod.getHyperlink());
        link.setText(currentMethod.getHyperlink());

        link.setOnAction((ActionEvent event) -> {
            MainApplication.hostServices.showDocument(currentMethod.getHyperlink());
            event.consume();
        });

        vbox.getChildren().add(link);

        this.setCenter(scrollPane);
    }

}
