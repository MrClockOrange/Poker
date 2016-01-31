package com.smougel;

import com.smougel.context.Table;
import com.smougel.handparser.Hand;
import com.smougel.handparser.HandParser;
import com.smougel.ia.EAction;
import com.smougel.ia.EquityPlayer;
import com.smougel.ia.SameActionAlways;
import com.smougel.ia.IPlayer;
import com.smougel.utils.Position;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;
import java.util.SortedMap;


/**
 * Created by sylvainmougel on 26/02/15.
 */
public class ScreenScrapper extends Thread {
    private static final String HAND_HISTORY_ROOT = "/Users/sylvainmougel/" +
            "Library/Application Support/PokerStarsFR/HandHistory/MrCLockOran/";

    /** The table position and size */
    private final Rectangle rectangle;
    private final Position windowStartPos;

    private Robot robot;

    /* The object representing the table */
    private Table table;

    /* The object used to bet by clicking on the screen */
    private  Clicker clicker;

    /* The properties of the table */
    private final Properties properties;

    /* The hand parser used to parse hand history */
    private HandParser handParser;


    public ScreenScrapper(Position windowPos, int tbNb) throws IOException, AWTException {
        super("Table " + tbNb);
        properties = new java.util.Properties();
        properties.load(ScreenScrapper.class.getResourceAsStream("/nine_players.properties"));
        windowStartPos = windowPos;
        rectangle = new Rectangle(
                windowPos.getX(),
                windowPos.getY(),
                Integer.valueOf(properties.getProperty("window.size.X")),
                Integer.valueOf(properties.getProperty("window.size.Y"))
        );
        robot = new Robot();
        clicker = new Clicker(robot, properties, windowPos);
        init();

    }

    private void init() throws AWTException, IOException {
        table = new Table(properties);
        handParser = new HandParser(HAND_HISTORY_ROOT);
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
        return robot.getPixelColor(215 + windowStartPos.getX(), 266 + windowStartPos.getY());
    }


    @Override
    public void run() {
        try {
            table.update(getImage());

            //sc.table.display();

            while (true) {

                Thread.sleep(500);

                if (getPlayingPixelColor().getBlue() > 150) {
                    System.out.println("My turn !!!");

                    // Update the table with a new screenshot
                    table.update(getImage());

                    // get the players name
                    handParser.parse();
                    SortedMap<String, Hand> handMap = handParser.getHands();
                    String lastHand = handMap.lastKey();
                    System.out.println(handMap.get(lastHand).getPlayersName());

                    IPlayer player = new SameActionAlways(EAction.CALL);
                    IPlayer eplayer = new EquityPlayer();


                    // Compute action
                    EAction action = eplayer.play(table.getHandState(), table.getPlayersStateHistory());
                    System.out.println("Computed action " + action);

                    // Wait for a while and play
                    long waitTime = getRandTime();
                    System.out.println("Wait for : " + waitTime);
                    Thread.sleep(waitTime);


                    switch (action) {
                        case BET:
                            clicker.betRaise();
                            break;
                        case CALL:
                            clicker.call();
                            break;
                        case FOLD:
                            clicker.fold();
                            break;
                    }
                    Thread.sleep(5000);

                }
                //System.out.println(sc.table.toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
