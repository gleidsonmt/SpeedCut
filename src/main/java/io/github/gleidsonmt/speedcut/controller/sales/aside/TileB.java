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

import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.core.app.factory.AvatarCreator;
import io.github.gleidsonmt.speedcut.core.app.model.Client;
import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import io.github.gleidsonmt.speedcut.core.app.model.Person;
import io.github.gleidsonmt.speedcut.core.app.model.Professional;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/04/2022
 */
public class TileB<T extends Person> extends ToggleButton implements Context {

    private Label lblName;

    public TileB(T entity) {

        this.setMaxWidth(Double.MAX_VALUE);

        StackPane root = new StackPane();
        setCursor(Cursor.HAND);

        setStyle("-fx-effect : none; -fx-background-color : transparent;");

        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
//                try {
////                    context.getDecorator().navigate("sales");
//                } catch (NavigationException e) {
//                    e.printStackTrace();
//                }
            }
        });


        VBox box = new VBox();
//        box.setStyle("-fx-background-color : -background-color; " +
//                "-fx-background-radius : 10px;" +
//                "-fx-border-width : 1; -fx-border-color : -light-gray-2;" +
//                "-fx-border-radius :10;");
//        box.getStyleClass().add("depth-2");
        box.setPadding(new Insets(5));

        box.setPrefSize(-1, -1);
        box.setMinSize(-1, -1);
        box.setMaxSize(-1, -1);

        box.getStyleClass().add("box");
        box.setAlignment(Pos.CENTER);

        double borderWidth = 2;
        double radius = 60;

//        setMinSize(200, 200);
//        setPrefSize(-1, 200);
//        setMaxSize(-1, 200);

        if (entity.getAvatar() != null) {

            String path;
            String imgPath;
            Image image = null;

//            if (entity.getImgPath().contains("theme")) {
//                path = "/io.github.gleidsonmt.speedcut.core.app/";
//                imgPath = Objects.requireNonNull(getClass().getResource(path + entity.getImgPath())).toExternalForm();
//                image = new Image(imgPath, 300, 300, true, true);
//            } else {
//                File file = new File(entity.getImgPath());
//                try {
//                    image = new Image(new FileInputStream(file));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//
//            }

//            Rectangle rectangle = new Rectangle();
//            rectangle.setStroke(Color.WHITE);
//            rectangle.setArcWidth(50);
//            rectangle.setArcHeight(50);
//            rectangle.setEffect(new DropShadow(5, Color.gray(0.6)));
//            rectangle.setWidth(150);
//            rectangle.setHeight(100);
//            rectangle.setFill(new ImagePattern(image));
//            box.getChildren().add(rectangle);

            box.getChildren().add(AvatarCreator.createAvatar(image, Color.WHITE, 0, radius));
        } else {

//            Label avatar = AvatarCreator.createDefaultAvatar(entity.getName(), radius, borderWidth, 40);
//            avatar.setStyle("-base : derive(-info, 30%); -fx-font-weight : bold; -fx-font-size : 40pt;");
//            box.getChildren().add(avatar);
        }


        lblName = new Label(entity.getName());
        lblName.setAlignment(Pos.CENTER);
        lblName.setWrapText(true);
//        lblName.setStyle(" -fx-text-fill : -info;");
        lblName.getStyleClass().addAll("title");
        lblName.getStyleClass().add("h4");

        VBox boxDetails = createDetails(entity);
        boxDetails.getChildren().add(0, lblName);
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

        this.setGraphic(root);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    private VBox createDetails(Entity entity) {
        return entity instanceof Client ? createDetailsForClient((Client) entity) : createDetailsForProfessional((Professional) entity);
    }

    private VBox createDetailsForClient(Client model) {

        VBox boxDetails = new VBox();
        boxDetails.setAlignment(Pos.CENTER);
        boxDetails.setPadding(new Insets(5));

        boxDetails.getStyleClass().add("box-details");

        Label lblScore = new Label("" + (model.getScore().getPoints() < 1 ? "(●'◡'●))" : model.getScore().getPoints()));
        lblScore.getStyleClass().add("lbl-score");
        lblScore.setWrapText(true);
//        lblScore.getStyleClass().add("h5");

        String _color = "-info";
        String badge = "novice";

        switch (model.getScore()) {
            case NOVICE -> {
                _color = "-info";
                badge = "novice";
            }
            case USUALLY -> {
                _color = "-mint";
                badge = "usually";
            }
            case VETERAN -> {
                _color = "-bittersweet";
                 badge = "veteran";

            }
            case PREMIUM -> {
                _color = "-grapefruit";
                 badge = "premium";

            }
            case SUPER -> {
                _color = "-sunflower";
                 badge = "super";

            }

            case SPECIAL -> {
                _color = "-sunflower";
                badge = "special";
            }
        }

        Label lblSpeciality = new Label(model.getScore().getText());
        lblSpeciality.getStyleClass().addAll( "h5", "lbl-info");
//        lblSpeciality.setStyle("-fx-text-fill : " + _color + ";");


//        SVGPath icon = new SVGPath();
//        icon.setContent("m16.083 22.833 1.459-4.791-3.917-3.084h4.833L20 10.125l1.542 4.833h4.833l-3.958 3.084 1.458 4.791L20 19.875Zm-5.958 15.5v-12.75Q8.333 23.708 7.5 21.396q-.833-2.313-.833-4.729 0-5.625 3.854-9.479Q14.375 3.333 20 3.333q5.625 0 9.479 3.855 3.854 3.854 3.854 9.479 0 2.416-.833 4.729-.833 2.312-2.625 4.187v12.75L20 35.042ZM20 27.208q4.417 0 7.479-3.062 3.063-3.063 3.063-7.479 0-4.417-3.063-7.479Q24.417 6.125 20 6.125t-7.479 3.063q-3.063 3.062-3.063 7.479 0 4.416 3.063 7.479 3.062 3.062 7.479 3.062Zm-7.125 7.125L20 32.25l7.125 2.083v-6.458q-1.625 1.083-3.458 1.604Q21.833 30 20 30q-1.833 0-3.667-.521-1.833-.521-3.458-1.604ZM20 31.083Z");
//        icon.setScaleX(0.8);
//        icon.setScaleY(0.8);
//        VBox.setMargin(lblSpeciality, new Insets(10, 0, 10, 0));
//        icon.setStyle("-fx-fill : " + _color + ";");
//
        String path = "/io.github.gleidsonmt.speedcut.core.app/";
        String imgPath = Objects.requireNonNull(AvatarCreator.class.getResource(path + "theme/img/badges/" + badge + ".png")).toExternalForm();
        Image image = new Image(imgPath, 30, 30, true, true);

        Circle circle = new Circle();
//        circle.setRadius(18);
//        circle.setStrokeWidth(2);
//        circle.setStroke(Color.TRANSPARENT);
////        circle.setEffect(new DropShadow(5, Color.gray(0.8)));
//
//        circle.setFill(new ImagePattern(image));

//        ImageView imgView = new ImageView();
//        imgView.setImage(image);
//        imgView.setFitWidth(30);
//        imgView.setFitHeight(30);
//        lblSpeciality.setGraphic(imgView);

        StackPane icon = new StackPane();
        StackPane iconBox = new StackPane();

        iconBox.getStyleClass().add("lbl-info-icon");
        iconBox.setAlignment(Pos.CENTER);
        icon.setPrefSize(30, 30);
        iconBox.setPrefSize(40, 40);
        icon.setBackground(new Background(
                new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)
        ));

        iconBox.getChildren().add(icon);
        lblSpeciality.setGraphic(iconBox);
        boxDetails.getChildren().addAll(lblScore, new Separator(), lblSpeciality);

        VBox.setMargin(lblSpeciality, new Insets(10));
        return boxDetails;
    }

    private VBox createDetailsForProfessional(Professional model) {
        VBox boxDetails = new VBox();
        boxDetails.setAlignment(Pos.CENTER);
        boxDetails.setPadding(new Insets(10));

        Hyperlink contact = new Hyperlink("jefferson@gmail.com");
        contact.setWrapText(true);
        contact.getStyleClass().add("h5");

        ProgressBar progressBar = new ProgressBar();
        progressBar.setProgress(0.5D);
        progressBar.setMinHeight(2);
        progressBar.setPrefHeight(3);
        VBox.setMargin(progressBar, new Insets(10, 0,0, 0));

        Label lblSpeciality = new Label("Creative Designer");
        lblSpeciality.getStyleClass().addAll("border", "border-t-1", "h5");

        VBox.setMargin(lblSpeciality, new Insets(10, 0, 0, 0));

        boxDetails.getChildren().addAll(contact, progressBar, lblSpeciality);

        return boxDetails;
    }
}
