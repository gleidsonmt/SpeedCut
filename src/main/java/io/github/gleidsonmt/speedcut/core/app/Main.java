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
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class Main extends Application implements IApp {


    private static final String IMAGE_CREDIT_URL = "http://www.nasa.gov/image-feature/global-mosaic-of-pluto-in-true-color";
    private static final String IMAGE_URL = "https://www.nasa.gov/sites/default/files/thumbnails/image/global-mosaic-of-pluto-in-true-color.jpg";
    private static final int MIN_PIXELS = 10;

    @Override
    public void start(Stage stage) {
        runApp(getHostServices());
//
//        var fileChooser = new FileChooser();
//        File file = fileChooser.showOpenDialog(stage);
//        if (file != null) {
//            Image image = null;
//            try {
//                image = new Image(new FileInputStream(file));
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            var view = new ImageView(image);
//            view.setPreserveRatio(true);
//            view.setOnMouseEntered(e -> {
//                view.setCursor(Cursor.OPEN_HAND);
//            });
//            view.setOnMousePressed(e -> {
//                view.setCursor(Cursor.CLOSED_HAND);
//            });
//            view.setOnMouseReleased(e -> {
//                view.setCursor(Cursor.OPEN_HAND);
//            });
//            view.setOnMouseExited(e -> {
//                view.setCursor(Cursor.DEFAULT);
//            });
//            var sp = new ScrollPane(view);
//            sp.setPannable(true);
//            sp.setHvalue(0.5);
//            sp.setVvalue(0.5);
//
//            var slider = new Slider(1.0, 3.0, 1.0);
//            slider.setBlockIncrement(0.1);
//            Image finalImage = image;
//            slider.valueProperty().addListener((o, oldV, newV) -> {
//                var x = sp.getHvalue();
//                var y = sp.getVvalue();
//                view.setFitWidth(finalImage.getWidth() * newV.doubleValue());
//                sp.setHvalue(x);
//                sp.setVvalue(y);
//            });
//
//            var root = new BorderPane(sp);
//            root.setBottom(slider);
//            stage.setScene(new Scene(root, 320, 240));
//            stage.show();
//        }


//            Image image = new Image(IMAGE_URL);
//            double width = image.getWidth();
//            double height = image.getHeight();
//
//            ImageView imageView = new ImageView(image);
//            imageView.setPreserveRatio(true);
//
//            reset(imageView, width / 2, height / 2);
//
//            ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();
//
//            imageView.setOnMousePressed(e -> {
//
//                Point2D mousePress = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
//                mouseDown.set(mousePress);
//
//            });
//
//            imageView.setOnMouseDragged(e -> {
//                Point2D dragPoint = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
//                shift(imageView, dragPoint.subtract(mouseDown.get()));
//                mouseDown.set(imageViewToImage(imageView, new Point2D(e.getX(), e.getY())));
//            });
//
//            imageView.setOnScroll(e -> {
//                double delta = e.getDeltaY();
//                Rectangle2D viewport = imageView.getViewport();
//
//                double scale = clamp(Math.pow(1.01, delta),
//
//                        // don't scale so we're zoomed in to fewer than MIN_PIXELS in any direction:
//                        Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),
//
//                        // don't scale so that we're bigger than image dimensions:
//                        Math.max(width / viewport.getWidth(), height / viewport.getHeight())
//
//                );
//
//                Point2D mouse = imageViewToImage(imageView, new Point2D(e.getX(), e.getY()));
//
//                double newWidth = viewport.getWidth() * scale;
//                double newHeight = viewport.getHeight() * scale;
//
//                // To keep the visual point under the mouse from moving, we need
//                // (x - newViewportMinX) / (x - currentViewportMinX) = scale
//                // where x is the mouse X coordinate in the image
//
//                // solving this for newViewportMinX gives
//
//                // newViewportMinX = x - (x - currentViewportMinX) * scale
//
//                // we then clamp this value so the image never scrolls out
//                // of the imageview:
//
//                double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
//                        30, width - newWidth);
//                double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
//                        0, height - newHeight);
//
//                imageView.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
//            });
//
//            imageView.setOnMouseClicked(e -> {
//                if (e.getClickCount() == 2) {
//                    reset(imageView, width, height);
//                }
//            });
//
//            Hyperlink link = new Hyperlink("Image Credit: NASA/JHUAPL/SwRI");
//            link.setOnAction(e -> getHostServices().showDocument(IMAGE_CREDIT_URL));
//
//            link.setPadding(new Insets(10));
//            link.setTooltip(new Tooltip(IMAGE_CREDIT_URL));
//
//            HBox buttons = createButtons(width, height, imageView);
//            Tooltip tooltip = new Tooltip("Scroll to zoom, drag to pan");
//            Tooltip.install(buttons, tooltip);
//
//            Pane container = new Pane(imageView);
//            container.setPrefSize(800, 600);
//
//            imageView.fitWidthProperty().bind(container.widthProperty());
//
//            imageView.fitHeightProperty().bind(container.heightProperty());
//            VBox root = new VBox(link, container, buttons);
//            root.setFillWidth(true);
//            VBox.setVgrow(container, Priority.ALWAYS);
//
//            stage.setScene(new Scene(root));
//            stage.setTitle("Pluto explorer");
//            stage.show();


    }


    private HBox createButtons(double width, double height, ImageView imageView) {
        Button reset = new Button("Reset");
        reset.setOnAction(e -> reset(imageView, width / 2, height / 2));
        Button full = new Button("Full view");
        full.setOnAction(e -> reset(imageView, width, height));
        HBox buttons = new HBox(10, reset, full);
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10));
        return buttons;
    }


    private void reset(ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }


    // shift the viewport of the imageView by the specified delta, clamping so
    // the viewport does not move off the actual image:
    private void shift(ImageView imageView, Point2D delta) {
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));
    }

    private double clamp(double value, double min, double max) {

        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }


    // convert mouse coordinates in the imageView to coordinates in the actual image:
    private Point2D imageViewToImage(ImageView imageView, Point2D imageViewCoordinates) {
        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInLocal().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInLocal().getHeight();

        Rectangle2D viewport = imageView.getViewport();
        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
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

