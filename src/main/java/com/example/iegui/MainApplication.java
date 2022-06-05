package com.example.iegui;


import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import com.sun.jna.Library;
import com.sun.jna.Native;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class MainApplication extends Application {


    public static HostServices hostServices;

    public static void main(String[] args) {

        launch();
    }

    public Scene startAnimation(Scene mainScene, Stage rootStage) throws FileNotFoundException {
        Pane pane = new Pane();
        InputStream inputStream = getClass().getResourceAsStream("/images/woldas.png");
        assert inputStream != null;
        Image logo = new Image(inputStream);
        ImageView imageView = new ImageView(logo);
        imageView.setFitHeight(0.1);
        imageView.setFitWidth(0.1);
        imageView.setX(375);
        imageView.setY(235);
        pane.getChildren().add(imageView);

        ScaleTransition scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(Duration.millis(1500));
        scaleTransition.setNode(imageView);
        scaleTransition.setByX(2500);
        scaleTransition.setByY(2500);

        Scene scene = new Scene(pane, 768, 500);

        scaleTransition.setCycleCount(1);
        scaleTransition.setAutoReverse(false);
        scaleTransition.play();

        scaleTransition.setOnFinished(event -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rootStage.setScene(mainScene);
        });

        return scene;
    }

    @Override
    public void start(Stage stage) throws IOException {
        MainApplication.hostServices = getHostServices();
        if (!new File("EnhanceMethod").exists()) {
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
        Controller controller = fxmlLoader.getController();
        controller.setContext(context);

        // context.getMethods().get(0).start("/home/martin/Downloads/output12.png", "/home/martin/Downloads/output13.png"); // For Test purposes SwinIR
        // context.getMethods().get(1).start("/home/martin/Downloads/bild6.jpg", "/home/martin/Downloads/output.png"); // For Test purposes Low light
        // context.getMethods().get(2).start("/home/martin/Downloads/output14.png","/home/martin/Downloads/output15.png"); // For Testing purposes white balance
        // context.getMethods().get(3).start("/home/martin/Downloads/IMG-20220601-WA0000.jpg","/home/martin/Downloads/output13.png"); // For Testing purposes white balance
        // context.getMethods().get(5).start("/home/kilian/Nextcloud/Schule/Informatik/IEGUI/EnhanceMethod/GPEN/Config/input.png","~/Downloads/friends.png"); // For Testing purposes white balance


        stage.setTitle("IEGUI");
        stage.setScene(startAnimation(scene, stage));
        stage.show();
        InputStream inputStream = getClass().getResourceAsStream("/images/woldas.png");
        assert inputStream != null;
        Image icon = new Image(inputStream);
        context.setIcon(icon);
        stage.getIcons().add(icon);

        if (context.isOpenWelcomeView()) {
            Stage stage2 = new Stage();
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("welcome-view.fxml"));
            Parent root2 = loader2.load();
            Scene scene2 = new Scene(root2, 768, 700);
            stage2.setMinHeight(480);
            stage2.setMinWidth(640);

            stage2.setScene(startAnimation(scene2, stage2));
            stage2.titleProperty().bind(context.getTextName("welcome"));
            Controller controller2 = loader2.getController();
            controller2.setContext(context);
            stage2.initModality(Modality.APPLICATION_MODAL);
            stage2.initOwner(stage);
            stage2.show();
            stage2.getIcons().add(icon);
        }
    }


    /**
     * Sets working directory under windows
     */
    private static interface MyKernel32 extends Library {
        public MyKernel32 INSTANCE = (MyKernel32) Native.loadLibrary("Kernel32", MyKernel32.class);

        /**
         * BOOL SetCurrentDirectory( LPCTSTR lpPathName );
         */
        int SetCurrentDirectoryW(String pathName);
    }

    /**
     * Sets workingrectory under linux
     */
    private interface MyCLibrary extends Library {
        MyCLibrary INSTANCE = (MyCLibrary) Native.loadLibrary("c", MyCLibrary.class);

        /**
         * int chdir(const char *path);
         */
        int chdir(String path);
    }

}