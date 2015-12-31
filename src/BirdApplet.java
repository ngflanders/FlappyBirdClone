import javax.imageio.ImageIO;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Project: FlappyBirdClone
 * Author:  Nick Flanders
 * Date:    12/28/2015.
 */
public class BirdApplet extends Applet implements Runnable{

    private int score = 0;
    private Font scoreFont;
    private Image dbImage;
    private Graphics dbGraphics;
    private boolean isStartScreen = true;
    private int x_pos = 80;
    private int y_pos = 100;
    private int radius = 20;
    private int scroll = 0;
    private int bgImgWidth;
    private int distance = 0;
    private double speed = -5;
    private double acc = -.25;
    private BufferedImage bgImage;
    private BufferedImage birdImage;
    private Random rand = new Random();


    public void run() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        while (isStartScreen) {

            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


        while (true) {

            // decrement scroll variable for shifting the background
            scroll -= 1;
            distance += 1;
            if (distance % (bgImgWidth-(x_pos-radius)) == 0)
                score += 1;

            // gravity changes circles speed
            speed += acc;

            // circle's position changes by speed
            y_pos -= speed;

            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            if (  y_pos + radius >= bgImage.getHeight() )
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
     * [3] We start by trying to read the bgImage from the source file. If that
     * succeeds, we are able to add the loaded bgImage to |bgImage|. Otherwise,
     * print what went wrong to the console.
     *
     * [4] Adding a mouse listener to the applet so we can control the bird.
     *
     * [5] Adding a keyboard listener to the applet so we can control the bird.
     *
     * [6] Initializing the score font by importing the scoreFont.ttf from the
     * project.
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
            bgImage = ImageIO.read(this.getClass().getResourceAsStream("bg.png"));
            birdImage = ImageIO.read(this.getClass().getResourceAsStream("bird.png"));
            bgImgWidth = bgImage.getWidth();
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
                        if (isStartScreen) {
                            isStartScreen = false;
                        }
                        resetSpeed();
                        break;
                }
            }
        });

        //See point [6]
        try {
            InputStream myStream = new BufferedInputStream(this.getClass().getResourceAsStream("scoreFont.ttf"));
            Font ttfBase = Font.createFont(Font.TRUETYPE_FONT, myStream);
            scoreFont = ttfBase.deriveFont(Font.PLAIN, 24);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void start() {
        Thread th = new Thread(this);
        th.start();
    }


    public void stop() { }


    public void destroy() { }


    /**
     * Added an update method to double-buffer our game. Double
     * buffering removed the "flashing"
     *
     * [1] If the |dbImage| is null, initialize it to the current
     * image of the screen. Also initialize |dbGraphics| to the
     * |dbImage|'s graphics.
     *
     * [2] Set our background color for the double buffer and also
     * fill the rectangle (background) with this color.
     *
     * [3] Set the foreground color, and then paint the graphics
     * or foreground to the screen.
     *
     * [4] Draw the double buffered image to the screen.
     */
    public void update(Graphics g) {
        //See point [1]
        if(dbImage == null){
            dbImage = createImage(this.getWidth(), this.getHeight());
            dbGraphics = dbImage.getGraphics();
        }

        //See point [2]
        dbGraphics.setColor(getBackground());
        dbGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());

        //See point [3]
        dbGraphics.setColor(getForeground());
        paint(dbGraphics);

        //See point [4]
        g.drawImage(dbImage, 0, 0, this);
    }


    public void paint (Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        super.paint(g); //Do not move/remove.
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Draws the background bgImage
        g.drawImage(bgImage, scroll,0,null);
        g.drawImage(bgImage, scroll+ bgImgWidth, 0, null);

        // if circle reaches midpoint between two images
        // reset the scroll variable to shift the images
        if (Math.abs(scroll) == bgImgWidth)
            scroll = 0;

        g.drawImage(birdImage, x_pos-radius, y_pos-radius,null);

        // start screen instructions
        g.setFont(scoreFont);
        if (isStartScreen) {
            g.drawString("Press space to start", 5, 25);
        }else{
            g.drawString("" + score, getWidth()/2 - (String.valueOf(score).length() * 6), 55);
        }

        // TODO generate pipes randomly

        g.drawRect(scroll + bgImgWidth ,0, 20, bgImage.getHeight()/3);
        g.drawRect(scroll+bgImgWidth, (2*bgImage.getHeight()/3), 20, bgImage.getHeight()/3);

    }

    private void resetSpeed() {
        speed = 4.5;
    }

    private int randInt(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }
}