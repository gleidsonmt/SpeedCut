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

import io.github.gleidsonmt.speedcut.core.app.factory.AvatarCreator;
import io.github.gleidsonmt.speedcut.core.app.model.*;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/04/2022
 */
public class Tile<T extends Person> extends ToggleButton implements Context {

    private Label lblName;

    public Tile(T entity) {

        this.setMaxWidth(Double.MAX_VALUE);
        this.setPrefSize(100, 100);

        GridPane.setMargin(this, new Insets(5));
//
        this.setId("person-tile");
        this.setText(entity.getName());
        this.setGraphic(createDetails(entity));

        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setPadding(new Insets(0));

    }


    private Node createDetails(Entity entity) {
        return entity instanceof Client ? createDetailsForClient((Client) entity) : createDetailsForProfessional((Professional) entity);
    }

    private GridPane createDetailsForClient(Client model) {

        GridPane wrapper = new GridPane();
        wrapper.getStyleClass().add("wrapper");

//        wrapper.setGridLinesVisible(true);

        VBox left = new VBox();
        left.setId("box-left");
        left.setAlignment(Pos.CENTER);
        left.setPadding(new Insets(0, 0,10,10));

        HBox titleBox = new HBox();
        titleBox.setId("tile-box");

        StackPane iconSelected =new StackPane();
        iconSelected.setPrefSize(30, 30);
//        iconSelected.setContent("M20.95 31.95 35.4 17.5l-2.15-2.15-12.3 12.3L15 21.7l-2.15 2.15ZM6 42V6h36v36Zm3-3h30V9H9Zm0 0V9v30Z");
        titleBox.getChildren().add(iconSelected);
        iconSelected.getStyleClass().add("icon-tile");

        VBox.setMargin(titleBox, new Insets(3));

        Label title = new Label();
        title.setText(model.getName() + " " + model.getLastName());
        title.setId("title");
        title.getStyleClass().add("h4");

        title.setId("title");
        title.setGraphic(iconSelected);
        titleBox.getChildren().add(title);
        title.setGraphicTextGap(10);

        left.getChildren().add(titleBox);

        left.setSpacing(10);

        HBox scoreBox = new HBox();
        scoreBox.setId("score-box");

        Label titleScore = new Label(model.getScore().getText() + " 10/500");

        switch (model.getScore()) {
            case SUPER -> titleScore.setStyle("-fx-text-fill : linear-gradient(to left, -green, -mint); -fx-font-weight : bold;");
            case PREMIUM -> titleScore.setStyle("-fx-text-fill : linear-gradient(to left, -deep-purple, -purple); -fx-font-weight : bold;");
            case VETERAN -> titleScore.setStyle("-fx-text-fill : linear-gradient(to left, -info, -cyan); -fx-font-weight : bold;");
            case SPECIAL -> titleScore.setStyle("-fx-text-fill : linear-gradient(to left, -mint, -info); -fx-font-weight : bold;");
            case NOVICE -> titleScore.setStyle("-fx-text-fill : linear-gradient(to left, -amber, -deep-orange); -fx-font-weight : bold;");
            case USUALLY -> titleScore.setStyle("-fx-text-fill : linear-gradient(to left, -green, -grass); -fx-font-weight : bold;");
        }

        Label points = new Label("55");
        points.setId("points");
        titleScore.setGraphicTextGap(6);
        titleScore.setId("title-score");
        titleScore.getStyleClass().add("h5");


        titleScore.setGraphic(AvatarCreator.createAvatarWithScore(model.getScore()));

        scoreBox.getChildren().addAll( titleScore);
        left.getChildren().add(scoreBox);

//        Node avatar = model.getAvatar(100, 1, 10);

//        wrapper.getChildren().addAll(left, avatar);

        wrapper.setPadding(new Insets(10));

        GridPane.setConstraints(left, 0,0,1,1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
//        GridPane.setConstraints(avatar, 1,0,1,1, HPos.RIGHT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);

        return wrapper;
    }

    private @NotNull VBox createDetailsForClientB(@NotNull Client model) {

        VBox boxDetails = new VBox();
        boxDetails.setAlignment(Pos.CENTER);
        boxDetails.setPadding(new Insets(5));

        boxDetails.getStyleClass().add("box-details");

        Label lblScore = new Label("" + (model.getScore().getPoints() < 1 ? "(●'◡'●))" : model.getScore().getPoints()));
        lblScore.getStyleClass().add("lbl-score");
        lblScore.setWrapText(true);
        lblScore.getStyleClass().add("h5");

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

    private @NotNull VBox createDetailsForProfessional(Professional model) {

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
