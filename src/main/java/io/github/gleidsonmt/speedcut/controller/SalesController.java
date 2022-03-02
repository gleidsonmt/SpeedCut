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

package io.github.gleidsonmt.speedcut.controller;

import animatefx.animation.*;
import io.github.gleidsonmt.gncontrols.GNButton;
import io.github.gleidsonmt.speedcut.core.app.animations.Animations;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  24/02/2022
 */
public class SalesController extends ResponsiveView {

    @FXML private ScrollPane scroll;
    @FXML private GridPane mainLayout;
    @FXML private VBox columnAdd;
    @FXML private VBox columnSales;
    @FXML private GridPane salesAction;
    @FXML private TableView tableSales;
    @FXML private TableColumn columnId;

    @FXML
    private void openPopupBuys() throws NavigationException {

        if (window.getWidth() > 600) {
            window.getRoot().getWrapper() // background for open popups, drawers..
                    .openPopup("buy", 600, 600); // open new popup with buy view as content
        } else {
            window.navigate("buy", true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        salesAction.getChildren().stream().map(e -> (Button)e).forEach(Animations::onHoverButton);
    }

    @Override
    protected void updateLayout(double width) {
//        mainLayout.getRowConstraints().clear();
//        mainLayout.getColumnConstraints().clear();

        if (width < 900) {
            scroll.setContent(columnAdd);
//            update(columnAdd, 0,0, Pos.CENTER);
//            update(columnSales, 0,1, Pos.CENTER);
        } else {
            scroll.setContent(mainLayout);
            mainLayout.getChildren().setAll(columnAdd, columnSales);
            update(columnAdd, 0,0, Pos.CENTER);
            update(columnSales, 1,0, Pos.CENTER);
        }
//
        if (width < 600) {
            salesAction.setVgap(0);
            salesAction.getChildren().stream().map(each -> (Button) each).forEach(each -> {
                each.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

                each.getStyleClass().addAll("border");
            });
            tableSales.getColumns().remove(columnId);

        } else {
            salesAction.setVgap(5);
            if (!tableSales.getColumns().contains(columnId)) {
                tableSales.getColumns().add(0, columnId);
            }
            salesAction.getChildren().stream().map(each -> (Button) each).forEach(each -> {
                each.setContentDisplay(ContentDisplay.LEFT);
                each.getStyleClass().removeAll("border");
            });

        }
    }

    @FXML private void onActionTest() {
        scroll.setContent(columnSales);
    }

    @Override
    public void onEnter() {
        super.onEnter();
    }

    @Override
    public void onExit() {
        super.onExit();
    }
}
