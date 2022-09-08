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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public class DaoClient extends AbstractDao<Client> {

    private final DaoCard   daoCard = new DaoCard();
    private final DaoPix    daoPix  = new DaoPix();
    private final DaoTerm    daoTerm  = new DaoTerm();

    @Override
    protected Client createElement(ResultSet result) {

        Client element = new Client();

        try {

            element.setId( result.getInt("id"));
            element.setName(result.getString("name"));
            element.setLastName(result.getString("last_name"));

            element.setPayWithCard(result.getBoolean("card"));
            element.setPayWithPix(result.getBoolean("pix"));
            element.setPayWithTerm(result.getBoolean("term"));

            element.getPaymentMethods().addAll(daoCard.readCardsForClient(element));
            element.getPaymentMethods().addAll(daoPix.readPixesForClient(element));

            element.getPaymentMethods().add(daoTerm.read(result.getInt("term_id")));

            int _score = result.getInt("score");

            if (_score < 1) {
                element.setScore(Score.SPECIAL);
            } else if (_score <= 9) { // 9 8 7.. 1
                element.setScore(Score.NOVICE);
            } else if (_score <= 49) { // 49 48
                element.setScore(Score.USUALLY);
            } else if (_score < 99) {
                element.setScore(Score.VETERAN);
            } else if (_score < 299) {
                element.setScore(Score.PREMIUM);
            } else {
                element.setScore(Score.SUPER);
            }

            String imgPath = result.getString("img_path");

            if (result.getString("sex").equals("F")) {
                element.setSex(Sex.FEMALE);
            } else element.setSex(Sex.MALE);


            element.setAvatar(new Avatar(result.getString("img_path")));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return element;
    }

    @Override
    public void put(Client model) throws SQLQueryError {

        try {
            List<String> cols = list("name");

            PreparedStatement prepare = prepare(createStoreQuery(model, cols));

            prepare.setString(1, model.getName());

            prepare.execute();

            System.out.println(model.getId());
            setIdAndAdd(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
