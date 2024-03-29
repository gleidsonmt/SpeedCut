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

package io.github.gleidsonmt.speedcut.core.app.factory.column;

import io.github.gleidsonmt.speedcut.core.app.factory.AvatarCreator;
import io.github.gleidsonmt.speedcut.core.app.layout.util.AvatarView;
import io.github.gleidsonmt.speedcut.core.app.model.AvatarMode;
import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import io.github.gleidsonmt.speedcut.core.app.model.Item;
import io.github.gleidsonmt.speedcut.core.app.util.Util;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public class AvatarColumnFactory<T extends Entity, S extends Item> implements Callback<TableColumn<T, S>, TableCell<T, S>> {
    @Override
    public TableCell<T, S> call(TableColumn<T, S> param) {
        return new TableCell<>(){
            @Override
            protected void updateItem(S item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {
                    setText(item.getName());
                    setAlignment(Pos.CENTER);
                    setId("badge-cell");
                    setGraphic(null);

                    if (item.getAvatar() != null) {
                        setGraphic(new AvatarView(item.getAvatar(), AvatarMode.THUMB));
                    } else {
                        setGraphic(Util.createIconLabel(item.getName().substring(0,1), 15));
                    }

                    setTooltip(new Tooltip(
                             item.getName() +"\n Data de Registro : "
                    ));

                    setGraphicTextGap(10);
                    setItem(item);
                    getStyleClass().addAll("border", "border-l-1");


                } else {

                    setItem(null);
                    setGraphic(null);
                    setText(null);
                    getStyleClass().removeAll("border", "border-l-1");
                    setStyle(null);

                }
            }
        };
    }
}
