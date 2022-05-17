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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
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


        try {
            textFieldPage1.textProperty().bind(Language.get("welcome"));
            textFieldPage2.textProperty().bind(Language.get("mainViewWelcome"));
            textFieldPage3.textProperty().bind(Language.get("settingsWelcome"));
            textFieldPage4.textProperty().bind(Language.get("loadingViewWelcome"));
            textFieldPage5.textProperty().bind(Language.get("finishViewWelcome"));
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
                    case 0: return textFieldPage1;
                    case 1: return textFieldPage2;
                    case 2: return textFieldPage3;
                    case 3: return textFieldPage4;
                    case 4: return textFieldPage5;
                    default: return null;
                }
            }
        });
    }
}
