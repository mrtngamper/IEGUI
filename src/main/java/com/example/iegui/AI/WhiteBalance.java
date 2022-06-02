package com.example.iegui.AI;

import com.example.iegui.util.Context;

import java.io.File;

public class WhiteBalance extends ImageEnhanceMethod{
    /**
     * Upon object creation the method directory is being stored and method settings are loaded from the Config folder.
     *
     * @param location The method location
     * @param lang     The language which should be loaded
     * @param context
     */
    public WhiteBalance(String location, String lang, Context context) {
        super(location, lang, context);
    }



    @Override
    public String[] getCMD() {
        return new String[]{
                getEnvDir()+"python3",
                "test.py",
                "--wb-settings",
                "D",
                "S",
                "T",
                "F",
                "C",
                "--model-name",
                "WB_model_p_128_D_S_T_F_C",
                "--testing-dir",
                String.format(context.getTempdir()+"/input"),
                "--outdir",
                String.format(context.getTempdir()+"/output"),
        };
    }
}
// python test.py --wb-settings D S T F C --model-name WB_model_p_128_D_S_T_F_C  --testing-dir data/test_images  --outdir ./results