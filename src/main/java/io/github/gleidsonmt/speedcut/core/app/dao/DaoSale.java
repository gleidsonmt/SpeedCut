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
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/02/2022
 */
public final class DaoSale extends AbstractDao<Sale> {

    private final DaoProfessional DAO_PROFESSIONAL = new DaoProfessional();
    private final DaoClient DAO_CLIENT = new DaoClient();
    private final DaoSaleItem DAO_SALE_ITEM = new DaoSaleItem();

    DaoSale() {

    }

    private final ListProperty<Sale> actives =
            new SimpleListProperty<>(FXCollections.observableArrayList()); // monostate

    public ObservableList<Sale> getActives() {
        return actives;
    }

    public ObservableList<Sale> findForCashier(Cashier cashier) {
        return findInServerForCashier(cashier.getId());
    }

    public ObservableList<Sale> findActivesFrom(Cashier cashier) {

        ObservableList<Sale> all = findInServerForCashier(cashier.getId());

//        all.forEach(c -> c.setCashier(cashier));
        ObservableList<Sale> sales = FXCollections.observableArrayList(all
                .stream()
                .filter(Sale::isActive)
                .toList());

        sales.forEach(e -> e.setCashier(cashier));

        return sales;
    }

    public Task<ObservableList<Sale>> findNoActiviesTask(Cashier cashier) {
        return new Task<>() {

            @Override
            protected ObservableList<Sale> call() throws Exception {
                executeSQL("select * from sale where active = 0 and cashier_id like '" + cashier.getId() + "';");

                ResultSet result = result();

                Thread.sleep(300);

                try {
                    while (result.next()) {


                        int id = result.getInt("ID");

                        Sale element = createElement(result);

                        if (getElements().stream().noneMatch(f -> f.getId() == id)) {
                            getElements().add(element);
                        }

                        if (!contains(cashier.getInactiveSales(), id)) {
                            cashier.getInactiveSales().add(element);
                            element.setCashier(cashier);
                        }

                        element.getProperties().put("init", false);

                        Thread.sleep(20);

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                return getElements();
            }
        };
    }

    public Task<ObservableList<Sale>> findActiviesTask(Cashier cashier) {

        return new Task<>() {

            @Override
            protected void failed() {
                System.err.println("Error on finding items for dao class " + getClass());
                getException().printStackTrace();
            }

            @Override
            protected ObservableList<Sale> call() throws Exception {

                ResultSet result = executeSQL("select * from sales where active = 1 and cashier_id like '" + cashier.getId() + "';");

                Thread.sleep(300);

                try {
                    while (result.next()) {


                        int id = result.getInt("ID");

                        Sale element = createElement(result);

                        add(element);

                        if (!contains(cashier.getActiveSales(), id)) {
                            cashier.getActiveSales().add(element);
                            element.setCashier(cashier);
                        }

                        element.getProperties().put("init", false);

                        Thread.sleep(20);

                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                return getElements();
            }
        };

    }

    private ObservableList<Sale> findInServerForCashier(long id) {

        ResultSet result = executeSQL("select * from sale where cashier_id like '" + id + "';");

        Sale element;


        ObservableList<Sale> list = FXCollections.observableArrayList();

        try {
            while (result.next()) {

                element = createElement(result);

                if (!contains(element.getId()))
                    getElements().add(element);

                if (!contains(list, element.getId())) {
                    list.add(element);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected Sale createElement(ResultSet result) {

        Sale element = new Sale();

        try {
            element.setId(result.getInt("id"));
            element.setProfessional(DAO_PROFESSIONAL.read(result.getInt("professional_id")));

            element.setClient( DAO_CLIENT.read(result.getInt("client_id")));

            element.setSaleItems(DAO_SALE_ITEM.findForSale(element));

            element.setActive(result.getBoolean("active"));

//            element.setCashier(DAO_CASHIER.read(result.getInt("CASHIER_ID")));
//
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return element;
    }

    @Override
    public boolean delete(Sale model) {
        boolean result = false;
        try {

            DAO_SALE_ITEM.delete(model.getSaleItems());

            result = prepare("delete from " + table +
                    " where id like '" + model.getId() + "';").execute();
//
            getElements().remove(model);
            actives.remove(model);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

//    @Override
//    public boolean update(Sale model) throws SQLQueryError {
//        PreparedStatement prepare = prepare("update sale set " +
//                "discount = ?, professional_id = ?, client_id = ? where id = "
//                + model.getId() + ";"
//        );
//
//        try {
//            prepare.setBigDecimal(1, model.getDiscount());
//            prepare.setInt(2, model.getProfessional().getId());
//            prepare.setInt(3, model.getClient().getId());
//            return prepare.execute();
//        } catch (SQLException exception) {
//            exception.printStackTrace();
//        }
//        return false;
//    }

    @Override
    public void put(Sale model) throws SQLQueryError {
        try {

            PreparedStatement prepare = prepare(createStoreQuery(model,
                    Arrays.asList(
                            "CLIENT_ID", "PROFESSIONAL_ID",
                            "CASHIER_ID", "ACTIVE"
                    )
            ));

            prepare.setInt(1, model.getClient().getId());
            prepare.setInt(2, model.getProfessional().getId());
            prepare.setInt(3, model.getCashier().getId());
            prepare.setBoolean(4, model.isActive());

            prepare.execute();

            if (model.getId() == 0) {
                executeSQL("select last_insert_id()");
                if (result().first()) getElements().add(model);
                model.setId(result().getInt(1));
            } else {
                getElements().add(model);
            }

            if (model.getSaleItems() != null) {
                for (SaleItem saleItem : model.getSaleItems()) {
                    DAO_SALE_ITEM.put(saleItem);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}