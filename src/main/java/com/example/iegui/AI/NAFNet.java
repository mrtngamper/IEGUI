package com.example.iegui.AI;

import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import com.example.iegui.util.paths;

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
                        paths.independent(getEnvDir()+"/python3"),
                        paths.independent("basicsr/demo.py"),
                        "-opt",
                        paths.independent("options/test/SIDD/NAFNet-width64.yml"),
                       "--input_path",
                        paths.independent(context.getTempdir()+"/input/input.png"),
                       "--output_path",
                        paths.independent(context.getTempdir()+"/output/output.png")
                };
            case "deblurring":
                return new String[]{
                        paths.independent(getEnvDir()+"/python3"),
                        paths.independent("basicsr/demo.py"),
                        "-opt",
                        paths.independent("options/test/REDS/NAFNet-width64.yml"),
                        "--input_path",
                        paths.independent(context.getTempdir()+"/input/input.png"),
                        "--output_path",
                        paths.independent(context.getTempdir()+"/output/input.png")
                };
            default:
                Alerts.Error("NAFNet: Case not Found");
                return null;
        }
    }
}
