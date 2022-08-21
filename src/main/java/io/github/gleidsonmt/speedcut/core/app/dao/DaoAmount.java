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
import io.github.gleidsonmt.speedcut.core.app.exceptions.SQLQueryError;
import io.github.gleidsonmt.speedcut.core.app.model.Amount;
import io.github.gleidsonmt.speedcut.core.app.model.Card;
import io.github.gleidsonmt.speedcut.core.app.model.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/08/2022
 */
public class DaoAmount extends AbstractDao<Amount> {

    private final DaoCard daoCard = new DaoCard();
    private final DaoPix  daoPix  = new DaoPix();
    private final DaoTerm  daoTerm  = new DaoTerm();

    @Override
    protected Amount createElement(ResultSet result)  {
        Amount element = new Amount();

        try {

            element.setId(result.getInt("id"));

            PaymentType paymentType = PaymentType.valueOf(result.getString("type"));

            paymentType.setValue(result.getBigDecimal("value"));

            element.setPaymentType(paymentType);

            element.setValue(paymentType.getValue());


            switch (paymentType) {
                case CARD -> element.setItem(daoCard.read(result.getInt("card_id")));
                case PIX -> element.setItem(daoPix.read(result.getInt("pix_id")));
                case TERM -> element.setItem(daoTerm.read(result.getInt("term_id")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return element;
    }

    @Override
    public void put(Amount model) throws SQLQueryError {
        try {

            List<String>  values = list("value", "type", "transaction_id");


            if (model.getPaymentType() == PaymentType.CARD) {
                values.add("card_id");
            } else if (model.getPaymentType() == PaymentType.TERM) {
                values.add("term_id");
            } else if (model.getPaymentType() == PaymentType.PIX) {
                values.add("pix_id");
            }

            PreparedStatement prepare = prepare(createStoreQuery(model, values));

            prepare.setBigDecimal(1, model.getValue());
            prepare.setString(2, model.getPaymentType().toString());
            prepare.setInt(3, model.getTransaction().getId());

            if (model.getItem() != null)
                prepare.setInt(4, model.getItem().getId());

            prepare.execute();

            if (create(model)) { // cria e seta o id final
                setId(model);
            }

            add(model);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public ObservableList<Amount> setAmountsFor(Transaction transaction) {

        ObservableList<Amount> amounts = FXCollections.observableArrayList();

        try {

            ResultSet resultSet = executeSQL("select * from amounts where transaction_id LIKE " + transaction.getId() + ";");

            while (resultSet.next()) {

                Amount ele = createElement(resultSet);
                add(ele);
                amounts.add(ele);

                transaction.setTotal(transaction.getTotal().add(ele.getValue()));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return amounts;
    }
}
