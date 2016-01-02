package com.smougel.cards;

/**
 * Created by sylvainmougel on 02/01/16.
 */
public interface ICard extends Comparable<ICard> {
    Color getColor();
    Values getValue();

    boolean isAdjacent(ICard current);

    boolean isSameColor(ICard current);
}
