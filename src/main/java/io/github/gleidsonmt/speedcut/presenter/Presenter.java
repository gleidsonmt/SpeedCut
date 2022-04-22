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
import io.github.gleidsonmt.speedcut.core.app.exceptions.SQLQueryError;
import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public abstract class Presenter<T extends Entity> {

    public void store(T model) {
        try {
            createConnection();
            getDao().store(model);
        } catch (SQLQueryError e) {
            e.printStackTrace();
        }
    }

    public boolean update(T model) {
        try {
            createConnection();
            return getDao().update(model);
        } catch (SQLQueryError e) {
            e.printStackTrace();
        }
        return false;
    }

    public T find(long id) {
        createConnection();
        T _value = getDao().find(id);
        getDao().disconnect(); // the find does not operation or transaction out of daos.
        return _value;
    }

    boolean delete(T model) {
        createConnection();
        return getDao().delete(model);
    }

    public boolean persist() {
        return getDao().commit();
    }

    abstract protected AbstractDao<T> getDao();

    public void createConnection() {
        getDao().createConnection();
    }

    public Task<ObservableList<T>> createAllElements() {
        createConnection();
        return getDao().populateAllTask();
    }

    public ObservableList<T> getElements() {
        return getDao().getElements();
    }

}
