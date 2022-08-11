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

package io.github.gleidsonmt.speedcut.core.app.factory.column;

import io.github.gleidsonmt.speedcut.core.app.model.SaleItem;
import io.github.gleidsonmt.speedcut.core.app.model.Transaction;
import javafx.scene.control.*;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  02/08/2022
 */
public class SettingsColumnFactory implements Callback<TableColumn<Transaction, Transaction>, TableCell<Transaction, Transaction>> {

    @Override
    public TableCell<Transaction, Transaction> call(TableColumn<Transaction, Transaction> param) {

        return new TableCell<>(){
            @Override
            protected void updateItem(Transaction item, boolean empty) {
                super.updateItem(item, empty);

                if (item != null) {

                    Label lblConfig = new Label();

                    SVGPath path = new SVGPath();
                    path.setContent("m9.825 21.5-.4-2.975q-.375-.125-.925-.413-.55-.287-.875-.612l-2.8 1.175-2.15-3.9L4.975 13q-.025-.25-.063-.525-.037-.275-.037-.525 0-.2.037-.462.038-.263.063-.588l-2.3-1.725 2.15-3.8 2.675 1.1q.425-.35.95-.637.525-.288.975-.463l.4-2.925h4.35l.375 2.975q.525.225.963.462.437.238.887.613l2.775-1.125 2.15 3.8-2.45 1.85q.075.275.087.525.013.25.013.45 0 .175-.025.425t-.05.6l2.35 1.75-2.15 3.9-2.825-1.2q-.425.35-.837.6-.413.25-.863.4l-.4 3.025Zm2.05-6.65q1.225 0 2.063-.838.837-.837.837-2.062 0-1.225-.837-2.063-.838-.837-2.063-.837-1.2 0-2.05.837-.85.838-.85 2.063t.85 2.062q.85.838 2.05.838Z");
                    path.setStyle("-fx-fill : -text-color;");
                    lblConfig.setGraphic(path);
                    lblConfig.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setGraphic(lblConfig);

                } else {
                    setText(null);
                    setGraphic(null);
                }
            }
        };
    }

//            implements javafx.util.Callback<TableColumn<S, Number>, TableCell<S, Number>>


}
