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

import io.github.gleidsonmt.gncontrols.GNButton;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.gncontrols.options.GNButtonType;
import io.github.gleidsonmt.speedcut.controller.sales.aside.SideSearchNavigation;
import io.github.gleidsonmt.speedcut.controller.sales.index.IndexController;
import io.github.gleidsonmt.speedcut.controller.sales.main.componets.GridTile;
import io.github.gleidsonmt.speedcut.core.app.animations.Animations;
import io.github.gleidsonmt.speedcut.core.app.dao.RepoCashierImpl;
import io.github.gleidsonmt.speedcut.core.app.dao.Repositories;
import io.github.gleidsonmt.speedcut.core.app.factory.AvatarCreator;
import io.github.gleidsonmt.speedcut.core.app.factory.LoadPlaceholder;
import io.github.gleidsonmt.speedcut.core.app.factory.column.AvatarColumnFactory;
import io.github.gleidsonmt.speedcut.core.app.factory.row.SaleRowFactory;
import io.github.gleidsonmt.speedcut.core.app.model.Cashier;
import io.github.gleidsonmt.speedcut.core.app.model.Client;
import io.github.gleidsonmt.speedcut.core.app.model.Professional;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import io.github.gleidsonmt.speedcut.core.app.util.DefaultCreator;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.ActionView;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IView;
import io.github.gleidsonmt.speedcut.core.app.view.WorkView;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  25/07/2022
 */
public class SalesColumnController extends WorkView implements ActionView {

    @FXML private GridPane salesAction;
    @FXML private GridPane transactionBox;

    @FXML private TableView<Sale> tableSales;
    @FXML private TableColumn<Sale, Number> columnId;
    @FXML private TableColumn<Sale, Professional> columnProfessional;
    @FXML private TableColumn<Sale, Client> columnClient;

    @FXML private Button btnSearchSale;
    @FXML private Button btnViewProfessionals;

    @FXML private Label lbl_hour;

    @FXML private Label lbl_sechduled;


    private SideSearchNavigation<Client> searchClientBox;
    private SideSearchNavigation<Professional> searchProfessionalBox;

    private SalesController salesController;
    private Node previousNode = null;
    private String next;


    private final RepoCashierImpl repoCashier = (RepoCashierImpl) Repositories.<Cashier>get(Cashier.class);
    private FilteredList<Sale> filteredList;

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runWatch();

        Client client = DefaultCreator.createClient();

        String path = "/io.github.gleidsonmt.speedcut.core.app/";
        String imgPath = Objects.requireNonNull(getClass().getResource(path + "theme/img/avatars/man@50.png")).toExternalForm();
        Image image = new Image(imgPath, 90, 0, true, true);

        lbl_sechduled.setGraphic(
                AvatarCreator.createAvatar(image)
        );

        salesAction.getChildren().stream().map(e -> (Button)e).forEach(Animations::onHoverButton);

        salesAction.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() < 466 ) {
                            salesAction.setVgap(0);

                if (((Button)salesAction.getChildren().get(0)).getContentDisplay().equals(ContentDisplay.LEFT)) {
                    salesAction.getChildren().stream().map(each -> (Button) each)
                            .forEach(each -> each.setContentDisplay(ContentDisplay.GRAPHIC_ONLY));
                }
            } else {
                if (((Button)salesAction.getChildren().get(0)).getContentDisplay().equals(ContentDisplay.GRAPHIC_ONLY)) {
                    salesAction.getChildren().stream().map(each -> (Button) each)
                            .forEach(each -> each.setContentDisplay(ContentDisplay.LEFT));
                }

            }
        });


        GNButton btn = new GNButton("Novo Cliente");
        btn.setGraphic(new IconContainer(Icons.ADD_CIRCLE));
        btn.setAlignment(Pos.CENTER_RIGHT);
        btn.setContentDisplay(ContentDisplay.RIGHT);
        btn.setButtonType(GNButtonType.ROUNDED);
        btn.setStyle("-fx-background-radius : 100px;");
        btn.setPrefWidth(120);

        GNButton btnAdd = new GNButton("Novo Profissional");
        btnAdd.setGraphic(new IconContainer(Icons.ADD_CIRCLE));
        btnAdd.setAlignment(Pos.CENTER_RIGHT);
        btnAdd.setContentDisplay(ContentDisplay.RIGHT);
        btnAdd.setButtonType(GNButtonType.ROUNDED);
        btnAdd.setStyle("-fx-background-radius : 100px;");
        btnAdd.setPrefWidth(120);

        searchClientBox = new SideSearchNavigation<>(
                Icons.ACCOUNT_CIRCLE,
                "Selecionar Clientes",
                new GridTile<>(salesController, Repositories.get(Client.class)),
                btn
        );

        btn.setOnAction(event -> {
            getController().setSecondColumn(
                    context.workAndGet("form_client").getRoot()
            );
        });

        searchProfessionalBox = new SideSearchNavigation<>(
                Icons.BADGE,
                "Selecionar Professionais",
                new GridTile<>(salesController, Repositories.get(Professional.class))
        );



    }


    @FXML
    private void goItems() {
        IView view = context.getRoutes().getView("buy");

        if (getController().getCols() < 2) {
            getController().setFirsColumn(view.getRoot());
        } else {
            getController().setSecondColumn(view.getRoot());
        }

        view.getController().pass(true);
        view.getController().onEnter();

    }

    @FXML
    private void openClients() {


        if (getController().getCols() < 2) {
            getController().setFirsColumn(searchClientBox);
        } else {
            getController().setSecondColumn(searchClientBox);
        }

        if(getController().selectedProperty().get() != null) {

            Client client = getController().selectedProperty().get().getClient();
            searchClientBox.onEnter(client.getId(), next);
            searchClientBox.select(client.getId());
            previousNode = searchClientBox;


        }
    }

    @FXML
    private void openProfessionals() {


        if (getController().getCols() < 2) {
            getController().setFirsColumn(searchProfessionalBox);
        } else {
            getController().setSecondColumn(searchProfessionalBox);
        }

        if(getController().selectedProperty().get() != null) {

            Professional client = getController().selectedProperty().get().getProfessional();
            searchProfessionalBox.onEnter(client.getId(), next);
            searchProfessionalBox.select(client.getId());
            previousNode = searchProfessionalBox;


        }
    }



    @Override
    public void work() {

        if (!super.isWorked()) {

            filteredList = new FilteredList<>(
                context.getCashier().getActiveSales(), p -> true);

            tableSales.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {

                if (newValue != null) {
//                    searchProfessionalBox.select(newValue.getProfessional().getId());
//                    searchClientBox.select(newValue.getClient().getId());
                }
//                getController().getItemsColumnController().addd

            });

            btnSearchSale.disableProperty().bind(
                    Bindings.isNull(
                            tableSales.getSelectionModel().selectedItemProperty()
                    )
            );


            btnViewProfessionals.disableProperty().bind(
                    Bindings.isNull(
                            tableSales.getSelectionModel().selectedItemProperty()
                    )
            );

            configTable();

            super.worked();
        }

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

    private void configTable() {

        columnId.setCellValueFactory(param -> param.getValue().idProperty());
        columnProfessional.setCellValueFactory(param -> param.getValue().professionalProperty());
        columnClient.setCellValueFactory(param -> param.getValue().clientProperty());

        columnProfessional.setCellFactory(new AvatarColumnFactory<>());
        columnClient.setCellFactory(new AvatarColumnFactory<>());

        tableSales.setRowFactory(new SaleRowFactory());

        tableSales.setItems(filteredList);

        runThread();

    }

    private void runThread() {

        Task<ObservableList<Sale>> populate = repoCashier.fetchActiveSales();
        Thread thread = new Thread(populate);
        thread.setName("Loading data table [Sales]");
        thread.setPriority(1);
        thread.start();

        populate.setOnRunning(event -> {
            LoadPlaceholder loadPlaceholder = new LoadPlaceholder();
            tableSales.setPlaceholder(loadPlaceholder);
        });

        populate.setOnSucceeded(event -> {
            tableSales.setPlaceholder(new Label("Sem vendas ativas."));

            if (tableSales.getItems() != null) {
                    tableSales.getSelectionModel().selectFirst();
            }


//            new PropertyValueFactory<>()

            IndexController indexController = (IndexController) context.getControlller("sale_index");
            indexController.onEnter();

        });

    }

    public void select (Sale sale) {
        tableSales.getSelectionModel().select(sale);
        tableSales.scrollTo(sale);
    }

    public SalesController getController() {
        if (this.salesController == null) this.salesController = (SalesController) context.getControlller("sales");
        return salesController;
    }

    public ReadOnlyObjectProperty<Sale> seletedItemProperty() {
        return tableSales.getSelectionModel().selectedItemProperty();
    }

    public Sale getSelected() {
        return tableSales.getSelectionModel().getSelectedItem();
    }
}
