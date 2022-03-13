/*
 *    Copyright (C) Gleidson Neves da Silveira
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.gleidsonmt.speedcut.core.app.layout.containers;

import io.github.gleidsonmt.gncontrols.GNButton;
import io.github.gleidsonmt.gncontrols.GNButtonHover;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.gncontrols.options.GNButtonType;
import io.github.gleidsonmt.gncontrols.options.GNHoverType;
import io.github.gleidsonmt.speedcut.core.app.layout.Root;
import io.github.gleidsonmt.speedcut.core.app.layout.options.AlertType;
import io.github.gleidsonmt.speedcut.core.app.layout.options.SnackBarType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/03/2022
 */
public class SnackBar  {

    private final Timeline timeline = new Timeline();

    private final Root  root;
    private final Label snack = new Label("Snack Bar");
    private final GNButtonHover labelAction = new GNButtonHover("Desfazer");

    private final IconContainer iconContainer = new IconContainer();
    private final VBox box = new VBox(iconContainer);

    private SnackBarType type = SnackBarType.DONE;

    public SnackBar(Root root) {
        this.root = root;

        snack.setPrefHeight(30);
        snack.setMaxHeight(30);
        snack.setGraphicTextGap(10D);
//        snack.getStyleClass().add("depth-2");

        box.setPadding(new Insets(5));
        box.setMinSize(25,25);
        box.setMaxSize(25,25);
        box.setAlignment(Pos.CENTER);

        labelAction.setHoverType(GNHoverType.SWIPE);

        labelAction.setButtonType(GNButtonType.SEMI_ROUNDED);
        labelAction.setPrefSize(80,30);

        labelAction.setCursor(Cursor.HAND);

        labelAction.getStyleClass().add("snack-button");
        snack.getStyleClass().add("snack-bar");
        labelAction.setStyle("-base : -mint;");

        snack.setContentDisplay(ContentDisplay.RIGHT);
        snack.setGraphic(labelAction);

    }

    public SnackBar type(SnackBarType type) {
        this.type = type;
        return this;
    }

    public SnackBar message(String message) {
        snack.setText(message);
        return this;
    }



    public SnackBar type(String snackType) {
        this.type = SnackBarType.valueOf(snackType.toUpperCase());
        return this;
    }

    public void showOnTop() {
        show(true);
    }

    public void show() {
        show(false);
    }

    private void show(boolean top) {

        String color = "-mint";
        String foreground = "white";
//
        iconContainer.setScaleX(0.8);
        iconContainer.setScaleY(0.8);

        switch (type) {
            case DONE -> {
                color = "-mint";
                iconContainer.setContent(Icons.DONE);
            }
            case WARNING -> {
                color = "-amber";
                iconContainer.setContent(Icons.WARNING);
            }
//            case ERROR -> {
//                color = "-grapefruit";
//                iconContainer.setContent(Icons.ERROR);
////                iconContainer.setScaleX(1.2);
////                iconContainer.setScaleY(1.2);
//            }
        }


        if (top) root.setAlignment(Pos.TOP_CENTER);
        else root.setAlignment(Pos.BOTTOM_CENTER);

        double trlY = !top ? snack.getPrefHeight()  : (snack.getPrefHeight() * -1 ) ; // 40 e o tamanho da barra

        if (!root.getChildren().contains(snack)) {
            root.getChildren().add(snack);
            snack.setTranslateY(trlY);
        }

        timeline.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(
                        snack.translateYProperty(), snack.getTranslateY()
                )),
                new KeyFrame(Duration.millis(200), new KeyValue(
                        snack.translateYProperty(), 0
                ))
        );

//        timeline.setOnFinished(null);

        box.setStyle("-fx-background-color : " + foreground + "; -fx-background-radius : 100;");
        iconContainer.setStyle("-fx-fill : " + color + ";");
//        setGraphic(box);
        snack.setStyle("-fx-background-color : " + color + ";" +
                "-fx-padding : 10; -fx-background-radius : 5; " +
                "-fx-text-fill : white; -fx-font-weight : bold;" );

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeline.getKeyFrames().setAll(
                        new KeyFrame(Duration.ZERO, new KeyValue(
                                snack.translateYProperty(), snack.getTranslateY()
                        )),
                        new KeyFrame(Duration.millis(200), new KeyValue(
                                snack.translateYProperty(),  top ? -40 -snack.getHeight() : snack.getHeight()
                        ))
                );
                timeline.play();

                timeline.setOnFinished(e -> {
                    root.getChildren().remove(snack);
                });

            }
        };

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                root.getChildren().remove(snack);
            }
        }, 3000);

        timer.schedule(task, 3000);

        timeline.play();
    }

    public SnackBar onFinished(EventHandler<ActionEvent> event) {
        timeline.setOnFinished(event);
        return this;
    }

    public SnackBar onAction(EventHandler<ActionEvent> eventHandler) {
        labelAction.addEventFilter(ActionEvent.ACTION, eventHandler);
        return this;
    }
}
