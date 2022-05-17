package com.example.iegui.controller;
import com.example.iegui.AI.ImageEnhanceMethod;
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

/**
 * Controller for main window which is displayed after application launch
 */
public class MainViewController extends Controller {
    public HBox hbox;
    public ImageView image;
    public VBox vbox;
    public Text imageName;

    @Override
    public void setContext(Context context) {
        super.setContext(context);

        Button browse = new Button();
        browse.textProperty().bind(Language.get("browse"));
        vbox.getChildren().add(0, browse);
        imageName.setFont(Font.font(20));
        imageName.textProperty().bind(Language.get("browse2"));
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