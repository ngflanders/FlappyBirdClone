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

    private Image dbImage;
    private Graphics dbg;

    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        while (true) {

            x_pos++;

            repaint();

            try {
                Thread.sleep(20);
                //throw new InterruptedException();
            } catch (InterruptedException e) {
                // nothing
            }

            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        }
    }


    public void init() { }

    public void start() {
        Thread th = new Thread(this);
        th.start();
    }

    public void update(Graphics g) {
        if (dbImage == null) {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();

            dbg.setColor(getBackground());
            dbg.fillRect(0,0,this.getSize().width, this.getSize().height);

            dbg.setColor(getForeground());
            paint(dbg);

            g.drawImage(dbImage,0,0, this);

        }
    }

    public void stop() { }

    public void destroy() { }

    public void paint (Graphics g) {
        g.setColor(Color.red);

        g.fillOval(x_pos - radius, y_pos - radius, 2 * radius, 2 * radius);
    }


}
