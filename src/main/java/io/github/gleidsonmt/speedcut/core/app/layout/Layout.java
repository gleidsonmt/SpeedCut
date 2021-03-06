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

import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class Layout extends BorderPane {

    private Node oldLeftNode;

    private final CenterLayout centerLayout = new CenterLayout();

    public Layout() {
        setCenter(centerLayout);

        leftProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) oldLeftNode = newValue;
        });

        getStyleClass().add("layout");
    }

    public CenterLayout getCenterLayout() {
        return centerLayout;
    }

    public Node getOldLeft() {
        return oldLeftNode;
    }

}
