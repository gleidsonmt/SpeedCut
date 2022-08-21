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

import io.github.gleidsonmt.speedcut.core.app.model.Transaction;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/08/2022
 */
public class TransactionRowFactory implements Callback<TableView<Transaction>, TableRow<Transaction>> {

    @Override
    public TableRow<Transaction> call(TableView<Transaction> param) {
        return new TableRow<>(){
            @Override
            protected void updateItem(Transaction item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {

                    switch (item.getType()) {
                        case ENTER -> {
                            getStyleClass().add("table-row-enter");
                        }
                        case EXIT -> {
                            getStyleClass().add("table-row-exit");
                        }

                    }

                } else {
                    setItem(null);
                    setText(null);
                    setGraphic(null);
                }
            }
        };
    }

}
