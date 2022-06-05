package com.example.iegui.AI;

import com.example.iegui.CustomNodes.CustomChoiceBox;
import com.example.iegui.CustomNodes.MethodSettingWindow;
import com.example.iegui.Exceptions.DynamicMessageException;
import com.example.iegui.util.Context;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.Collections;
import java.util.HashMap;

public class SwinIR extends ImageEnhanceMethod{
      private SimpleStringProperty task=new SimpleStringProperty("");
      private int denoisingLevel=50;

      private SimpleStringProperty denoisingLevelProperty = new SimpleStringProperty("15");


    @Override
    public MethodSettingWindow getSettingWindow() {


        HashMap<SimpleStringProperty, String> denoisingSelections = new HashMap<>();
        denoisingSelections.putAll(Collections.singletonMap(new SimpleStringProperty(String.valueOf(50)),"50"));
        denoisingSelections.putAll(Collections.singletonMap(new SimpleStringProperty(String.valueOf(25)),"25"));
        denoisingSelections.putAll(Collections.singletonMap(new SimpleStringProperty(String.valueOf(15)),"15"));
        CustomChoiceBox denoisingSelector = new CustomChoiceBox(denoisingSelections,denoisingLevelProperty);
        denoisingLevelProperty.addListener((observableValue, s, t1) -> denoisingLevel=Integer.parseInt(t1));

        denoisingLevelProperty.setValue("15");

        MethodSettingWindow msw = new MethodSettingWindow();
        HBox settingBox = new HBox();
        task.setValue("");
        task.addListener((observableValue, s, t1) -> {
            settingBox.getChildren().clear();
            switch(t1){
                case "denoising":
                    settingBox.getChildren().add(denoisingSelector);
                    break;
                case "super-resolution":
                    break;
                default:
                    break;
            }
        });

        HashMap<SimpleStringProperty, String> selections= new HashMap<>();
        selections.putAll(Collections.singletonMap(context.getTextName("superResolution"),"super-resolution"));
        selections.putAll(Collections.singletonMap(context.getTextName("denoising"),"denoising"));
        CustomChoiceBox taskSelector = new CustomChoiceBox(selections,task);

        msw.getChildren().add(taskSelector);
        msw.setSpacing(10);
        msw.getChildren().add(settingBox);

        return msw;
    }


    @Override
       public String[] getCMD() throws DynamicMessageException {
            switch(task.getValue()) {
                case "super-resolution":
                    return new String[]{
                            Context.independent(getEnvDir()+"/"+"python"),
                            "main_test_swinir.py",
                            "--task",
                            "real_sr",
                            "--scale",
                            "4",
                            "--large_model",
                            "--model_path",
                            Context.independent(getLocation()+"/model_zoo/swinir/003_realSR_BSRGAN_DFOWMFC_s64w8_SwinIR-L_x4_GAN.pth"),
                            "--input",
                            Context.independent(context.getTempdir()+"/input"),
                            "--output",
                            Context.independent(context.getTempdir()+"/output"),
                    };
                case "denoising":
                    return new String[]{
                            Context.independent(getEnvDir()+"/python3"),
                            "main_test_swinir.py",
                            "--task",
                            "color_dn",
                            "--noise",
                            String.valueOf(denoisingLevel),
                            "--model_path",
                            Context.independent(getLocation()+"/model_zoo/swinir/005_colorDN_DFWB_s128w8_SwinIR-M_noise"+String.valueOf(denoisingLevel)+".pth"),
                            "--input",
                            Context.independent(context.getTempdir()+"/input"),
                            "--output",
                            Context.independent(context.getTempdir()+"/output"),
                    };
                default:
                    throw new DynamicMessageException(context.getTextName("wrongSettings").getValue());
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
