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

import io.github.gleidsonmt.speedcut.controller.PaymentType;
import io.github.gleidsonmt.speedcut.core.app.model.*;
import javafx.collections.FXCollections;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/08/2022
 */
public final class DaoPix extends AbstractDao<Pix> {

    public DaoPix() {
        table = "pixe"; // problem with plural
    }

    @Override
    protected Pix createElement(ResultSet result) {
        Pix element = new Pix();

        try {

            element.setId(result.getInt("pixes.id"));
            element.setKey(result.getString("pixes.value"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return element;
    }


    public PaymentMethod readPixesForClient(Client client) {

        Pixes pixes = new Pixes(FXCollections.observableArrayList());

        try {

            connect();
            ResultSet resultSet =
                    executeSQL("select * from pixes_clients left join pixes on pixes_clients.pix_id = pixes.id where client_id like '" + client.getId() + "';");

            while (resultSet.next()) {
                Pix element = createElement(resultSet);
                pixes.getPixes().add(element);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pixes;
    }

}
