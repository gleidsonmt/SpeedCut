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
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public class ProductColumnFactory<T extends Entity, S extends Item> implements Callback<TableColumn<T, S>, TableCell<T, S>> {

    @Override
    public TableCell<T, S> call(TableColumn<T, S> param) {
        return new TableCell<>(){
            @Override
            protected void updateItem(S item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.toString());
                    setAlignment(Pos.CENTER_LEFT);
                    setId("badge-cell");
                    setGraphic(null);
                    AvatarCreator creator = new AvatarCreator(item.getName(), item.getAvatar().getStyle());
                    setGraphic(creator);
                    item.getAvatar().setMouseTransparent(true);
                    setStyle(item.getAvatar().getStyle());
                    setGraphicTextGap(10);
                    setItem(item);
                    getStyleClass().addAll("border", "border-l-1");
//                    getTableRow().setStyle(creator.getStyle());
//                    getTableRow().setId("table-row-custom");

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
