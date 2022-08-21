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

package io.github.gleidsonmt.speedcut.core.app.factory.cell;

import animatefx.animation.Tada;
import io.github.gleidsonmt.gncontrols.GNFloatingButton;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.gncontrols.options.GNButtonType;
import io.github.gleidsonmt.speedcut.core.app.dao.Repositories;
import io.github.gleidsonmt.speedcut.core.app.dao.Repository;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  19/05/2022
 */
public class QuantityCell extends GridPane implements Context {

    private final SaleItem saleItem;
    private final TableView<SaleItem> tableView;

    private final IntegerProperty quantity = new SimpleIntegerProperty();

    public QuantityCell(TableView<SaleItem> _tableView, SaleItem _saleItem) {
        saleItem = _saleItem;
        tableView = _tableView;
        initLayout();
    }

    private void initLayout() {

        setAlignment(Pos.CENTER);
        GNFloatingButton plus = new GNFloatingButton();
        plus.setGraphic(new IconContainer(Icons.ADD));

        Label count = new Label();

        quantity.bindBidirectional(saleItem.quantityProperty());
        count.textProperty().bind(Bindings.convert(quantity));

        count.setMinSize(20, 20);
        count.setAlignment(Pos.CENTER);
        GNFloatingButton minus = new GNFloatingButton();
        minus.setGraphic(new IconContainer(Icons.REMOVE));
        minus.setButtonType(GNButtonType.ROUNDED);
        plus.setButtonType(GNButtonType.ROUNDED);

        getChildren().addAll(minus, count, plus);
        GridPane.setConstraints(minus, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.NEVER, Priority.NEVER);
        GridPane.setConstraints(count, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
        GridPane.setConstraints(plus, 2, 0, 1, 1, HPos.RIGHT, VPos.CENTER, Priority.NEVER, Priority.NEVER);

        minus.setMinSize(25, 25);
        plus.setMinSize(25, 25);

        minus.setMaxSize(25, 25);
        plus.setMaxSize(25, 25);

        minus.setStyle("-fx-border-radius : 100px; -fx-background-radius : 100px;");
        plus.setStyle("-fx-border-radius : 100px; -fx-background-radius : 100px;");

        plus.setOnMouseEntered(event -> new Tada(plus).play());
        minus.setOnMouseEntered(event -> new Tada(minus).play());

        plus.setOnAction(event -> {

            tableView.getSelectionModel().select(saleItem);

            if (count.getText() == null || count.getText().isEmpty() || count.getText().isBlank())
                return;

            int test = Integer.parseInt(count.getText());

            if (test > 9999) return;

            quantity.set(quantity.get() + 1);
            saleItem.setQuantity(quantity.get());

            save(saleItem);
        });

        minus.setOnAction(event -> {

            tableView.getSelectionModel().select(saleItem);

            if (count.getText() == null || count.getText().isEmpty() || count.getText().isBlank())
                return;

            int test = Integer.parseInt(count.getText());

            if (test < 2) return;

            quantity.set(quantity.get() - 1);
            saleItem.setQuantity(quantity.get());

            save(saleItem);
        });

    }

    private void save(SaleItem saleItem) {
        Repository<SaleItem> repo = Repositories.get(SaleItem.class);
        repo.put(saleItem);
        repo.persist();
    }
}
