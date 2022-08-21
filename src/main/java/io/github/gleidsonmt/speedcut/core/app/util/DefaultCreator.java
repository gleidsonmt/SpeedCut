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

import io.github.gleidsonmt.speedcut.core.app.factory.AvatarCreator;
import io.github.gleidsonmt.speedcut.core.app.model.Client;
import io.github.gleidsonmt.speedcut.core.app.model.Professional;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  28/07/2022
 */
public class DefaultCreator {


    public static Professional createProfessional() {
        Professional professional = new Professional();
        professional.setName("Profissional");
        professional.setId(1);
        professional.setImgPath("theme/img/avatars/woman@400.png");
        return professional;
    }

    public static Client createClient() {
        Client client = new Client();
        client.setName("Cliente");
        client.setImgPath("theme/img/avatars/woman@400.png");
        client.setId(1);
        return client;
    }


}
