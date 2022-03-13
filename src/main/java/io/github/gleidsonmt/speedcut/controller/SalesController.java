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
import io.github.gleidsonmt.gncontrols.GNFloatingButton;
import io.github.gleidsonmt.gncontrols.GNListView;
import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.gncontrols.options.FieldType;
import io.github.gleidsonmt.gncontrols.options.GNButtonType;
import io.github.gleidsonmt.gncontrols.options.TrayAction;
import io.github.gleidsonmt.speedcut.core.app.animations.Animations;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.factory.*;
import io.github.gleidsonmt.speedcut.core.app.model.*;
import io.github.gleidsonmt.speedcut.core.app.util.DiscountPopup;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import io.github.gleidsonmt.speedcut.presenter.ClientPresenter;
import io.github.gleidsonmt.speedcut.presenter.ProfessionalPresenter;
import io.github.gleidsonmt.speedcut.presenter.SaleItemPresenter;
import io.github.gleidsonmt.speedcut.presenter.SalePresenter;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Camera;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.math.BigDecimal;
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
    @FXML private GridPane gridActions;
    @FXML private VBox boxSales;
    @FXML private TableView<Sale> tableSales;
    @FXML private TableColumn<Sale, Number> columnId;
    @FXML private TableColumn<Sale, Professional> columnProfessional;
    @FXML private TableColumn<Sale, Client> columnClient;

    @FXML private TableView<SaleItem> saleItems;
    @FXML private TableColumn<SaleItem, Number> columnSaleItemId;
    @FXML private TableColumn<SaleItem, Number> quantityColumn;
    @FXML private TableColumn<SaleItem, Item> itemNameColumn;
    @FXML private TableColumn<SaleItem, BigDecimal> valueColumn;
    @FXML private TableColumn<SaleItem, BigDecimal> totalColumn;

    @FXML private Label lbl_total;
    @FXML private Label lbl_amount;
    @FXML private Label lbl_value;
    @FXML private Label lbl_discount;
    @FXML private Label totalDiscount;
    @FXML private GNFloatingButton discountButton;

    @FXML private Button include;
    @FXML private Button btnSearchSale;

    private final GNListView<Client>    listView    = new GNListView<>();
    private final VBox                  boxSearch   = new VBox();

    private final SalePresenter salePresenter           = new SalePresenter();
    private final SaleItemPresenter saleItemPresenter   = new SaleItemPresenter();

    private boolean boxSearchOpened = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        salesAction.getChildren().stream().map(e -> (Button)e).forEach(Animations::onHoverButton);
        gridActions.getChildren().stream().map(e -> (Button)e).forEach(Animations::onHoverButton);
    }

    @FXML
    private void openPopupBuys() throws NavigationException {

        if (window.getWidth() > 600) {
            window.getRoot().getWrapper() // background for open popups, drawers..
                    .openPopup("buy", 600, 600); // open new popup with buy view as content
        } else {
            window.navigate("buy", true);
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

        try {
            openPopupBuys();
        } catch (NavigationException e) {
            e.printStackTrace();
        }
//        sales.add(sale);
//        window.getRoot().getWrapper().openDrawerRight("sale");
    }

    @FXML
    private void deleteSale() {
        Sale sale = tableSales.getSelectionModel().getSelectedItem();

        if (sale != null) {
//            salePresenter.delete(sale);
//            salePresenter.persist();
//
            window.getRoot().createSnackBar()
                    .type("done")
                    .onAction(event -> {
//                        filteredList.getSource().add(sale);
                        salePresenter.createConnection();
                        salePresenter.store(sale);
                        salePresenter.persist();
//                        System.out.println(sale.getId());
                    })
                    .onFinished(event -> {
                        salePresenter.delete(sale);
                        salePresenter.persist();
                    })
                    .message("Venda removida")
                    .show();

//            filteredList.getSource().remove(sale);

        }
    }

    @FXML
    private void openSearch() {
        if (boxSearch.getChildren().size() < 1) {
            createContainer();
            openSearchBox();
        } else if (!boxSearchOpened) {
            openSearchBox();
        }
    }

    private final Timeline timeline = new Timeline();
    private final GNTextBox textBox = new GNTextBox();

    private final GNButton btnSale = new GNButton("Adicionar Venda");
    private final GNButton btnEmployee = new GNButton("Selecionar Funcionario");
    private final GNButton btnClose = new GNButton("Close");

    private void openSearchBox() {
        timeline.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(
                        textBox.maxWidthProperty(), 0
                )),
                new KeyFrame(Duration.millis(300), new KeyValue(
                        textBox.maxWidthProperty(), columnAdd.getWidth()
                )),
                new KeyFrame(Duration.ZERO, new KeyValue(
                        listView.maxHeightProperty(), 0
                )),
                new KeyFrame(Duration.millis(200), new KeyValue(
                        listView.maxHeightProperty(), 250
                ))
        );
        timeline.setOnFinished(event -> {
            if (!textBox.isFocused()) textBox.requestFocus();
            boxSearchOpened = true;

        });


        timeline.play();

        columnAdd.getChildren().remove(salesAction);
        columnAdd.getChildren().add(0, boxSearch);
    }

    private void closeSearchBox() {
        timeline.getKeyFrames().setAll(
//                    new KeyFrame(Duration.ZERO, new KeyValue(
//                            textBox.maxWidthProperty(), 0
//                    )),
//                    new KeyFrame(Duration.millis(300), new KeyValue(
//                            textBox.maxWidthProperty(), columnAdd.getWidth()
//                    )),
                new KeyFrame(Duration.ZERO, new KeyValue(
                        listView.maxHeightProperty(), 250
                )),
                new KeyFrame(Duration.millis(200), new KeyValue(
                        listView.maxHeightProperty(), 0
                ))
        );

    }

    private void createContainer() {

        boxSearch.setAlignment(Pos.CENTER_LEFT);
        boxSearch.setSpacing(5D);
        textBox.setFloatPrompt(false);
        textBox.setLeadIcon(Icons.SEARCH);
        textBox.setFieldType(FieldType.FILLED);
        textBox.setTrayAction(TrayAction.CLEAR);
        textBox.setStyle("-fx-min-height : 50px;");

        btnEmployee.setPrefWidth(-1);
        btnSale.setPrefWidth(-1);

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

//        salesAction.getChildren().setAll(textBox);
        GridPane.setConstraints(textBox, 0,0, 3, 1, HPos.LEFT, VPos.CENTER, Priority.SOMETIMES, Priority.ALWAYS);
        listView.setCellFactory(new ListWithGraphicFactory<>());

        ClientPresenter clientPresenter = new ClientPresenter();
        Task<ObservableList<Client>> populate = clientPresenter.createAllElements();
        FilteredList<Client> filteredClients = new FilteredList<>(clientPresenter.getElements(), p -> true);
        new Thread(populate).start();

        listView.setItems(filteredClients);

        populate.setOnSucceeded(p -> listView.getSelectionModel().selectFirst());

        textBox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filteredClients.setPredicate(p -> p.getName().toLowerCase().contains(newValue.toLowerCase()));
                listView.getSelectionModel().selectFirst();
            }
        });

        btnClose.setOnAction(event -> {
            closeSearchBox();
            timeline.setOnFinished(e -> {
                columnAdd.getChildren().remove( boxSearch );
                columnAdd.getChildren().add(0, salesAction);
            });
            timeline.play();
            boxSearchOpened = false;

        });

        btnSale.setOnAction(event -> {
            Sale sale = new Sale();

            Client client = listView.getSelectionModel().getSelectedItem();

            sale.setClient(client);
            sale.setProfessional(new ProfessionalPresenter().find(1));
            sale.setDiscount(BigDecimal.ZERO);
            sale.setSaleItems(FXCollections.observableArrayList());

            salePresenter.store(sale);

            if (salePresenter.persist())
                tableSales.getSelectionModel().select(sale);

            try {
                openPopupBuys();
            } catch (NavigationException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void updateLayout(double width) {

        if (width < 900) {
            scroll.setContent(columnAdd);
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

    private boolean init = false;

    FilteredList<Sale> filteredList = new FilteredList<>(salePresenter.getElements(), p -> true);


    @Override
    public void onEnter() {
        super.onEnter();


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
            itemNameColumn.setCellFactory(new ProductColumnFactory<>());
            valueColumn.setCellFactory(new MoneyColumnFactory<>());

            totalColumn.setCellFactory(new MoneyColumnFactory<>());

            Task<ObservableList<Sale>> populate = salePresenter.createAllElements();

            columnProfessional.setCellFactory(new AvatarColumnFactory<>());
            columnClient.setCellFactory(new AvatarColumnFactory<>());

            tableSales.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    saleItems.setItems(newValue.getSaleItems());
                    saleItems.getSelectionModel().selectFirst();
                    lbl_discount.setText(MoneyUtil.format(newValue.getDiscount()));
                }
                else saleItems.setItems(null);
            });

            saleItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    lbl_value.setText(MoneyUtil.format(newValue.getTotal()));
                } else lbl_value.setText(MoneyUtil.format(BigDecimal.ZERO));
            });

            lbl_total.textProperty().addListener((observable, oldValue, newValue) -> {
                lbl_amount.setText(
                        MoneyUtil.format(
                                MoneyUtil.get(newValue).subtract(
                                tableSales.getSelectionModel().getSelectedItem().getDiscount()
                        ))
                );
            });

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
//                for (Sale sale : populate.getValue()) {
//                    sales.add(sale);
//                    tableSales.getSelectionModel().selectFirst();
//
//                }
                tableSales.setPlaceholder(new Label("Itens não encontrados."));
            });

            saleItems.itemsProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    recount();
                }
            });
            init = true;
        }

        textBox.getEditor().requestFocus();
        textBox.requestFocus();

    }


    @Override
    public void onExit() {
        super.onExit();
    }

    public void recount() {
        final BigDecimal[] _total = {BigDecimal.ZERO};
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

        System.out.println(saleItem.getSale());

        if (salePresenter.persist()) {
            System.out.println(tableSales.getSelectionModel().getSelectedItem());
            tableSales.getSelectionModel()
                    .getSelectedItem().getSaleItems().add(saleItem);
            add(saleItem.getTotal());
        }
    }


}
