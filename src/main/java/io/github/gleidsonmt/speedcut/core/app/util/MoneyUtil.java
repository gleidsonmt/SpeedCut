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

package io.github.gleidsonmt.speedcut.core.app.util;

import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  27/02/2022
 */
public class MoneyUtil {
    private static DecimalFormat formatter;

    public static @NotNull String format(String value) {
        return format(get(value));
    }

    public static @NotNull String format(BigDecimal value) {
        configFormatter();
        return formatter.format(value);
    }

    /**
     * Operacao de multiplicacao.
     * @param first
     * @param second
     * @return
     */
    public static String multiply(String first, String second) {

        // se formato como monetario retorna um bigdecimal com casas, se formato como numero retona o bigdecialm puro sem casas
        BigDecimal _first = first.contains(".") || first.contains(",") ? get(first) : clean(first);
        BigDecimal _second = second.contains(".") || second.contains(",") ? get(second) : clean(second);

        return format(_first.multiply(_second));
    }

    public static String multiply(TextField first, TextField second) {
        return format(get(first.getText()).multiply(get(second.getText())));
    }

    /**
     * Converte um bigdecimal em string.
     * @param value
     * @return
     */
    public static @NotNull String parse(BigDecimal value) {
        return getPure(value);
    }

    /**
     * Converte uma string em um BigDecimal.
     * @param value
     * @return
     */
    public static @NotNull BigDecimal get(String value) {
        value = value.replaceAll("[^0-9]", "");
        value = value.replaceAll("([0-9])([0-9]{2})$", "$1.$2");
        return value.length() == 0 ? new BigDecimal(0) : new BigDecimal(value);
    }

    /**
     * Remove tudo q nao for numero.
     * @param value
     * @return
     */
    private static @NotNull BigDecimal clean(String value) {
        value = value.replaceAll("[^0-9]", "");
        return new BigDecimal(value);
    }

    /**
     * Remove o cifrao mais mantem a formatacao.
     * @param value
     * @return
     */
    private static @NotNull String getPure(BigDecimal value) {
        configFormatter();
        return formatter.format(value).replaceAll("[^0-9.,]", "");
    }

    /**
     * Retorna o simbolo monetario corrente.
     * @return
     */
    // was used in preferences
    public static String getSymbol() {
        configFormatter();
        return formatter.getDecimalFormatSymbols().getCurrencySymbol();
    }

    /**
     * Configura o formato.
     */
    private static void configFormatter() {
        formatter = (DecimalFormat) NumberFormat.getCurrencyInstance();
    }
}
