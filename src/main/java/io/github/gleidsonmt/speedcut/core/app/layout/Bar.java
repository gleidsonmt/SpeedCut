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

import io.github.gleidsonmt.gncontrols.controls.GNIconButton;
import io.github.gleidsonmt.gncontrols.controls.button.GNIconButton;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class Bar extends HBox {

    private final GNIconButton hamburger   = new GNIconButton();

    public Bar() {
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().addAll("border", "border-b-1");
        hamburger.getStyleClass().add("hamburger");
        hamburger.setMinSize(30, 30);
        hamburger.setGraphic(new IconContainer(Icons.HAMBURGER));
    }

    public void addItem(Hyperlink hyperlink) {
        hyperlink.setStyle("-fx-font-size : 20;");
        getChildren().add(hyperlink);
    }

    public void setHamburger(boolean value) {
        if (value) {
            if (!getChildren().contains(hamburger)) getChildren().add(0, hamburger);
        } else getChildren().remove(hamburger);
    }
}
