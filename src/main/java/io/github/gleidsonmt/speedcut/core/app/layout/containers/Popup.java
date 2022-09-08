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
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IContext;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import org.girod.javafx.svgimage.SVGImage;
import org.jetbrains.annotations.NotNull;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/03/2022
 * Creates Popups in application.
 */
public class Popup implements Context {

    private Pos pos = Pos.CENTER;

    private Region      content;
    private IView       view;
    private PopupView   controller;
    private double      width;
    private double      height;

    private final   Wrapper wrapper;

    private Insets  insets = new Insets(0);

    private AnimationFX animationFX;
    private PopupAnimation animation = PopupAnimation.PULSE;

    private EventHandler<ActionEvent>   onEnter;
    private EventHandler<ActionEvent>   onExit;

    public Popup(Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public Popup content(@NotNull IView _view) {

        this.view = _view;
        this.content = (Pane) _view.getRoot();

        if (_view.getController() instanceof PopupView) this.controller = (PopupView) _view.getController();

        return this;
    }


    @Deprecated
    public Popup size(double width, double height) {
        this.width = width; this.height = height;
        return this;
    }

    public Popup alignment(Pos pos) {
        alignment(pos, new Insets(0));
        return this;
    }

    public Popup alignment(@NotNull String pos) {
        alignment(Pos.valueOf(pos.toUpperCase()), new Insets(0));
        return this;
    }

    public Popup alignment(Pos pos, Insets insets) {
        this.pos = pos;
        this.insets = insets;
        return this;
    }

    public Popup animation(PopupAnimation animation) {
        this.animation = animation;
        return this;
    }

    public Popup animation(@NotNull String animation) {
        animation(PopupAnimation.valueOf(animation.toUpperCase()));
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



//        if (!wrapper.getRoot().getChildren().contains(this.content))
//            wrapper.getRoot().getChildren().add(this.content);

        wrapper.show();

        resetSizes(this.content);

        wrapper.setAligment(pos);
        wrapper.addContent(this.content);


        if (height <= 0) height = this.content.getBoundsInLocal().getHeight();
        if (width  <= 0) width  = this.content.getBoundsInLocal().getWidth();


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

                // In test
                if (this.controller != null) {

                    if (height > (context.getDecorator().getHeight() -40) && (context.getDecorator().getWidth() - 40) < width) {
                        this.controller.updateMode(PopupLayout.SCREEN);
                    } else if (height > (context.getDecorator().getHeight()-40)) {
                        this.controller.updateMode(PopupLayout.WIDE);
                    } else{
                        this.controller.updateMode(PopupLayout.DEFAULT);
                    }

                }
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

    private final EventHandler<ActionEvent> popupOpen = event -> {

    };

    private final EventHandler<ActionEvent> popupClose = new EventHandler<>() {
        @Override
        public void handle(ActionEvent event) {
            wrapper.clear();
        }
    };

    private void resetSizes(@NotNull Region node) {
        node.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        node.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        node.setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    }
}
