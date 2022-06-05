package com.example.iegui.AI;

import com.example.iegui.CustomNodes.CustomChoiceBox;
import com.example.iegui.CustomNodes.DescribedNode;
import com.example.iegui.CustomNodes.MethodSettingWindow;
import com.example.iegui.util.Context;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.text.Text;


import java.io.File;
import java.util.Collections;
import java.util.HashMap;

public class LowLightImageEnhance extends ImageEnhanceMethod{

    private double gamma = 0.6;

    private SimpleStringProperty gammaProperty= new SimpleStringProperty();

    private double lambda = 0.15;

    @Override
    public MethodSettingWindow getSettingWindow() {

        MethodSettingWindow msw = new MethodSettingWindow();

        HashMap<SimpleStringProperty, String> selections= new HashMap<>();
        for(double i = 1; i<10; i+=1){
            selections.putAll(Collections.singletonMap(new SimpleStringProperty((String.valueOf(i/10))),String.valueOf(i/10)));
        }
        gammaProperty.addListener((observableValue, s, t1) -> gamma = Double.parseDouble(t1));
        gammaProperty.setValue("0.1");

        CustomChoiceBox taskSelector = new CustomChoiceBox(selections,gammaProperty);

        msw.getChildren().add(new DescribedNode(context.getTextName("gamma"),taskSelector));
        msw.setSpacing(10);
        msw.setDownscale(getDownscaleFactor(),context);
        return msw;
    }



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
                Context.independent(getEnvDir()+"/python"),
                "demo.py",
                "-l",
                String.valueOf(lambda),
                "-g",
                String.valueOf(gamma),
                "--input",
                Context.independent(new File(context.getTempdir()+"/input/").getAbsolutePath()),
                "--output",
                Context.independent((new File(context.getTempdir()+"/output/").getAbsolutePath()))

        };
    }
}
