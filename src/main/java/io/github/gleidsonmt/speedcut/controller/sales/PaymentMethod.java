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
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.controller.SalesController;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  11/04/2022
 */
public class PaymentMethod extends VBox {

    private int cols = 0;
    private int rows = 0;

    private final GridPane content = new GridPane();
    private final GNMonetaryField monetaryField = new GNMonetaryField();


    public PaymentMethod(SalesController controller) {

        setMinHeight(100);
        getChildren().add(content);
        VBox.setMargin(this, new Insets(10));
        VBox.setVgrow(content, Priority.ALWAYS);

        content.getStyleClass().addAll("border", "border-t-1");

        Button btnCard = createButton(0,0,  HPos.LEFT, "Cartao de Credito");
        Button btnTerm = createButton(0,1,  HPos.LEFT, "Nota a Prazo");

        btnCard.setGraphic(createIcon(Graphics.CARD));
        btnTerm.setGraphic(createIcon(Graphics.TERM));

        content.getChildren().add(monetaryField);
        monetaryField.setAlignment(Pos.CENTER);
        monetaryField.setStyle("-fx-font-size : 14pt; -fx-border-radius : 0;");

        monetaryField.setMaxHeight(80);
        monetaryField.setPrefHeight(100);

        GridPane.setConstraints(
                monetaryField,
                1,0,1,2,
                HPos.CENTER,
                VPos.CENTER,
                Priority.ALWAYS, Priority.ALWAYS
        );

        Button btnCheque = createButton(2, 0, HPos.RIGHT, "Cheque");
        Button btnMoney = createButton(2, 1,  HPos.RIGHT, "Dinheiro");

        btnCheque.setGraphic(createIcon(Graphics.CHEQUE));
        btnMoney.setGraphic(new IconContainer(Icons.MONETARY));

        content.getColumnConstraints().add( new ColumnConstraints(10d, 100d, -1.0d, Priority.SOMETIMES, HPos.RIGHT, true));

        content.setHgap(5);
//        content.setGridLinesVisible(true);

    }

    private Button createButton(int col, int row, HPos alignment, String text) {
        Button button = new Button(text);
        button.setPrefWidth(100);
        button.setMaxWidth(Double.MAX_VALUE);
        button.getStyleClass().add("btn-payments");
        button.setContentDisplay(ContentDisplay.LEFT);

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
    }

    private Group createIcon(Graphics icon) {
        Group group = new Group();

        SVGPath _content = new SVGPath();
        _content.setContent(icon.content);

        _content.setScaleX(0.03);
        _content.setScaleY(0.03);
        group.getChildren().add(0, _content);
        return group;
    }

    private enum Graphics {

        CARD("M527.9 32H48.1C21.5 32 0 53.5 0 80v352c0 26.5 21.5 48 48.1 48h479.8c26.6 0 48.1-21.5 48.1-48V80c0-26.5-21.5-48-48.1-48zM54.1 80h467.8c3.3 0 6 2.7 6 6v42H48.1V86c0-3.3 2.7-6 6-6zm467.8 352H54.1c-3.3 0-6-2.7-6-6V256h479.8v170c0 3.3-2.7 6-6 6zM192 332v40c0 6.6-5.4 12-12 12h-72c-6.6 0-12-5.4-12-12v-40c0-6.6 5.4-12 12-12h72c6.6 0 12 5.4 12 12zm192 0v40c0 6.6-5.4 12-12 12H236c-6.6 0-12-5.4-12-12v-40c0-6.6 5.4-12 12-12h136c6.6 0 12 5.4 12 12z"),
        TERM("M630.6 364.9l-90.3-90.2c-12-12-28.3-18.7-45.3-18.7h-79.3c-17.7 0-32 14.3-32 32v79.2c0 17 6.7 33.2 18.7 45.2l90.3 90.2c12.5 12.5 32.8 12.5 45.3 0l92.5-92.5c12.6-12.5 12.6-32.7.1-45.2zm-182.8-21c-13.3 0-24-10.7-24-24s10.7-24 24-24 24 10.7 24 24c0 13.2-10.7 24-24 24zm-223.8-88c70.7 0 128-57.3 128-128C352 57.3 294.7 0 224 0S96 57.3 96 128c0 70.6 57.3 127.9 128 127.9zm127.8 111.2V294c-12.2-3.6-24.9-6.2-38.2-6.2h-16.7c-22.2 10.2-46.9 16-72.9 16s-50.6-5.8-72.9-16h-16.7C60.2 287.9 0 348.1 0 422.3v41.6c0 26.5 21.5 48 48 48h352c15.5 0 29.1-7.5 37.9-18.9l-58-58c-18.1-18.1-28.1-42.2-28.1-67.9z"),
        CHEQUE("M0 448c0 17.67 14.33 32 32 32h576c17.67 0 32-14.33 32-32V128H0v320zm448-208c0-8.84 7.16-16 16-16h96c8.84 0 16 7.16 16 16v32c0 8.84-7.16 16-16 16h-96c-8.84 0-16-7.16-16-16v-32zm0 120c0-4.42 3.58-8 8-8h112c4.42 0 8 3.58 8 8v16c0 4.42-3.58 8-8 8H456c-4.42 0-8-3.58-8-8v-16zM64 264c0-4.42 3.58-8 8-8h304c4.42 0 8 3.58 8 8v16c0 4.42-3.58 8-8 8H72c-4.42 0-8-3.58-8-8v-16zm0 96c0-4.42 3.58-8 8-8h176c4.42 0 8 3.58 8 8v16c0 4.42-3.58 8-8 8H72c-4.42 0-8-3.58-8-8v-16zM624 32H16C7.16 32 0 39.16 0 48v48h640V48c0-8.84-7.16-16-16-16z");

        private final String content;

        private Graphics(String content) {
            this.content = content;
        }

        public String getContent() {
            return this.content;
        }

        public static Icons get(String name) {
            Icons icon = null;
            for (Icons _icon : Icons.values()) {
                if (_icon.name().equals(name.toUpperCase())) {
                    icon = _icon;
                }
            }
            return icon;
        }

        public String toString() {
            return super.name();
        }
    }

    public void setValue(String value) {
        monetaryField.setText(value);
    }
}
