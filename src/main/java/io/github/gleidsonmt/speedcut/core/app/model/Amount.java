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

package io.github.gleidsonmt.speedcut.core.app.model;

import io.github.gleidsonmt.speedcut.controller.PaymentType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/08/2022
 */
public class Amount extends Entity {

    private final ObjectProperty<BigDecimal> value = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<PaymentType> paymentType = new SimpleObjectProperty<>(PaymentType.MONEY);
    private final ObjectProperty<Transaction> transaction = new SimpleObjectProperty<>();

    private ObjectProperty<ValueItem> item = new SimpleObjectProperty<>();

    public Amount() {
    }

    public Transaction getTransaction() {
        return transaction.get();
    }

    public ObjectProperty<Transaction> transactionProperty() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction.set(transaction);
    }

    public PaymentType getPaymentType() {
        return paymentType.get();
    }

    public ObjectProperty<PaymentType> paymentTypeProperty() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType.set(paymentType);
    }

    public BigDecimal getValue() {
        return value.get();
    }

    public ObjectProperty<BigDecimal> valueProperty() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value.set(value);
    }

    public ValueItem getItem() {
        return item.get();
    }

    public ObjectProperty<ValueItem> itemProperty() {
        return item;
    }

    public void setItem(ValueItem item) {
        this.item.set(item);
    }
}
