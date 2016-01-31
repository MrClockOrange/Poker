package com.smougel;

import com.smougel.utils.Position;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Properties;

/**
 * Created by sylvainmougel on 20/06/15.
 */
public class Clicker {


    private final Robot robot;
    private final Position foldPosition;
    private final Position windowPos;
    private final Position callPosition;
    private final Position raisePosition;
    private final Integer buttonX;
    private final Integer buttonY;

    public Clicker(Robot r, Properties prop) {
        robot = r;
        buttonX = Integer.valueOf(prop.getProperty("button.size.X"));
        buttonY = Integer.valueOf(prop.getProperty("button.size.Y"));
        windowPos = new Position(
                Integer.valueOf(prop.getProperty("window.ori.X")),
                Integer.valueOf(prop.getProperty("window.ori.Y"))
        );
        foldPosition = new Position(
                Integer.valueOf(prop.getProperty("fold.pos.X")),
                Integer.valueOf(prop.getProperty("fold.pos.Y"))
        );
        callPosition = new Position(
                Integer.valueOf(prop.getProperty("call.pos.X")),
                Integer.valueOf(prop.getProperty("call.pos.Y"))
        );
        raisePosition = new Position(
                Integer.valueOf(prop.getProperty("bet.pos.X")),
                Integer.valueOf(prop.getProperty("bet.pos.Y"))
        );
    }

    public Clicker(Robot r, Properties properties, Position wp) {
        robot = r;
        buttonX = Integer.valueOf(properties.getProperty("button.size.X"));
        buttonY = Integer.valueOf(properties.getProperty("button.size.Y"));
        windowPos = wp;
        foldPosition = new Position(
                Integer.valueOf(properties.getProperty("fold.pos.X")),
                Integer.valueOf(properties.getProperty("fold.pos.Y"))
        );
        callPosition = new Position(
                Integer.valueOf(properties.getProperty("call.pos.X")),
                Integer.valueOf(properties.getProperty("call.pos.Y"))
        );
        raisePosition = new Position(
                Integer.valueOf(properties.getProperty("bet.pos.X")),
                Integer.valueOf(properties.getProperty("bet.pos.Y"))
        );
    }

    public void fold() {
        System.out.println("Fold !!!");
        robot.mouseMove(
                 foldPosition.getX() + windowPos.getX() + getRandX(),
                 foldPosition.getY() + windowPos.getY() + getRandY()
        );
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void call() {
        System.out.println("Call !!!");
        robot.mouseMove(
                callPosition.getX() + windowPos.getX() + getRandX(),
                callPosition.getY() + windowPos.getY() + getRandY()
        );
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    public void betRaise() {
        System.out.println("Bet !!!");
        robot.mouseMove(
                raisePosition.getX() + windowPos.getX() + getRandX(),
                raisePosition.getY() + windowPos.getY() + getRandY()
        );
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }


    public int getRandX() {
        int max = buttonX;
        int min = - max;
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public int getRandY() {
        int max = buttonY;
        int min = - max;
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
