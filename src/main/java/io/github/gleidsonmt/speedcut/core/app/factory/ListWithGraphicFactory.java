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

package io.github.gleidsonmt.speedcut.core.app.factory;

import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import io.github.gleidsonmt.speedcut.core.app.model.Item;
import io.github.gleidsonmt.speedcut.core.app.model.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/02/2022
 */
public class ListWithGraphicFactory implements Callback<ListView<? extends Item>, ListCell<? extends Item>> {

    @Override
    public ListCell<Item> call(ListView param) {
        return new ListCell<>(){

            @Override
            protected void updateItem(Item item, boolean empty) {


                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                    setId("badge-cell");
                    setGraphic(item.getAvatar());
                    setStyle(item.getAvatar().getStyle());
                    setItem(item);
                    setGraphicTextGap(15);
                } else {
                    setText(null);
                    setItem(null);
                    setGraphic(null);
                }
            }
        };
    }
}
