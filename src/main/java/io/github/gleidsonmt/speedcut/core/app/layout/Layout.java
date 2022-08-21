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

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class Layout extends BorderPane implements ILayout {

    private Node oldLeftNode;

    private final CenterLayout  centerLayout = new CenterLayout();
    private final FlowPane      bar          = new FlowPane();
    private final Text          title        = new Text("SpeedCut");

    public Layout() {
        setCenter(centerLayout);

        leftProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) oldLeftNode = newValue;
        });

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

    @Override
    public void setContent(Parent iView) {
        centerLayout.setBody(iView);
    }

    @Override
    public void setDrawer(Parent iView) {
        setLeft(iView);
    }

    @Override
    public void setAside(Parent iView) {
        setRight(iView);
    }

    @Override
    public void setNav(Parent iView) {
        setTop(iView);
    }

    @Override
    public void setFooter(Parent iView) {
        setBottom(iView);
    }
}
