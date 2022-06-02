package com.example.iegui.CustomNodes;

import com.example.iegui.util.Alerts;
import com.example.iegui.util.Context;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.yaml.snakeyaml.util.UriEncoder;

import java.io.File;
import java.net.URI;

/**
 * This Node is being displayed in the Main View after an images was selected
 */
public class ImageLoaded extends BorderPane {

    public void setNewImage(File imageFile, Context context) throws Exception {
        if(imageFile.exists()){
            HBox hbox = new HBox();
            hbox.setAlignment(Pos.CENTER_LEFT);
            Image image = new Image(imageFile.toURI().toString());

            String[] splitter = imageFile.getName().split("\\.");
            String fileType = splitter[splitter.length - 1];
            if(!fileType.equals("png") && !fileType.equals("jpg") && !fileType.equals("gif") && !fileType.equals("jps") && !fileType.equals("mpo")) {
                Alerts.Error(context.getTextName("noFoto").getValue());
                throw new Exception();
            }

            VBox.setVgrow(hbox, Priority.ALWAYS);
            HBox.setHgrow(hbox, Priority.ALWAYS);

            this.setCenter(hbox);

            ImageView imageView = new ImageView();
            imageView.setImage(image);

            Label imageName = new Label();
            imageName.setText(imageFile.getName());

            imageView.setPreserveRatio(true);

            //hbox.getChildren().add(imageView);
            hbox.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    imageName.setStyle("-fx-font-size: "+String.valueOf(t1.intValue()/4));
                    imageView.setFitHeight(t1.intValue()*0.8);
                }
            });

            hbox.setSpacing(10);
            hbox.setPadding(new Insets(0,0,0,10));

            hbox.getChildren().add(imageView);
            hbox.getChildren().add(imageName);

        }else{
            Alerts.Error(context.getTextName("imageNotFound").getValue());
            throw new Exception();
        }
    }

}
