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

import io.github.gleidsonmt.speedcut.core.app.factory.AvatarCreator;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/06/2022
 */
public class SaleItemCell extends GridPane {

    public SaleItemCell(SaleItem saleItem) {

        if (saleItem.getTradeItem().getAvatar() == null) {
            Label _default = AvatarCreator.createDefaulRecttAvatar(saleItem.getTradeItem().getName(), 15,0,0);
            getChildren().addAll(_default);
            GridPane.setConstraints(_default, 0,0, 1,2, HPos.LEFT, VPos.CENTER);
            GridPane.setMargin(_default, new Insets(0,5,0,0));

        }

        Label lblName = new Label(saleItem.getTradeItem().getName());
        lblName.getStyleClass().addAll("h5");
        lblName.setStyle("-fx-font-weight : bold;");

        Label lblQuantity = new Label("x" + saleItem.getQuantity());
        lblQuantity.setStyle("-fx-text-fill : -text-color");

        Label lblDescprition = new Label("lorem ipsum dolor color");
        lblDescprition.setStyle("-fx-text-fill : -text-color");

        Label lblPrice = new Label(MoneyUtil.format(saleItem.getTradeItem().getPrice()));
        lblPrice.setStyle("-fx-text-fill : -text-color");



        add(lblName, 1,0);
        add(lblQuantity, 2,0);
        add(lblDescprition, 1,1);
        add(lblPrice, 1,2);

        GridPane.setConstraints(lblName, 1,0, 1,1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.SOMETIMES);
        GridPane.setConstraints(lblDescprition, 1,1, 1,1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(lblQuantity, 2,0, 1,1, HPos.RIGHT, VPos.CENTER, Priority.SOMETIMES, Priority.SOMETIMES);
        GridPane.setConstraints(lblPrice, 2,1, 1,1, HPos.RIGHT, VPos.CENTER, Priority.SOMETIMES, Priority.SOMETIMES);

//        setGridLinesVisible(true);

    }

    private void styleText(Label label) {

    }
}
