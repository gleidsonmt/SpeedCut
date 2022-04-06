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

package io.github.gleidsonmt.speedcut.core.app.layout.util;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  28/02/2022
 */
@SuppressWarnings("unused")
public final class AlignmentUtil {

    public static void topLeft(Node content, Insets insets) {
        reset(content);
        AnchorPane.setTopAnchor(content, insets.getTop());
        AnchorPane.setLeftAnchor(content, insets.getLeft());
    }

    public static void topCenter(Node content, Insets insets) {
        reset(content);
        AnchorPane.setRightAnchor(content, insets.getRight());
        AnchorPane.setTopAnchor(content, insets.getTop());
        AnchorPane.setLeftAnchor(content, insets.getRight());
    }

    public static void topRight(Node content, Insets insets) {
        reset(content);
        AnchorPane.setTopAnchor(content, insets.getTop());
        AnchorPane.setRightAnchor(content, insets.getRight());
    }

    public static void centerRight(Node content, Insets insets) {
        reset(content);
        AnchorPane.setTopAnchor(content, insets.getTop());
        AnchorPane.setRightAnchor(content, insets.getRight());
        AnchorPane.setBottomAnchor(content, insets.getBottom());
    }

    public static void bottomRight(Node content, Insets insets) {
        reset(content);
        AnchorPane.setRightAnchor(content, insets.getRight());
        AnchorPane.setBottomAnchor(content, insets.getBottom());
    }

    public static void bottomCenter(Node content, Insets insets) {
        reset(content);
        AnchorPane.setRightAnchor(content, insets.getRight());
        AnchorPane.setBottomAnchor(content, insets.getBottom());
        AnchorPane.setLeftAnchor(content, insets.getLeft());
    }

    public static void bottomLeft(Node content, Insets insets) {
        reset(content);
        AnchorPane.setLeftAnchor(content, insets.getLeft());
        AnchorPane.setBottomAnchor(content, insets.getBottom());
    }

    public static void centerLeft(Node content, Insets insets) {
        reset(content);
        AnchorPane.setTopAnchor(content, insets.getTop());
        AnchorPane.setLeftAnchor(content, insets.getLeft());
        AnchorPane.setBottomAnchor(content, insets.getBottom());
    }



    private static void reset(Node content) {
        AnchorPane.clearConstraints(content);
    }
}
