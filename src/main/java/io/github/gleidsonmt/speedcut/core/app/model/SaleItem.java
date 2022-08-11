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

import io.github.gleidsonmt.speedcut.core.app.dao.RepoSaleItemImpl;
import io.github.gleidsonmt.speedcut.core.app.dao.Repositories;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/02/2022
 */
@SuppressWarnings("unused")
public final class SaleItem extends Entity {

    private final BooleanProperty discount    = new SimpleBooleanProperty();

    private final IntegerProperty               quantity    = new SimpleIntegerProperty();
    private final ObjectProperty<BigDecimal>    total       = new SimpleObjectProperty<>();
    private final ObjectProperty<Sale>          sale        = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal>    unit      =  new SimpleObjectProperty<>();
    private final ObjectProperty<TradeItem>     tradeItem   = new SimpleObjectProperty<>();

    public SaleItem() {

        quantity.addListener((observable, oldValue, newValue) -> {
            calcTotal();
        });

        discount.addListener((observable, oldValue, newValue) -> {
            calcTotal();

            if (newValue) {

                BigDecimal discount = getItem().getPrice().multiply(getItem().getDiscount()); // fixed discount

                unit.set(
                    getItem().getPrice().subtract(discount)
                );

            } else {
                unit.set(getItem().getPrice());
            }

        });

    }



    private BigDecimal getDiscount() {
        return isDiscount() ? getItem().getPrice().multiply(getItem().getDiscount()) : BigDecimal.ZERO;
    }

    private void calcTotal() {
//                ( (uni - desconto) * quant) = total
        total.set(
                getItem().getPrice()
                        .subtract(getDiscount())
                        .multiply(BigDecimal.valueOf(getQuantity())
                        )
        );

    }

    public int getQuantity() {
        return quantity.get();
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }

    public BigDecimal getTotal() {
        return total.get();
    }

    public ObjectProperty<BigDecimal> totalProperty() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total.set(total);
    }

    public Sale getSale() {
        return sale.get();
    }

    public ObjectProperty<Sale> saleProperty() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale.set(sale);
    }

    public TradeItem getItem() {
        return tradeItem.get();
    }

    public ObjectProperty<TradeItem> itemProperty() {
        return tradeItem;
    }

    public void setTradeItem(TradeItem item) {
        this.tradeItem.set(item);
    }

    public boolean isDiscount() {
        return discount.get();
    }

    public int hasDiscount() {
        return getUnit().compareTo(getItem().getPrice());
    }

    public BooleanProperty discountProperty() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount.set(discount);
    }

    public BigDecimal getUnit() {
        return unit.get();
    }

    public ObjectProperty<BigDecimal> unitProperty() {
        return unit;
    }

    public void setUnit(BigDecimal unit) {
        this.unit.set(unit);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SaleItem{");
        sb.append("quantity=").append(quantity);
        sb.append(", discount=").append(discount);
        sb.append(", total=").append(total);
        sb.append(", sale=\n").append(sale);
        sb.append(", tradeItem=").append(tradeItem);
        sb.append('}');
        return sb.toString();
    }
}