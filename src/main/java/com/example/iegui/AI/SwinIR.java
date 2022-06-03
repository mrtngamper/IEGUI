package com.example.iegui.AI;

import com.example.iegui.CustomNodes.MethodSettingWindow;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import com.example.iegui.util.paths;
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
      private int denoisingLevel=25;
      private int scaleFactor=4;


    @Override
    public MethodSettingWindow getSettingWindow() {

        return super.getSettingWindow();
    }

    @Override
       public String[] getCMD(){
            switch(task) {
                case "super-resolution":
                    return new String[]{
                            paths.independent(getEnvDir()+"/"+"python"),
                            "main_test_swinir.py",
                            "--task",
                            "real_sr",
                            "--scale",
                            String.valueOf(scaleFactor),
                            "--large_model",
                            "--model_path",
                            paths.independent(getLocation()+"/model_zoo/swinir/003_realSR_BSRGAN_DFOWMFC_s64w8_SwinIR-L_x4_GAN.pth"),
                            "--input",
                            paths.independent(context.getTempdir()+"/input"),
                            "--output",
                            paths.independent(context.getTempdir()+"/output"),
                    };
                case "denoising":
                    return new String[]{
                            paths.independent(getEnvDir()+"/python3"),
                            "main_test_swinir.py",
                            "--task",
                            "color_dn",
                            "--noise",
                            String.valueOf(denoisingLevel),
                            "--model_path",
                            paths.independent(getLocation()+"/model_zoo/swinir/005_colorDN_DFWB_s128w8_SwinIR-M_noise"+String.valueOf(denoisingLevel)+".pth"),
                            "--input",
                            paths.independent(context.getTempdir()+"/input"),
                            "--output",
                            paths.independent(context.getTempdir()+"/output"),
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
