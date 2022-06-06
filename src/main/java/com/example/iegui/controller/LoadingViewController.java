package com.example.iegui.controller;

import com.example.iegui.util.Context;
import com.example.iegui.util.Controller;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.Callable;
import java.util.function.Function;

/**
 * Controller for loading view
 */
public class LoadingViewController extends Controller {

    @FXML
    TextArea textarea;

    @FXML
    HBox loadingBox;

    @FXML
    BorderPane loadingPane;

    private Button abort;

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

        abort = new Button();
        abort.textProperty().bind(context.getTextName("abort"));

        abort.setOnAction(actionEvent -> loadingPane.getScene().getWindow().fireEvent(new WindowEvent( loadingPane.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST)));

        loadingBox.setAlignment(Pos.CENTER_RIGHT);
        loadingBox.getChildren().add(abort);
    }

    public void setAbortEvent(javafx.event.EventHandler<ActionEvent> event){
        abort.setOnAction(event);
    }

}
