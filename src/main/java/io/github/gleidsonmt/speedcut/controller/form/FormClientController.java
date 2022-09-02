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

package io.github.gleidsonmt.speedcut.controller.form;

import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.gncontrols.skin.TextBox;
import io.github.gleidsonmt.speedcut.controller.sales.aside.SideHeaderNavigation;
import io.github.gleidsonmt.speedcut.controller.sales.main.SalesController;
import io.github.gleidsonmt.speedcut.core.app.dao.Repositories;
import io.github.gleidsonmt.speedcut.core.app.dao.Repository;
import io.github.gleidsonmt.speedcut.core.app.model.Client;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.ActionView;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IView;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/08/2022
 */
public class FormClientController implements Initializable, ActionView, Context {

    @FXML private StackPane root;
    @FXML private VBox body;
    @FXML private GNTextBox tfName;
    @FXML private Circle avatarCircle;

    private Client client;
    private SideHeaderNavigation sideHeaderNavigation;

    boolean pressSaved = false;

    private List<TextBox> form = FXCollections.observableArrayList();
    private BooleanProperty formValid = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sideHeaderNavigation = new SideHeaderNavigation(Icons.PERSON_ADD, "Adicione um novo cliente.");
        body.getChildren().add(0, sideHeaderNavigation);

//        txtObs.setValid();

        avatarCircle.setFill(new ImagePattern(
                new Image(Objects.requireNonNull(getClass().getResource(context.getPaths().getAvatars() + "woman@400.png")).toExternalForm())
        ));

//        SnapshotView snapshotView = new SnapshotView();

    }

    @FXML
    private void openFileChooser() {

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

//        double width = Math.min(image.getWidth(), (context.getDecorator().getWidth() / 2));
//        double width = image.getWidth();
//        double height = context.getDecorator().getHeight() - 50;

        context .getDecorator()
                .getRoot()
                .getWrapper()
                .getPopup()
//                .size( width, height )
                .onEnter(event -> {
                    viewController.setImage(image);
                    viewController.onEnter();
                })
                .onExit(event -> setAvatar(viewController.getImage()))
                .alignment(Pos.CENTER)
                .content(view.getRoot())
                .show();

    }

    public void setAvatar(Image image) {
        avatarCircle.setFill(new ImagePattern(image));
    }



    @Override
    public void onEnter() {

        client = new Client();
        formValid.bind(tfName.validProperty());

    }

    @Override
    public void onExit() {

        tfName.textProperty().unbind();
        tfName.getEditor().clear();

    }

    @FXML
    private void save() {
        createBinds();

        if (!pressSaved) {
            createValidations();
        }
        pressSaved = true;

        putInDatabase();
    }

    private void putInDatabase() {
        Repository<Client> repo = Repositories.get(Client.class);
        repo.put(client);
        repo.persist();
    }

    private void createBinds() {
        client.nameProperty().bind(tfName.textProperty());
    }

    private void createValidations() {
        tfName.validProperty().bind(tfName.getEditor().lengthProperty().greaterThan(3));
    }
}
