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

import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.view.ActionViewController;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/09/2020
 */
public class DrawerController extends ResponsiveView implements ActionViewController {

    @FXML private VBox drawerBox;
    @FXML private HBox searchBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new DrawerBehavior(drawerBox, searchBox);
    }

    private void openOptions() {
//        ContextMenu contextSettings = new ContextMenu();
//        MenuItem profile = new MenuItem("Profile");
//        profile.setGraphic(new IconContainer(Icons.CONTACT));
//        MenuItem logout = new MenuItem("Logout");
//        logout.setGraphic(new IconContainer(Icons.LOGOUT));
//
//        contextSettings.getItems().addAll(profile, logout);
//
//        Bounds bounds = btn_settings.localToScreen(btn_settings.getBoundsInLocal());
////
//        contextSettings.show(ConfigApp.INSTANCE.getDecorator().getWindow(),
//                bounds.getMaxX(), bounds.getMinY()
//        );
    }

    @Override
    protected void updateLayout(double width) {

    }

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @FXML
    private void goProducts() throws NavigationException {
        window.navigate("register", true);
    }

    @FXML
    private void goSales() throws NavigationException {
        window.navigate("sales", true);
    }
}
