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

import io.github.gleidsonmt.speedcut.core.app.model.Service;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/02/2022
 */
public class DaoService extends AbstractDao<Service> {

    @Override
    protected Service createElement(ResultSet result) {
        Service element = new Service();

        try {
            element.setId( result.getInt("id") );
            element.setName(result.getString("NAME"));
            element.setPrice(result.getBigDecimal("PRICE"));

            BigDecimal discount = result.getBigDecimal("DISCOUNT");
            element.setDiscount( discount == null ? BigDecimal.ZERO : discount);

            element.setImgPath(result.getString("IMG_PATH"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return element;
    }

    private Node createDefaultAvatar(String name) {
        List<String> colors = Arrays.asList(
                "#4FC1E9", "#48CFAD", "#AA66CC",
                "#FFA000", "#ED5565");
        Random random = new Random();

        Label graphic = new Label(name.substring(0,1));
        graphic.setPrefSize(35, 35);
        graphic.setContentDisplay(ContentDisplay.CENTER);
        graphic.setTextAlignment(TextAlignment.CENTER);
        graphic.setAlignment(Pos.CENTER);

        graphic.setId("custom-avatar");

        graphic.setStyle("-base : " +
                colors.get(random.nextInt(colors.size())) +";");

        return graphic;
    }

    @Override
    public void put(Service model)  {
        PreparedStatement prepare = prepare("insert into " + table + "(NAME, PRICE) values(?, ?);");
        try {
            prepare.setString(1, model.getName());
            prepare.setBigDecimal(2, model.getPrice());
            prepare.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        commit(); // only tests
    }

}
