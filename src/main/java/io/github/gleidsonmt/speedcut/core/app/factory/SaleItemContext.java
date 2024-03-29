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
import io.github.gleidsonmt.speedcut.controller.sales.main.SalesController;
import io.github.gleidsonmt.speedcut.core.app.dao.Repositories;
import io.github.gleidsonmt.speedcut.core.app.dao.Repository;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  28/03/2022
 * @Revised 1.0
 */
public class SaleItemContext extends ContextMenu implements Context {

    public SaleItemContext(TableRow<SaleItem> tableRow, SalesController controller) {

        MenuItem menuDiscount = new MenuItem();
        menuDiscount.setText("Desconto");
        menuDiscount.setGraphic(new IconContainer(Icons.DISCOUNT));

        MenuItem menuEdit = new MenuItem();
        menuEdit.setText("Editar");
        menuEdit.setGraphic(new IconContainer(Icons.EDIT_FILLED));

        MenuItem menuRemove = new MenuItem();
        menuRemove.setText("Remover");
        menuRemove.setGraphic(new IconContainer(Icons.DELETE_OUTLINED));


        if (!tableRow.getItem().getTradeItem().getDiscount().equals(BigDecimal.ZERO)) { // primeiro verifico so o item pode possuir algum desconto
            getItems().addAll(menuDiscount, menuEdit, menuRemove); // pode possuir entao adiona a opcao
        } else {
            getItems().addAll( menuEdit, menuRemove);
        }

        menuDiscount.setOnAction(event -> {
            SaleItem saleItem = tableRow.getItem();
            Repository<SaleItem> repo =  Repositories.get(SaleItem.class);

            saleItem.setHasDiscount(!saleItem.hasDiscount());

            repo.put(saleItem);
            repo.persist();

        });

//        menuRemove.setOnAction(event ->
//                controller.deleteSaleItem());

    }

}
