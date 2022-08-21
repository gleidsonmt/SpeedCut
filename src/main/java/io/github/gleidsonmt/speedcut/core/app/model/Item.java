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
package io.github.gleidsonmt.speedcut.core.app.model;

import io.github.gleidsonmt.speedcut.core.app.factory.AvatarCreator;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.sql.Struct;
import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  25/12/2020
 */

public class Item extends Entity {

    private final ObjectProperty<Rectangle> avatar = new SimpleObjectProperty<>();
    private final StringProperty imgPath = new SimpleStringProperty();

    private Rectangle recAvatar;
//    Image image = new Image(imgPath, 90, 0, true, false);


    public Item() {
    }

    public Node getAvatar() {
        return getAvatar(30);
    }

    public Node getAvatar(double size) {
        return getAvatar(size, 1);
    }

    public Node getAvatar(double size, double borderWidth) {
        return getAvatar(size, borderWidth, Color.WHITE);
    }

    public Node getAvatar(double size, double borderWidth, Color stroke) {
        return getAvatar(size, borderWidth, stroke, null);
    }

    public Node getAvatar(double size, double borderWidth, double arc) {
        return getAvatar(size, size, arc, borderWidth, Color.WHITE, null);
    }

    public Node getAvatar(double size, double borderWidth, Color stroke, Effect effect) {
        return getAvatar(size, size, borderWidth, 1, stroke, effect);
    }

    public Node getAvatar(double width, double height, double arc, double borderWidth, Color stroke, Effect effect) {
        if (imgPath.get() == null) {
            imgPath.set("theme/img/avatars/woman@400.png");
        }
            File file = new File(imgPath.get());

            if (file.exists()) {
                return createAvatar(
                        new Image(file.getAbsolutePath(), 200, 200, true, true),
                        width, height, arc, borderWidth, stroke, effect);
            } else{
                String path = "/io.github.gleidsonmt.speedcut.core.app/";
                return createAvatar(
                        new Image(
                                Objects.requireNonNull(getClass().getResource(path + getImgPath())).toExternalForm(),  200, 200, true, true ),
                        width, height, arc, borderWidth, stroke, effect
                );
            }
    }

    private Rectangle createAvatar(Image image, double width, double height, double arc, double borderWidth, Color stroke, Effect effect) {

        if (avatar.get() == null) {
            recAvatar = new Rectangle();
            avatar.set(recAvatar);
        }

        Rectangle recAvatar = new Rectangle();

        recAvatar.setWidth(width);
        recAvatar.setHeight(height);

        recAvatar.setStroke(stroke);
        recAvatar.setEffect(effect);
//
        recAvatar.setArcWidth(arc);
        recAvatar.setArcHeight(arc);
//
        recAvatar.setStrokeWidth(borderWidth);

        recAvatar.setFill(new ImagePattern(image));

        return recAvatar;
    }

    public String getImgPath() {
        return imgPath.get();
    }

    public StringProperty imgPathProperty() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath.set(imgPath);
    }
}
