package com.example.iegui.util;


import com.example.iegui.Exceptions.TextNotFoundException;
import com.example.iegui.Exceptions.YAMLTypeNotValidException;
import javafx.beans.property.SimpleStringProperty;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility Class to manage Languages and enable language change at runtime
 */
public class Language extends Controller {

    /**
     * HashMap containing all the language data.
     */
    private HashMap<String, SimpleStringProperty> lan = new HashMap<>();


    /**
     * Loads all language objects from the specified File into the lan HashMap
     * @param filename Filename of the
     * @throws FileNotFoundException
     */
    public void load(String filename) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream inputStream = getClass().getResourceAsStream(filename);
        if(inputStream==null){
            Alerts.Error("Language file not found");
            System.out.println(filename);
            return;
        }
        Map<String, Object> map = yaml.load(inputStream);
        if(map == null){
            throw new FileNotFoundException(filename);
        }

        for (String i:lan.keySet() ) {
            lan.get(i).setValue("-------------");
        }
        for (String i: map.keySet()) {
            if(lan.containsKey(i)){
                lan.get(i).setValue(map.get(i).toString());
            }else{
                lan.put(i, new SimpleStringProperty(map.get(i).toString()));
            }
        }
    }

    /**
     * Returns the corresponding value in the lan HashMap
     * @param key
     * @return
     */
    public SimpleStringProperty get(String key) throws TextNotFoundException {
        if(!lan.containsKey(key)) {
            throw new TextNotFoundException(key, context.getLang());
        }
        return lan.get(key);
    }




}
