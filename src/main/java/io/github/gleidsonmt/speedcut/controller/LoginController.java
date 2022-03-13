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

package io.github.gleidsonmt.speedcut.controller;

import io.github.gleidsonmt.gncontrols.GNPasswordBox;
import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.speedcut.core.app.animations.Animations;
import io.github.gleidsonmt.speedcut.presenter.UserPresenter;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.model.User;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import javafx.fxml.FXML;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/02/2022
 */
public class LoginController extends ResponsiveView {

    @FXML private GNTextBox userNameBox;
    @FXML private GNPasswordBox passwordBox;

    private final UserPresenter presenter = new UserPresenter();
    private User user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameBox.textProperty().addListener((observable, oldValue, newValue) ->
            userNameBox.reset());
        passwordBox.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordBox.reset();
        });
    }

    @FXML
    private void login() throws NavigationException {
        user = presenter.find(userNameBox.getText());

        if (user != null) {
            if (presenter.validate(user, passwordBox.getText())) {
                window.navigate("sales");
            } else {
                passwordBox.setValid(false);
                Animations.errorOnTextBox(passwordBox);
            }
        } else {
            userNameBox.setValid(false);
            Animations.errorOnTextBox(userNameBox);
        }

    }

    private String compilePassword(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] messageDigest = md.digest(pass.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }


    @FXML
    private void exit() {
        System.out.println("exit");
    }

    @Override
    protected void updateLayout(double width) {

    }

    @Override
    public void onEnter() {

    }
}
