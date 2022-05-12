package com.example.iegui.AI;

public class SwinIR extends ImageEnhanceMethod{


    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     *
     * @param location The model location
     * @param lang     The language which should be loaded
     */
    public SwinIR(String location, String lang) {
        super(location, lang);

    }
}
