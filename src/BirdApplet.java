import javax.imageio.ImageIO;
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
    private int scroll = 0;
    private int imgWidth;
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

            // decrement scroll variable for shifting the background
            scroll -= 1;

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

            if (  y_pos + radius >= image.getHeight() )
                break;

            if ( y_pos - radius <= 0 )
                y_pos = radius;
        }

    }


    /**
     * Initialize all components of the Application.
     *
     * [1] Setting the size of the applet window
     *
     * [2] Here we remove the MenuBar from all frames. Instead of trying to locate
     * the exact frame which contains the MenuBar, we cycle through all of them
     * and eventually remove it.
     *
     * [3] We start by trying to read the image from the source file. If that
     * succeeds, we are able to add the loaded image to |image|. Otherwise,
     * print what went wrong to the console.
     *
     * [4] Adding a mouse listener to the applet so we can control the bird.
     *
     * [5] Adding a keyboard listener to the applet so we can control the bird.
     */
    public void init() {
        //See point [1]
        setSize(288,388);

        //See point [2]
        for (Frame frame : Frame.getFrames()) {
            frame.setMenuBar(null);
            frame.pack();
        }

        //See point [3]
        try {
            image = ImageIO.read(this.getClass().getResourceAsStream("bg.png"));
            imgWidth = image.getWidth();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //See point [4]
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                resetSpeed();
            }
        });

        //See point [5]
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case (KeyEvent.VK_SPACE):
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

        //Draws the background image
        g.drawImage(image, scroll,0,null);
        g.drawImage(image, scroll+imgWidth, 0, null);

        // if circle reaches midpoint between two images
        // reset the scroll variable to shift the images
        if (Math.abs(scroll) == imgWidth) {
            scroll = 0;
            System.out.println("==");
        }

        g.setColor(Color.red);
        g.fillOval(x_pos - radius, y_pos - radius, 2 * radius, 2 * radius);
    }

    private void resetSpeed() {
        speed = 6;
    }
}