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

import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import io.github.gleidsonmt.speedcut.core.app.model.Transaction;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/08/2022
 */
public class SaleColumnFactory implements Callback<TableColumn<Transaction, Sale>, TableCell<Transaction, Sale>> {


    @Override
    public TableCell<Transaction, Sale> call(TableColumn<Transaction, Sale> param) {
        return new TableCell<>() {
            @Override
            protected void updateItem(Sale item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {

                    SVGPath icon = new SVGPath();
                    icon.setContent("M6 16.708 2.292 13 6 9.292l.771.77-2.396 2.396h12.417v1.084H4.375l2.396 2.396Zm8-6-.771-.77 2.396-2.396H3.208V6.458h12.417l-2.396-2.396.771-.77L17.708 7Z");

                    setGraphic(icon);

//                    setText(item.getId());

                } else {
                    setItem(null);
                    setText(null);
                    setGraphic(null);
                }
            }
        };
    }
}
