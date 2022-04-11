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

package io.github.gleidsonmt.speedcut.controller.sales;

import io.github.gleidsonmt.gncontrols.GNMonetaryField;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.controller.SalesController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  11/04/2022
 */
public class PaymentMethod extends VBox {

    private int cols = 0;
    private int rows = 0;

    private GridPane content = new GridPane();

    public PaymentMethod(SalesController controller) {

        setMinHeight(100);
        getChildren().add(content);
        VBox.setMargin(this, new Insets(10));
        VBox.setVgrow(content, Priority.ALWAYS);

        content.getStyleClass().addAll("border", "border-t-1");

        createButton(0,0,  HPos.LEFT, Icons.CREDIT_CARD, "Cartao de Credito");
        createButton(0,1,  HPos.LEFT, Icons.CREDIT_CARD, "Nota a Prazo");

        GNMonetaryField monetaryField = new GNMonetaryField();
        content.getChildren().add(monetaryField);

        GridPane.setConstraints(
                monetaryField,
                1,0,1,2,
                HPos.CENTER,
                VPos.CENTER,
                Priority.ALWAYS, Priority.ALWAYS
        );

        createButton(2, 0, HPos.RIGHT,  Icons.CREDIT_CARD, "Cheque");
        createButton(2, 1,  HPos.RIGHT, Icons.CREDIT_CARD, "Dinheiro");


        content.getColumnConstraints().add( new ColumnConstraints(10d, 100d, -1.0d, Priority.SOMETIMES, HPos.RIGHT, true));

        content.setHgap(5);
//        content.setGridLinesVisible(true);

    }

    private Button createButton(int col, int row, HPos alignment, Icons icon, String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.getStyleClass().add("btn-payments");

        content.getChildren().add(button);
        GridPane.setConstraints(
                button,
                col, row, 1,1,
                alignment,
                VPos.CENTER,
                Priority.ALWAYS, Priority.ALWAYS
        );

        System.out.println(cols);

        cols++;
        if (cols > 2) {
            cols = 0;
            rows++;
        }


        return button;
    };

}
