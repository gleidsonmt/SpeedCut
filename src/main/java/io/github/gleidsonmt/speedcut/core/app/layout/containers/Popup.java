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
import io.github.gleidsonmt.speedcut.core.app.layout.util.AlignmentUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/03/2022
 */
public class Popup {

    private Pos     pos = Pos.CENTER;

    private Node    content;
    private double  width;
    private double  height;

    private final   Wrapper wrapper;

    private Insets  insets = new Insets(0);

    private AnimationFX animationFX;
    private Animations animation = Animations.PULSE;

    private EventHandler<ActionEvent>   onEnter;
    private EventHandler<ActionEvent>   onExit;

    public enum Animations {
        PULSE, BOUNCE_IN,
    }

    public Popup(Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public Popup content(Node content) {
        this.content = content;
        return this;
    }

    public Popup size(double width, double height) {
        this.width = width; this.height = height;
        return this;
    }

    public Popup alignment(Pos pos) {
        alignment(pos, new Insets(0));
        return this;
    }

    public Popup alignment(String pos) {
        alignment(Pos.valueOf(pos.toUpperCase()), new Insets(0));
        return this;
    }

    public Popup alignment(Pos pos, Insets insets) {
        this.pos = pos;
        this.insets = insets;
        return this;
    }

    public Popup animation(Animations animation) {
        this.animation = animation;
        return this;
    }

    public Popup animation(String animation) {
        animation(Animations.valueOf(animation.toUpperCase()));
        return this;
    }

    public Popup onEnter(EventHandler<ActionEvent> eventHandler) {
        this.onEnter = eventHandler;
        return this;
    }

    public Popup onExit(EventHandler<ActionEvent> eventHandler) {
        this.onExit = eventHandler;
        return this;
    }

    public void show() {

        wrapper.show();

//        if (!wrapper.getRoot().getChildren().contains(this.content))
//            wrapper.getRoot().getChildren().add(this.content);

        wrapper.addContent(this.content);

        ((Pane) this.content).setPrefSize(width, height);
        ((Pane) this.content).setMaxSize(width, height);
        ((Pane) this.content).setMinSize(width, height);

        switch (pos) {
            case TOP_LEFT, BASELINE_LEFT -> AlignmentUtil.topLeft(content, this.insets);
            case TOP_CENTER, BASELINE_CENTER -> AlignmentUtil.topCenter(content, this.insets);
            case TOP_RIGHT, BASELINE_RIGHT -> AlignmentUtil.topRight(content, this.insets);
            case CENTER_RIGHT -> AlignmentUtil.centerRight(content, this.insets);
            case BOTTOM_RIGHT -> AlignmentUtil.bottomRight(content, this.insets);
            case BOTTOM_CENTER -> AlignmentUtil.bottomCenter(content, this.insets);
            case BOTTOM_LEFT -> AlignmentUtil.bottomLeft(content, this.insets);
            case CENTER_LEFT -> AlignmentUtil.centerLeft(content, this.insets);

            case CENTER -> AlignmentUtil.topLeft(content, new Insets(
                    (wrapper.getHeight() / 2) - (height / 2),
                    0, 0,
                    (wrapper.getWidth() / 2) - (width / 2)
            ));
            default -> throw new IllegalStateException("Unexpected value: " + pos);
        }


        switch (animation) {
            case PULSE -> {
                animationFX = new Pulse(content);
                animationFX.setSpeed(2.5);
            }
            case BOUNCE_IN -> {
                animationFX = new BounceIn(content);
                animationFX.setSpeed(1.5);
            }
        }

        if (animation != null) {

            if (onEnter != null)
                onEnter.handle(new ActionEvent());

            animationFX.getTimeline().setOnFinished(event -> {
                popupOpen.handle(new ActionEvent());
            });

            animationFX.play();
        }
    }

    public void close() {

        switch (animation) {
            case PULSE -> {
                animationFX = new Pulse(content);
                animationFX.setSpeed(2.5);
            }
            case BOUNCE_IN -> {
                animationFX = new BounceOut(content);
                animationFX.setSpeed(1.5);
            }
        }

        if (animation != null) {
            animationFX.getTimeline().setOnFinished(event -> {
                if (onExit != null)
                    onExit.handle(new ActionEvent());

                popupClose.handle(new ActionEvent());
                wrapper.hide();
            });
            animationFX.play();
        }
    }

    private final EventHandler<ActionEvent> popupOpen = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {

        }
    };

    private final EventHandler<ActionEvent> popupClose = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            wrapper.clear();
        }
    };
}
