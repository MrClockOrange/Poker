package com.smougel.cards;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;

/**
 * Created by sylvainmougel on 16/03/15.
 */
public class Card implements ICard {

    /* Black color */
    private static final int[] BLACK = {0, 0, 0};

    /* Red color */
    private static final int[] RED = {0, 0, 255};

    /* Width of the image */
    private final int width;

    /* Pixels describing the image */
    private byte[] pixels = null;

    /* The card value from 2 to Ace */
    private Values value = null;

    /* The card value ie Spade Heart Diamond or Club */
    private Color color = null;

    public Card(BufferedImage bf) {
        width = bf.getWidth();
        pixels = ((DataBufferByte) bf.getRaster().getDataBuffer()).getData();
        value = getValue();
        color = getColor();
    }

    public Card(Color c, Values v) {
        color = c;
        value = v;
        width = 0;
    }


    public Values getValue() {
        // Loop on values
        if (value == null) {
            outerloop:
            for (Values v : Values.values()) {
                //System.out.println("Checking " + v.toString());

                int[][] rv = v.getRelevantPixels();

                boolean ok = true;
                for (int i = 0; i < rv.length; i++) {
                    int line = rv[i][0];
                    int column = rv[i][1];
                    int[] rgb = getRGBFromPixelCoord(line, column);
                    if (!isBlack(rgb) && !isRed(rgb)) {
                        //System.out.println("inccorect pixel found at " + i + "(value : " + r + " " + g + " " + b + ")");
                        ok = false;
                        break;
                    } else {
                        ok &= true;
                    }

                }
                // all the pixels are checked
                if (ok) {
                    value = v;
                    break outerloop;
                }

            }
        }
        return value;
    }

    public Color getColor() {
        //Identify color

        if (color == null) {
            for (Color c : Color.reds()) {
                int[][] rv = c.getRelevantPixels();
                int[] rgb = getRGBFromPixelCoord(rv[0][0], rv[0][1]);
                if (isRed(rgb)) {
                    color = c;
                    break;
                }
            }
        }
        //try black cards
        if (color == null) {
            for (Color c : Color.blacks()) {
                int[][] rv = c.getRelevantPixels();
                int[] rgb = getRGBFromPixelCoord(rv[0][0], rv[0][1]);
                if (isBlack(rgb)) {
                    color = c;
                    break;
                }
            }
        }

        return color;
    }

    private int[] getRGBFromPixelCoord(int line, int column) {
        int index = 3 * (line * width + column);
        int r = (pixels[index] >> 16) & 0xFF;
        int g = (pixels[index + 1] >> 8) & 0xFF;
        int b = (pixels[index + 2] >> 8) & 0xFF;

        int[] result = {r, g, b};
        return result;
    }


    private static boolean isRed(int[] rgb) {
        return (rgb[0] == RED[0]) && (rgb[1] == RED[1]) && (rgb[2] == RED[2]);

    }

    private static boolean isBlack(int[] rgb) {
        return (rgb[0] == BLACK[0]) && (rgb[1] == BLACK[1]) && (rgb[2] == BLACK[2]);
    }




    public String toString() {
        if (value != null && color != null) {
            return value.getStrRepr()  + color.getStrRepr();
        } else {
            return "";
        }
    }

    public boolean isAdjacent(ICard card) {
        int diff= this.getValue().getIntVal() - card.getValue().getIntVal();

        return (diff == 1) || (diff == -1)
                || (diff == 12) || (diff == -12); // Ace and two are adjacent
    }

    public boolean isSameColor(ICard card) {
       return this.getColor().equals(card.getColor());


    }

    @Override
    public int compareTo(ICard card) {
        return this.getValue().getIntVal() - card.getValue().getIntVal();
    }

}
