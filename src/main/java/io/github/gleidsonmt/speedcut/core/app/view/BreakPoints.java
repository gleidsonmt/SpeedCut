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

package io.github.gleidsonmt.speedcut.core.app.view;

import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  04/09/2022
 */
public class BreakPoints implements Context {

    public static final double X_SMALL = 576D;

    public static final double SMALL = 576D;
    public static final double MEDIUM = 768;
    public static final double LARGE = 992;
    public static final double X_LARGE = 1200;
    public static final double XX_LARGE = 1400;
    public static final double FHD = 1900;

    public static boolean isXSmall () {
        return context.getWindow().getWidth() < SMALL;
    }

    public static boolean isSmall () {
        return context.getDecorator().getWidth() >= SMALL && context.getWindow().getWidth() <= MEDIUM;
    }

    public static boolean isMedium () {
        return context.getDecorator().getWidth() >= MEDIUM && context.getWindow().getWidth() <= LARGE;
    }

    public static boolean isLarge () {
        return context.getDecorator().getWidth() >= LARGE && context.getWindow().getWidth() <= X_LARGE;
    }

    public static boolean isXLarge () {
        return context.getDecorator().getWidth() >= X_LARGE && context.getWindow().getWidth() <= XX_LARGE;
    }

    public static boolean isXXLarge () {
        return context.getDecorator().getWidth() > XX_LARGE && context.getWindow().getWidth() <= FHD;
    }
}