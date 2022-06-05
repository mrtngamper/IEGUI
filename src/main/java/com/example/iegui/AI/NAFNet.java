package com.example.iegui.AI;

import com.example.iegui.CustomNodes.CustomChoiceBox;
import com.example.iegui.CustomNodes.DescribedNode;
import com.example.iegui.CustomNodes.MethodSettingWindow;
import com.example.iegui.Exceptions.DynamicMessageException;
import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.text.Text;

import java.util.Collections;
import java.util.HashMap;

public class NAFNet extends ImageEnhanceMethod{

    SimpleStringProperty task = new SimpleStringProperty(); // denoising, deblurring

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
    public String[] getCMD() throws DynamicMessageException {
        switch(task.getValue()){
            case "denoising":
                return new String[]{
                        Context.independent(getEnvDir()+"/python"),
                        Context.independent(getLocation()+"/basicsr/demo.py"),
                        "-opt",
                        Context.independent(getLocation()+"/options/test/SIDD/NAFNet-width64.yml"),
                       "--input_path",
                        Context.independent(context.getTempdir()+"/input/input.png"),
                       "--output_path",
                        Context.independent(context.getTempdir()+"/output/output.png")
                };
            case "deblurring":
                return new String[]{
                        Context.independent(getEnvDir()+"/python"),
                        Context.independent(getLocation()+"/basicsr/demo.py"),
                        "-opt",
                        Context.independent(getLocation()+"/options/test/REDS/NAFNet-width64.yml"),
                        "--input_path",
                        Context.independent(context.getTempdir()+"/input/input.png"),
                        "--output_path",
                        Context.independent(context.getTempdir()+"/output/input.png")
                };
            default:
                throw new DynamicMessageException(context.getTextName("wrongSettings").getValue());
        }
    }

    @Override
    public MethodSettingWindow getSettingWindow() {

        MethodSettingWindow msw = new MethodSettingWindow();

        HashMap<SimpleStringProperty, String> selections= new HashMap<>();
        selections.putAll(Collections.singletonMap(context.getTextName("denoising"),"denoising"));
        selections.putAll(Collections.singletonMap(context.getTextName("deblurring"),"deblurring"));

        CustomChoiceBox taskSelector = new CustomChoiceBox(selections,task);


        msw.getChildren().add(new DescribedNode(context.getTextName("task"),taskSelector));
        msw.setSpacing(10);
        msw.setDownscale(getDownscaleFactor(),context);
        return msw;
    }
}
