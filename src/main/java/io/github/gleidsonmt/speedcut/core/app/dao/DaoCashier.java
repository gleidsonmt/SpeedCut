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
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import io.github.gleidsonmt.speedcut.core.app.model.Service;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/04/2022
 */
public class DaoCashier extends AbstractDao<Cashier> {

    private static final ListProperty<Cashier> elements =
            new SimpleListProperty<>(FXCollections.observableArrayList());

    private final String table = getClass().getSimpleName().replaceAll("Dao", "");

    public Cashier findOpened() {
        Optional<Cashier> result = elements.stream()
                .filter(Cashier::isOpen)
                .findAny();
        return result.orElseGet(this::findOpenedInServer);
    }

    private Cashier findOpenedInServer() {
        connect();
        executeSQL("select * from " + table + " where open = 1;");
        ResultSet result = result();
        Cashier element;
        try {
            if (result.first()) {
                element = createElement(result.getInt("ID"), result);
                elements.add(element);
                return element;
            } else return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Cashier createElement(long id, ResultSet result) {
        Cashier element = new Cashier();

        try {
            element.setId( (int) id);
            element.setOpen(result.getBoolean("OPEN"));
            BigDecimal amount = result.getBigDecimal("AMOUNT");
            element.setAmount( amount == null ? BigDecimal.ZERO : amount);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return element;
    }

    @Override
    public void store(Cashier model) throws SQLQueryError {
        try {

            PreparedStatement prepare = prepare("insert into cashier" +
                    "(OPEN, AMOUNT) values(?, ?)");

            prepare.setBoolean(1, model.isOpen());
            prepare.setBigDecimal(2, model.getAmount());

            prepare.execute();

            // retrieve last id
            executeSQL("select last_insert_id()");
            result().first();

            model.setId(result().getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
