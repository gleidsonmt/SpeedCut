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

import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.speedcut.controller.SalesController;
import io.github.gleidsonmt.speedcut.core.app.model.Client;
import io.github.gleidsonmt.speedcut.core.app.model.Person;
import io.github.gleidsonmt.speedcut.core.app.model.Professional;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import io.github.gleidsonmt.speedcut.core.app.util.Scroll;
import io.github.gleidsonmt.speedcut.core.app.view.IManager;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import io.github.gleidsonmt.speedcut.presenter.Presenter;
import io.github.gleidsonmt.speedcut.presenter.SalePresenter;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Duration;

import java.util.stream.Collectors;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/04/2022
 * Refactor 1
 */
public class GridTile<T extends Person> extends ScrollPane implements WorkingNode, IManager {

    private final Presenter<T> presenter;
    private boolean populate = false;
    private final SalesController controller;

    private final GridPane      grid    = new GridPane();
    private final ToggleGroup   group   = new ToggleGroup();

    public GridTile(SalesController controller, Presenter<T> presenter) {
        this.presenter = presenter;
        this.controller = controller;
        this.setFitToWidth(true);
        this.setPadding(new Insets(10,0,0,0));
        this.setContent(grid);
        window.widthProperty().addListener((observableValue, oldValue, newValue) ->
                updateLayout());
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

    private void populate(Presenter<T> presenter, int id) {

        Task<ObservableList<T>> createElements = presenter.createAllElements();

        Thread main = new Thread(createElements);
        main.setDaemon(true);
        main.start();

        Task<T> populate = new Task<>() {
            @Override
            protected T call() {
            int maxCols = getMaxCols();

            int col = 0;
            int row = 0;

            for (T element : presenter.getElements()) {

                Tile<T> tile = new Tile<>(element);
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

                    Sale selected = controller.getSelected();
                    SalePresenter salePresenter = new SalePresenter();

                    if (newValue.getUserData() instanceof Professional) {
                        selected.setProfessional((Professional) newValue.getUserData());
                    } else {
                        selected.setClient((Client) newValue.getUserData());
                    }

                    salePresenter.update(selected);
                    salePresenter.persist();
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
        });

        Thread th = new Thread(populate);
        th.setDaemon(true);

        createElements.setOnSucceeded(eve -> th.start());

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) group.selectToggle(oldValue);
        });
    }

    @Override
    public void work(int id) {
        populate = true;
        populate(presenter, id);
    }

    @Override
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
        if (ResponsiveView.BreakPoints.isXSmall()) {
            maxCols = 1;
        } else if (ResponsiveView.BreakPoints.isSmall()) {
            maxCols = 1;
        } else if (ResponsiveView.BreakPoints.isMedium()) {
            maxCols = 1;
        } else if (ResponsiveView.BreakPoints.isXLarge() || ResponsiveView.BreakPoints.isXXLarge()) {
            maxCols = 2;
        } else if ( window.getWidth() > ResponsiveView.BreakPoints.FHD ) {
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
}
