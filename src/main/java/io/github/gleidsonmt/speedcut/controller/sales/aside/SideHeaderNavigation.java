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

package io.github.gleidsonmt.speedcut.controller.sales.aside;

import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.controller.sales.main.SalesController;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/08/2022
 */
public class SideHeaderNavigation extends HBox implements Context {

    private Button btnBack;

    public SideHeaderNavigation(Icons icon, String title, boolean needsBackButton) {
        this(new IconContainer(icon), title, needsBackButton);
    }

    public SideHeaderNavigation(Icons icon, String title) {
        this(new IconContainer(icon), title, true);
    }

    public SideHeaderNavigation(@NotNull IconContainer icon, String title, boolean needsBackButton) {

        VBox.setMargin(this, new Insets(0, 5,0,5));

        this.setMinHeight(60);
        this.setMaxHeight(60);

        icon.setScaleX(1.2);
        icon.setScaleY(1.2);

        this.setAlignment(Pos.CENTER_LEFT);
        this.getStyleClass().addAll("border", "border-b-1");

        Label lblInfo = new Label();
        lblInfo.setMaxHeight(60);
        lblInfo.setText(title);
        lblInfo.getStyleClass().addAll("h4");
        lblInfo.setStyle("-fx-font-weight : bold;");
        icon.setStyle("-fx-fill : -info;");
        lblInfo.setGraphic(icon);
        HBox.setMargin(lblInfo, new Insets(0, 0, 0, 15));
        lblInfo.setGraphicTextGap(10);

        if (needsBackButton) {
            btnBack = new Button();

            btnBack.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            btnBack.setGraphic(new IconContainer(Icons.ARROW_BACK));

            btnBack.setPrefWidth(60);
            btnBack.setPrefHeight(60);
            btnBack.setMinWidth(60);
            btnBack.setMinHeight(60);
            btnBack.setMaxWidth(60);

            btnBack.getStyleClass().addAll("btn-flat", "border", "border-r-1");

            this.getChildren().setAll( btnBack, lblInfo);


        }

        this.getChildren().setAll( lblInfo);



        if (btnBack != null)
            configActions();
    }

    private void configActions() {
        btnBack.setOnAction(action -> {
                SalesController controller = (SalesController) context.getRoutes().getView("sales").getController();
                controller.remove();
            }
        );

    }
}
