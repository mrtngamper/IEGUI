package com.example.iegui.AI;

import com.example.iegui.CustomNodes.DescribedNode;
import com.example.iegui.CustomNodes.MethodSettingWindow;
import com.example.iegui.util.Context;
import com.example.iegui.util.NumberField;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.util.ArrayList;

public class GPEN extends ImageEnhanceMethod {
    private String task = "face-enhancement";
    private int scaleFactor = 4;
    private int inputResolution = 512;
    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     *
     * @param location The method location
     * @param lang     The language which should be loaded
     * @param context
     */
    public GPEN(String location, String lang, Context context) {
        super(location, lang, context);
    }

    @Override
    public String[] getCMD() {
        switch(task) {
            case "face-enhancement":
                return new String[]{
                        Context.independent(getEnvDir() + "/python3"),
                        "main.py",
                        "--model",
                        "GPEN-BFR-512",
                        "--task",
                        "FaceEnhancement",
                        "--sr_scale",
                        String.valueOf(scaleFactor),
                        "--in_size",
                        String.valueOf(inputResolution),
                        "--indir",
                        context.getTempdir()+"/input",
                        "--outdir",
                        context.getTempdir()+"/output",
                };
            case "face-colorization":
                return new String[]{
                        Context.independent(getEnvDir() + "/python3"),
                        "main.py",
                        "--model",
                        "GPEN-Colorization-1024",
                        "--task",
                        "FaceColorization",
                        "--sr_scale",
                        String.valueOf(scaleFactor),
                        "--in_size",
                        "1024",
                        "--indir",
                        context.getTempdir()+"/input",
                        "--outdir",
                        context.getTempdir()+"/output",
                };
            case "face-inpainting":
                return new String[]{
                        Context.independent(getEnvDir() + "/python3"),
                        "main.py",
                        "--model",
                        "GPEN-Inpainting-1024",
                        "--task",
                        "FaceInpainting",
                        "--sr_scale",
                        String.valueOf(scaleFactor),
                        "--in_size",
                        "1024",
                        "--indir",
                        context.getTempdir()+"/input",
                        "--outdir",
                        context.getTempdir()+"/output",
                };
            case "segmentation-to-face":
                return new String[]{
                        Context.independent(getEnvDir() + "/python3"),
                        "main.py",
                        "--model",
                        "GPEN-Seg2face-512",
                        "--task",
                        "Segmentation2Face",
                        "--sr_scale",
                        String.valueOf(scaleFactor),
                        "--in_size",
                        "512",
                        "--indir",
                        Context.independent(context.getTempdir()+"/input"),
                        "--outdir",
                        Context.independent(context.getTempdir()+"/output"),
                };
            default:
                return null;
        }
    }


    /**
     *
     * @param task face-enhancement, face-colorization, face-inpainting or segmentation-to-face
     */
    public void setTask(String task) {
        if (task.equals("face-enhancement") || task.equals("face-colorization") || task.equals("face-inpainting") || task.equals("segmentation-to-face")) {
            this.task = task;
        } else {
            throw new IllegalArgumentException("Task must be one of the following: face-enhancement, face-colorization, face-inpainting or segmentation-to-face");
        }
    }

    public void setScaleFactor(int scaleFactor) {
        if (scaleFactor > 0) {
            this.scaleFactor = scaleFactor;
        } else {
            throw new IllegalArgumentException("Scale factor must be greater than 0");
        }
    }

    public void setInputResolution(int inputResolution) {
        this.inputResolution = inputResolution;
    }

    @Override
    public MethodSettingWindow getSettingWindow() {
        MethodSettingWindow settingWindow = new MethodSettingWindow();

        ArrayList<String> availableTasks = new ArrayList<>(0);
        availableTasks.add(context.getTextName("faceEnhancement").get());
        availableTasks.add(context.getTextName("faceColorization").get());
        availableTasks.add(context.getTextName("faceInpainting").get());
        availableTasks.add(context.getTextName("segmentation2Face").get());

        context.getTextName("faceEnhancement").addListener((observableValue, s, t1) -> availableTasks.set(0, s));
        context.getTextName("faceColorization").addListener((observableValue, s, t1) -> availableTasks.set(1, s));
        context.getTextName("faceInpainting").addListener((observableValue, s, t1) -> availableTasks.set(2, s));
        context.getTextName("segmentation2Face").addListener((observableValue, s, t1) -> availableTasks.set(3, s));


        ChoiceBox<String> taskSelector = new ChoiceBox<>(FXCollections.observableList(availableTasks));


        taskSelector.setValue(availableTasks.get(0));

        Integer[] availableScales = new Integer[] {1, 2, 4};

        ChoiceBox<Integer> scaleSelector = new ChoiceBox<>(FXCollections.observableArrayList(availableScales));
        scaleSelector.setValue(1);

        scaleSelector.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> scaleFactor = newValue);

        Integer[] availableInSizes = new Integer[] {256, 512};
        ChoiceBox<Integer> inSizeSelector = new ChoiceBox<>(FXCollections.observableArrayList(availableInSizes));

        inSizeSelector.setValue(512);
        inSizeSelector.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> inputResolution = newValue);

        settingWindow.getChildren().addAll(
                new DescribedNode(context.getTextName("task"),taskSelector),
                new DescribedNode(context.getTextName("inRes"),inSizeSelector),
                new DescribedNode(context.getTextName("scale"),scaleSelector));

        settingWindow.setDownscale(super.getDownscaleFactor(), context);

        taskSelector.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue) -> {
            switch (newValue.intValue()) {
                case 0:
                    task = "face-enhancement";
                    scaleSelector.setDisable(false);
                    inSizeSelector.setDisable(false);
                    break;
                case 1:
                    task = "face-colorization";
                    scaleSelector.setDisable(true);
                    inSizeSelector.setDisable(true);
                    break;
                case 2:
                    task = "face-inpainting";
                    scaleSelector.setDisable(true);
                    inSizeSelector.setDisable(true);
                    break;
                case 3:
                    task = "segmentation-to-face";
                    scaleSelector.setDisable(true);
                    inSizeSelector.setDisable(true);
                    break;
            }
        });
        return settingWindow;
    }
}
