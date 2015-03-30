package com.smougel.utils;

/**
 * Created by sylvainmougel on 15/03/15.
 */
public class Position {

    private final int upper;
    private final int left;

    public Position(int l, int u) {
        left = l;
        upper = u;

    }

    public int getUpper() {
        return upper;
    }

    public int getLeft() {
        return left;
    }
}
