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

package io.github.gleidsonmt.speedcut.controller.form;

import io.github.gleidsonmt.gncontrols.material.icon.Icons;
import io.github.gleidsonmt.speedcut.controller.sales.aside.SideHeaderNavigation;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.ActionView;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  21/08/2022
 */
public class PictureSelectorB implements ActionView, Context, Initializable {

    @FXML private AnchorPane boxSelector;
    @FXML private AnchorPane boxContainer;
    @FXML private VBox body;
    @FXML private VBox boxLight;

    @FXML private VBox wrapper;
    @FXML private ImageView imageView;
    @FXML private StackPane root;

    @FXML private Path top_left;
    @FXML private Path bottom_right;

    @FXML private Circle pointer;

    private double initX;
    private double initY;

    private double newX;
    private double newY;

    private double deltaX;
    private double deltaY;

    private static final int MIN_PIXELS = 10;

    private SideHeaderNavigation sideHeaderNavigation;

    private ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();

    private DoubleProperty maxWidth = new SimpleDoubleProperty();

    @FXML
    void startScroll(ScrollEvent event) {
        System.out.println("start scroll");
    }

    @FXML
    private void getImageCord(@NotNull MouseEvent event) {
        Point2D mousePress = imageViewToImage(imageView, new Point2D(event.getX(), event.getY()));
        mouseDown.set(mousePress);
    }

    @FXML
    private void draggImage(@NotNull MouseEvent event) {
        Point2D dragPoint = imageViewToImage(imageView, new Point2D(event.getX(), event.getY()));
        shift(imageView, dragPoint.subtract(mouseDown.get()));
        mouseDown.set(imageViewToImage(imageView, new Point2D(event.getX(), event.getY())));
    }

    // shift the viewport of the imageView by the specified delta, clamping so
    // the viewport does not move off the actual image:
    private void shift(@NotNull ImageView imageView, @NotNull Point2D delta) {

        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth() ;
        double height = imageView.getImage().getHeight() ;

        double maxX = width - viewport.getWidth();
        double maxY = height - viewport.getHeight();

        double minX = clamp(viewport.getMinX() - delta.getX(), 0, maxX);
        double minY = clamp(viewport.getMinY() - delta.getY(), 0, maxY);

        if (minY < 0) return;

        imageView.setViewport(new Rectangle2D(minX, minY, viewport.getWidth(), viewport.getHeight()));

    }

    @FXML
    private void scroll(@NotNull ScrollEvent event) {

//        if (!event.getTarget().equals(boxContainer) || !event.getTarget().equals(boxLight)) return;

        System.out.println("scrolling");

        double delta = event.getDeltaY();
        Rectangle2D viewport = imageView.getViewport();

        double width = imageView.getImage().getWidth();
        double height = imageView.getImage().getHeight();


        double scale = clamp(Math.pow(1.01, delta),

                // don't scale so we're zoomed in to fewer than MIN_PIXELS in any direction:
                Math.min(MIN_PIXELS / viewport.getWidth(), MIN_PIXELS / viewport.getHeight()),

                // don't scale so that we're bigger than image dimensions:
                Math.max(width / viewport.getWidth(), height / viewport.getHeight())

        );

        Point2D mouse = imageViewToImage(imageView, new Point2D(event.getX(), event.getY()));

        double newWidth = viewport.getWidth() * scale;
        double newHeight = viewport.getHeight() * scale;

        // To keep the visual point under the mouse from moving, we need
        // (x - newViewportMinX) / (x - currentViewportMinX) = scale
        // where x is the mouse X coordinate in the image

        // solving this for newViewportMinX gives

//         newViewportMinX = event.getX() - (x - currentViewportMinX) * scale;

        // we then clamp this value so the image never scrolls out
        // of the imageview:

//        System.out.println("eve = " + eve);
//
//        if (newWidth >= width) return;


        double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                0, width - newWidth);

        double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                0, height - newHeight);

//        if (newMinY < 0) {
//            newMinY = 0;
//        }

//        System.out.println("newWidth = " + newWidth);
//        System.out.println("maxWidth = " + maxWidth.get());

//        if (newWidth > maxWidth.get()) {
//            newWidth = maxWidth.get();
//        }


//        Bounds bounds = imageView.getBoundsInParent();
//
//        if (bounds.getMinX() > newMinX) {
//            newMinX = bounds.getMinX();
//        }

        imageView.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));

    }

    // convert mouse coordinates in the imageView to coordinates in the actual image:
    private @NotNull Point2D imageViewToImage(@NotNull ImageView imageView, @NotNull Point2D imageViewCoordinates) {

        double xProportion = imageViewCoordinates.getX() / imageView.getBoundsInParent().getWidth();
        double yProportion = imageViewCoordinates.getY() / imageView.getBoundsInParent().getHeight();
//
        Rectangle2D viewport = imageView.getViewport();

        return new Point2D(
                viewport.getMinX() + xProportion * viewport.getWidth(),
                viewport.getMinY() + yProportion * viewport.getHeight());
    }


    @FXML
    void endScroll(ScrollEvent event) {
        System.out.println("end scroll" );
    }

    private double clamp(double value, double min, double max) {

        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;

    }


    private void reset(@NotNull ImageView imageView, double width, double height) {
        imageView.setViewport(new Rectangle2D(0, 0, width, height));
    }

    @FXML
    private void moveXY(@NotNull MouseEvent event) {

        if (event.getTarget() instanceof Path) return;
//
        if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
            return;
        }
//
        if (event.isStillSincePress()) {
            return;
        }

        AnchorPane.clearConstraints(boxSelector);

        newX = event.getScreenX();
        newY = event.getScreenY();

        deltaX = newX - initX;
        deltaY = newY - initY;

        Bounds bounds = imageView.getLayoutBounds();

        double padX = imageView.getLocalToParentTransform().getTx();
        double padY = imageView.getLocalToParentTransform().getTy();

//        boolean maxX = (deltaX + boxSelector.getWidth()) < 401;
//        boolean maxY = (deltaY + boxSelector.getHeight()) < 401 ;
        boolean maxX = (deltaX + boxSelector.getWidth()) < (bounds.getWidth() ) + (padX + 2);
        boolean maxY = (deltaY + boxSelector.getHeight()) < (bounds.getHeight() ) + (padY + 2);

        if (deltaX > (padX-2) && maxX)
            boxSelector.setLayoutX( deltaX);

        if (deltaY > (padY-2) && maxY)
            boxSelector.setLayoutY( deltaY );

    }

    @FXML
    private void getBoxInitial(@NotNull MouseEvent event) {

        if (event.getTarget() instanceof Path) return; // dont use the path or update

        initX = (event.getScreenX() ) - (boxSelector.getLocalToParentTransform().getTx());
        initY = (event.getScreenY() ) - (boxSelector.getLocalToParentTransform().getTy());

    }

    @FXML
    private void fixedOnTopLeft(MouseEvent event) {

        getInitialCordinates(event);
        AnchorPane.clearConstraints(boxSelector);

        double _minX =  boxSelector.getLocalToParentTransform().getTx();
        double _minY =  boxSelector.getLocalToParentTransform().getTy();

        double _maxX = imageView.getFitWidth()- (_minX + boxSelector.getWidth());
        double _maxY = imageView.getFitWidth()- (_minY + boxSelector.getHeight());

        AnchorPane.setRightAnchor(boxSelector, _maxX );
        AnchorPane.setBottomAnchor(boxSelector, _maxY );

    }

    @FXML
    private void resizeFromTopLeft(@NotNull MouseEvent event) {

        if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
            return;
        }
//
        if (event.isStillSincePress()) {
            return;
        }

        newX = event.getScreenX();
        newY = event.getScreenY();

        deltaX = newX - initX;
        deltaY = newY - initY;

        double padTop = boxSelector.getLocalToParentTransform().getTy();
        double padLef = boxSelector.getLocalToParentTransform().getTx();

        // define tehe border delimiter
        boolean maxY = ( deltaY +  (padTop )) > -2;
        boolean maxX = ( deltaX +  (padLef )) > -2;

        if (maxX && maxY) {
            setWidth(boxSelector.getPrefWidth() - deltaX);
            setHeight(boxSelector.getPrefHeight() - deltaX);
        }

    }

    @FXML
    private void fixedOnTopRight(MouseEvent event) {

        getInitialCordinates(event);
        AnchorPane.clearConstraints(boxSelector);

        double _minX =  boxSelector.getLocalToParentTransform().getTx();
        double _minY =  boxSelector.getLocalToParentTransform().getTy();

        double _maxY = imageView.getFitWidth()- (_minY + boxSelector.getHeight());

        AnchorPane.setLeftAnchor(boxSelector, _minX );
        AnchorPane.setBottomAnchor(boxSelector, _maxY );

    }

    @FXML
    private void resizeFromTopRight(@NotNull MouseEvent event) {

        if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
            return;
        }

        if (event.isStillSincePress()) {
            return;
        }

        newX = event.getScreenX();
        newY = event.getScreenY();

        deltaX = newX - initX;
        deltaY = newY - initY;

        // gettting paddings
        double padTop = boxSelector.getLocalToParentTransform().getTy();
        double padLef = boxSelector.getLocalToParentTransform().getTx();

        // define tehe border delimiter
        boolean maxY = (deltaY + (padTop )) >= -1;
        boolean maxX = (deltaX + ( boxSelector.getPrefWidth() + padLef)) < 401;

        if (maxX && maxY) {
            setHeight(boxSelector.getPrefHeight() + deltaX);
            setWidth(boxSelector.getPrefWidth() + deltaX);
        }

    }

    @FXML
    private void fixedOnBottomLeft(MouseEvent event) {

        getInitialCordinates(event);
        AnchorPane.clearConstraints(boxSelector);

        double _minX = boxSelector.getLocalToParentTransform().getTx();
        double _minY = boxSelector.getLocalToParentTransform().getTy();

        double _maxX = imageView.getFitWidth()- (_minX + boxSelector.getWidth());

        AnchorPane.setRightAnchor( boxSelector, _maxX );
        AnchorPane.setTopAnchor( boxSelector, _minY );

    }

    @FXML
    private void resizeFromBottomLeft(@NotNull MouseEvent event) {

        if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
            return;
        }

        if (event.isStillSincePress()) {
            return;
        }

        newX = event.getScreenX();
        newY = event.getScreenY();

        deltaX = newX - initX;
        deltaY = newY - initY;

        // gettting paddings
        double padTop = boxSelector.getLocalToParentTransform().getTy();
        double padLef = boxSelector.getLocalToParentTransform().getTx();

        // define tehe border delimiter
        boolean maxY = (deltaY + ( boxSelector.getPrefHeight() + padTop)) < 401;
        boolean maxX = ( deltaX +  padLef ) > -1;

        if (maxX && maxY) {
            setWidth(boxSelector.getPrefWidth() - deltaX);
            setHeight(boxSelector.getPrefHeight() - deltaX);
        }

    }

    @FXML
    private void fixedOnBottomRight(MouseEvent event) {

        getInitialCordinates(event);
        AnchorPane.clearConstraints(boxSelector);

        double padTop = boxSelector.getLocalToParentTransform().getTy();
        double padLef = boxSelector.getLocalToParentTransform().getTx();

        //+8 is to subtract bettween path padding + border width (-10 + 2)
        double _minX = boxSelector.getLocalToParentTransform().getTx() ;
        double _minY = boxSelector.getLocalToParentTransform().getTy() ;

        AnchorPane.setTopAnchor(boxSelector, _minY );
        AnchorPane.setLeftAnchor(boxSelector, _minX );

    }

    @FXML
    private void resizeFromBottomRight(@NotNull MouseEvent event) {

//         if mouse be outside
        if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
            return;
        }

        if (event.isStillSincePress()) { // if dragged don't update
            return;
        }

        newX = event.getScreenX();
        newY = event.getScreenY();

        deltaX = newX - initX;
        deltaY = newY - initY;

        double padTop = boxSelector.getLocalToParentTransform().getTy();
        double padLef = boxSelector.getLocalToParentTransform().getTx();

        double height = imageView.getLayoutBounds().getHeight();
        double width = imageView.getLayoutBounds().getWidth();

//        System.out.println("imageView = " + width);
//        System.out.println("imageView = " + height);

        boolean maxY = (deltaY + ( boxSelector.getPrefHeight() + padTop)) < height + 2 ;
        boolean maxX = (deltaX + (boxSelector.getPrefWidth() + padLef)) < width + 2 ;
//        boolean maxY = (deltaY + ( boxSelector.getPrefHeight() + padTop)) < 401 ;
//        boolean maxX = (deltaX + (boxSelector.getPrefWidth() + padLef)) < 401 ;


        if (maxY && maxX) {
            setHeight(boxSelector.getPrefHeight() + deltaX);
            setWidth(boxSelector.getPrefWidth() + deltaX);
        }
    }

    @FXML
    private void doubleClick(@NotNull MouseEvent event) {

//        if (event.getClickCount() < 2) return;
//
//        AnchorPane.clearConstraints(boxSelector);
//
//        boolean max = boxSelector.getPrefWidth() > 399;
//
//        boxSelector.setPrefSize(max ? (imageView.getFitWidth()/ 2D) : wrapper.getWidth(), max ? (imageView.getFitWidth()/ 2D) : wrapper.getWidth());
//
//        AnchorPane.setTopAnchor(boxSelector, max ? 100D : 0D);
//
//        AnchorPane.setLeftAnchor(boxSelector, max ? 100D : 0D);

    }

    @FXML
    private void getInitialCordinates(@NotNull MouseEvent event) {

        if (event.getTarget() instanceof Path) {
            initX = event.getScreenX() ;
            initY = event.getScreenY() ;
        } else {
            initX = (event.getScreenX()) - (boxSelector.getLocalToParentTransform().getTx());
            initY = (event.getScreenY()) - (boxSelector.getLocalToParentTransform().getTy());
        }

    }

    private void setWidth(double width) {
        if (width >= boxSelector.getMinWidth()) {
            boxSelector.setPrefWidth(width);
            initX = newX;
        }
    }

    private void setHeight(double height) {
        if (height >= boxSelector.getMinHeight()) {
            boxSelector.setPrefHeight(height);
            initY = newY;
        }
    }

    @Override
    public void onEnter() {
        AnchorPane.clearConstraints(boxSelector);
        reset(imageView, 400, 400);

//
    }

    @Override
    public void onExit() {

    }

    public void setImage(Image image) {
        imageView.setImage(image);

        Bounds bounds = imageView.getLayoutBounds();
        Bounds boundsInParent = imageView.getBoundsInParent();

        System.out.println("imageView.getLocalToParentTransform().getTx()  = " + imageView.getLocalToParentTransform().getTx() );

        System.out.println("bounds = " + bounds);
        System.out.println("boundsInParent = " + boundsInParent);
        System.out.println("imageView.getLocalToParentTransform() = " + imageView.getLocalToParentTransform());

//        double padLef = wrapper.getWidth()

        AnchorPane.setTopAnchor(boxSelector, boundsInParent.getMinY());
        AnchorPane.setLeftAnchor(boxSelector,  boundsInParent.getMinX() );

        boxSelector.setPrefSize(
                Math.min(bounds.getHeight() /2, bounds.getWidth() / 2),
                Math.min(bounds.getHeight() /2, bounds.getWidth() / 2)
        );

    }

    public void setAvatarImage() {
//        imageView.setClip(boxSelector);

        SnapshotParameters params = new SnapshotParameters();
//        params.camera = camera == null ? null : camera.copy();
//        params.depthBuffer = depthBuffer;
//        params.fill = fill;
//        params.viewport = viewport;
//        params.transform = transform == null ? null : transform.clone();
//        return params;

//        params.setTransform(boxSelector.getLocalToParentTransform());

        double _minX = boxSelector.getLocalToParentTransform().getTx();
        double _minY = boxSelector.getLocalToParentTransform().getTy();
        double _width = boxSelector.getPrefWidth();
        double _hegth = boxSelector.getPrefHeight();

        System.out.println("_minY = " + _minY);

//        imageView.setViewport(boxSelector.getBoundsInParent());
        params.setViewport(new Rectangle2D(_minX, _minY, _width, _hegth));

        System.out.println("params = " + params);

//        imageView.setEffect(null);

        imageView.setEffect(null);
        WritableImage wImage = new WritableImage((int) _width, (int) _hegth);
        WritableImage image = imageView.snapshot(params, wImage);
        imageView.setEffect(new ColorAdjust(0,0,-0.50 ,0));


        FormClientController controller = (FormClientController) context.getRoutes().getView("form_client").getController();

        controller.setAvatar(image);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sideHeaderNavigation = new SideHeaderNavigation(Icons.DRAW, "Recorte seu novo avatar.", false);
        body.getChildren().add(0, sideHeaderNavigation);

//        imageView.fitWidthProperty().bind(body.widthProperty());

        maxWidth.bind(
                context.getDecorator().getRoot().widthProperty().subtract(
                        context.getDecorator().getRoot().getWidth() / 2
                ).subtract(context.getDecorator().getRoot().getLayout().drawerWidthProperty())
        );

        imageView.fitWidthProperty().bind(
                context.getDecorator().getRoot().widthProperty().subtract(
                        context.getDecorator().getRoot().getWidth() / 2
                ).subtract(
                        context.getDecorator().getRoot().getLayout().hasDrawerProperty().get() ?
                        context.getDecorator().getRoot().getLayout().drawerWidthProperty().get() :
                        0D
                )
        );



        imageView.fitHeightProperty().bind(
                        context.getDecorator().getRoot().heightProperty().subtract(100)
        );


//        imageView.setOnScroll(new EventHandler<ScrollEvent>() {
//            @Override
//            public void handle(ScrollEvent event) {
//                System.out.println("fuck you");
//            }
//        });

    }
}
