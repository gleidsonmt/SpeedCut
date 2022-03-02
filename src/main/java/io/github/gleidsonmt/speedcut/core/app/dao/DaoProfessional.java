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

import io.github.gleidsonmt.speedcut.core.app.model.Professional;
import io.github.gleidsonmt.speedcut.core.app.model.Professional;
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/02/2022
 */
public class DaoProfessional extends AbstractDao<Professional> {

    private static final ListProperty<Professional> elements =
            new SimpleListProperty<>(FXCollections.observableArrayList());

    private final String table = getClass().getSimpleName().replaceAll("Dao", "");

    @Override
    public Professional find(long id) {
        // Find in elements
        Optional<Professional> result = elements.stream()
                .filter(f -> f.getId() == id)
                .findAny();
        // return element in java array or find in sql server
        return result.orElseGet(() -> findInServer(id));
    }

    public Professional find(String name) {
        Optional<Professional> result = elements.stream()
                .filter(f -> Objects.equals(f.getName(), name))
                .findAny();
        // return element in java array or find in sql server
        return result.orElseGet(() -> findInServer(name));
    }

    // Find user in sql server
    private Professional findInServer(long id) {
        connect();
        executeSQL("select * from user where id like '" + id + "';");

        ResultSet result = result();
        Professional element;
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

    private Professional findInServer(String name) {
        connect();
        executeSQL("select * from product where NAME like '" + name + "';");

        ResultSet result = result();
        Professional element;
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

    private Professional createElement(long id, ResultSet result) {
        Professional element = new Professional();

        try {
            element.setId( (int) id);
            element.setName(result.getString("NAME"));

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
    public void store(Professional model)  {
        connect();
        PreparedStatement prepare = prepare("insert into " + table + "(name) values(?);");
        try {
            prepare.setString(1, model.getName());
            prepare.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        commit(); // only tests
    }

    public Task<ObservableList<Professional>> populateAllTask() {

        return new Task<>() {

            @Override
            protected void failed() {
                System.err.println("Error on finding items for dao class " + getClass());
                getException().printStackTrace();
            }

            @Override
            protected ObservableList<Professional> call() {

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
//                return elements;
            }
        };
    }
}
