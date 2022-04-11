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

import io.github.gleidsonmt.speedcut.core.app.dao.AbstractDao;
import io.github.gleidsonmt.speedcut.core.app.dao.DaoCashier;
import io.github.gleidsonmt.speedcut.core.app.exceptions.SQLQueryError;
import io.github.gleidsonmt.speedcut.core.app.model.Cashier;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/04/2022
 */
public class CashierPresenter extends Presenter<Cashier> {

    private final DaoCashier DAO_CASHIER = new DaoCashier();

    @Override
    protected AbstractDao<Cashier> getDao() {
        return DAO_CASHIER;
    }

    public Cashier getOpened() {
        return DAO_CASHIER.findOpened();
    }

    public void store(Cashier model) {
        try {
            createConnection();
            DAO_CASHIER.store(model);
        } catch (SQLQueryError e) {
            e.printStackTrace();
        }
    }

}
