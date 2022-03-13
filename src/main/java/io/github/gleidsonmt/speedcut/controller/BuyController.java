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
import io.github.gleidsonmt.speedcut.core.app.animations.Animations;
import io.github.gleidsonmt.speedcut.presenter.ProfessionalPresenter;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.factory.ListWithGraphicFactory;
import io.github.gleidsonmt.speedcut.core.app.factory.LoadPlaceholder;
import io.github.gleidsonmt.speedcut.core.app.layout.Root;
import io.github.gleidsonmt.speedcut.core.app.model.*;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import io.github.gleidsonmt.speedcut.core.app.view.ActionViewController;
import io.github.gleidsonmt.speedcut.presenter.ProductPresenter;
import io.github.gleidsonmt.speedcut.presenter.ServicePresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
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
public class BuyController implements ActionViewController {

    @FXML private StackPane root;
    @FXML private GNListView<Item> listItems;
    @FXML private GNListView professionalList;
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

    @FXML private GNButton confirm;
    @FXML private GNButton close;

    @FXML private Label newItem;

    private final ObservableList<Item> items = FXCollections.observableArrayList();
    private final FilteredList<Item> filteredSaleItems = new FilteredList<>(items, p -> true);

    private SalesController salesController = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() >= 600) {
                if (window.getViews().getCurrent().getName().equals("buy")) {
                    try {
                        window.navigate("sales");
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
        });

        searchItem.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filteredSaleItems.setPredicate(p -> p.getName().toLowerCase().contains(newValue.toLowerCase()));
                listItems.getSelectionModel().selectFirst();
            }
        });


        listItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                calculate(  ((Item) newValue).getPrice());
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

        actions.getChildren().forEach(f -> Animations.onHoverButton((GNButton) f));

        plus10.setOnAction(event -> plus(10));
        plus50.setOnAction(event -> plus(50));
        plus100.setOnAction(event -> plus(100));


    }

    private void plus(int plus) {
        quantField.setText(String.valueOf( Integer.parseInt(quantField.getText()) + plus));
    }

    private void calculate() {
        if (listItems.getSelectionModel().getSelectedItem() != null) {
            calculate(((Item) listItems.getSelectionModel().getSelectedItem()).getPrice());
        }
    }

    private void calculate(BigDecimal _value) {
        value.setText( MoneyUtil.format(_value));
        total.setText( MoneyUtil.multiply(value.getText(), quantField.getText()) );
    }

    @FXML
    private void close()  {

        if (window.getWidth() > 600) {
            window.getRoot().getWrapper().closePopup();
        } else {
            try {
                window.navigate("sales", true);
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

    @Override
    @SuppressWarnings("unchecked")
    public void onEnter() {

        if (salesController == null) salesController = (SalesController) window.getViews().getControllerFrom("sales");

        if (!init) {
            init = true;
            if (itemType.getSelectedToggle().equals(radioService)) {
                populateServices();
            } else {
                populateProducts();
//                populateProducts().addEventFilter(WorkerStateEvent.WORKER_STATE_SUCCEEDED, e -> populateProfessionals());
            }
//            populateProfessionals();
            searchItem.getEditor().clear();
            listItems.setCellFactory(new ListWithGraphicFactory());
//            professionalList.setCellFactory(new ListWithGraphicFactory());
        }

        searchItem.requestFocus();
    }

    @Override
    public void onExit() {
    }

    private Task<ObservableList<Service>> populateServices() {

        Task<ObservableList<Service>> populate = new ServicePresenter().createAllElements();

        Thread thread = new Thread(populate);
        thread.setName("Loading data table [Professional]");
        thread.setPriority(1);
        thread.start();

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

        });


        return populate;
    }

    private  Task<ObservableList<Product>> populateProducts() {

        Task<ObservableList<Product>> populate = new ProductPresenter().createAllElements();

        Thread thread = new Thread(populate);
        thread.setName("Loading data table [Products]");
        thread.setPriority(1);
        thread.start();

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

        });

        return populate;
    }

    private void populateProfessionals() {

        Task<ObservableList<Professional>> populate = new ProfessionalPresenter().createAllElements();

        ObservableList<Professional> professionalItems = FXCollections.observableArrayList();
        FilteredList<Professional> filteredList = new FilteredList<>(professionalItems, p -> true);

        searchProfessional.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filteredSaleItems.setPredicate(p -> p.getName().toLowerCase().contains(newValue.toLowerCase()));
            }
        });

        professionalList.setItems(filteredList);

        Thread thread = new Thread(populate);
        thread.setName("Loading data table [Professional]");
        thread.setPriority(2);
        thread.start();

        populate.setOnRunning(event -> {
            LoadPlaceholder loadPlaceholder = new LoadPlaceholder();
            professionalList.setPlaceholder(loadPlaceholder);
        });

        populate.setOnSucceeded(event -> {
            for (Professional product : populate.getValue()) {
                professionalItems.add(product);
                professionalList.getSelectionModel().selectFirst();
            }

            professionalList.setPlaceholder(new Label("Itens não encontrados."));

        });

    }

    @FXML
    private void confirm() {
        next();
        window.getRoot().getWrapper().closePopup();
    }

    @FXML
    private void next() {
//        window.getRoot().openSnackBar("Item " + listItems.getSelectionModel().getSelectedItem().getName() + " adicionado!", true, Root.SnackType.DONE);

        SaleItem saleItem = new SaleItem();
        saleItem.setItem(listItems.getSelectionModel().getSelectedItem());
        saleItem.setQuantity(Integer.parseInt(quantField.getText()));
        saleItem.setTotal(MoneyUtil.get(total.getText()));
//
        BigDecimal ac = MoneyUtil.get(total.getText());

        salesController.addSaleItem(saleItem);
    }

}
