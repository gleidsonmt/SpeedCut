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

package io.github.gleidsonmt.speedcut.core.app.cursores;

import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  30/08/2022
 * Excluir sem problemas
 */

public class AppCursores implements Context {

    private static final String EXTENSION = ".png";

    public static final ImageCursor ZOOM_IN = new ImageCursor(
            new Image(
                    Objects.requireNonNull(AppCursores.class.getResource(context.getPaths().getCursores() + "zoom_in" + EXTENSION)).toExternalForm()

//                    ,60, 60, true, true
            )
    );


}
