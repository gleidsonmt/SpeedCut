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
import io.github.gleidsonmt.speedcut.core.app.layout.containers.SnackBar;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.Wrapper;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IRoot;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import io.github.gleidsonmt.speedcut.core.app.view.WindowDecorator;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.*;

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
                layout.getContent().setPadding(new Insets(0));
                layout.getContent().getChildren().add(0, layout.getBar());
            } else  {

                layout.getContent().setPadding(new Insets(40, 0,0,0));
                layout.getContent().getChildren().remove(layout.getBar());
            }
        });


        hamburger.setOnAction(event -> {
            wrapper.show();

//            wrapper.openDrawerLeft(getLayout().getOldLeft());

            wrapper.getDrawer()
                    .content(getLayout().getOldLeft())
                    .width(250D)
                    .side("left")
                    .show();

        });


        window.widthProperty().addListener((observable, oldValue, newValue) -> {
            double drawerWidth = 250;
            double _new = newValue.doubleValue();

            needsBar.set(window.getContent().equals(this));

            if (needsBar.get()) {
                if (_new < ResponsiveView.BreakPoints.X_LARGE) {
                    layout.setLeft(null);
                    window.addControl(0, hamburger);
                    getLayout().getBar().setPadding(new Insets(0,0,0,40));

                } else {
                    window.removeControl(hamburger);
                    layout.setLeft(getLayout().getOldLeft());
                    getLayout().getBar().setPadding(new Insets(0));
                }
            }

        });
    }

    public Wrapper getWrapper() {
        return wrapper;
    }

    public Layout getLayout() {
        return layout;
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
