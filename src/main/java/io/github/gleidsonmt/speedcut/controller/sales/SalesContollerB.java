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

import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  24/02/2022
 */
public class SalesContollerB extends ResponsiveView {

//    @FXML private ScrollPane scroll;
//    @FXML private GridPane mainLayout;
//    @FXML private HBox boxSalesControl;
//    @FXML private StackPane columnAdd;
//    @FXML private StackPane columnSales;
//    @FXML private GridPane salesAction;
//    @FXML private GridPane gridActions;
//    @FXML private VBox transactionBox;
//    @FXML private VBox container;
//    @FXML private TableView<Sale> tableSales;
//    @FXML private TableColumn<Sale, Number> columnId;
//    @FXML private TableColumn<Sale, Professional> columnProfessional;
//    @FXML private TableColumn<Sale, Client> columnClient;
//    @FXML private Label lbl_hour;
//    @FXML private VBox firstColumnContent;
//    @FXML private VBox containerSales;
//
//    @FXML public  TableView<SaleItem> saleItems;
//    @FXML private TableColumn<SaleItem, Number> columnSaleItemId;
//    @FXML private TableColumn<SaleItem, Number> quantityColumn;
//    @FXML private TableColumn<SaleItem, TradeItem> itemNameColumn;
//    @FXML private TableColumn<SaleItem, BigDecimal> valueColumn;
//    @FXML private TableColumn<SaleItem, BigDecimal> totalColumn;
//
//    @FXML private Label lbl_total;
//    @FXML private Label lbl_subtotal;
//    @FXML private Label lbl_discount;
//    @FXML private Label totalDiscount;
//    @FXML private GNFloatingButton discountButton;
//
//    @FXML private Button receive;
//    @FXML private Button remove;
//    @FXML private Button include;
//    @FXML private HBox infoBox;
//
//    @FXML private StackPane boxTable;
//
////    private final SaleItemPresenter saleItemPresenter   = new SaleItemPresenter();
//    private final CashierPresenter  cashierPresenter    = new CashierPresenter();
//
//    private final PayActions        payActions      = new PayActions(this);
//    private final PaymentBox paymentMethod   = new PaymentBox(this);
//
//
//    private final SideNavigation<Client> searchClientBox = new SideNavigation<>(
//        this, Icons.ACCOUNT_CIRCLE,
//            "Selecionar Clientes",
//                new GridTile<>(this, Repositories.get(Client.class))
//    );
//
//    private final SideNavigation<Professional> searchProfessionalBox = new SideNavigation<>(
//            this, Icons.BADGE,
//            "Selecionar Profissional",
//            new GridTile<>(this, Repositories.get(Professional.class))
//    );
//
//    private final Button btnBack = new Button();
//    private Node previousNode = null;
//
//    private final RepoSaleImpl repoSales   = (RepoSaleImpl)    Provider.of(Sale.class);
//
//    private final Repository<SaleItem> repoSaleItem   = Repositories.get(SaleItem.class);
//    private final RepoCashierImpl repoCashier = (RepoCashierImpl) Repositories.<Cashier>get(Cashier.class);
//
//    private  FilteredList<Sale> filteredList;
//
//    private final Transaction transaction = new Transaction();
//    private boolean init = false;
//
//
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//
//        if (repoCashier.getOpened() != null)
//            filteredList = new FilteredList<>(
//                repoCashier.getOpened().getActiveSales(), p -> true);
//
//        salesAction.getChildren().stream().map(e -> (Button)e).forEach(Animations::onHoverButton);
//        gridActions.getChildren().stream().map(e -> (Button)e).forEach(Animations::onHoverButton);
//        boxSalesControl.getChildren().stream().map(e -> (Button)e).forEach(Animations::onHoverButton);
//
//        btnBack.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
//        btnBack.setGraphic(new IconContainer(Icons.ARROW_BACK));
//        btnBack.getStyleClass().addAll("btn-flat", "border", "border-r-1");
//        btnBack.setPrefWidth(50);
//        btnBack.setMinHeight(60);
//
//        Animations.onHoverButton(btnBack);
//        runWatch();
//
//        btnBack.setOnAction(event -> resetFirstColumn());
//
////        tableSales.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
////            if (newValue != null) {
////                searchProfessionalBox.select(newValue.getProfessional().getId());
////                searchClientBox.select(newValue.getClient().getId());
////            }
////        });
    }
//
//    private void runWatch () {
//
//        Task task = new Task() {
//            @Override
//            protected Object call() throws Exception {
//                while (true) {
//                    Platform.runLater(() -> {
//
//                        LocalTime localTime = LocalTime.now();
//                        DateTimeFormatter formatter
//                                = DateTimeFormatter.ofPattern("HH:mm:ss");
//                        lbl_hour.setText(localTime.format(formatter));
//                    });
//
//                    Thread.sleep(1000);
//                }
//            }
//        };
//
//        Thread thread = new Thread(task);
//        thread.setDaemon(true);
//        thread.setName("Task Watch");
//        thread.start();
//
//    }
//
//    @FXML
//    private void goItems() {
//        IView view = app.window.getViews().get("buy");
//        container.getChildren().setAll(grow(view.getRoot()));
//        view.getController().onEnter();
//    }
//
//    @FXML
//    private void goControl () {
//        try {
//            app.window.navigate("sale_index", true);
//        } catch (NavigationException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private DiscountPopup popup = null;
//
//    @FXML
//    private void openDiscount() {
//        if (popup == null)
//
//         popup = new DiscountPopup(totalDiscount, lbl_discount, lbl_total, lbl_subtotal, discountButton);
//
//        if (!popup.isShowing())
//            popup.showAndWait(discountButton);
//    }
//
//    @FXML
//    private void addDefaultSale()  {
//
//        Sale sale = repoSales.createDefault();
//        repoSales.connect();
//        repoSales.store(sale);
//
//        if (repoSales.persist()) {
//            sale.setCashier(repoCashier.getOpened());
//            repoCashier.getOpened().getActiveSales().add(sale);
//            tableSales.getSelectionModel().select(sale);
//        }
////
//        IView view = app.window.getViews().get("buy");
//        view.getController().onEnter();
//        container.getChildren().setAll(grow(view.getRoot()));
//        tableSales.scrollTo(sale);
//    }
//
    @Override
    protected void updateLayout(double width) {
//
//        if (width < BreakPoints.MEDIUM) {
//
//                if (!scroll.getContent().equals(container)) {
//                    scroll.setContent(container);
//                }
//
//                if (!boxSalesControl.getChildren().contains(btnBack)) {
//                    boxSalesControl.getChildren().add(0, btnBack);
//                    boxSalesControl.setAlignment(Pos.CENTER_LEFT);
//                }
//
//        } else {
//            if (!scroll.getContent().equals(mainLayout)) {
//                scroll.setContent(mainLayout);
//
//                if (!columnSales.getChildren().contains(container))
//                    columnSales.getChildren().setAll(container);
//
//                    columnAdd.getChildren().setAll(firstColumnContent);
//
//
//                    if (previousNode != null) {
//                        if (container.getChildren().size() < 1)
//                            container.getChildren().setAll(previousNode);
//                    } else {
//                        resetSecondColumn();
//                    }
//                    boxSalesControl.getChildren().remove(btnBack);
//                    boxSalesControl.setAlignment(Pos.CENTER);
//            }
//        }
//
//        if (width < BreakPoints.LARGE) {
//            salesAction.setVgap(0);
//
//            salesAction.getChildren().stream().map(each -> (Button) each)
//                    .forEach(each -> each.setContentDisplay(ContentDisplay.GRAPHIC_ONLY));
//
//            if (columnId.isVisible()) {
//                columnId.setVisible(false);
//                columnSaleItemId.setVisible(false);
//            }
//
//        } else {
//            salesAction.setVgap(5);
//            if (!tableSales.getColumns().contains(columnId)) {
//                tableSales.getColumns().add(0, columnId);
//            }
//            if (!saleItems.getColumns().contains(columnSaleItemId)) {
//                saleItems.getColumns().add(0, columnSaleItemId);
//            }
//            if (!columnId.isVisible()) {
//                columnId.setVisible(true);
//                columnSaleItemId.setVisible(true);
//            }
//
//            salesAction.getChildren().stream().map(each -> (Button) each)
//                    .forEach(each -> each.setContentDisplay(ContentDisplay.LEFT));
//
//        }
    }

//    public void goTableSalesItem() {
//        container.getChildren().setAll(containerSales);
//        previousNode = containerSales;
//    }


    @Override
    public void onEnter() {
        super.onEnter();

//        if (cashierPresenter.getOpened() == null) {
//
////            app.window.getWrapper().getPopup()
////                    .content(app.window.getViews().getRootFrom("open"))
////                    .size(400, 400)
////                    .show();
//
//        }
//
//        System.out.println("oxi");
//        if (!init) {
//
//            columnId.setCellValueFactory(param -> param.getValue().idProperty());
//            columnProfessional.setCellValueFactory(param -> param.getValue().professionalProperty());
//            columnClient.setCellValueFactory(param -> param.getValue().clientProperty());
////
//            columnSaleItemId.setCellValueFactory(param -> param.getValue().idProperty());
//            quantityColumn.setCellValueFactory(param -> param.getValue().quantityProperty());
//
//            itemNameColumn.setCellValueFactory(param -> param.getValue().itemProperty());
//            valueColumn.setCellValueFactory(param -> param.getValue().getItem().priceProperty());
//
//            totalColumn.setCellValueFactory(param -> param.getValue().totalProperty());
//
//            quantityColumn.setCellFactory(new QuantityColumnFactory<>(this));
//
//            itemNameColumn.setCellFactory(new ProductColumnFactory<>());
//            valueColumn.setCellFactory(new MoneyColumnFactory<>());
//
//            totalColumn.setCellFactory(new MoneyColumnFactory<>());
//
//            columnProfessional.setCellFactory(new AvatarColumnFactory<>());
//            columnClient.setCellFactory(new AvatarColumnFactory<>());
////
//            saleItems.setRowFactory(new SaleItemsRowFactory(this));
//            tableSales.setRowFactory(new SaleRowFactory(this));
//
//            tableSales.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
//                if (newValue != null) {
//
//                    new Thread(popupalteItems(newValue.getSaleItems())).start();
//
//                    saleItems.getSelectionModel().selectFirst();
//
////                    if (saleItems != null) {
////                        if (saleItems.getItems().isEmpty())
//                            saleItems.setPlaceholder(new Label("Sem itens na venda."));
////                    }
//
////                    Label placeSale = new Label("Sem itens na venda.");
////
////                    saleItems.setPlaceholder(
////                            saleItems == null ? saleItems.getItems().isEmpty() ?  :
////                    );
//                }
//                else {
//                    saleItems.getItems().clear();
//                    saleItems.setPlaceholder(new Label("Sem itens na venda."));
//                }
//            });
////
//            tableSales.setItems(filteredList);
//
//            Task<ObservableList<Sale>> populate = repoCashier.fetchActiveSales();
//
//            Thread thread = new Thread(populate);
//            thread.setName("Loading data table [Sales]");
//            thread.setPriority(1);
//            thread.start();
//
//
//            populate.setOnRunning(event -> {
////                LoadPlaceholder loadPlaceholder = new LoadPlaceholder();
////                tableSales.setPlaceholder(loadPlaceholder);
////
////                LoadPlaceholder ld = new LoadPlaceholder();
////                saleItems.setPlaceholder(ld);
//            });
////
//            populate.setOnSucceeded(event -> {
//
//                if (tableSales.getItems().isEmpty()) {
//                    tableSales.setPlaceholder(new Label("Sem vendas ativas."));
//                    saleItems.setPlaceholder(new Label("Sem items de venda."));
//                } else {
//                    tableSales.getSelectionModel().selectFirst();
//                }
//
//
//
//           });
//
//            configDisablePayButtons(receive, remove);
//
//            include.disableProperty().bind(
//                    Bindings.isNull(
//                            tableSales.getSelectionModel().selectedItemProperty()
//                    )
//            );
//
//            remove.disableProperty().bind(
//                    Bindings.isNull(
//                            saleItems.getSelectionModel().selectedItemProperty()
//                    )
//            );
//
//
//            lbl_subtotal.textProperty().bindBidirectional(
//                    transaction.subtotalProperty(),
//                    new MoneyStringConverter()
//            );
//
//            lbl_total.textProperty().bindBidirectional(
//                    transaction.totalProperty(),
//                    new MoneyStringConverter()
//            );
//
//            lbl_discount.textProperty().bindBidirectional(
//                    transaction.discountProperty(),
//                    new MoneyStringConverter()
//            );
//
//            init = true;
//        }
    }
//
//    private synchronized Task<ObservableList<SaleItem>> popupalteItems(ObservableList<SaleItem> items) {
//        return new Task<>() {
//
//            @Override
//            protected ObservableList<SaleItem> call() throws Exception {
//
//                if (!saleItems.getItems().isEmpty()) {
//                    Platform.runLater(() -> {
//                        saleItems.getItems().clear();
//                        transaction.reset();
//                    });
//                }
//
//                for (SaleItem item : items) {
//                    Thread.sleep(50);
//
//                    item.getProperties().put("init", false);
//
//
//                    BigDecimal total = item.getItem().getPrice().multiply(
//                      BigDecimal.valueOf(item.getQuantity())
//                    );
//
//                    Platform.runLater(() -> {
//                        saleItems.getItems().add(item);
//                        transaction.add(total);
////                        transaction.addSubtotal(total);
//                    });
//
//                }
//
//                return null;
//            }
//        };
//    }
//
//    private void configDisablePayButtons(Button... buttons) {
//        for (Button button : buttons) {
//
//            button.disableProperty().bind(
//                    Bindings.isNull(tableSales.getSelectionModel().selectedItemProperty())
//                            .or(Bindings.isEmpty(saleItems.getItems()))
//            );
//
//        }
//
//    }
//
//    @Override
//    public void onExit() {
//        super.onExit();
//    }
//
//    public Transaction getTransaction() {
//        return transaction;
//    }
//
//    public void addSaleItem(SaleItem saleItem) {
//
//        saleItem.setSale(tableSales.getSelectionModel().getSelectedItem());
//
//        boolean test = saleItems.getItems().stream().anyMatch(
//                math -> math.getItem().getName().equalsIgnoreCase(saleItem.getItem().getName()));
//
//        if (!test) {
//            saleItems.getItems().add(saleItem);
//            getSelected().getSaleItems().add(saleItem);
//        }
//
//        System.out.println("saleItem = " + saleItem.getTotal());
//        transaction.add(saleItem.getTotal());
//
//        repoSaleItem.connect();
//        repoSaleItem.put(saleItem);
//
//        if (repoSaleItem.persist()) {
//            app.window.createSnackBar()
//                    .message("#" + saleItem.getId() + " | Adicionado")
//                    .show();
//        }
//    }
//
//    @FXML
//    public void deleteSaleItem() {
//
//        SaleItem _saleItem = saleItems.getSelectionModel().getSelectedItem();
//
//        if (_saleItem != null) {
//
//            Repository<SaleItem> repo = Repositories.get(SaleItem.class);
//
//            repo.connect();
//            repo.delete(_saleItem);
//
//            if (repo.persist()) {
//                app.window  .createSnackBar()
//                        .message("Sale Item removed.")
//                        .show();
//
//                transaction.subtract(_saleItem.getItem().getPrice());
//
//                saleItems.getItems().remove(_saleItem);
//            }
//            repo.disconnect();
//        } else {
//            app.window  .createSnackBar()
//                    .message("Não existem itens de vendas.")
//                    .show();
//        }
//    }
//
//    @FXML
//    public void openProfessionals() {
//        container.getChildren().setAll(searchProfessionalBox);
//
//        setNext(null);
//
//        if (tableSales.getSelectionModel().getSelectedItem() != null) {
//            Professional professional = tableSales.getSelectionModel().getSelectedItem().getProfessional();
//
//            searchProfessionalBox.onEnter(professional.getId(), next);
//            searchProfessionalBox.select(professional.getId());
//            previousNode = searchProfessionalBox;
//        }
//    }
//
//    @FXML
//    public void openClients() {
//        container.getChildren().setAll(searchClientBox);
//        if (tableSales.getSelectionModel().getSelectedItem() != null) {
//            Client client = tableSales.getSelectionModel().getSelectedItem().getClient();
//
//            searchClientBox.onEnter(client.getId(), next);
//            searchClientBox.select(client.getId());
//            previousNode = searchClientBox;
//        }
//    }
//
//    @FXML
//    private void openReceive () {
//        switchPayControls(true);
//    }
//
//    public Sale getSelected () {
//        return tableSales.getSelectionModel().getSelectedItem();
//    }
//
//    private String next;
//
//    public void setNext(String next) {
//        this.next = next;
//    }
//
//    public String getNext() {
//        return next;
//    }
//
//    public void removeSale (Sale sale) {
//        repoCashier.getOpened().getActiveSales().remove(sale);
//    }
//
//    public void select (Sale sale) {
//        tableSales.getSelectionModel().select(sale);
//        tableSales.scrollTo(sale);
//    }
//
//    public void switchPayControls(boolean pay) {
//        if (pay) {
//
//
//            transactionBox.getChildren().remove(gridActions);
//            transactionBox.getChildren().add(payActions);
//
//            paymentMethod.setValue(MoneyUtil.format(lbl_total.getText()));
//            containerSales.getChildren().add(2, paymentMethod);
//
////            payActions.
//
//        } else {
//            containerSales.getChildren().add(gridActions);
//            transactionBox.getChildren().remove(payActions);
//            transactionBox.getChildren().remove(paymentMethod);
//        }
//    }
//
//    public void animationControls() {
//
//        FadeIn fade = new FadeIn(boxTable);
//        fade.play();
//
//        Flip tada = new Flip(boxTable);
//        tada.setSpeed(1.5);
//        tada.play();
//
//        FadeOut fadeOut = new FadeOut(boxTable);
//        fadeOut.play();
//
//        tada.getTimeline().setOnFinished(event -> {
//
//        });
//
//    }
//
//    void resetFirstColumn () {
//        container.getChildren().clear();
//        container.getChildren().add(firstColumnContent);
//        VBox.setVgrow(firstColumnContent, Priority.ALWAYS);
//    }
//
//    public void resetSecondColumn () {
//        container.getChildren().clear();
//        container.getChildren().setAll(containerSales);
//    }
//
//    private Node grow(Node node) {
//        VBox.setVgrow(node, Priority.ALWAYS);
//        return node;
//    }

}
