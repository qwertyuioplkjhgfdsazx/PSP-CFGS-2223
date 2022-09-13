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
package UD02.DebugNetBeans;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es|www.ieseduardoprimo.es)
 */
public class Deadlock {
    public static void main(String[] args) {
        final String res1 = "First Resource";
        final String res2 = "Second Resource";
        Thread thread1 = new Thread() {
            public void run() {
                synchronized (res1) {
                    System.out.println("Thread 1: locked the res1");
 
                    try {
                        Thread.sleep(2000);
                    } catch (Exception excep) {
                    }
 
                    synchronized (res2) {
                        System.out.println("Thread 1: locked the res2");
                    }
                }
            }
        };
        Thread thread2 = new Thread() {
            public void run() {
                synchronized (res2) {
                    System.out.println("Thread 2: locked the res2");
 
                    try {
                        Thread.sleep(2000);
                    } catch (Exception excep) {
                    }
 
                    synchronized (res1) {
                        System.out.println("Thread 2: locked the res1");
                    }
               }
            }
        };
        thread1.start();
        thread2.start();
    }
}