package com.example.iegui.AI;

import com.example.iegui.util.Context;
import com.example.iegui.util.paths;

import java.io.File;

public class LLFlow extends ImageEnhanceMethod{
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
        return new String[]{
                paths.independent(environment + "/bin/python3"),
                "test_unpaired.py",
                "--opt",
                paths.independent("confs/LOLv2-pc.yml"),
                "--input",
                paths.independent(context.getTempdir()+"/input"),
                "--output",
                paths.independent(context.getTempdir()+"/output")
                // "python test_unpaired.py --opt confs/LOLv2-pc.yml -n result"
        };
    }
}
