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
import io.github.gleidsonmt.speedcut.core.app.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/08/2022
 */
public final class DaoCard extends AbstractDao<Card> {

    DaoCard() {

    }

    protected Card createElement(ResultSet result) {

        Card element = new Card();

        try {

            element.setId(result.getInt("id"));
            element.setCvc(result.getInt("cvc"));
            element.setName(result.getString("name"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return element;
    }

    @Override
    public void put(Card model) throws SQLQueryError {
        try {

            List<String> values = Arrays.asList(
                "value", "cvc"
            );

            PreparedStatement prepare = prepare(createStoreQuery(model, values));

            prepare.setBigDecimal(1, model.getValue());
            prepare.setInt(2,model.getCvc());
            prepare.execute();

            if (create(model)) { // cria e seta o id final
                setId(model);
            }

            add(model);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public PaymentMethod readCardsForClient(Client client) {

        Cards cards = new Cards(FXCollections.observableArrayList());

        try {

            connect();
            ResultSet resultSet = executeSQL("select * from " + table + "s where client_id like '" + client.getId() + "';");

            while (resultSet.next()) {
                Card element = createElement(resultSet);
                cards.getCards().add(element);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cards;
    }
}
