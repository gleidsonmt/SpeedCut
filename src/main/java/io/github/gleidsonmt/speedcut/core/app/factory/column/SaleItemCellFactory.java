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

import io.github.gleidsonmt.speedcut.core.app.factory.cell.SaleItemCell;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/06/2022
 */
public class SaleItemCellFactory implements Callback<ListView<SaleItem>, ListCell<SaleItem>> {

    @Override
    public ListCell<SaleItem> call(ListView<SaleItem> saleItemListView) {
        return new ListCell<>() {
            @Override
            protected void updateItem(SaleItem item, boolean b) {
                super.updateItem(item, b);

                if (item != null) {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                    SaleItemCell saleItemCell = new SaleItemCell(item);
                    saleItemCell.setPadding(new Insets(5,10,5,10));
                    saleItemCell.getStyleClass().add("border");

                    setGraphic(saleItemCell);

                    setAlignment(Pos.CENTER);
                    setPadding(new Insets(0));
                    setMouseTransparent(true);


                } else {
                    setItem(null);
                    setGraphic(null);
                    setText(null);
                    setStyle(null);
                }
            }
        };
    }
}
