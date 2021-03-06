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

import io.github.gleidsonmt.speedcut.core.app.Global;
import io.github.gleidsonmt.speedcut.core.app.dao.Dao;
import io.github.gleidsonmt.speedcut.core.app.dao.DaoCashier;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  28/12/2021
 */
public class LoadViews extends Service<ViewComposer> implements Global, IManager {

    private final   StringBuilder       builder = new StringBuilder();
    private         List<ViewComposer>  yamlViews = null;

    public LoadViews() {
        Yaml yaml = new Yaml(new Constructor(List.class));
        yamlViews = yaml.load(getClass().getResourceAsStream(
                corePath + "views.yml"));
    }

    @Override
    protected Task<ViewComposer> createTask() {
        return new Task<>() {
            @Override
            protected ViewComposer call()  {

                setLoadText("Starting application.. ");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                setLoadText("Loading database...");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for(ViewComposer view : yamlViews) {
                    Platform.runLater( () -> {
                        loadView(view);
                    });
                }

                return null;
            }
        };
    }

    private void setLoadText(String message) {
        final Label labelInfo = (Label)
                window.lookup("#labelLoading");
        Platform.runLater( () -> labelInfo.setText(message));
    }

    @Override
    protected void succeeded() {
        try {
            if (properties.isLogged()) {

                window.setLeftDrawer(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
                        corePath + "view/drawer.fxml"))));

                window.navigate("sales");

            } else if (!properties.isRegistered()) {
                window.navigate("register");
            } else {
                window.navigate("login");
            }
        } catch (NavigationException | IOException e) {
            e.printStackTrace();
        }
    }

    private void loadView(ViewComposer view) {
        loadView(view, null);
    }

    private void loadView(ViewComposer view, String path) {

        FXMLLoader loader = new FXMLLoader();
        URL location = null;

        if (path == null) {
            path = "/io.github.gleidsonmt.speedcut.view";
        }

        if(view.getDirectory() != null ) {
            builder.append("/").append(view.getDirectory());
        }

        if(view.getViews() != null) {
            for (ViewComposer v : view.getViews()) {
                if (v.getFxml() != null) {

                    location = getClass().getResource(path + builder + "/"
                            + v.getFxml());
                }
                v.setRoot(view);
                loadView(v);
            }
        }

        if(view.getDirectory() == null ) {
            location = LoadViews.class.getResource(path + builder + "/"
                    + view.getFxml());

        } else if(view.getFxml() != null && view.getDirectory() != null){
            location = getClass().getResource(path + builder + "/"
                    + view.getFxml());
        }

        if(view.getDirectory() != null) {
            String act = builder.substring(builder.lastIndexOf("/") + 1 , builder.length());
            if (act.equals(view.getDirectory())) {
                builder.delete(builder.lastIndexOf("/"), builder.length());
            }
        }

        if ( location != null && view.getFxml() != null) {
//            loader.setCharset(StandardCharsets.UTF_8);
            loader.setLocation(location);
//            loader.setResources(App.INSTANCE.getResourceBundle());

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            setLoadText("Loading Views [" + view.getName() + "]");
            window.getViews().add(new View(view, loader));

        } else if(view.getFxml() != null) {
            IOException io = new IOException("The fxml with ["
                    + view.getName()  + "]" + " doesn't correspond.");
            io.printStackTrace();
        }
    }

}
