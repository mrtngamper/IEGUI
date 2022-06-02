package com.example.iegui.AI;

import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;

public class NAFNet extends ImageEnhanceMethod{

    String task = "deblurring"; // denoising, deblurring

    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     *
     * @param location The method location
     * @param lang     The language which should be loaded
     * @param context
     */
    public NAFNet(String location, String lang, Context context) {
        super(location, lang, context);
    }

    @Override
    public String[] getCMD() {
        switch(task){
            case "denoising":
                return new String[]{
                        getEnvDir()+"python3",
                        "basicsr/demo.py",
                        "-opt",
                        "options/test/SIDD/NAFNet-width64.yml",
                       "--input_path",
                        context.getTempdir()+"/input/input.png",
                       "--output_path",
                        context.getTempdir()+"/output/output.png"
                };
            case "deblurring":
                return new String[]{
                        getEnvDir()+"python3",
                        "basicsr/demo.py",
                        "-opt",
                        "options/test/REDS/NAFNet-width64.yml",
                        "--input_path",
                        context.getTempdir()+"/input/input.png",
                        "--output_path",
                        context.getTempdir()+"/output/input.png"
                };
            default:
                Alerts.Error("NAFNet: Case not Found");
                return null;
        }
    }
}
