package com.smougel;

import com.smougel.context.Table;
import com.smougel.handparser.Hand;
import com.smougel.handparser.HandParser;
import com.smougel.ia.EAction;
import com.smougel.ia.EquityPlayer;
import com.smougel.ia.SameActionAlways;
import com.smougel.ia.IPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import java.util.SortedMap;


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

    private HandParser handParser;


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

        handParser = new HandParser("/Users/sylvainmougel/" +
                "Library/Application Support/PokerStarsFR/HandHistory/MrCLockOran/");
    }

    public static long getRandTime() {
        int max = 5000;
        int min = 2000;
        return min + (long)(Math.random() * ((max - min) + 1));
    }


    private BufferedImage getImage() {
        return robot.createScreenCapture(rectangle);
    }

    private Color getPlayingPixelColor() {
        return robot.getPixelColor(215, 310);
    }


    public static void main(String[] args) throws AWTException, IOException, InterruptedException {
        Properties properties = new java.util.Properties();
        properties.load(ScreenScrapper.class.getResourceAsStream("/nine_players.properties"));

        ScreenScrapper sc = new ScreenScrapper(properties);

        sc.table.update(sc.getImage());
        //sc.table.display();

        while (true) {

            Thread.sleep(500);
            if (sc.getPlayingPixelColor().getBlue() > 150) {
                System.out.println("My turn !!!");

                // Update the table with a new screenshot
                sc.table.update(sc.getImage());

                // get the player name
                sc.handParser.parse();
                SortedMap<String, Hand> handMap = sc.handParser.getHands();
                String lastHand = handMap.lastKey();
                System.out.println(handMap.get(lastHand).getPlayersName());

                IPlayer player = new SameActionAlways(EAction.CALL);
                IPlayer eplayer = new EquityPlayer();


                // Compute action
                EAction action = eplayer.play(sc.table.getHandState(), sc.table.getPlayersStateHistory());
                System.out.println("Computed action " + action);

                // Wait for a while and play
                long waitTime = getRandTime();
                System.out.println("Wait for : " + waitTime);
                Thread.sleep(waitTime);


                switch (action) {
                    case BET:
                        sc.clicker.betRaise();
                        break;
                    case CALL:
                        sc.clicker.call();
                        break;
                    case FOLD:
                        sc.clicker.fold();
                        break;
                }
                Thread.sleep(5000);

            }
            //System.out.println(sc.table.toString());

        }

    }
}
