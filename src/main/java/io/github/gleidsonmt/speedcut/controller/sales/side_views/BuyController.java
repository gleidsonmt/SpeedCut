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

package io.github.gleidsonmt.speedcut.controller.sales.side_views;

import io.github.gleidsonmt.gncontrols.GNButton;
import io.github.gleidsonmt.gncontrols.GNListView;
import io.github.gleidsonmt.gncontrols.controls.GNTextBox;
import io.github.gleidsonmt.speedcut.controller.sales.main.SalesController;
import io.github.gleidsonmt.speedcut.core.app.animations.Animations;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.factory.ListAvatarFactory;
import io.github.gleidsonmt.speedcut.core.app.factory.LoadPlaceholder;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.SnackBar;
import io.github.gleidsonmt.speedcut.core.app.model.Product;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import io.github.gleidsonmt.speedcut.core.app.model.Service;
import io.github.gleidsonmt.speedcut.core.app.model.TradeItem;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.ActionView;
import io.github.gleidsonmt.speedcut.presenter.TradeItemPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  25/02/2022
 */
@SuppressWarnings("rawtypes")
public class BuyController implements ActionView, Initializable {

    @FXML private StackPane root;
    @FXML private GNListView<TradeItem> listItems;
    @FXML private ToggleGroup itemType;
    @FXML private RadioButton radioProduct;
    @FXML private RadioButton radioService;
    @FXML private Label value;
    @FXML private Label total;
    @FXML private TextField quantField;
    @FXML private GNButton plus;
    @FXML private GNButton minus;

    @FXML private Hyperlink plus10;
    @FXML private Hyperlink plus50;
    @FXML private Hyperlink plus100;

    @FXML private GridPane actions;

    @FXML private GNTextBox searchItem;
    @FXML private GNTextBox searchProfessional;

    @FXML private Button confirm;
    @FXML private Button close;

    @FXML private Label newItem;

    private final ObservableList<TradeItem> items = FXCollections.observableArrayList();
    private final FilteredList<TradeItem> filteredSaleItems = new FilteredList<>(items, p -> true);

    private SalesController salesContoller = null;

    private TradeItemPresenter tradeItemPresenter = new TradeItemPresenter();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() >= 600) {
                if (context.getRoutes().getCurrent().getName().equals("buy")) {
                    try {
                        context.getRoutes().setContent("sales");
                    } catch (NavigationException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        itemType.selectToggle(radioProduct);
        listItems.setItems(filteredSaleItems);

        itemType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(radioProduct)) {
                populateProducts();
            } else {
                populateServices();
            }
            searchItem.requestFocus();
        });

//        searchItem.textProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue != null) {
//                filteredSaleItems.setPredicate(p -> p.getName().toLowerCase().contains(newValue.toLowerCase()));
//                listItems.getSelectionModel().selectFirst();
//            }
//        });


        listItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                calculate(  newValue.getPrice());
            }
        });

        quantField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) calculate();
        });

        minus.setOnAction(event -> {
            int count = Integer.parseInt(quantField.getText()) - 1;
            quantField.setText(count > 0 ? String.valueOf(count) : String.valueOf(1) );
        });

        plus.setOnAction(event -> {
            int count = Integer.parseInt(quantField.getText()) + 1;
            quantField.setText(count > 0 ? String.valueOf(count) : String.valueOf(1) );
        });

        actions.getChildren().stream().map(m -> (Button)m).forEach(Animations::onHoverButton);

        plus10.setOnAction(event -> plus(10));
        plus50.setOnAction(event -> plus(50));
        plus100.setOnAction(event -> plus(100));


    }

    private void plus(int plus) {
        quantField.setText(String.valueOf( Integer.parseInt(quantField.getText()) + plus));
    }

    private void calculate() {
        if (listItems.getSelectionModel().getSelectedItem() != null) {
            calculate(listItems.getSelectionModel().getSelectedItem().getPrice());
        }
    }

    private void calculate(BigDecimal _value) {
        value.setText( MoneyUtil.format(_value) );
        total.setText( MoneyUtil.multiply(value.getText(), quantField.getText()) );
    }

    @FXML
    private void close()  {

        if (context.getDecorator().getWidth() > 600) {
            context.getDecorator().getRoot().getWrapper().getPopup().close();
        } else {
            try {
                context.getRoutes().setContent("sales");
            } catch (NavigationException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    private void reset() {
        quantField.setText("1");
        calculate();
    }

    private boolean init = false;
    private boolean add = false;

    @Override
    public void onEnter() {

        if (salesContoller == null) salesContoller =
                (SalesController) context.getRoutes().getView("sales").getController();

        if (!init) {
            init = true;

            itemType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                  populate((RadioButton) newValue);
                }
            });

            populate(radioProduct);

            listItems.setCellFactory(new ListAvatarFactory<>(context));

        } else {
            quantField.setText("1");
//            quantField.requestFocus();
            searchItem.requestFocus();
        }

        itemType.selectToggle(radioProduct);
        radioProduct.setSelected(true);

    }

    private void populate(RadioButton radio) {

        Task task;

        if (radio == radioProduct) {
            task = populateProducts();
        } else {
            task = populateServices();
        }

        Thread thread = new Thread(task);
        thread.setName("Loading data table Buy Items");
        thread.setPriority(1);
        thread.start();
    }

    private boolean create = false;

    @Deprecated
    @Override
    public void pass(boolean exe) {
        create = exe;
    }

    @Override
    public void onExit() {
    }

    private Task<ObservableList<Service>> populateServices() {

        Task<ObservableList<Service>> populate = tradeItemPresenter.createServices();

        populate.setOnRunning(event -> {
            LoadPlaceholder loadPlaceholder = new LoadPlaceholder();
            listItems.setPlaceholder(loadPlaceholder);
        });

        populate.setOnSucceeded(event -> {
            items.clear();

            for (Service service : populate.getValue()) {
                items.add(service);
                listItems.getSelectionModel().selectFirst();
            }

            listItems.setPlaceholder(new Label("Itens não encontrados."));

            searchItem.requestFocus();
        });


        return populate;
    }

    private  Task<ObservableList<Product>> populateProducts() {

        Task<ObservableList<Product>> populate = tradeItemPresenter.createProducts();

        populate.setOnRunning(event -> {
            LoadPlaceholder loadPlaceholder = new LoadPlaceholder();
            listItems.setPlaceholder(loadPlaceholder);
        });

        populate.setOnSucceeded(event -> {
            items.clear();

            for (Product product : populate.getValue()) {
                items.add(product);
                listItems.getSelectionModel().selectFirst();
            }

            listItems.setPlaceholder(new Label("Itens não encontrados."));
            searchItem.requestFocus();
        });

        return populate;
    }

    @FXML
    private void back () {
        if (salesContoller.getCols() > 1) {
            salesContoller.remove();
        } else salesContoller.resetFirstColumn();
    }

    @FXML
    private void addAndNext() {
        createOrUpdate();
       showMessage();
    }

    @FXML
    private void addAndClose() {
        createOrUpdate();
        back();
        showMessage();
    }

    private void showMessage() {
        context.getDecorator()
                .getRoot()
                .createSnackBar()
                .color(SnackBar.Colors.SUCCESS)
                .message("#" + listItems.getSelectionModel().getSelectedItem().getName() + " | Item Adicionado.")
                .show();
    }

    @FXML
    private void addAndChoiceAll() {
        createOrUpdate();
//        salesContoller.setNext("professionals");
    }

    private void createOrUpdate () {

        SaleItem saleItem = new SaleItem();
        saleItem.setTradeItem(listItems.getSelectionModel().getSelectedItem());

        saleItem.setHasDiscount(false);

        saleItem.setQuantity(Integer.parseInt(quantField.getText()));
        saleItem.setTotal(MoneyUtil.get(total.getText()));

        saleItem.setUnit(saleItem.getTradeItem().getPrice());


        if (!create) { // if dont create

            salesContoller.getSaleItems().stream().filter(

                            math -> math.getTradeItem().getName().equalsIgnoreCase(saleItem.getTradeItem().getName()))
                    .forEach(each -> {

                        saleItem.setId(each.getId());
                        each.setQuantity(each.getQuantity() + saleItem.getQuantity());
                        saleItem.setQuantity(each.getQuantity());
                        saleItem.setHasDiscount(each.hasDiscount());

                    });

        }
//
//
        salesContoller.addSaleItem(saleItem, create);

    }




}
