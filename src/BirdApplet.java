import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Project: FlappyBirdClone
 * Author:  Nick Flanders
 * Date:    12/28/2015.
 */
public class BirdApplet extends Applet implements Runnable{

    private int x_pos = 80;
    private int y_pos = 100;
    private int radius = 20;
    private double speed = -5;
    private double acc = -.2;
    private BufferedImage image;

    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {

            // gravity changes circles speed
            speed += acc;

            // circle's position changes by speed
            y_pos -= speed;

            repaint();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            if (  (y_pos - radius) <= 0  || (y_pos + radius >= image.getHeight() ) )
                break;
        }
    }


    public void init() {
        setSize(288,388);
        for (Frame frame : Frame.getFrames()) {
            frame.setMenuBar(null);
            frame.pack();
        }

        try {
            image = ImageIO.read(this.getClass().getResourceAsStream("bg.png")); //Load the background image
        } catch (IOException e) {
            e.printStackTrace(); //If it fails, print it out.
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("Click");
                resetSpeed();
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
                        //System.out.println("Space");
                        resetSpeed();
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
        Graphics2D g = (Graphics2D) g2;
        super.paint(g); //Do not move/remove.
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(image, 0, 0, null); //Draws the background image

        g.setColor(Color.red);
        g.fillOval(x_pos - radius, y_pos - radius, 2 * radius, 2 * radius);
    }

    private void resetSpeed() {
        speed = 8;
    }

}