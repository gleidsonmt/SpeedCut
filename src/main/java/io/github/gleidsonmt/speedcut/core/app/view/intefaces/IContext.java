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

import io.github.gleidsonmt.speedcut.core.app.model.Cashier;
import io.github.gleidsonmt.speedcut.core.app.model.User;
import io.github.gleidsonmt.speedcut.core.app.view.WindowDecorator;
import javafx.application.HostServices;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Parent;

import java.util.Properties;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  19/08/2022
 */
@SuppressWarnings({"unexported", "ClassEscapesDefinedScope"})
public interface IContext {

    @Deprecated
    WindowDecorator getWindow();

    IDecorator getDecorator();

    Cashier getCashier();

    @Deprecated
    ActionView getControlller(String view);

    Parent getRoot(String view);

    IView workAndGet(String view);

    IView workAndLeave() ;

    @Deprecated
    IView getPrevious();

    User getUser();

    void setUser(User user);

    AppPaths getPaths();

    IRotes getRoutes();

    void openLink(String link);

    Properties getProperties();

    void startApp(HostServices hostServices);

}
