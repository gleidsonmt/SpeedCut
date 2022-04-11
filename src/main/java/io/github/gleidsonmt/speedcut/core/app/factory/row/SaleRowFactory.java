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
import io.github.gleidsonmt.speedcut.core.app.factory.SaleContext;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  28/03/2022
 */
public record SaleRowFactory(SalesController controller)
        implements Callback<TableView<Sale>, TableRow<Sale>> {

    @Override
    public TableRow<Sale> call(TableView<Sale> param) {
        return new TableRow<>(){
            @Override
            protected void updateItem(Sale item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {
                    setOnMouseClicked(event -> {
                        if (event.getClickCount() == 2)
                            controller.goTableSalesItem();
                    });
                    setContextMenu(new SaleContext(this));
                } else {
                    setItem(null);
                    setOnMouseClicked(null);
                }
            }
        };
    }
}
