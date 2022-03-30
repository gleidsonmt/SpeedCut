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

import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  28/02/2022
 */
public final class AlignmentUtil {

    public static void centerLeft(Node content) {
        centerLeft(content, 0);
    }

    public static void centerLeft(Node content, double padding) {
        reset(content);
        AnchorPane.setTopAnchor(content, padding);
        AnchorPane.setLeftAnchor(content, padding);
        AnchorPane.setBottomAnchor(content, padding);
    }

    public static void centerRight(Node content) {
        centerLeft(content, 0);
    }

    public static void centerRight(Node content, double padding) {
        reset(content);
        AnchorPane.setTopAnchor(content, padding);
        AnchorPane.setRightAnchor(content, padding);
        AnchorPane.setBottomAnchor(content, padding);
    }

    public static void topLeft(Node content) {
        topLeft(content, 0,0);
    }

    public static void topLeft(Node content, double topPadding, double leftPadding) {
        reset(content);
        AnchorPane.setTopAnchor(content, topPadding);
        AnchorPane.setLeftAnchor(content, leftPadding);
    }

    private static void reset(Node content) {
        AnchorPane.clearConstraints(content);
    }
}