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

package io.github.gleidsonmt.speedcut.core.app.view.intefaces;

import io.github.gleidsonmt.speedcut.core.app.layout.ILayout;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.IWrapper;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.SnackBar;
import io.github.gleidsonmt.speedcut.core.app.layout.containers.Wrapper;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Parent;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  20/08/2022
 */

@SuppressWarnings("all")
public interface IRoot {

    void setTitle(String title);

    SnackBar createSnackBar();

    IWrapper getWrapper();

    ILayout getLayout();

    void setContent(Parent content);

    double getWidth();

    double getHeight();

    ReadOnlyDoubleProperty widthProperty();

    ReadOnlyDoubleProperty heightProperty();

}
