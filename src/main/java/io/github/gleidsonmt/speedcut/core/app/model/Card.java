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
import io.github.gleidsonmt.speedcut.controller.payment.IPaymentMethod2;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  01/08/2022
 */
public class Card extends ValueItem {

    private IntegerProperty cvc = new SimpleIntegerProperty();

    public Card() {

    }

    public Card(BigDecimal value) {
        super.setValue(value);
    }

    public int getCvc() {
        return cvc.get();
    }

    public IntegerProperty cvcProperty() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc.set(cvc);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Card{");
        sb.append("id=").append(super.getId()).append(", ");
        sb.append("value=").append(super.getValue()).append(", ");
        sb.append("cvc=").append(cvc);
        sb.append('}');
        return sb.toString();
    }
}
