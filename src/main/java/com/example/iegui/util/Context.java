package com.example.iegui.util;

import com.example.iegui.AI.ImageEnhanceMethod;
import com.example.iegui.AI.SwinIR;
import com.example.iegui.Exceptions.TextNotFoundException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

/**
 * Context which is given to every Controller. It contains shared data between elements of the UI.
 */
public class Context {
    /**
     * The stage of the main window
     */
    private Stage stage;
    private Language language = new Language();

    /**
     * List containing instances of image enhance methods
     */
    private ObservableList<ImageEnhanceMethod> methods = FXCollections.observableArrayList();

    /**
     * If WelcomeView should be shown again
     */
    private SimpleBooleanProperty openWelcomeView= new SimpleBooleanProperty();

    /**
     * Current language
     */
    private SimpleStringProperty lang = new SimpleStringProperty("en");

    /**
     * Filename of the settings file. Can be specified upon creation of Object
     */
    private String settings_file_name;

    private SimpleStringProperty selectedFile = new SimpleStringProperty();

    public  Context(Stage stage,String settings){
        this.stage=stage;
        this.settings_file_name=settings;
        loadSettings();


        try {
            language.setContext(this);
            language.load("Language/"+lang.getValue()+".yml");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        methods.add(new SwinIR("EnhanceMethod/SwinIR",lang.getValue(),this));
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
        this.lang.set(lang);
        storeSettings();
    }

    /**
     * Loads settings contained in a context.
     */
    private void loadSettings(){
        Yaml yaml = new Yaml();
        try {
            InputStream inputStream = new FileInputStream(settings_file_name);
            Map<String, Object> map = yaml.load(inputStream);

            // Add properties if necessary
            if(map.containsKey("Language")){
                lang.setValue((String)map.get("Language"));
            }

            if(map.containsKey("OpenWelcomeView")){
                if((Boolean) map.get("OpenWelcomeView")) {
                    openWelcomeView.setValue((Boolean) map.get("OpenWelcomeView"));
                } else {
                    System.out.println("Could not find OpenWelcomeView key");
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
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
}
