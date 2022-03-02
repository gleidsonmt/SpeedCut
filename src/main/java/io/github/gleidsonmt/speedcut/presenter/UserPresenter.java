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

import io.github.gleidsonmt.speedcut.core.app.dao.DaoUser;
import io.github.gleidsonmt.speedcut.core.app.model.User;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/02/2022
 */
public class UserPresenter implements Presenter<User> {

    private static final DaoUser dao = new DaoUser();

    public User find(long id) {
       return dao.find(id);
    }

    public User find(String userName) {
        return dao.find(userName);
    }

    public boolean validate(User user, String password) {
        return dao.validate(user, password);
    }

    @Override
    public void store(User user) {
        dao.store(user);
    }

    @Override
    public boolean persist() {
        return dao.commit();
    }

}
