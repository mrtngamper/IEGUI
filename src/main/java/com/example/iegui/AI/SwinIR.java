package com.example.iegui.AI;

import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class SwinIR extends ImageEnhanceMethod{


    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     *
     * @param location The model location
     * @param lang     The language which should be loaded
     */
    public SwinIR(String location, String lang, Context context) {
        super(location, lang);


    }

    @Override
    public void start(String inputfile, String outputfile) {

    }
}
