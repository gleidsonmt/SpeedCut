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

package io.github.gleidsonmt.speedcut.core.app.view;

import io.github.gleidsonmt.speedcut.core.app.exceptions.ControllerCastException;
import javafx.scene.Parent;

import java.net.URL;
import java.nio.charset.Charset;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  05/02/2022
 */
public interface IView {

    String getName();

    ViewComposer getComposer();

    ActionViewController getController() ;

    Parent getRoot();

    Charset getCharset();

    URL getLocation();
}
