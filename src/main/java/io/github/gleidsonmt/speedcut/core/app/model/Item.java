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

import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  25/12/2020
 */

public class Item extends Entity {

    private final ObjectProperty<Avatar>    avatar      = new SimpleObjectProperty<>();
    private final ObjectProperty<Avatar>    thumbnail   = new SimpleObjectProperty<>();
    private final StringProperty            description = new SimpleStringProperty();

    public Avatar getAvatar() {
        return avatar.get();
    }

    public ObjectProperty<Avatar> avatarProperty() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar.set(avatar);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Avatar getThumbnail() {
        return thumbnail.get();
    }

    public ObjectProperty<Avatar> thumbnailProperty() {
        return thumbnail;
    }

    public void setThumbnail(Avatar thumbnail) {
        this.thumbnail.set(thumbnail);
    }
}