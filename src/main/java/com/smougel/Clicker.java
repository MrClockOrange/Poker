package com.smougel;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Properties;

/**
 * Created by sylvainmougel on 20/06/15.
 */
public class Clicker {


    private final Robot robot;
    private final Properties clickerProperties;
    private int randX;

    public Clicker(Robot r, Properties prop) {
        robot = r;
        clickerProperties = prop;
    }

    public void fold() {
        System.out.println("Fold !!!");
        robot.mouseMove(
                Integer.valueOf(clickerProperties.getProperty("fold.pos.X")) + getRandX(),
                Integer.valueOf(clickerProperties.getProperty("fold.pos.Y")) + 44 + getRandY()
        );
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void call() {
        System.out.println("Call !!!");
        robot.mouseMove(
                Integer.valueOf(clickerProperties.getProperty("call.pos.X")) + getRandX(),
                Integer.valueOf(clickerProperties.getProperty("call.pos.Y")) + 44 + getRandY()
        );
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void betRaise() {
        System.out.println("Bet !!!");
        robot.mouseMove(
                Integer.valueOf(clickerProperties.getProperty("bet.pos.X")) + getRandX(),
                Integer.valueOf(clickerProperties.getProperty("bet.pos.Y")) + 44 + getRandY()
        );
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }


    public int getRandX() {
        int max = Integer.valueOf(clickerProperties.getProperty("button.size.X"));
        int min = - max;
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public int getRandY() {
        int max = Integer.valueOf(clickerProperties.getProperty("button.size.Y"));
        int min = - max;
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
