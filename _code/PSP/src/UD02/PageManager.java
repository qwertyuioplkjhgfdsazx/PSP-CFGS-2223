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
package UD02;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es|www.ieseduardoprimo.es)
 */
public class PageManager extends Thread {

    private static List<String> list = new ArrayList<String>();

    @Override
    public void run() {
        while (true) {
            if (list.size() >= 10) {
                list.remove(0);
            } else if (list.size() < 10) {
                list.add("Text");
            }
            for (String s : list) {
                //going through the list
            }

        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            list.add("Texto");
        }
        for (int i = 0; i < 100; i++) {
            new PageManager().start();
        }
    }
}