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
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  17/05/2022
 */
public class RepoSaleImpl implements Repository<Sale> {

    private final DaoSale DAO_SALE = new DaoSale();

    @Override
    public AbstractDao<Sale> getDao() {
        return DAO_SALE;
    }

    @Override
    public Task<ObservableList<Sale>> fetchAll() {
        return DAO_SALE.populateAllTask();
    }

    @Override
    public void put(Sale sale) {
        try {
            DAO_SALE.put(sale);
        } catch (SQLQueryError e) {
            e.printStackTrace();
        }
    }
}
