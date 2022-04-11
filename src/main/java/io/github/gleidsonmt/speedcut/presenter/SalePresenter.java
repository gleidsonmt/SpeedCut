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
import io.github.gleidsonmt.speedcut.core.app.dao.DaoSale;
import io.github.gleidsonmt.speedcut.core.app.exceptions.SQLQueryError;
import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.ResultSet;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/03/2022
 */
public class SalePresenter extends Presenter<Sale> {

    private static final DaoSale dao = new DaoSale();

    @Override
    protected AbstractDao<Sale> getDao() {
        return dao;
    }

    public Sale createDefaultSale() {
        createConnection();
        return dao.createDefaultSale();
    }

    public boolean delete(Sale sale) {
        createConnection();
        return dao.delete(sale);
    }

    public boolean update(Sale sale) {
        createConnection();
        try {
            return dao.update(sale);
        } catch (SQLQueryError e) {
            e.printStackTrace();
        }
        return false;
    }

    public ObservableList<Sale> getElements() {
        return dao.getElements();
    }
}
