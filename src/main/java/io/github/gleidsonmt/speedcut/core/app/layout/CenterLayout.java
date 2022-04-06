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

import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.view.IManager;
import io.github.gleidsonmt.speedcut.core.app.view.ViewComposer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class CenterLayout extends VBox implements IManager {

    private final ScrollPane    body        = new ScrollPane();
    private final FlowPane      bar         = new FlowPane();

    public CenterLayout() {
        getChildren().add(0, body);
        body.setFitToWidth(true);
        body.setFitToHeight(true);
        VBox.setVgrow(body, Priority.ALWAYS);
//        VBox.setMargin(body, new Insets(0, 0, 0,0));
        body.getStyleClass().addAll("border", "border-t-1");

        initBar();
    }

    public void setBody(Parent body) {
        this.body.setContent(body);
    }

    public void initBar() {
        getChildren().add(0, bar);
//        VBox.setMargin(bar, new Insets(5, 0, 0,5));
        bar.setPrefHeight(40);
        bar.setMinHeight(40);

//        Hyperlink hyperlink = new Hyperlink("Carousel");
//        hyperlink.setStyle("-fx-font-size : 14pt;");
//
//        bar.getChildren().add(0, hyperlink);
    }

    @Deprecated
    public void addBread(int index, ViewComposer composer) {
        Hyperlink hyperlink = new Hyperlink(composer.getTitle());
        hyperlink.setStyle("-fx-font-size : 14pt;");

        hyperlink.setOnAction(event -> {
            try {
                window.navigate(composer.getName());
            } catch (NavigationException e) {
                e.printStackTrace();
            }
        });

        bar.getChildren().add(index, hyperlink);
    }

}
