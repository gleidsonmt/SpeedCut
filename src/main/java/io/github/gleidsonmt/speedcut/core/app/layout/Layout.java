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

package io.github.gleidsonmt.speedcut.core.app.layout;

import io.github.gleidsonmt.speedcut.core.app.layout.containers.Drawer;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class Layout extends BorderPane implements ILayout {

    private Node oldLeftNode;

    private final CenterLayout  centerLayout = new CenterLayout();
    private final StackPane drawerBody = new StackPane();


    private final FlowPane      bar          = new FlowPane();
    private final Text          title        = new Text("SpeedCut");

    private BooleanProperty hasDrawer = new SimpleBooleanProperty();

    public Layout() {
        setCenter(centerLayout);

        leftProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) oldLeftNode = newValue;
        });

        hasDrawer.bind(leftProperty().isNull());

        getStyleClass().add("layout");

        //        getChildren().add(0, bar);
        centerLayout.setPadding(new Insets(0, 0, 0,0));

        bar.setPrefHeight(40);
        bar.setMinHeight(40);

        bar.getStyleClass().addAll("border", "border-b-1");

        VBox.setMargin(bar, new Insets(0, 10, 0, 10));

        title.setStyle("-fx-font-size : 18pt; -fx-fill : -info;");

        title.setMouseTransparent(true);
        bar.getChildren().add(title);

        setCenter(centerLayout);
        setLeft(drawerBody);

//        setTop(bar);
//        bar.toBack();
    }

    public FlowPane getBar() {
        return bar;
    }

    public void setTitle(String _title) {
        title.setText(_title);
    }

    public Node getOldLeft() {
        return oldLeftNode;
    }

    public CenterLayout getContent() {
        return centerLayout;
    }

    public void setContent(Parent content) {
        centerLayout.setBody(content);
    }

    @Override
    public void setDrawer(@NotNull IView iView) {
        drawerBody.getChildren().setAll(iView.getRoot());
    }

    @Override
    public void setAside(@NotNull IView iView) {
        setRight(iView.getRoot());
    }

    @Override
    public void setNav(@NotNull IView iView) {
        setTop(iView.getRoot());
    }

    @Override
    public void setFooter(@NotNull IView iView) {
        setBottom(iView.getRoot());
    }

    @Override
    public ReadOnlyDoubleProperty drawerWidthProperty() {
        return drawerBody.widthProperty();
    }

    @Override
    public ReadOnlyBooleanProperty hasDrawerProperty() {
        return ReadOnlyBooleanProperty.readOnlyBooleanProperty(hasDrawer);
    }


}
