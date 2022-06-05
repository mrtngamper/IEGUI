package com.example.iegui.AI;

import com.example.iegui.CustomNodes.CustomChoiceBox;
import com.example.iegui.CustomNodes.DescribedNode;
import com.example.iegui.CustomNodes.MethodSettingWindow;
import com.example.iegui.util.Context;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;

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
                Context.independent(getEnvDir()+"/python"),
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
                Context.independent(String.format(context.getTempdir()+"/input")),
                "--outdir",
                Context.independent(String.format(context.getTempdir()+"/output")),
        };
    }

    @Override
    public MethodSettingWindow getSettingWindow() {
        MethodSettingWindow msw = new MethodSettingWindow();
        msw.setDownscale(getDownscaleFactor(),context);
        return msw;
    }
}
// python test.py --wb-settings D S T F C --model-name WB_model_p_128_D_S_T_F_C  --testing-dir data/test_images  --outdir ./results