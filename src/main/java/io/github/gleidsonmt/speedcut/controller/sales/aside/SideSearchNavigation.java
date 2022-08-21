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

package io.github.gleidsonmt.speedcut.controller.sales.aside;

import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.gncontrols.options.FieldType;
import io.github.gleidsonmt.gncontrols.options.TrayAction;
import io.github.gleidsonmt.speedcut.controller.sales.main.componets.GridTile;
import io.github.gleidsonmt.speedcut.controller.sales.main.SalesController;
import io.github.gleidsonmt.speedcut.core.app.model.Person;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/04/2022
 */
public class SideSearchNavigation<T extends Person> extends StackPane implements Context {

    private final VBox body = new VBox();
    private final VBox boxSearch = new VBox();
    private final VBox boxHeader = new VBox();

    private final GNTextBox search = new GNTextBox();

    private final GridTile<T> content;

    private SalesController controller;

    private String next = "saleItem";

    private final SideHeaderNavigation header;

    public SideSearchNavigation(Icons icon, String title, GridTile<T> content) {
        this(icon, title, content, (Node) null);
    }

    public SideSearchNavigation(Icons icon, String title, GridTile<T> content, Node... actions) {
        this.content = content;
        this.header = new SideHeaderNavigation(new IconContainer(icon), title);

        HBox leftActions = new HBox();
        leftActions.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(leftActions, Priority.ALWAYS);


        if (actions.length > 0) {
            for (Node action : actions) {

                if (action != null)
                    leftActions.getChildren().add(action);
            }
        }
        header.getChildren().add(leftActions);

        configLayout();
    }

    private void configLayout () {

        setAlignment(Pos.TOP_CENTER);
//        setPadding(new Insets(0, 5, 5, 5));

        VBox.setVgrow(this, Priority.ALWAYS);

        getChildren().add(body);

        boxHeader.setMaxHeight(122);

        boxHeader.getChildren().add(header);
        body.getChildren().add(boxHeader);

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

    public void onEnter(int id, String next) {

        System.out.println("id = " + id);

        if (!content.isPopulate()) {
            setAlignment(Pos.CENTER);
            content.work(id, search);
        } else search.requestFocus();

        this.next = next;
    }

    public String getNext() {
        return next;
    }

    public GNTextBox getSearch () {
        return search;
    }

    public void select (int id) {
        content.select(id);
    }

    public SalesController getController() {
        if (this.controller == null) this.controller = (SalesController) context.getControlller("sales");
        return controller;
    }


}
