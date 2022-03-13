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

import io.github.gleidsonmt.speedcut.core.app.exceptions.SQLQueryError;
import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import io.github.gleidsonmt.speedcut.core.app.model.User;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/02/2022
 */
public final class DaoUser extends AbstractDao<User> {

    private static final ListProperty<User> elements =
            new SimpleListProperty<>(FXCollections.observableArrayList());

    @Override
    public User find(long id) {
        // Find in elements
        Optional<User> result = elements.stream()
                .filter(f -> f.getId() == id)
                .findAny();
        // return element in java array or find in sql server
        return result.orElseGet(() -> findInServer(id));
    }

    public User find(String userName) {
        Optional<User> result = elements.stream()
                .filter(f -> Objects.equals(f.getUserName(), userName))
                .findAny();
        // return element in java array or find in sql server
        return result.orElseGet(() -> findInServer(userName));
    }


    public boolean validate(User user, String password) {
        return user.getPassword().
                equals(
                        createSecurePassword(password, user.getSalt())
                );
    }

    // Find user in sql server
    private User findInServer(long id) {
        connect();
        executeSQL("select * from user where id like '" + id + "';");

        ResultSet result = result();
        User element;
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

    private User findInServer(String userName) {
        connect();
        executeSQL("select * from user where USERNAME like '" + userName + "';");

        ResultSet result = result();
        User element;
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

    @Override
    protected User createElement(long id, ResultSet result) {
        User element = new User();

        try {
            element.setSalt(getSalt());
            element.setId( (int) id);
            element.setUserName(result.getString("USERNAME"));

            element.setPassword(result.getString("PASSWORD"));
            element.setSalt(result.getBytes("SALT"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return element;
    }

    @Override
    public void store(User model)  {
        connect();
        PreparedStatement prepare = prepare("insert into user(username, password, salt) values(?, ?, ?);");
        try {
            model.setSalt(getSalt());
            prepare.setString(1, model.getUserName());
            prepare.setString(2, createSecurePassword(
                    model.getPassword(),
                    model.getSalt())
            );
            prepare.setBytes(3, model.getSalt());
            prepare.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Task<ObservableList<User>> populateAllTask() {
        return null;
    }

    public String createSecurePassword(String password, byte[] salt) {

        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    private byte[] getSalt()  {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

}
