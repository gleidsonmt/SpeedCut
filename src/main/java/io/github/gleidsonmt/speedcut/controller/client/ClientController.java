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

package io.github.gleidsonmt.speedcut.controller.client;

import io.github.gleidsonmt.speedcut.core.app.factory.AvatarCreator;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.ActionView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  06/08/2022
 */
public class ClientController implements ActionView, Initializable {


    @FXML
    private Circle avatarCirlce;

    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String path = "/io.github.gleidsonmt.speedcut.core.app/";
        String imgPath = Objects.requireNonNull(AvatarCreator.class.getResource(path + "theme/img/avatars/man@400.png")).toExternalForm();
        Image image = new Image(imgPath, 200, 0, true, true);

//        circle.setRadius(18);
        avatarCirlce.setStrokeWidth(2);
        avatarCirlce.setStroke(Color.WHITE);
//        avatarCirlce.setEffect(new DropShadow(5, Color.gray(0.8)));

        avatarCirlce.setFill(new ImagePattern(image));

    }
}
