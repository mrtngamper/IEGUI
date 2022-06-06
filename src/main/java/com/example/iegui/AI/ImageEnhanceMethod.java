package com.example.iegui.AI;

import com.example.iegui.CustomNodes.ImageComparisonPain;
import com.example.iegui.CustomNodes.MethodSettingWindow;
import com.example.iegui.Exceptions.DynamicMessageException;
import com.example.iegui.Exceptions.YAMLTypeNotValidException;
import com.example.iegui.controller.LoadingViewController;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import org.yaml.snakeyaml.Yaml;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * An ImageEnhanceMethod Object contains Info about a Method (e.g. SwinIR).
 */
public abstract class ImageEnhanceMethod {
    /**
     * Example images. Keys are the input file paths, Values the output file paths
     */
    private final HashMap<String, String> examples = new HashMap<>();
    /**
     * Example miniatures for illustriation. Keys are the inputs, Values the outputs
     */
    private final HashMap<String, String> miniatures = new HashMap<>();
    /**
     * The factor of how much the input image will be downscaled before processing
     */
    private final SimpleDoubleProperty downscaleFactor = new SimpleDoubleProperty(1);

    protected Context context;

    /**
     * The pointe to the name of the method in the language file
     */
    private String name = "";
    /**
     * A pointer to a short description of the method in the language file
     */
    private String description = "";

    public String getLong_description() {
        return long_description;
    }

    /**
     * A pointer to a longer description of the method in the language file
     */
    private String long_description ="";


    /**
     * Path of the enhancement method
     */
    private String location;
    /**
     * A Node which allows to set some parameters of enhancement methods
     */
    private MethodSettingWindow settingWindow;
    /**
     * The Name of the python environment. The code will look for a [environment].txt file in the Environments folder.
     * This file then contains the libraries which will be loaded into an environment
     */
    private String environment;

    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     *
     * @param location The method location
     * @param lang     The language which should be loaded
     */
    public ImageEnhanceMethod(String location, String lang, Context context) {
        this.context = context;
        this.location = location;
        try {
            loadYAML(location + "/" + "Config" + "/" + "en" + ".yml");
        } catch (Exception e) {
            Alerts.Warning(e.getMessage());
        }
    }


    public HashMap<String, String> getExamples() {
        return examples;
    }

    public HashMap<String, String> getMiniatures() {
        return miniatures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnvironment() {
        return environment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DoubleProperty getDownscaleFactor() {
        return downscaleFactor;
    }


    public MethodSettingWindow getSettingWindow() {
        return settingWindow;
    }

    public void setSettingWindow(MethodSettingWindow settingWindow) {
        this.settingWindow = settingWindow;
    }

    private Thread workingThread;

    /**
     * Start Method gets executed, when an enhancement should be performed. It is given an input string and an output
     * string, which should correspond to the filenames. During the operation a loading screen is being displayed
     *
     * @param inputfile  Input image path. (File should exist)
     * @param outputfile Output image path.
     */
    public void start(String inputfile, String outputfile) {

        workingThread=new Thread(new Runnable() {
            @Override
            public void run() {
                LoadingView loadingView = new LoadingView();
                loadingView.show();
                try {
                    createEnvironment();
                }catch(Exception e){
                    Alerts.Error(e.getMessage());
                    return;
                }
                try {
                    String[] cmd = getCMD();
                    boolean error = false;
                    ProcessBuilder pb = new ProcessBuilder(cmd);
                    pb.redirectErrorStream(true);
                    pb.directory(new File(getLocation()+"/"));
                    System.out.println(getLocation());


                    Path tempInput = Path.of(context.getTempdir() + "/input/input.png");
                    Path tempOutput = Path.of(context.getTempdir() + "/output/input.png");

                    BufferedImage before = ImageIO.read(new File(inputfile));
                    int w = before.getWidth();
                    int h = before.getHeight();
                    BufferedImage after = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                    AffineTransform at = new AffineTransform();
                    at.scale(downscaleFactor.get(), downscaleFactor.get());
                    AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                    after = scaleOp.filter(before, after);

                    BufferedImage image = new BufferedImage((int) (w * downscaleFactor.get()), (int) (h * downscaleFactor.get()), BufferedImage.TYPE_INT_ARGB);
                    image.setData(after.getData());

                    ImageIO.write(image, "png", tempInput.toFile());


                    for (int i = 0; i < 2; i++) {
                        Process process = pb.start();
                        printProcessOutput(process);
                        process.waitFor();
                        switch (process.exitValue()) {
                            case 0:
                                if (Path.of(outputfile).toFile().exists()) {
                                    Files.delete(Path.of(outputfile));
                                }

                                for (File file: new File(context.getTempdir().toFile().getAbsolutePath()+"/output").listFiles()) {
                                    String extension = file.getName().replaceAll(".*\\.","");
                                    if(extension.equals("png")){
                                        Files.copy(Path.of(file.getAbsolutePath()), Path.of(outputfile));
                                        break;
                                    }
                                }
                                cleanUp();

                                System.out.println(context.getTextName("success").getValue());
                                loadingView.close();

                                showFinishedView(inputfile,outputfile);
                                return;
                            case 132:
                                System.out.println(context.getTextName("nodependencies").getValue());
                                installDependencies();
                                break;
                            default:
                                error=true;
                                break;
                        }
                        if(error) {
                            break;
                        }
                    }
                    Alerts.Error(context.getTextName("unexpectederror").getValue());
                } catch (Exception e) {
                    Alerts.Error(e.getMessage());
                }
                loadingView.close();
            }
        });
        workingThread.start();
    }

    private void cleanUp(){
        recursiveDelete(new File(context.getTempdir().toFile().getAbsolutePath()+"/input"));
        recursiveDelete(new File(context.getTempdir().toFile().getAbsolutePath()+"/output"));
    }


    /**
     * Deletes all elements from directory recursively
     * @param file Root directory
     */
    private void recursiveDelete(File file){
        if(file.isDirectory()){
            for (File f:file.listFiles() ) {
                if(f.isDirectory()){
                    recursiveDelete(f);
                    f.delete();
                }else{
                    f.delete();
                }
            }
        }
    }

    /**
     * A function which returns the command which should be executed in the start function depending on method specific settings.
     *
     * @return A string array containing the command parameters.
     */
    public abstract String[] getCMD() throws DynamicMessageException;



    /**
     * Loads ImageEnhanceMethod options from file
     *
     * @param file filename of the yml file which contains the data
     * @throws YAMLTypeNotValidException thrown, when yaml object type is wrong
     * @throws FileNotFoundException     thrown, when file does not exist
     */
    public void loadYAML(String file) throws YAMLTypeNotValidException, FileNotFoundException {

        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(file);
        Map<String, Object> map = yaml.load(inputStream);

        Object ex_input = map.get("examples");
        if (ex_input != null) {
            if (ex_input instanceof HashMap) {
                examples.putAll((HashMap) ex_input);
            } else {
                throw new YAMLTypeNotValidException(HashMap.class.toString(), ex_input.getClass().toString(), "miniatures", file);
            }
        }

        Object min_in = map.get("miniatures");
        if (min_in != null) {
            if (min_in instanceof HashMap) {
                miniatures.putAll((HashMap) min_in);
            } else {
                throw new YAMLTypeNotValidException(HashMap.class.toString(), min_in.getClass().toString(), "miniatures", file);
            }
        }

        Object name_yml = map.get("name");
        if (name_yml != null) {
            if (name_yml instanceof String) {
                name = name_yml.toString();
            } else {
                throw new YAMLTypeNotValidException(String.class.toString(), name_yml.getClass().toString(), "name", file);
            }
        }

        Object description_yml = map.get("description");
        if (description_yml != null) {
            if (description_yml instanceof String) {
                description = description_yml.toString();
            } else {
                throw new YAMLTypeNotValidException(String.class.toString(), description_yml.getClass().toString(), "description", file);
            }
        }

        Object environment = map.get("environment");
        if (environment != null) {
            if (environment instanceof String) {
                this.environment = environment.toString();
            } else {
                throw new YAMLTypeNotValidException(String.class.toString(), environment.getClass().toString(), "environment", file);
            }
        }

        Object long_descriptionyml = map.get("description_long");
        if(long_descriptionyml!=null){
            if(long_descriptionyml instanceof  String){
                this.long_description= (String)long_descriptionyml;
            }else{
                throw new YAMLTypeNotValidException(String.class.toString(), long_descriptionyml.getClass().toString(), "description_long", file);
            }
        }
        Object link = map.get("link");
        if(link!=null){
            if(link instanceof  String){
                this.hyperlink= (String)link;
            }else{
                throw new YAMLTypeNotValidException(String.class.toString(), link.getClass().toString(), "link", file);
            }
        }
    }

    public String getHyperlink() {
        return hyperlink;
    }

    /**
     * Hyperlink to the methods github repository
     */
    private String hyperlink;


    /**
     * This method prints the output of a process to the standard output
     *
     * @param process A process containing its inputstream
     */
    private void printProcessOutput(Process process) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder builder = new StringBuilder();
                String line = null;
                try {
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    String result = builder.toString();
                } catch (Exception ignore) {
                }
            }
        }).start();
    }

    /**
     * Tries to install all the dependencies found in the requirements.txt
     *
     * @throws Exception
     */
    public void installDependencies() throws Exception {
        System.out.println(context.getTextName("installingdependencies").getValue());
        File file = new File("Environments"+"/"+environment);
        if(file.exists()) {
            String[] cmd = {
                    Context.independent(getEnvDir()+"/pip3"),
                    "install",
                    "-r",
                    Context.independent("Environments"+"/"+environment+".txt")
            };
            ProcessBuilder pb = new ProcessBuilder(cmd);

            Process process = pb.start();
            printProcessOutput(process);

            process.waitFor();
            switch (process.exitValue()) {
                case 132:
                    System.out.println(context.getTextName("nodependencies").getValue());
                    break;
                default:

            }

            System.out.println(context.getTextName("depInstalled").getValue());
            return;
        }
        System.out.println(context.getTextName("envDoesNotExit").getValue());
    }

    /**
     * Creates a python environment in the Environments directory and installs the dependencies which can also be found there
     *
     * @throws Exception An Exception may be thrown when a environment file (specified in the environment attribute) does not exist, or python encounters an error (no internet connection, package unexistent etc.)
     */
    public void createEnvironment() throws Exception {
        File file = new File("Environments" + "/" + environment);
        if (!file.exists()) {
            String[] cmd = {"python", "-m", "venv", file.getAbsolutePath()};
            Process process = Runtime.getRuntime().exec(cmd);
            printProcessOutput(process);
            process.waitFor();

            System.out.println(context.getTextName("envCreated").getValue());
            installDependencies();
            return;
        }
        System.out.println(context.getTextName("depInstalled").getValue());
    }


    public String getEnvDir(){
        String environment =  new File("Environments"+"/"+getEnvironment()).getAbsolutePath();
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.US);
        if (os.contains("windows")) {
            return Context.independent(environment + "/Scripts/");
        }
        return   Context.independent(environment + "/bin/");
    }


    /**
     * Loading View which is displayed while an image is being processed.
     */
    private class LoadingView {
        private Stage stage;

        /**
         * This method loads and displayes the loading view.
         *
         * @param context The context is given to the new Controller
         * @return The stage containing the window is returned
         */
        private Stage showLoadingView(Context context) {
            try {

                Stage stage = new Stage();

                URL fxmlLocation = getClass().getResource("/com/example/iegui/loading-view.fxml");
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root, 500, 300);

                LoadingViewController controller = fxmlLoader.getController();
                controller.setContext(context);
                stage.setTitle("Loading ...");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setHeaderText(context.getTextName("abort").getValue()+"?");
                                alert.setTitle("WARNING");
                                Optional<ButtonType> ret = alert.showAndWait();
                                if(ButtonType.OK.equals(ret.get())){
                                    workingThread.stop();
                                    cleanUp();
                                    recursiveDelete( new File("Environments"+"/"+getEnvironment()));
                                    try {
                                        Files.delete(new File("Environments"+"/"+getEnvironment()).toPath());
                                    } catch (IOException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    stage.close();
                                }
                            }
                        });
                        windowEvent.consume();
                    }
                });


                stage.resizableProperty().setValue(Boolean.FALSE);
                stage.initStyle(StageStyle.DECORATED);
                stage.show();
                stage.getIcons().add(context.getIcon());
                return stage;
            } catch (Exception e) {
                Alerts.Error(e.getMessage());
            }
            return null;
        }

        public Stage getStage() {
            return stage;
        }

        /**
         * Displayes the view
         */
        public void show() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    stage = showLoadingView(context);
                    if (stage == null || !stage.isShowing()) {
                        stage = null;
                    }
                }
            });
        }



        /**
         * Closes the window if it is opened
         */
        public void close() {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if (stage != null) {
                        stage.close();
                        context.getOutputStream().clearStreams();
                    }
                }
            });

        }
    }



    /**
     * Displays the finished view
     * @param input path to input image
     * @param output  path to output image
     */
    private void showFinishedView(String input, String output){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Stage stage = new Stage();
                BorderPane pane = new BorderPane();

                Scene scene = new Scene(pane, 500, 300);
                scene.getStylesheets().add(getClass().getResource("/com/example/iegui/views.css").toExternalForm());;

                stage.setMinWidth(320);
                stage.setMinHeight(240);

                HBox topBox = new HBox();
                Text result = new Text();
                topBox.setAlignment(Pos.CENTER);
                topBox.getChildren().add(result);
                result.setStyle("-fx-font-family: Arial; -fx-font-size: 30; -fx-font-weight: bold");
                result.textProperty().bind(context.getTextName("result"));

                ImageComparisonPain pain = new ImageComparisonPain(input,output);
                HBox box = new HBox();
                box.getChildren().add(pain);
                box.setAlignment(Pos.CENTER);
                box.setPadding(new Insets(10,10,10,10));
                HBox.setHgrow(pain, Priority.ALWAYS);
                VBox.setVgrow(pain,Priority.ALWAYS);

                pane.setCenter(box);
                HBox bottomBox = new HBox();
                bottomBox.setAlignment(Pos.CENTER);
                Button ok = new Button();
                ok.textProperty().bind(context.getTextName("ok"));
                bottomBox.getChildren().add(ok);


                ok.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        stage.close();
                    }
                });

                pane.setBottom(bottomBox);
                pane.setTop(topBox);

                stage.setTitle("Done");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.show();
                stage.getIcons().add(context.getIcon());
            }
        });
    }
}
