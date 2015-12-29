import java.applet.Applet;
import java.awt.*;

/**
 * Project: FlappyBirdClone
 * Author:  Nick Flanders
 * Date:    12/28/2015.
 */
public class BirdApplet extends Applet implements Runnable {

    int x_pos = 10;
    int y_pos = 100;
    int radius = 20;

    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        while (true) {

            x_pos++;

            repaint();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        }
    }


    public void init() {
        setSize(340,470);
        Frame[] frames = Frame.getFrames();
        for (Frame frame : frames) {
            frame.setMenuBar(null);
            frame.pack();
        }
    }

    public void start() {
        Thread th = new Thread(this);
        th.start();
    }

    public void stop() { }

    public void destroy() { }

    public void paint (Graphics g2) {
        /**
         * Added to fix antialiasing
         */
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.red);

        g.fillOval(x_pos - radius, y_pos - radius, 2 * radius, 2 * radius);
    }


}