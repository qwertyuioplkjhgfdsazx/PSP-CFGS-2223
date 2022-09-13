/*
 * Copyright (C) 2022 David Martínez (wwww.martinezpenya.es|www.ieseduardoprimo.es)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package UD02.Example11;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es|www.ieseduardoprimo.es)
 */
public class safeReaderWriter extends Thread {

    private static List<String> words = new CopyOnWriteArrayList<String>();

    @Override
    public void run() {
        words.add("New word");
        for (String word : words) {
            words.size();
        }
        System.out.println(words.size());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new safeReaderWriter().start();
        }
    }
}
