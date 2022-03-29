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

package io.github.gleidsonmt.speedcut.core.app.factory.row;

import io.github.gleidsonmt.speedcut.controller.SalesController;
import io.github.gleidsonmt.speedcut.core.app.factory.SaleItemContext;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  27/03/2022
 */
public record SaleItemsRowFactory(
        SalesController controller) implements Callback<TableView<SaleItem>, TableRow<SaleItem>> {

    @Override
    public TableRow<SaleItem> call(TableView<SaleItem> param) {
        return new TableRow<>() {

            private void addStyle(boolean value) {
                if (value) {
                    getStyleClass().addAll("table-row-discount");
                    setStyle("-base : -secondary; -primary-color : -secondary;");
                } else {
                    getStyleClass().removeAll("table-row-discount");
                    setStyle("-base : -info; -primary-color : -info;");
                }
            }

            @Override
            protected void updateItem(SaleItem item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {
                    setItem(item);
                    addStyle(!Objects.equals(item.getDiscount(), BigDecimal.ZERO));
                    setContextMenu(new SaleItemContext(this, controller));
                } else {
                    setItem(null);
                    addStyle(false);
                }
            }
        };
    }
}
