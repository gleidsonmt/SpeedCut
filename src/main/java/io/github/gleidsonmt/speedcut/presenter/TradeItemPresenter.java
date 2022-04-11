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
import io.github.gleidsonmt.speedcut.core.app.dao.DaoService;
import io.github.gleidsonmt.speedcut.core.app.dao.DaoTradeItem;
import io.github.gleidsonmt.speedcut.core.app.model.Product;
import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import io.github.gleidsonmt.speedcut.core.app.model.Service;
import io.github.gleidsonmt.speedcut.core.app.model.TradeItem;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  21/03/2022
 */
public class TradeItemPresenter extends Presenter<TradeItem> {

    private static final DaoTradeItem   DAO_TRADE_ITEM     = new DaoTradeItem();
    private static final DaoProduct     DAO_PRODUCT     = new DaoProduct();
    private static final DaoService     DAO_SERVICE     = new DaoService();

    @Override
    protected AbstractDao<TradeItem> getDao() {
        return DAO_TRADE_ITEM;
    }

    public Task<ObservableList<Product>> createProducts() {
        createConnection();
        return DAO_PRODUCT.populateAllTask();
    }

    public Task<ObservableList<Service>> createServices() {
        createConnection();
        return DAO_SERVICE.populateAllTask();
    }
}
