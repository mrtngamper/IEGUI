package com.example.iegui.AI;

import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SwinIR extends ImageEnhanceMethod{
      private String task="";
      private int denoisingLevel;
      private int jpegArtifactRemovalLevel;
      private int scaleFactor;




    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     *
     * @param location The model location
     * @param lang     The language which should be loaded
     */
    public SwinIR(String location, String lang, Context context) {
        super(location, lang);

       // methods.add("Super Resolution")
    }

    @Override
    public void start(String inputfile, String outputfile) {
        File file = new File(getLocation()+"/"+"environment");
        try{
            createEnvironment();
        }catch(Exception e){
            Alerts.Error(e.getMessage());
            return;
        }
        try {
            String[] cmd = {
                    file.getAbsolutePath() + "/bin/python3",
                   "main_test_swinir.py"
            };
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            pb.redirectInput(ProcessBuilder.Redirect.INHERIT);

            pb.directory(new File(getLocation()));

            Process process = pb.start();
            process.waitFor();
            switch(process.exitValue()){
                case 132:
                    System.out.println("Dependencies not found");
                    break;
                default:
                    Alerts.Error("Unerwarteter Fehler");
            }
        }catch(Exception e){
            Alerts.Error(e.getMessage());
        }
    }


}
