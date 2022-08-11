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

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/04/2022
 */
public final class Cashier extends Entity {

    private final BooleanProperty open = new SimpleBooleanProperty();
    private final ObjectProperty<BigDecimal> amount = new SimpleObjectProperty<>();

    private final ListProperty<Sale> activeSales = new SimpleListProperty<>(FXCollections.observableArrayList());
    private final ListProperty<Sale> inactiveSales = new SimpleListProperty<>(FXCollections.observableArrayList());

    private final ListProperty<Transaction> transactions = new SimpleListProperty<>(FXCollections.observableArrayList());

    public BigDecimal getAmount() {
        return amount.get();
    }

    public ObjectProperty<BigDecimal> amountProperty() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount.set(amount);
    }

    public boolean isOpen() {
        return open.get();
    }

    public BooleanProperty openProperty() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open.set(open);
    }

    public ObservableList<Sale> getActiveSales() {
        return activeSales.get();
    }

    public ListProperty<Sale> activeSalesProperty() {
        return activeSales;
    }

    public void setActiveSales(ObservableList<Sale> activeSales) {
        this.activeSales.set(activeSales);
    }

    public ObservableList<Sale> getInactiveSales() {
        return inactiveSales.get();
    }

    public ListProperty<Sale> inactiveSalesProperty() {
        return inactiveSales;
    }

    public void setInactiveSales(ObservableList<Sale> inactiveSales) {
        this.inactiveSales.set(inactiveSales);
    }

    public ObservableList<Transaction> getTransactions() {
        return transactions.get();
    }

    public ListProperty<Transaction> transactionsProperty() {
        return transactions;
    }

    public void setTransactions(ObservableList<Transaction> transactions) {
        this.transactions.set(transactions);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Cashier{");
        sb.append("open=").append(open);
        sb.append(", amount=").append(amount);
        sb.append(", activeSales=").append(activeSales);
        sb.append(", inactiveSales=").append(inactiveSales);
        sb.append('}');
        return sb.toString();
    }
}
