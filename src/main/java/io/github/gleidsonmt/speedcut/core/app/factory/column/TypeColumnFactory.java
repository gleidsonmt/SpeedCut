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

import io.github.gleidsonmt.speedcut.core.app.model.Transaction;
import io.github.gleidsonmt.speedcut.core.app.model.Type;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.util.Callback;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/08/2022
 */
public class TypeColumnFactory implements Callback<TableColumn<Transaction, Type>, TableCell<Transaction, Type>> {

    @Override
    public TableCell<Transaction, Type> call(TableColumn<Transaction, Type> param) {
        return new TableCell<>() {
            @Override
            protected void updateItem(Type item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {

                    setText(item.name().equals("ENTER") ? "Entrada" : "Saída");

                    Circle icon = new Circle();
                    icon.setStrokeType(StrokeType.OUTSIDE);
                    icon.setRadius(3);
                    icon.setStrokeWidth(6);

                    getStyleClass().addAll("type", "border", "border-l-1");

                    switch (item) {
                        case ENTER -> {

                            icon.getStyleClass().add("icon-enter");
//
//                            icon.setFill(Color.web("#33B5E5"));
//                            icon.setStroke(Color.web("#33B5E550"));
                        }
                        case EXIT -> {
                            icon.getStyleClass().add("icon-exit");
//                            icon.getStyleClass().add("icon-exit");

//                            icon.setFill(Color.web("#FFA000"));
//                            icon.setStroke(Color.web("#FFA00050"));
                        }
                    }

                    setGraphicTextGap(8);
                    setGraphic(icon);

                } else {
                    setText(null);
                    setItem(null);
                    setGraphic(null);
                    getStyleClass().removeAll("border", "border-l-1");
                }

            }
        };
    }

}
