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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/02/2022
 */
public final class DaoUser extends AbstractDao<User> {

    private final ObjectProperty<User> current = new SimpleObjectProperty<>();

    public boolean validate(User user, String compare) {

        return user.getPassword().
                equals(
                        createSecurePassword(compare, user.getSalt())
                );
    }

    // used to read with the username...
    public User read(String col, String value) {
        return findInServer(col, value);
    }

    @Deprecated
    public ObjectProperty<User> actProperty() {
        if (current.get() == null) {
            try {
                connect();
                executeSQL("select * from users where logged = true;");
                if (result().next()) {
                    User user = createElement(result());
                    current.set(user);
                    return current;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return current;
    }

    public User getCurrent() {
        if (current.get() == null) {
            try {
                connect();
                executeSQL("select * from users where logged = true;");
                if (result().next()) {
                    User user = createElement(result());
                    current.set(user);
                    return current.get();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return current.get();
    }

    @Override
    protected User createElement(ResultSet result) {
        User element = new User();

        try {

            element.setId(result.getInt("id"));

            element.setUserName(result.getString("username"));
            element.setLogged(result.getBoolean("logged"));


            element.setPassword(result.getString("password"));
            element.setSalt(result.getBytes("salt"));

            element.setEmail(result.getString("email"));


            element.setImgPath(result.getString("img_path"));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return element;
    }

    @Override
    public void put(User model)  {
        connect();
        
        List<String> cols = !create(model) ?
                list("username", "logged") :
                list("username", "logged", "password", "salt");
        

        PreparedStatement prepare = prepare(createStoreQuery(model, cols));

        try {

            if (model.getSalt() == null)
                model.setSalt(getSalt());

            prepare.setString(1, model.getUserName());            
            prepare.setBoolean(2, model.isLogged());


            if (create(model)) { // Exception for create a sha password
                prepare.setString(3, createSecurePassword(
                        model.getPassword(),
                        model.getSalt())
                );
                prepare.setBytes(4, model.getSalt());
            }


            prepare.execute();

            add(model);

            if (model.isLogged()) {
                current.set(model);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private String createSecurePassword(String password, byte[] salt) {

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
