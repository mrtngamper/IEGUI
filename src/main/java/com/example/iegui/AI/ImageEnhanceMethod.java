package com.example.iegui.AI;

import com.example.iegui.CustomNodes.MethodSettingWindow;
import com.example.iegui.Exceptions.YAMLTypeNotValidException;
import com.example.iegui.util.Alerts;
import org.yaml.snakeyaml.*;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * An ImageEnhanceMethod Object contains Info about a Method (e.g. SwinIR).
 */
public abstract class ImageEnhanceMethod {
    private String name="";
    private String description="";


    private final HashMap<String,String> examples = new HashMap<>();

    private final HashMap<String, String> miniatures = new HashMap<>();

    private String location;

    private  MethodSettingWindow settingWindow;


    private String environment;

    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     * @param location The method location
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


    public HashMap<String, String> getExamples() {
        return examples;
    }

    public HashMap<String, String> getMiniatures() {
        return miniatures;
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

    public String getEnvironment() {
        return environment;
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


    public abstract void start(String inputfile, String outputfile);



    /**
     * Loads ImageEnhanceMethod options from file
     * @param file filename of the yml file which contains the data
     * @throws YAMLTypeNotValidException thrown, when yaml object type is wrong
     * @throws FileNotFoundException thrown, when file does not exist
     */
    public void loadYAML(String file) throws YAMLTypeNotValidException, FileNotFoundException {

            Yaml yaml = new Yaml();
            InputStream inputStream = new FileInputStream(file);
            Map<String, Object> map = yaml.load(inputStream);

            Object ex_input = map.get("examples");
            if (ex_input != null) {
                if (ex_input instanceof HashMap) {
                    examples.putAll((HashMap) ex_input);
                } else {
                    throw new YAMLTypeNotValidException(HashMap.class.toString(), ex_input.getClass().toString(), "miniatures", file);
                }
            }

            Object min_in = map.get("miniatures");
            if (min_in != null) {
                if (min_in instanceof HashMap) {
                    miniatures.putAll((HashMap)min_in);
                } else {
                    throw new YAMLTypeNotValidException(HashMap.class.toString(), min_in.getClass().toString(), "miniatures", file);
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

        Object environment = map.get("environment");
        if (environment != null) {
            if (environment instanceof String) {
                this.environment = environment.toString();
            } else {
                throw new YAMLTypeNotValidException(String.class.toString(), environment.getClass().toString(), "environment", file);
            }
        }
    }

    /**
     * Tries to install all the dependencies found in the requirements.txt
     * @throws Exception
     */
    public void installDependencies() throws Exception{
        File file = new File("Environments"+"/"+environment);
        if(file.exists()) {
            String[] cmd = {
                    "Environments/"+environment+"/bin/pip3",
                    "install",
                    "-r",
                    "Environments"+"/"+environment+".txt"
            };
            ProcessBuilder pb = new ProcessBuilder(cmd);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(ProcessBuilder.Redirect.INHERIT);
            pb.redirectInput(ProcessBuilder.Redirect.INHERIT);


            Process process = pb.start();
            process.waitFor();
            switch(process.exitValue()){
                case 132:
                    System.out.println("Dependencies not found");
                    break;
                default:
                    Alerts.Error("Unerwarteter Fehler");
            }


            System.out.println("Python Virtual Environment Created");
            return ;
        }
        System.out.println("Python Virtual Environment Does Not Exist");
    }

    /**
     * Creates a python environment with the name environment in the location and installs the dependencies
     * @throws Exception
     */
    public void createEnvironment() throws Exception {
        File file = new File("Environments"+"/"+environment);
        if(!file.exists()) {
            String[] cmd = {
                    "python",
                    "-m",
                    "venv",
                    file.getAbsolutePath()
            };
            Process process = Runtime.getRuntime().exec(cmd);
            process.waitFor();

            System.out.println("Python Virtual Environment Created");
            installDependencies();
            return ;
        }
        System.out.println("Python Virtual Environment Does Exist");
    }
}
