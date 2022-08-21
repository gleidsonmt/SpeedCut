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
    protected SaleItem createElement( ResultSet result) {
        SaleItem element = new SaleItem();

        try {
            element.setId( result.getInt("id"));

            element.setTradeItem(
                     result.getInt("SERVICE_ID") == 0 ?
                             DAO_PRODUCT.read(result.getInt("PRODUCT_ID")) :
                             DAO_SERVICE.read(result.getInt("SERVICE_ID"))
            );

            element.setDiscount(result.getBigDecimal("discount"));

            element.setQuantity(result.getInt("qtd"));

            element.setTotal(BigDecimal.valueOf(element.getQuantity() *
                    element.getTradeItem().getPrice().doubleValue()));


            element.setUnit(element.getTradeItem().getPrice());

            element.setHasDiscount(result.getBoolean("has_discount"));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return element;
    }


    public ObservableList<SaleItem> findForSale(Sale sale) {

        ObservableList<SaleItem> saleItems = FXCollections.observableArrayList();

        ResultSet result = executeSQL("select * from sale_items where sale_id like " + sale.getId() + ";");

        try {
            while (result.next()) {

                SaleItem saleItem = createElement(result);

                saleItem.setSale(sale);
                saleItems.add(saleItem);

            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return saleItems;
    }

    @Override
    public void put(SaleItem model) throws SQLQueryError {
        try {

            String itemType = "product";

            if (model.getTradeItem() instanceof Service) {
                itemType = "service";
            }

            table = "sale_item";

            PreparedStatement prepare = prepare(createStoreQuery(
                    model,
                    list(
                            "qtd", "SALE_ID", itemType + "_id",
                            "has_discount", "discount"
                    )
            ));

            prepare.setInt(1, model.getQuantity());
            prepare.setInt(2, model.getSale().getId());
            prepare.setInt(3, model.getTradeItem().getId());
            prepare.setBoolean(4, model.hasDiscount());
            prepare.setBigDecimal(5, model.getDiscount());

            prepare.execute();

            setId(model);
            add(model);
            // retrieve last id

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean delete(SaleItem model) {
        boolean result = false;

        try {
            prepare("delete from sale_items" +
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
                prepare("delete from sale_items" +
                        " where id like " + model.getId() + ";").execute();

//            getElements().remove(model);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
