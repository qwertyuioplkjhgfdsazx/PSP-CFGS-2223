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

# Depuración de aplicaciones multihilo

Depurar aplicaciones es una de las tareas que más a menudo realizamos los programadores. Nos permite verificar el estado de nuestra aplicación en un punto concreto. Poner marcas en el código o utilizar las herramientas de depuración (debugging, en inglés) con todas las utilidades que nos proporcionan algunos IDE, es una tarea imprescindible para revisar nuestra aplicación. Depurar una aplicación secuencial en la que únicamente existe una línea de ejecución es simple con una herramienta de depuración. Vamos avanzando paso a paso al hilo de ejecución y vamos mirando valores de variable, condiciones… hasta que terminamos. Pero en la programación multihilo hay más de un hilo de ejecución. Deberemos ir consultando cada hilo por separado para ver su funcionamiento y qué valores va tomando.

Veremos cómo podemos depurar una aplicación multihilo y utilizaremos la herramienta para detectar posibles errores como el interbloqueo en una aplicación.

Para realizar las pruebas utilizaremos el IDE de Netbeans. Con otros IDEs el funcionamiento es similar. Para probar cómo consultar los hilos abriremos un proyecto y seleccionaremos el proyecto en la ventana de proyectos y escogeremos la opción Debug. El proyecto que analizaremos es el siguiente: 

`Shuttle.java`

```java
package UD02.SpaceShuttle;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

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
```

`ShuttlePanel.java`

```java
package UD02.SpaceShuttle;

import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;

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
```

`SpaceShuttle.java`

```java
package UD02.SpaceShuttle;

import javax.swing.JFrame;

public class SpaceShuttle extends javax.swing.JFrame {

    public SpaceShuttle() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
            java.util.logging.Logger.getLogger(SpaceShuttle.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SpaceShuttle.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SpaceShuttle.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SpaceShuttle.class.getName()).log(
                    java.util.logging.Level.SEVERE, null, ex);
        }

        SpaceShuttle f = new SpaceShuttle();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Space Shuttle");
        f.setSize(400, 600);
        f.setContentPane(new ShuttlePanel());
        f.setVisible(true);
    }
}
```

`shuttle.png`

![shuttle](/assets/shuttle.png)

> Los puntos de ruptura (*breakpoints* en inglés) son marcas que indican en el IDE donde se debe detener la ejecución del código cuando se realice en modo depuración.

Si no tenemos puestos puntos de ruptura la aplicación se ejecutará hasta terminar. La aplicación es un panel en el que se muestran unas imágenes que se mueven. Cada imagen (nave espacial) es controlada por un hilo. La aplicación crea 3 hilos, por tanto habrá tres imágenes en el panel en movimiento.

Aplicación de naves en funcionamiento:

![image-20220827183918727](/assets/SpaceShuttle.png)

En la ventana de depuración podemos ver los tres hilos creados, con el nombre de `Shuttle Thread 0`, `Shuttle Thread 1` y Shuttle Thread 2. El resto de hilos creados son el hilo principal y los del sistema.

Depuración de hilos:

![Thread Debug](/assets/ThreadsDebug.png)

A la derecha del nombre del hilo hay un símbolo de pausa. Si lo clicamos el hilo se detendrá. El símbolo se convertirá en el triángulo de *play*. Cuando el hilo está detenido podemos ver que sale una carpeta que se puede expandir. Dentro vemos la lista de llamadas del hilo. Ya que el hilo está detenido, la nave (la imagen que se mueve por el panel) se detiene también.

Hilo detenido:

![Thread Stop](/assets/ThreadStop.png)

Si hemos puesto algún punto de interrupción, la aplicación se comporta como si no trabajara con hilos. La ejecución se detiene en este punto.

Para poner un punto de interrupción debemos hacer clic en el margen izquierdo del editor junto al número de línea en el que queremos poner el punto de interrupción. Aparecerá un cuadrado rojo que marca el punto.

En la siguiente figura se ve que el punto de interrupción está puesto cuando el hilo ejecuta el movimiento de la imagen.

Punto de interrupción:

![Breakpoint](/assets/breakpoint.png)

En la ventana de depuración se ve cómo se está ejecutando el primer hilo que llega. Pulsando las teclas de función `F7` o `F8` irá avanzando la línea de programa sobre el hilo que está activo. El resto de hilos están listos para ejecutarse. Si queremos que los otros hilos empiecen deberemos clicar sobre la flecha que está debajo de la ventana de depuración y seleccionar el hilo que queremos ejecutar. Una vez que todos los hilos se están ejecutando, podemos ver la ejecución de uno u otro hilo picando sobre el hilo en la ventana de depuración. El hilo que controlamos aparece con un marco verde.

A la izquierda del código aparecen los iconos de la rueda dentada que nos indican dónde están detenidos los hilos que no estamos depurando. Si pulsamos sobre la rueda dentada con el botón derecho podemos ver cuál es el hilo que está detenido en este punto.

Intercambiando la depuración de los hilos:

![Thread Change](/assets/ThreadChange.png)

Si queremos cambiar para continuar depurando otro hilo lo seleccionaremos de la lista. Únicamente podemos depurar un hilo con un punto de ruptura (*breakpoint*, en inglés). Cuando clicamos sobre `F7` o `F8` para ir avanzando únicamente avanza un hilo, los demás no se detienen en el punto de interrupción.

# Buscando interbloqueos

Depurar aplicaciones es bastante simple y muy útil. Los IDE proporcionan cada vez herramientas que nos ayudan más a la corrección de errores. Netbeans en su herramienta de depuración incluye una función que detecta interbloqueos automáticamente. Usaremos el siguiente fragmento de código.

`Deadlock.java`

```java
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
```

Para utilizarla debemos crear puntos de ruptura. Los puntos de interrupción se ponen donde nosotros pensamos que existe el punto crítico de nuestra aplicación y donde podría ser la fuente de los errores. En este caso los pondremos en el método `run()` de la clase que hereda `threads` o implementa `runnable`. En este caso en las líneas 7 y 23.

Ejecutamos la aplicación en modo depuración y vemos cómo se detiene en los puntos de interrupción.

Punto de interrupción:

![Deadlock breakpoint](/assets/breakpointdeadlock.png)

En este punto podemos continuar la ejecución (teclas de función `F7` o F8`)` y ver cómo avanza la aplicación y van variando los valores de las variables, la línea de ejecución, etc.

Para detectar el interbloqueo debemos terminar la ejecución. Podemos pulsar sobre cada botón derecho y seleccionar `resume`, podemos pulsar `F5` o pulsar sobre el icono de la barra de herramientas del triángulo blanco (*play*) sobre fondo verde. El resultado que tendremos es la aplicación ejecutándose. Para ver si se ha producido un interbloqueo debemos ir al menú de depuración y hacer clic sobre la opción `Check for DeadLock`.

Examinar interbloqueo:

![Deadlock check](/assets/DeadlockCheck.png)

Automáticamente Netbeans nos dirá si existen o no interbloqueos. Nos mostrará un mensaje diciendo si lo ha encontrado o no.

Mensaje de interbloqueo:

![Deadlock detected](/assets/DeadlockDetected.png)

La ventana de depuración nos dice dónde se encuentran los hilos bloqueados en situación de interbloqueo.

Las herramientas de depuración son muy útiles para encontrar posibles fallos de programación. Cuando programamos utilizando muchos hilos, la depuración de aplicaciones es fundamental.

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

