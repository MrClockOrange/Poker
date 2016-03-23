package com.smougel.cards;

import com.smougel.utils.PixelMapper;

import java.util.Comparator;
import java.util.EnumSet;

/**
 * Created by sylvainmougel on 16/03/15.
 */
public enum Values  {

    NONE("", PixelMapper.NONE_RELEVANT_PIXELS, 0),
    TWO("2", PixelMapper.TWO_RELEVANT_PIXELS,2),
    EIGHT("8", PixelMapper.EIGHT_RELEVANT_PIXELS, 8),
    FIVE("5", PixelMapper.FIVE_RELEVANT_PIXELS, 5),
    TEN("T", PixelMapper.TEN_RELEVANT_PIXELS, 10),
    JACK("J", PixelMapper.JACK_RELEVANT_PIXELS,11),
    QUEEN("Q", PixelMapper.QUEEN_RELEVANT_PIXELS, 12),
    KING("K", PixelMapper.KING_RELEVANT_PIXELS, 13),
    NINE("9", PixelMapper.NINE_RELEVANT_PIXELS, 9),
    SEVEN("7", PixelMapper.SEVEN_RELEVANT_PIXELS, 7),
    SIX("6", PixelMapper.SIX_RELEVANT_PIXELS, 6),
    FOUR("4", PixelMapper.FOUR_RELEVANT_PIXELS, 4),
    ACE("A", PixelMapper.ACE_RELEVANT_PIXELS, 14),
    THREE("3", PixelMapper.THREE_RELEVANT_PIXELS, 3),;

    private final int[][] relevantPixels;
    private final String strRepr;
    private final int intVal;

    private final static EnumSet<Values> trueValues =
            EnumSet.of(
                    Values.TWO,
                    Values.THREE,
                    Values.FOUR,
                    Values.FIVE,
                    Values.SIX,
                    Values.SEVEN,
                    Values.EIGHT,
                    Values.NINE,
                    Values.TEN,
                    Values.JACK,
                    Values.QUEEN,
                    Values.KING,
                    Values.ACE
            );


    Values(String repr, int[][] relevantPix, int val) {
        relevantPixels = relevantPix;
        strRepr = repr;
        intVal = val;

    }





    public static EnumSet<Values> trueValues() {
        return trueValues;
    }



    public int[][] getRelevantPixels() {
        return relevantPixels;
    }

    public String getStrRepr() {
        return strRepr;
    }

    public int getIntVal() {
        return intVal;
    }

    public static Comparator<Values> getComparator() {
        return new Comparator<Values>() {
            @Override
            public int compare(Values v1, Values v2) {
                return v2.getIntVal() - v1.getIntVal();

            }
        };
    }


}
