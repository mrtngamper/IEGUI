package com.example.iegui.AI;

import com.example.iegui.CustomNodes.MethodSettingWindow;
import com.example.iegui.Exceptions.YAMLTypeNotValidException;
import com.example.iegui.util.Alerts;
import org.yaml.snakeyaml.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

/**
 * An ImageEnhanceMethod Object contains Info about a Method (e.g. SwinIR).
 */
public abstract class ImageEnhanceMethod {
    private String name="";
    private String description="";

    private final ArrayList<String> example_input= new ArrayList<>();
    private final ArrayList<String> example_output= new ArrayList<>();

    private String miniature_input="";
    private String miniature_output="";

    private String location;

    private  MethodSettingWindow settingWindow;

    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     * @param location The model location
     * @param lang The language which should be loaded
     */
    public ImageEnhanceMethod(String location, String lang){
        this.location=location;
        try {
            loadYAML(location + "/" + "Config" + "/" + lang + ".yml");
        }catch(Exception e){
            Alerts.Warning(e.getMessage());
        }
    }


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


    public void loadYAML(String file) throws YAMLTypeNotValidException, FileNotFoundException {

            Yaml yaml = new Yaml();
            InputStream inputStream = new FileInputStream(file);
            Map<String, Object> map = yaml.load(inputStream);

            Object ex_input = map.get("example_input");
            if (ex_input != null) {
                if (ex_input instanceof ArrayList) {
                    example_input.addAll((ArrayList) ex_input);
                } else {
                    throw new YAMLTypeNotValidException(ArrayList.class.toString(), ex_input.getClass().toString(), "example_input", file);
                }
            }

            Object ex_output = map.get("example_output");
            if (ex_output != null) {
                if (ex_output instanceof ArrayList) {
                    example_output.addAll((ArrayList) ex_output);
                } else {
                    throw new YAMLTypeNotValidException(ArrayList.class.toString(), ex_output.getClass().toString(), "example_output", file);
                }
            }

            Object min_in = map.get("miniature_input");
            if (min_in != null) {
                if (min_in instanceof String) {
                    miniature_input = min_in.toString();
                } else {
                    throw new YAMLTypeNotValidException(String.class.toString(), min_in.getClass().toString(), "miniature_input", file);
                }
            }

            Object min_out = map.get("miniature_output");
            if (min_out != null) {
                if (min_out instanceof String) {
                    miniature_output = min_out.toString();
                } else {
                    throw new YAMLTypeNotValidException(String.class.toString(), min_out.getClass().toString(), "miniature_output", file);
                }
            }

            Object name_yml = map.get("name");
            if (name_yml != null) {
                if (name_yml instanceof String) {
                    name = name_yml.toString();
                } else {
                    throw new YAMLTypeNotValidException(String.class.toString(), name_yml.getClass().toString(), "name", file);
                }
            }

            Object description_yml = map.get("description");
            if (description_yml != null) {
                if (description_yml instanceof String) {
                    description = description_yml.toString();
                } else {
                    throw new YAMLTypeNotValidException(String.class.toString(), description_yml.getClass().toString(), "description", file);
                }
            }

    }
}
