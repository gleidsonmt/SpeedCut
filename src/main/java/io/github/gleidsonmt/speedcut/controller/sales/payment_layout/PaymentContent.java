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

import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.controller.PaymentType;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  23/07/2022
 */
public  class PaymentContent extends VBox {

    // containers
    private final Label       lblTitle = new Label();

    /// attributes
    private final PaymentType paymentType;

    public PaymentContent(PaymentType paymentType, Group icon, String title, Node... nodes) {

        this.paymentType = paymentType;

        lblTitle.setText(title);

        lblTitle.setGraphic(icon);
        lblTitle.getStyleClass().add("h5");

        this.setPadding(new Insets(10,5,5,10));

        this.getChildren().add(lblTitle);

        this.getChildren().add(new Separator());
        this.setSpacing(5);

        VBox content = new VBox();
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(content);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        int height = nodes.length * 40;
        scrollPane.setMinHeight(height);
        getChildren().add(scrollPane);

        VBox.setMargin(this, new Insets(5));
        StackPane.setMargin(this, new Insets(5));

    }

    public void setText(String text) {
        lblTitle.setText(text);
    }

    public void setIcon(Node icon) {
        lblTitle.setGraphic(icon);
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }
}
