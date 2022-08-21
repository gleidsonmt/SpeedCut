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

    private final BooleanProperty hasDiscount    = new SimpleBooleanProperty();

    private final IntegerProperty               quantity    = new SimpleIntegerProperty();
    private final ObjectProperty<BigDecimal>    total       = new SimpleObjectProperty<>();
    private final ObjectProperty<Sale>          sale        = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal>    unit        =  new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal>    discount    =  new SimpleObjectProperty<>();
    private final ObjectProperty<TradeItem>     tradeItem   = new SimpleObjectProperty<>();

    public SaleItem() {

        quantity.addListener((observable, oldValue, newValue) -> { // calcula a quantidade de desconto total este sale item

            discount.set(
                    // desconto em dinheiro vezes a quantidade da a ele um desconto total
                    // multiplica o preco pelo desconto 10 * 0.02 = 0.20 e depois umtilica pela quantidade 5 * 0.20 = 1.00
                    getTradeItem().getPrice().multiply(
                            getTradeItem().getDiscount()
                    ).multiply(BigDecimal.valueOf(newValue.intValue()))
            );

            calcTotal(hasDiscount()); // calcula o total com o desconto total do item
        });

        hasDiscount.addListener((observable, oldValue, newValue) -> {

            calcTotal(newValue); // calcula o total com o desconto
            unit.set(
                    newValue ? // se tem desconto
                    getTradeItem().getPrice().subtract( // subtrai o preco pelo desconto
                            getTradeItem().getPrice().multiply(getTradeItem().getDiscount()) // calcula o desconto total em dinheiro
                    ) :
                    getTradeItem().getPrice()); // se nao tem desconto retorna o valor fixo de preco

        });

//        setDiscount();
    }



    private void calcTotal(boolean hasDiscount) {
//                ( (uni - desconto) * quant) = total

        if (hasDiscount) {

            total.set(
                    getTradeItem().getPrice()
                            .multiply(BigDecimal.valueOf(getQuantity()))
                            .subtract(getDiscount())
            );


        } else {
            total.set(
                    getTradeItem().getPrice()
                            .multiply(BigDecimal.valueOf(getQuantity()))
            );
        }

    }

    public boolean hasDiscount() {
        return hasDiscount.get();
    }

    public BooleanProperty hasDiscountProperty() {
        return hasDiscount;
    }

    public void setHasDiscount(boolean hasDiscount) {
        this.hasDiscount.set(hasDiscount);
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

    public BigDecimal getUnit() {
        return unit.get();
    }

    public ObjectProperty<BigDecimal> unitProperty() {
        return unit;
    }

    public void setUnit(BigDecimal unit) {
        this.unit.set(unit);
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

    public TradeItem getTradeItem() {
        return tradeItem.get();
    }

    public ObjectProperty<TradeItem> tradeItemProperty() {
        return tradeItem;
    }

    public void setTradeItem(TradeItem tradeItem) {
        this.tradeItem.set(tradeItem);
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