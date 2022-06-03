package com.example.iegui.CustomNodes;

import com.example.iegui.util.Context;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * This View is displayed in the Main View before any image was selected
 */
public class DragAndDrop extends BorderPane {
    public DragAndDrop(Context context, Button browse){
        VBox vbox = new VBox();
        this.setCenter(vbox);
        vbox.setAlignment(Pos.CENTER);

        browse.textProperty().bind(context.getTextName("browse"));
        browse.setStyle("-fx-font-smoothing-type: lcd;\n" + " -fx-fill: accent3;\n" + " -fx-font-family: \"Arial Black\";\n" );

        VBox.setVgrow(vbox, Priority.ALWAYS);
        Label text = new Label();
        text.setFont(Font.font(20));
        text.textProperty().bind(context.getTextName("browse2"));


        this.setId("dragAndDrop");

        vbox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                browse.setStyle("-fx-font-size: "+String.valueOf(t1.intValue()/4));
                text.setStyle("-fx-font-size: "+String.valueOf(t1.intValue()/7));
            }
        });

        vbox.getChildren().add(browse);
        vbox.getChildren().add(text);
    }
}
