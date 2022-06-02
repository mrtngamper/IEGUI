package com.example.iegui.AI;

import com.example.iegui.util.Context;
import com.example.iegui.util.paths;

import java.io.File;

public class LowLightImageEnhance extends ImageEnhanceMethod{

    private double gamma = 0.6;

    private double lambda = 0.15;

    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     *
     * @param location The method location
     * @param lang     The language which should be loaded
     * @param context
     */
    public LowLightImageEnhance(String location, String lang, Context context) {
        super(location, lang, context);
    }

    @Override
    public String[] getCMD() {
        return new String[]{
                paths.independent(getEnvDir()+"/python3"),
                "demo.py",
                "-l",
                String.valueOf(lambda),
                "-g",
                String.valueOf(gamma),
                "--input",
                paths.independent(context.getTempdir()+"/input/"),
                "--output",
                paths.independent(context.getTempdir()+"/output/"),

        };
    }
}
