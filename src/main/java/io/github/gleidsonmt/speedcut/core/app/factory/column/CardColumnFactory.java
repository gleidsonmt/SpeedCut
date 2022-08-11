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

import io.github.gleidsonmt.speedcut.controller.payment.IPaymentMethod;
import io.github.gleidsonmt.speedcut.controller.payment.IPaymentMethod2;
import io.github.gleidsonmt.speedcut.core.app.model.Card;
import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import javafx.beans.property.ListProperty;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.math.BigDecimal;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public class CardColumnFactory<T extends Entity> implements Callback<TableColumn<T, ListProperty<Card>>, TableCell<T, ListProperty<Card>>> {

    @Override
    public TableCell<T, ListProperty<Card>> call(TableColumn<T, ListProperty<Card>> param) {

        return new TableCell<>(){

            @Override
            protected void updateItem(ListProperty<Card> item, boolean empty) {

                super.updateItem(item, empty);

                if (item != null && !empty) {

                    BigDecimal current = BigDecimal.ZERO;


                    for (Card card : item) {

//                        System.out.println("card = " + card.getValue());
                        current = current.add(card.getValue());
                    }

                    Hyperlink link = new Hyperlink(MoneyUtil.format(current));

                    link.setStyle(" -fx-border-color : -light-gray-2; -fx-border-width : 1px; -fx-border-radius : 3px;");

                    setGraphic(link);
                    setText(null);
//                    setText("null");
                    getStyleClass().addAll("border", "border-l-1");

                } else {

                    setItem(null);
                    setGraphic(null);
                    setText(null);
                    getStyleClass().removeAll("border", "border-l-1");
                    setStyle(null);

                }
            }
        };
    }
}
