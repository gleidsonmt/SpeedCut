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

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.gleidsonmt.gndecorator.GNDecorator;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.AppPaths;
import io.github.gleidsonmt.speedcut.core.app.Theme;
import io.github.gleidsonmt.speedcut.core.app.dao.Repositories;
import io.github.gleidsonmt.speedcut.core.app.layout.ILayout;
import io.github.gleidsonmt.speedcut.core.app.layout.Root;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.Wrapper;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IDecorator;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IRoot;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class WindowDecorator extends GNDecorator implements IDecorator {

    private final IRoot root = new Root(this);
    private HostServices hostServices;

    public WindowDecorator(Properties _properties, @NotNull AppPaths _path) throws IOException {

        Theme theme = new Theme(_path.getCore());
        getStylesheets().setAll(theme.getStyleesheets());
        getIcons().addAll(theme.getLogo());

        root.getLayout().setContent(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(_path.getViews() + "layout/loader.fxml"))));

        if (root instanceof StackPane) {
            setContent( (StackPane) root);
        }

        fullBody();

        setWidth(Integer.parseInt(_properties.getProperty("app.width")));
        setHeight(Integer.parseInt(_properties.getProperty("app.height")));

        setMinWidth(Integer.parseInt(_properties.getProperty("app.min.width")));
        setMinHeight(Integer.parseInt(_properties.getProperty("app.min.height")));

    }

    @Override
    public IRoot getRoot() {
        return root;
    }

    @Override
    public Wrapper getWrapper() {
        return root.getWrapper();
    }

    public void loadViews() {
        LoadViews loadViews = new LoadViews(this);
        loadViews.start();
    }

    protected boolean hasInstance() {
        return getWindow().isShowing();
    }

    @Override
    public ILayout getLayout() {
        return getRoot().getLayout();
    }

    @Override
    public void setModule(String title) {
        root.setTitle(title);
    }

    @Override
    public void show(HostServices hostServices) {
        this.hostServices = hostServices;

        if (!hasInstance()) {

            Repositories.load();

            show();
            loadViews();
//
            getWindow().setOnHidden(event -> getRoot().getWrapper().getPopOver().hide());

            CSSFX.start();
//            ScenicView.show(getWindow().getScene());

        }
    }

    @Override
    public void close() {
        this.getWindow().hide();
    }

}
