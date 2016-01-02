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

    public Clicker(Robot r, Properties prop) {
        robot = r;
        clickerProperties = prop;
    }

    public void fold() {
        System.out.println("Folding !!!");
        robot.mouseMove(
                Integer.valueOf(clickerProperties.getProperty("fold.pos.X")),
                Integer.valueOf(clickerProperties.getProperty("fold.pos.Y")) + 44
        );
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void call() {
        System.out.println("Calling !!!");
        robot.mouseMove(
                Integer.valueOf(clickerProperties.getProperty("call.pos.X")),
                Integer.valueOf(clickerProperties.getProperty("call.pos.Y")) + 44
        );
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void betRaise() {
        System.out.println("Bet !!!");
        robot.mouseMove(
                Integer.valueOf(clickerProperties.getProperty("bet.pos.X")),
                Integer.valueOf(clickerProperties.getProperty("call.pos.Y")) + 44
        );
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }


}
