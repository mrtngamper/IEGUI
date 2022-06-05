package com.example.iegui.util;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.converter.NumberStringConverter;

import java.text.ParsePosition;
import java.util.function.UnaryOperator;



public class NumberField extends HBox {
    private TextField numberField= new TextField();
    private int maxValue;
    // TODO: Double und nicht parsen
    public NumberField(int maxValue){
        this.maxValue=maxValue;

        NumberStringFilteredConverter converter = new NumberStringFilteredConverter();
        final TextFormatter<Number> formatter = new TextFormatter<>(
                converter,
                0,
                converter.getFilter(maxValue)
        );
        numberField.setTextFormatter(formatter);
        numberField.setPromptText("number");
        numberField.setPrefWidth(50);

        final double ARROW_SIZE = 4;
        Path arrowUp = new Path();
        arrowUp.getElements().addAll(new MoveTo(-ARROW_SIZE, 0), new LineTo(ARROW_SIZE, 0),
                new LineTo(0, -ARROW_SIZE), new LineTo(-ARROW_SIZE, 0));

        Path arrowDown = new Path();
        arrowDown.getElements().addAll(new MoveTo(-ARROW_SIZE,0), new LineTo(ARROW_SIZE,0),
                new LineTo(0,ARROW_SIZE), new LineTo(-ARROW_SIZE,0));



        VBox vbox = new VBox();
        Button up = new Button();
        up.prefHeightProperty().bind(numberField.heightProperty().divide(2));
        up.minHeightProperty().bind(numberField.heightProperty().divide(2));
        up.maxHeightProperty().bind(numberField.heightProperty().divide(2));
        up.setGraphic(arrowUp);
        up.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(getValue()<maxValue){
                    setValue(getValue()+0.1);
                }
            }
        });

        Button down = new Button();
        down.prefHeightProperty().bind(numberField.heightProperty().divide(2));
        down.minHeightProperty().bind(numberField.heightProperty().divide(2));
        down.maxHeightProperty().bind(numberField.heightProperty().divide(2));
        down.setGraphic(arrowDown);
        down.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(getValue()>=0){
                    setValue(getValue()-0.1);
                }
            }
        });

        vbox.getChildren().addAll(up,down);

        this.getChildren().add(numberField);
        this.getChildren().add(vbox);
    }
    public void setValue(double value){
        numberField.setText(String.valueOf(value));
    }
    public double getValue(){
        if(numberField.getText().equals("")){
            return -1;
        }
        return Double.parseDouble(numberField.getText());
    }
}
class NumberStringFilteredConverter extends NumberStringConverter {
    public UnaryOperator<TextFormatter.Change> getFilter(int maxValue) {
        return change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) {
                return change;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = getNumberFormat().parse(newText, parsePosition);
            if (object == null || parsePosition.getIndex() < newText.length()) {
                return null;
            } else {
                try {
                    if (Double.parseDouble(newText) <= maxValue) {
                        return change;
                    } else {
                        return null;
                    }
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    return null;
                }
            }
        };
    }
}