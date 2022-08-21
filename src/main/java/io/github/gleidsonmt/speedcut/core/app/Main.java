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

import io.github.gleidsonmt.speedcut.core.app.view.intefaces.IApp;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class Main extends Application implements IApp {

    @Override
    public void start(Stage stage) {
        runApp(getHostServices());
    }

    @Override
    public void stop() {
        context.getProperties().stringPropertyNames().forEach(f -> context.getProperties().setProperty(f, context.getProperties().getProperty(f)));

        try {
            context.getProperties().store(new FileOutputStream("src/main/resources/"+ context.getPaths().getCore() + "app.properties"), "Updating properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void runApp(HostServices hostServices) {
            context.startApp(hostServices);

//        if (!hasInstance())
//        else throw new RuntimeException("Can't have only instance of app.");


    }
}

