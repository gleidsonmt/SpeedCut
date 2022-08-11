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

import io.github.gleidsonmt.speedcut.core.app.model.Category;
import io.github.gleidsonmt.speedcut.core.app.model.Transaction;
import io.github.gleidsonmt.speedcut.core.app.model.Type;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/08/2022
 */
public class CategoryColumnFactory implements Callback<TableColumn<Transaction, Category>, TableCell<Transaction, Category>> {

    @Override
    public TableCell<Transaction, Category> call(TableColumn<Transaction, Category> param) {

        return new TableCell<>() {

            @Override
            protected void updateItem(Category item, boolean empty) {

                super.updateItem(item, empty);

                if (item != null) {

//                    setText(item.name().equals("ENTER") ? "Entrada" : "Saída");

                    if (item.name().equals("SALE")) {
                        setText("Venda");
                    }

                    SVGPath icon = new SVGPath();

                    getStyleClass().addAll( "border", "border-l-1");
                    switch (item) {
                        case SALE -> {
                            setText("Venda");
                            // icon transfer
                            icon.setContent("m6.083 16.833-3.895-3.895 3.895-3.876.979 1-2.187 2.188h11.979v1.396H4.875l2.187 2.187Zm7.855-5.895-.98-1 2.188-2.188h-12V6.354h12l-2.188-2.187.98-1 3.874 3.875Z");

                        }
                        case BILL -> {
                            setText("Conta");
                            // icon bill
                            icon.setContent("M3.188 17.792V2.167l1.124.916 1.146-.916 1.125.916 1.146-.916 1.125.916L10 2.167l1.146.916 1.125-.916 1.146.916 1.125-.916 1.146.916 1.124-.916v15.625l-1.124-.917-1.146.917-1.125-.917-1.146.917-1.125-.917-1.146.917-1.146-.917-1.125.917-1.146-.917-1.125.917-1.146-.917Zm2.916-4.459h7.792v-1.395H6.104Zm0-2.666h7.792V9.271H6.104Zm0-2.646h7.792V6.625H6.104Zm-1.521 7.375h10.834V4.562H4.583Zm0-10.834v10.834Z");
                        }
                    }

                    setGraphicTextGap(8);
                    setGraphic(icon);

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
