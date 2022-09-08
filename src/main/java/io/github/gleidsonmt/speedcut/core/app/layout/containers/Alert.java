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

import animatefx.animation.AnimationFX;
import animatefx.animation.BounceIn;
import animatefx.animation.BounceOut;
import animatefx.animation.Pulse;
import io.github.gleidsonmt.gncontrols.GNButton;
import io.github.gleidsonmt.gncontrols.options.GNButtonType;
import io.github.gleidsonmt.speedcut.core.app.layout.options.AlertType;
import io.github.gleidsonmt.speedcut.core.app.layout.util.AlignmentUtil;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/03/2022
 */
public class Alert implements Context {

    private final Wrapper wrapper;

    private final VBox      content     = new VBox();
    private final StackPane container   = new StackPane();

    private Pos     pos = Pos.CENTER;
    private double  width = 400;
    private double  height = 300;

    private AnimationFX animationFX;
    private PopupAnimation animation = PopupAnimation.PULSE;

    private String      message = "Alert Message";
    private String      title   = "Title Message";
    private AlertType   type    = AlertType.INFO;

    private EventHandler<ActionEvent>   onEnter;
    private EventHandler<ActionEvent>   onExit;

    private final GNButton btnConfirm = new GNButton("OK");
    private final GNButton btnCancel = new GNButton("Cancel");

    private Insets   insets = new Insets(0);

    public Alert(@NotNull Wrapper wrapper) {

        this.wrapper = wrapper;
        container.getChildren().add(content);
        container.setStyle("-fx-background-color : -background-color; -fx-background-radius : 10px;");
        content.setStyle("-fx-background-color : -background-color; -fx-background-radius : 10px;");

        wrapper.setAligment(Pos.CENTER);
    }

    public Alert alertType(AlertType alertType) {
        this.type = alertType;
        return this;
    }

    public Alert alertType(@NotNull String alertType) {
        this.type = AlertType.valueOf(alertType.toUpperCase());
        return this;
    }

    public Alert message(String message) {
        this.message = message;
        return this;
    }

    public Alert title(String title) {
        this.title = title;
        return this;
    }

    public void show() {
        content.getChildren().setAll(
            createAlertHeader(type),
            createContent(title, message),
            createActions(type)
        );

        wrapper.show();

        wrapper.addContent(this.content);
        this.content.setPrefSize(width, height);

        switch (animation) {
            case PULSE -> animationFX = new Pulse(content);
            case BOUNCE_IN -> {
                animationFX = new BounceIn(content);
                animationFX.setSpeed(1.5);
            }
        }

        if (animation != null) {
            animationFX.getTimeline().setOnFinished(event -> {
                if (onEnter != null) onEnter.handle(new ActionEvent());
            });
            animationFX.play();
        }
    }


    private @NotNull VBox createAlertHeader(@NotNull AlertType type){
        VBox header = new VBox();
        header.setMinHeight(120);
        header.setAlignment(Pos.CENTER);

        ImageView icon = null;
        String color = null;

        String path = "/io.github.gleidsonmt.speedcut.core.app/theme/img/";
//        String path = context.getPaths().getCore()

        switch (type) {
            case INFO -> {
                color = "-info";
                icon = new ImageView(Objects.requireNonNull(getClass()
                        .getResource(path + "info_48dp.png")).toExternalForm());
            }
            case WARNING -> {
                color = "-amber";
                icon = new ImageView(Objects.requireNonNull(getClass()
                        .getResource(path + "warning_48dp.png")).toExternalForm());
            }
            case ERROR -> {
                color = "-grapefruit";
                icon = new ImageView(Objects.requireNonNull(getClass()
                        .getResource(path + "error_48dp.png")).toExternalForm());
            }
            case SUCCESS -> {
                color = "-mint";
                icon = new ImageView(Objects.requireNonNull(getClass()
                        .getResource(path + "done_48dp.png")).toExternalForm());
            }
        }

//        header.setEffect(colorAdjust);

//        header.setBackground(new Background(new BackgroundFill(color, new CornerRadii(
//                10,false), Insets.EMPTY)));

        header.setStyle("-fx-background-radius : 10px; -fx-background-color : " + color + "; -fx-border-radius : 10px;");

        icon.setPreserveRatio(true);
        icon.setSmooth(true);
        icon.setFitWidth(151);
        icon.setFitHeight(78);

        header.getChildren().add(icon);
        return header;
    }

    private static VBox  createContent(String title, String message){
        VBox container = new VBox();
        container.setAlignment(Pos.TOP_CENTER);
        container.setSpacing(20D);

        VBox.setVgrow(container, Priority.ALWAYS);

        VBox.setMargin(container, new Insets(10,0,0,0));

        Label lblTitle = new Label(title);
        lblTitle.getStyleClass().add("h2");
        lblTitle.setStyle("-fx-text-fill : -text-color; -fx-font-weight : bold;");

        Label text = new Label();
        text.setWrapText(true);
        text.setText(message);
        text.setAlignment(Pos.CENTER);
        lblTitle.setStyle("-fx-text-fill : -text-color;");

        container.getChildren().addAll(lblTitle, text);

        return container;
    }



    private HBox createActions(AlertType alertType, GNButton... actions){

        HBox _actions = new HBox();
        _actions.setMinSize(100, 73);
        _actions.setAlignment(Pos.CENTER);
        VBox.setMargin(_actions, new Insets(10, 10, 10, 10));
        _actions.setSpacing(5D);

        _actions.getChildren().setAll(actions);

        String color = "-info";

        switch (alertType) {
            case ERROR -> color = "-grapefruit";
            case WARNING -> color = "-amber";
            case INFO -> color = "-info";
            case SUCCESS ->  color = "-mint";
        }

        String finalColor = color;

        if (!_actions.getChildren().contains(btnConfirm)) {
            _actions.getChildren().add(btnConfirm);
            btnConfirm.setDefaultButton(true);
        }

        _actions.getChildren().stream().map(m -> (GNButton) m).forEach(c -> {
            c.setButtonType(GNButtonType.SEMI_ROUNDED);
            c.setStyle("-base : " + finalColor + ";");
            c.getStyleClass().addAll("btn-flat", "btn-out", "btn" + finalColor);
            c.setPrefWidth(80);
        });

        _actions.getChildren().forEach(
                e -> e.addEventFilter(ActionEvent.ACTION,
                event -> {
//                    wrapper.close(new BounceOut(container));
                    close();
                }));

        return _actions;
    }

    private void close() {

        switch (animation) {
            case PULSE -> animationFX = new Pulse(content);
            case BOUNCE_IN -> {
                animationFX = new BounceOut(content);
                animationFX.setSpeed(1.5);
            }
        }

        if (animation != null) {
            animationFX.getTimeline().setOnFinished(event -> {
                if (onExit != null) onExit.handle(new ActionEvent());
                wrapper.hide();
            });

            animationFX.play();
        }
    }
}
