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

import io.github.gleidsonmt.speedcut.core.app.model.Professional;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  12/06/2022
 */
public class RepoProfessionalImpl implements Repository<Professional> {

    private final DaoProfessional DAO_PROFESSIONAL = new DaoProfessional();

    @Override
    public Professional read(long id) {
        return null;
    }

    @Override
    public AbstractDao<Professional> getDao() {
        return DAO_PROFESSIONAL;
    }

    @Override
    public Task<ObservableList<Professional>> fetchAll() {
        return DAO_PROFESSIONAL.populateAllTask();
    }
}
