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

import io.github.gleidsonmt.speedcut.core.app.model.Company;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/01/2021
 */
public final class DaoCompany extends AbstractDao<Company> {

    private static final ListProperty<Company> elements =
            new SimpleListProperty<>(FXCollections.observableArrayList());


    @Override
    public Company find(long id) {
        Optional<Company> result = elements.stream()
                .filter(f -> f.getId() == id)
                .findAny();
        return result.orElseGet(() -> findInServer(id));
    }

    private @Nullable Company findInServer(long id) {
        connect();
        executeSQL("select * from sale where id like '" + id + "';");

        ResultSet result = result();
        Company element = null;
        try {
            if (result.first()) {
                element = createElement(id, result);
                elements.add(element);
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return element;
    }

    public Task<ObservableList<Company>> createTask() {

        return new Task<>() {

            @Override
            protected void failed() {
                System.err.println("Error in create task on " + getClass());
            }

            @Override
            protected ObservableList<Company> call() {

                connect();
                executeSQL("select * from sale;");
                ResultSet result = result();

                try {
                    while (result.next()) {

                        int id = result.getInt("ID");
                        if (elements.stream().noneMatch(f -> f.getId() == id)) {
                            elements.add(createElement(id, result));
                        }

                    }

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    disconnect();
                }

                return elements;
            }
        };
    }

    @Override
    protected @NotNull Company createElement(long id, ResultSet result) {
        Company element = new Company();

//        try {
//            element.setId(id);
////            element.setRegistered(result.getTimestamp("REGISTERED").toLocalDateTime());
////            element.setProfessional(daoProfessional.find(result.getInt("PROFESSIONAL_ID")));
////            element.setClient(daoClient.find(result.getInt("CLIENT_ID")));
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }

        return element;
    }

    @Override
    public Task<ObservableList<Company>> populateAllTask() {
        return null;
    }
}


