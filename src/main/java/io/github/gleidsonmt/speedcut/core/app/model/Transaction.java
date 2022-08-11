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

import io.github.gleidsonmt.speedcut.controller.payment.IPaymentMethod;
import io.github.gleidsonmt.speedcut.controller.payment.IPaymentMethod2;
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

    private  ListProperty<Amount> amounts = new SimpleListProperty<>(FXCollections.observableArrayList());


    // Test
    private final ObjectProperty<BigDecimal> subtotal   = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> discount   = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> total      = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> change     = new SimpleObjectProperty<>(BigDecimal.ZERO);


    private final ObjectProperty<BigDecimal>        cash    = new SimpleObjectProperty<>(BigDecimal.ZERO);

    private final ObjectProperty<IPaymentMethod2>   card    = new SimpleObjectProperty<>();
    private final ObjectProperty<IPaymentMethod>    check   = new SimpleObjectProperty<>();
    private final ObjectProperty<IPaymentMethod>    term    = new SimpleObjectProperty<>();


    public Transaction() {

    }

    @Deprecated
    public void reset() {
        change.set(BigDecimal.ZERO);
        subtotal.set(BigDecimal.ZERO);
        discount.set(BigDecimal.ZERO);
        total.set(BigDecimal.ZERO);
    }

    public void addDiscount(BigDecimal newValue) {
        this.discount.set(getDiscount().add(newValue));
    }

    public void subtractDiscount(BigDecimal newValue) {
        this.discount.set(getDiscount().subtract(newValue));
    }

    public void addCurrent(BigDecimal newValue) {
        this.current.set(getCurrent().add(newValue));
    }

    public void subCurrent(BigDecimal newValue) {
        this.current.set(getCurrent().subtract(newValue));
    }

    // Adicionar sempre o valor bruto sem desconto ele vai calcular normal

    public void addTotal(BigDecimal newValue) {
        total.setValue(getTotal().add(newValue));
    }

    public void subTotal(BigDecimal newValue) {
        total.setValue(getTotal().subtract(newValue));
    }

    public void addSub(BigDecimal newValue) {
        subtotal.setValue(getSubtotal().add(newValue));

        _calcTotal();
    }

    public void subSub(BigDecimal newValue) { // yes, yes.. that's the name
        subtotal.setValue(getSubtotal().subtract(newValue));

        _calcTotal();
    }


    @Deprecated
    public void subtract(BigDecimal newValue) {
//        this.setSubtotal(getSubtotal().subtract(newValue));
//        _calcTotal();
    }

    public ObjectProperty<IPaymentMethod2> cardProperty() {
        return card;
    }

    public void setCard(IPaymentMethod2 card) {
        this.card.set(card);
    }

    public IPaymentMethod2 getCard() {
        return card.get();
    }


    private void _calcTotal() {
        total.set(subtotal.get().subtract(discount.get()));
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

    public BigDecimal getDiscount() {
        return discount.get();
    }

    public ObjectProperty<BigDecimal> discountProperty() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount.set(discount);
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

    public BigDecimal getCash() {
        return cash.get();
    }

    public ObjectProperty<BigDecimal> cashProperty() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash.set(cash);
    }

    public IPaymentMethod getCheck() {
        return check.get();
    }

    public ObjectProperty<IPaymentMethod> checkProperty() {
        return check;
    }

    public void setCheck(IPaymentMethod check) {
        this.check.set(check);
    }

    public IPaymentMethod getTerm() {
        return term.get();
    }

    public ObjectProperty<IPaymentMethod> termProperty() {
        return term;
    }

    public void setTerm(IPaymentMethod term) {
        this.term.set(term);
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

    public BigDecimal getSubtotal() {
        return subtotal.get();
    }

    public ObjectProperty<BigDecimal> subtotalProperty() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal.set(subtotal);
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

    public BigDecimal getCurrent() {
        return current.get();
    }

    public ObjectProperty<BigDecimal> currentProperty() {
        return current;
    }

    public void setCurrent(BigDecimal current) {
        this.current.set(current);
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Transaction{");
        sb.append("id=").append(getId());
        sb.append(", cashChange=").append(change);
        sb.append(", total=").append(total);
        sb.append(", discount=").append(discount);
        sb.append(", finalValue=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
 