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

import io.github.gleidsonmt.gncontrols.GNButton;
import io.github.gleidsonmt.gncontrols.GNListView;
import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.gncontrols.options.FieldType;
import io.github.gleidsonmt.gncontrols.options.GNButtonType;
import io.github.gleidsonmt.gncontrols.options.TrayAction;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.factory.ListWithGraphicFactory;
import io.github.gleidsonmt.speedcut.core.app.model.Client;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import io.github.gleidsonmt.speedcut.presenter.ClientPresenter;
import io.github.gleidsonmt.speedcut.presenter.ProfessionalPresenter;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  12/03/2022
 */
public class BoxClientsSearch extends VBox {

    public BoxClientsSearch() {

        GNListView<Client> listView = new GNListView<>();

        VBox boxSearch = new VBox();
        boxSearch.setAlignment(Pos.CENTER_LEFT);
        boxSearch.setSpacing(5D);
        GNTextBox textBox = new GNTextBox();
        textBox.setFloatPrompt(false);
        textBox.setLeadIcon(Icons.SEARCH);
        textBox.setFieldType(FieldType.FILLED);
        textBox.setTrayAction(TrayAction.CLEAR);
        textBox.setStyle("-fx-min-height : 50px;");

        GNButton btnSale = new GNButton("Adicionar Venda");
        GNButton btnEmployee = new GNButton("Selecionar Funcionario");
        btnEmployee.setPrefWidth(-1);
        btnSale.setPrefWidth(-1);

        GNButton btnClose = new GNButton("Close");
        btnClose.setButtonType(GNButtonType.SEMI_ROUNDED);
        btnClose.setPrefSize(-1, -1);
        btnClose.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btnClose.setPrefWidth(-1);
        btnClose.setPrefWidth(-1);
        btnClose.setGraphic(new IconContainer(Icons.CLEAR));

        btnEmployee.getStyleClass().addAll("btn-mint", "deep-button");
        btnClose.getStyleClass().addAll("btn-grapefruit", "deep-button");
        btnSale.getStyleClass().addAll("deep-button");

        btnEmployee.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btnSale.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        HBox searchActions = new HBox();
        searchActions.setSpacing(5);
        searchActions.getChildren().setAll(btnClose, btnSale, btnEmployee);
        btnSale.setGraphic(new IconContainer(Icons.ADD_CIRCLE));
        btnEmployee.setGraphic(new IconContainer(Icons.ACCOUNT_CIRCLE));
        searchActions.getStyleClass().addAll("border", "border-b-1");
//        searchActions.getChildren().stream().map(m -> (GNButton) m).forEach(f -> f.getStyleClass().addAll("deep-button"));
        searchActions.setMinHeight(50);
        searchActions.setAlignment(Pos.CENTER_RIGHT);
        searchActions.setPadding(new Insets(0,5,0,0));

        btnSale.setButtonType(GNButtonType.SEMI_ROUNDED);
        btnEmployee.setButtonType(GNButtonType.SEMI_ROUNDED);
        boxSearch.getChildren().setAll(textBox,  listView, searchActions);

        textBox.setMinHeight(50);
        textBox.setPrefHeight(50);



    }
}
