package com.example.iegui;


import com.example.iegui.controller.FinishedViewController;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;

import com.sun.jna.Native;

import com.sun.jna.Library;

public class MainApplication extends Application {


    public static void main(String[] args) {

        launch();
    }

    /**
     * Sets working directory under windows
     */
    private static interface MyKernel32 extends Library {
        public MyKernel32 INSTANCE = (MyKernel32) Native.loadLibrary("Kernel32", MyKernel32.class);

        /** BOOL SetCurrentDirectory( LPCTSTR lpPathName ); */
        int SetCurrentDirectoryW(String pathName);
    }

    /**
     * Sets workingrectory under linux
     */
    private interface MyCLibrary extends Library {
        MyCLibrary INSTANCE = (MyCLibrary) Native.loadLibrary("c", MyCLibrary.class);

        /** int chdir(const char *path); */
        int chdir( String path );
    }


    @Override
    public void start(Stage stage) throws IOException {
        if(!new File("EnhanceMethod").exists()) {
            String workingDir = "";
            try {

                workingDir = new File(MainApplication.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
            } catch (URISyntaxException e) {
                System.out.println(e.getMessage());
            }

            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                MyKernel32 library = MyKernel32.INSTANCE;
                library.SetCurrentDirectoryW(workingDir);
            } else {
                MyCLibrary library = MyCLibrary.INSTANCE;
                System.out.println(library.chdir(workingDir));
            }
            System.setProperty("user.dir", workingDir);
            System.setProperty("user.home", workingDir);
        }

        Context context = new Context(stage, "Settings/settings.yml");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 768, 500);
        stage.setMinHeight(480);
        stage.setMinWidth(640);

        //this.context=context;
        Controller controller = fxmlLoader.getController();
        controller.setContext(context);

        // context.getMethods().get(0).start("/home/martin/Downloads/output12.png", "/home/martin/Downloads/output13.png"); // For Test purposes SwinIR
        // context.getMethods().get(1).start("/home/martin/Downloads/bild6.jpg", "/home/martin/Downloads/output.png"); // For Test purposes Low light
        // context.getMethods().get(2).start("/home/martin/Downloads/output14.png","/home/martin/Downloads/output15.png"); // For Testing purposes white balance
        // context.getMethods().get(3).start("/home/martin/Downloads/IMG-20220601-WA0000.jpg","/home/martin/Downloads/output13.png"); // For Testing purposes white balance
        context.getMethods().get(3).start("EnhanceMethod/LLFlow/code/confs/bild6.jpg","~/Downloads/llflow_smallNet_output.png"); // For Testing purposes white balance


        stage.setTitle("IEGUI");
        stage.setScene(scene);
        stage.show();
       // showFinishedView("/home/martin/Dokumente/Unbenannt.png","/home/martin/Dokumente/Unbenannt.png");


        if(context.isOpenWelcomeView()){
            Stage stage2 = new Stage();
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("welcome-view.fxml"));
            Parent root2 = loader2.load();
            Scene scene2 = new Scene(root2, 768, 700);
            stage2.setMinHeight(480);
            stage2.setMinWidth(640);

            stage2.setScene(scene2);
            stage2.titleProperty().bind(context.getTextName("welcome"));
            Controller controller2 =  loader2.getController();
            controller2.setContext(context);
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.show();
        }
    }
  // Context context ;


    /**
     * Displays the finished view
     * @param input path to input image
     * @param output  path to output image
     */
    /*private void showFinishedView(String input, String output){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Stage stage = new Stage();

                    URL fxmlLocation = getClass().getResource("/com/example/iegui/finished-view.fxml");
                    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

                    Parent root = null;
                    root = fxmlLoader.load();
                    Scene scene = new Scene(root, 500, 300);

                    FinishedViewController controller = fxmlLoader.getController();
                    controller.setContext(context);
                   // controller.setImages(input, output);

                    stage.setTitle("Done");
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    Alerts.Error(e.getMessage());
                }
            }
        });
    }*/
}