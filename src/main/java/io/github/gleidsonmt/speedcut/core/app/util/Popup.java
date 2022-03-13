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

package io.github.gleidsonmt.speedcut.core.app.util;

import animatefx.animation.Pulse;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import org.controlsfx.control.PopOver;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/03/2022
 */
public class Popup extends PopOver {

    private Region content;

    public Popup(Region content) {
        this(content, -1D);
    }

    public Popup(Region content, double width) {

        this.content = content;
        this.getRoot().getStylesheets().add(
                Popup.class.getResource("/io.github.gleidsonmt.speedcut.core.app/theme/css/poplight.css").toExternalForm());

//        this.setArrowLocation(ArrowLocation.TOP_LEFT);
        this.setArrowIndent(0);
        this.setArrowSize(0);
        this.setCloseButtonEnabled(false);
        this.setHeaderAlwaysVisible(false);
        this.setCornerRadius(0);

        this.setHeaderAlwaysVisible(false);
        this.setContentNode(content);

        this.getRoot().setPrefWidth(width);
        this.getRoot().setMaxWidth(width);

        this.setCornerRadius(0);

    }

    public void showBottomRight(Node node) {

//        this.setArrowLocation(ArrowLocation.TOP_LEFT);


        Bounds bounds = node.localToScreen(node.getBoundsInLocal());
        this.show(node, bounds.getMinX() + bounds.getWidth() ,
                bounds.getMinY() + bounds.getHeight()  );

//        Node skinNode = this.getSkin().getNode();
//        new Pulse(skinNode).play();
//
//        Pulse fadeInLeft = new Pulse();
//        fadeInLeft.setNode(getRoot());
//        fadeInLeft.play();
    }

    public void showBottomLeft(Node node) {

//        this.setArrowLocation(ArrowLocation.TOP_LEFT);


        Bounds bounds = node.localToScreen(node.getBoundsInLocal());
        Bounds bound = content.localToScreen(content.getBoundsInLocal());
        System.out.println("content = " + content.getPrefWidth());
//        this.show(node, bounds.getMinX() - reg.getWidth(),
//                bounds.getMinY() + bounds.getHeight()  );

//        Node skinNode = this.getSkin().getNode();
//        new Pulse(skinNode).play();
//
//        Pulse fadeInLeft = new Pulse();
//        fadeInLeft.setNode(getRoot());
//        fadeInLeft.play();
    }

    public void showTopRight(Node node) {

//        this.setArrowLocation(ArrowLocation.TOP_LEFT);


        Bounds bounds = node.localToScreen(node.getBoundsInLocal());
        this.show(node, bounds.getMinX() + bounds.getWidth() ,
                bounds.getMinY() - bounds.getHeight()  );

//        Node skinNode = this.getSkin().getNode();
//        new Pulse(skinNode).play();
//
//        Pulse fadeInLeft = new Pulse();
//        fadeInLeft.setNode(getRoot());
//        fadeInLeft.play();
    }

}
