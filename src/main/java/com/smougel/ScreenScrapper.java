package com.smougel;

import com.smougel.context.Table;
import com.smougel.handparser.HandParser;
import com.sun.java.util.jar.pack.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Created by sylvainmougel on 26/02/15.
 */
public class ScreenScrapper {
    private static final String HAND_HISTORY_ROOT = "/Users/sylvainmougel/" +
            "Library/Application Support/PokerStarsFR/HandHistory/MrCLockOran/";

    /** The table position and size */
    private final Rectangle rectangle;

    private final Robot robot;

    /* The object representing the table */
    private final Table table;

    /* The object used to bet by clicking on the screen */
    private final Clicker clicker;

    /* the properties of the table */
    private final Properties properties;

    private final  HandParser handParser;


    ScreenScrapper(Properties p) throws AWTException, IOException {
        properties = p;
        handParser = new HandParser(HAND_HISTORY_ROOT);
        robot = new Robot();
        rectangle = new Rectangle(
                Integer.valueOf(properties.getProperty("window.ori.X")),
                Integer.valueOf(properties.getProperty("window.ori.Y")),
                Integer.valueOf(properties.getProperty("window.size.X")),
                Integer.valueOf(properties.getProperty("window.size.Y"))
                );
        table = new Table(properties);
        clicker = new Clicker(robot, properties);
    }


    private BufferedImage getImage() {
        return robot.createScreenCapture(rectangle);
    }

    private Color getPlayingPixelColor() {
        return robot.getPixelColor(215, 310);
    }


    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        Properties properties = new java.util.Properties();
        properties.load(ScreenScrapper.class.getResourceAsStream("/table.properties"));

        ScreenScrapper sc = new ScreenScrapper(properties);

        sc.table.update(sc.getImage());
        //sc.table.display();

        while(true) {

            Thread.sleep(500);
            if (sc.getPlayingPixelColor().getBlue() > 150) {
                System.out.println("My turn !!!");
                sc.table.update(sc.getImage());
                Thread.sleep(500);
                //sc.clicker.fold();
                Thread.sleep(5000);

            }
            //System.out.println(sc.table.toString());

        }

    }
}
