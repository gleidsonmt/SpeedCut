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

import animatefx.animation.FadeIn;
import io.github.gleidsonmt.speedcut.controller.sales.main.SalesController;
import io.github.gleidsonmt.speedcut.core.app.factory.SaleItemContext;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.math.BigDecimal;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  27/03/2022
 * @Revised 0.1
 */
public class SaleItemsRowFactory
        implements Callback<TableView<SaleItem>, TableRow<SaleItem>> {

    private SalesController controller;

    public SaleItemsRowFactory(SalesController controller) {
        this.controller = controller;
    }

    @Override
    public TableRow<SaleItem> call(TableView<SaleItem> param) {
        return new TableRow<>() {

            private void addStyle(boolean value) {

                getStyleClass().removeAll("table-row-discount");

                if (value) {
                    getStyleClass().addAll("table-row-discount");
                    setStyle("-base : -secondary; -primary-color : -secondary; -fx-font-weight:bold;");
                } else {
                    getStyleClass().removeAll("table-row-discount");
                    setStyle("-base : -info; -primary-color : -info; -fx-font-weight:bold;");
                }

            }

            @Override
            protected void updateItem(SaleItem item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null && !empty) {
                    setItem(item);
                    setContextMenu(new SaleItemContext(this, controller));

                    if (item.getProperties().get("init") != null) {
                        this.setOpacity(0);

                        getStyleClass().addAll("table-row-discount");

                        FadeIn fadeIn = new FadeIn(this);
                        fadeIn.play();

                        item.getProperties().remove("init");


                    }

                    addStyle(item.hasDiscount());

//                    if (item.hasDiscount()) item.setDiscount();

                    item.hasDiscountProperty().addListener((observable, oldValue, newValue) -> {
                        addStyle(newValue);
                    });

//                    addListener(item);

                } else {
                    setItem(null);
                    setStyle(null);
                    addStyle(false);
                }
            }
        };


    }
}
