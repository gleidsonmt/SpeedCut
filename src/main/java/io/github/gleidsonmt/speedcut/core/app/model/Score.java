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

package io.github.gleidsonmt.speedcut.core.app.model;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  16/08/2022
 */
public enum Score {

    SPECIAL(0, "Special"), // < 10
    NOVICE(10, "Novato(a)"), // < 10
    USUALLY(50, "Casual"), // > 9 && < 49
    VETERAN(100, "Veterano(a)"), // > 49 && < 99
    PREMIUM(300, "Premium"), // > 99 && < 299
    SUPER(500, "Super"); // > 299 && < 259

//    1 - Novata - blue
//2 - Veterana - mint
//3 - Tem um tempo - bettersweet
//4 - Premium - gold
//5 - Já e de casa - linear(mint,

    private final int points;
    private final String text;

    Score (int _points, String _text) {
        this.points = _points;
        this.text = _text;
    }

    public int getPoints () {
        return this.points;
    }

    public String getText () {
        return this.text;
    }
}
