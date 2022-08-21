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

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  17/05/2022
 */
@SuppressWarnings("unused")
public class Transaction extends Entity {

    // Fixed
    private final ObjectProperty<BigDecimal> current     = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private final ObjectProperty<Type>      type        = new SimpleObjectProperty<>();
    private final ObjectProperty<Category>  category    = new SimpleObjectProperty<>();
    private final ObjectProperty<Sale>      sale        = new SimpleObjectProperty<>();
    private final ObjectProperty<Cashier>   cashier     = new SimpleObjectProperty<>();

    private final ListProperty<Amount> amounts = new SimpleListProperty<>(FXCollections.observableArrayList());


    // Test
    private final ObjectProperty<BigDecimal> subtotal   = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> discount   = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> total      = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> change     = new SimpleObjectProperty<>(BigDecimal.ZERO);


    public BigDecimal getCurrent() {
        return current.get();
    }

    public ObjectProperty<BigDecimal> currentProperty() {
        return current;
    }

    public void setCurrent(BigDecimal current) {
        this.current.set(current);
    }

    public Type getType() {
        return type.get();
    }

    public ObjectProperty<Type> typeProperty() {
        return type;
    }

    public void setType(Type type) {
        this.type.set(type);
    }

    public Category getCategory() {
        return category.get();
    }

    public ObjectProperty<Category> categoryProperty() {
        return category;
    }

    public void setCategory(Category category) {
        this.category.set(category);
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

    public Cashier getCashier() {
        return cashier.get();
    }

    public ObjectProperty<Cashier> cashierProperty() {
        return cashier;
    }

    public void setCashier(Cashier cashier) {
        this.cashier.set(cashier);
    }

    public ObservableList<Amount> getAmounts() {
        return amounts.get();
    }

    public ListProperty<Amount> amountsProperty() {
        return amounts;
    }

    public void setAmounts(ObservableList<Amount> amounts) {
        this.amounts.set(amounts);
    }

    public BigDecimal getSubtotal() {
        return subtotal.get();
    }

    public ObjectProperty<BigDecimal> subtotalProperty() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal.set(subtotal);
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

    public BigDecimal getTotal() {
        return total.get();
    }

    public ObjectProperty<BigDecimal> totalProperty() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total.set(total);
    }

    public BigDecimal getChange() {
        return change.get();
    }

    public ObjectProperty<BigDecimal> changeProperty() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change.set(change);
    }
}
 