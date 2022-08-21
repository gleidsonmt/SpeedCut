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
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/08/2022
 */
public class TotalColumnFactory implements Callback<TableColumn<Transaction, BigDecimal>, TableCell<Transaction, BigDecimal>> {


    @Override
    public TableCell<Transaction, BigDecimal> call(TableColumn<Transaction, BigDecimal> param) {

        return new TableCell<>(){

            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {

                    getStyleClass().addAll( "border", "border-l-1");

//                    getStyleClass().add("amount");


                    if (getTableRow().getItem() != null) {
                        switch (getTableRow().getItem().getType()) {

                            case ENTER -> setText("+ " + MoneyUtil.format(item));
                            case EXIT -> setText("- " + MoneyUtil.format(item));

                        }
                    }

                    setContentDisplay(ContentDisplay.TEXT_ONLY);

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
