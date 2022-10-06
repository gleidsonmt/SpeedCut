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

package io.github.gleidsonmt.speedcut.controller.sales.main.componets;

import io.github.gleidsonmt.gncontrols.controls.GNTextBox;
import io.github.gleidsonmt.speedcut.controller.sales.aside.Tile;
import io.github.gleidsonmt.speedcut.controller.sales.main.SalesController;
import io.github.gleidsonmt.speedcut.core.app.dao.Repositories;
import io.github.gleidsonmt.speedcut.core.app.dao.Repository;
import io.github.gleidsonmt.speedcut.core.app.model.Client;
import io.github.gleidsonmt.speedcut.core.app.model.Person;
import io.github.gleidsonmt.speedcut.core.app.model.Professional;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import io.github.gleidsonmt.speedcut.core.app.util.Scroll;
import io.github.gleidsonmt.speedcut.core.app.view.BreakPoints;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/04/2022
 * Refactor 2
 */
@SuppressWarnings("all")
public class GridTile<T extends Person> extends ScrollPane implements Context {

    private boolean populate = false;

    private SalesController controller;

    private final GridPane      grid    = new GridPane();
    private final ToggleGroup   group   = new ToggleGroup();

    private final Repository<T> repository;

    private String type = "c";

    public GridTile(SalesController controller, Repository<T> repository) {
        this.repository = repository;
//        this.presenter = presenter;

        this.controller = controller;
        this.setFitToWidth(true);
        this.setPadding(new Insets(10,5,0,5));
        this.setContent(grid);

        context.getDecorator().getRoot().widthProperty().addListener((observableValue, oldValue, newValue) ->
                updateLayout());

        grid.setPadding(new Insets(8));
        grid.setHgap(5);
        grid.setVgap(10);
    }

    public void find (String value) {
        if (value != null) {
            grid.getChildren().setAll(
                    group.getToggles().stream().map(m -> (ToggleButton) m)
                            .filter(f -> ((Person) f.getUserData()).getName().toLowerCase()
                            .contains(value.toLowerCase()))
                            .collect(Collectors.toList())
            );
            updateLayout();
        }
    }

    private void populate(@NotNull Repository<T> repository, int id, GNTextBox search) {

        Task<ObservableList<T>> createElements = repository.fetchAll();

        Thread main = new Thread(createElements);
        main.setDaemon(true);
        main.start();

        Task<T> populate = new Task<>() {

            @Override
            protected T call() {

            int maxCols = getMaxCols();

            int col = 0;
            int row = 0;

            for (T element : repository.getDao().getElements()) {

                Tile<T> tile = new Tile<>(element);

                tile.setOnMouseClicked(mouseEvent -> {

                });

                tile.setUserData(element);
                group.getToggles().add(tile);

                Platform.runLater(() -> grid.getChildren().addAll(tile));

                GridPane.setConstraints(tile, col, row, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

                if (maxCols > col) col++;

                if (col == maxCols) {
                    col = 0;
                    row++;
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {

                    Sale selected = getController().getSaleSelected();

                    Repository<Sale> repoSale   = Repositories.get(Sale.class);

                    if (newValue.getUserData() instanceof Professional) {
                        selected.setProfessional((Professional) newValue.getUserData());
                    } else {
                        selected.setClient((Client) newValue.getUserData());
                    }

                    repoSale.connect();
                    repoSale.put(selected);
                    repoSale.persist();

                }
            });

            updateLayout();
            return null;
            }
        };

        populate.setOnSucceeded(eve -> {

            for (Toggle t : group.getToggles()) {
                Person person = (Person) t.getUserData();
                if (id == person.getId() ) {
                    group.selectToggle(t);
                    scrollTo((ToggleButton) t);
                }
            }

            search.requestFocus();
        });

        Thread th = new Thread(populate);
        th.setDaemon(true);

        createElements.setOnSucceeded(eve -> {
            this.type = ((ObservableList<T>) eve.getSource().getValue()).get(0) instanceof Client ? "c" : "p";
            th.start();
        });

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) group.selectToggle(oldValue);
        });

    }

    public void work(int id, GNTextBox search) {
        populate = true;
        populate(repository, id, search);
    }

    public boolean isPopulate() {
        return populate;
    }

    private void updateLayout () {
        int maxCols = this.getMaxCols();

        int col = 0;
        int row = 0;

        for (Node node : grid.getChildren()) {

            GridPane.setConstraints(node, col, row, 1, 1,
                    HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

            if (maxCols > col) col++;

            if (col == maxCols) {
                col = 0;
                row++;
            }
        }
    }

    private int getMaxCols () {


        int maxCols = 0;
        if (BreakPoints.isXSmall()) {
            maxCols = type.equals("c") ? 1 :2;
        } else if (BreakPoints.isSmall()) {
            maxCols = type.equals("c") ? 1 :2;
        } else if (BreakPoints.isMedium()) {
            maxCols = type.equals("c") ? 1 :2;
        } else if (BreakPoints.isXLarge() || BreakPoints.isXXLarge()) {
            maxCols = type.equals("c") ? 1 : 3;
        } else if ( context.getDecorator().getWidth() > BreakPoints.FHD ) {
            maxCols = 3;
        }
        return maxCols;
    }

    @SuppressWarnings("unchecked")
    public void select (int id) {

        for (Toggle toggle : group.getToggles()) {
            T pro = (T) toggle.getUserData();
            if (pro.getId() == id) {
                group.selectToggle(toggle);
                this.scrollTo((ToggleButton) toggle);
            }
        }

    }

    private void scrollTo (Node node) {
        Scroll.scrollTo(this, node);
    }

    private SalesController getController() {
        if (controller == null) controller = ( SalesController) context.getRoutes().getView("sales").getController();
        return controller;
    }

}
