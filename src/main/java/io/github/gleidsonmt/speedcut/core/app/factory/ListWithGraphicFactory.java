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

import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import io.github.gleidsonmt.speedcut.core.app.model.Item;
import io.github.gleidsonmt.speedcut.core.app.model.Product;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.util.*;


/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/02/2022
 */
public class ListWithGraphicFactory<T extends Item> implements Callback<ListView<T>, ListCell<T>> {

    @Override
    public ListCell<T> call(ListView param) {
        return new ListCell<>(){

            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                    setId("badge-cell");

                    setGraphic(null);

                    if (item.getAvatar() != null) {
//                        setGraphic(item.getAvatar());

                        String avatar = item.getAvatar();

                        if (avatar.contains("theme")) {

                            String path = "/io.github.gleidsonmt.speedcut.core.app/";
                            String imgPath = Objects.requireNonNull(getClass().getResource(path + avatar)).toExternalForm();
                            Image image = new Image(imgPath, 90, 0, true, false);
                            setGraphic(AvatarCreator.createAvatar(image));
                        }
                    } else {
                        setGraphic(AvatarCreator.createDefaultAvatar(item.getName(), 18));
                    }

                    setGraphicTextGap(15);
                    setItem(item);

                } else {

                    setItem(null);
                    setGraphic(null);
                    setText(null);

                }
            }
        };
    }
}
