package com.example.iegui.util;

import com.example.iegui.AI.*;
import com.example.iegui.Exceptions.TextNotFoundException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

/**
 * Context which is given to every Controller. It contains shared data between elements of the UI.
 */
public class Context {
    /**
     * The stage of the main window
     */
    private Stage stage;

    /**
     * Temporary directory for input and output images
     */
    private Path tempdir;

    /**
     * Language Object which contains all the language Data
     */
    private Language language = new Language();

    /**
     * List containing instances of image enhance methods
     */
    private ObservableList<ImageEnhanceMethod> methods = FXCollections.observableArrayList();

    /**
     * If WelcomeView should be shown again
     */
    private SimpleBooleanProperty openWelcomeView= new SimpleBooleanProperty(true);

    /**
     * Current language
     */
    private SimpleStringProperty lang = new SimpleStringProperty("en");

    /**
     * Filename of the settings file. Can be specified upon creation of Object
     */
    private String settings_file_name;

    private SimpleStringProperty selectedFile = new SimpleStringProperty();

    private Image icon;

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    /**
     * New Output Stream for System.out to enable printing to TextArea
     */
    private ExtendedOutputStream outputStream = new ExtendedOutputStream();

    public ExtendedOutputStream getOutputStream() {
        return outputStream;
    }

    public Path getTempdir() {
        return tempdir;
    }

    public  Context(Stage stage, String settings){
        Context context = this;

        openWelcomeViewProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                context.storeSettings();
            }
        });

        this.stage=stage;
        this.settings_file_name=settings;
        loadSettings();

        System.setOut(new PrintStream(outputStream));

        try {
            tempdir=Files.createTempDirectory("IEGUI");
            new File(tempdir.toFile().getAbsolutePath()+"/input").mkdirs();
            new File(tempdir.toFile().getAbsolutePath()+"/output").mkdirs();
        } catch (IOException e) {
            Alerts.Error(e.getMessage());
            return;
        }

        try {
            language.setContext(this);
            language.load("/language/"+lang.getValue()+".yml");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        methods.add(new SwinIR("EnhanceMethod/SwinIR",lang.getValue(),this));
        methods.add(new LowLightImageEnhance("EnhanceMethod/Low-light-Image-Enhancement",lang.getValue(),this));
        methods.add(new WhiteBalance("EnhanceMethod/mixedillWB2",lang.getValue(),this));
        methods.add(new LLFlow("EnhanceMethod/LLFlow/code",lang.getValue(),this));
        methods.add(new NAFNet("EnhanceMethod/NAFNet",lang.getValue(),this));
        methods.add(new GPEN("EnhanceMethod/GPEN",lang.getValue(),this));
    }


    /**
     * Returns the String of the specified hashmap key to import different languages
     * @param key hashmap for the language
     * @return the text of the hashmap
     */
    public SimpleStringProperty getTextName(String key) {
        try {
            return language.get(key);
        } catch (TextNotFoundException e) {
            Alerts.Warning(e.getMessage());
        }
        return new SimpleStringProperty("");
    }
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ObservableList<ImageEnhanceMethod> getMethods() {
        return methods;
    }

    public String getLang() {
        return lang.get();
    }

    public SimpleStringProperty langProperty() {
        return lang;
    }

    public void setLang(String lang) {
        try {
            this.lang.set(lang);
            language.load("/language/"+lang+".yml");
            storeSettings();
        } catch (FileNotFoundException e) {
            Alerts.Error(e.getMessage());
        }
    }

    /**
     * Loads settings contained in a context.
     */
    public void loadSettings(){
        File file = new File(settings_file_name);
        if(!file.exists()){
            storeSettings();
        }

        Yaml yaml = new Yaml();
        try {
            InputStream inputStream = new FileInputStream(settings_file_name);
            Map<String, Object> map = yaml.load(inputStream);

            // Add properties if necessary
            if(map.containsKey("language")){
                lang.setValue((String)map.get("language"));
            }

            if(map.containsKey("OpenWelcomeView")){
                if( map.containsKey("OpenWelcomeView")) {
                    openWelcomeView.setValue((Boolean) map.get("OpenWelcomeView"));
                } else {
                    System.out.println("Could not find OpenWelcomeView key");
                }
            }
        }catch(Exception e){
            Alerts.Error(e.getMessage());
        }
    }


    /**
     * Stores settings contained in a context.
     */
    public void storeSettings(){
        try {
            StringBuffer buffer = new StringBuffer();

            //Add properties if necessary
            buffer.append("Language: "+lang.getValue()+"\n");
            buffer.append("OpenWelcomeView: " + openWelcomeView.getValue().toString().toLowerCase() + '\n');

            Files.writeString(Path.of(settings_file_name),buffer.toString());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean isOpenWelcomeView() {
        return openWelcomeView.get();
    }

    public SimpleBooleanProperty openWelcomeViewProperty() {
        return openWelcomeView;
    }


    /**
     * Loads Image into ImageView
     * @param path Path to Image
     * @return returns ImageView
     * @throws FileNotFoundException
     */
    public static  ImageView loadImage(String path) throws FileNotFoundException {
        FileInputStream input = new FileInputStream(path);
        Image image = new Image(input);
        ImageView view = new ImageView(image);
        view.setPreserveRatio(true);
        return view;
    }


    /**
     * Creates path String from String which is platform independent
     * @param path path String
     * @return platform independent  path String
     */
    public static String independent(String path){
        return new File(path).getAbsolutePath();
    }
}
