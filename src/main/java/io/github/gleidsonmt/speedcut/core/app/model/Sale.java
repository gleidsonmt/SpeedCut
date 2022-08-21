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
import javafx.collections.ObservableList;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  25/02/2022
 */
public class Sale extends Entity {

    private final ObjectProperty<Professional>  professional    = new SimpleObjectProperty<>();
    private final ObjectProperty<Client>        client          = new SimpleObjectProperty<>();
    private final ObjectProperty<Cashier>       cashier         = new SimpleObjectProperty<>();

    private final BooleanProperty active = new SimpleBooleanProperty();

    private final ObjectProperty<Transaction>  Transaction    = new SimpleObjectProperty<>();

    private final ListProperty<SaleItem> saleItems = new SimpleListProperty<>();

    public Sale() {
    }

    public Professional getProfessional() {
        return professional.get();
    }

    public ObjectProperty<Professional> professionalProperty() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional.set(professional);
    }

    public Client getClient() {
        return client.get();
    }

    public ObjectProperty<Client> clientProperty() {
        return client;
    }

    public void setClient(Client client) {
        this.client.set(client);
    }

    public ObservableList<SaleItem> getSaleItems() {
        return saleItems.get();
    }

    public ListProperty<SaleItem> saleItemsProperty() {
        return saleItems;
    }

    public void setSaleItems(ObservableList<SaleItem> saleItems) {
        this.saleItems.set(saleItems);
    }

    public boolean isActive() {
        return active.get();
    }

    public BooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
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

    public Transaction getTransaction() {
        return Transaction.get();
    }

    public ObjectProperty<Transaction> transactionProperty() {
        return Transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.Transaction.set(transaction);
    }

    //    @Override
//    public String toString() {
//        return "Sale{" +
//                "id=" + getId() +
//                "professional=" + professional +
//                ", client=" + client +
//                ", saleItems=" + saleItems +
//                '}';
//    }
}
