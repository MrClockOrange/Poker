package com.smougel.utils;

/**
 * Created by sylvainmougel on 15/03/15.
 */
public class Position {

    private final int x;
    private final int y;

    public Position(int x_, int y_) {
        x = x_;
        y = y_;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "X : " + x + " Y : " + y;
    }
}
