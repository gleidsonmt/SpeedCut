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

import animatefx.animation.FadeOut;
import io.github.gleidsonmt.gncontrols.GNFloatingButton;
import io.github.gleidsonmt.gncontrols.GNListView;
import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.controller.SalesController;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.factory.ListAvatarActionFactory;
import io.github.gleidsonmt.speedcut.core.app.model.Client;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import io.github.gleidsonmt.speedcut.core.app.view.IManager;
import io.github.gleidsonmt.speedcut.core.app.view.IView;
import io.github.gleidsonmt.speedcut.presenter.ClientPresenter;
import io.github.gleidsonmt.speedcut.presenter.ProfessionalPresenter;
import io.github.gleidsonmt.speedcut.presenter.SalePresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  11/04/2022
 */
public class SearchClientBox extends StackPane implements IManager {


    private final VBox body = new VBox();
    private final VBox boxHeader = new VBox();
    private final HBox header = new HBox();
    private final VBox content = new VBox();
    private final VBox boxSearch = new VBox();

    private final Button btnBack = new Button();
    private final Label lblInfo = new Label();


    private final GNListView<Client>    listView        = new GNListView<>();
    private final GNTextBox             textBox         = new GNTextBox();
    private final GNFloatingButton      btnSale         = new GNFloatingButton();
    private final GNFloatingButton      btnEmployee     = new GNFloatingButton();
    private final GNFloatingButton      btnClose        = new GNFloatingButton();
    private final GNFloatingButton      btnEdit         = new GNFloatingButton();
    private final HBox                  searchActions   = new HBox();

    private final SalePresenter     salePresenter = new SalePresenter();
    private final SalesController   controller;
    private       Sale              model;

    public SearchClientBox(SalesController controller) {
        this.controller = controller;
        configLayout();
//        populateListView();
//        setActions();
    }

    public void setModel ( Sale sale ) {
        this.model = sale;

        if (model.getClient() != null) {
            List<Client> list = listView.getItems().stream().filter(p ->
                    p.getName().equalsIgnoreCase(sale.getClient().getName())).toList();

            listView.getSelectionModel().select(list.get(0));
            listView.scrollTo(listView.getSelectionModel().getSelectedItem());
        }

//        listView.scrollTo(sale.getClient());
    }

    public void switchToConfirm () {
//        if (searchActions.getChildren().contains(btnEdit)) return;
//        searchActions.getChildren().remove(btnSale);
//        searchActions.getChildren().add(1, btnEdit);
    }

    public void resetControls () {
//        if (searchActions.getChildren().contains(btnSale)) return;
//        searchActions.getChildren().remove(btnEdit);
//        searchActions.getChildren().add(1, btnSale);
    }

    private void configLayout () {

        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(0,5,5,5));

        VBox.setVgrow(this, Priority.ALWAYS);

        getChildren().add(body);
        body.getChildren().add(boxHeader);

        boxHeader.setMaxHeight(122);

        header.setMinHeight(60);
        header.setMaxHeight(60);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().addAll("border", "border-b-1");
        boxHeader.getChildren().add(header);

        btnBack.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btnBack.setGraphic(new IconContainer(Icons.ARROW_BACK));
        btnBack.setPrefWidth(50);
        btnBack.setPrefHeight(60);
        btnBack.setMinHeight(60);
        btnBack.getStyleClass().addAll("btn-flat", "border", "border-r-1");

        lblInfo.setText("Selecionar Clientes");
        lblInfo.getStyleClass().addAll("h3");
        lblInfo.setStyle("-fx-text-fill : -base;");
        IconContainer icon = new IconContainer(Icons.ACCOUNT_CIRCLE);
        icon.setStyle("-fx-fill : -info;");
        lblInfo.setGraphic(icon);
        HBox.setMargin(lblInfo, new Insets(0,0,0,10));


        header.getChildren().setAll(btnBack, lblInfo);

//        setSpacing(5D);
//
//        listView.getStyleClass().add("no-border");
//
//        textBox.setFloatPrompt(false);
//        textBox.setLeadIcon(Icons.SEARCH);
//        textBox.setFieldType(FieldType.FILLED);
//        textBox.setTrayAction(TrayAction.CLEAR);
//        textBox.setStyle("-fx-min-height : 50px;");
//
//        VBox.setMargin(textBox, new Insets(10, 0,0,0));
//
//        btnEmployee.setPrefWidth(-1);
//        btnSale.setPrefWidth(-1);
//
//        btnClose.setPrefSize(-1, -1);
//        btnClose.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//        btnClose.setPrefWidth(-1);
//        btnClose.setPrefWidth(-1);
//        btnClose.setGraphic(new IconContainer(Icons.CLEAR));
//
//        btnEmployee.getStyleClass().addAll("btn-secondary", "deep-button");
//        btnClose.getStyleClass().addAll("btn-grapefruit", "deep-button");
//        btnSale.getStyleClass().addAll("deep-button");
//
//        btnEdit.setPrefSize(-1, -1);
//
//        btnEdit.setButtonType(GNButtonType.SEMI_ROUNDED);
//        btnSale.setButtonType(GNButtonType.SEMI_ROUNDED);
//        btnEmployee.setButtonType(GNButtonType.SEMI_ROUNDED);
//        btnClose.setButtonType(GNButtonType.SEMI_ROUNDED);
//
//        btnEdit.setGraphic(new IconContainer(Icons.DONE));
//        btnEdit.getStyleClass().addAll("btn-mint", "deep-button");
//
//        searchActions.setSpacing(5);
//        searchActions.getChildren().setAll(btnClose, btnSale, btnEmployee);
//        btnSale.setGraphic(new IconContainer(Icons.ADD_CIRCLE));
//        btnEmployee.setGraphic(new IconContainer(Icons.ACCOUNT_CIRCLE));
//        searchActions.getStyleClass().addAll("border", "border-t-1");
//
////        searchActions.getChildren().stream().map(m -> (GNButton) m).forEach(f -> f.getStyleClass().addAll("deep-button"));
//        searchActions.setMinHeight(58);
//        searchActions.setAlignment(Pos.CENTER_RIGHT);
////        searchActions.setPadding(new Insets(0,5,0,0));
//
//        this.setPadding(new Insets(0, 5, 0,5));
//
//        this.getChildren().setAll(textBox,  listView, searchActions);
//
//        VBox.setVgrow(listView, Priority.ALWAYS);
//        VBox.setVgrow(this, Priority.ALWAYS);
//
//        textBox.setMinHeight(60);
//        textBox.setPrefHeight(60);

    }

    private void populateListView () {

        listView.setCellFactory(new ListAvatarActionFactory<>(
                event -> {
                    if (event.getClickCount() == 2) {
                        btnSale.getOnAction().handle(new ActionEvent());
                    }
                }
        ));

        ClientPresenter clientPresenter = new ClientPresenter();
        Task<ObservableList<Client>> populate = clientPresenter.createAllElements();

        FilteredList<Client> filteredClients = new FilteredList<>(
                clientPresenter.getElements(), p -> true);

        new Thread(populate).start();

        listView.setItems(filteredClients);

        populate.setOnSucceeded(p -> listView.getSelectionModel().selectFirst());

        textBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filteredClients.setPredicate(p ->
                        p.getName().toLowerCase().contains(newValue.toLowerCase()));
                listView.getSelectionModel().selectFirst();
            }
        });
    }

    private void setActions () {

        btnClose.setOnAction(event -> {
            FadeOut fadeIn = new FadeOut(this);
            fadeIn.setSpeed(5);
            fadeIn.play();

            fadeIn.getTimeline().setOnFinished(e -> {
//                controller.resetColumns();
            });

        });

        btnSale.setOnAction(event -> {

            this.model.setDiscount(BigDecimal.ZERO);
            this.model.setSaleItems(FXCollections.observableArrayList());
            this.model.setClient(listView.getSelectionModel().getSelectedItem());

            if (model.getProfessional() == null)
                model.setProfessional(new ProfessionalPresenter().find(1));

            salePresenter.store(this.model);

            if (salePresenter.persist())
                controller.select(this.model);

            btnClose.getOnAction().handle(new ActionEvent());

        });

        btnEdit.setOnAction(event -> {
            this.model.setClient(listView.getSelectionModel().getSelectedItem());
            btnClose.getOnAction().handle(new ActionEvent());
        });

        btnEmployee.setOnAction(event -> {

//            IView view = window.getViews().get("professionals");
//            ProfessionalsController ctrl = (ProfessionalsController) view.getController();
//
//            ctrl.onEnter();
//            this.model.setClient(listView.getSelectionModel().getSelectedItem());
//            ctrl.updateModel(this.model);
//
//            if (window.getWidth() < 700) {
//                try {
//                    window.navigate(view, true);
//
//                } catch (NavigationException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                controller.setSecondColumn(
//                        window.getViews().getRootFrom("professionals"));
//
//            }
        });
    }
}
