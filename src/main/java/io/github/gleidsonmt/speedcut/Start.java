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

package io.github.gleidsonmt.speedcut;

import io.github.gleidsonmt.speedcut.core.app.Main;
import io.github.gleidsonmt.speedcut.core.app.dao.RepoClientImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class Start extends Main {

    public static void main(String[] args) {

//        try {
//            Thumbnails.of(Objects.requireNonNull(
//                            new File("C:\\Users\\Gleidson\\IntelliJIDEAProjects\\SpeedCut\\src\\main\\resources\\io.github.gleidsonmt.speedcut.core.app\\theme\\img\\avatars").listFiles()))
//                    .size(100, 100)
//                    .outputFormat("png")
//                    .toFiles(Rename.PREFIX_DOT_THUMBNAIL);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        launch(args);
    }

}
