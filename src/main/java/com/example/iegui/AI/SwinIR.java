package com.example.iegui.AI;

import com.example.iegui.CustomNodes.MethodSettingWindow;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SwinIR extends ImageEnhanceMethod{
      private String task="super-resolution";
      private int denoisingLevel;
      private int jpegArtifactRemovalLevel;
      private int scaleFactor=4;


    @Override
    public MethodSettingWindow getSettingWindow() {
        return super.getSettingWindow();
    }

    @Override
       public String[] getCMD(){
          String environment =  new File(getLocation()+"/"+"environment").getAbsolutePath();
            switch(task) {
                case "super-resolution":
                    return new String[]{
                            environment + "/bin/python3",
                            "main_test_swinir.py",
                            "--task",
                            "real_sr",
                            "--scale",
                            String.valueOf(scaleFactor),
                            "--large_model",
                            "--model_path",
                            "model_zoo/swinir/003_realSR_BSRGAN_DFOWMFC_s64w8_SwinIR-L_x4_GAN.pth",
                            "--input",
                            context.getTempdir()+"/input",
                            "--output",
                            context.getTempdir()+"/output",
                    };
                default:
                    return null;
            }
      }

    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     *
     * @param location The model location
     * @param lang     The language which should be loaded
     */
    public SwinIR(String location, String lang, Context context) {
        super(location, lang,context);
    }

}
