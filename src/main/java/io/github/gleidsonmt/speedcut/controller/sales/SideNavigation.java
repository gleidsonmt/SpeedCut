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
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.gncontrols.options.FieldType;
import io.github.gleidsonmt.gncontrols.options.TrayAction;
import io.github.gleidsonmt.speedcut.controller.SalesController;
import io.github.gleidsonmt.speedcut.core.app.model.Person;
import io.github.gleidsonmt.speedcut.core.app.model.Professional;
import io.github.gleidsonmt.speedcut.core.app.view.ActionViewController;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/04/2022
 */
public class SideNavigation<T extends Person> extends StackPane {

    private final VBox body = new VBox();
    private final VBox boxHeader = new VBox();
    private final HBox header = new HBox();
    private final VBox boxSearch = new VBox();

    private final Button btnBack = new Button();
    private final Label lblInfo = new Label();
    private final GNTextBox search = new GNTextBox();

    private final SalesController controller;
    private final GridTile<T> content;

    public SideNavigation (SalesController controller, Icons icon, String title, GridTile<T> content) {
        this(controller, icon, title, content, (Node) null);
    }

    public SideNavigation (SalesController controller, Icons icon, String title, GridTile<T> content, Node... actions) {
        this.controller = controller;
        this.content = content;
        configLayout(new IconContainer(icon), title);
        configActions();
    }

    private void configLayout (IconContainer icon, String title) {

        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(0, 5, 5, 5));

        VBox.setVgrow(this, Priority.ALWAYS);

        getChildren().add(body);
        body.getChildren().add(boxHeader);

        boxHeader.setMaxHeight(122);

        header.setMinHeight(60);
        header.setMaxHeight(60);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().addAll("border", "border-b-1");
        boxHeader.getChildren().add(header);

        btnBack.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        btnBack.setGraphic(new IconContainer(Icons.ARROW_BACK));
        btnBack.setPrefWidth(50);
        btnBack.setPrefHeight(60);
        btnBack.setMinHeight(60);
        btnBack.getStyleClass().addAll("btn-flat", "border", "border-r-1");

        lblInfo.setText(title);
        lblInfo.getStyleClass().addAll("h3");
        lblInfo.setStyle("-fx-text-fill : -base;");
        icon.setStyle("-fx-fill : -info;");
        lblInfo.setGraphic(icon);
        HBox.setMargin(lblInfo, new Insets(0, 0, 0, 10));

        header.getChildren().setAll(btnBack, lblInfo);

        boxSearch.setMinHeight(62);
        boxSearch.setMaxHeight(62);

        search.setTrayAction(TrayAction.CLEAR);
        search.setFieldType(FieldType.FILLED);
        search.setLeadIcon(Icons.SEARCH);
        search.setMaxHeight(Double.MAX_VALUE);
        boxSearch.getChildren().addAll(search);
        VBox.setVgrow(search, Priority.ALWAYS);

        boxHeader.getChildren().addAll(boxSearch);

        body.getChildren().add(content);
        VBox.setVgrow(content, Priority.ALWAYS);

        search.textProperty().addListener((observableValue, s, newValue) -> content.find(newValue));
    }

    private void configActions() {
        btnBack.setOnAction(action -> controller.resetSecondColumn());
    }

    public void onEnter(int id) {
        if (!content.isPopulate()) {
            setAlignment(Pos.CENTER);
            content.work(id);
        }

        search.requestFocus();
    }

    public void select (int id) {
        content.select(id);
    }
}
