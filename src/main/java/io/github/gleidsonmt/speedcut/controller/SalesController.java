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

import io.github.gleidsonmt.gncontrols.GNFloatingButton;
import io.github.gleidsonmt.gncontrols.GNListView;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.controller.sales.GridTile;
import io.github.gleidsonmt.speedcut.controller.sales.PayActions;
import io.github.gleidsonmt.speedcut.controller.sales.PaymentMethod;
import io.github.gleidsonmt.speedcut.controller.sales.SideNavigation;
import io.github.gleidsonmt.speedcut.core.app.animations.Animations;
import io.github.gleidsonmt.speedcut.core.app.dao.DaoCashier;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.factory.LoadPlaceholder;
import io.github.gleidsonmt.speedcut.core.app.factory.column.AvatarColumnFactory;
import io.github.gleidsonmt.speedcut.core.app.factory.column.MoneyColumnFactory;
import io.github.gleidsonmt.speedcut.core.app.factory.column.ProductColumnFactory;
import io.github.gleidsonmt.speedcut.core.app.factory.column.QuantityColumnFactory;
import io.github.gleidsonmt.speedcut.core.app.factory.row.SaleItemsRowFactory;
import io.github.gleidsonmt.speedcut.core.app.factory.row.SaleRowFactory;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.Popup;
import io.github.gleidsonmt.speedcut.core.app.model.*;
import io.github.gleidsonmt.speedcut.core.app.util.DiscountPopup;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import io.github.gleidsonmt.speedcut.core.app.view.IView;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import io.github.gleidsonmt.speedcut.core.app.view.View;
import io.github.gleidsonmt.speedcut.presenter.ClientPresenter;
import io.github.gleidsonmt.speedcut.presenter.ProfessionalPresenter;
import io.github.gleidsonmt.speedcut.presenter.SaleItemPresenter;
import io.github.gleidsonmt.speedcut.presenter.SalePresenter;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  24/02/2022
 */
public class SalesController extends ResponsiveView {

    @FXML private ScrollPane scroll;
    @FXML private GridPane mainLayout;
    @FXML private HBox boxSalesControl;
    @FXML private StackPane columnAdd;
    @FXML private StackPane columnSales;
    @FXML private GridPane salesAction;
    @FXML private GridPane gridActions;
    @FXML private VBox container;
    @FXML private VBox columnContent;
    @FXML private VBox columnSecondContent;
    @FXML private TableView<Sale> tableSales;
    @FXML private TableColumn<Sale, Number> columnId;
    @FXML private TableColumn<Sale, Professional> columnProfessional;
    @FXML private TableColumn<Sale, Client> columnClient;
    @FXML private Label lbl_hour;
    @FXML private VBox firstColumnContent;
    @FXML private VBox containerSales;

    @FXML public  TableView<SaleItem> saleItems;
    @FXML private TableColumn<SaleItem, Number> columnSaleItemId;
    @FXML private TableColumn<SaleItem, Number> quantityColumn;
    @FXML private TableColumn<SaleItem, TradeItem> itemNameColumn;
    @FXML private TableColumn<SaleItem, BigDecimal> valueColumn;
    @FXML private TableColumn<SaleItem, BigDecimal> totalColumn;

    @FXML private Label lbl_total;
    @FXML private Label lbl_amount;
    @FXML private Label lbl_value;
    @FXML private Label lbl_discount;
    @FXML private Label totalDiscount;
    @FXML private GNFloatingButton discountButton;

    @FXML private Button receive;
    @FXML private Button remove;

    private final SalePresenter salePresenter           = new SalePresenter();
    private final SaleItemPresenter saleItemPresenter   = new SaleItemPresenter();

    private final PayActions        payActions      = new PayActions(this);
    private final PaymentMethod     paymentMethod   = new PaymentMethod(this);

    private final SideNavigation<Client> searchClientBox = new SideNavigation<>(
        this, Icons.ACCOUNT_CIRCLE,
            "Selecionar Clientes",
                new GridTile<>(this, new ClientPresenter())
    );

    private final SideNavigation<Professional> searchProfessionalBox = new SideNavigation<>(
            this, Icons.BADGE,
            "Selecionar Profissional",
            new GridTile<>(this, new ProfessionalPresenter())
    );

    private final Button btnBack = new Button();
    private Node previousNode = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        salesAction.getChildren().stream().map(e -> (Button)e).forEach(Animations::onHoverButton);
        gridActions.getChildren().stream().map(e -> (Button)e).forEach(Animations::onHoverButton);
        boxSalesControl.getChildren().stream().map(e -> (Button)e).forEach(Animations::onHoverButton);

        btnBack.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btnBack.setGraphic(new IconContainer(Icons.ARROW_BACK));
        btnBack.getStyleClass().addAll("btn-flat", "border", "border-r-1");
        btnBack.setPrefWidth(50);
        btnBack.setMinHeight(60);

        Animations.onHoverButton(btnBack);
        runWatch();

        btnBack.setOnAction(event -> resetFirstColumn());

        tableSales.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                searchProfessionalBox.select(newValue.getProfessional().getId());
                searchClientBox.select(newValue.getClient().getId());
            }
        });
    }

    private void runWatch () {

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                while (true) {
                    Platform.runLater(() -> {

                        LocalTime localTime = LocalTime.now();
                        DateTimeFormatter formatter
                                = DateTimeFormatter.ofPattern("HH:mm:ss");
                        lbl_hour.setText(localTime.format(formatter));
                    });

                    Thread.sleep(1000);
                }
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.setName("Task Watch");
        thread.start();

    }

    @FXML
    private void goItems() {
        IView view = views.get("buy");
        container.getChildren().setAll(grow(view.getRoot()));
        view.getController().onEnter();
    }

    @FXML
    private void goControl () {
        try {
            window.navigate("sale_index");
        } catch (NavigationException e) {
            e.printStackTrace();
        }
    }

    private DiscountPopup popup = null;

    @FXML
    private void openDiscount() {
        if (popup == null)
         popup = new DiscountPopup(totalDiscount, lbl_discount, lbl_total, lbl_amount, discountButton);

        if (!popup.isShowing())
            popup.showAndWait(discountButton);
    }


    @FXML
    private void addDefaultSale()  {

        salePresenter.createConnection();
        Sale sale = salePresenter.createDefaultSale();

        if (salePresenter.persist())
            tableSales.getSelectionModel().select(sale);

        IView view = window.getViews().get("buy");

        view.getController().onEnter();

        container.getChildren().setAll(grow(view.getRoot()));

        tableSales.scrollTo(sale);
    }

    @Override
    protected void updateLayout(double width) {

        if (width < BreakPoints.MEDIUM) {

                if (!scroll.getContent().equals(container)) {
                    scroll.setContent(container);
                }

                if (!boxSalesControl.getChildren().contains(btnBack)) {
                    boxSalesControl.getChildren().add(0, btnBack);
                    boxSalesControl.setAlignment(Pos.CENTER_LEFT);
                }

        } else {
            if (!scroll.getContent().equals(mainLayout)) {
                scroll.setContent(mainLayout);

                if (!columnSales.getChildren().contains(container))
                    columnSales.getChildren().setAll(container);

                    columnAdd.getChildren().setAll(firstColumnContent);

                    System.out.println(container.getChildren().size());

                    if (previousNode != null) {
                        if (container.getChildren().size() < 1)
                            container.getChildren().setAll(previousNode);
                    } else {
                        resetSecondColumn();
                    }
                    boxSalesControl.getChildren().remove(btnBack);
                    boxSalesControl.setAlignment(Pos.CENTER);
            }
        }

        if (width < BreakPoints.LARGE) {
            salesAction.setVgap(0);

            salesAction.getChildren().stream().map(each -> (Button) each)
                    .forEach(each -> each.setContentDisplay(ContentDisplay.GRAPHIC_ONLY));

            if (columnId.isVisible()) {
                columnId.setVisible(false);
                columnSaleItemId.setVisible(false);
            }

        } else {
            salesAction.setVgap(5);
            if (!tableSales.getColumns().contains(columnId)) {
                tableSales.getColumns().add(0, columnId);
            }
            if (!saleItems.getColumns().contains(columnSaleItemId)) {
                saleItems.getColumns().add(0, columnSaleItemId);
            }
            if (!columnId.isVisible()) {
                columnId.setVisible(true);
                columnSaleItemId.setVisible(true);
            }

            salesAction.getChildren().stream().map(each -> (Button) each)
                    .forEach(each -> each.setContentDisplay(ContentDisplay.LEFT));

        }
    }

    public void goTableSalesItem() {
        container.getChildren().setAll(containerSales);
        previousNode = containerSales;
    }

    private boolean init = false;
    private final FilteredList<Sale> filteredList = new FilteredList<>(salePresenter.getElements(), p -> true);

    @Override
    public void onEnter() {
        super.onEnter();

        if (new DaoCashier().findOpened() == null) {
            window.getWrapper().getPopup()
                    .content(window.getViews().getRootFrom("open"))
                    .size(400, 400)
                    .show();
        }

        if (columnSales.getChildren().isEmpty()) {
            columnSales.getChildren().add(columnSecondContent);
        }

        if (!init) {
            columnId.setCellValueFactory(param -> param.getValue().idProperty());
            columnProfessional.setCellValueFactory(param -> param.getValue().professionalProperty());
            columnClient.setCellValueFactory(param -> param.getValue().clientProperty());

            columnSaleItemId.setCellValueFactory(param -> param.getValue().idProperty());
            quantityColumn.setCellValueFactory(param -> param.getValue().quantityProperty());

            itemNameColumn.setCellValueFactory(param -> param.getValue().itemProperty());
            valueColumn.setCellValueFactory(param -> param.getValue().getItem().priceProperty());

            totalColumn.setCellValueFactory(param -> param.getValue().totalProperty());

            quantityColumn.setCellFactory(new QuantityColumnFactory<>(lbl_total, lbl_value));

            itemNameColumn.setCellFactory(new ProductColumnFactory<>(window));
            valueColumn.setCellFactory(new MoneyColumnFactory<>());

            totalColumn.setCellFactory(new MoneyColumnFactory<>());

            Task<ObservableList<Sale>> populate = salePresenter.createAllElements();

            columnProfessional.setCellFactory(new AvatarColumnFactory<>());
            columnClient.setCellFactory(new AvatarColumnFactory<>());

            saleItems.setRowFactory(new SaleItemsRowFactory(this));
            tableSales.setRowFactory(new SaleRowFactory(this));

            tableSales.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    saleItems.setItems(newValue.getSaleItems());

                    saleItems.getSelectionModel().selectFirst();
                    lbl_discount.setText(MoneyUtil.format(newValue.getDiscount()));

                    receive.setDisable(newValue.getSaleItems().isEmpty());
                    remove.setDisable(newValue.getSaleItems().isEmpty());

                }
                else {
                    saleItems.setItems(null);
                }
            });

            saleItems.setPlaceholder(new Label("Sem itens na venda."));

            saleItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    lbl_value.setText(MoneyUtil.format(newValue.getTotal()));
                } else lbl_value.setText(MoneyUtil.format(BigDecimal.ZERO));
            });

            lbl_total.textProperty().addListener((observable, oldValue, newValue) -> lbl_amount.setText(
                    MoneyUtil.format(
                            MoneyUtil.get(newValue).subtract(
                            tableSales.getSelectionModel().getSelectedItem().getDiscount()
                    ))
            ));

            tableSales.setItems(filteredList);

            Thread thread = new Thread(populate);
            thread.setName("Loading data table [Professional]");
            thread.setPriority(1);
            thread.start();

            populate.setOnRunning(event -> {
                LoadPlaceholder loadPlaceholder = new LoadPlaceholder();
                tableSales.setPlaceholder(loadPlaceholder);
            });

            populate.setOnSucceeded(event -> {
                tableSales.getSelectionModel().selectFirst();
                if (tableSales.getSelectionModel().getSelectedItem() != null)
                    saleItems.setItems(
                            tableSales.getSelectionModel().getSelectedItem().getSaleItems()
                    );
                tableSales.setPlaceholder(new Label("Itens não encontrados."));
            });

            saleItems.itemsProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    recount();
                }
            });

            init = true;
        }
    }

    @Override
    public void onExit() {
        super.onExit();
    }

    public void recount() {
        final BigDecimal[] _total = { BigDecimal.ZERO };
        saleItems.getItems().forEach(f -> _total[0] = _total[0].add(f.getTotal()));
        lbl_total.setText(MoneyUtil.format(_total[0]));

        lbl_amount.setText(
                MoneyUtil.format(_total[0].subtract(
                        tableSales.getSelectionModel().getSelectedItem().getDiscount()
                ))
        );
    }

    private void add(BigDecimal ac) {
        BigDecimal act = MoneyUtil.get(lbl_total.getText());
        lbl_total.setText(MoneyUtil.format(act.add(ac)));

        lbl_amount.setText(
                MoneyUtil.format(
                        MoneyUtil.get(lbl_total.getText()).subtract(
                        tableSales.getSelectionModel().getSelectedItem().getDiscount()
                ))
        );
    }

    public void addSaleItem(SaleItem saleItem) {
        saleItem.setSale(tableSales.getSelectionModel().getSelectedItem());
        saleItemPresenter.store(saleItem);

        if (salePresenter.persist()) {
            tableSales.getSelectionModel()
                    .getSelectedItem().getSaleItems().add(saleItem);
            add(saleItem.getTotal());
        }
    }

    public void updateItem(SaleItem saleItem) {

        SaleItem _item = saleItems.getItems().stream()
                        .filter(f -> f.getItem().getName().equalsIgnoreCase(saleItem.getItem().getName()))
                                .findAny().orElseGet(SaleItem::new);

        saleItems.getSelectionModel().select(_item.getId());


        int qtd = saleItem.getQuantity() + _item.getQuantity();
        _item.setQuantity(qtd);
//        saleItem.setQuantity(_item.getQuantity());

        saleItemPresenter.update(_item);
        saleItemPresenter.persist();
    }

    @FXML
    public void deleteSaleItem() {
        SaleItem _saleItem = saleItems.getSelectionModel().getSelectedItem();
        if (_saleItem != null) {
            SaleItemPresenter saleItemPresenter = new SaleItemPresenter();
            saleItemPresenter.delete(_saleItem);
            if (saleItemPresenter.persist()) {
                window  .createSnackBar()
                        .message("Sale Item removed.")
                        .show();
            }
        } else {
            window  .createSnackBar()
                    .message("Não existem itens de vendas.")
                    .show();
        }
    }

    @FXML
    public void openProfessionals() {
        container.getChildren().setAll(searchProfessionalBox);

        if (tableSales.getSelectionModel().getSelectedItem() != null) {
            Professional professional = tableSales.getSelectionModel().getSelectedItem().getProfessional();

            searchProfessionalBox.onEnter(professional.getId());
            searchProfessionalBox.select(professional.getId());
            previousNode = searchProfessionalBox;
        }
    }

    @FXML
    public void openClients() {
        container.getChildren().setAll(searchClientBox);
        if (tableSales.getSelectionModel().getSelectedItem() != null) {
            Client professional = tableSales.getSelectionModel().getSelectedItem().getClient();

            searchClientBox.onEnter(professional.getId());
            searchClientBox.select(professional.getId());
            previousNode = searchClientBox;
        }
    }

    @FXML
    private void openReceive () {
        switchPayControls(true);
    }

    public Sale getSelected () {
        return tableSales.getSelectionModel().getSelectedItem();
    }

    public void select (Sale sale) {
        tableSales.getSelectionModel().select(sale);
        tableSales.scrollTo(sale);
    }

    public void switchPayControls(boolean pay) {
        if (pay) {
            columnSecondContent.getChildren().remove(gridActions);
            columnSecondContent.getChildren().add(payActions);
            paymentMethod.setValue(MoneyUtil.format(lbl_amount.getText()));
            columnSecondContent.getChildren().add(2, paymentMethod);
        } else {
            columnSecondContent.getChildren().add(gridActions);
            columnSecondContent.getChildren().remove(payActions);
            columnSecondContent.getChildren().remove(paymentMethod);
        }
    }

    void resetFirstColumn () {
        container.getChildren().clear();
        container.getChildren().add(firstColumnContent);
        VBox.setVgrow(firstColumnContent, Priority.ALWAYS);
    }

    public void resetSecondColumn () {
        container.getChildren().clear();
        container.getChildren().setAll(containerSales);
    }

    private Node grow(Node node) {
        VBox.setVgrow(node, Priority.ALWAYS);
        return node;
    }

}
