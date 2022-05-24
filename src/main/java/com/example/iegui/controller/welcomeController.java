package com.example.iegui.controller;

import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;

public class welcomeController extends Controller  {
    public Button buttonOK;
    public javafx.scene.control.CheckBox CheckBox;
    public HBox HboxPag;
    public Pagination pagination;
    public BorderPane borderPane;
    public HBox HboxBottom;

    public void onOKButtonPressed() {
        Stage stage = (Stage) buttonOK.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setContext(Context context) {

        this.context=context;

        CheckBox.selectedProperty().setValue(context.openWelcomeViewProperty().getValue());
        context.openWelcomeViewProperty().bind(CheckBox.selectedProperty());

        Text textFieldPage1Title = new Text();
        Text textFieldPage1Text = new Text();
        Text textFieldPage2Title = new Text();
        Text textFieldPage2Text = new Text();
        Text textFieldPage3Title = new Text();
        Text textFieldPage3Text = new Text();
        Text textFieldPage4Title = new Text();
        Text textFieldPage4Text = new Text();
        Text textFieldPage5Title = new Text();
        Text textFieldPage5Text = new Text();

        ImageView imageViewcooleTypn = addimage("Planning/Images/85165e6f-e900-4740-8b3b-77d3b48f7d8b.jpg");
        ImageView imageView = addimage("Planning/Images/MainView.png");
        ImageView imageViewsettings = addimage("Planning/Images/SettingView.png");


        imageViewcooleTypn.setFitHeight(800);
        imageViewcooleTypn.setFitWidth(800);


        imageView.setFitWidth(500);
        imageView.setFitHeight(500);

        imageViewsettings.setFitWidth(500);
        imageViewsettings.setFitHeight(500);

        setTitlefont(textFieldPage1Title);
        setTitlefont(textFieldPage2Title);
        setTitlefont(textFieldPage3Title);
        setTitlefont(textFieldPage4Title);
        setTitlefont(textFieldPage5Title);

        setTextFond(textFieldPage1Text);
        setTextFond(textFieldPage2Text);
        setTextFond(textFieldPage3Text);
        setTextFond(textFieldPage4Text);
        setTextFond(textFieldPage5Text);

        VBox pageBoxMain = new VBox();
        VBox pagBoxWelcome = new VBox();
        VBox pageBoxSettings = new VBox();
        VBox pageBoxLoading = new VBox();
        VBox pageBoxFinish = new VBox();

        pageBoxFinish.setAlignment(Pos.CENTER);
        pageBoxLoading.setAlignment(Pos.CENTER);
        pageBoxSettings.setAlignment(Pos.CENTER);
        pagBoxWelcome.setAlignment(Pos.CENTER);
        pageBoxMain.setAlignment(Pos.CENTER);

        pageBoxMain.getChildren().add(textFieldPage2Title);
        pageBoxMain.getChildren().add(textFieldPage2Text);
        pageBoxMain.getChildren().add(imageView);

        pagBoxWelcome.getChildren().add(textFieldPage1Title);
        pagBoxWelcome.getChildren().add(textFieldPage1Text);
        pagBoxWelcome.getChildren().add(imageViewcooleTypn);

        pageBoxSettings.getChildren().add(textFieldPage3Title);
        pageBoxSettings.getChildren().add(textFieldPage3Text);
        pageBoxSettings.getChildren().add(imageViewsettings);

        pageBoxLoading.getChildren().add(textFieldPage4Title);
        pageBoxLoading.getChildren().add(textFieldPage4Text);

        pageBoxFinish.getChildren().add(textFieldPage5Title);
        pageBoxFinish.getChildren().add(textFieldPage5Text);

        try {
            textFieldPage1Title.textProperty().bind(context.getTextName("welcomeTitle"));
            textFieldPage1Text.textProperty().bind(context.getTextName("welcomeText"));
            textFieldPage2Title.textProperty().bind(context.getTextName("mainViewWelcomeTitle"));
            textFieldPage2Text.textProperty().bind(context.getTextName("mainViewWelcomeText"));
            textFieldPage3Title.textProperty().bind(context.getTextName("settingsWelcomeTitle"));
            textFieldPage3Text.textProperty().bind(context.getTextName("settingsWelcomeText"));
            textFieldPage4Title.textProperty().bind(context.getTextName("loadingViewWelcomeTitle"));
            textFieldPage4Text.textProperty().bind(context.getTextName("loadingViewWelcomeText"));
            textFieldPage5Title.textProperty().bind(context.getTextName("finishViewWelcomeTitle"));
            textFieldPage5Text.textProperty().bind(context.getTextName("finishViewWelcomeText"));
        }catch (Exception e){
            e.getMessage();
        }



        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer integer) {
                switch (integer){
                    case 0: return pagBoxWelcome;
                    case 1: return pageBoxMain;
                    case 2: return pageBoxSettings;
                    case 3: return pageBoxLoading;
                    case 4: return pageBoxFinish;
                    default: return null;
                }
            }
        });
    }

    private void setTextFond(Text text) {
        text.setFont(Font.font("verdana",16));
    }

    private void setTitlefont(Text textField) {
        textField.setUnderline(true);
        textField.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        Color colorTitle = Color.web("#5E747F");
        textField.setFill(colorTitle);
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

    public void onCheckBoxPressed() {
        context.storeSettings();

    }
}