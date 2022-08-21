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

import io.github.gleidsonmt.speedcut.core.app.model.Entity;
import io.github.gleidsonmt.speedcut.core.app.model.Model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  21/05/2022
 */
@SuppressWarnings("unchecked")
public class Repositories {

    private static final HashMap<String, Repository<? extends Entity>> repositories = new HashMap<>();

    private Repositories(){ }

    public static void load() {

        if (!repositories.isEmpty()) return;

        List<Repository<? extends Entity>> list = Arrays.asList(
                new RepoCashierImpl(),
                new RepoProfessionalImpl(),
                new RepoSaleItemImpl(),
                new RepoSaleImpl(),
                new RepoClientImpl(),
                new RepoServiceImpl(),
                new RepoTransactionImpl(),
                new RepoCardImpl(),
                new RepoAmount(),
                new RepoUserImpl()
        );

        for (Repository<? extends Entity> dao : list) {

            String key = dao.getClass().getSimpleName().replaceAll("Repo", "")
                    .replaceAll("Impl", "");
            repositories.putIfAbsent(key, dao);
        }
    }

    @Deprecated
    public static <T extends Entity> Repository<T> get(String model) {
        return (Repository<T>) repositories.get("Dao" + model);
    }

    public static <T extends Entity> Repository<T>  get(Class<? extends Model> _class) {
        return (Repository<T>) repositories.get(_class.getSimpleName());
    }


}
