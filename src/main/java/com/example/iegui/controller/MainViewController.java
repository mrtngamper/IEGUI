package com.example.iegui.controller;
import com.example.iegui.AI.ImageEnhanceMethod;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;


/**
 * Controller for main window which is displayed after application launch
 */
public class MainViewController extends Controller implements Initializable {
    @FXML
    private BorderPane bP;
    @FXML
    private SplitPane splitPane;
    @FXML
    private BorderPane subBorderPane;
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
    private SimpleStringProperty imageFile = new SimpleStringProperty("");


    @Override
    public void setContext(Context context) {
        super.setContext(context);

        Button browse = new Button();
        browse.textProperty().bind(context.getTextName("browse"));
        exit.textProperty().bind(context.getTextName("exit"));
        imageName.textProperty().bind(context.getTextName("imageName"));
        settingsSetting.textProperty().bind(context.getTextName("settingsSetting"));
        languageSetting.textProperty().bind(context.getTextName("languageSetting"));
        lanDeSetting.textProperty().bind(context.getTextName("lanDeSetting"));
        lanEnSetting.textProperty().bind(context.getTextName("lanEnSetting"));
        tutorialSetting.textProperty().bind(context.getTextName("tutorialSetting"));
        helpSetting.textProperty().bind(context.getTextName("helpSetting"));
        aboutSetting.textProperty().bind(context.getTextName("aboutSetting"));
        open.textProperty().bind(context.getTextName("open"));
        file.textProperty().bind(context.getTextName("file"));

        vbox.getChildren().add(0, browse);

        bP.maxHeightProperty().bind(splitPane.heightProperty().multiply(0.25));
        bP.minHeightProperty().bind(splitPane.heightProperty().multiply(0.25));

        imageFile.setValue("Images/browse.png");
        image.setImage(new Image(new File(imageFile.getValue()).toURI().toString()));

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
                if(!fileType.equals("png") && !fileType.equals("jpg") && !fileType.equals("gif") && !fileType.equals("jps") && !fileType.equals("mpo")) {
                    Alerts.Error(context.getTextName("noFoto").getValue());
                } else {
                    imageFile.setValue(f.getAbsolutePath());
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

        imageFile.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if(!newValue.equals("")) {
                    vbox.getChildren().remove(browse);
                    imageName.textProperty().unbind();
                    String[] splitter = imageFile.getValue().split("/");
                    String fileName = splitter[splitter.length - 1];
                    image.fitHeightProperty().bind(hbox.heightProperty());
                    image.fitWidthProperty().bind(hbox.heightProperty());

                    image.setImage(new Image(new File(imageFile.getValue()).toURI().toString()));

                    imageName.setText(fileName);
                }
            }
        });

        /*ListView<ImageEnhanceMethod> list = new ListView();

        list.setItems(context.getMethods());

        list.setCellFactory(new Callback<ListView<ImageEnhanceMethod>, ListCell<ImageEnhanceMethod>>() {
            @Override
            public ListCell<ImageEnhanceMethod> call(ListView<ImageEnhanceMethod> imageEnhanceMethodListView) {
                ListCell cell = new ListCell();

            }
        });
        subBorderPane.setCenter(list);
        */
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onBrowseButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jps", "*.mpo"));
        File selectedFile = fileChooser.showOpenDialog(context.getStage());
        if (selectedFile != null) {
            imageFile.setValue(selectedFile.getAbsolutePath());
        }
    }

    public void onExitButton(ActionEvent actionEvent) {
        Platform.exit();
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