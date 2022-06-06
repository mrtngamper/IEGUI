package com.example.iegui.AI;

import com.example.iegui.CustomNodes.CustomChoiceBox;
import com.example.iegui.CustomNodes.DescribedNode;
import com.example.iegui.CustomNodes.MethodSettingWindow;
import com.example.iegui.Exceptions.DynamicMessageException;
import com.example.iegui.util.Context;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.text.Text;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;

public class LLFlow extends ImageEnhanceMethod{
    private SimpleStringProperty task = new SimpleStringProperty();
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
    public String[] getCMD() throws DynamicMessageException {
        String environment =  new File("Environments"+"/"+getEnvironment()).getAbsolutePath();
        switch(task.getValue()) {
            case "v1":
                return new String[]{
                        Context.independent(getEnvDir()+"/python"),
                        "test_unpaired.py",
                        "--opt",
                        Context.independent("confs/LOL-pc.yml"),
                        "--input",
                        Context.independent(context.getTempdir()+"/input"),
                        "--output",
                        Context.independent(context.getTempdir()+"/output")
                        // "python test_unpaired.py --opt confs/LOLv2-pc.yml -n result"
                };
            case "v2":
                return new String[]{
                        Context.independent(getEnvDir()+"/python"),
                        "test_unpaired.py",
                        "--opt",
                        Context.independent("confs/LOLv2-pc.yml"),
                        "--input",
                        Context.independent(context.getTempdir()+"/input"),
                        "--output",
                        Context.independent(context.getTempdir()+"/output")
                        // "python test_unpaired.py --opt confs/LOLv2-pc.yml -n result"
                };
            case "small-net":
                return new String[]{
                        Context.independent(getEnvDir()+"/python"),
                        "test_unpaired.py",
                        "--opt",
                        Context.independent("confs/LOL_smallNet.yml"),
                        "--input",
                        Context.independent(context.getTempdir()+"/input"),
                        "--output",
                        Context.independent(context.getTempdir()+"/output")
                        // "python test_unpaired.py --opt confs/LOLv2-pc.yml -n result"
                };
            default:
                throw new DynamicMessageException(context.getTextName("wrongSettings").getValue());
        }

    }

    @Override
    public MethodSettingWindow getSettingWindow() {

        MethodSettingWindow msw = new MethodSettingWindow();

        HashMap<SimpleStringProperty, String> selections= new HashMap<>();
        selections.putAll(Collections.singletonMap(context.getTextName("oldModel"),"v1"));
        selections.putAll(Collections.singletonMap(context.getTextName("newModel"),"v2"));
        selections.putAll(Collections.singletonMap(context.getTextName("fastModel"),"small-net"));

        CustomChoiceBox taskSelector = new CustomChoiceBox(selections,task);

        msw.getChildren().add(new DescribedNode(context.getTextName("model"),taskSelector));
        msw.setSpacing(10);
        msw.setDownscale(getDownscaleFactor(),context);
        return msw;
    }
}
