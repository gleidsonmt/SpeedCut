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

package io.github.gleidsonmt.speedcut.controller.sales.main;

import io.github.gleidsonmt.speedcut.core.app.dao.*;
import io.github.gleidsonmt.speedcut.core.app.model.*;
import io.github.gleidsonmt.speedcut.core.app.util.DefaultCreator;
import io.github.gleidsonmt.speedcut.core.app.view.BreakPoints;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.ActionView;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IView;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.*;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  24/02/2022
 */
public class SalesController extends ResponsiveView {

    @FXML private VBox container;
    @FXML private VBox containerSales;

    private final Transaction transaction = new Transaction();

    /// Beggin

    @FXML private SalesColumnController salesColumnController;
    @FXML private ItemsColumnController itemsColumnController;

    @FXML private HBox mainLayout;

    @FXML private StackPane firstColumn;
    @FXML private StackPane secondColumn;
    @FXML private StackPane itemsColumn;
    @FXML private StackPane salesColumn;

    private ObservableList<Node> oldViews = FXCollections.observableArrayList();

    public ObservableList<Node> getOldViews() {
        return oldViews;
    }

    public void remove() {
        if (!getOldViews().isEmpty()) {
            if (getOldViews().size() > 1) {

                oldViews.remove(oldViews.get(oldViews.size() - 1)); // remove o primeiro
                setSecondColumn(oldViews.get(oldViews.size() - 1)); // navega para o anterior

            } else {
                oldViews.remove(oldViews.get(oldViews.size() - 1));
                resetSecondColumn();
            }
        } else resetSecondColumn();
    }

    public void setFirstColumn(Node node) {
        firstColumn.getChildren().setAll(node);
    }

    public void setSecondColumn(@NotNull IView view) {
        secondColumn.getChildren().setAll(view.getRoot());
    }

    public void setSecondColumn(@NotNull Node view) {
        secondColumn.getChildren().setAll(view);
    }

    public int getCols() {
        return mainLayout.getChildren().size();
    }

    public void resetSecondColumn () {
        secondColumn.getChildren().setAll(itemsColumn);
    }

    public void resetFirstColumn () {
        firstColumn.getChildren().setAll(salesColumn);
    }

    public ReadOnlyObjectProperty<Sale> selectedProperty() {
        return salesColumnController.seletedItemProperty();
    }

    @FXML
    private void goItems() {
        IView view = context.getRoutes().getView("buy");
        container.getChildren().setAll(grow(view.getRoot()));
        view.getController().onEnter();


        oldViews.add(view.getRoot());

    }


    @Override
    protected void updateLayout(double width) {

        if (width < BreakPoints.MEDIUM) {

            if (!mainLayout.getChildren().contains(secondColumn)) return;

            mainLayout.getChildren().remove(secondColumn);

        } else {


            if (mainLayout.getChildren().contains(secondColumn)) return;
                mainLayout.getChildren().add(secondColumn);

            if (!firstColumn.getChildren().contains(salesColumn)) {
                secondColumn.getChildren().setAll(firstColumn.getChildren()); // switch first to second
                firstColumn.getChildren().setAll(salesColumn);
            }

//            secondColumn.toBack();
//            resetFirstColumn();

        }
    }

    @Override
    public void onEnter() {
        super.onEnter();

        salesColumnController.work();
        itemsColumnController.work();

//        if (cashierPresenter.getOpened() == null) {
//
////            app.window.getWrapper().getPopup()
////                    .content(app.window.getViews().getRootFrom("open"))
////                    .size(400, 400)
////                    .show();
//        }
//
//        if (!init) {
//            tableSales.setRowFactory(new SaleRowFactory(this));
//            init = true;
//        }

    }

    @Override
    public void onExit() {
        super.onExit();
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void addSaleItem(SaleItem saleItem, boolean create) {

        RepoSaleImpl repoSale = (RepoSaleImpl) Repositories.<Sale>get(Sale.class);
        RepoSaleItemImpl repoSaleItem = (RepoSaleItemImpl) Repositories.<SaleItem>get(SaleItem.class);

        Sale sale = null;

        if (create) { // se nao existir venda

            sale = new Sale();
            sale.setActive(true);
            sale.setProfessional(DefaultCreator.createProfessional());
            sale.setClient(DefaultCreator.createClient());

            sale.setCashier(context.getCashier());
            saleItem.setSale(sale);
            sale.setSaleItems(FXCollections.observableArrayList(saleItem));

            repoSale.put(sale);

            repoSaleItem.put(saleItem);

            repoSale.persist();
            context.getCashier().getActiveSales().add(sale);

            select(sale);


        } else { // se existir venda adcionar item

            sale = getSaleSelected();

            saleItem.setSale(sale);

            boolean test = itemsColumnController.getItems().stream().anyMatch(
                math -> math.getTradeItem().getName().equalsIgnoreCase(saleItem.getTradeItem().getName()));

            if (!test) {

                sale.getSaleItems().addAll(saleItem); // add in sales
                getSaleItems().add(saleItem); // add in table

            }

            repoSaleItem.put(saleItem);
            repoSaleItem.persist();

        }
    }

    @FXML
    public void openProfessionals() {

    }


    public void pay() {
        itemsColumnController.pay();
    }

    public void closePaymentBox() {
        itemsColumnController.closePaymentBox();
    }

    public ObservableList<SaleItem> getSaleItems() {
        return itemsColumnController.getItems();
    }

    public void deleteSaleItem() {
        itemsColumnController.deleteSaleItem();
    }

    public Sale getSaleSelected () {
        return salesColumnController.getSelected();
    }

    public SaleItem getItemSelected() {
        return itemsColumnController.getSelected();
    }

    public void select (Sale sale) {
        salesColumnController.select(sale);
    }

    private Node grow(Node node) {
        VBox.setVgrow(node, Priority.ALWAYS);
        return node;
    }

}
