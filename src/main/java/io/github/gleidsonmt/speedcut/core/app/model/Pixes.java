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
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  11/08/2022
 */
public class Pixes extends PaymentMethod {

    private final ListProperty<Pix> pixes = new SimpleListProperty<>();

    public Pixes(ObservableList<Pix> _pixes) {
        pixes.setValue(_pixes);
    }

    public ObservableList<Pix> getPixes() {
        return pixes.get();
    }

    public ListProperty<Pix> pixesProperty() {
        return pixes;
    }

    public void setPixes(ObservableList<Pix> pixes) {
        this.pixes.set(pixes);
    }
}
