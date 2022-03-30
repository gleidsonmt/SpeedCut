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

import io.github.gleidsonmt.speedcut.core.app.model.Product;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/02/2022
 */
public class DaoProduct extends AbstractDao<Product> {

    private final String table = getClass().getSimpleName().replaceAll("Dao", "");

    @Override
    protected Product createElement(long id, ResultSet result) {
        Product element = new Product();

        try {
            element.setId( (int) id);
            element.setName(result.getString("NAME"));
            element.setPrice(result.getBigDecimal("PRICE"));

            BigDecimal discount = result.getBigDecimal("DISCOUNT");
            element.setDiscount( discount == null ? BigDecimal.ZERO : discount);

            element.setAvatar(result.getString("IMG_PATH"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return element;
    }

    @Override
    public void store(Product model)  {
        PreparedStatement prepare = prepare("insert into " + table + "(name, price) values(?, ?);");
        try {
            prepare.setString(1, model.getName());
            prepare.setBigDecimal(2, model.getPrice());
            prepare.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
