package com.example.iegui.AI;

import com.example.iegui.CustomNodes.MethodSettingWindow;
import com.example.iegui.util.Context;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.ArrayList;

public class GPEN extends ImageEnhanceMethod {
    private String task = "segmentation-to-face";
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
        String environment =  new File("Environments"+"/"+getEnvironment()).getAbsolutePath();
        switch(task) {
            case "face-enhancement":
                return new String[]{
                        environment + "/bin/python3",
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
                        environment + "/bin/python3",
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
                        environment + "/bin/python3",
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
                        environment + "/bin/python3",
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
                        context.getTempdir()+"/input",
                        "--outdir",
                        context.getTempdir()+"/output",
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
        HBox hBox = new HBox();

        ArrayList<StringProperty> availableTasks = new ArrayList<>(0);
        for (int i = 0; i < 4; i++) {
            availableTasks.add(new SimpleStringProperty());
        }

        availableTasks.get(0).bind(context.getTextName("faceEnhancement"));
        availableTasks.get(1).bind(context.getTextName("faceColorization"));
        availableTasks.get(2).bind(context.getTextName("faceInpainting"));
        availableTasks.get(3).bind(context.getTextName("segmentation2Face"));

        ChoiceBox<StringProperty> taskSelector = new ChoiceBox<>(FXCollections.observableList(availableTasks));

        taskSelector.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            switch (number.intValue()) {
                case 0:
                    task = "face-enhancement";
                    break;
                case 1:
                    task = "face-colorization";
                    break;
                case 2:
                    task = "face-inpainting";
                    break;
                case 3:
                    task = "segmentation-to-face";
                    break;
            }
        });

        Integer[] availableScales = new Integer[] {1, 2, 4};
        ChoiceBox<Integer> scaleSelector = new ChoiceBox<>(FXCollections.observableArrayList(availableScales));

        scaleSelector.getSelectionModel().selectedItemProperty().addListener((observableValue, integer, t1) -> scaleFactor = integer);

        hBox.getChildren().addAll(taskSelector, scaleSelector);

        MethodSettingWindow msw = new MethodSettingWindow();
        msw.setHBox(hBox, super.getDownscaleFactor());

        return msw;
    }
}
