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

package io.github.gleidsonmt.speedcut.core.app.layout.util;

import io.github.gleidsonmt.speedcut.core.app.model.Avatar;
import io.github.gleidsonmt.speedcut.core.app.model.AvatarMode;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.logging.Logger;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  08/09/2022
 */
@SuppressWarnings("unused")
public class AvatarView extends Rectangle implements Context {

    private final ObjectProperty<Image> image = new SimpleObjectProperty<>();

    public AvatarView(Avatar avatar) {
        this(avatar, 200, 200, 100, 1, Color.WHITE, null, AvatarMode.FULL);
    }

    public AvatarView(Avatar avatar, double size) {
        this(avatar, size, size, 100, 1, Color.WHITE, null, AvatarMode.FULL);
    }

    public AvatarView(Avatar avatar, AvatarMode type) {
        this(avatar, 30, 30, 100, 1, Color.WHITE, null, type);
    }

    public AvatarView(Avatar avatar, double width, double height, double arc,
                      double borderWidth, Color stroke, Effect effect, AvatarMode type) {

        this.setWidth(width);
        this.setHeight(height);

        this.setStroke(stroke);
        this.setEffect(effect);

        this.setArcWidth(arc);
        this.setArcHeight(arc);

        this.setStrokeWidth(borderWidth);


        this.image.set(avatar);

        this.setFill(new ImagePattern(avatar));

    }

    public Image getImage() {
        return image.get();
    }

    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    public void setImage(Image image) {
        this.image.set(image);
    }
}
