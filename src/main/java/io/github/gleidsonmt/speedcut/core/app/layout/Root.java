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

package io.github.gleidsonmt.speedcut.core.app.layout;

import io.github.gleidsonmt.gncontrols.GNFloatingButton;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.core.app.view.WindowDecorator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.skin.ColorPickerSkin;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class Root extends StackPane {

    private final WindowDecorator window;
    private final Layout layout = new Layout();
    private final Wrapper wrapper;

    private final GNFloatingButton hamburger   = new GNFloatingButton();

    public Root(WindowDecorator window) {
        this.window = window;
        wrapper = new Wrapper(window, 250);
        getChildren().add(wrapper);
        getChildren().add(layout);

        getStyleClass().addAll("border", "border-b-1");
        hamburger.getStyleClass().add("hamburger");
        hamburger.setMinSize(35, 35);
        hamburger.setMaxSize(35, 35);
        hamburger.setGraphic(new IconContainer(Icons.HAMBURGER));


        hamburger.setOnAction(event -> {
            wrapper.toFront();
            wrapper.openDrawerLeft(getLayout().getOldLeft());
            window.getControls().forEach(c -> c.setVisible(false));
        });

        window.widthProperty().addListener((observable, oldValue, newValue) -> {
            double drawerWidth = 250;
            double _new = newValue.doubleValue();
            if ((_new/ 4) < drawerWidth) {
                layout.setLeft(null);
                window.addControl(0, hamburger);

            } else {
                window.removeControl(hamburger);
                layout.setLeft(getLayout().getOldLeft());
            }
        });
    }

    public enum  SnackType {
        DONE, ERROR, INFO, WARNING
    }

    public void openSnackBar(String message) {
        openSnackBar(message, false);
    }

    public void openSnackBar(String message, boolean top) {
        openSnackBar(message, top, SnackType.DONE);
    }

    public void openSnackBar(String message, SnackType type) {
        openSnackBar(message, false, type);
    }

    public void openSnackBar(String message, boolean top, SnackType type) {

        Label snack = new Label(message);
        snack.setPrefHeight(30);
        snack.setMaxHeight(30);

        IconContainer iconContainer = new IconContainer();
        VBox box = new VBox(iconContainer);

        box.setPadding(new Insets(5));
        box.setMinSize(25,25);
        box.setMaxSize(25,25);
        box.setAlignment(Pos.CENTER);

        String color = "-mint";
        String foreground = "white";

        switch (type) {
            case DONE -> {
                color = "-mint";
                iconContainer.setContent(Icons.DONE);
            }
            case ERROR -> {
                color = "-grapefruit";
                iconContainer.setContent(Icons.ERROR);
//                iconContainer.setScaleX(1.2);
//                iconContainer.setScaleY(1.2);
            }
        }

        box.setStyle("-fx-background-color : " + foreground + "; -fx-background-radius : 100;");

        iconContainer.setStyle("-fx-fill : " + color + ";");
        snack.setGraphic(box);
        snack.setStyle("-fx-background-color : " + color + ";" +
                "-fx-padding : 10; -fx-background-radius : 5; " +
                "-fx-text-fill : white;" +
                "-fx-font-weight : bold;");

        Timeline timeline = new Timeline();

        if (top) setAlignment(Pos.TOP_CENTER);
        else setAlignment(Pos.BOTTOM_CENTER);

        double trlY = !top ? snack.getPrefHeight()  : (snack.getPrefHeight() * -1 ) ; // 40 e o tamanho da barra

        if (!getChildren().contains(snack)) {
            getChildren().add(snack);
            snack.setTranslateY(trlY);
        }

        timeline.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(
                        snack.translateYProperty(), snack.getTranslateY()
                )),
                new KeyFrame(Duration.millis(200), new KeyValue(
                        snack.translateYProperty(), top ? 40 : 0
                ))
        );

        timeline.play();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeline.getKeyFrames().setAll(
                        new KeyFrame(Duration.ZERO, new KeyValue(
                                snack.translateYProperty(), snack.getTranslateY()
                        )),
                        new KeyFrame(Duration.millis(200), new KeyValue(
                                snack.translateYProperty(),  top ? -40 - snack.getHeight() : 0
                        ))
                );
                timeline.play();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 1000);

    }

    public Wrapper getWrapper() {
        return wrapper;
    }

    public CenterLayout getCenterLayout() {
        return layout.getCenterLayout();
    }

    public Layout getLayout() {
        return layout;
    }

}
