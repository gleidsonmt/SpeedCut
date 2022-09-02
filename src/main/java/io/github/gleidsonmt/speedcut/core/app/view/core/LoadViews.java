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

package io.github.gleidsonmt.speedcut.core.app.view.core;

import io.github.gleidsonmt.speedcut.controller.form.PictureSelectorController;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.view.View;
import io.github.gleidsonmt.speedcut.core.app.view.ViewComposer;
import io.github.gleidsonmt.speedcut.core.app.view.WindowDecorator;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IView;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  28/12/2021
 */
public class LoadViews extends Service<ViewComposer> implements Context {

    private final   StringBuilder       builder = new StringBuilder();
    private         List<ViewComposer>  yamlViews = null;

    private WindowDecorator decorator;

    public LoadViews(WindowDecorator decorator) {
        this.decorator = decorator;

        Yaml yaml = new Yaml(new Constructor(List.class));

        yamlViews = yaml.load(getClass().getResourceAsStream(
                context.getPaths().getCore() + "views.yml"));
    }

    @Override
    protected Task<ViewComposer> createTask() {
        return new Task<>() {
            @Override
            protected ViewComposer call()  {

                setLoadText("Iniciando Aplicação.. ");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                setLoadText("Carregando base de dados...");

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
                decorator.lookup("#labelLoading");
        Platform.runLater( () -> labelInfo.setText(message));
    }

    @Override
    protected void succeeded() {
        try {

            if (context.getUser() != null && context.getUser().isLogged()) {

                decorator.fullBody();

                decorator.getLayout()
                        .setDrawer(context.getRoutes().load(
                        "layout/drawer.fxml", "Drawer", "drawer"
                        )
                );

                context.getRoutes().setContent("dash");

                FileChooser fileChooser = new FileChooser();

                fileChooser.setTitle("Selecione uma imagem");
                fileChooser.setInitialDirectory(new File("C:\\Users\\" + System.getProperties().get("user.name") + "\\Pictures\\"));


                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Transparent Images", "*.png"),
                        new FileChooser.ExtensionFilter("Simple Images", "*.jpg")
                );

                File file = fileChooser.showOpenDialog(context.getDecorator().getScene().getWindow());

                if (file == null) return;

                IView view = context.getRoutes().load("popups/picture_selector.fxml", "Picture Selector", "pic_selector");

                PictureSelectorController viewController = (PictureSelectorController) view.getController();

                Image image = new Image(String.valueOf(file));
                viewController.setImage(image);

                // a condi;'ao dele ficar muito pequeno eh transformar em um root no principal

                double width = Math.min(image.getWidth(), (context.getDecorator().getWidth() / 2));
//                double height = context.getDecorator().getHeight() - 50;
//                double width = image.getWidth();
                double height = image.getHeight();

                context .getDecorator()
                        .getRoot()
                        .getWrapper()
                        .getPopup()
                        .size( width + 40, 400 + 350)
                        .onEnter(event -> {
                            viewController.setImage(image);
                            viewController.onEnter();
                        })
//                        .onExit(event -> setAvatar(viewController.getImage()))
                        .alignment(Pos.CENTER)
                        .content(view.getRoot())
                        .show();

//                context.getDecorator().getRoot().setNeedsBar(true);

            } else {
//                context.getDecorator().getRoot().setNeedsBar(false);
                context.getRoutes().setView("login");
            }
        } catch (NavigationException e) {
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

            context.getRoutes().addView(new View(view, loader));

        } else if(view.getFxml() != null) {
            IOException io = new IOException("The fxml with ["
                    + view.getName()  + "]" + " doesn't correspond.");
            io.printStackTrace();
        }
    }

}
