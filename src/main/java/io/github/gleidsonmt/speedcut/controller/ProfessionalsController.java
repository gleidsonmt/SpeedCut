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

import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import io.github.gleidsonmt.speedcut.core.app.model.Professional;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import io.github.gleidsonmt.speedcut.presenter.ProfessionalPresenter;
import io.github.gleidsonmt.speedcut.presenter.SalePresenter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
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
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/03/2022
 */
public class ProfessionalsController extends ResponsiveView {

    @FXML private GNTextBox search;
    @FXML private StackPane root;
    @FXML private GridPane grid;
    @FXML private ScrollPane scroll;

    private final ProfessionalPresenter presenter = new ProfessionalPresenter();
    private boolean init = false;
    private Sale model;
    private final ToggleGroup group = new ToggleGroup();

    @Override
    public synchronized void onEnter() {
        super.onEnter();

        scroll.setVvalue(0D);

        if (!init) {

            Task<ObservableList<Professional>> populate = presenter.createAllElements();

            group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == null) group.selectToggle(oldValue);
            });

            Thread thread = new Thread(populate);
            thread.setName("Thread Load Professionls on professionals controller.");
            thread.start();

            populate.setOnSucceeded(event -> {

                grid.getChildren().clear();

                for (Professional professional : presenter.getElements()) {

                    ProfessionalTile professionalTile = new ProfessionalTile(professional);
                    professionalTile.setUserData(professional);
                    group.getToggles().add(professionalTile);
                    grid.getChildren().add(professionalTile);
                }

                updateLayout(root.getPrefWidth());

                for (Toggle toggle : group.getToggles()) {
                    Professional pro = (Professional) toggle.getUserData();
                    if (pro.getId() == model.getProfessional().getId()) {
                        group.selectToggle(toggle);
                    }
                }
            });

            group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    model.setProfessional((Professional) newValue.getUserData());
                    SalePresenter presenter = new SalePresenter();
                    presenter.update(model);
                    presenter.persist();
                }
            });

            init = true;
        }
    }

    @Override
    protected void updateLayout(double width) {
        int col = 0;
        int row = 0;
        int maxCols = 0;

        if (grid.getChildren().size() > 5) {
            if (window.getWidth() < 630) {
                maxCols = 1;
            } else if (window.getWidth() < 950) {
                maxCols = 2;
            } else {
                maxCols = 3;
            }
        }

        for (Node node : grid.getChildren()) {
            update(node, col, row);
//            GridPane.setConstraints(node, col, row, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

//                col++;
            if (maxCols > col) col++;

            if (col == maxCols) {
                col = 0;
                row++;
            }
        }
        search.requestFocus();

    }

    @Override
    public void onExit() {

    }

    @Override
    public synchronized void updateModel(Entity entity) {
        model = (Sale) entity;
        search.requestFocus();
    }

    @FXML
    private void backToSales() {
        try {

            if (window.getViews().getCurrent().equals(window.getViews().get("sales")) ||
                window.getViews().getPrevious().equals(window.getViews().get("sales"))
            ) {
                window.navigate("sales");
            }

            if (window.getWrapper().getPopOver().isShowing()) {
                window.getWrapper().getPopOver().close();
            }

        } catch (NavigationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                grid.getChildren().setAll(
                        group.getToggles().stream().map(m -> (ToggleButton) m)
                            .filter(f ->
                                    ((Professional)f.getUserData()).getName().toLowerCase()
                                    .contains(newValue.toLowerCase()
                            ) )
                        .collect(Collectors.toList()));
            }
        });
    }
}
