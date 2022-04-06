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

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import fr.brouillard.oss.cssfx.CSSFX;
import io.github.gleidsonmt.gndecorator.GNDecorator;
import io.github.gleidsonmt.speedcut.core.app.Global;
import io.github.gleidsonmt.speedcut.core.app.exceptions.ControllerCastException;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.layout.CenterLayout;
import io.github.gleidsonmt.speedcut.core.app.layout.Root;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.Wrapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class WindowDecorator extends GNDecorator implements Global {

    private final ViewManager   views = new ViewManager();

    private final Root root = new Root(this);

    protected WindowDecorator() {

        try {
            root.getCenterLayout().setBody(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(
                    corePath + "view/loader.fxml"))));

        } catch (IOException e) {
            e.printStackTrace();
        }

        addStyleSheets();

        setContent(root);
        fullBody();

        getIcons().addAll(
                new Image(Objects.requireNonNull(getClass().getResource(corePath + "theme/img/logo.png")).toExternalForm())
        );

        setWidth(Integer.parseInt(properties.getProperty("app.width")));
        setHeight(Integer.parseInt(properties.getProperty("app.height")));

        setMinWidth(Integer.parseInt(properties.getProperty("app.min.width")));
        setMinHeight(Integer.parseInt(properties.getProperty("app.min.height")));

    }

    public void addStyleSheets() {
        addStylesheets(
                Objects.requireNonNull(getClass().getResource(corePath + "theme/fonts/fonts.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(corePath + "theme/css/material-color.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(corePath + "theme/css/light.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(corePath + "theme/css/skeleton.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(corePath + "theme/css/bootstrap.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(corePath + "theme/css/shape.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(corePath + "theme/css/typographic.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(corePath + "theme/css/helpers.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(corePath + "theme/css/master.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(corePath + "theme/css/custom-scroll.css")).toExternalForm()
        );
    }

    public void lockControls(boolean value) {
        if (value) lockControls();
        else unLockControls();
    }

    public ViewManager getViews() {
        return views;
    }

    public void setLeftDrawer(Parent content) {
        root.getLayout().setLeft(content);
        HBox bar = (HBox) lookup("#gn-bar");

        root.getLayout().leftProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                bar.setPadding(new Insets(0,0,0, getWrapper().getDrawer().getWidth()));
            } else {
                bar.setPadding(new Insets(0));
            }
        });

        bar.setPadding(new Insets(0,0,0, getWrapper().getDrawer().getWidth()));
    }

    @Deprecated
    public Root getRoot() {
        return root;
    }

    public Wrapper getWrapper() {
        return root.getWrapper();
    }

    @Deprecated
    public CenterLayout getCenterLayout() {
        return root.getCenterLayout();
    }

    @Deprecated
    public ObservableList<Node> getControls() {
        return this.getCustomControls();
    }

    public void hideControls(boolean value) {
        this.getCustomControls().forEach(c -> c.setVisible(value));
    }

    protected void persist() throws IOException {
        properties.stringPropertyNames().forEach(f -> properties.setProperty(f, properties.getProperty(f)));

        try {
            properties.store(new FileOutputStream("src/main/resources/" + corePath + "app.properties"), "Fcil");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Hyperlink> crumbs = new ArrayList<>();

    public void navigate(String _view) throws NavigationException {
        navigate(_view, false);
    }

    public void navigate(String _view, boolean animate) throws NavigationException {

        IView view = views.get(_view);

        if (view == null) throw new NavigationException("NAVIGATION", String.format("The view '%s' was not encountered.", _view));

        IView previous = views.getCurrent(); // Pega a view atual

        try {

            if (previous != null) { // Se view diferente de nulo
                if (previous.getController() != null) // se controlador diferente de nulo
                    previous.getController().onExit(); // executa a acao da visao antes de sair
            }

            if (view.getController() != null) { // verifica se a nova view possui controlador
                view.getController().onEnter(); // executa a primeira acao
            }

            views.setCurrent(view); // atualiza a visao corrent na controladora de views.

        } catch (ControllerCastException e) {
            e.printStackTrace();
        }


        if (animate) {
            if (previous != null) {
                FadeOut fadeOut = new FadeOut(previous.getRoot());
                fadeOut.setSpeed(8);
                fadeOut.play();

                fadeOut.getTimeline().setOnFinished(event -> {
                    previous.getRoot().setOpacity(1);
                    FadeIn fadeIn = new FadeIn(view.getRoot());
                    fadeIn.setSpeed(8);

                    view.getRoot().setOpacity(0);
                    root.getCenterLayout().setBody(view.getRoot()); // Configura o layout center

                    fadeIn.getTimeline().setOnFinished(e -> view.getRoot().setOpacity(1));
                    fadeIn.play();
                });

            }
        } else {
            root.getCenterLayout().setBody(view.getRoot()); // Configura o layout center
        }

        count = 0;
        crumbs.forEach(this::removeControl);
        crumbs.clear();
        getControls().clear();
        updateBreadcrumb(view.getComposer()); // atualiza o breadcrumb

//        getCenterLayout().
//        update(view.getComposer());

        getControls().stream().filter(f -> f instanceof Hyperlink)
                .map(m -> (Hyperlink) m)
                .forEach(e -> {
                    Bounds bounds = e.localToScene(e.getBoundsInLocal());
                }
        );
    }

    int count =  0;

    // atualiza o breadcrumb // in test
    private void updateBreadcrumb(ViewComposer viewComposer) {
        if (viewComposer.getRoot() != null) {

            addControl(count == 0 ? count : count--, createBread(viewComposer));

            updateBreadcrumb(viewComposer.getRoot());

            Label label = new Label("/");
            label.setStyle("-fx-font-size : 14pt; -fx-text-fill : -info;");
            label.setPrefSize(5, 30);
            label.setAlignment(Pos.CENTER);
            label.setMouseTransparent(true);
            addControl(count++, label);

        } else {
//            getControls().removeAll(crumbs);
//            crumbs.clear();

            Hyperlink hyperlink = new Hyperlink();
            hyperlink.setStyle("-fx-font-size : 20;");
            hyperlink.setText(viewComposer.getTitle());
//            crumbs.add(count++, hyperlink);


            hyperlink.setOnAction(event -> {
                try {
                    navigate(viewComposer.getName());
                } catch (NavigationException e) {
                    e.printStackTrace();
                }
            });

            addControl(count++, hyperlink);

        }

//        crumbs.forEach(e -> {
//            Hyperlink hyperlink = new Hyperlink();
//            hyperlink.setText("/");
//            addControl(e);
//            System.out.println("count = " + count);
//            if (count != 0)
//                addControl(hyperlink);
//        });

    }

    private Hyperlink createBread(ViewComposer composer) {
        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setStyle("-fx-font-size : 20;");
        hyperlink.setText(composer.getTitle());
        return hyperlink;
    }

    public void loadViews() {
        LoadViews loadViews = new LoadViews();
        loadViews.start();
    }

    protected boolean hasInstance() {
        return getWindow().isShowing();
    }

    protected void start() {
        show();
        loadViews();

        getWindow().setOnHidden(event -> getRoot().getWrapper().getPopOver().hide());

        CSSFX.start();
//        ScenicView.show(getWindow().getScene());
    }
}
