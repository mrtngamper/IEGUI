package com.example.iegui.controller;
import com.example.iegui.AI.ImageEnhanceMethod;
import com.example.iegui.util.Context;
import com.example.iegui.Exceptions.TextNotFoundException;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import com.example.iegui.util.Language;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.net.URL;
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
    private Text imageText;
    @FXML
    private Menu file;
    @FXML
    private MenuItem open;
    @FXML
    private MenuItem exit;
    @FXML
    private BorderPane borderPane;
    public HBox hbox;
    public ImageView image;
    public VBox vbox;
    public Text imageName;


    @Override
    public void setContext(Context context) {
        super.setContext(context);

        file.textProperty().bind(context.getTextName("file"));
        open.textProperty().bind(context.getTextName("open"));
        exit.textProperty().bind(context.getTextName("exit"));
        imageText.textProperty().bind(context.getTextName("imageName"));
        settingsSetting.textProperty().bind(context.getTextName("settingsSetting"));
        languageSetting.textProperty().bind(context.getTextName("languageSetting"));
        lanDeSetting.textProperty().bind(context.getTextName("lanDeSetting"));
        lanEnSetting.textProperty().bind(context.getTextName("lanEnSetting"));
        tutorialSetting.textProperty().bind(context.getTextName("tutorialSetting"));
        helpSetting.textProperty().bind(context.getTextName("helpSetting"));
        aboutSetting.textProperty().bind(context.getTextName("aboutSetting"));
        Button browse = new Button();
        browse.textProperty().bind(Language.get("browse"));
        vbox.getChildren().add(0, browse);
        imageName.setFont(Font.font(20));
        imageName.textProperty().bind(Language.get("browse2"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        imageText.setFont(Font.font("Arial"));
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