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
import io.github.gleidsonmt.speedcut.core.app.model.Client;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiFunction;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public class DaoClient extends AbstractDao<Client> {

    private Image getSLCircle(int width, int height) {
        WritableImage raster = new WritableImage(width, height);
        PixelWriter pixelWriter = raster.getPixelWriter();
        Point2D center = new Point2D((double) width / 2, (double) height / 2);
//        for (int y = 0; y < height; y++) {
//            for (int x = 0; x < width; x++) {
//                double dy = x - center.getX();
//                double dx = y - center.getY();
//                pixelWriter.setColor(x, y, Color.gray(dx, dy));
//            }
//        }
        return raster;
    }

    public static <T> Image transform(Image in, BiFunction<Color, T, Color> f, T arg) {
//        int width = (int) in.getWidth();
//        int height = (int) in.getHeight();
        int width = 200;
        int height = 200;

        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(x, y, f.apply(in.getPixelReader().getColor(x, y), arg));
            }
        }
        return out;
    }

    @Override
    protected Client createElement(long id, ResultSet result) {

        Client element = new Client();

        try {
            element.setId( (int) id);
            element.setName(result.getString("NAME"));
            element.setAvatar(result.getString("IMG_PATH"));



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
