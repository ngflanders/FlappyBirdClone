import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Project: FlappyBirdClone
 * Author:  Nick Flanders
 * Date:    12/28/2015.
 */
public class BirdApplet extends JApplet implements Runnable{

    int x_pos = 80;
    int y_pos = 100;
    int radius = 20;
    int speed = 4;
    boolean upwardMotion;

    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        while (true) {
            //Check for ball's Y position
            if((y_pos + radius >= getBounds().height)){
                upwardMotion = true;
            }else if((y_pos - radius) <= 0){
                upwardMotion = false;
            }

            //Move ball forward or backward
            if(upwardMotion){
                y_pos -= speed;
            }else{
                y_pos += speed;
            }

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
        for (Frame frame : Frame.getFrames()) {
            frame.setMenuBar(null);
            frame.pack();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("Click");
                upwardMotion = !upwardMotion;
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //I'm guessing you make this a switch statement
                //in the case that we want to add more options, yet?
                //aka, "esc" closes the game or something.
                //"1" starts a new game?
                switch (e.getKeyCode()) {
                    case (KeyEvent.VK_SPACE):
                        System.out.println("Space");
                        upwardMotion = !upwardMotion;
                        break;
                }
            }
        });
    }

    public void start() {
        Thread th = new Thread(this);
        th.start();
    }

    public void stop() { }

    public void destroy() { }

    public void paint (Graphics g2) {
        super.paint(g2);
        Graphics2D g = (Graphics2D) g2;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.red);

        g.fillOval(x_pos - radius, y_pos - radius, 2 * radius, 2 * radius);
    }
}