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

package io.github.gleidsonmt.speedcut.controller.login;

import io.github.gleidsonmt.gncontrols.GNPasswordBox;
import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.speedcut.core.app.animations.Animations;
import io.github.gleidsonmt.speedcut.core.app.controller.Masks;
import io.github.gleidsonmt.speedcut.core.app.dao.RepoUserImpl;
import io.github.gleidsonmt.speedcut.core.app.dao.Repositories;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.model.User;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/02/2022
 */
public class Login extends ResponsiveView implements Initializable {

    @FXML private GridPane loginContent;
    @FXML private GridPane registerContent;

    @FXML private GridPane layout;
    @FXML private StackPane imgWrapper;
    @FXML private StackPane body;
    @FXML private VBox content;


    // Login fields
    @FXML private GNTextBox userNameBox;
    @FXML private GNPasswordBox passwordBox;

    // register fields
    @FXML private GNTextBox userName;
    @FXML private GNTextBox cpfField;
    @FXML private GNTextBox phone;
    @FXML private GNPasswordBox password;
    @FXML private GNPasswordBox password2;
    @FXML private VBox form;

    private RepoUserImpl repoUser;

    private final User user = new User();

    private boolean init = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        repoUser = (RepoUserImpl) Repositories.<User>get(User.class);


        Masks.cpfCnpjField(cpfField.getEditor());
        Masks.phoneNumber(phone.getEditor());

    }

    @FXML
    private void login() throws NavigationException {

        User user = repoUser.read("username", userNameBox.getText());

        if (user != null) {

            if (repoUser.validate(user, passwordBox.getText())) {

                user.setLogged(true);
                repoUser.put(user);
                repoUser.persist(); // don't miss only used in tests

                context.getRoutes().goHome();


//                    context.getDecorator().getRoot().setNeedsBar(true);

                    context.getDecorator().getLayout().setDrawer(
                            context.getRoutes().load("layout/drawer.fxml", "Drawer", "drawer")
                    );

            } else {
                passwordBox.setValid(false);
                Animations.errorOnTextBox(passwordBox);
            }
        } else {
            userNameBox.setValid(false);
            Animations.errorOnTextBox(userNameBox);
        }

    }

    @FXML
    private void exitSystem() {
        context.getDecorator().close();
    }

    @FXML
    private void goToPinterest() {
        context.openLink("https://br.pinterest.com/pin/29977153758385965/");
    }

    @FXML
    private void save() throws NavigationException {
        if (!init) initBinds();

        cpfField.setValid(Masks.isCPF(cpfField.getText()));
        phone.setValid(Masks.isPhoneNumber(phone.getText()));

        List<GNTextBox> list = form.getChildren().stream().filter(c -> c instanceof GNTextBox)
                .map(c -> ((GNTextBox) c)).filter(c -> !c.isValid()).toList();

//        if (!password.getText().equals(password2.getText())) {
//            password.setValid(false);
//            Animations.errorOnTextBox(password);
//        }

        if (list.size() > 0 ) {
            list.forEach(Animations::errorOnTextBox);
        } else {
            repoUser.put(user);
            if (repoUser.persist()) context.getRoutes().setContent("sales");
            context.getProperties().setProperty("app.registered", "true");
        }
    }

    @Override
    protected void updateLayout(double width) {
        layout.getColumnConstraints().clear();
        layout.getRowConstraints().clear();

        if (width <= 765) {
            layout.getChildren().remove(imgWrapper);
        } else {
            if (!layout.getChildren().contains(imgWrapper))
                layout.getChildren().add(imgWrapper);

            GridPane.setConstraints(imgWrapper, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.SOMETIMES, Priority.ALWAYS);
            GridPane.setConstraints(body, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER, Priority.ALWAYS, Priority.ALWAYS);
        }

    }

    @Override
    public void onEnter() {
        super.onEnter();

        boolean _register = Boolean.parseBoolean(context.getProperties().getProperty("app.registered"));

        passwordBox.getEditor().clear();
        passwordBox.reset();
        passwordBox.requestFocus();

        loginContent.setVisible(_register);
        registerContent.setVisible(!_register);

    }

    private void initBinds() {

        userNameBox.textProperty().addListener((observable, oldValue, newValue) ->
                userNameBox.reset());

        passwordBox.textProperty().addListener((observable, oldValue, newValue) -> passwordBox.reset());

        // 'Register' binds

        user.userNameProperty().bind(userName.textProperty());
        user.passwordProperty().bind(password.textProperty());

        userName.validProperty().bind(userName.getEditor().lengthProperty().greaterThan(3));
        password.validProperty().bind(password.getEditor().lengthProperty().greaterThan(3));

        password.validProperty().bind(Bindings.createBooleanBinding(() -> password.getText().equals(password2.getText()), password2.textProperty()));

        phone.textProperty().addListener((observable, oldValue, newValue) -> {
            phone.reset();
        });

        cpfField.textProperty().addListener((observable, oldValue, newValue) -> {
            cpfField.reset();
        });

        password2.textProperty().addListener((observable, oldValue, newValue) -> {
            password2.reset();
        });

        init = true;
    }
}
