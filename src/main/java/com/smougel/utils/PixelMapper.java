package com.smougel.utils;

/**
 * Created by sylvainmougel on 16/03/15.
 */
public class PixelMapper {


    public static final int[][] NONE_RELEVANT_PIXELS = {
            {0, 0}, {0, 10},
    };

    public static final int[][] ACE_RELEVANT_PIXELS = {
            {1, 5},
            {11, 1}, {11, 2}, {11, 3},  {11, 9}, {11, 10},
    };

    public static final int[][] TWO_RELEVANT_PIXELS = {

            {11, 2}, {11, 3}, {11, 4}, {11, 5}, {11, 6}, {11, 7}, {11, 8},
    };

    public static final int[][] THREE_RELEVANT_PIXELS = {
            {1, 5}, {1, 6},
            {3, 7}, {3, 8},
            {5, 5}, {5, 6}, {5, 7},
    };


    public static final int[][] FOUR_RELEVANT_PIXELS = {
            {8, 2}, {8, 3}, {8, 4}, {8, 5},
    };

    public static final int[][] FIVE_RELEVANT_PIXELS = {
            {0, 4}, {0, 5}, {0, 6},
            //{2, 2}, {2, 3},
            {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6}, {5, 7}, {5, 8},
    };


    public static final int[][] SIX_RELEVANT_PIXELS = {
            {5, 2}, {5, 3}, {5, 4}, {5, 5}, {5, 6},
            {7, 2}, {7, 3},
    };

    public static final int[][] SEVEN_RELEVANT_PIXELS = {
            {1, 2}, {1, 3}, {1, 4}, {1, 5}, {1, 6},
            {11, 5}, {11, 6}

    };

    public static final int[][] EIGHT_RELEVANT_PIXELS = {
            {3, 7}, {3, 8},
            {7, 2}, {7, 3}, {7, 8}, {7, 9},
            {8, 2}, {8, 3}, {8, 8}, {8, 9}

    };

    public static final int[][] NINE_RELEVANT_PIXELS = {
            {1, 4}, {1, 5}, {1, 6}, {1, 7},
            {6, 5}, {6, 6}, {6, 7}, {1, 7}

    };

    public static final int[][] TEN_RELEVANT_PIXELS = {
            {2, 2}, {2, 3}, {2, 5}, {2, 6}, {2, 8}, {2, 9},
            {7, 2}, {7, 3}, {7, 5}, {7, 6}, {7, 8}, {7, 9}
    };

    public static final int[][] JACK_RELEVANT_PIXELS = {
            {0, 6}, {0, 7}, {0, 8}, {0, 9},
            {1, 6}, {1, 7}, {1, 8}, {1, 9},
            {10, 3}, {10, 4}, {10, 5}, {10, 6}
    };


    public static final int[][] QUEEN_RELEVANT_PIXELS = {
            {6, 1}, {6, 2}, {6, 3}, {6, 4}, {6, 8}, {6, 9},
            {10, 5}, {10, 6}, {10, 6}, {10, 8}, {10, 9}
    };

    public static final int[][] KING_RELEVANT_PIXELS = {
            {0, 2}, {0, 3}, {0, 4}, {0, 8}, {0, 9},
            {11, 2}, {11, 3}, {11, 4}, {11, 8}, {11, 9}
    };

    public static final int[][] HEARTS_RELEVANT_PIXELS = {
            {14, 2},
    };

    public static final int[][] CLUBS_RELEVANT_PIXELS = {
            {17, 2},
    };

    public static final int[][] DIAMONDS_RELEVANT_PIXELS = {
            {13, 5},
    };

    public static final int[][] SPADES_RELEVANT_PIXELS = {
            {17, 4},
    };

}
