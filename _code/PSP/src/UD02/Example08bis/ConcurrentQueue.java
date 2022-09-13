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
package UD02.Example08bis;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es|www.ieseduardoprimo.es)
 */
public class ConcurrentQueue implements Runnable {

    private static Queue<Integer> queue = new ConcurrentLinkedDeque<Integer>();

    @Override
    public void run() {
        queue.add(10);
        for (Integer i : queue) {
            System.out.print(1 + ":");
        }
        System.out.println("Queue size:" + queue.size());
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new ConcurrentQueue()).start();
        }
    }
}
