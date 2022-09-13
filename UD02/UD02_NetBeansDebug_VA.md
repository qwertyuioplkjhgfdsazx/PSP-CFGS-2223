---
title: Depurar aplicación multihilo con NetBeans
language: ES
author: David Martínez Peña [www.martinezpenya.es]
subject: Programación de Servicios y Procesos
keywords: [PSP, 2022, Programacion, servicios, procesos, multihilo, Java]
IES: IES Eduardo Primo Marqués (Carlet) [www.ieseduardoprimo.es]
header: ${title} - ${subject} (ver. ${today}) 
footer:${currentFileName}.pdf - ${author} - ${IES} - ${pageNo}/${pageCount}
typora-root-url:${filename}/../
typora-copy-images-to:${filename}/../assets
imgcover:/media/DADES/NextCloud/DOCENCIA/PSP_2223/PSP-CFGS-2223/UD01/assets/cover.png
---
[toc]

# Depuració d'aplicacions multifils

Depurar aplicacions és una de les tasques que més sovint fem els programadors. Ens permet verificar l'estat de la nostra aplicació en un punt en concret. Posar marques al codi o utilitzar les eines de depuració (debugging, en anglès) amb totes les utilitats que ens proporcionen alguns IDE, és una tasca imprescindible per revisar la nostra aplicació. Depurar una aplicació seqüencial en la qual únicament hi ha una línia d'execució és simple amb una eina de depuració. Anem avançant pas a pas al fil d'execució i anem mirant valors de variable, condicions… fins que acabem. Però en la programació multifil hi ha més d'un fil d'execució. Haurem d'anar consultant cada fil per separat per veure el seu funcionament i quins valors va prenent.

Veurem com podem depurar una aplicació multifil i utilitzarem l'eina per detectar possibles errors com l'interbloqueig a una aplicació.

Per fer les proves utilitzarem l'IDE de Netbeans. Amb altres IDEs el funcionament és similar. Per provar com consultar els fils obrirem un projecte i seleccionarem el projecte a la finestra de projectes i triarem l'opció Debug. El projecte que analitzarem és el següent: 

`Nau.java`

```java
package UD02.StarShip;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

class Nau {

    private int x, y;
    private int dsx, dsy, v; //desplaçaments, v sleep
    private int tx = 10; //marge x
    private int ty = 10; //marge i image
    private String img = "/images/nau.jpg";
    private Image image;

    public Nau(int x, int y, int dsx, int dsy, int v)  {

        this.x = x;
        this.y = y;
        this.dsx = dsx;
        this.dsy = dsy;
        this.v = v;
        image = new ImageIcon(Nau.class.getResource("ship.png")).getImage();
    }

    public int velocitat() { //sleep en milisegons
        return v;
    }

    public void moure() {
        x = x + dsx;
        y = y + dsy;
        // arriva als marges
        if (x >= 450 - tx || x <= tx) {
            dsx = -dsx;
        }
        if (y >= 500 - ty || y <= ty) {
            dsy = -dsy;
        }
    }

    public void pinta(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, x, y, null);
    }
}
```

`PanelNau.java`

```java
package UD02.StarShip;

import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

class PanelNau extends JPanel implements Runnable {

    private int numNaus = 3;
    Thread[] filsNau;
    Nau[] nau;

    public PanelNau() {
        filsNau = new Thread[numNaus]; //Creo dos hilos
        nau = new Nau[numNaus];
        for (int i = 0; i < filsNau.length; i++) {
            filsNau[i] = new Thread(this);
            filsNau[i].setName("Fil Nau − " + i);
            Random rand = new Random();
            int velocitat = rand.nextInt(50);
            int posX = rand.nextInt(100) + 30;
            int posY = rand.nextInt(100) + 30;
            int dX = rand.nextInt(3) + 1;
            int dY = rand.nextInt(3) + 1;
            nau[i] = new Nau(posX, posY, dX, dY, velocitat);
        }
        for (int i = 0; i < filsNau.length; ++i) {
            filsNau[i].start();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < filsNau.length; ++i) {
            while (filsNau[i].currentThread() == filsNau[i]) {
                try {
                    filsNau[i].sleep(nau[i].velocitat()); //
                    nau[i].moure();
                } catch (InterruptedException e) {
                }
                repaint();
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < nau.length; ++i) {
            nau[i].pinta(g);

        }

    }
}
```

`NauEspaial.java`

```java
package UD02.StarShip;

import javax.swing.JFrame;

public class NauEspaial extends javax.swing.JFrame {

    public NauEspaial() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
                getContentPane());

        getContentPane()
                .setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
        pack();
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NauEspaial.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NauEspaial.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NauEspaial.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NauEspaial.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }

        NauEspaial f = new NauEspaial();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Naus Espaials");
        f.setContentPane(new PanelNau());
        f.setSize(480, 560);
        f.setVisible(true);
    }
}
```

`ship.png`

![ship](/assets/ship.png)

> Els punts de ruptura (*breakpoints* en anglès) són marques que indiquen a l'IDE on cal aturar l'execució del codi quan es faci en mode depuració.

Si no tenim posats punts de ruptura l'aplicació s'executarà fins a acabar. L'aplicació és un panell en el qual es mostren unes imatges que es mouen. Cada imatge (nau espacial) és controlada per un fil. L'aplicació crea 3 fils, per tant hi haurà tres imatges al panell en moviment. 

Aplicació de naus en funcionament:

![image-20220823171913084](/assets/naus.png)

A la finestra de depuració podem veure els tres fils creats, amb el nom de *Fil Nau 0*, *Fil Nau 1* i *Fil Nau 2*. La resta de fils creats són el fil principal i els del sistema.

Depuració de fils:

![img](/assets/filsdebug.png)

A la dreta del nom del fil hi ha un símbol de pausa. Si el clquem el fil s'aturarà. El símbol es convertirà en el triangle de *play*. Quan el fil es troba aturat ([figura.13](file:///media/DADES/NextCloud/DOCENCIA/PSP_2223/05_IOC/web/WebContent/u1/a2/continguts.html#Figura12b)) podem veure que surt una carpeta que es pot expandir. Dins hi veiem la llista de trucades del fil. Ja que el fil està aturat, la nau (la imatge que es mou pel panell) s'atura també.

Fil aturat:

![img](/assets/filaturat.png)

Si hem posat algun punt d'interrupció, l'aplicació es comporta com si no treballés amb fils. L'execució s'atura en aquest punt.

Per posar un punt d'interrupció hem de picar al marge esquerre de l'editor al costat del número de línia en el qual volem posar el punt d'interrupció. Apareixerà un quadrat vermell que marca el punt.

En la següent figura es veu que el punt d'interrupció està posat quan el fil executa el moviment de la imatge. 

Punt d'interrupció :

![img](/assets/breakpoint.png)

En la finestra de depuració es veu com s'està executant el primer fil que arriba. Clicant les tecles de funció F7 o F8 anirà avançant la línia de programa sobre el fil que està actiu. La resta de fils estan preparats per executar-se. Si volem que els altres fils comencin haurem de cliar sobre la fletxa que està sota la finestra de depuració i seleccionar el fil que volem executar. Un cop tots els fils s'estan executant, podem veure l'execució d'un o altre fil picant sobre el fil a la finestra de depuració. El fil que estem controlant apareix amb un marc verd.

A l'esquerra del codi apareixen les icones de la roda dentada que ens indiquen on es troben aturats els fils que no estem depurant. Si piquem sobre la roda dentada amb al botó dret podem veure quin és el fil que està aturat en aquest punt.

Intercanviant l'execució dels fils :

![img](/assets/puntinterrupcio.png)

Si volem canviar per continuar depurant un altre fil el seleccionarem de la llista. Únicament podem depurar un fil amb un punt de ruptura (*breakpoint*, en anglès). Quan cliquem sobre F7 o F8 per anar avançant únicament avança un fil, els altres no s'aturen al punt d'interrupció. 

Depurar aplicacions és bastant simple i molt útil. Els IDE cada vegada proporcionen eines que ens ajuden més a la correcció d'errors. Netbeans a la seva eina de depuració inclou una funció que detecta interbloquejos automàticament.

Per utilitzar-la hem de crear punts de ruptura. Els punt d'interrupció es posen on nosaltres pensem que hi ha el punt crític de la nostra aplicació i on podria ser la font dels errors. En aquest cas els posarem al mètode `run()` de la classe que hereta `threads` o implementa `runnable`. 

Executem l'aplicació en mode depuració i veiem com s'atura als punt d'interrupció. 

Punt d'interrupció:

![img](/assets/breakpointdeadlock.png)

En aquest punt podem continuar l'execució (tecles de funció F7 o F8) i veure com avança l'aplicació i van variant els valors de les variables, la línia d'execució, etc.

Per detectar l'interbloqueig hem d'acabar l'execució. Podem clicar sobre cada botó dret i seleccionar *resume*, podem clicar F5 o clicar sobre la icona de la barra d'eines del triangle blanc (*play*) sobre fons verd. El resultat que tindrem és l'aplicació executant-se. Per veure si s'ha produït un interbloqueig hem d'anar al menú de depuració i clicar sobre l'opció *Check for DeadLock*.

Examinar interbloqueig:

![img](/assets/deadlock.png)

Automàticament Netbeans ens dirà si existeixen o no interbloquejos. Ens mostrarà un missatge dient si n'ha trobat o no.

Missatge d'interbloqueig :

![img](/assets/deadlockdetectado.png)

La finestra de depuració ens diu on es troben els fils bloquejats en situació d'interbloqueig.

Les eines de depuració són molt útils per trobar possibles errades de programació. Quan programem utilitzant molts fils, la depuració de les aplicacions és fonamental.

# Fuentes de información

- [Wikipedia](https://en.wikipedia.org)
- [Programación de servicios y procesos - FERNANDO PANIAGUA MARTÍN [Paraninfo]](https://www.paraninfo.es/catalogo/9788413665269/programacion-de-servicios-y-procesos)
- [Programación de Servicios y Procesos - ALBERTO SÁNCHEZ CAMPOS [Ra-ma]](https://www.ra-ma.es/libro/programacion-de-servicios-y-procesos-grado-superior_49240/)
- [Programación de Servicios y Procesos - Mª JESÚS RAMOS MARTÍN - [Garceta] (1ª y 2ª Edición)](https://www.garceta.es)
- [Programación de servicios y procesos - CARLOS ALBERTO CORTIJO BON [Sintesis]](https://www.sintesis.com/desarrollo%20de%20aplicaciones%20multiplataforma-341/programaci%C3%B3n%20de%20servicios%20y%20procesos-ebook-2910.html)
- [Programació de serveis i processos - JOAR ARNEDO MORENO, JOSEP CAÑELLAS BORNAS i JOSÉ ANTONIO LEO MEGÍAS [IOC]](https://ioc.xtec.cat/materials/FP/Recursos/fp_dam_m09_/web/fp_dam_m09_htmlindex/index.html)
-  GitHub repositories:
  - https://github.com/ajcpro/psp
  - https://oscarmaestre.github.io/servicios/index.html
  - https://github.com/juanro49/DAM/tree/master/DAM2/PSP
  - https://github.com/pablohs1986/dam_psp2021
  - https://github.com/Perju/DAM
  - https://github.com/eldiegoch/DAM
  - https://github.com/eldiegoch/2dam-psp-public
  - https://github.com/franlu/DAM-PSP
  - https://github.com/ProgProcesosYServicios
  - https://github.com/joseluisgs
  - https://github.com/oscarnovillo/dam2_2122
  - https://github.com/PacoPortillo/DAM_PSP_Tarea02_La-Cena-de-los-Filosofos
