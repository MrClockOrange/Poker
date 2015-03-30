package com.smougel.hands;

import com.smougel.cards.Card;

import java.util.List;
import java.util.SortedSet;

/**
 * Created by sylvainmougel on 24/03/15.
 */
public class Figure implements Comparable<Figure> {
    private final FigureType type;
    private final List<Card> kickers;


    public Figure(FigureType t, List<Card> k) {
        type = t;
        kickers = k;

    }

    public FigureType getType() {
        return type;
    }

    public List<Card> getKickers() {
        return kickers;
    }

    @Override
    public int compareTo(Figure figure) {
        int result = 0;
        if (type.ordinal() > figure.getType().ordinal()) {
            result = 1;
        } else if (type.ordinal() == figure.getType().ordinal()) {
            // Compare the kickers
            for (int i = 0; i < kickers.size(); i++) {
                int comp = kickers.get(i).compareTo(figure.getKickers().get(i));
                if ( comp == 0) {
                    continue;
                } else {
                    result = comp;
                    break;
                }
            }


        } else {
            result = -1;
        }
        return result;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getType().toString());
        for (Card c : this.getKickers()) {
            sb.append(" ");
            sb.append(c.getValue());
        }
        return sb.toString();
    }
}
