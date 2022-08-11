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

import io.github.gleidsonmt.speedcut.core.app.factory.cell.QuantityCell;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public class QuantityColumnFactory<S extends SaleItem>
        implements Callback<TableColumn<S, Number>, TableCell<S, Number>> {

    @Override
    public TableCell<S, Number> call(TableColumn<S, Number> param) {
        return new TableCell<>() {

            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(null);

                    if (getTableRow().getItem() != null)

                        setGraphic(
                                new QuantityCell(getTableRow().getItem()));

                    getStyleClass().addAll("border", "border-l-1");
//
                } else {
                    setText(null);
                    setGraphic(null);
                    setItem(null);
                    setStyle(null);
                    getStyleClass().removeAll("border", "border-l-1");
                }
            }
        };

    }

}
