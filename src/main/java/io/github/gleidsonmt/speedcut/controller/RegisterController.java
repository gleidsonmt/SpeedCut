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
import io.github.gleidsonmt.speedcut.core.app.Global;
import io.github.gleidsonmt.speedcut.core.app.animations.Animations;
import io.github.gleidsonmt.speedcut.core.app.controller.Masks;
import io.github.gleidsonmt.speedcut.core.app.exceptions.NavigationException;
import io.github.gleidsonmt.speedcut.core.app.model.User;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import io.github.gleidsonmt.speedcut.presenter.Presenter;
import io.github.gleidsonmt.speedcut.presenter.UserPresenter;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  31/12/2021
 */
public class RegisterController extends ResponsiveView  {

    @FXML private StackPane root;
    @FXML private StackPane imgWrapper;
    @FXML private StackPane content;
    @FXML private GridPane layout;
    @FXML private GNTextBox userName;
    @FXML private GNTextBox cpfField;
    @FXML private GNTextBox phone;
    @FXML private GNPasswordBox passwordBox;
    @FXML private GNPasswordBox passwordBox2;

    @FXML private VBox form;

    private final User user = new User();

    private boolean init = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Masks.cpfCnpjField(cpfField.getEditor());
        Masks.phoneNumber(phone.getEditor());
    }

    private void initBinds() {

        user.userNameProperty().bind(userName.textProperty());
        user.passwordProperty().bind(passwordBox.textProperty());

        userName.validProperty().bind(userName.getEditor().lengthProperty().greaterThan(3));
        passwordBox.validProperty().bind(passwordBox.getEditor().lengthProperty().greaterThan(3));

        phone.textProperty().addListener((observable, oldValue, newValue) -> {
            phone.reset();
        });

        cpfField.textProperty().addListener((observable, oldValue, newValue) -> {
            cpfField.reset();
        });

        passwordBox2.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordBox2.reset();
        });

        init = true;
    }

    @FXML
    private void save() throws NavigationException {
        if (!init) initBinds();

        System.out.println("default button trigged on register");

        cpfField.setValid(Masks.isCPF(cpfField.getText()));
        phone.setValid(Masks.isPhoneNumber(phone.getText()));

        List<GNTextBox> list = form.getChildren().stream().filter(c -> c instanceof GNTextBox)
                .map(c -> ((GNTextBox) c)).filter(c -> !c.isValid()).toList();

        if (!passwordBox.getText().equals(passwordBox2.getText())) {
            passwordBox2.setValid(false);
            Animations.errorOnTextBox(passwordBox2);
        }

        if (list.size() > 0 ) {
            list.forEach(Animations::errorOnTextBox);
        } else {
            Presenter<User> presenter = new UserPresenter();
            presenter.store(user);
            if (presenter.persist()) window.navigate("sales");
            ;
            properties.setProperty("app.registered", "true");
        }

    }

    @Override
    protected void updateLayout(double width) {
        layout.getColumnConstraints().clear();
        layout.getRowConstraints().clear();

        if (width <= 765) {
            System.out.println("updating");
            update(imgWrapper, 0, 1);
            update(content, 0,0);
            window.setTitle("SpeedCut");
        } else {
            update(imgWrapper, 0,0, Pos.CENTER_LEFT);
            update(content, 1,0, Pos.CENTER);
            window.setTitle(null);
        }
    }

    @Override
    public void onEnter() {
        super.onEnter();
        System.out.println("new On Enter");
    }
}
