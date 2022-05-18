package com.example.iegui.controller;

import com.example.iegui.util.Controller;
import com.example.iegui.util.Language;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class welcomeController extends Controller implements Initializable  {
    public Button buttonOK;
    public javafx.scene.control.CheckBox CheckBox;
    public HBox HboxPag;
    public Pagination pagination;
    public BorderPane borderPane;
    public HBox HboxBottom;

    public void onOKButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) buttonOK.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Text textFieldPage1 = new Text();
        Text textFieldPage2 = new Text();
        Text textFieldPage3 = new Text();
        Text textFieldPage4 = new Text();
        Text textFieldPage5 = new Text();


        ImageView imageViewcooleTypn = addimage("Planning/Images/CooleTypen.jpg");
        ImageView imageView = addimage("Planning/Images/MainView.png");
        ImageView imageViewsettings = addimage("Planning/Images/SettingView.png");


        VBox pageBoxMain = new VBox();
        VBox pagBoxWelcome = new VBox();
        VBox pageBoxSettings = new VBox();

        pageBoxMain.getChildren().add(textFieldPage2);
        pageBoxMain.getChildren().add(imageView);

        pagBoxWelcome.getChildren().add(textFieldPage1);
        pagBoxWelcome.getChildren().add(imageViewcooleTypn);

        pageBoxSettings.getChildren().add(textFieldPage3);
        pageBoxSettings.getChildren().add(imageViewsettings);

        try {
            textFieldPage1.textProperty().bind(context.getTextName("welcome"));
            textFieldPage2.textProperty().bind(context.getTextName("mainViewWelcome"));
            textFieldPage3.textProperty().bind(context.getTextName("settingsWelcome"));
            textFieldPage4.textProperty().bind(context.getTextName("loadingViewWelcome"));
            textFieldPage5.textProperty().bind(context.getTextName("finishViewWelcome"));
        }catch (Exception e){

        }

        textFieldPage1.setFont(new Font(20));
        textFieldPage2.setFont(new Font(20));
        textFieldPage3.setFont(new Font(20));
        textFieldPage4.setFont(new Font(20));
        textFieldPage5.setFont(new Font(20));
        if (CheckBox.isSelected()){
            File file = new File("url");
            try {
                FileWriter fw = new FileWriter(file);
                FileReader fr = new FileReader(file);
                int i;
                while((i=fr.read())!=-1){
                   if(Integer.toString(i).equals("s")){
                       fw.write("t");
                       break;
                   }
                }
                fr.close();
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer integer) {
                switch (integer){
                    case 0: return pagBoxWelcome;
                    case 1: return pageBoxMain;
                    case 2: return pageBoxSettings;
                    case 3: return textFieldPage4;
                    case 4: return textFieldPage5;
                    default: return null;
                }
            }
        });
    }

    public ImageView addimage(String url){
        ImageView imageView = new ImageView();
        try{
            FileInputStream inputstream = new FileInputStream(url);
            Image image = new Image(inputstream);
            imageView.setImage(image);
            imageView.setFitWidth(600);
            imageView.setFitHeight(600);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
        }catch(Exception e){
            e.getMessage();
        }
        return imageView;
    }
}
