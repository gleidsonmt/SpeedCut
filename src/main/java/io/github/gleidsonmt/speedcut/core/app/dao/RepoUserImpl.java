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

import io.github.gleidsonmt.speedcut.core.app.model.User;
import javafx.beans.property.ObjectProperty;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/07/2022
 */
public class RepoUserImpl implements Repository<User>  {

    private final DaoUser DAO_USER = new DaoUser();

    @Override
    public AbstractDao<User> getDao() {
        return DAO_USER;
    }

    @Deprecated
    public ObjectProperty<User> actProperty() {
        return DAO_USER.actProperty();
    }

    public User getCurrent() {
        return DAO_USER.getCurrent();
    }

    public User read(String col, String value) {
        return DAO_USER.read(col, value);
    }

    public boolean validate(User user, String password) {
        return DAO_USER.validate(user, password);
    }
}
