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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  21/03/2022
 */
@SuppressWarnings("unused")
public class Model {

    private final IntegerProperty   id;
    private final StringProperty    name;

    public Model() {
        this(null);
    }

    public Model(String name) {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.setName(name);
    }

    public int getId() {
        return this.id.get();
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return this.name.get();
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

}
