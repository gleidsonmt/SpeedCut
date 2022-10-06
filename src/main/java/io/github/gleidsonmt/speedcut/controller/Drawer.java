/*
 * Copyright (C) Gleidson Neves da Silveira
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with popOver program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.github.gleidsonmt.speedcut.controller;

import io.github.gleidsonmt.gncontrols.GNTextBoxB;
import io.github.gleidsonmt.gncontrols.material.icon.IconContainer;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.core.app.dao.RepoCashierImpl;
import io.github.gleidsonmt.speedcut.core.app.dao.Repositories;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.model.Cashier;
import io.github.gleidsonmt.speedcut.core.app.model.User;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.ActionView;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/09/2020
 */
public class Drawer extends ResponsiveView implements Initializable, ActionView, Context {

    @FXML private VBox drawerBox;
    @FXML private HBox searchBox;

    @FXML private Button btnSettings;

    @FXML private StackPane boxAvatar;
    @FXML private Rectangle avatarCircle;

    @FXML private Label lblUsername;
    @FXML private Label lblEmail;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new DrawerBehavior(drawerBox, searchBox);

        lblUsername.textProperty().bind(context.getUser().userNameProperty());
        lblEmail.textProperty().bind(context.getUser().emailProperty());

        avatarCircle.setFill(new ImagePattern(context.getUser().getThumbnail()));

//        context.getUser().imgPathProperty().addListener((observable, oldValue, newValue) ->
//                avatarCircle.setFill(( (Rectangle)  context.getUser().getAvatar(40)).getFill()));


    }

    @FXML
    private void openOptions() {

        ContextMenu contextSettings = new ContextMenu();

        MenuItem profile = new MenuItem("Profile");
        profile.setGraphic(new IconContainer(Icons.ACCOUNT_CIRCLE));
        MenuItem logout = new MenuItem("Logout");
        logout.setGraphic(new IconContainer(Icons.LOGOUT));

        logout.setOnAction(event -> {

            context.getProperties().setProperty("app.registered", "true");

            context.getUser().setLogged(false);
            Repositories.<User>get(User.class).put(context.getUser());
            Repositories.<User>get(User.class).persist();

            try {
                context.getRoutes().setView("login");
            } catch (NavigationException e) {
                e.printStackTrace();
            }
        });

        contextSettings.getItems().addAll(profile, logout);

        Bounds bounds = btnSettings.localToScreen(btnSettings.getBoundsInLocal());

        contextSettings.show(context.getWindow().getWindow(), bounds.getMaxX(), bounds.getMinY());


    }

    @Override
    protected void updateLayout(double width) {

    }

    @Override
    public void onEnter() {
//        System.out.println("context.getUser().getAvatar() = " + context.getUser().getAvatar());
    }

    @Override
    public void onExit() {

    }

    @FXML
    private void goProducts() throws NavigationException {
        context.getRoutes().setContent("register");
    }

    @FXML
    private void goSales() throws NavigationException {

//        RepoCashierImpl repo = (RepoCashierImpl) Repositories.<Cashier>get(Cashier.class);
//
//        if (repo.getOpened() == null) {
//            context.getRoutes().setContent("open_cashier");
//        } else


//            context.getRoutes().setContent("sales");

        context.getRoutes().setContent("sales");

    }

    @FXML
    private void goDash() throws NavigationException {
        context.getRoutes().setContent("dash");
    }

    @FXML
    private void goRegisterClient() throws NavigationException {
        context.getRoutes().setContent("client_register");
    }

    @FXML
    private void goAbout() throws NavigationException {
        context.getRoutes().setContent("about");
    }
}

