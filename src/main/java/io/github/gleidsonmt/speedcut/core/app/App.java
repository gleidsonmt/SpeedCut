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

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.Console;
import java.io.Writer;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class App extends Application implements IApp {

    boolean hasInstance = false;

    @Override
    public void start(Stage stage) throws Exception {
        ObjectOne one = new ObjectOne();
        ObjectThree three = new ObjectThree();
        ObjectTwo two = new ObjectTwo();

        System.out.println(one.getName());
        System.out.println(two.getName());
        System.out.println(three.getName());


    }

    private void run() {

    }
}
