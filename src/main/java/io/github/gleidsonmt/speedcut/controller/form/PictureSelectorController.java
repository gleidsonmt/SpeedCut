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

import io.github.gleidsonmt.gncontrols.GNButton;
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
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Separator;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.net.URL;
import java.util.IllegalFormatCodePointException;
import java.util.ResourceBundle;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  21/08/2022
 */
public class PictureSelectorController implements ActionView, Context, Initializable {

    @FXML private Circle top_left;
    @FXML private Circle bottom_left;
    @FXML private GNButton btnLockInTop;

    @FXML private StackPane root;;
    @FXML private VBox body;
    @FXML private AnchorPane boxContainer;
    @FXML private AnchorPane boxSelector;
    @FXML private ImageView imageView;
    @FXML private VBox wrapper;

    @FXML private Circle miniView;
    @FXML private Circle circleView;
    @FXML private Rectangle rectView;

    private static final int MIN_PIXELS = 10;

    private double initX;
    private double initY;

    private double newX;
    private double newY;

    private double maxW;
    private double maxH;

    private double deltaX;
    private double deltaY;

    // Port for rectangle view
    private double viewWidth = 600;
    private double viewHeight = 400;


    private SideHeaderNavigation sideHeaderNavigation;

//    private final DoubleProperty viewMaxWidthPort = new SimpleDoubleProperty();
//    private final DoubleProperty viewMaxHeightPort = new SimpleDoubleProperty();

    private final ObjectProperty<Point2D> mouseDown = new SimpleObjectProperty<>();



    @Override
    public void onEnter() {

    }

    @Override
    public void onExit() {

    }

    @FXML
    private void scrollImage(@NotNull ScrollEvent event) {

        imageView.setCursor(Cursor.CROSSHAIR);

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

        double newMinX = clamp(mouse.getX() - (mouse.getX() - viewport.getMinX()) * scale,
                0, width - newWidth);

        double newMinY = clamp(mouse.getY() - (mouse.getY() - viewport.getMinY()) * scale,
                0, height - newHeight);

        if (newMinY <= 0) return;

        imageView.setViewport(new Rectangle2D(newMinX, newMinY, newWidth, newHeight));
    }

    @FXML
    private void updateCursor() {
        boxContainer.setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void getImageCord(@NotNull MouseEvent event) {

        if (event.getTarget() instanceof Circle) return;
        if (event.getTarget() instanceof Path) return;

        Point2D mousePress = imageViewToImage(imageView, new Point2D(event.getX(), event.getY()));
        mouseDown.set(mousePress);
        boxContainer.setCursor(Cursor.CROSSHAIR);
    }

    @FXML
    private void draggImage(@NotNull MouseEvent event) {

        if (event.getTarget() instanceof Circle) return;
        if (event.getTarget() instanceof Path) return;

        if (!event.isControlDown()) return;

        Point2D dragPoint = imageViewToImage(imageView, new Point2D(event.getX(), event.getY()));
        shift(imageView, dragPoint.subtract(mouseDown.get()));
        mouseDown.set(imageViewToImage(imageView, new Point2D(event.getX(), event.getY())));

        boxContainer.setCursor(Cursor.MOVE);
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
    private void closePopup() {
        context.getDecorator().getRoot().getWrapper().getPopup().close();
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

    private double clamp(double value, double min, double max) {

        if (value < min) return min;
        return Math.min(value, max);

    }

    public void setImage(Image image) {
        imageView.setImage(image);
        centerImage(image);
    }

    private boolean exited;

    @FXML
    private void onMouseExited() {
        exited = true;
    }

    @FXML
    private void onMouseEntered() {
//        top_left.setOnMouseDragged(this::resizeFromTopLeft);
        exited = false;
    }

    @FXML
    private void lockInTop() {
        AnchorPane.setTopAnchor(boxSelector, 0D);
        btnLockInTop.setVisible(false);
    }

    @FXML
    private void draggBoxSelector(@NotNull MouseEvent event) {

//        if (event.getTarget() instanceof Path) return;
////
//        if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) {
//            return;
//        }
////
//        if (event.isStillSincePress()) {
//            return;
//        }
//
//        AnchorPane.clearConstraints(boxSelector);
//
//        newX = event.getScreenX();
//        newY = event.getScreenY();
//
//        deltaX = newX - initX;
//        deltaY = newY - initY;
//
//        Bounds bounds = boxContainer.getLayoutBounds();
//
//        double padTop = boxSelector.getLocalToParentTransform().getTy() ;
//        double padLef = boxSelector.getLocalToParentTransform().getTx() ;
//
////        boolean maxX = (deltaX + boxSelector.getWidth()) < 401;
////        boolean maxY = (deltaY + boxSelector.getHeight()) < 401 ;
//        boolean maxX = (deltaX + boxSelector.getWidth()) < (boxContainer.getWidth() ) + (padLef + 2);
//        boolean maxY = (deltaY + boxSelector.getHeight()) < (boxContainer.getHeight() ) + (padTop + 2);
//
//        if (deltaX > (padLef-2) && maxX)
//            boxSelector.setLayoutX( deltaX);
//
//        if (deltaY > (padTop-2) && maxY)
//            boxSelector.setLayoutY( deltaY );

    }


    /**
     *  Get the initial cordinates and anchor for drag event.
     * @param event Mouse event for this event
     */
    @FXML
    private void fixedOnBottomLeft(MouseEvent event) {

//        btnLockInTop.setVisible(true);

        initY = clampPointerBottomY(event);
        initX = clampPointerForLeftX(event);

        clearConstraints(boxSelector);

        double _minX =  (boxSelector.getLocalToParentTransform().getTx()  ) ;
        double _minY =  (boxSelector.getLocalToParentTransform().getTy()  ) ;

        double _maxX = viewWidth - (_minX + boxSelector.getWidth());

        AnchorPane.setRightAnchor( boxSelector, _maxX );
        AnchorPane.setTopAnchor( boxSelector, _minY );

    }

    /**
     * Event drag from bottom left.
     * This event starts on the circle at the bottom.
     * @param event Mouse event for this event
     */
    @FXML
    private void resizeFromBottomLeft(@NotNull MouseEvent event) {

        if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) return; // if out of bounds and not the primary button(left)
        if (event.isStillSincePress()) return; // if the mouse doesn't continuos pressed

        // get the new x positions
        newX = event.getSceneX();
        newY = event.getSceneY();

        // get the difference from initial clicked to dragg
        deltaX = newX - initX;
        deltaY = newY - initY;

        // get the bounds for the box limit
        double maxY = (boxContainer.getLocalToSceneTransform().getTy() + boxContainer.getHeight()) -2;
        double maxX = (boxContainer.getLocalToSceneTransform().getTx() -2);

        // don't leave from the limit
        if ( newY < maxY )  setHeight(boxSelector.getPrefHeight() + deltaY);
        if ( newX > maxX )  setWidth (boxSelector.getPrefWidth()  - deltaX);

    }

    @FXML
    private void fixedOnBottomRight(MouseEvent event) {

//        btnLockInTop.setVisible(true);

        initY = clampPointerBottomY(event);
        initX = clampPointerForRightX(event);

        clearConstraints(boxSelector);
//
        double _minX =  (boxSelector.getLocalToParentTransform().getTx()  ) ;
        double _minY =  (boxSelector.getLocalToParentTransform().getTy()  ) ;

        AnchorPane.setRightAnchor( boxSelector, _minX );
        AnchorPane.setTopAnchor( boxSelector, _minY );

    }

    @FXML
    private void fixedOnTopLeft(MouseEvent event) {

//        btnLockInTop.setVisible(true); //256

        // Gets the initial cordinates and clamp with the size of circle
        initY = clampPointerTopY(event);
        initX = clampPointerForLeftX(event);

        // Remove old anchors
        clearConstraints(boxSelector);

        // Get the min x and y postion (Relative to boxContainer)
        double _minX =  (boxSelector.getLocalToParentTransform().getTx() ) ;
        double _minY =  (boxSelector.getLocalToParentTransform().getTy()  ) ;

        // Get the maxX and maxY position in the parent (Relative to boxContainer)
        double _maxX = viewWidth - (_minX + boxSelector.getWidth());
        double _maxY = boxContainer.getHeight() - (_minY + boxSelector.getHeight());

        // Anchor positions (Relative to boxContainer)
        AnchorPane.setRightAnchor(boxSelector, _maxX );
        AnchorPane.setBottomAnchor(boxSelector, _maxY );

    }

    @FXML
    private void resizeFromTopLeft(@NotNull MouseEvent event) {

        if (!event.isPrimaryButtonDown() || (initX == -1 && initY == -1)) return; // if out of bounds and not the primary button(left)
        if (event.isStillSincePress()) return; // if the mouse doesn't continuos pressed

        // get the new x positions
        newX = event.getSceneX();
        newY = event.getSceneY();

        // get the difference from initial clicked to dragg
        deltaX = newX - initX;
        deltaY = newY - initY;

        // get the bounds for the box
        double maxX = (boxContainer.getLocalToSceneTransform().getTx() -2);
        double maxY = (boxContainer.getLocalToSceneTransform().getTy() -2);

        // don't leave from the limit
        if (newY > maxY) setHeight(boxSelector.getPrefHeight()  - deltaY);
        if (newX > maxX) setWidth(boxSelector.getPrefWidth()    - deltaX);
    }

    @FXML
    private void getInitialCordinates(@NotNull MouseEvent event) {

        if (event.getTarget() instanceof Circle || event.getTarget() instanceof Path) {



        } else {

            initX = (event.getScreenX()) - (boxSelector.getLocalToParentTransform().getTx());
            initY = (event.getScreenY()) - (boxSelector.getLocalToParentTransform().getTy());
//
        }
    }

    private void clearConstraints(Node node) {
        AnchorPane.clearConstraints(node);
    }

    private double clampPointerForLeftX(@NotNull MouseEvent event) {
        double min = boxSelector.getLocalToSceneTransform().getTx();
        double _count = event.getSceneX() - min;
        double _sum = event.getSceneX() - _count;
        return  _sum - 1;
    }

    private double clampPointerForRightX(@NotNull MouseEvent event) {
        double min = (boxSelector.getLocalToSceneTransform().getTx() + boxSelector.getWidth()) -2;
        double _count = event.getSceneX() - min;
        double _sum = event.getSceneX() - _count;
        return  _sum - 1;
    }

    private double clampPointerTopY(@NotNull MouseEvent event) {
        double min = boxSelector.getLocalToSceneTransform().getTy();
        double _count = event.getSceneY() - min;
        double _sum = event.getSceneY() - _count;
        return _sum -1;
    }

    private double clampPointerBottomY(@NotNull MouseEvent event) {
        double min = (boxSelector.getLocalToSceneTransform().getTy() + boxSelector.getHeight()) -2; // two border width

        double _count = event.getSceneY() - min;
        double _sum = event.getSceneY() - _count;


//        Pane separator = new Pane();
//        separator.setPrefSize(200, 200);
//        separator.setStyle("-fx-background-color : red;");
//
//        context.getDecorator().getWrapper().addContent(separator);
//
//        AnchorPane.setTopAnchor(separator, min);
//        AnchorPane.setLeftAnchor(separator, boxSelector.getLocalToSceneTransform().getTx());

        return _sum -1;
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

    @Deprecated
    public void updateAvatars() {
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

        params.setViewport(new Rectangle2D(_minX, _minY, _width, _hegth));

        wrapper.setEffect(null);
        WritableImage wImage = new WritableImage((int) _width, (int) _hegth);
        WritableImage image = imageView.snapshot(params, wImage);
        wrapper.setEffect(new ColorAdjust(0,0,-0.50 ,0));

        FormClientController controller = (FormClientController) context.getRoutes().getView("form_client").getController();
        controller.setAvatar(image);

        context.getDecorator().getWrapper().getPopup().close();

    }

    public WritableImage getImage() {

        SnapshotParameters params = new SnapshotParameters();

        double _minX = boxSelector.getLocalToParentTransform().getTx();
        double _minY = boxSelector.getLocalToParentTransform().getTy();
        double _width = boxSelector.getPrefWidth();
        double _height = boxSelector.getPrefHeight();

        params.setViewport(new Rectangle2D(_minX, _minY, _width, _height));

        wrapper.setEffect(null);
        WritableImage wImage = new WritableImage((int) _width, (int) _height);

        WritableImage image = imageView.snapshot(params, wImage);
        wrapper.setEffect(new ColorAdjust(0,0,-0.50 ,0));
        return image;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sideHeaderNavigation = new SideHeaderNavigation(Icons.DRAW, "Recorte seu novo avatar.", false);
        body.getChildren().add(0, sideHeaderNavigation);

    }

    private void centerImage(Image image) {

        // Get the actual view port
        viewWidth = Math.min(image.getWidth(), (context.getDecorator().getWidth() /2));
        viewHeight = Math.min(image.getHeight(), (context.getDecorator().getHeight() / 2));

        // Sets the view port
        imageView.setViewport(new Rectangle2D(0,0, viewWidth, viewHeight));

        // Sets the image view size
        imageView.setFitWidth(viewWidth);
        imageView.setFitHeight(viewHeight);

    }
}
