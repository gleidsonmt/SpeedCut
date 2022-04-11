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

import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.controller.SalesController;
import io.github.gleidsonmt.speedcut.core.app.animations.Animations;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  08/04/2022
 */
public class PayActions extends GridPane  {

    private int count = 0;

    public PayActions(SalesController controller) {

        setMinHeight(60);
        setHgap(5);
        setVgap(5);
        setPadding(new Insets(2));
        VBox.setMargin(this, new Insets(0, 5, 0, 5));

        Button btnCancel = createButton(Icons.ERROR, "btn-grapefruit");
        Button btnPrint = createButton(Icons.PRINT_FILLED, null);
        Button btnPay = createButton(Icons.DONE, "btn-mint");

        getChildren().stream().map(e -> (Button)e).forEach(Animations::onHoverButton);

        btnCancel.setOnAction(event ->
                controller.switchPayControls(false));

    }

    private Button createButton(Icons icon, String clazz) {
        Button button = new Button();
        button.setMaxWidth(Double.MAX_VALUE);
        button.getStyleClass().add("deep-button");
        button.getStyleClass().add(clazz);
        button.setGraphic(createIcon(icon));

        getChildren().add(button);

        GridPane.setConstraints(
                button,
                count++,0,1,1,
                HPos.CENTER,
                VPos.CENTER,
                Priority.ALWAYS, Priority.ALWAYS
        );

        return button;
    }

    private IconContainer createIcon(Icons icon) {
        IconContainer iconContainer = new IconContainer(icon);
        iconContainer.setScaleX(1.3);
        iconContainer.setScaleY(1.3);
        return iconContainer;
    }

}
