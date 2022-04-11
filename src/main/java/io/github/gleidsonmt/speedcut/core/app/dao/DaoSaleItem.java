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

import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.core.app.exceptions.SQLQueryError;
import io.github.gleidsonmt.speedcut.core.app.model.Product;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import io.github.gleidsonmt.speedcut.core.app.model.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public class DaoSaleItem extends AbstractDao<SaleItem> {

    private final DaoService DAO_SERVICE = new DaoService();
    private final DaoProduct DAO_PRODUCT = new DaoProduct();

    @Override
    protected SaleItem createElement(long id, ResultSet result) {
        SaleItem element = new SaleItem();
        try {
            element.setId( (int) id);

            element.setItem(
                     result.getInt("SERVICE_ID") == 0 ?
                             DAO_PRODUCT.find(result.getInt("PRODUCT_ID")) :
                             DAO_SERVICE.find(result.getInt("SERVICE_ID"))
            );

            element.setQuantity(result.getInt("QTD"));

            element.setTotal(BigDecimal.valueOf(element.getQuantity() *
                    element.getItem().getPrice().doubleValue()));

            element.setDiscount(
                    result.getBigDecimal("discount") == null ? BigDecimal.ZERO :
                            result.getBigDecimal("discount")
            );


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return element;
    }


    public ObservableList<SaleItem> findForSale(Sale sale) {

        ObservableList<SaleItem> saleItems = FXCollections.observableArrayList();

        executeSQL("select * from sale_item where sale_id like " + sale.getId() + ";");
        ResultSet result = result();

        try {
            while (result.next()) {

                SaleItem saleItem = createElement(result.getInt("id"), result);
                saleItem.setSale(sale);
                saleItems.add(saleItem);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return saleItems;
    }

    public boolean update(SaleItem saleItem) {
        System.out.println("saleItem++++ = " + saleItem.getId());
        try {
            PreparedStatement prepare =
                    prepare("update " +
                            "sale_item set QTD = ? where id like "
                            + saleItem.getId() + ";");

            prepare.setInt(1, saleItem.getQuantity());


            return prepare.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void store(SaleItem model) throws SQLQueryError {
        try {

            String itemType = "PRODUCT";

            if (model.getItem() instanceof Service) {
                itemType = "SERVICE";
            }

            PreparedStatement prepare = prepare("insert into sale_item" +
                    "(QTD, SALE_ID, " + itemType + "_ID, TOTAL) values(?, ?, ?, ?)");

            System.out.println("saleid = " + model.getId());

            prepare.setInt(1, model.getQuantity());
            prepare.setInt(2, model.getSale().getId());
            prepare.setInt(3, model.getItem().getId());
            prepare.setBigDecimal(4, model.getTotal());

            prepare.execute();

            // retrieve last id
            executeSQL("select last_insert_id()");
            result().first();

            model.setId(result().getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean delete(SaleItem model) {
        boolean result = false;

        try {
            prepare("delete from sale_item" +
                    " where id like " + model.getId() + ";").execute();

            model.getSale().getSaleItems().remove(model);
//            getElements().remove(model);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public void delete(ObservableList<SaleItem> items) {
        for (SaleItem model : items) {

            try {
                prepare("delete from sale_item" +
                        " where id like " + model.getId() + ";").execute();

//            getElements().remove(model);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
