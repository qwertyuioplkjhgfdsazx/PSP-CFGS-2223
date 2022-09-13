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
package UD02.Example22;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es|www.ieseduardoprimo.es)
 */
public class BasicJoin extends Thread {

    private int id;
    private boolean suspend = false;
    private Thread referenceThread;

    public BasicJoin(int id) {
        this.id = id;
    }

    public void threadSuspend(Thread referenceThread) {
        this.suspend = true;
        this.referenceThread = referenceThread;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                if (suspend) {
                    referenceThread.join();
                }
                System.out.printf("Thread %d. Interaction %d\n", id, i);
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BasicJoin thread1 = new BasicJoin(1);
        BasicJoin thread2 = new BasicJoin(2);
        thread1.start();
        thread2.start();
        thread2.threadSuspend(thread1);
    }
}
