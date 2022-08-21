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

package io.github.gleidsonmt.speedcut.core.app.factory;

import io.github.gleidsonmt.speedcut.core.app.model.Item;
import io.github.gleidsonmt.speedcut.core.app.model.Model;
import io.github.gleidsonmt.speedcut.core.app.model.Score;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public class AvatarCreator extends Label {

    public static Label createDefaultAvatar(Model model) {
       return createDefaultAvatar(model.getName());
    }

    public static Label createDefaultAvatar(String name) {
        return createDefaultAvatar(name, 35);
    }


    public static Label createDefaultAvatar(String name, double radius) {
        return createDefaultAvatar(name, radius,2, 12);
    }

    public static Label createDefaultAvatar(String name, double radius, double borderWidth, double textSize) {
        Label label = new Label();
        List<String> colors = Arrays.asList(
                "#4FC1E9", "#48CFAD", "#AA66CC",
                "#FFA000", "#ED5565");
        label.setText(name.substring(0,1));

        Random random = new Random();

        label.setPrefSize(radius * 2, radius * 2);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);

        label.setId("custom-avatar");

        label.setStyle("-fx-font-size : " + textSize + ";" +
                "-fx-border-width : " + borderWidth + ";" +
                "-base : " +
                colors.get(random.nextInt(colors.size())) +";");
        return label;
    }

    public static Label createDefaulRecttAvatar(String name, double size, double borderWidth, double textSize) {
        Label label = new Label();
        List<String> colors = Arrays.asList(
                "#4FC1E9", "#48CFAD", "#AA66CC",
                "#FFA000", "#ED5565");
        label.setText(name.substring(0,1));

        Random random = new Random();

        label.setPrefSize(size * 2, size * 2);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);

        label.setId("custom-rect-avatar");

        label.setStyle("-fx-font-size : " + textSize + ";" +
                "-fx-border-width : " + borderWidth + ";" +
                "-fx-background-color : " +
                colors.get(random.nextInt(colors.size())) +";");
        return label;
    }


    public static Circle createAvatar(Item item) {
        String path = "/io.github.gleidsonmt.speedcut.core.app/";
        String imgPath = Objects.requireNonNull(AvatarCreator.class.getResource(path + item.getImgPath())).toExternalForm();
        Image image = new Image(imgPath, 90, 0, true, false);
        return createAvatar(image);
    }

    public static Circle createAvatar(Item item, Color borderColor) {
        String path = "/io.github.gleidsonmt.speedcut.core.app/";
        String imgPath = Objects.requireNonNull(AvatarCreator.class.getResource(path + item.getImgPath())).toExternalForm();
        Image image = new Image(imgPath, 90, 0, true, false);
        return createAvatar(image, borderColor);
    }

    public static Circle createAvatar(Item item, Color borderColor, double borderWidth, double radius,  double width, double height, boolean preserverRatio, boolean smooth) {
        String path = "/io.github.gleidsonmt.speedcut.core.app/";
        String imgPath = Objects.requireNonNull(AvatarCreator.class.getResource(path + item.getImgPath())).toExternalForm();
        Image image = new Image(imgPath, width, height, preserverRatio, smooth);
        return createAvatar(image, borderColor, borderWidth, radius);
    }

    public static Circle createAvatar(Image image) {
        Circle circle = new Circle();
        circle.setRadius(18);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.WHITE);
//        circle.setEffect(new DropShadow(5, Color.gray(0.8)));

        circle.setFill(new ImagePattern(image));
        return circle;
    }

    public static Circle createAvatar(Image image, Color borderColor) {
        Circle circle = new Circle();
        circle.setRadius(18);
        circle.setStrokeWidth(2);
        circle.setStroke(borderColor);
        circle.setEffect(new DropShadow(5, Color.gray(0.8)));

        circle.setFill(new ImagePattern(image));
        return circle;
    }

    public static Circle createAvatar(Image image, Color borderColor, double borderWidth, double radius) {
        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setStrokeWidth(borderWidth);
        circle.setStroke(borderColor);
        circle.setEffect(new DropShadow(5, Color.gray(0.8)));


        circle.setFill(new ImagePattern(image));
        return circle;
    }

    public static Rectangle createRectAvatar(Image image, Color borderColor, double borderWidth, double size) {
        Rectangle circle = new Rectangle();
        circle.setStrokeWidth(borderWidth);
        circle.setStroke(borderColor);
        circle.setWidth(size);
        circle.setHeight(size);
        circle.setEffect(new DropShadow(5, Color.gray(0.8)));


        circle.setFill(new ImagePattern(image));
        return circle;
    }

    public static Circle createAvatarWithScore(Score _score) {
        String path = "/io.github.gleidsonmt.speedcut.core.app/";

        String _x =  null;

        String imgPath = Objects.requireNonNull(AvatarCreator.class.getResource(path + "theme/img/badges/" +_score.toString().toLowerCase() + "_64.png")).toExternalForm();
        Image image = new Image(imgPath, 64, 64, true, true);
        return createAvatar(image);
    }
}
