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

import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.controller.SalesController;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import io.github.gleidsonmt.speedcut.core.app.view.IManager;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  28/03/2022
 */
public class SaleItemContext extends ContextMenu implements IManager {

    private final TableRow<SaleItem> tableRow;

    public SaleItemContext(TableRow<SaleItem> tableRow, SalesController controller) {
        this.tableRow = tableRow;

        MenuItem menuDiscount = new MenuItem();
        menuDiscount.setText("Desconto");
        menuDiscount.setGraphic(new IconContainer(Icons.DISCOUNT));

        MenuItem menuEdit = new MenuItem();
        menuEdit.setText("Editar");
        menuEdit.setGraphic(new IconContainer(Icons.EDIT_FILLED));

        MenuItem menuRemove = new MenuItem();
        menuRemove.setText("Remover");
        menuRemove.setGraphic(new IconContainer(Icons.DELETE_OUTLINED));

        if (!tableRow.getItem().getItem().getDiscount().equals(BigDecimal.ZERO)) { // primeiro verifico so o item pode possuir algum desconto
            getItems().addAll(menuDiscount, menuEdit, menuRemove); // pode possuir entao adiona a opcao
            if (!tableRow.getItem().getDiscount().equals(BigDecimal.ZERO)) { // se ja tiver desconto
                menuDiscount.setGraphic(new IconContainer(Icons.CLEAR));
            } else {
                menuDiscount.setGraphic(new IconContainer(Icons.DISCOUNT));
            }
        } else {
            getItems().addAll( menuEdit, menuRemove);
        }

        menuDiscount.setOnAction(event -> {

            BigDecimal _discountSale = tableRow.getItem().getDiscount();

            if (_discountSale.equals(BigDecimal.ZERO)) {
                BigDecimal _discountSaleItem = tableRow.getItem().getItem().getDiscount();
                if (!_discountSaleItem.equals(BigDecimal.ZERO)) {
                    BigDecimal _calTotal =
                            _discountSaleItem.multiply(BigDecimal.valueOf(tableRow.getItem().getQuantity()));

                    tableRow.getItem().setDiscount(_discountSaleItem);

                    tableRow.getItem().setTotal(
                            tableRow.getItem().getTotal().subtract(_calTotal)
                    );
                    addStyle(true);
                    menuDiscount.setGraphic(new IconContainer(Icons.CLEAR));
                } else {
                    addStyle(false);
                }
            } else {
                tableRow.getItem().setTotal(
                        tableRow.getItem().getItem().getPrice().multiply(
                                BigDecimal.valueOf(tableRow.getItem().getQuantity())
                        )
                );
                tableRow.getItem().setDiscount(BigDecimal.ZERO);
                addStyle(false);
                menuDiscount.setGraphic(new IconContainer(Icons.DISCOUNT));

            }
            controller.recount();
        });

        menuRemove.setOnAction(event ->
                controller.deleteSaleItem());
    }

    void addStyle(boolean value) {
        if (value) {
            tableRow.getStyleClass().addAll("table-row-discount");
            tableRow.setStyle("-base : -secondary; -primary-color : -secondary;");
        } else {
            tableRow.getStyleClass().removeAll("table-row-discount");
            tableRow.setStyle("-base : -info; -primary-color : -info;");
        }
    }
}
