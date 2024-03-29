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

package io.github.gleidsonmt.speedcut.core.app.layout;

import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IView;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Parent;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  20/08/2022
 */
@SuppressWarnings("unused")
public interface ILayout {

    void setDrawer(IView iView);

    void setAside(IView iView);

    void setNav(IView iView);

    void setFooter(IView iView);

    ReadOnlyDoubleProperty drawerWidthProperty();

    ReadOnlyBooleanProperty hasDrawerProperty();

}
