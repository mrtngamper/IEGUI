package com.example.iegui.AI;

import com.example.iegui.util.Context;

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
        return new String[]{
              getEnvDir()+"python3",
                "test_unpaired.py",
                "--opt",
                "confs/LOLv2-pc.yml",
                "--input",
                context.getTempdir()+"/input",
                "--output",
                context.getTempdir()+"/output"
                // "python test_unpaired.py --opt confs/LOLv2-pc.yml -n result"
        };
    }
}
