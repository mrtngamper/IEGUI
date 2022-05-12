package com.example.iegui.AI;

import com.example.iegui.CustomNodes.MethodSettingWindow;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public abstract class ImageEnhanceMethod {
    private String name;
    private String description;

    private final ArrayList<String> example_input= new ArrayList<>();
    private final ArrayList<String> example_output= new ArrayList<>();

    private String miniature_input;
    private String miniature_output;

    private String location;

    private  MethodSettingWindow settingWindow;


    public ArrayList<String> getExample_input() {
        return example_input;
    }

    public ArrayList<String> getExample_output() {
        return example_output;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMiniature_input() {
        return miniature_input;
    }

    public void setMiniature_input(String miniature_input) {
        this.miniature_input = miniature_input;
    }

    public String getMiniature_output() {
        return miniature_output;
    }

    public void setMiniature_output(String miniature_output) {
        this.miniature_output = miniature_output;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public MethodSettingWindow getSettingWindow() {
        return settingWindow;
    }

    public void setSettingWindow(MethodSettingWindow settingWindow) {
        this.settingWindow = settingWindow;
    }

    public void loadYAML(String file){
        try {
            System.out.println(Files.readString(Path.of(file)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
