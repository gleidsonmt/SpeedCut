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

import io.github.gleidsonmt.speedcut.core.app.exceptions.ControllerCastException;
import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import javafx.application.Platform;
import javafx.beans.value.WritableObjectValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Parent;

import java.util.HashMap;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  15/02/2022
 */
public class ViewManager implements IViewManager {

    private final static HashMap<String, IView> VIEWS = new HashMap<>();

    private IView current;
    private IView previous;

    @Override
    public void add(IView view) {
        VIEWS.put(view.getName(), view);
    }

    @Override
    public IView get(String name)  {
        return VIEWS.get(name);
    }

    public Parent getRootFrom(String name) {
        return VIEWS.get(name).getRoot();
    }

    public ActionViewController getControllerFrom(String name) {
        try {
            return VIEWS.get(name).getController();
        } catch (ControllerCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IView getCurrent() {
        return current;
    }

    public IView getPrevious() {
        return previous;
    }

    public void setCurrent(IView view) {
        this.previous = current;
        this.current = view;
    }

    public boolean contains(String nameView) {
        return VIEWS.containsKey(nameView);
    }
}
