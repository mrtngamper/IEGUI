package com.example.iegui.CustomNodes;

import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;
import java.util.concurrent.Callable;

/**
 * Pain which enables image comparisons with a dynamic slider to switch between the two images.
 */
public class ImageComparisonPain extends Pane {

    private SimpleDoubleProperty position= new SimpleDoubleProperty(0.7);
    private SimpleDoubleProperty height = new SimpleDoubleProperty(0);
    private SimpleDoubleProperty width = new SimpleDoubleProperty(0);
    private double imageRatio ;

    public ImageComparisonPain(String input, String output){
        try {
            ImageView inputImage = Context.loadImage(input);
            ImageView outputImage = Context.loadImage(output);
            imageRatio= inputImage.getImage().getHeight()/inputImage.getImage().getWidth();

            Pane inputPane = new Pane();
            inputPane.getChildren().add(inputImage);
            Pane outputPane = new Pane();
            outputPane.getChildren().add(outputImage);


            Pane pain = this;

            height.bind(Bindings.createDoubleBinding(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    double ratio = pain.getHeight()/pain.getWidth();
                    if(ratio> imageRatio){
                        return pain.getWidth()*imageRatio;
                    }else{
                        return pain.getHeight();
                    }
                }
            },this.heightProperty(), this.widthProperty()));

            width.bind(Bindings.createDoubleBinding(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    double ratio = pain.getHeight()/pain.getWidth();
                    if(ratio>= imageRatio){
                        return pain.getWidth();
                    }else{
                        return pain.getHeight()/imageRatio;
                    }
                }
            },this.heightProperty(), this.widthProperty()));

            inputImage.fitHeightProperty().bind(height);
            inputImage.fitWidthProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    inputImage.setViewport(new Rectangle2D(0,0,inputImage.getImage().getWidth()*position.getValue(),inputImage.getImage().getHeight()));
                    return width.getValue()*position.getValue();
                }
            },position,width));

            outputImage.fitHeightProperty().bind(height);
            outputImage.fitWidthProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    outputImage.setViewport(new Rectangle2D(outputImage.getImage().getWidth()*(position.getValue()),0,outputImage.getImage().getWidth()-outputImage.getImage().getWidth()*(position.getValue()),outputImage.getImage().getHeight()));
                     return width.getValue()*(1-position.getValue());
                }
            },position,width));

            inputImage.setPreserveRatio(false);
            outputImage.setPreserveRatio(false);

            inputImage.xProperty().bind(this.widthProperty().subtract(width).divide(2));
            outputImage.xProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    return (pain.getWidth()-width.getValue())/2+width.getValue()*position.getValue();
                }
            },width,position));
            Rectangle rectangle = new Rectangle();
            rectangle.setY(0);
            rectangle.setWidth(10);
            rectangle.heightProperty().bind(height);
            rectangle.xProperty().bind(Bindings.createDoubleBinding(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    return (pain.getWidth()-width.getValue())/2+width.getValue()*position.getValue()-rectangle.getWidth()/2;
                }
            },width, position));

            rectangle.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    double minX = (pain.getWidth()-width.getValue())/2;
                    double maxX = minX+width.getValue();
                   if(mouseEvent.getX()>=maxX){
                        position.setValue(0.99);
                    }else if(mouseEvent.getX()<=minX){
                        position.setValue(0.01);
                    }else{
                        position.setValue((mouseEvent.getX()-minX)/(maxX-minX));
                    }
                }
            });


            rectangle.setFill(Color.color(123/256f,158/256f,125/256f));


            Rectangle frame = new Rectangle();
            frame.widthProperty().bind(width);
            frame.heightProperty().bind(height);
            frame.xProperty().bind(this.widthProperty().subtract(width).divide(2));

            frame.setFill(Color.TRANSPARENT);
            frame.setStrokeWidth(3);
            frame.setStroke(Color.BLACK);


            this.getChildren().addAll(frame, inputImage,outputImage, rectangle);


        } catch (FileNotFoundException e) {
            Alerts.Error(e.getMessage());
            return;
        }
    }
}
