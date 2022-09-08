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
import io.github.gleidsonmt.speedcut.core.app.model.Avatar;
import io.github.gleidsonmt.speedcut.core.app.model.Cashier;
import io.github.gleidsonmt.speedcut.core.app.model.User;
import io.github.gleidsonmt.speedcut.core.app.view.WindowDecorator;
import io.github.gleidsonmt.speedcut.core.app.view.core.Routes;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.*;
import javafx.application.HostServices;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  21/05/2022
 * This class provides configs about the app.
 */
@SuppressWarnings("all")
public class App implements AppPaths, IContext {

    // default paths
    private String module = "/io.github.gleidsonmt.speedcut";
    private String core = module + ".core.app";
    private String views = module + ".view";

    private String theme = core + "/theme";
    private String images = core + "/theme/img";
    private String avatars = images + "/avatars";
    private String cursores = images + "/cursores";

    // Manipulating items
    private HostServices    hostServices;
    private WindowDecorator window;
    private IRotes          routes;

    // Testing
    private ObjectProperty<User>    user        = new SimpleObjectProperty<>();
    private Properties              properties  = new Properties();

    /// tests
    private Logger logger = Logger.getLogger("app");

    public App ()  {

        logger.setLevel(Level.ALL);
        loadProperties();
        logger.log(Level.INFO, "Initialing Application.");

        try {
            window = new WindowDecorator(properties, getPaths());
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
            e.printStackTrace();
        }
        routes = new Routes(window, getPaths());

    }


    private void loadProperties() {

        try {
            properties.load(getClass().getResourceAsStream(getFromCore("app.properties")));
        } catch (IOException e) {
            logger.severe(e.getMessage() );
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

    /*********************************************************************
     *
     *                  Util
     *
     **********************************************************************/

    private String executeCommand(String command) {
        StringBuffer output = new StringBuffer();
        Process p;
        String[] s = new String[1];
        s[0] = "Hello World!";

        System.out.println(Arrays.stream(Logger.getLogger("javafx").getHandlers()).toArray());
        try {
            p = Runtime.getRuntime().exec("notepad", s);

            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "What is for this";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }


    public ActionView getControlller(String view) {
        ActionView actionView = this.routes.getView(view).getController();
        if (actionView instanceof WorkedView) {
            new Throwable("This controller is part of the other view, it's not possible to get.");
            return null;
        }
        return this.routes.getView(view).getController();
    }

    @Deprecated
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
    public String getModule() {
        return module;
    }

    @Override
    public String getFromModule(String file) {
        return module.concat(formatFile(file));
    }

    @Override
    public String getCore() {
        return core;
    }

    @Override
    public String getViews() {
        return views;
    }

    @Override
    public String getFromView(String file) {
        return views.concat(formatFile(file));
    }

    @Override
    public String getAvatars() {
        return avatars;
    }

    @Deprecated
    @Override
    public String getCursores() {
        return cursores;
    }

    @Override
    public String getImages() {
        return images;
    }

    @Override
    public String getTheme() {
        return theme;
    }

    @Override
    public String getFromCore(@NotNull String fileOrPath) {
        return core.concat(formatFile(fileOrPath));
    }

    private @NotNull String formatFile(@NotNull String value) {

        if (value.contains("/")) {

            if (value.indexOf('/') == 0) {
                return value.replaceAll("/", "");
            } else return "/" + value;
        } else {
            return "/" + value;
        }
    }

    @Override
    public Image getImage(String path) {

        String test = getImages() + formatFile(path);

        Image image = new Image(getClass().getResource(test).toExternalForm());

        return image;
    }

    @Override
    public Avatar getAvatar(String path) {


        return new Avatar(
                getClass().getResource(
                        getAvatars() + formatFile(path)
                ).toExternalForm()
        );
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}
