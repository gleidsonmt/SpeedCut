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
 * Create on  25/02/2022
 */
public class Sale extends Entity {

    private final ObjectProperty<BigDecimal>    discount        = new SimpleObjectProperty<>();
    private final ObjectProperty<Professional>  professional    = new SimpleObjectProperty<>();
    private final ObjectProperty<Client>        client          = new SimpleObjectProperty<>();

    private final ListProperty<SaleItem> saleItems = new SimpleListProperty<>();

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

    public BigDecimal getDiscount() {
        return discount.get();
    }

    public ObjectProperty<BigDecimal> discountProperty() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount.set(discount);
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + getId() +
                "professional=" + professional +
                ", client=" + client +
                ", saleItems=" + saleItems +
                '}';
    }
}
