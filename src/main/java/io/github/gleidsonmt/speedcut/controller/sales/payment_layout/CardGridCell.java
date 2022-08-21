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

package io.github.gleidsonmt.speedcut.controller.sales.payment_layout;

import io.github.gleidsonmt.speedcut.core.app.model.Amount;
import io.github.gleidsonmt.speedcut.core.app.model.Card;
import io.github.gleidsonmt.speedcut.core.app.model.Cards;
import io.github.gleidsonmt.speedcut.core.app.model.Transaction;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/08/2022
 */
public class CardGridCell extends GridPane {

    public enum Color {
        INFO,
        AMBER,
        BITTERSWEET;
    }

    public CardGridCell(Amount amount, Card card) {

        VBox.setMargin(this, new Insets(0,0,5,0));
        VBox.setVgrow(this, Priority.ALWAYS);

        StackPane iconContainer = new StackPane();
        iconContainer.setAlignment(Pos.CENTER_LEFT);

        Label lblNumber = new Label(String.valueOf(card.getCvc()));

        String _color = String.valueOf(Color.values()[new Random().nextInt(Color.values().length)]);

        lblNumber.setAlignment(Pos.BOTTOM_RIGHT);
        lblNumber.setStyle("-fx-background-color : -" + _color + ";" +
                "-fx-background-radius : 5px;" +
                "-fx-font-size : 8pt;" +
                "-fx-text-fill : white;");

        lblNumber.setPadding(new Insets(5));
        lblNumber.setPrefSize(50, 30);

        Label lblPoints = new Label("... ...");
        lblPoints.setStyle("-fx-text-fill : white;");
        lblPoints.setPrefSize(50, 27);
        lblPoints.setPadding(new Insets(5));


        Label lblValue = new Label(MoneyUtil.format(amount.getValue()));
        lblValue.setStyle("-fx-text-fill : -text-color; -fx-font-weight : bold;");
        iconContainer.getChildren().addAll(lblNumber, lblPoints);

        Label lblNameOfCard = new Label(card.getName());
        lblNameOfCard.setStyle("-fx-text-fill : -text-color;");
        getChildren().addAll(iconContainer, lblNameOfCard, lblValue);

        GridPane.setConstraints(iconContainer, 0,0,1,1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(lblNameOfCard, 1,0,1,1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(lblValue, 2,0,1,1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

    }

}
