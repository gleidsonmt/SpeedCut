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

import animatefx.animation.BounceInDown;
import animatefx.animation.BounceOutUp;
import animatefx.animation.FadeOut;
import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.speedcut.core.app.exceptions.ControllerCastException;
import io.github.gleidsonmt.speedcut.core.app.view.ActionViewController;
import io.github.gleidsonmt.speedcut.core.app.view.IView;
import io.github.gleidsonmt.speedcut.core.app.view.WindowDecorator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
                                CornerRadii.EMPTY,
                                Insets.EMPTY))
        );
    }

    public void openDrawerLeft(Node content) {
        this.content = content;

        if (!root.getChildren().contains(content))
            root.getChildren().add(content);

        content.setStyle("-fx-background-color : -background-color;");
        setOnMouseClicked(eventCloseDrawer);
        open(Pos.CENTER_LEFT);

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
                                content.translateXProperty(), window.getWidth() - drawerSize
                        ))
                );
            }
        }



        timeline.setOnFinished(e -> {
            window.lockControls(true);
//                content.toFront();
        });

        timeline.play();
    }

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
        animation.setSpeed(1.2);

        animation.getTimeline().setOnFinished(event1 -> {

            content.requestFocus();
            GNTextBox searchBox = (GNTextBox) content.lookup("#search");
            searchBox.requestFocus();

            controller.onEnter();
//            window.getViews().setCurrent(this.view);

            content.setOpacity(1);


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
}
