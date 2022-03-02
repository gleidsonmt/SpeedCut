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

import java.io.IOException;
import java.util.Properties;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class AppProperties extends Properties implements Global {

    AppProperties() {
        try {
            load(getClass().
                    getResourceAsStream(corePath + "app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public boolean isLogged() {
        return Boolean.parseBoolean(getProperty("app.logged"));
    }

    public boolean isRegistered() {
        return Boolean.parseBoolean(getProperty("app.registered"));
    }

}
