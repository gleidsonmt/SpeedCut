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

package io.github.gleidsonmt.speedcut.controller.payment;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/06/2022
 */
public class CardPayment implements IPaymentMethod {

    private final ObjectProperty<BigDecimal> value = new SimpleObjectProperty<>();

    public CardPayment(BigDecimal value) {
        this.value.set(value);
    }

    @Override
    public ObjectProperty<BigDecimal> valueProperty() {
        return value;
    }

    @Override
    public BigDecimal getValue() {
        return value.get();
    }

    @Override
    public void setValue(BigDecimal value) {
        this.value.set(value);
    }
}
