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
package UD02.Example02;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es|www.ieseduardoprimo.es)
 */
public class Mouse extends Thread {

    private String name;
    private int feedingTime;

    public Mouse(String name, int feedingTime) {
        super();
        this.name = name;
        this.feedingTime = feedingTime;
    }

    public void eat() {
        try {
            System.out.printf("The mouse %s has started to feed%n", name);
            Thread.sleep(feedingTime * 1000);
            System.out.printf("The mouse %s has stopped to feed%n", name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        this.eat();
    }

    public static void main(String[] args) {
        Mouse fievel = new Mouse("Fievel", 4);
        Mouse jerry = new Mouse("Jerry", 5);
        Mouse pinky = new Mouse("Pinky", 3);
        Mouse mickey = new Mouse("Mickey", 6);
        fievel.start();
        jerry.start();
        pinky.start();
        mickey.start();
    }
}
