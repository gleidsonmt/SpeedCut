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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.controlsfx.control.PopOver;

import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  17/03/2022
 */
@SuppressWarnings("unused")
public class PopupOver {

    private final PopOver pop = new PopOver();

    private Node    content;
    private double  width;
    private double  height;

    private PopOver.ArrowLocation arrowLocation;

    private EventHandler<ActionEvent>   onEnter;
    private EventHandler<ActionEvent>   onExit;

    public PopupOver () {
        visibleArrow(false);
    }

    public PopupOver content (Node content) {
        this.content = content;
        return this;
    }

    public PopupOver size (double width, double height) {
        this.width = width; this.height = height;
        return this;
    }

    public PopupOver arrowLocation (PopOver.ArrowLocation arrowLocation) {
        this.arrowLocation = arrowLocation;
        return this;
    }

    public PopupOver arrowLocation (String location) {
        this.arrowLocation = PopOver.ArrowLocation.valueOf(location.toUpperCase());
        return this;
    }

    public PopupOver visibleArrow(boolean value) {
        String path = "/io.github.gleidsonmt.speedcut.core.app/";
        if (!value) {
            this.pop.getRoot().getStylesheets().addAll(
                    (Objects.requireNonNull(getClass().getResource(path + "theme/css/poplight.css")).toExternalForm())
            );
        } else {
            this.pop.getRoot().getStylesheets().removeAll(
                    (Objects.requireNonNull(getClass().getResource(path + "theme/css/poplight.css")).toExternalForm())
            );
        }
        return this;
    }

    public PopupOver onEnter(EventHandler<ActionEvent> eventHandler) {
        this.onEnter = eventHandler;
        return this;
    }

    public boolean isShowing() {
        return pop.isShowing();
    }

    public void close() {
        pop.hide();
    }

    public void hide() {
        pop.setAnimated(false);
        pop.hide();
    }

    public void show(Node owner) {

        pop.setWidth(width);
        pop.setHeight(height);

        if (arrowLocation != null)
            pop.setArrowLocation(arrowLocation);

        if (this.content instanceof Pane) {
            ((Pane) this.content).setPrefSize(width, height);
        }

        pop.setOnShowing(event -> onEnter.handle(new ActionEvent()));

        pop.setOnHidden(event -> {
            pop.setContentNode(null);   // has a bug with set content
        });

        pop.setContentNode(this.content);
        pop.show(owner);

    }
}
