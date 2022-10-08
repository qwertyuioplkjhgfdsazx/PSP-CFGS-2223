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
package UD02.Example20;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es|www.ieseduardoprimo.es)
 */
public class SimpleWaitNotify implements Runnable {

    private volatile boolean runningMethod1 = false;

    public synchronized void method1() {
        for (int i = 0; i < 10; i++) {
            System.out.printf("Method1: Running %d\n", i);
            if (i == 5) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void method2() {
        for (int i = 10; i < 20; i++) {
            System.out.printf("Method2: Running %d\n", i);
        }
        this.notifyAll();
    }

    @Override

    public void run() {
        if (!runningMethod1) {
            runningMethod1 = true;
            method1();
        } else {
            method2();
        }
    }

    public static void main(String[] args) {
        SimpleWaitNotify swn = new SimpleWaitNotify();
        new Thread(swn).start();
        new Thread(swn).start();
    }
}
