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
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class Wrapper {

    private final WindowDecorator window;

    private final StackPane  container  = new StackPane();
    private final AnchorPane root       = new AnchorPane();


    public Wrapper(Root _root, WindowDecorator window) {

        this.window = window;
        container.getChildren().add(root);

        root.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.gray(0.5, 0.3),
//                                Color.WHITE,
                                CornerRadii.EMPTY,
                                Insets.EMPTY))
        );

        _root.getChildren().add(container);
    }

    public void addContent(Node node) {
        // usado como drawer, popup and alerts
//        root.getChildren().clear();
        root.getChildren().add(node);

//        node.setBlendMode(BlendMode.COLOR_BURN);
    }

    private PopupOver popupOver;

    public PopupOver getPopOver() {
        if (popupOver == null) popupOver = new PopupOver();
        return popupOver;
    }

    private Popup popup;

    public Popup getPopup() {
        if (popup == null) popup = new Popup(this);
        return popup;
    }

    private Alert alert;

    public Alert getAlert() {
        if (alert == null) alert = new Alert(this);
        return alert;
    }

    private Drawer drawer;

    public Drawer getDrawer() {
        if (drawer == null) drawer = new Drawer(this);
        return drawer;
    }

    @Deprecated
    public AnchorPane getRoot() {
        return root;
    }

    @Deprecated
    public WindowDecorator getWindow() {
        return this.window;
    }

    void clear() {
        root.getChildren().clear();
    }

    public void show() {
        container.toFront();
    }

    void hide () {
        container.toBack();
    }

    double getHeight() {
        return container.getHeight();
    }

    double getWidth() {
        return container.getWidth();
    }
}
