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
import io.github.gleidsonmt.speedcut.core.app.model.Cashier;
import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  17/05/2022
 */
public interface Repository<E extends Entity> extends Dao<E> {

    AbstractDao<E> getDao();

    default boolean persist() {
        return getDao().commit();
    }

    default void connect() {
        getDao().connect();
    }

    default void disconnect() {
        getDao().disconnect();
    }

    default boolean delete(E model) {
        return getDao().delete(model);
    }

    default void put(E model) {
        try {
            getDao().put(model);
        } catch (SQLQueryError e) {
            e.printStackTrace();
        }
    }

    default E read(long id) {
        return getDao().read(id);
    }

    default Task<ObservableList<E>> fetchAll() {
        return getDao().fetchAll();
    }

}
