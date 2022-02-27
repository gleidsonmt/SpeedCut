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

package io.github.gleidsonmt.speedcut.core.app.factory;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  26/02/2022
 */
public class LoadPlaceholder extends HBox {

    private final Rectangle one = new Rectangle();
    private final Rectangle two = new Rectangle();
    private final Rectangle three = new Rectangle();

    public LoadPlaceholder() {

        setAlignment(Pos.CENTER);
        setSpacing(5);

        getChildren().addAll(one, two, three);


        getChildren().forEach(
                c -> {
                    ((Rectangle) c).setWidth(6);
                    ((Rectangle) c).setHeight(20);
                    ((Rectangle) c).setArcHeight(5);
                    ((Rectangle) c).setArcWidth(5);
                    ((Rectangle) c).setFill(Color.web("#33B5E5"));
                    ((Rectangle) c).setStroke(Color.web("#33B5E5"));
                }
        );

        createAnimation(one, 62, 2);
        createAnimation(two, 45, 1);
        createAnimation(three, 53, 1.5);
    }

    private void createAnimation(Rectangle rectangle, double height, double rate) {
        Timeline animation = new Timeline();

        animation.setDelay(Duration.millis(500));
        animation.setCycleCount(-1);
        animation.setRate(rate);
        animation.setAutoReverse(true);

        animation.getKeyFrames().setAll(
                new KeyFrame(Duration.ZERO, new KeyValue(rectangle.heightProperty(), 10)),
                new KeyFrame(Duration.millis(500), new KeyValue(rectangle.heightProperty(), height))

        );

        animation.play();

    }



}
