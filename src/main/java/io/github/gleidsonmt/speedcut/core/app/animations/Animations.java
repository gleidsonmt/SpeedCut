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

package io.github.gleidsonmt.speedcut.core.app.animations;

import animatefx.animation.Pulse;
import io.github.gleidsonmt.gncontrols.GNButton;
import io.github.gleidsonmt.gncontrols.controls.GNTextBox;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ToggleButton;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  22/02/2022
 */
public class Animations {

    public static void errorOnTextBox(GNTextBox textBox) {
        Pulse animation = new Pulse(textBox);
        animation.setSpeed(3);
        animation.play();
    }

    public static void onHoverButton(ButtonBase button) {
        Pulse animation = new Pulse(button);
        animation.setSpeed(3);
        button.hoverProperty().addListener((observable, oldValue, newValue) -> {
            animation.play();
        });
    }
}
