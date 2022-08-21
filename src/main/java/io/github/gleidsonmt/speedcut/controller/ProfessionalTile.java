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

import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.model.Professional;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/03/2022
 */
public class ProfessionalTile extends ToggleButton implements Context {

    public ProfessionalTile(Professional professional) {
        StackPane root = new StackPane();

        setCursor(Cursor.HAND);

        setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                try {
                    context.getRoutes().setContent("sales");
                } catch (NavigationException e) {
                    e.printStackTrace();
                }
            }
        });

        VBox box = new VBox();
        box.setStyle("-fx-background-color : -background-color; -fx-background-radius : 10px;");
        box.getStyleClass().add("depth-1");
        box.setPadding(new Insets(5));

        box.getStyleClass().add("box");
        box.setAlignment(Pos.CENTER);

        double borderWidth = 4;
        double radius = 60;

        setMinSize(250, 300);
        setPrefSize(250, 300);
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

//        if (professional.getAvatar() != null) {
//            box.getChildren().add(
//                    pro
//            );
//        } else {
//            Label avatar = AvatarCreator.createDefaultAvatar(professional.getName(), radius, borderWidth, 40);
//            avatar.setStyle("-base : derive(-info, 30%); -fx-font-weight : bold; -fx-font-size : 40pt;");
//            box.getChildren().add(avatar);
//        }
        box.getChildren().add(professional.getAvatar());

        VBox boxDetails = new VBox();
        boxDetails.setAlignment(Pos.CENTER);
        boxDetails.setPadding(new Insets(10));

        Label lblName = new Label(professional.getName());
        lblName.setAlignment(Pos.CENTER);
        lblName.setWrapText(true);
        lblName.setStyle("-fx-font-weight : bold; -fx-text-fill : -info;");
        lblName.getStyleClass().add("h3");

        Hyperlink contact = new Hyperlink("jefferson@gmail.com");
        contact.setWrapText(true);
        contact.getStyleClass().add("h4");

        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(0.5D);
        progressBar.setMinHeight(2);
        progressBar.setPrefHeight(3);
        VBox.setMargin(progressBar, new Insets(10, 0,0, 0));

        Label lblSpeciality = new Label("Creative Designer");
        lblSpeciality.getStyleClass().addAll("border", "border-t-1", "h4");

        VBox.setMargin(lblSpeciality, new Insets(10, 0, 0, 0));

        boxDetails.getChildren().addAll(lblName, contact, progressBar, lblSpeciality);

        box.getChildren().add(boxDetails);
        root.getChildren().add(box);

        IconContainer iconContainer = new IconContainer(Icons.DONE);
        iconContainer.setScaleX(1.5);
        iconContainer.setScaleY(1.5);

        Label iconBox = new Label();
        iconBox.getStyleClass().add("icon-box");
        iconBox.setGraphic(iconContainer);
        iconBox.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        root.getChildren().add(iconBox);

        root.setAlignment(Pos.TOP_RIGHT);
        iconBox.setTranslateX(-10);
        iconBox.setTranslateY(10);

        root.getStyleClass().add("wrapper");
        getStyleClass().add("toggle-pro");

        setGraphic(root);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }
}
