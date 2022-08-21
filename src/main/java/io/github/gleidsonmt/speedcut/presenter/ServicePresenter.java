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
import io.github.gleidsonmt.speedcut.core.app.dao.DaoService;
import io.github.gleidsonmt.speedcut.core.app.dao.DaoUser;
import io.github.gleidsonmt.speedcut.core.app.model.Service;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/02/2022
 */
public class ServicePresenter extends DaoUser.Presenter<Service> {

    private final DaoService dao = new DaoService();

    public Service find(long id) {
        return null;
    }

    @Override
    public void store(Service model) {
        dao.put(model);
    }

    public boolean update(Service model) {
        return false;
    }

    public boolean delete(Service model) {
        return false;
    }

    @Override
    public AbstractDao<Service> getDao() {
        return null;
    }

    public Task<ObservableList<Service>> createAllElements() {
        return dao.populateAllTask();
    }
}
