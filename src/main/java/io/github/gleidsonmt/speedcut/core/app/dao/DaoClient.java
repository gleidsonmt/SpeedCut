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

package io.github.gleidsonmt.speedcut.core.app.dao;

import io.github.gleidsonmt.gncontrols.options.model.Avatar;
import io.github.gleidsonmt.speedcut.core.app.factory.AvatarCreator;
import io.github.gleidsonmt.speedcut.core.app.model.Client;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public class DaoClient extends AbstractDao<Client> {

    @Override
    protected Client createElement(long id, ResultSet result) {

        Client element = new Client();

        try {
            element.setId( (int) id);
            element.setName(result.getString("NAME"));

            element.setAvatar(new AvatarCreator(element.getName()));

//            String path = "/io.github.gleidsonmt.speedcut.core.app/";
////            String imageUri = result.getString("AVATAR");
//            String imageUri = "theme/img/avatars/man5@400.png";
//
//            Circle circle = new Circle();
//            circle.setRadius(18);
//            circle.setStrokeWidth(2);
//            circle.setStroke(Color.WHITE);
//            circle.setEffect(new DropShadow(5, Color.gray(0.8)));
//
//            ImagePattern imagePattern = new ImagePattern(
//                    new Avatar(Objects.requireNonNull(getClass().getResource(path + imageUri)).toExternalForm())
//            );
//
//            circle.setFill(imagePattern);

//            if (imageUri.startsWith("theme")) {
//                element.setAvatar(circle);
//            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return element;
    }

    // store(Field fields)
    // store("name", "price");
    // insert into(name, price) values

    // update

}
