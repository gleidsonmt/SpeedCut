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

package io.github.gleidsonmt.speedcut.controller;

import io.github.gleidsonmt.gncontrols.GNMonetaryField;
import io.github.gleidsonmt.speedcut.core.app.dao.DaoCashier;
import io.github.gleidsonmt.speedcut.core.app.model.Cashier;
import io.github.gleidsonmt.speedcut.core.app.util.MoneyUtil;
import io.github.gleidsonmt.speedcut.core.app.view.ActionViewController;
import io.github.gleidsonmt.speedcut.presenter.CashierPresenter;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/04/2022
 */
public class OpenController implements ActionViewController {

    @FXML private GNMonetaryField monetaryField;

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void initialize (URL location, ResourceBundle resources) {

    }

    @FXML
    private void closePopup () {
        window.getWrapper().getPopup().close();
    }

    @FXML
    private void openCashier () {
        CashierPresenter presenter = new CashierPresenter();
        Cashier cashier = new Cashier();
        cashier.setOpen(true);
        cashier.setAmount(MoneyUtil.get(monetaryField.getText()));
        presenter.store(cashier);
        if (presenter.persist()) {
            window.getWrapper().getPopup().close();
        }
//        monetaryField.getText()
    }
}
