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

import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.core.app.model.Amount;
import io.github.gleidsonmt.speedcut.core.app.model.PaymentMethod;
import io.github.gleidsonmt.speedcut.core.app.model.Pix;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.shape.SVGPath;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/08/2022
 */
public class PixCell extends GridPane {

    public PixCell(Amount amount) {

        String _compare = ((Pix) amount.getItem() ).getKey();

        Label lblValue = new Label(MoneyUtil.format(amount.getValue()));
        lblValue.setStyle("-fx-text-fill : -text-color; -fx-font-weight : bold;");
        Label lblName = new Label(_compare);
        lblName.setStyle("-fx-text-fill : -text-color;");

        getChildren().addAll(lblName, lblValue);

        SVGPath icon = new SVGPath();
        icon.setStyle("-fx-fill : -text-color;");

        if (_compare.contains("@")) {
            icon.setContent(Graphics.MAIL.getContent());
        } else if (_compare.contains(".")) {
            icon.setContent(Graphics.CPF.getContent());
        } else icon.setContent(Graphics.PHONE.getContent());

        lblName.setGraphic(icon);
        GridPane.setConstraints(lblName, 0,0,1,1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        GridPane.setConstraints(lblValue, 2,0,1,1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

    }

    public enum Graphics {

        MAIL("M4 20q-.825 0-1.412-.587Q2 18.825 2 18V6q0-.825.588-1.412Q3.175 4 4 4h16q.825 0 1.413.588Q22 5.175 22 6v12q0 .825-.587 1.413Q20.825 20 20 20Zm8-7L4 8v10h16V8Zm0-2 8-5H4ZM4 8V6v12Z"),
        PHONE("M19.95 21q-3.225 0-6.287-1.438-3.063-1.437-5.425-3.8-2.363-2.362-3.8-5.425Q3 7.275 3 4.05q0-.45.3-.75t.75-.3H8.1q.35 0 .625.225t.325.575l.65 3.5q.05.35-.012.637-.063.288-.288.513L6.975 10.9q1.05 1.8 2.638 3.375Q11.2 15.85 13.1 17l2.35-2.35q.225-.225.588-.338.362-.112.712-.062l3.45.7q.35.075.575.337.225.263.225.613v4.05q0 .45-.3.75t-.75.3ZM6.025 9l1.65-1.65L7.25 5H5.025q.125 1.025.35 2.025.225 1 .65 1.975Zm8.95 8.95q.975.425 1.988.675 1.012.25 2.037.325v-2.2l-2.35-.475ZM6.025 9Zm8.95 8.95Z"),
        CPF("m6 20 1-4H3.5l.5-2h3.5l1-4h-4L5 8h4l1-4h2l-1 4h4l1-4h2l-1 4h3.5l-.5 2h-3.5l-1 4h4l-.5 2h-4l-1 4h-2l1-4H9l-1 4Zm3.5-6h4l1-4h-4Z");

        private final String content;

        Graphics(String content) {
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
}
