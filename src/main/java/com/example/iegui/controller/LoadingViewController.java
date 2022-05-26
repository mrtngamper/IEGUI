package com.example.iegui.controller;

import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class LoadingViewController extends Controller {

    @FXML
    TextArea textarea;


    public void appendText(String valueOf) {
        Platform.runLater(() -> textarea.appendText(valueOf));
    }

    @Override
    public void setContext(Context context) {
        super.setContext(context);

        textarea.setEditable(false);
        context.getOutputStream().addStream(new Function<Integer, Object>() {
            @Override
            public Object apply(Integer integer) {
                appendText(String.valueOf((char)integer.intValue()));
                return null;
            }
        });
    }


}
