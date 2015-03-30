package com.smougel.cards;

import com.smougel.utils.PixelMapper;

import java.util.EnumSet;

/**
 * Created by sylvainmougel on 18/03/15.
 */
public enum Color {

    NONE("", PixelMapper.NONE_RELEVANT_PIXELS),
    HEARTS("h", PixelMapper.HEARTS_RELEVANT_PIXELS),
    DIAMONDS("d", PixelMapper.DIAMONDS_RELEVANT_PIXELS),
    SPADES("s", PixelMapper.SPADES_RELEVANT_PIXELS),
    CLUBS("c", PixelMapper.CLUBS_RELEVANT_PIXELS),;

    private final int[][] relevantPixels;
    private final String strRepr;

    private final static EnumSet<Color> trueValues =
            EnumSet.of(
                    Color.SPADES,
                    Color.CLUBS,
                    Color.DIAMONDS,
                    Color.HEARTS);

    Color(String repr, int[][] rp) {
        relevantPixels = rp;
        strRepr = repr;
    }

    public static EnumSet<Color> reds() {
        return EnumSet.of(Color.NONE, Color.HEARTS, Color.DIAMONDS);
    }

    public static EnumSet<Color> blacks() {
        return EnumSet.of(Color.NONE, Color.SPADES, Color.CLUBS);
    }

    public static EnumSet<Color> trueValues() {
        return trueValues;
    }

    public int[][] getRelevantPixels() {
        return relevantPixels;
    }

    public String getStrRepr() {
        return strRepr;
    }
}
