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

import io.github.gleidsonmt.speedcut.core.app.model.Amount;
import io.github.gleidsonmt.speedcut.core.app.model.Transaction;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import javafx.beans.property.ListProperty;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/08/2022
 */
public class AmountColumnFactory implements Callback<TableColumn<Transaction, ListProperty<Amount>>, TableCell<Transaction, ListProperty<Amount>>> {


    @Override
    public TableCell<Transaction, ListProperty<Amount>> call(TableColumn<Transaction, ListProperty<Amount>> param) {

        return new TableCell<>(){

            @Override
            protected void updateItem(ListProperty<Amount> item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {

                    BigDecimal total = BigDecimal.ZERO;

                    for (Amount amount : item) {
                        total = total.add(amount.getValue());


                    }

                    getStyleClass().addAll( "border", "border-l-1");

//                    getStyleClass().add("amount");


                    if (getTableRow().getItem() != null) {
                        switch (getTableRow().getItem().getType()) {

                            case ENTER -> setText("+ " + MoneyUtil.format(total));
                            case EXIT -> setText("- " + MoneyUtil.format(total));

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
