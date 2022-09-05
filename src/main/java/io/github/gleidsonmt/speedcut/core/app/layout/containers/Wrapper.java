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

import io.github.gleidsonmt.speedcut.core.app.layout.Root;
import io.github.gleidsonmt.speedcut.core.app.view.WindowDecorator;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class Wrapper implements IWrapper {

    private final WindowDecorator window;

    private final StackPane  container  = new StackPane();

    private final VBox rootV = new VBox();
    private final HBox rootH = new HBox();

    public Wrapper(@NotNull Root _root, WindowDecorator window) {

        this.window = window;
        container.getChildren().add(rootV);

        container.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.gray(0.5, 0.3),
//                                Color.RED,
                                CornerRadii.EMPTY,
                                Insets.EMPTY)
                )
        );

        _root.getChildren().add(container);

        rootV.setFillWidth(false);
        rootH.setFillHeight(false);
    }

    public void addContent(Node node) {
        // usado como drawer, popup and alerts
//        root.getChildren().clear();

        rootV.getChildren().add(node); // add one more item

//        rootV.getChildren().setAll(node); // set all items in content

    }

    public void setAligment(Pos pos) {
        rootV.setAlignment(pos);
    }

    private PopupOver popupOver;

    @Override
    public PopupOver getPopOver() {
        if (popupOver == null) popupOver = new PopupOver();
        return popupOver;
    }

    private Popup popup;

    @Override
    public Popup getPopup() {
        if (popup == null) popup = new Popup(this);
        return popup;
    }

    private Alert alert;

    @Override
    public Alert getAlert() {
        if (alert == null) alert = new Alert(this);
        return alert;
    }

    private Drawer drawer;

    @Override
    public Drawer getDrawer() {
        if (drawer == null) drawer = new Drawer(this);
        return drawer;
    }

    @Deprecated
    public Pane getRoot() {
        return rootV;
    }

    @Deprecated
    public WindowDecorator getWindow() {
        return this.window;
    }

    void clear() {
        rootV.getChildren().clear();
    }

    public void show() {
        container.toFront();
    }

    void hide () {
        container.toBack();
    }

    @Deprecated
    double getHeight() {
        return container.getHeight();
    }

    @Deprecated
    double getWidth() {
        return container.getWidth();
    }
}
