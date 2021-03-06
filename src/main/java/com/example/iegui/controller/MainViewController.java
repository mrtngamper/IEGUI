package com.example.iegui.controller;
import com.example.iegui.AI.ImageEnhanceMethod;
import com.example.iegui.CustomNodes.DragAndDrop;
import com.example.iegui.CustomNodes.EnhanceMethodListView;
import com.example.iegui.CustomNodes.ImageLoaded;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    private ListView<ImageEnhanceMethod> list;

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


        try {
            URL url = getClass().getResource("MainViewController.class");
            String scheme = url.getProtocol();
            System.out.println(scheme);
            switch(scheme) {
                case "jar":
                    JarURLConnection con = (JarURLConnection) url.openConnection();
                    JarFile archive = con.getJarFile();
                    Enumeration<JarEntry> entries = archive.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        if (entry.getName().startsWith("language")) {
                            try {
                                MenuItem item = new MenuItem();
                                String[] split = entry.getName().split("language/");
                                item.setText(split[1].split("\\..*")[0]);
                                item.setOnAction(this::onLanguagePressed);
                                languageSetting.getItems().add(item);
                            }catch(Exception ignore){}
                        }
                    }
                    break;
                case "file":
                    String file1 = url.getPath();
                    File resourceFile = new File(file1);
                    File rootDir = resourceFile.getParentFile().getParentFile().getParentFile().getParentFile().getParentFile();
                    File languageDir = new File(rootDir+"/"+"language/");
                    for (File i : languageDir.listFiles()) {
                        MenuItem item = new MenuItem();
                        item.setText(i.getName().split("\\..*")[0]);
                        item.setOnAction(this::onLanguagePressed);
                        languageSetting.getItems().add(item);
                    }
                    break;
            }

        }catch(Exception e){
            Alerts.Error(e.getMessage());
        }

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
        bP.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            File f = db.getFiles().get(0);
            imageFile.setValue(f.getAbsolutePath());
        });

        imageFile.addListener((observableValue, s, t1) -> {
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
        });

        bP.setOnDragOver(event -> {
            if (event.getGestureSource() != browse && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            }
            event.consume();
        });

        final String[] oldStyle = {""};
        bP.setOnDragEntered(dragEvent -> {
            oldStyle[0] =bP.getStyle();
            bP.setStyle("-fx-background-color: #d4ffd1 ");
        });
        bP.setOnDragExited(dragEvent -> bP.setStyle(oldStyle[0]));


        list =  new EnhanceMethodListView(context, subBorderPane, imageFile);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onBrowseButton(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(context.getTextName("selectFile").getValue());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(context.getTextName("imageFile").getValue(), "*.png", "*.jpg", "*.jpeg", "*.gif", "*.jps", "*.mpo"));
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
        String language = mi.getText();
        context.setLang(language);
    }

    public void onCloseButton(ActionEvent actionEvent) {
        imageFile.setValue("");
    }

    public void about(ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/iegui/about.fxml"));


            Parent root = loader.load();
            Scene scene =new Scene(root, 500, 300);
            Controller controller = loader.getController();
            controller.setContext(context);

            stage.setScene(scene);
            stage.setResizable(false);

            stage.titleProperty().bind(context.getTextName("ieguiTeam"));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(context.getStage());
            stage.show();
            stage.getIcons().add(context.getIcon());
        } catch (IOException e) {
            Alerts.Error(e.getMessage());
        }

    }

}