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

package io.github.gleidsonmt.speedcut.core.app;

import io.github.gleidsonmt.speedcut.core.app.dao.RepoCashierImpl;
import io.github.gleidsonmt.speedcut.core.app.dao.RepoUserImpl;
import io.github.gleidsonmt.speedcut.core.app.dao.Repositories;
import io.github.gleidsonmt.speedcut.core.app.model.Cashier;
import io.github.gleidsonmt.speedcut.core.app.model.User;
import io.github.gleidsonmt.speedcut.core.app.view.*;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.*;
import javafx.application.HostServices;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Properties;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  21/05/2022
 */
@SuppressWarnings("all")
public class App implements AppPaths, IContext {

    private ObjectProperty<User>    user        = new SimpleObjectProperty<>();
    private Properties              properties  = new Properties();

    private IRotes          routes;
    private HostServices    hostServices;
    private WindowDecorator window;

    public App ()  {
        loadProperties();
        try {
            window = new WindowDecorator(properties, getPaths());
        } catch (IOException e) {
            e.printStackTrace();
        }
        routes = new Routes(window, getPaths());
    }

    private void createAlert(String title, String message, String errror) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(errror);
        alert.show();
    }

    private void createFile(@NotNull File file) {
        try {
            file.createNewFile();

            if (file.exists()) {
              load(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void load(File file) {
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

        properties.putIfAbsent("app.name", "SpeedCut");
        properties.putIfAbsent("app.width", "1280");
        properties.putIfAbsent("app.height", "800");
        properties.putIfAbsent("app.min.width", "450");
        properties.putIfAbsent("app.min.height", "720");
        properties.putIfAbsent("user.logged", "false");
        properties.putIfAbsent("user.registered", "false");

    }

    private void loadProperties() {

        File file = new File(getClass().getResource(getCore() + "app.properties").getFile());

        if (!file.exists()) {
            createFile(file);
        } else {
            load(file);
        }
    }

    /*********************************************************************
     *
     *                  Util
     *
     **********************************************************************/

    public ActionView getControlller(String view) {
        ActionView actionView = this.routes.getView(view).getController();
        if (actionView instanceof WorkedView) {
            new Throwable("This controller is part of the other view, it's not possible to get.");
            return null;
        }
        return this.routes.getView(view).getController();
    }

    public Parent getRoot(String view) {
        return routes.getView(view).getRoot();
    }

    public IView workAndGet(String view) {
        IView _view = this.routes.getView(view);
        _view.getController().onEnter();
//        this.routes.setCurrent(_view);
        return _view;
    }

    public IView workAndLeave() {
        IView _view = getPrevious();
        if (_view.getController() != null)
            _view.getController().onExit();
        return _view;
    }

    @Deprecated
    public IView getPrevious() {
        return this.routes.getPrevious();
    }

    @Override
    public User getUser() {
        RepoUserImpl repositories = (RepoUserImpl) Repositories.<User>get(User.class);
        user.set(repositories.getCurrent());
        return user.get();
    }

    @Override
    public void setUser(User user) {
        this.user.set(user);
    }

    public AppPaths getPaths() {
        return this;
    }

    @Override
    public IRotes getRoutes() {
        return routes;
    }

    @Override
    public Cashier getCashier() {
        RepoCashierImpl repo = (RepoCashierImpl) Repositories.<Cashier>get(Cashier.class);
        return repo.getOpened();
    }

    @Override
    public void openLink(String link) {
        this.hostServices.showDocument(link);
    }

    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public void startApp(HostServices _hostServices) {
        this.hostServices = _hostServices;
        getDecorator().show(_hostServices);
    }

    @Override
    @Deprecated
    public WindowDecorator getWindow() {
        return window;
    }

    @Override
    public IDecorator getDecorator() {
        return window;
    }

    @Override
    public String getCore() {
        return "/io.github.gleidsonmt.speedcut.core.app/";
    }

    @Override
    public String getViews() {
        return "/io.github.gleidsonmt.speedcut.view/";
    }

    @Override
    public String getAvatars() {
        return "/io.github.gleidsonmt.speedcut.core.app/theme/img/avatars/";
    }
}
