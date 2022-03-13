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
import io.github.gleidsonmt.speedcut.core.app.dao.DaoProduct;
import io.github.gleidsonmt.speedcut.core.app.dao.DaoSaleItem;
import io.github.gleidsonmt.speedcut.core.app.dao.DaoService;
import io.github.gleidsonmt.speedcut.core.app.exceptions.SQLQueryError;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/02/2022
 */
public class SaleItemPresenter extends Presenter<SaleItem> {

    private static final DaoSaleItem DAO_SALE_ITEM = new DaoSaleItem();

    @Override
    protected AbstractDao<SaleItem> getDao() {
        return DAO_SALE_ITEM;
    }

    public boolean update(SaleItem saleItem) {
        try {
            createConnection();
            return getDao().update(saleItem);
        } catch (SQLQueryError e) {
            e.printStackTrace();
            return false;
        }
    }
}
