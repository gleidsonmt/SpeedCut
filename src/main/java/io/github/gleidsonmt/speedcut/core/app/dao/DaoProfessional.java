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

package io.github.gleidsonmt.speedcut.core.app.dao;

import io.github.gleidsonmt.speedcut.core.app.factory.AvatarCreator;
import io.github.gleidsonmt.speedcut.core.app.model.Professional;
import io.github.gleidsonmt.speedcut.core.app.model.Professional;
import io.github.gleidsonmt.speedcut.core.app.model.Service;
import io.github.gleidsonmt.speedcut.presenter.ProductPresenter;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.text.TextAlignment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  14/02/2022
 */
public final class DaoProfessional extends AbstractDao<Professional> {

    @Override
    protected Professional createElement(long id, ResultSet result) {
        Professional element = new Professional();

        try {
            element.setId( (int) id);
            element.setName(result.getString("NAME"));

            element.setAvatar(result.getString("IMG_PATH"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return element;
    }

    public int getSizeFromServer() {
        executeSQL("select count(id) as count from " + table + ";");
        ResultSet result = result();
//        int size = 0;
        try {
            if (result.first()) {
                return result.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return 0;
    }
}
