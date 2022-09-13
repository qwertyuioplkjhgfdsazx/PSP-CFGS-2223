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
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author David Martínez (wwww.martinezpenya.es|www.ieseduardoprimo.es)
 */
class Shuttle {

    private int x, y;
    private int dsx, dsy, v; //displacement, v sleep
    private int tx = 37; //margin x
    private int ty = 63; //margin y image (74x126 pixels)
    private Image image;

    public Shuttle(int x, int y, int dsx, int dsy, int v)  {

        this.x = x;
        this.y = y;
        this.dsx = dsx;
        this.dsy = dsy;
        this.v = v;
        image = new ImageIcon(Shuttle.class.getResource("shuttle.png")).getImage();
    }

    public int velocity() { //sleep in miliseconds
        return v;
    }

    public void move() {
        x = x + dsx;
        y = y + dsy;
        // touch the margin?
        if (x >= (400 - tx*2) || x <= 0) {
            dsx = -dsx;
        }
        if (y >= (600 - ty*2) || y <= 0) {
            dsy = -dsy;
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, x, y, null);
    }
}

