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

import io.github.gleidsonmt.speedcut.core.app.model.Cashier;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  17/05/2022
 */
public class RepoCashierImpl implements Repository<Cashier> {

    private final DaoCashier DAO_CASHIER = new DaoCashier();
    private final DaoSale DAO_SALE = new DaoSale();

    private final ObjectProperty<Cashier> cashier = new SimpleObjectProperty<>();

    @Override
    public AbstractDao<Cashier> getDao() {
        return DAO_CASHIER;
    }

    @Override
    public Task<ObservableList<Cashier>> fetchAll() {
        return DAO_CASHIER.populateAllTask();
    }

    @Override
    public void put(Cashier cashier) {

    }

    @Override
    public boolean delete(Cashier model) {
        return false;
    }

    @Override
    public Cashier read(long id) {
        return DAO_CASHIER.read(id);
    }


    @Override
    public boolean persist() {
        return DAO_CASHIER.commit();
    }

    public Cashier getOpened() {

        if (getDao().isConnected()) connect();

        if (cashier.get() == null)  cashier.set(DAO_CASHIER.findOpened());
        else return cashier.get();

        return cashier.get();
    }

    public Task<ObservableList<Sale>> fetchActiveSales () {
        return DAO_SALE.findActiviesTask(cashier.get());
    }

    public ObservableList<Sale> getActiveSales() {
        return DAO_SALE.getActives();
    }

    public Task<ObservableList<Sale>> fetchInactiveSales () {
        return DAO_SALE.findNoActiviesTask(cashier.get());
    }
}
