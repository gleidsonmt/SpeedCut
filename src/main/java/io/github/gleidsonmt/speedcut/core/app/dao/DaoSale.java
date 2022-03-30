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
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import javafx.collections.FXCollections;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/02/2022
 */
public final class DaoSale extends AbstractDao<Sale> {

    private final DaoProfessional   DAO_PROFESSIONAL    = new DaoProfessional();
    private final DaoClient         DAO_CLIENT          = new DaoClient();
    private final DaoSaleItem       DAO_SALE_ITEM       = new DaoSaleItem();

    @Override
    protected Sale createElement(long id, ResultSet result) {
        Sale element = new Sale();
        try {
            element.setId( (int) id);
            element.setDiscount(result.getBigDecimal("DISCOUNT"));
            element.setProfessional(DAO_PROFESSIONAL.find(result.getInt("PROFESSIONAL_ID")));
            element.setClient(DAO_CLIENT.find(result.getInt("CLIENT_ID")));
            element.setSaleItems(DAO_SALE_ITEM.findForSale(element));

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

            result = prepare("delete from " +
                    table +
                    " where id like '" + model.getId() + "';").execute();
//
            getElements().remove(model);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean update(Sale model) throws SQLQueryError {
        PreparedStatement prepare = prepare("update sale set " +
                "discount = ?, professional_id = ?, client_id = ? where id = "
                + model.getId() + ";"
        );

        try {
            prepare.setBigDecimal(1, model.getDiscount());
            prepare.setInt(2, model.getProfessional().getId());
            prepare.setInt(3, model.getClient().getId());
            return prepare.execute();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    @Override
    public void store(Sale model) throws SQLQueryError {
        try {

            if (getElements().contains(model)) return;

            String sql = "insert into " + table + "(discount, client_id, professional_id) "
                    + "values(?, ?, ?);";

            if (model.getId() != 0) {
                sql = "insert into " + table + "(discount, client_id, professional_id, id) "
                        + "values(?, ?, ?, ?);";

            }

            PreparedStatement prepare =
                    prepare(sql);

            prepare.setBigDecimal(1, model.getDiscount());
            prepare.setInt(2, model.getClient().getId());
            prepare.setInt(3, model.getProfessional().getId());

            if (model.getId() != 0) {
                prepare.setInt(4, model.getId());
            }

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
                    DAO_SALE_ITEM.store(saleItem);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Sale createDefaultSale() {
        Sale sale = new Sale();

        try {
            sale.setClient(DAO_CLIENT.find(1));
            sale.setProfessional(DAO_PROFESSIONAL.find(1));
            sale.setDiscount(BigDecimal.ZERO);
            sale.setSaleItems(FXCollections.observableArrayList());
            store(sale);
        } catch (SQLQueryError e) {
            e.printStackTrace();
        }

        return sale;
    }
}