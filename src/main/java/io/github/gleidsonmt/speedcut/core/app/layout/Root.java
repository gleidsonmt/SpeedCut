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

package io.github.gleidsonmt.speedcut.core.app.layout;

import io.github.gleidsonmt.gncontrols.GNFloatingButton;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.IWrapper;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.SnackBar;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.Wrapper;
import io.github.gleidsonmt.speedcut.core.app.view.BreakPoints;
import io.github.gleidsonmt.speedcut.core.app.view.WindowDecorator;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IRoot;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class Root extends StackPane implements IRoot {

    private final Layout layout = new Layout();
    private final Wrapper wrapper;

    private SnackBar snackBar;

    private final BooleanProperty needsBar = new SimpleBooleanProperty(false);
    private final GNFloatingButton hamburger = new GNFloatingButton();

    public Root(WindowDecorator window) {

        wrapper = new Wrapper(this, window);
//        getChildren().add(wrapper);
        getChildren().add(layout);


        hamburger.getStyleClass().add("hamburger");
        hamburger.setMinSize(35, 35);
        hamburger.setMaxSize(35, 35);
        hamburger.setGraphic(new IconContainer(Icons.HAMBURGER));

        needsBar.addListener((observable, oldValue, newValue) -> {

            if (newValue) {
                layout.getContent().getChildren().add(0, layout.getBar());
            } else  {

                layout.getContent().getChildren().remove(layout.getBar());
            }
        });


        hamburger.setOnAction(event -> {
            wrapper.show();

//            wrapper.openDrawerLeft(getLayout().getOldLeft());

            wrapper.getDrawer()
                    .content(layout.getOldLeft())
                    .width(250D)
                    .side("left")
                    .show();

        });


        window.widthProperty().addListener((observable, oldValue, newValue) -> {
            double drawerWidth = 250;
            double _new = newValue.doubleValue();

            needsBar.set(window.getContent().equals(this));

            if (needsBar.get()) {
                if (_new < BreakPoints.X_LARGE) {
                    layout.setLeft(null);
                    window.addControl(0, hamburger);
                    layout.getBar().setPadding(new Insets(0,0,0,40));

                } else {
                    window.removeControl(hamburger);
                    layout.setLeft(layout.getOldLeft());
                    layout.getBar().setPadding(new Insets(0));
                }
            }

        });
    }

    public IWrapper getWrapper() {
        return wrapper;
    }

    @Override
    public ILayout getLayout() {
        return layout;
    }

    @Override
    public void setContent(Parent content) {
        layout.setContent(content);
    }

    public boolean isNeedsBar() {
        return needsBar.get();
    }

    public BooleanProperty needsBarProperty() {
        return needsBar;
    }

    public void setNeedsBar(boolean needsBar) {
        this.needsBar.set(needsBar);
    }

    @Override
    public void setTitle(String _title) {
        layout.setTitle(_title);
    }

    @Override
    public SnackBar createSnackBar() {
        return new SnackBar(this);
    }
}
