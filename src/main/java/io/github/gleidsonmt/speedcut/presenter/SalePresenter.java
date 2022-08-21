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

package io.github.gleidsonmt.speedcut.presenter;

import io.github.gleidsonmt.speedcut.core.app.dao.*;
import io.github.gleidsonmt.speedcut.core.app.exceptions.SQLQueryError;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/03/2022
 */
public class SalePresenter extends Presenter<Sale> {

//    private static final DaoSale getDao() = new DaoSale();

    @Override
    protected AbstractDao<Sale> getDao() {
//        return Provider.of(DaoSale.class);
        return null;
    }

    public void createDefaultSale() {
//        createConnection();
//        return getDao().createDefaultSale();
    }

    public boolean delete(Sale sale) {
//        createConnection();
        return getDao().delete(sale);
    }

    public boolean update(Sale sale) {
//        createConnection();
//        try {
//            return getDao().update(sale);
//        } catch (SQLQueryError e) {
//            e.printStackTrace();
//        }
        return false;
    }

    public ObservableList<Sale> getElements() {
        return getDao().getElements();
    }


}
