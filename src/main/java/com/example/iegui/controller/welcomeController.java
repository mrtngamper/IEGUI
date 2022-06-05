package com.example.iegui.controller;

import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.*;

public class welcomeController extends Controller  {

    @FXML
    public Button buttonOK;

    @FXML
    public javafx.scene.control.CheckBox CheckBox;

    @FXML
    public Pagination pagination;

    @FXML
    public BorderPane borderPane;

    @FXML
    public HBox HboxBottom;




    public void onOKButtonPressed() {
        Stage stage = (Stage) buttonOK.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setContext(Context context) {
        this.context=context;
        int pagecount=7;

        CheckBox.selectedProperty().setValue(context.openWelcomeViewProperty().getValue());
        Bindings.bindBidirectional(context.openWelcomeViewProperty(),CheckBox.selectedProperty());

        ScrollPane pagBoxWelcome = createView(context.getTextName("welcomeTitle"),context.getTextName("welcomeText"),"/images/85165e6f-e900-4740-8b3b-77d3b48f7d8b.jpg");
        ScrollPane pageBoxMain = createView(context.getTextName("mainViewWelcomeTitle"),context.getTextName("mainViewWelcomeText"),"/images/mainView.png");
        ScrollPane pageBoxSettings = createView(context.getTextName("settingsWelcomeTitle"),context.getTextName("settingsWelcomeText"),"/images/mainView2.png");

        ScrollPane pageBoxSettings2 = createView(context.getTextName("settingsWelcomeTitle2"),context.getTextName("settingsWelcomeText2"),"/images/mainView3.png");
        ScrollPane pageBoxLoading = createView(context.getTextName("loadingViewWelcomeTitle"),context.getTextName("loadingViewWelcomeText"),"/images/loading.png");

        ScrollPane pageBoxWait = createView(context.getTextName("loadingViewWelcomeTitle2"),context.getTextName("loadingViewWelcomeText2"),null);
        ScrollPane pageBoxEnjoy = createView(context.getTextName("finishViewWelcomeTitle"),context.getTextName("finishViewWelcomeText"),"/images/result.png");



        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer integer) {
                switch (integer){
                    case 0: return pagBoxWelcome;
                    case 1: return pageBoxMain;
                    case 2: return pageBoxSettings;
                    case 3: return pageBoxSettings2;
                    case 4: return pageBoxLoading;
                    case 5: return pageBoxWait;
                    case 6: return pageBoxEnjoy;
                    default: return null;
                }
            }
        });
        pagination.setPageCount(pagecount);
        pagination.maxPageIndicatorCountProperty().bind(pagination.pageCountProperty());
    }

    /**
     * Formatting for normal texts
     * @param text
     */
    private void setTextFond(Text text) {
        text.setFont(Font.font("verdana",16));
    }

    /**
     * Formatting for title texts
     * @param textField
     */
    private void setTitlefont(Text textField) {
        textField.setUnderline(true);
        textField.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        Color colorTitle = Color.web("#5E747F");
        textField.setFill(colorTitle);
        textField.setTextAlignment(TextAlignment.CENTER);
    }


    /**
     * Creates ImageView from a given image url
     * @param url The url to the iamge
     * @return An ImageView which shows the image from the arguments.
     */
    private ImageView addimage(String url){
        ImageView imageView = new ImageView();
        try{
            InputStream inputstream = this.getClass().getResourceAsStream(url);
            Image image = new Image(inputstream);
            imageView.setImage(image);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            imageView.setCache(true);
        }catch(Exception e){
            e.getMessage();
        }
        return imageView;
    }

    /**
     * Creates view for Pagination element. It returns a Vertically scrollable Pain.
     * @param title The title of the help paragraph
     * @param text The description text for the paragraph
     * @param imagePath The path to an image file (optional - it is ignored when null)
     * @return A ScrollPane containing all the information from the arguments.
     */
    private ScrollPane createView(SimpleStringProperty title, SimpleStringProperty text, String imagePath){
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane();

        ImageView image =null;
        if(imagePath!=null){
             image = addimage(imagePath);
        }

        Text titletext = new Text();
        Text texttext = new Text();

        setTitlefont(titletext);
        setTextFond(texttext);

        scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1) {
                texttext.wrappingWidthProperty().setValue(t1.getWidth()*0.9);
            }
        });
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        if(image!=null){
            ImageView finalImage = image;
            scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
                @Override
                public void changed(ObservableValue<? extends Bounds> observableValue, Bounds bounds, Bounds t1) {
                    finalImage.setFitWidth((int)(t1.getWidth()*0.8));

                    // This if clause is used, because with ScrollBarPolicy.AS_NEEDED
                    // the whole view will flicker, when it can't decide whether
                    // it should display the scrollbar. Please do not change!
                    if(vbox.getHeight()>=t1.getHeight()){
                        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                    }else{
                        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    }
                }
            });
        }

        titletext.textProperty().bind(title);
        texttext.textProperty().bind(text);
        texttext.setTextAlignment(TextAlignment.CENTER);

        vbox.getChildren().add(titletext);
        vbox.getChildren().add(texttext);

        if(imagePath!=null) {

            vbox.getChildren().add(image);
        }

        scrollPane.setContent(vbox);

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        vbox.setSpacing(20);

        return scrollPane;
    }

}