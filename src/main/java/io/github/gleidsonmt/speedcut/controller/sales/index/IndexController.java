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

package io.github.gleidsonmt.speedcut.controller.sales.index;

import io.github.gleidsonmt.gncontrols.GNButton;
import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.controller.sales.payment_layout.BoxPaymentDetails;
import io.github.gleidsonmt.speedcut.core.app.converters.MoneyStringConverter;
import io.github.gleidsonmt.speedcut.core.app.dao.RepoCashierImpl;
import io.github.gleidsonmt.speedcut.core.app.dao.RepoTransactionImpl;
import io.github.gleidsonmt.speedcut.core.app.dao.Repositories;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.factory.AvatarCreator;
import io.github.gleidsonmt.speedcut.core.app.factory.LoadPlaceholder;
import io.github.gleidsonmt.speedcut.core.app.factory.Paginator;
import io.github.gleidsonmt.speedcut.core.app.factory.column.*;
import io.github.gleidsonmt.speedcut.core.app.factory.row.TransactionRowFactory;
import io.github.gleidsonmt.speedcut.core.app.model.*;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.ActionView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.function.Predicate;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  03/04/2022
 */
public class IndexController implements ActionView {

    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, Client> clientColumn;

    @FXML private TableColumn<Transaction, Sale> columnSale;
    @FXML private TableColumn<Transaction, Type> typeColumn;
    @FXML private TableColumn<Transaction, Category> categoryColumn;
    @FXML private TableColumn<Transaction, BigDecimal> amountColumn;

    @FXML private TableColumn<Transaction, Number> idColumn;
    @FXML private ComboBox<Integer> cmbEntries;
    @FXML private ListView<SaleItem> listItems;

    @FXML private TableColumn<Transaction, ListProperty<Card>> cardColumn;

    @FXML private VBox tableContent;

    @FXML private Label lblIdOrder;
    @FXML private Label lblSaleId;
    @FXML private Label lblProfName;
    @FXML private Label lblSub;
    @FXML private Label lblTotal;
    @FXML private Label lblDiscount;
    @FXML private Label lblTime;
    @FXML private Label lblChange;
    @FXML private Label lblAmount;
    @FXML private VBox avatarBox;

    @FXML private StackPane boxInfoLeft;
    @FXML private VBox boxSaleDetail;
    @FXML private VBox boxDetailContent;
    @FXML private VBox boxItems;
    @FXML private GNButton btnPayDetail;

    @FXML private SVGPath iconDetails;
    @FXML private GNTextBox tfSearch;

    // Fixed
    private final BoxPaymentDetails boxPaymentDetails = new BoxPaymentDetails();

    // Filter
    private final ObjectProperty<Predicate<Transaction>> nameFilter = new SimpleObjectProperty<>(); // Propriedade de objeto Predicado

    private final RepoTransactionImpl repository = (RepoTransactionImpl) Repositories.<Transaction>get(Transaction.class);

    private FilteredList<Transaction> fulList;

    private final RepoCashierImpl repo = (RepoCashierImpl) Repositories.<Cashier>get(Cashier.class);
    private final Cashier cashier = repo.getOpened();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setUpTable();
        cmbEntries.getItems().addAll(5, 10, 50, 100);
        cmbEntries.getSelectionModel().selectFirst();

        boxPaymentDetails.getProperties().put("selected", false);
    }

    private boolean init = false;

    public void selectFirst() {
        transactionTable.getSelectionModel().selectFirst();
    }


    @Override
    public void onEnter() {

//        lblIdOrder

        if (transactionTable.getItems().size() > 0 ) {
            transactionTable.getSelectionModel().selectFirst();
        }

        if (!init) {

//            cashier = app.getCashier();


            fulList = new FilteredList<>(context.getCashier().getTransactions(), p -> true);

            Task<ObservableList<Transaction>> populate = repository.transactionsFor(context.getCashier());

            Thread runPopulate = new Thread(populate);
            runPopulate.setDaemon(true);
            runPopulate.start();

            populate.setOnRunning(event -> {
                LoadPlaceholder loadPlaceholder = new LoadPlaceholder();
                transactionTable.setPlaceholder(loadPlaceholder);
            });

            populate.setOnSucceeded(event -> {

                transactionTable.setPlaceholder(new Label("Não há items a serm exibidos."));

                Paginator<Transaction> paginator = new Paginator<>(fulList, transactionTable);
//
                paginator.limitProperty().bind(cmbEntries.getSelectionModel().selectedItemProperty());

                paginator.setComparator(
                        (o1, o2) -> o1.getId() > o2.getId() ? 0 : 1
                );

                paginator.load();

                tableContent.getChildren().add(paginator);


//                transactionTable.setItems(fulList);

                var ref = new Object() {
                    boolean create = true;
                };

                createBinds();

                transactionTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {

                    System.out.println("fuckkkkkk ");
                    if (newValue != null) {


                        updateAvatar(newValue.getSale().getProfessional());
                        listItems.setItems(newValue.getSale().getSaleItems());

                        boxPaymentDetails.updateContent(
                                transactionTable.getSelectionModel().getSelectedItem()
                        );

                    }
                });

                if (transactionTable.getItems().size() > 0) {
                    transactionTable.getSelectionModel().select(0);
                }


            });



            if (fulList.isEmpty()) {
                transactionTable.setPlaceholder(new Label("Sem transações para este caixa."));
            }

            init = true;
        }

//        app.getCashier().getTransactions().addListener((ListChangeListener<Transaction>) c -> {
//            if (c.next()) {
//                if (c.wasAdded()) {
//                    init = false;
//                    onEnter();
//                }
//            }
//        });

    }

//    private void updateColor(BigDecimal bigDecimal) {
//        if (bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
//            lblDiscount.setStyle("-fx-text-fill : -text-color; -fx-font-weight : bold;");
//        } else {
//            lblDiscount.setStyle("-fx-text-fill : -grapefruit; -fx-font-weight : bold;");
//        }
//    }

    private void updateAvatar(Professional professional) {
        String avatar = professional.getImgPath();
        avatarBox.getChildren().clear();

        if (avatar != null) {
            String path = "/io.github.gleidsonmt.speedcut.core.app/";
            String imgPath = Objects.requireNonNull(getClass().getResource(path + avatar)).toExternalForm();
            Image image = new Image(imgPath, 90, 0, true, true);
//                        setGraphic(AvatarCreator.createAvatar(image));
            avatarBox.getChildren().add(AvatarCreator.createAvatar(image));
//                    } else {
//                        setGraphic(AvatarCreator.createDefaultAvatar(item.getName(), 18));
        }
    }

    @Override
    public void onExit() {

    }

    private void createBinds() {



        if (transactionTable.getItems().size() > 0) {

            updateAvatar(transactionTable.getItems().get(0).getSale().getProfessional());

            lblIdOrder.textProperty().bind(
                    Bindings.convert(
                            Bindings.select(
                                    transactionTable.getSelectionModel().selectedItemProperty(),
                                    "id"
                            )
                    )
            );

            lblProfName.textProperty().bind(
                    Bindings.select(
                            transactionTable.getSelectionModel().selectedItemProperty(),
                            "sale", "professional", "name"
                    )
            );

            lblSaleId.textProperty().bind(
                    Bindings.convert(
                            Bindings.select(
                                    transactionTable.getSelectionModel().selectedItemProperty(),
                                    "sale", "id"
                            )
                    )
            );


//
            listItems.setItems(transactionTable.getSelectionModel().getSelectedItem().getSale().getSaleItems());
//
            lblSub.textProperty().bind(Bindings.format(MoneyUtil.getSymbol() + " %.2f ",
                    Bindings.select(transactionTable.getSelectionModel().selectedItemProperty(), "subtotal")
            ));

            lblTotal.textProperty().bind(Bindings.format(MoneyUtil.getSymbol() + " %.2f ",
                    Bindings.select(transactionTable.getSelectionModel().selectedItemProperty(), "total")
            ));

            lblDiscount.textProperty().bind(Bindings.format(MoneyUtil.getSymbol() + " %.2f ",
                    Bindings.select(transactionTable.getSelectionModel().selectedItemProperty(), "discount")
            ));


            lblChange.textProperty().bind(Bindings.format(MoneyUtil.getSymbol() + " %.2f ",
                    Bindings.select(transactionTable.getSelectionModel().selectedItemProperty(), "change")
            ));

            lblAmount.textProperty().bindBidirectional(
                    boxPaymentDetails.finalAmountProperty(),
                    new MoneyStringConverter()
            );

            LocalDateTime today = LocalDateTime.now();

            String formattedDate = today.format(DateTimeFormatter.ofPattern("MMM dd, HH:mm"));

            char[] arr = formattedDate.toCharArray();
            arr[0] = Character.toUpperCase(arr[0]);

            lblTime.setText(new String(arr));

//                updateColor(MoneyUtil.get(lblDiscount.getText()));
//                lblDiscount.textProperty().addListener((observableValue, _old, _new) -> updateColor(MoneyUtil.get(_new)));


                nameFilter.bind(
                        Bindings.createObjectBinding(
                            new Callable<Predicate<Transaction>>() {
                                @Override
                                public Predicate<Transaction> call() throws Exception {
                                    return new Predicate<Transaction>() {
                                        @Override
                                        public boolean test(Transaction transaction) {
                                            return transaction.getSale().getClient().getName().toLowerCase().contains(
                                                    tfSearch.getText().toLowerCase()
                                            );
                                        }
                                    };
                                }
                            },  tfSearch.textProperty())

                        );

                fulList.predicateProperty().bind(
                        Bindings.createObjectBinding(
                                new Callable<Predicate<? super Transaction>>() {
                                    @Override
                                    public Predicate<? super Transaction> call() throws Exception {
                                        return nameFilter.get();
                                    }
                                }, nameFilter
                        ));
        }

    }

    private void setUpTable () {

        transactionTable.setRowFactory(new TransactionRowFactory());

        clientColumn.setCellFactory(new AvatarColumnFactory<>());
        clientColumn.setCellValueFactory(param -> param.getValue().getSale().clientProperty());

        idColumn.setCellValueFactory(param -> param.getValue().idProperty());

        categoryColumn.setCellValueFactory(param -> param.getValue().categoryProperty());
        categoryColumn.setCellFactory(new CategoryColumnFactory());

        typeColumn.setCellFactory(new TypeColumnFactory());
        typeColumn.setCellValueFactory(param -> param.getValue().typeProperty());

        amountColumn.setCellFactory(new TotalColumnFactory());
        amountColumn.setCellValueFactory(param -> param.getValue().totalProperty());

        listItems.setCellFactory(new SaleItemCellFactory());

    }

    @FXML
    private void back() {
        try {

            context.getRoutes().setContent(context.getRoutes().getPrevious().getName());
        } catch (NavigationException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void viewPayments() {

        iconDetails.setScaleX(1.2);
        iconDetails.setScaleY(1.2);

        if ( ( (boolean) boxPaymentDetails.getProperties().get("selected"))) {
            boxPaymentDetails.getProperties().replace("selected", false);
            boxDetailContent.getChildren().setAll(boxItems);
            iconDetails.setContent("M6 22q-.825 0-1.412-.587Q4 20.825 4 20V8q0-.825.588-1.412Q5.175 6 6 6h2q0-1.65 1.175-2.825Q10.35 2 12 2q1.65 0 2.825 1.175Q16 4.35 16 6h2q.825 0 1.413.588Q20 7.175 20 8v12q0 .825-.587 1.413Q18.825 22 18 22Zm0-2h12V8h-2v2q0 .425-.287.712Q15.425 11 15 11t-.712-.288Q14 10.425 14 10V8h-4v2q0 .425-.287.712Q9.425 11 9 11t-.712-.288Q8 10.425 8 10V8H6v12Zm4-14h4q0-.825-.587-1.412Q12.825 4 12 4q-.825 0-1.412.588Q10 5.175 10 6ZM6 20V8v12Z");
            btnPayDetail.setText("Detalhes da Venda");

        } else {
            boxPaymentDetails.getProperties().replace("selected", true);
            iconDetails.setContent(Icons.MONETARY.getContent());

            boxDetailContent.getChildren().setAll(boxPaymentDetails);
            btnPayDetail.setText("Detalhes do Pagamento");

        }

        boxPaymentDetails.updateContent(
                transactionTable.getSelectionModel().getSelectedItem()
        );


    }
}
