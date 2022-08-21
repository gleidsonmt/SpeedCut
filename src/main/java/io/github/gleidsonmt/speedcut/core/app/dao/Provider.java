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

import io.github.gleidsonmt.speedcut.core.app.model.Model;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  17/05/2022
 */
@Deprecated
public class Provider {

    private final static HashMap<String, Repository<? extends Model>> map = new HashMap<>();

    public static Repository<? extends Model> of(Class<? extends Model> clazz) {

        boolean find = Stream.of(map).anyMatch(p -> p.containsKey("Repo" + clazz.getSimpleName() + "Impl") );

        if (!find) {
            try {
                Class<?> _clazz = Class.forName("io.github.gleidsonmt.speedcut.core.app.repo.Repo"
                        + clazz.getSimpleName() + "Impl");

                map.putIfAbsent("Repo" + clazz.getSimpleName() ,
                        (Repository<? extends Model>) _clazz.getDeclaredConstructor().newInstance()
                );
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return map.get("Repo" + clazz.getSimpleName());
    }


}
