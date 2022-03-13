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

import io.github.gleidsonmt.speedcut.core.app.database.DataConnection;
import io.github.gleidsonmt.speedcut.core.app.exceptions.SQLQueryError;
import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import io.github.gleidsonmt.speedcut.core.app.model.Professional;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import io.github.gleidsonmt.speedcut.core.app.model.User;
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
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  25/12/2020
 */
public abstract class AbstractDao<T extends Entity> implements Dao<T> {

    private static final DataConnection dataConnection =
            new DataConnection();

    private final ListProperty<T> elements =
            new SimpleListProperty<>(FXCollections.observableArrayList()); // monostate

    protected final String table =
            getClass().getSimpleName().replaceAll("Dao", "").toLowerCase();

    public AbstractDao() {

    }



    /**********************************************************************************
     *
     *      Connection
     *
     **********************************************************************************/

    @Deprecated
    protected Connection getConnection() {
        return dataConnection.getConnection();
    }

    protected Statement getStatement() {
        return dataConnection.getStatement();
    }

    public void createConnection() {
        connect();
    }

    protected void connect() {
        try {
            if (dataConnection.getConnection() == null) {
                    dataConnection.init();
            } else if (dataConnection.getConnection().isClosed()) {
                dataConnection.init();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed() {
        return dataConnection.isClosed();
    }

    public void disconnect() {
        try {
            dataConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected boolean executeSQL(String sql) {
        try {
            return dataConnection.executeSQL(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    protected ResultSet result() {
        return dataConnection.getResult();
    }

    protected PreparedStatement prepare(String sql) {
        try {
            return dataConnection.getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**********************************************************************************
     *
     *      Dao
     *
     **********************************************************************************/

    @Override
    public boolean delete(T model) {
        boolean result = false;
        connect();
        try {
            result = prepare("delete from " +
                    table +
                    " where id like '" + model.getId() + "';").execute();

            elements.remove(model);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public void store(T model) throws SQLQueryError {
        throw new SQLQueryError("SQLQueryError: " ,
                String.format("Not implemented method for %s yet.",
                        table));
    }

    @Override
    public boolean update(T model) throws SQLQueryError {
            throw new SQLQueryError("SQLQueryError: " ,
                    String.format("Not implemented method for %s yet.",
                            table));
    }

    public boolean commit() {

        try {
            dataConnection.getConnection().commit();
            dataConnection.getConnection().close();

            return true;
        } catch (SQLException throwables) {

            throwables.printStackTrace();

            try {
                dataConnection.getConnection().rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return false;
        } finally {
            disconnect();
        }
    }


    /**********************************************************************************
     *
     *      Util
     *
     **********************************************************************************/

    protected T createElement(long id, ResultSet result) {
        return null;
    }

    @Override
    public T find(long id) {
        // Find in elements
        Optional<T> result = elements.stream()
                .filter(f -> f.getId() == id)
                .findAny();
        // return element in java array or find in sql server
        return result.orElseGet(() -> findInServer(id));
    }

    public T find(String name) {
        Optional<T> result = elements.stream()
                .filter(f -> Objects.equals(f.getName(), name))
                .findAny();
        // return element in java array or find in sql server
        return result.orElseGet(() -> findInServer(name));
    }

    private T findInServer(long id) {
        connect();
        executeSQL("select * from " + table + " where id like '" + id + "';");

        ResultSet result = result();
        T element;
        try {
            if (result.first()) {
                element = createElement(id, result);
                elements.add(element);
                return element;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private T findInServer(String name) {
        connect();
        executeSQL("select * from " + table +" where NAME like '" + name + "';");

        ResultSet result = result();
        T element;
        try {
            if (result.first()) {
                element = createElement(result.getInt("id"), result);
                elements.add(element);
                return element;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Task<ObservableList<T>> populateAllTask() {
        return new Task<>() {

            @Override
            protected void failed() {
                System.err.println("Error on finding items for dao class " + getClass());
                getException().printStackTrace();
            }

            @Override
            protected ObservableList<T> call() {

                executeSQL("select count(id) as count from " + table + ";");
                ResultSet result = result();
                System.out.println(result);

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
                }

                return elements.get();
//                return elements;
            }
        };
    }

//    protected Node createDefaultAvatar(@NotNull String name) {
//        List<String> colors = Arrays.asList(
//                "#4FC1E9", "#48CFAD", "#AA66CC",
//                "#FFA000", "#ED5565");
//        Random random = new Random();
//
//        Label graphic = new Label(name.substring(0,1));
//        graphic.setPrefSize(35, 35);
//        graphic.setContentDisplay(ContentDisplay.CENTER);
//        graphic.setTextAlignment(TextAlignment.CENTER);
//        graphic.setAlignment(Pos.CENTER);
//
//        graphic.setId("custom-avatar");
//
//        graphic.setStyle("-base : " +
//                colors.get(random.nextInt(colors.size())) +";");
//
//        return graphic;
//    }

    /**********************************************************************************
     *
     *      Getters And Setters
     *
     **********************************************************************************/

    public ObservableList<T> getElements() {
        return elements.get();
    }

    public ListProperty<T> elementsProperty() {
        return elements;
    }

    public void setElements(ObservableList<T> elements) {
        this.elements.set(elements);
    }

    /**********************************************************************************
     *
     *      Presenter
     *
     **********************************************************************************/

    public abstract static class Presenter<T extends Entity> {

        public void store(T model) {
            try {
                getDao().store(model);
            } catch (SQLQueryError e) {
                e.printStackTrace();
            }
        }

        public boolean update(T model) {
            try {
                return getDao().update(model);
            } catch (SQLQueryError e) {
                e.printStackTrace();
            }
            return false;
        }

        public T find(long id) {
            return  getDao().find(id);
        }

        boolean delete(T model) {
            return getDao().delete(model);
        }

        public boolean persist() {
            return getDao().commit();
        }

        abstract protected AbstractDao<T> getDao();

        public Task<ObservableList<T>> createAllElements() {
            return getDao().populateAllTask();
        }

    }
}
