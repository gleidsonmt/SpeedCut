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

import io.github.gleidsonmt.gncontrols.GNTextBox;
import io.github.gleidsonmt.speedcut.core.app.view.Form;
import io.github.gleidsonmt.speedcut.core.app.view.ResponsiveView;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  24/02/2022
 */
public class FormController extends ResponsiveView {

    @FXML private ScrollPane scroll;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Form form = new Form();
//        form.setGridLinesVisible(true);

        GNTextBox textBox1 = new GNTextBox("Vai ");
        textBox1.setFloatPrompt(true);
        textBox1.setPromptText("Prompt Text");
        GNTextBox textBox2 = new GNTextBox("Vai ");
        textBox2.setFloatPrompt(true);
        textBox2.setPromptText("Prompt Text");
        GNTextBox textBox3 = new GNTextBox("Vai ");
        textBox3.setFloatPrompt(true);
        textBox3.setPromptText("Prompt Text");
        GNTextBox textBox4 = new GNTextBox("Vai ");
        textBox4.setFloatPrompt(true);
        textBox4.setPromptText("Prompt Text");

        form.setVgap(10);
        form.setHgap(10);

        form.getChildren().addAll(
                textBox1, textBox2, textBox3, textBox4
        );

        GridPane.setConstraints(textBox1, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
        GridPane.setConstraints(textBox2, 1, 0, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
        GridPane.setConstraints(textBox3, 0, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);
        GridPane.setConstraints(textBox4, 1, 1, 1, 1, HPos.LEFT, VPos.CENTER, Priority.ALWAYS, Priority.NEVER);


        StackPane layout = new StackPane(form);
        layout.setPadding(new Insets(10));
        scroll.setContent(layout);

        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);

    }

    @Override
    protected void updateLayout(double width) {

    }

}
