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

package io.github.gleidsonmt.speedcut;

import io.github.gleidsonmt.speedcut.core.app.dao.RepoClientImpl;
import io.github.gleidsonmt.speedcut.core.app.model.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  18/08/2022
 */
public class Sample extends Application {

    @Override
    public void start(Stage stage) throws Exception {

//        RepoClientImpl repoClient = new RepoClientImpl();
//
//        Client model = repoClient.read(2);
//
//
//
////        StackPane root = new StackPane(model.getAvatar());
//
//        stage.setScene(new Scene(root, 600, 600));
//        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}