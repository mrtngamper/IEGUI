package com.example.iegui.CustomNodes;

import com.example.iegui.AI.ImageEnhanceMethod;
import com.example.iegui.util.Context;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;

public class EnhanceMethodListView extends ListView {
    private SettingsView settings ;

    public EnhanceMethodListView(Context context, BorderPane subBorderPane, SimpleStringProperty openedFile){
        this.setItems(context.getMethods());

        settings = new SettingsView(context, this, subBorderPane, openedFile);

        EnhanceMethodListView dis = this;

        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getClickCount()==2){
                    settings.setEnhanceMethod((ImageEnhanceMethod) dis.getSelectionModel().getSelectedItem());
                    subBorderPane.setCenter(settings);
                }
            }
        });
        this.setCellFactory(new Callback<ListView<ImageEnhanceMethod>, ListCell<ImageEnhanceMethod>>() {
            @Override
            public ListCell<ImageEnhanceMethod> call(ListView<ImageEnhanceMethod> imageEnhanceMethodListView) {
                return new ListCell<>(){
                    @Override
                    protected void updateItem(ImageEnhanceMethod imageEnhanceMethod, boolean b) {
                        super.updateItem(imageEnhanceMethod, b);
                        if(imageEnhanceMethod!=null){

                            VBox methodWindow = new VBox();
                            Label name = new Label();
                            name.textProperty().bind(context.getTextName(imageEnhanceMethod.getName()));

                            name.setStyle(name.getStyle()+"; -fx-font-weight: bold; -fx-font-family: Arial; -fx-font-size: 20;");
                            Label description = new Label();
                            description.textProperty().bind(context.getTextName(imageEnhanceMethod.getDescription()));
                            methodWindow.getChildren().addAll(name, description);

                            for (String beforePath: imageEnhanceMethod.getExamples().keySet()) {
                                //ImageView before = new ImageView(new Image(beforePath));
                                //ImageView after = new ImageView(new Image(imageEnhanceMethod.getExamples().get(beforePath)));
                                //methodWindow.getChildren().addAll(before, after);
                            }
                            try {
                                methodWindow.getChildren().add(imageEnhanceMethod.getSettingWindow().getHBox());
                            } catch(Exception e) {}
                            setGraphic(methodWindow);
                        }
                    }
                };

            }
        });
        subBorderPane.setCenter(this);
    }

}
