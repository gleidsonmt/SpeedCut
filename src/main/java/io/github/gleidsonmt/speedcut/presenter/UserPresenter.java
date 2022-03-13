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

package io.github.gleidsonmt.speedcut.presenter;

import io.github.gleidsonmt.speedcut.core.app.dao.AbstractDao;
import io.github.gleidsonmt.speedcut.core.app.dao.DaoUser;
import io.github.gleidsonmt.speedcut.core.app.exceptions.SQLQueryError;
import io.github.gleidsonmt.speedcut.core.app.model.User;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/02/2022
 */
public final class UserPresenter extends AbstractDao.Presenter<User> {

    public User find(long id) {
       return getDao().find(id);
    }

    public User find(String userName) {
        return ((DaoUser) getDao()).find(userName);
    }

    public boolean validate(User user, String password) {
        return ((DaoUser) getDao()).validate(user, password);
    }

    @Override
    public void store(User user) {
        try {
            getDao().store(user);
        } catch (SQLQueryError e) {
            e.printStackTrace();
        }
    }

    @Override
    public AbstractDao<User> getDao() {
        return new DaoUser();
    }

}
