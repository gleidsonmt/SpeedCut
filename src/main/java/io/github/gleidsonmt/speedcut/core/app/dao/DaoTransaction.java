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
import io.github.gleidsonmt.speedcut.core.app.model.Category;
import io.github.gleidsonmt.speedcut.core.app.model.Type;
import io.github.gleidsonmt.speedcut.core.app.model.Transaction;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  19/05/2022
 */
public class DaoTransaction extends AbstractDao<Transaction> {

    // Fixed
    private final DaoAmount daoAmount = new DaoAmount();
    private final DaoSale daoSale = new DaoSale();

    //
    private BooleanProperty hasTransactions = new SimpleBooleanProperty();

    public DaoTransaction() {

    }

    public Task<ObservableList<Transaction>> populateFor(Cashier cashier) {

        return new Task<>() {

            @Override
            protected void failed() {
                System.err.println("Error on finding items for dao class " + getClass());
                getException().printStackTrace();
            }

            @Override
            protected synchronized ObservableList<Transaction> call() {
                connect();

                getElements().clear(); // limpei os elements
                executeSQL("select * from " + table + "s where cashier_id like " + cashier.getId() + ";"); // executei a consulta
                ResultSet result = result(); // peguei o resultado ok!!

                try {
                    while (result.next()) {
                        int id = result.getInt("id");

                        add(createElement(result));

                        if (!contains(cashier.getTransactions(), id)) {

                            cashier.getTransactions().add(createElement(result));

                        }

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                disconnect();
                return getElements();
//                return elements;
            }
        };
    }

    @Override
    protected Transaction createElement( ResultSet result ) {

        Transaction element = new Transaction();

        try {

            element.setId( result.getInt("id"));
            element.setSale(daoSale.read(result.getInt("sale_id")));

            element.setAmounts(daoAmount.setAmountsFor(element));

            element.setType(Type.valueOf(result.getString("type")));
            element.setCategory(Category.valueOf(result.getString("category")));

            element.setTotal(result.getBigDecimal("total"));
            element.setSubtotal(result.getBigDecimal("subtotal"));
            element.setDiscount(result.getBigDecimal("discount"));
            element.setChange(result.getBigDecimal("cash_change"));


        } catch (SQLException  throwables) {
            throwables.printStackTrace();
        }

        return element;
    }

    @Override
    public void put(Transaction model) throws SQLQueryError {

        List<String> values =
                list(
                "sale_id", "cashier_id", "category", "type",
                        "total", "discount", "cash_change", "subtotal"
                );

        PreparedStatement prepare = prepare(createStoreQuery(model, values));

        try {

            prepare.setInt(1, model.getSale().getId());
            prepare.setInt(2, model.getCashier().getId());
            prepare.setString(3, String.valueOf(model.getCategory()));
            prepare.setString(4, String.valueOf(model.getType()));

            prepare.setBigDecimal(5, model.getTotal());
            prepare.setBigDecimal(6, model.getDiscount());
            prepare.setBigDecimal(7, model.getChange());
            prepare.setBigDecimal(8, model.getSubtotal());

            prepare.execute();

            setId(model);
            add(model);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }
}
