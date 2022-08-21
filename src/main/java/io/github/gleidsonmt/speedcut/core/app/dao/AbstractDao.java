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
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.*;
import java.util.*;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  25/12/2020
 */
@SuppressWarnings("unused")
public abstract class AbstractDao<T extends Entity> implements Dao<T>, Connection {

    private static final DataConnection dataConnection =
            new DataConnection();

    private final ListProperty<T> elements =
            new SimpleListProperty<>(FXCollections.observableArrayList()); // monostate

    protected String table =
            getClass().getSimpleName().replaceAll("Dao", "").toLowerCase();

    public AbstractDao() {

    }

    /**********************************************************************************
     *
     *      Connection
     *
     **********************************************************************************/


    @Override
    public void connect() {
        try {
            if (dataConnection.getConnection() == null) {
                    dataConnection.init();
            }

            if (dataConnection.getConnection().isClosed()) {
                dataConnection.init();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public boolean isClosed() {
        return dataConnection.isClosed();
    }

    public boolean isConnected() {
        try {
            if (dataConnection.getConnection() == null) return false;
            return dataConnection.getConnection().isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void disconnect() {
        try {
            dataConnection.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected ResultSet executeSQL(String sql) {
        try {
            return dataConnection.executeSQL(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    protected ResultSet result() {
        return dataConnection.getResult();
    }

    protected PreparedStatement prepare(String sql) {
        try {

            if (dataConnection.isClosed()) connect();

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
                    "s where id like '" + model.getId() + "';").execute();

            elements.remove(model);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public void put(T model) throws SQLQueryError {
        throw new SQLQueryError("SQLQueryError: " ,
                String.format("Not implemented method for %s yet.",
                        table));
    }


    // Creates an update or store query using cols as an array
    protected String createStoreQuery(T model, List<String> values) {

        StringBuilder columns = new StringBuilder();
        StringBuilder _items = new StringBuilder();

        String sql;

        if (create(model)) {

            for (int i = 0; i < values.size(); i++) {
                if (i != (values.size() -1 )) {

                    columns.append(values.get(i).concat(", "));
                    _items.append("?, ");
                } else {
                    columns.append(values.get(i));
                    _items.append("?");
                }
            }

            sql = "insert into " + table + "s(" + columns + ") "
                    + "values(" + _items + ");";
        } else {

            for (int i = 0; i < values.size(); i++) {
                if (i != (values.size() -1 )) {
                    columns.append(values.get(i).concat(" = ?, "));
                } else {
                    columns.append(values.get(i)).append(" = ?");
                }
            }

            sql = "update " + table + "s set " + columns + " "
                    + "where id = " + model.getId() + ";";
        }

        return sql;
    }

    public ArrayList<String> list(String... items) {
        return new ArrayList<>(List.of(items));
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

    @Deprecated
    protected T createElement(long id, ResultSet result) throws SQLException {
        return null;
    }

    protected abstract T createElement(ResultSet result);

    protected void add(T t) {
        if (!contains(t.getId())) getElements().add(t);
    }

    protected boolean create(T t) {
        return t.getId() == 0;
    }

    protected boolean contains (long id) {
        return elements.stream().anyMatch(f -> f.getId() == id);
    }

    protected boolean contains (List<T> list, long id) {
        return list.stream().anyMatch(f -> f.getId() == id);
    }

    protected void setId(T t) {
        if (create(t)) {
            ResultSet re = executeSQL("select last_insert_id();");
            try {
                if (re.first()) {
                    t.setId(re.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void setIdAndAdd(T t) {
        if (create(t)) {
            ResultSet re = executeSQL("select last_insert_id();");
            try {
                if (re.first()) {
                    t.setId(re.getInt(1));
                    add(t);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public T read(long id) {
        // Find in elements
        Optional<T> result = elements.stream()
                .filter(f -> f.getId() == id)
                .findAny();

        // return element in java array or find in sql server
        return result.orElseGet(() -> findInServer(id));
    }

    // Read an element from the list if don't have anyone reads from server
    public T read(String name) {
        Optional<T> result = elements.stream()
                .filter(f -> Objects.equals(f.getName(), name))
                .findAny();
        // return element in java array or find in sql server
        return result.orElseGet(() -> findInServer(name));
    }

    // Reads a list when the list contains a conditition using liked ex where like 'id'
    public ObservableList<T> readListWhere(String column, int id) {

        ObservableList<T> list = FXCollections.observableArrayList();
        try {

            connect();
            ResultSet resultSet = executeSQL("select * from " + table + "s where " + column + " like '" + id + "';");

            while (resultSet.next()) {
                T element = createElement(resultSet);
                list.add(element);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Find in server an entity
    protected T findInServer(long id) {
        connect();
        ResultSet result = executeSQL("select * from " + table + "s where id like '" + id + "';");
        T element;
        try {
            if (result.first()) {
                element = createElement( result);
                elements.add(element);
                return element;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Find in server for an entity from your name
    private T findInServer(String name) {
        connect();
        ResultSet result = executeSQL("select * from " + table +"s where name like '" + name + "';");

        T element;
        try {
            if (result.first()) {
                element = createElement(result);
                elements.add(element);
                return element;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected T findInServer(String col, String value) {
        connect();
        ResultSet result = executeSQL("select * from " + table +"s where " + col + " like '" + value + "';");

        T element;
        try {
            if (result.first()) {
                element = createElement(result);
                elements.add(element);
                return element;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Deprecated
    public Task<ObservableList<T>> populateAllTask() {
        return new Task<>() {

            @Override
            protected void failed() {
                System.err.println("Error on finding items for dao class " + getClass());

                getException().printStackTrace();
            }

            @Override
            protected synchronized ObservableList<T> call() {
                connect();

                executeSQL("select count(id) as count from " + table + "s;");
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

                executeSQL("select * from " + table + "s;");
                result = result();

                try {
                    while (result.next()) {
                        int id = result.getInt("id");


                        if (elements.stream().noneMatch(f -> f.getId() == id)) {
                            elements.add(createElement(result));
                        }

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                disconnect();
                return elements.get();
//                return elements;
            }
        };
    }

    public Task<ObservableList<T>> fetchAll() {
        return new Task<>() {

            @Override
            protected void failed() {
                System.err.println("Error on finding items for dao class " + getClass());

                getException().printStackTrace();
            }

            @Override
            protected synchronized ObservableList<T> call() {
                connect();

                executeSQL("select count(id) as count from " + table + "s;");
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

                executeSQL("select * from " + table + "s;");
                result = result();

                try {
                    while (result.next()) {
                        int id = result.getInt("id");


                        if (elements.stream().noneMatch(f -> f.getId() == id)) {
                            elements.add(createElement(result));
                        }

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                disconnect();
                return elements.get();
//                return elements;
            }
        };
    }

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

    @Deprecated
    public abstract static class Presenter<T extends Entity> {

        public void store(T model) {
            try {
                getDao().put(model);
            } catch (SQLQueryError e) {
                e.printStackTrace();
            }
        }

        public T find(long id) {
            return  getDao().read(id);
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
