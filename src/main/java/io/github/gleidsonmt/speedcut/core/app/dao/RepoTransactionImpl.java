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
import io.github.gleidsonmt.speedcut.core.app.model.Transaction;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  19/05/2022
 */
public class RepoTransactionImpl implements Repository<Transaction> {

    // fixed
    private final DaoTransaction DAO_TRANSACTION = new DaoTransaction();
    private final BooleanProperty has = new SimpleBooleanProperty();


    public RepoTransactionImpl() {
        has.bind(getDao().elementsProperty().sizeProperty().greaterThan(0));
    }

    @Override
    public AbstractDao<Transaction> getDao() {
        return DAO_TRANSACTION;
    }

    public Task<ObservableList<Transaction>> transactionsFor(Cashier cashier) {
        return DAO_TRANSACTION.populateFor(cashier);
    }

    public BooleanProperty hasTransactionsProperty() {
       return has;
    }

}