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

import animatefx.animation.*;
import io.github.gleidsonmt.gncontrols.GNButton;
import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.gncontrols.options.GNButtonType;
import io.github.gleidsonmt.speedcut.core.app.exceptions.ControllerCastException;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.Alert;
import io.github.gleidsonmt.speedcut.core.app.layout.options.AlertType;
import io.github.gleidsonmt.speedcut.core.app.layout.util.AlignmentUtil;
import io.github.gleidsonmt.speedcut.core.app.view.ActionViewController;
import io.github.gleidsonmt.speedcut.core.app.view.IView;
import io.github.gleidsonmt.speedcut.core.app.view.WindowDecorator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class Wrapper extends StackPane {

    private final WindowDecorator window;

    private final Timeline timeline = new Timeline();
    private final AnchorPane root = new AnchorPane();

    private final double drawerSize;

    private Node content; // usado como drawer, popup and alerts
    private IView view; // usado para acionar acaoes e pegar views
    private Label snackBar;
    private Pos alignment; // alinha o container do popup

    public Wrapper( WindowDecorator window, double drawerSize) {
        this.window = window;
        this.drawerSize = drawerSize;
        getChildren().add(root);

        setBackground(
                new Background(
                        new BackgroundFill(
                                Color.gray(0.5, 0.5),
//                                Color.WHITE,
                                CornerRadii.EMPTY,
                                Insets.EMPTY))
        );

//        root.setStyle("-fx-background-color : white;");
    }

    private void addContent(Node node) {
        root.getChildren().clear();
        root.getChildren().add(node);
    }

    /************************************************************************************
     *
     *           Drawer
     *
     *************************************************************************************/

    public void openDrawerLeft(Node content) {
        this.content = content;

        if (!root.getChildren().contains(content))
            root.getChildren().add(content);

        content.setStyle("-fx-background-color : -background-color;");
        setOnMouseClicked(eventCloseDrawer);
        open(Pos.CENTER_LEFT);

    }

    public void openDrawerRight(String viewName) {
        openDrawerRight(window.getViews().getRootFrom(viewName));
    }

    public void openDrawerRight(Node content) {
        this.content = content;

        if (!root.getChildren().contains(content))
            root.getChildren().add(content);

        content.setStyle("-fx-background-color : -background-color;");
        setOnMouseClicked(eventCloseDrawer);
        open(Pos.CENTER_RIGHT);

    }

    private void open(Pos pos) {
        this.alignment = pos;

        toFront();

        switch (pos) {
            case CENTER_LEFT -> {

                AlignmentUtil.centerLeft(content);
                content.setTranslateX(-(drawerSize));

                timeline.getKeyFrames().setAll(
                        new KeyFrame(Duration.ZERO, new KeyValue(
                                content.translateXProperty(), content.getTranslateX()
                        )),
                        new KeyFrame(Duration.millis(200), new KeyValue(
                                content.translateXProperty(), 0
                        ))
                );
            }

            case CENTER_RIGHT -> {

                AlignmentUtil.centerRight(content);
//                content.setTranslateX(window.getWidth());

                timeline.getKeyFrames().setAll(
                        new KeyFrame(Duration.ZERO, new KeyValue(
                                content.translateXProperty(), window.getWidth()
                        )),
                        new KeyFrame(Duration.millis(200), new KeyValue(
                                content.translateXProperty(), window.getWidth() - 600
                        ))
                );
            }
        }

        timeline.setOnFinished(e -> {
            window.lockControls(true);
                content.toFront();
        });

        timeline.play();
    }

    /************************************************************************************
     *
     *           PoPup
     *
     *************************************************************************************/


    public void openPopup(String viewName, double width, double height) {
        view = window.getViews().get(viewName);
        ActionViewController controller = null;
        try {
            controller = this.view.getController();
        } catch (ControllerCastException e) {
            e.printStackTrace();
        }
        openPopup(view.getRoot(), width, height, controller);
    }

    private void openPopup(Parent content, double width, double height,  ActionViewController controller)  {

        this.content = content;
        toFront();

        if (!root.getChildren().contains(content))
            root.getChildren().add(content);

        double padding = 0;

        if (window.getHeight() - 20 < height) {
            padding = 40;
            ((Region) content).setPrefSize(width, window.getHeight() - (padding *2));
        } else {
            ((Region) content).setPrefSize(width, height);
            padding = 0;
        }

        AnchorPane.setLeftAnchor(content, (root.getWidth() /2) - (width/2));
        AnchorPane.setTopAnchor(content, (root.getHeight() /2) - (height/2) + padding);

        BounceInDown animation = new BounceInDown(content);
//        animation.setSpeed(1.2);

        animation.getTimeline().setOnFinished(event1 -> {
            content.requestFocus();
            controller.onEnter();
//            window.getViews().setCurrent(this.view);
            content.setOpacity(1);
            setOpacity(1);
        });

        animation.play();

        window.lockControls();
    }

    public void closePopup() {
        BounceOutUp animation = new BounceOutUp(this.content);
        animation.setSpeed(1.2);
        animation.getTimeline().setOnFinished(event -> {
            window.unLockControls();
            AnchorPane.clearConstraints(this.content);
            content.setTranslateX(0);
            content.setTranslateY(0);

            try {
                this.view.getController().onExit();
            } catch (ControllerCastException e) {
                e.printStackTrace();
            }

            toBack();
            root.getChildren().clear();
            this.content.setOpacity(1);
        });
        animation.play();

    }



    private final EventHandler<MouseEvent> eventCloseDrawer = new EventHandler<>() {
        @Override
        public void handle(MouseEvent event) {
            System.out.println("alignment = " + alignment);
            switch (alignment) {
                case CENTER_LEFT -> {
                    timeline.getKeyFrames().setAll(
                            new KeyFrame(Duration.ZERO, new KeyValue(
                                    content.translateXProperty(), content.getTranslateX()
                            )),
                            new KeyFrame(Duration.millis(200), new KeyValue(
                                    content.translateXProperty(), -(drawerSize)
                            ))
                    );
                }
                case CENTER_RIGHT -> {
                    timeline.getKeyFrames().setAll(
                            new KeyFrame(Duration.ZERO, new KeyValue(
                                    content.translateXProperty(), content.getTranslateX()
                            )),
                            new KeyFrame(Duration.millis(200), new KeyValue(
                                    content.translateXProperty(), window.getWidth()
                            ))
                    );
                }
            }

            timeline.setOnFinished(e -> {
                window.getControls().forEach(c -> c.setVisible(true));
                window.lockControls(false);
                content.setTranslateX(0);
                content.setTranslateY(0);
                setOnMouseClicked(null);
                root.getChildren().clear();
                toBack();
            });
            timeline.play();
        }
    };

/************************************************************************************
 *
                    Alert
 *
 *************************************************************************************/

    private Alert alert = new Alert(this);

    public Alert createAlert() {

//        VBox box = new VBox();
//        StackPane content = new StackPane(box);
//        content.setStyle("-fx-background-color : -background-color;" +
//                "-fx-background-radius : 10px;");
//
//
        alert.setPrefSize(400, 300);
//
////        btnOk.setCancelButton(true);
//
//        box.getChildren().setAll(
//                createAlertHeader(alertType),
//                createContent("Title", "Message"),
//                createActions(alertType)
//        );
//

        addContent(alert);
        AlignmentUtil.topLeft(alert,
                (root.getHeight() /2) - (alert.getPrefHeight() / 2),
                (root.getWidth() /2) - (alert.getPrefWidth() / 2)
        );
//
//        toFront();
//
//        new BounceIn(root).play();
        return alert;
    }

    private VBox createAlertHeader(AlertType type){
        VBox header = new VBox();
        header.setStyle("-fx-background-radius : 10px;");
        header.setMinHeight(120);
        header.setAlignment(Pos.CENTER);

        ImageView icon = null;
        String color = null;

        String path = "/io.github.gleidsonmt.speedcut.core.app/theme/img/";

        switch (type) {
            case INFO -> {
                color = "-info";
                icon = new ImageView(Objects.requireNonNull(getClass()
                        .getResource(path + "info_48dp.png")).toExternalForm());
            }
            case WARNING -> {
                color = "-warning";
                icon = new ImageView(Objects.requireNonNull(getClass()
                        .getResource(path + "warning_48dp.png")).toExternalForm());
            }
            case ERROR -> {
                color = "-error";
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

        header.setStyle("-fx-background-color : " + color + "; -fx-border-radius : 10px;");

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

    private GNButton btnConfirm = new GNButton("OK");
    private GNButton btnCancel = new GNButton();

    private HBox createActions(AlertType alertType, GNButton... actions){

        HBox _actions = new HBox();
        _actions.setMinSize(100, 73);
        _actions.setAlignment(Pos.CENTER);
        VBox.setMargin(_actions, new Insets(10, 10, 10, 10));
        _actions.setSpacing(5D);

        _actions.getChildren().setAll(actions);

        switch (alertType) {
            case ERROR -> {

                _actions.getChildren().forEach(c -> c.getStyleClass().addAll("btn-out", "btn-danger"));
            }
            case WARNING -> {

                _actions.getChildren().stream().map(m -> (GNButton) m).forEach(c -> {
                    c.setButtonType(GNButtonType.SEMI_ROUNDED);
                    c.setStyle("-base : -warning;");
                    c.getStyleClass().addAll("btn-flat", "btn-out", "btn-warning");
                });
            }
            case INFO -> _actions.getChildren().forEach(c -> c.getStyleClass().addAll("btn-out", "btn-info"));
            case SUCCESS -> {
                if (!_actions.getChildren().contains(btnConfirm)) {
                    _actions.getChildren().add(btnConfirm);
                    btnConfirm.setDefaultButton(true);
                }
                _actions.getChildren().stream().map(m -> (GNButton) m).forEach(c -> {
                    c.setButtonType(GNButtonType.SEMI_ROUNDED);
                    c.setStyle("-base : -mint;");
                    c.getStyleClass().addAll("btn-flat", "btn-out", "btn-mint");
                });
            }
        }

        _actions.getChildren().stream().filter(p -> p instanceof GNButton).map( c -> (GNButton) c).forEach(b -> b.setPrefWidth(100));

        _actions.getChildren().forEach( e -> e.addEventFilter(ActionEvent.ACTION, event -> {
            BounceOut bounceOut = new BounceOut(root);
//            bounceOut.getTimeline().setOnFinished(ev -> close());
            bounceOut.play();
            bounceOut.getTimeline().setOnFinished(ev -> {
                toBack();
            });
        }));




//        _actions.getChildren().forEach( c -> c.setEffect(colorAdjust));

        _actions.getChildren().stream().filter(e -> e instanceof GNButton).forEach(e -> ((Button) e).setPrefWidth(80));
        return _actions;
    }

    public void show() {
        show(new FadeIn(root));
    }

    public void show(AnimationFX animation) {
        toFront();
        animation.play();
        root.requestFocus();
    }

    public void close() {
        close(new FadeOut(root));
    }

    public void close(AnimationFX animation) {
        close(animation, 1.0);
    }
    public void close(AnimationFX animation, double speed) {
        animation.getTimeline().setOnFinished(e -> toBack());
        animation.play();
    }
}
