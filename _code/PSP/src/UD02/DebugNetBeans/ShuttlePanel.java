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

import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es|www.ieseduardoprimo.es)
 */
class ShuttlePanel extends JPanel implements Runnable {

    private int shuttleNumber = 3;
    Thread[] shuttleThreads;
    Shuttle[] shuttle;

    public ShuttlePanel() {
        shuttleThreads = new Thread[shuttleNumber];
        shuttle = new Shuttle[shuttleNumber];
        for (int i = 0; i < shuttleThreads.length; i++) {
            shuttleThreads[i] = new Thread(this);
            shuttleThreads[i].setName("Shuttle Thread − " + i);
            Random rand = new Random();
            int velocity = rand.nextInt(50);
            int posX = rand.nextInt(100) + 30;
            int posY = rand.nextInt(100) + 30;
            int dX = rand.nextInt(3) + 1;
            int dY = rand.nextInt(3) + 1;
            shuttle[i] = new Shuttle(posX, posY, dX, dY, velocity);
        }
        for (int i = 0; i < shuttleThreads.length; ++i) {
            shuttleThreads[i].start();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < shuttleThreads.length; ++i) {
            while (shuttleThreads[i].currentThread() == shuttleThreads[i]) {
                try {
                    shuttleThreads[i].sleep(shuttle[i].velocity()); //
                    shuttle[i].move();
                } catch (InterruptedException e) {
                }
                repaint();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < shuttle.length; ++i) {
            shuttle[i].draw(g);
        }
    }
}

