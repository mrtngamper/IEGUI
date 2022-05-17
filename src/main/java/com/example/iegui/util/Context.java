package com.example.iegui.util;

import com.example.iegui.AI.ImageEnhanceMethod;
import com.example.iegui.AI.SwinIR;
import com.example.iegui.Exceptions.TextNotFoundException;
import javafx.beans.property.SimpleStringProperty;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
    private ArrayList<ImageEnhanceMethod> methods = new ArrayList<>();

    /**
     * Current language
     */
    private SimpleStringProperty lang = new SimpleStringProperty("en");

    private SimpleStringProperty selectedFile = new SimpleStringProperty();

    public  Context(Stage stage){
        this.stage=stage;



        try {
            language.setContext(this);
            language.load("Language/"+lang.getValue()+".yml");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        methods.add(new SwinIR("EnhanceMethod/SwinIR",lang.getValue(),this));
    }


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

    public ArrayList<ImageEnhanceMethod> getMethods() {
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
    }
}
