package com.example.iegui.controller;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import com.example.iegui.util.Language;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.example.iegui.util.Language;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.security.AllPermission;
import java.util.ResourceBundle;

/**
 * Controller for main window which is displayed after application launch
 */
public class MainViewController extends Controller implements Initializable {
    @FXML
    private Menu settingsSetting;
    @FXML
    private Menu languageSetting;
    @FXML
    private MenuItem lanDeSetting;
    @FXML
    private MenuItem lanEnSetting;
    @FXML
    private CheckMenuItem tutorialSetting;
    @FXML
    private Menu helpSetting;
    @FXML
    private MenuItem aboutSetting;
    @FXML
    private Text imageName;
    @FXML
    private Menu file;
    @FXML
    private MenuItem open;
    @FXML
    private MenuItem exit;
    @FXML
    private BorderPane borderPane;
    @FXML
    private HBox hbox;
    @FXML
    private ImageView image;
    @FXML
    private VBox vbox;


    @Override
    public void setContext(Context context) {
        super.setContext(context);

        Button browse = new Button();
        browse.textProperty().bind(context.getTextName("browse"));
        vbox.getChildren().add(0, browse);

        browse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                onBrowseButton(actionEvent);
            }
        });

        imageName.setFont(Font.font(20));
        imageName.textProperty().bind(context.getTextName("browse2"));

        vbox.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                File f = db.getFiles().get(0);

                System.out.println(f.getAbsolutePath());
                String[] splitter = f.getName().split("\\.");
                String fileType = splitter[splitter.length - 1];
                System.out.println(fileType);
                if(!fileType.equals("png") && !fileType.equals("jpeg")) {
                    Alerts.Error(context.getTextName("noFoto").getValue());
                }
            }
        });

        vbox.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != browse && event.getDragboard().hasFiles()) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public void onBrowseButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpeg", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(context.getStage());
        if (selectedFile != null) {
            System.out.println("Suppi");
        }
    }




    /*ListView<ImageEnhanceMethod> list = new ListView();

        list.setItems(context.getMethods());

        list.setCellFactory(new Callback<ListView<ImageEnhanceMethod>, ListCell<ImageEnhanceMethod>>() {
        @Override
        public ListCell<ImageEnhanceMethod> call(ListView<ImageEnhanceMethod> imageEnhanceMethodListView) {
            ListCell cell = new ListCell();
            cell.setGraphic
        }
    });*/
}