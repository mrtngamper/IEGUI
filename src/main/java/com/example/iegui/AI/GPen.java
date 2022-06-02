package com.example.iegui.AI;

import com.example.iegui.util.Context;

public class GPen extends ImageEnhanceMethod{
    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     *
     * @param location The method location
     * @param lang     The language which should be loaded
     * @param context
     */
    public GPen(String location, String lang, Context context) {
        super(location, lang, context);
    }

    @Override
    public String[] getCMD() {
        return new String[0];
    }
}
