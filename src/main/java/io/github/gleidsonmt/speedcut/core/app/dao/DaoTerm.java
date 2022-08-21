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

import io.github.gleidsonmt.speedcut.core.app.model.*;
import javafx.collections.FXCollections;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  10/08/2022
 */
public class DaoTerm extends AbstractDao<Term> {

    @Override
    protected Term createElement(ResultSet result) {
        Term element = new Term();

        try {

            element.setId(result.getInt("id"));
            element.setExpDate(result.getDate("exp_date").toLocalDate());

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return element;
    }

    public PaymentMethod readTermForClient(Client client) {

        Terms terms = new Terms(FXCollections.observableArrayList());

        try {

            connect();
            ResultSet resultSet =
                    executeSQL("select * from terms where client_id like '" + client.getId() + "';");

            while (resultSet.next()) {
                Term element = createElement(resultSet);
                terms.getTerms().add(element);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return terms;
    }
}
