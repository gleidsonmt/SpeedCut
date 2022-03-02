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

    private static final ListProperty<Service> elements =
            new SimpleListProperty<>(FXCollections.observableArrayList());

    private final String table = getClass().getSimpleName().replaceAll("Dao", "");

    @Override
    public Service find(long id) {
        // Find in elements
        Optional<Service> result = elements.stream()
                .filter(f -> f.getId() == id)
                .findAny();
        // return element in java array or find in sql server
        return result.orElseGet(() -> findInServer(id));
    }

    public Service find(String name) {
        Optional<Service> result = elements.stream()
                .filter(f -> Objects.equals(f.getName(), name))
                .findAny();
        // return element in java array or find in sql server
        return result.orElseGet(() -> findInServer(name));
    }

    // Find user in sql server
    private Service findInServer(long id) {
        connect();
        executeSQL("select * from " + table + " where id like '" + id + "';");

        ResultSet result = result();
        Service element;
        try {
            if (result.first()) {
                element = createElement(id, result);
                elements.add(element);
                return element;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }

    private Service findInServer(String name) {
        connect();
        executeSQL("select * from " + table + " where NAME like '" + name + "';");

        ResultSet result = result();
        Service element;
        try {
            if (result.first()) {
                element = createElement(result.getInt("id"), result);
                elements.add(element);
                return element;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }

    private Service createElement(long id, ResultSet result) {
        Service element = new Service();

        try {
            element.setId( (int) id);
            element.setName(result.getString("NAME"));
            element.setPrice(result.getBigDecimal("PRICE"));

            element.setAvatar(createDefaultAvatar(element.getName()));

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
    public void store(Service model)  {
        connect();
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

    public Task<ObservableList<Service>> populateAllTask() {

        return new Task<>() {

            @Override
            protected void failed() {
                System.err.println("Error on finding items for model dao " + table);
            }

            @Override
            protected ObservableList<Service> call() {
//                only tests
//                double _p = 1;
//                for (int i = 0; i < 200; i++) {
//                    Service service = new Service("Name " + i);
//                    service.setPrice(new BigDecimal(_p * 10));
//                    _p++;
//                    store(service);
//                }

                connect();

                executeSQL("select count(id) as count from " + table + ";");
                ResultSet result = result();

                try {
                    if (result.first()) {
                        if (result().getInt("count") == elements.size()) {
                            return elements;
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                executeSQL("select * from " + table + ";");
                result = result();

                try {
                    while (result.next()) {
                        int id = result.getInt("ID");
                        if (elements.stream().noneMatch(f -> f.getId() == id)) {
                            elements.add(createElement(id, result));
                        }

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    disconnect();
                }

                return elements.get();
//                return elements; // only test
            }
        };
    }
}
