package com.smougel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;



/**
 * Created by sylvainmougel on 26/02/15.
 */
public class ScreenScrapper {

    /** The table position and size */
    private final Rectangle rectangle;

    private final Robot robot;

    /* The object representing the table */
    private final Table table;


    ScreenScrapper(Rectangle r) throws AWTException, IOException {
        robot = new Robot();
        rectangle = r;
        table = new Table();
    }


    private BufferedImage getImage() {
        return robot.createScreenCapture(rectangle);
    }

    private Color getPlayingPixelColor() {
        return robot.getPixelColor(215, 310);
    }


    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        ScreenScrapper sc = new ScreenScrapper(new Rectangle(0, 44, 477,328));
        sc.table.update(sc.getImage());
        //sc.table.display();

        while(true) {

            Thread.sleep(500);
            if (sc.getPlayingPixelColor().getBlue() > 150) {
                System.out.println("My turn !!!");
                sc.table.update(sc.getImage());
                Thread.sleep(5000);

            }
            //System.out.println(sc.table.toString());

        }

    }
}
