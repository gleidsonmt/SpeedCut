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

package io.github.gleidsonmt.speedcut.core.app.view;

import io.github.gleidsonmt.speedcut.core.app.view.intefaces.ActionView;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  24/02/2022
 */
public abstract class ResponsiveView implements ActionView {

    private final ChangeListener<Number> updateListener = (observable, oldValue, newValue) -> updateLayout(newValue.doubleValue());

    protected abstract void updateLayout(double width);

    @Deprecated
    public void update(Node node, int col, int row) {
        GridPane.clearConstraints(node);
        GridPane.setConstraints(node, col, row, 1,1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
    }


    @Deprecated
    public void update(Node node, int col, int row, Pos pos) {

        switch (pos) {
            case CENTER -> GridPane.setConstraints(node, col, row, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
            case CENTER_LEFT -> GridPane.setConstraints(node, col, row, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        }

    }

    @Override
    public void onEnter() {

        updateLayout(context.getDecorator().getRoot().getWidth());

        context.getDecorator()
                .getRoot()
                .widthProperty()
                .addListener(updateListener);

    }

    @Override
    public void onExit() {

        context.getDecorator()
                .getRoot()
                .widthProperty()
                .removeListener(updateListener);

    }
}
