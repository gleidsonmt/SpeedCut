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

import animatefx.animation.*;
import io.github.gleidsonmt.gncontrols.GNFloatingButton;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.gncontrols.options.GNButtonType;
import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import io.github.gleidsonmt.speedcut.presenter.SaleItemPresenter;
import io.github.gleidsonmt.speedcut.presenter.SalePresenter;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public class QuantityColumnFactory<S extends SaleItem> implements Callback<TableColumn<S, Number>, TableCell<S, Number>>  {

    private Label lbl;
    private Label lbl_value;

    public QuantityColumnFactory(Label lbl, Label lbl_value) {
        this.lbl = lbl;
        this.lbl_value = lbl_value;
    }

    @Override
    public TableCell<S, Number> call(TableColumn<S, Number> param) {
        return new TableCell<>() {

            private final SaleItemPresenter salePresenter = new SaleItemPresenter();


            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(null);

                    GridPane container = new GridPane();

                    getStyleClass().addAll("border", "border-l-1");

                    container.setAlignment(Pos.CENTER);
                    GNFloatingButton plus = new GNFloatingButton();
                    plus.setGraphic(new IconContainer(Icons.ADD));
                    Label count = new Label(String.valueOf(item));
                    count.setMinSize(20, 20);
//                    count.setStyle("-fx-background-color : -medium-gray; -fx-background-radius : 100;");
                    count.setAlignment(Pos.CENTER);
                    GNFloatingButton minus = new GNFloatingButton();
                    minus.setGraphic(new IconContainer(Icons.REMOVE));
                    minus.setButtonType(GNButtonType.ROUNDED);
                    plus.setButtonType(GNButtonType.ROUNDED);

                    container.getChildren().addAll(minus, count, plus);
                    GridPane.setConstraints(minus, 0,0, 1,1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER);
                    GridPane.setConstraints(count, 1,0, 1,1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
                    GridPane.setConstraints(plus, 2,0, 1,1, HPos.RIGHT, VPos.CENTER, Priority.NEVER, Priority.NEVER);

                    minus.setMinSize(25, 25);
                    plus.setMinSize(25, 25);

                    minus.setMaxSize(25, 25);
                    plus.setMaxSize(25, 25);

                    minus.setStyle("-fx-border-radius : 100px; -fx-background-radius : 100px;");
                    plus.setStyle("-fx-border-radius : 100px; -fx-background-radius : 100px;");
                    setGraphic(container);

                    plus.setOnMouseEntered(event -> {
                        new Tada(plus).play();
                    });

                    minus.setOnMouseEntered(event -> {
                        new Tada(minus).play();
                    });

                    plus.setOnAction(event -> {
                        getTableView().getSelectionModel().select(getTableRow().getItem());
                        getTableRow().getItem().setQuantity(Integer.parseInt(count.getText()) + 1);
                        count.setText(String.valueOf(getTableRow().getItem().getQuantity()));
                        salePresenter.update(getTableRow().getItem());
                        salePresenter.persist();

                        updateValues(getTableRow().getItem().getDiscount());

                        sumOrSub(true, MoneyUtil.get(lbl.getText()));

                    });

                    minus.setOnAction(event -> {
                        getTableView().getSelectionModel().select(getTableRow().getItem());
                        getTableRow().getItem().setQuantity(Integer.parseInt(count.getText()) - 1);
                        count.setText(String.valueOf(getTableRow().getItem().getQuantity()));
                        salePresenter.update(getTableRow().getItem());
                        salePresenter.persist();

                        updateValues(getTableRow().getItem().getDiscount());
                        sumOrSub(false, MoneyUtil.get(lbl.getText()));


                    });

//                    if (getTableRow().getItem() != null) {
//                        getTableRow().getItem().discountProperty().addListener((observable, oldValue, newValue) -> {
//                            if (getTableRow().getItem() != null) {
//
//                                System.out.println("newValue = " + newValue);
//                                if (newValue != null) {
//                                    if (!newValue.equals(BigDecimal.ZERO)) {
//                                        getTableRow().getStyleClass().add("table-row-discount");
//                                        getTableRow().setStyle("-base : -secondary; -primary-color : -secondary;");
//                                    }
//
//                                }
//                                updateValues(newValue);
//                            }
//
//                        });
//                    }

                } else {
                    setText(null);
                    setGraphic(null);
                    setItem(null);
                    setStyle(null);
                    getStyleClass().removeAll("border", "border-l-1");
                }
            }

            private void updateValues(BigDecimal discount) {

                getTableRow().getItem().setTotal(
                        new BigDecimal(getTableRow().getItem().getQuantity()).multiply(
                                getTableRow().getItem().getItem().getPrice().subtract(discount))
                );

                lbl_value.setText(MoneyUtil.format(getTableRow().getItem().getTotal()));

                BigDecimal _totalDiscount =
                        getTableRow().getItem().getDiscount().multiply(
                        BigDecimal.valueOf(getTableRow().getItem().getQuantity()));

                BigDecimal _total = MoneyUtil.get(lbl.getText());
                lbl.setText(MoneyUtil.format(_total.subtract(_totalDiscount)));
            }

            private void sumOrSub(boolean value, BigDecimal first) {
                if (value) {
                    lbl.setText(
                            MoneyUtil.format(first.add(
                                    getTableRow().getItem().getItem().getPrice().subtract(
                                            getTableRow().getItem().getDiscount()
                                    )

                            ))
                    );
                } else {
                    MoneyUtil.format(first.subtract(
                            getTableRow().getItem().getItem().getPrice().subtract(
                                    getTableRow().getItem().getDiscount()
                            )

                    ));
                }
            }

            private void updateLbl() {

            }
        };

    }

}
