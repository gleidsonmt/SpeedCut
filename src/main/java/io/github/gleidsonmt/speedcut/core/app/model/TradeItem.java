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

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  21/03/2022
 */
@SuppressWarnings("unused")
public class TradeItem extends Item {

    private final ObjectProperty<BigDecimal> discount   = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> price      = new SimpleObjectProperty<>();


    public TradeItem() {
    }

    public BigDecimal getDiscount() {
        return discount.get();
    }

    public ObjectProperty<BigDecimal> discountProperty() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount.set(discount);
    }

    public BigDecimal getPrice() {
        return price.get();
    }

    public ObjectProperty<BigDecimal> priceProperty() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price.set(price);
    }




    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TradeItem{");
        sb.append("discount=").append(discount);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
