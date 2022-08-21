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

import animatefx.animation.FadeOut;
import animatefx.animation.Tada;
import io.github.gleidsonmt.speedcut.controller.PaymentType;
import io.github.gleidsonmt.speedcut.controller.sales.index.IndexController;
import io.github.gleidsonmt.speedcut.controller.sales.main.componets.PayActions;
import io.github.gleidsonmt.speedcut.controller.sales.main.componets.PaymentBox;
import io.github.gleidsonmt.speedcut.core.app.animations.Animations;
import io.github.gleidsonmt.speedcut.core.app.converters.MoneyStringConverter;
import io.github.gleidsonmt.speedcut.core.app.dao.RepoTransactionImpl;
import io.github.gleidsonmt.speedcut.core.app.dao.Repositories;
import io.github.gleidsonmt.speedcut.core.app.dao.Repository;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.factory.column.MoneyColumnFactory;
import io.github.gleidsonmt.speedcut.core.app.factory.column.ProductColumnFactory;
import io.github.gleidsonmt.speedcut.core.app.factory.column.QuantityColumnFactory;
import io.github.gleidsonmt.speedcut.core.app.factory.row.SaleItemsRowFactory;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.SnackBar;
import io.github.gleidsonmt.speedcut.core.app.model.*;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IView;
import io.github.gleidsonmt.speedcut.core.app.view.WorkView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/07/2022
 */
public class ItemsColumnController
        extends WorkView
        implements Context {

    @FXML private TableView<SaleItem> tableItems;
    @FXML private TableColumn<SaleItem, Number> columnSaleItemId;
    @FXML private TableColumn<SaleItem, Number> quantityColumn;
    @FXML private TableColumn<SaleItem, TradeItem> itemNameColumn;
    @FXML private TableColumn<SaleItem, BigDecimal> valueColumn;
    @FXML private TableColumn<SaleItem, BigDecimal> totalColumn;

    @FXML private Button btnControlSales;
    @FXML private GridPane gridActions;
    @FXML private GridPane gridInfo;

    @FXML private Button receive;
    @FXML private Button remove;
    @FXML private Button include;
    @FXML private HBox infoBox;

    @FXML private Label lbl_total;
    @FXML private Label lbl_subtotal;
    @FXML private Label lbl_discount;
    @FXML private Label lbl_value;
    @FXML private Label infoValue;

    @FXML private StackPane boxPay;

    @FXML private VBox transactionBox;

    private SalesController salesController;

    private final PayActions payActions      = new PayActions();

    private PaymentBox paymentBox;

    private boolean payBoxOpened = false;

    private final ObjectProperty<BigDecimal> total    = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> subTotal = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> discount = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> current  = new SimpleObjectProperty<>(BigDecimal.ZERO);

    @FXML
    private void addItem() {
//        creater = false;
        IView view = context.getRoutes().getView("buy");

        view.getController().pass(getController().getSaleSelected() != null);

        if (getController().getCols() < 2) {
            getController().setFirsColumn(view.getRoot());
        } else {
            getController().setSecondColumn(view.getRoot());
        }
//
        view.getController().pass(false);
        view.getController().onEnter();

    }

    @FXML
    public void closePaymentBox() {

        payBoxOpened = false;

        transactionBox.getChildren().remove(paymentBox);

        transactionBox.getChildren().add(gridActions);
        transactionBox.getChildren().remove(payActions);

        infoValue.setStyle("-fx-font-weight : bold;");
        infoValue.setText("Ativo:");
        lbl_value.setStyle("-fx-font-weight : bold;");

        current.set(BigDecimal.ZERO);

//        lbl_value.textProperty().unbind();
    }

    @FXML
    private void openPaymentBox() {

        payBoxOpened = true;

        transactionBox.getChildren().add(paymentBox);
        paymentBox.toBack();

        paymentBox.setValue(MoneyUtil.format(lbl_total.getText()));

        transactionBox.getChildren().remove(gridActions);
        transactionBox.getChildren().add(2, payActions);


        current.set(paymentBox.getValue());

        lbl_value.textProperty().bindBidirectional(
                current, new MoneyStringConverter()
        );

        addRedStyle();
        infoValue.setText("Falta:");

        resetValues();

    }

    private ObservableList<Amount> amounts = FXCollections.observableArrayList();
    private BigDecimal sum = BigDecimal.ZERO;

    // used to reset values used in calc of the amount the transactions
    private void resetValues() {
        amounts.clear();
        sum = BigDecimal.ZERO;
    }

    @FXML
    public void pay() {

        // creates a new amount
        Amount amount = new Amount();
        amount.setValue(paymentBox.getValue());

        // swith among type of amount, card, money etc.
        switch (paymentBox.getPaymentType()) {

            case CARD -> {
                amount.setPaymentType(PaymentType.CARD);
                amount.setItem(paymentBox.getCard());
            }

            case PIX -> {
                amount.setPaymentType(PaymentType.PIX);
                amount.setItem(paymentBox.getPix());
            }

            case TERM -> {
                amount.setPaymentType(PaymentType.TERM);

                for (PaymentMethod paymentMethod : getController().getSaleSelected().getClient().getPaymentMethods()) {
                    if (paymentMethod instanceof Term) {
                        amount.setItem(paymentMethod);
                    }
                }
            }
            case MONEY -> amount.setPaymentType(PaymentType.MONEY);
            default -> throw new IllegalStateException("Unexpected value: " + paymentBox.getPaymentType());
        }

        sum = sum.add(amount.getValue()); // get new value for current
        amounts.add(amount); // add amount to list


        finalizePay(amounts);
    }

    private void finalizePay(ObservableList<Amount> amounts) {

        Transaction transaction = new Transaction();
        transaction.getAmounts().setAll(amounts);
        transaction.setSale(getController().getSaleSelected());
        transaction.setCashier(context.getCashier());
        transaction.setCategory(Category.SALE);
        transaction.setType(Type.ENTER);

        // will some corrections in this
        transaction.setTotal(total.get());
        transaction.setSubtotal(subTotal.get());
        transaction.setDiscount(discount.get());
        transaction.setCurrent(sum);
        transaction.setChange(sum.subtract(total.get()));

        if (sum.compareTo(total.get()) < 0 ) {
            current.set(total.get().subtract(sum));
            paymentBox.setValue(current.get());
            return;
        }

        RepoTransactionImpl repository = (RepoTransactionImpl) Repositories.<Transaction>get(Transaction.class);
        repository.put(transaction);

        for (Amount a : transaction.getAmounts()) {
            a.setTransaction(transaction);
            Repositories.get(Amount.class).put(a);
        }

        repository.persist();

        context.getCashier().getTransactions().add(0, transaction);
        context.getCashier().getActiveSales().remove(transaction.getSale());

        boxPay.toFront();
        boxPay.setOpacity(1);
        Tada startAnimation = new Tada(boxPay);
        startAnimation.setSpeed(1.5);

        startAnimation.getTimeline().setOnFinished(
                event -> {
                    FadeOut endAnimation = new FadeOut(boxPay);
                    endAnimation.setSpeed(1.2);
                    endAnimation.play();
                }
        );

        startAnimation.play();

        // if has only one
        ((IndexController) context.getControlller("sale_index")).selectFirst();

    }

    private void addRedStyle() {
        infoValue.setStyle("-fx-text-fill : -grapefruit; -fx-font-weight : bold;");
        lbl_value.setStyle("-fx-text-fill : -grapefruit; -fx-font-weight : bold;");
    }

    @FXML
    public void deleteSaleItem() {

        SaleItem _saleItem = tableItems.getSelectionModel().getSelectedItem();

        if (_saleItem != null && tableItems.getItems().size() > 1) {

            Repository<SaleItem> repo = Repositories.get(SaleItem.class);
            repo.delete(_saleItem);
//
            if (repo.persist()) {
                context.getDecorator()
                        .getRoot()
                        .createSnackBar()
                        .color(SnackBar.Colors.GRAPEFRUIT)
                        .message("#" + _saleItem.getId() + " | Item de venda removido.")
                        .show();

                _saleItem.getSale().getSaleItems().remove(_saleItem);
                tableItems.getItems().remove(_saleItem);
            }
        } else if (_saleItem != null) {

            Repository<Sale> rep = Repositories.get(Sale.class);

            Sale sale = getController().getSaleSelected();

            AtomicBoolean delete = new AtomicBoolean(true);

            context.getDecorator()
                    .getRoot()
                    .createSnackBar()
                    .color(SnackBar.Colors.WARNING)
                    .message("#" + sale.getId() + " | Venda removida")
                    .onHide(event -> {
                        if (delete.get()) {
                            rep.delete(sale);
                            rep.persist();
                        }
                    })
                    .undo(event -> {
                        sale.getCashier().getActiveSales().add(sale);

                        sale.getCashier().getActiveSales().sort((saleOne, saleTwo) ->
                                saleOne.getId() < saleTwo.getId() ? 0 : 1
                        );

                        getController().select(sale);

                        delete.set(false);
                    })
                    .action(event -> sale.getCashier().getActiveSales().remove(sale))
                    .show();
        } else {
            context.getDecorator()
                    .getRoot()
                    .createSnackBar()
                    .message("Não existem itens de vendas.")
                    .show();
        }
    }

    @Override
    protected void work() {

        if (!super.isWorked()) {

            gridActions.getChildren().stream().map(e -> (Button)e).forEach(Animations::onHoverButton);
            paymentBox = new PaymentBox();
            configFactories();

//            tableItems.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
//                if (newValue != null) {
//                    searchClientBox.select(newValue.getClient().getId());
//                }
//            });

            gridInfo.visibleProperty().bind(
                    Bindings.isNotNull(
                            tableItems.getSelectionModel().selectedItemProperty()
                    )
            );

            RepoTransactionImpl repo = (RepoTransactionImpl) Repositories.<Transaction>get(Transaction.class);

            btnControlSales.disableProperty().bind(
                    repo.hasTransactionsProperty().not()
            );

            receive.disableProperty().bind(
                    Bindings.isNull(
                            tableItems.getSelectionModel().selectedItemProperty()
                    )
            );

            remove.disableProperty().bind(
                    Bindings.isNull(
                            tableItems.getSelectionModel().selectedItemProperty()
                    )
            );

            include.disableProperty().bind(
                    Bindings.isNull(
                            tableItems.getSelectionModel().selectedItemProperty()
                    )
            );


            super.worked();
        }
    }

    private void createBindsForTransaction() {

        lbl_subtotal.textProperty().unbind();
        lbl_discount.textProperty().unbind();
        lbl_total.textProperty().unbind();
        lbl_value.textProperty().unbind();

        lbl_subtotal.textProperty().bindBidirectional(
                subTotal, new MoneyStringConverter()
        );

        lbl_total.textProperty().bindBidirectional(
                total, new MoneyStringConverter()
        );

        lbl_discount.textProperty().bindBidirectional(
                discount, new MoneyStringConverter()
        );

        lbl_value.textProperty().bindBidirectional(
                current, new MoneyStringConverter()
        );
    }

    private final ChangeListener<Boolean> changeHasDiscount = (observable, oldValue, newValue) -> {

        SaleItem item = getSelected();

        if (payBoxOpened) {
            current.set(
                    newValue ? current.get().subtract(getSelected().getDiscount()) : current.get().add(getSelected().getDiscount())
            );
            paymentBox.setValue(current.get());
        }

        total.set( // se tiver tiver desconto adiciona se nao tiver retira
                newValue ? total.get().subtract(item.getDiscount()) : total.get().add(item.getDiscount())
        );

        discount.set( // se tiver desconto adiciona se nao tiver retira
                newValue ? discount.get().add(item.getDiscount()) : discount.get().subtract(item.getDiscount())
        );
    };

    // Usado para atualizar os valores quando uma celula da tabela for alterada
    private final ChangeListener<Number> changeQuantity = (observable, oldValue, newValue) -> {

        SaleItem item = getSelected();

        if (item.hasDiscount()) {
            discount.set(
                    newValue.doubleValue() > oldValue.doubleValue() ?
                            discount.get().add(item.getTradeItem().getPrice().multiply(item.getTradeItem().getDiscount())) :
                            discount.get().subtract(item.getTradeItem().getPrice().multiply(item.getTradeItem().getDiscount()))
            );
        }

        subTotal.set(
                // se estiver a aumentar a quantidade
                (newValue.doubleValue() > oldValue.doubleValue()) ?
                        subTotal.get().add(item.getTradeItem().getPrice()) :
                        subTotal.get().subtract(item.getTradeItem().getPrice())
        );

        total.set(
                // se estiver aumentando a quantidade
                (newValue.doubleValue() > oldValue.doubleValue()) ?
                        (item.hasDiscount() ? total.get().add(item.getUnit()) :  total.get().add(item.getTradeItem().getPrice()) ) :
                        // se estiver diminundo a quantidade e se tem ou nao desconto
                        (item.hasDiscount() ? total.get().subtract(item.getUnit()) : total.get().subtract(item.getTradeItem().getPrice()))
        );


        if (payBoxOpened) {

            current.set(
                    // se estiver a aumentar a quantidade
                    newValue.doubleValue() > oldValue.doubleValue() ? // se estiver a aumentar a quantidade faca
                            // com desconto       // com desconto +                         // sem desconto +
                            item.hasDiscount() ?  current.get().add(item.getUnit())  :      current.get().add(item.getTradeItem().getPrice())  : // // se estiver diminunido faca
                            //sem desconto        // com desconto -                         // sem desconto -
                            item.hasDiscount() ?  current.get().subtract(item.getUnit()) :  current.get().subtract(item.getTradeItem().getPrice())
            );

            paymentBox.setValue(current.get());
        }
//
    };

    // configura as factories
    private void configFactories() {

        columnSaleItemId.setCellValueFactory(param -> param.getValue().idProperty());
        quantityColumn.setCellValueFactory(param -> param.getValue().quantityProperty());

        itemNameColumn.setCellValueFactory(param -> param.getValue().tradeItemProperty());

        valueColumn.setCellValueFactory(param -> param.getValue().unitProperty());

        totalColumn.setCellValueFactory(param -> param.getValue().totalProperty());

        SalesController salesController = (SalesController) context.getControlller("sales");

        quantityColumn.setCellFactory(new QuantityColumnFactory());
        tableItems.setRowFactory(new SaleItemsRowFactory(salesController));
        itemNameColumn.setCellFactory(new ProductColumnFactory<>());

        valueColumn.setCellFactory(new MoneyColumnFactory<>());
        totalColumn.setCellFactory(new MoneyColumnFactory<>());

        total.addListener((observable, oldValue, newValue) -> {
//            paymentBox.setValue(newValue);
        });

        tableItems.getItems().addListener((ListChangeListener<SaleItem>) c -> {
            if (c.next()) {

                if (c.wasRemoved()) {
                    for (SaleItem item : c.getRemoved()) {
                        item.hasDiscountProperty().removeListener(changeHasDiscount);
                        item.quantityProperty().removeListener(changeQuantity);
                    }
                }

                if (c.wasAdded()) {
                    for (SaleItem item : c.getAddedSubList()) {

                        total.set(total.get().add(item.getTotal()));

                        if (item.hasDiscount()) {
                            discount.set(discount.get().add(item.getDiscount()));
                        }

                        item.hasDiscountProperty().addListener(changeHasDiscount);
                        item.quantityProperty().addListener(changeQuantity);
                    }
                    subTotal.set(total.get().add(discount.get()));
                }
            }
        });

        salesController.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                Task<ObservableList<SaleItem>> task = popupalteItems(newValue.getSaleItems());

                // quando clicado na tabela reseta os valores
                subTotal.set(BigDecimal.ZERO);
                total.set(BigDecimal.ZERO);
                discount.set(BigDecimal.ZERO);



                task.setOnSucceeded(event -> {
                    tableItems.getSelectionModel().selectFirst();
                    resetValues();

                    paymentBox.flush(newValue);
                    createBindsForTransaction();

                    if (payBoxOpened) {
                        current.set(total.get());
                        paymentBox.setValue(current.get());
                    }
                });

                new Thread(task).start();

            }  else {
                tableItems.getItems().clear();
                tableItems.setPlaceholder(new Label("Sem itens na venda."));
            }
        });
    }

    @FXML
    private void goControl () {
        try {
            context.getRoutes().setContent("sale_index");
        } catch (NavigationException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<SaleItem> getItems() {
        return tableItems.getItems();
    }

    public SaleItem getSelected() {
        return tableItems.getSelectionModel().getSelectedItem();
    }

    public SalesController getController() {
        if (this.salesController == null) this.salesController = (SalesController) context.getControlller("sales");
        return salesController;
    }

    private Task<ObservableList<SaleItem>> popupalteItems(ObservableList<SaleItem> items) {

        return new Task<>() {

            @Override
            protected ObservableList<SaleItem> call() throws Exception {

                if (!tableItems.getItems().isEmpty()) {
                    Platform.runLater(() -> tableItems.getItems().clear());
                }

                for (SaleItem item : items) {
                    Thread.sleep(50);

                    item.getProperties().put("init", false);

                    Platform.runLater(() -> tableItems.getItems().add(item));

                }
                return null;
            }
        };
    }
}
