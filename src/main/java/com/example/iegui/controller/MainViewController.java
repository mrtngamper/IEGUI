package com.example.iegui.controller;
import com.example.iegui.AI.ImageEnhanceMethod;
import com.example.iegui.CustomNodes.DragAndDrop;
import com.example.iegui.CustomNodes.ImageLoaded;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
    private Menu file;
    @FXML
    private MenuItem open;
    @FXML
    private MenuItem exit;

    private SimpleStringProperty imageFile = new SimpleStringProperty("");

    private float ratio;

    @FXML
    private MenuItem close;

    private DragAndDrop dragAndDrop ;

    private ImageLoaded imageLoaded;

    @Override
    public void setContext(Context context) {
        super.setContext(context);

        Button browse = new Button();
        browse.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                onBrowseButton(actionEvent);
            }
        });


        exit.textProperty().bind(context.getTextName("exit"));
        Label imageName = new Label();
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
        close.textProperty().bind(context.getTextName("close"));

        bP.maxHeightProperty().bind(splitPane.heightProperty().multiply(0.25));
        bP.minHeightProperty().bind(splitPane.heightProperty().multiply(0.25));



        dragAndDrop= new DragAndDrop(context,browse);
        imageLoaded = new ImageLoaded();
        bP.setCenter(dragAndDrop);


        tutorialSetting.selectedProperty().setValue(context.openWelcomeViewProperty().getValue());
        Bindings.bindBidirectional(context.openWelcomeViewProperty(),tutorialSetting.selectedProperty());



        imageName.setFont(Font.font(20));
        imageName.textProperty().bind(context.getTextName("browse2"));
        bP.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                File f = db.getFiles().get(0);
                imageFile.setValue(f.getAbsolutePath());
            }
        });
        imageFile.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                try {
                    if(t1.equals("")){
                        throw new Exception();
                    }
                    bP.setCenter(imageLoaded);
                    imageLoaded.setNewImage(new File(t1),context);
                } catch (Exception e) {
                    imageFile.setValue("");
                    bP.setCenter(dragAndDrop);
                }
            }
        });

        bP.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != browse && event.getDragboard().hasFiles()) {
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            }
        });

        final String[] oldStyle = {""};
        bP.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                oldStyle[0] =bP.getStyle();
                bP.setStyle("-fx-background-color: #D2D0BA");
            }
        });
        bP.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                bP.setStyle(oldStyle[0]);
            }
        });


        ListView<ImageEnhanceMethod> list = new ListView();

        list.setItems(context.getMethods());

        list.setCellFactory(new Callback<ListView<ImageEnhanceMethod>, ListCell<ImageEnhanceMethod>>() {
            @Override
            public ListCell<ImageEnhanceMethod> call(ListView<ImageEnhanceMethod> imageEnhanceMethodListView) {
                return new ListCell<>(){
                    @Override
                    protected void updateItem(ImageEnhanceMethod imageEnhanceMethod, boolean b) {
                        super.updateItem(imageEnhanceMethod, b);
                        if(imageEnhanceMethod!=null){

                            VBox methodWindow = new VBox();
                            Label name = new Label();
                            name.textProperty().bind(context.getTextName(imageEnhanceMethod.getName()));
                            //name.getText().setStyle("-fx-font-weight: bold");

                            name.setFont(new Font("Arial", 20));
                            Label description = new Label();
                            description.textProperty().bind(context.getTextName(imageEnhanceMethod.getDescription()));
                            methodWindow.getChildren().addAll(name, description);

                            for (String beforePath: imageEnhanceMethod.getExamples().keySet()) {
                                //ImageView before = new ImageView(new Image(beforePath));
                                //ImageView after = new ImageView(new Image(imageEnhanceMethod.getExamples().get(beforePath)));
                                //methodWindow.getChildren().addAll(before, after);
                            }
                            try {
                                methodWindow.getChildren().add(imageEnhanceMethod.getSettingWindow().getHBox());
                            } catch(Exception e) {}
                            setGraphic(methodWindow);
                        }
                    }
                };

            }
        });
        subBorderPane.setCenter(list);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onBrowseButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.jps", "*.mpo"));
        File selectedFile = fileChooser.showOpenDialog(context.getStage());
        if (selectedFile != null) {
            imageFile.setValue(selectedFile.getAbsolutePath());
        }
    }

    public void onExitButton(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void onLanguagePressed(ActionEvent actionEvent) {
        MenuItem mi = (MenuItem) actionEvent.getSource();
        String language = mi.getText().toLowerCase();
        context.setLang(language);
    }

    public void onCloseButton(ActionEvent actionEvent) {
        imageFile.setValue("");
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