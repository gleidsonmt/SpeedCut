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

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public final class Client extends Person {

    private final ListProperty<PaymentMethod> paymentMethods = new SimpleListProperty<>(FXCollections.observableArrayList());

    private final BooleanProperty payWithCard   = new SimpleBooleanProperty();
    private final BooleanProperty payWithPix    = new SimpleBooleanProperty();
    private final BooleanProperty payWithMoney  = new SimpleBooleanProperty();
    private final BooleanProperty payWithTerm   = new SimpleBooleanProperty();

    public boolean isPayWithCard() {
        return payWithCard.get();
    }

    public BooleanProperty payWithCardProperty() {
        return payWithCard;
    }

    public void setPayWithCard(boolean payWithCard) {
        this.payWithCard.set(payWithCard);
    }

    public ObservableList<PaymentMethod> getPaymentMethods() {
        return paymentMethods.get();
    }

    public ListProperty<PaymentMethod> paymentMethodsProperty() {
        return paymentMethods;
    }

    public void setPaymentMethods(ObservableList<PaymentMethod> paymentMethods) {
        this.paymentMethods.set(paymentMethods);
    }

    public boolean isPayWithPix() {
        return payWithPix.get();
    }

    public BooleanProperty payWithPixProperty() {
        return payWithPix;
    }

    public void setPayWithPix(boolean payWithPix) {
        this.payWithPix.set(payWithPix);
    }

    public boolean isPayWithMoney() {
        return payWithMoney.get();
    }

    public BooleanProperty payWithMoneyProperty() {
        return payWithMoney;
    }

    public void setPayWithMoney(boolean payWithMoney) {
        this.payWithMoney.set(payWithMoney);
    }

    public boolean isPayWithTerm() {
        return payWithTerm.get();
    }

    public BooleanProperty payWithTermProperty() {
        return payWithTerm;
    }

    public void setPayWithTerm(boolean payWithTerm) {
        this.payWithTerm.set(payWithTerm);
    }
}
