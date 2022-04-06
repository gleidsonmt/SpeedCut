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

import io.github.gleidsonmt.speedcut.core.app.layout.util.AlignmentUtil;
import io.github.gleidsonmt.speedcut.core.app.view.IManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  29/03/2022
 */
@SuppressWarnings("unused")
public class Drawer implements IManager {

    private final StackPane     container   = new StackPane();
    private final VBox          content     = new VBox();
    private final Timeline      timeline    = new Timeline();

    private final Wrapper wrapper;

    private DrawerSide  side    = DrawerSide.LEFT;
    private double      width   = 250;

    private Parent  root;
    private boolean show;

    public enum DrawerSide { RIGHT, LEFT }

    private final EventHandler<MouseEvent> eventClose = event -> close();

    public Drawer (Wrapper wrapper) {
        this.wrapper = wrapper;
        container.getChildren().add(content);
        container.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.WHITE,
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )
                )
        );
    }

    public Drawer content (Node content) {
        container.getChildren().clear();
        container.getChildren().add(content);
        return this;
    }

    public Drawer width (double width) {
        this.width = width;
        return this;
    }

    public Drawer side (DrawerSide side) {
        this.side = side;
        return this;
    }

    public Drawer side (String side) {
        this.side = DrawerSide.valueOf(side.toUpperCase());
        return this;
    }

    public double getWidth () {
        return this.width;
    }

    private void openFromLeft () {
        timeline.getKeyFrames().setAll(
            new KeyFrame(Duration.ZERO, new KeyValue(
                    container.translateXProperty(), -width
            )),
            new KeyFrame(Duration.millis(250), new KeyValue(
                    container.translateXProperty(), 0
            ))
        );

        timeline.setOnFinished(event ->
                root.setOnMouseClicked(eventClose));
    }

    private void openFromRight () {

        timeline.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(
                        container.translateXProperty(), window.getWidth()
                )),
                new KeyFrame(Duration.millis(250), new KeyValue(
                        container.translateXProperty(),   window.getWidth() - width
                ))
        );

        timeline.setOnFinished(event ->
                root.setOnMouseClicked(eventClose));
    }

    public boolean isShowing() {
        return show;
    }

    public void close() {
        switch (side) {
            case LEFT -> timeline.getKeyFrames().setAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(
                            container.translateXProperty(), container.getTranslateX()
                    )),
                    new KeyFrame(Duration.millis(250), new KeyValue(
                            container.translateXProperty(), -width
                    ))
            );
            case RIGHT -> timeline.getKeyFrames().setAll(
                    new KeyFrame(Duration.ZERO, new KeyValue(
                            container.translateXProperty(), container.getTranslateX()
                    )),
                    new KeyFrame(Duration.millis(200), new KeyValue(
                            container.translateXProperty(), window.getWidth()
                    ))
            );
        }

        timeline.setOnFinished(e -> {
            window.hideControls(true);
            window.lockControls(false);
            content.setTranslateX(0);
            content.setTranslateY(0);
            root.setOnMouseClicked(null);
            wrapper.hide();
        });
        this.show = false;
        timeline.play();
    }

    public void show () {

        container.setMaxWidth(width);
        wrapper.addContent(container);
        wrapper.show();

        root = container.getParent().getParent();

        if (this.side == DrawerSide.LEFT) {
            AlignmentUtil.centerLeft(container, new Insets(0));
            openFromLeft();
        } else if (this.side == DrawerSide.RIGHT) {
            AnchorPane.clearConstraints(container);
            AnchorPane.setTopAnchor(container, 0D);
            AnchorPane.setBottomAnchor(container, 0D);
            openFromRight();
        }
        this.show = true;
        timeline.play();
    }
}
