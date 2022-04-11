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

package io.github.gleidsonmt.speedcut.core.app.factory;

import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.controller.SalesController;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.model.Sale;
import io.github.gleidsonmt.speedcut.core.app.view.ActionViewController;
import io.github.gleidsonmt.speedcut.core.app.view.IManager;
import io.github.gleidsonmt.speedcut.presenter.ProfessionalPresenter;
import io.github.gleidsonmt.speedcut.presenter.SalePresenter;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/04/2022
 */
public class SaleContext extends ContextMenu implements IManager {

    private final SalePresenter salePresenter = new SalePresenter();

    public SaleContext(TableRow<Sale> tableRow) {

        MenuItem menuDelete = new MenuItem();
        menuDelete.setText("Remover");
        menuDelete.setGraphic(new IconContainer(Icons.DELETE_OUTLINED));

        menuDelete.setOnAction(event -> {
            window.createSnackBar()
                    .type("done")
                    .onAction(e -> {
                        salePresenter.createConnection();
                        salePresenter.store(tableRow.getItem());
                        salePresenter.persist();
                    })
                    .onFinished(e -> {
                        salePresenter.delete(tableRow.getItem());
                        salePresenter.persist();
                    })
                    .message("Venda removida")
                    .show();

        });

        MenuItem menuAppointment = new MenuItem();
        menuAppointment.setText("Agendar");
        menuAppointment.setGraphic(new IconContainer(Icons.EVENT));

        MenuItem menuProfessional = new MenuItem();
        menuProfessional.setText("Profissional");
        menuProfessional.setGraphic(new IconContainer(Icons.BADGE));



        menuProfessional.setOnAction(e -> {

            int size = new ProfessionalPresenter().getSizeFromServer();
            ActionViewController controller = window.getViews().getControllerFrom("professionals");

            if (size < 6) {

                window.getWrapper()
                        .getPopOver()
                        .size(320, 600)
                        .arrowLocation("left_top")
                        .visibleArrow(false)
                        .content(window.getViews().getRootFrom("professionals"))
                        .onEnter(event -> {
                            controller.onEnter();
                            controller.updateModel(tableRow.getItem());
                        })
                        .show(tableRow);

                if (window.getWidth() < 700) {
                    try {
                        window.navigate("professionals");
                        controller.updateModel(tableRow.getItem());

                    } catch (NavigationException ed) {
                        ed.printStackTrace();
                    }
                }
            } else {
                try {
                    controller.updateModel(tableRow.getItem());
                    window.navigate("professionals");
                } catch (NavigationException ex) {
                    ex.printStackTrace();
                }
            }
        });

        MenuItem menuClient = new MenuItem();
        menuClient.setText("Cliente");
        menuClient.setGraphic(new IconContainer(Icons.PERSON));

        menuClient.setOnAction(event -> {
            SalesController controller = (SalesController) window.getViews().getControllerFrom("sales");
            controller.editClient();
        });

        this.getItems().addAll(menuClient, menuProfessional, menuAppointment, menuDelete);

    }
}
