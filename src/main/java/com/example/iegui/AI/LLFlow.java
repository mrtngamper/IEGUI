package com.example.iegui.AI;

import com.example.iegui.util.Context;
import com.example.iegui.util.paths;

import java.io.File;

public class LLFlow extends ImageEnhanceMethod{
    private String task = "small-net";
    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     *
     * @param location The method location
     * @param lang     The language which should be loaded
     * @param context
     */
    public LLFlow(String location, String lang, Context context) {
        super(location, lang, context);
    }

    @Override
    public String[] getCMD() {
        String environment =  new File("Environments"+"/"+getEnvironment()).getAbsolutePath();
        switch(task) {
            case "v1":
                return new String[]{
                        environment + "/bin/python3",
                        "test_unpaired.py",
                        "--opt",
                        "confs/LOL-pc.yml",
                        "--input",
                        context.getTempdir()+"/input",
                        "--output",
                        context.getTempdir()+"/output"
                        // "python test_unpaired.py --opt confs/LOLv2-pc.yml -n result"
                };
            case "v2":
                return new String[]{
                        environment + "/bin/python3",
                        "test_unpaired.py",
                        "--opt",
                        "confs/LOLv2-pc.yml",
                        "--input",
                        context.getTempdir()+"/input",
                        "--output",
                        context.getTempdir()+"/output"
                        // "python test_unpaired.py --opt confs/LOLv2-pc.yml -n result"
                };
            case "small-net":
                return new String[]{
                        environment + "/bin/python3",
                        "test_unpaired.py",
                        "--opt",
                        "confs/LOL_smallNet.yml",
                        "--input",
                        context.getTempdir()+"/input",
                        "--output",
                        context.getTempdir()+"/output"
                        // "python test_unpaired.py --opt confs/LOLv2-pc.yml -n result"
                };
            default:
                return null;
        }
    }
}
