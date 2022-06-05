package com.example.iegui.CustomNodes;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


/**
 * Adds a description in top of a node
 */
public class DescribedNode extends VBox {
    public DescribedNode(SimpleStringProperty description, Node node){
        this.setAlignment(Pos.CENTER);
        Text text = new Text();
        text.setStyle("-fx-font-weight: bold");
        text.textProperty().bind(description);
        this.getChildren().add(text);
        this.getChildren().add(node);
    }
}
