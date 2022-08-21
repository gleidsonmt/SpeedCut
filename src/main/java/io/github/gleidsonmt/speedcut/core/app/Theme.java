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

package io.github.gleidsonmt.speedcut.core.app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  20/08/2022
 */
public final class Theme {

    private final ObservableList<String> styleesheets = FXCollections.observableArrayList();

    private final String path;

    public Theme(String _path) {

        this.path = _path;

        styleesheets.addAll(
                Objects.requireNonNull(getClass().getResource(_path + "theme/fonts/fonts.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(_path + "theme/css/material-color.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(_path + "theme/css/light.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(_path + "theme/css/skeleton.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(_path + "theme/css/bootstrap.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(_path + "theme/css/shape.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(_path + "theme/css/typographic.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(_path + "theme/css/helpers.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(_path + "theme/css/master.css")).toExternalForm(),
                Objects.requireNonNull(getClass().getResource(_path + "theme/css/custom-scroll.css")).toExternalForm()
        );

    }

    public ObservableList<String> getStyleesheets() {
        return styleesheets;
    }

    @Contract(" -> new")
    public @NotNull Image getLogo() {
        return new Image(Objects.requireNonNull(getClass().getResource(path + "theme/img/logo_mini.png")).toExternalForm());
    }
}
