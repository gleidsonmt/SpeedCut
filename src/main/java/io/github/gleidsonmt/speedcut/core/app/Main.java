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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.*;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 * The class used to default operations about running the app
 * Start and Stop actions.
 */
public class Main extends Application implements IApp {

    private final Logger logger = Logger.getLogger("app");
    private FileHandler fileHandler;

    {
        try {
            fileHandler = new FileHandler("speed.txt");
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {

                    String _logger = record.getLoggerName();
                    String level = record.getLevel().getLocalizedName();
                    String message = record.getMessage();
                    String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, HH:mm"));

                    String _class = record.getSourceClassName();

                    String _method = record.getSourceMethodName();

                    return "\n" + "Logger { " + _logger + " }\n" +
                            "Date and Time " + date + " => Class [" + _class + "]\n" +
                            "Method -> " + _method + "\n" +
                            "["+ level +"] Message -> \"" + message +"\"\n";
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage stage) {
        logger.addHandler(fileHandler);
        runApp(getHostServices());
    }

    @Override
    public void stop() {
        
        fileHandler.flush();
        fileHandler.close();

        context.getProperties().stringPropertyNames().forEach(f -> context.getProperties().setProperty(f, context.getProperties().getProperty(f)));

        try {
            context.getProperties().store(new FileOutputStream("src/main/resources/"+ context.getPaths().getFromCore("app.properties")  ), "Updating properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void runApp(HostServices hostServices) {
        context.startApp(hostServices);
    }
}

