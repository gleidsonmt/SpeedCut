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

import io.github.gleidsonmt.speedcut.core.app.model.Avatar;
import io.github.gleidsonmt.speedcut.core.app.model.Person;
import io.github.gleidsonmt.speedcut.core.app.model.Sex;
import io.github.gleidsonmt.speedcut.core.app.view.intefaces.Context;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  09/08/2022
 */
public class Util implements Context {


    public static void setAvatar(Person element, String path) {

        if (isValidString(path)) {

            File file = new File(path);


            if (!file.exists()) {
                    setDefaultAvatarPerson(element);
            } else {

                element.setAvatar(new Avatar(file.getAbsolutePath()));
                Util.resolveThumbFullPath(file.getAbsolutePath());
                element.setThumbnail(Util.resolveThumbFullPath(file.getAbsolutePath()));

            }

        } else {
            setDefaultAvatarPerson(element);
        }

    }


    private static void setDefaultAvatarPerson(Person element) {

        Avatar image = null;
        Avatar thumbnail = null;

        String path;

        try {
            path = convertPathForPerson(element);
            image = createPackageImage(path);
            thumbnail = createPackageImage(path, true);

        } catch (Throwable e) {
            Logger.getLogger("app").severe("Error this class is not a instance from Person class.");
            e.printStackTrace();
        }

        element.setAvatar(image);
        element.setThumbnail(thumbnail);

    }

    /**
     * Verify if this string is null or black or empty.
     * @param string The value for comparation.
     * @return if is a non-null string or empty.
     */
    public static boolean isValidString(String string) {
        return string != null && (!string.isBlank() && !string.isEmpty());
    }

    private static @NotNull String convertPathForPerson(@NotNull Person element) throws Throwable {

        String path;

        if (element.getSex() == Sex.MALE) {
            path = "theme@male";
        } else path = "theme@female";

        return path;

    }

    private static @NotNull Avatar createPackageImage(@NotNull String _path) {
        return createPackageImage(_path, false);
    }

    private static @NotNull Avatar createPackageImage(@NotNull String _path, boolean thumb) {

        String type;
        String imagePath = null;

        if (_path.contains("@")) {

            type = _path.substring(_path.indexOf("@") + 1);

            switch (type) {
                case "male":
                    imagePath = thumb ? "thumbnail.man.png" : "man.png";
                    break;
                case "female":
                    imagePath = thumb ? "thumbnail.woman.png" : "woman.png";
                    break;
                case "service":

                    break;
                case "product":

                    break;
            }

            return context.getAvatar(imagePath);
        }

        return null;
    }

    @Contract("_ -> new")
    private static @NotNull Avatar resolveThumbFullPath(@NotNull String path) {

        String cutPath = path.substring(0, path.lastIndexOf("\\") + 1);
        String imageName = path.substring(path.lastIndexOf("\\") +1);
        String newThumbName = cutPath + "thumbnail." + imageName;

        String parseThumbName = newThumbName.replace("\\", "/");

        File file = new File(newThumbName);

        if (file.exists()) {
            return new Avatar(parseThumbName);
        } else {
            return new Avatar(path);
        }

    }

    public static Label createIconLabel(String initial, double size) {
        Label label = new Label();
        List<String> colors = Arrays.asList(
                "#4FC1E9", "#48CFAD", "#AA66CC",
                "#FFA000", "#ED5565");
        label.setText(initial);

        label.setId("custom-rect-avatar");

        Random random = new Random();

        label.setPrefSize(size * 2, size * 2);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(Pos.CENTER);

        label.setId("custom-rect-avatar");
//
//        label.setStyle("-fx-font-size : " + textSize + ";" +
//                "-fx-border-width : " + borderWidth + ";" +
//                "-fx-background-color : " +
//                colors.get(random.nextInt(colors.size())) +";");

        label.setStyle("-fx-font-size : 8pt;" +
                "-fx-border-width : 1px;" +
                "-fx-background-color : " +
                colors.get(random.nextInt(colors.size())) +";");

        return label;
    }

}
