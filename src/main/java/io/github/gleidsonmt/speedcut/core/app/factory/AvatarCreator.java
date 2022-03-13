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

package io.github.gleidsonmt.speedcut.core.app.factory;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextAlignment;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/03/2022
 */
public class AvatarCreator extends Label {



    public AvatarCreator(String name) {
        List<String> colors = Arrays.asList(
                "#4FC1E9", "#48CFAD", "#AA66CC",
                "#FFA000", "#ED5565");
        setText(name.substring(0,1));

        Random random = new Random();

        setPrefSize(35, 35);
        setContentDisplay(ContentDisplay.CENTER);
        setTextAlignment(TextAlignment.CENTER);
        setAlignment(Pos.CENTER);

        setId("custom-avatar");

        setStyle("-base : " +
                colors.get(random.nextInt(colors.size())) +";");
    }

    public AvatarCreator(String name, String style) {
        setMouseTransparent(true);
        setText(name.substring(0,1));

        setPrefSize(35, 35);
        setContentDisplay(ContentDisplay.CENTER);
        setTextAlignment(TextAlignment.CENTER);
        setAlignment(Pos.CENTER);

        setId("custom-avatar");

        setStyle(style);
    }

}
