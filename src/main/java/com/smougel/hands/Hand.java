package com.smougel.hands;

import com.smougel.cards.Card;
import com.smougel.cards.Color;
import com.smougel.cards.Values;

import java.util.*;

/**
 * Created by sylvainmougel on 24/03/15.
 */
public class Hand {


    private Map<Color, List<Card>> colorMap;

    private SortedMap<Values, List<Card>> valueMap;
    private List<Card> allCards;
    private List<Card> privateCards;


    public Hand() {
        allCards = new ArrayList<Card>();
        privateCards = new ArrayList<Card>();
        colorMap = new TreeMap<Color, List<Card>>();
        valueMap = new TreeMap<Values, List<Card>>(Values.getComparator());

    }

    public Hand(Card... cardsArg) {
        this();
        for (Card c : cardsArg) {
            this.add(c);
        }
        this.updateMap();
    }


    public Hand(String... cards) {
        this();
        for (String c : cards) {
            this.add(Deck.CARDS.get(c));
        }
        this.updateMap();
    }

    public void add(Card card) {
        if (!card.getValue().equals(Values.NONE)) {
            allCards.add(card);
        }
    }

    public void addPrivate(Card card) {
        if (!card.getValue().equals(Values.NONE)) {
            privateCards.add(card);
        }
    }

    public int nbOfCard() {
        return allCards.size();
    }

    public void clear() {
        allCards.clear();
    }


    public void updateMap() {
        // Order the allCards from the stronger to the weaker
        Collections.sort(allCards, Collections.reverseOrder());

        for (Card c : allCards) {
            Color color = c.getColor();
            Values value = c.getValue();

            List<Card> sameColorCard = colorMap.get(color);
            if (sameColorCard == null) {
                sameColorCard = new ArrayList<Card>();
                sameColorCard.add(c);
                colorMap.put(color, sameColorCard);
            } else {
                sameColorCard.add(c);
            }

            List<Card> sameValueCards = valueMap.get(value);
            if (sameValueCards == null) {
                sameValueCards = new ArrayList<Card>();
                sameValueCards.add(c);
                valueMap.put(value, sameValueCards);
            } else {
                sameValueCards.add(c);
            }
        }
    }


    public Figure getFlush() {
        Figure result = null;
        for (Color c : colorMap.keySet()) {
            List<Card> sameColorCard = colorMap.get(c);
            if (sameColorCard.size() >= 5) {
                //Collections.reverse(sameColorCard);
                result = new Figure(FigureType.FLUSH, sameColorCard.subList(0,5));
            }

        }
        return result;

    }

    public Figure getFourOfAKind() {
        Figure result = null;
        List<Card> kickers = new ArrayList<Card>(2);
        for (Values c : valueMap.keySet()) {
            List<Card> sameValue = valueMap.get(c);
            if (sameValue.size() == 4) {
                //Collections.reverse(sameColorCard);
                kickers.add(sameValue.get(0));
                kickers.add(getHighest(1, kickers.get(0)).get(0));
                result = new Figure(FigureType.FOUR_OF_A_KIND, kickers);
                break;
            }
        }

        return result;

    }

    public Figure getThreeOfAKind() {
        Figure result = null;
        List<Card> kickers = new ArrayList<Card>(3);
        for (Values c : valueMap.keySet()) {
            List<Card> sameValue = valueMap.get(c);
            if (sameValue.size() == 3) {
                //Collections.reverse(sameColorCard);
                kickers.add(sameValue.get(0));
                List<Card> k = getHighest(2, kickers.get(0));
                kickers.addAll(k);
                result = new Figure(FigureType.THREE_OF_A_KIND, kickers);
                break;
            }
        }

        return result;

    }

    public Figure getDoublePair() {
        Figure result = null;
        List<Card> kickers = new ArrayList<Card>(3);
        boolean firstPairFound = false;
        for (Values c : valueMap.keySet()) {
            List<Card> sameValue = valueMap.get(c);
            if (sameValue.size() == 2) {
                //Collections.reverse(sameColorCard);
                kickers.add(sameValue.get(0));

                if (firstPairFound) {
                    List<Card> k = getHighest(1, kickers.get(0), kickers.get(1));
                    kickers.addAll(k);
                    result = new Figure(FigureType.DOUBLE_PAIR, kickers);
                    break;
                }
                firstPairFound = true;
            }
        }

        return result;

    }

    public Figure getPair() {
        Figure result = null;
        List<Card> kickers = new ArrayList<Card>(4);
        for (Values c : valueMap.keySet()) {
            List<Card> sameValue = valueMap.get(c);
            if (sameValue.size() == 2) {
                kickers.add(sameValue.get(0));
                List<Card> k = getHighest(3, kickers.get(0));
                kickers.addAll(k);
                result = new Figure(FigureType.PAIR, kickers);
                break;

            }
        }

        return result;

    }

    public Figure getNone() {
        Figure result = null;
        List<Card> kickers = new ArrayList<Card>(5);
        for (Values c : valueMap.keySet()) {
            kickers.add(valueMap.get(c).get(0));
            if (kickers.size() == 5) {
                result = new Figure(FigureType.NOTHING, kickers);
                break;
            }

        }

        return result;

    }

    public Figure getFull() {
        Figure result = null;

        Card k1 = null;
        Card k2 = null;
        boolean pairFound = false;
        boolean brelanFound = false;
        for (Values c : valueMap.keySet()) {
            List<Card> sameValue = valueMap.get(c);
            if (sameValue.size() == 2) {
                k2 = sameValue.get(0);
                pairFound = true;
            }

            if (sameValue.size() == 3) {
                k1 = sameValue.get(0);
                brelanFound = true;

            }
            if (pairFound && brelanFound) {
                List<Card> kickers = new ArrayList<Card>(2);
                kickers.add(k1);
                kickers.add(k2);
                result = new Figure(FigureType.FULL, kickers);
                break;
            }
        }

        return result;

    }

    /**
     * Return a list of allCards that ar no in cardsArg
     *
     * @param nbOfCards
     * @param cardsArg
     * @return
     */
    List<Card> getHighest(int nbOfCards, Card... cardsArg) {
        List<Card> result = new ArrayList<Card>();
        outerloop:
        for (Card c : allCards) {
            //Check the card is different from all the card in argument
            boolean different = true;
            for (Card carg : cardsArg) {
                different = different && !c.getValue().equals(carg.getValue());
            }
            if (different) {
                result.add(c);
                if (result.size() == nbOfCards) {
                    break outerloop;
                }

            }
        }
        return result;
    }

    public Figure getQuintFlush() {
        Figure result = null;
        List<Card> kickers = new ArrayList<Card>(5);
        int unadjacentNb = 0;
        int adjacentNb = 1;
        Card previous = allCards.get(0);
        kickers.add(previous);
        for (int i = 1; adjacentNb < 5 && unadjacentNb < 3 && i < allCards.size(); i++) {

            Card current = allCards.get(i);
            if (current.compareTo(previous) == 0) {
                //skip this card
                continue;
            }
            if (previous.isAdjacent(current) && previous.isSameColor(current)) {
                adjacentNb++;
                kickers.add(current);
            } else {
                unadjacentNb++;
                adjacentNb = 1;
                kickers.clear();
                kickers.add(current);
            }
            previous = current;
        }
        // Special case for ace:
        if ((adjacentNb == 4)
                && (allCards.get(0).getValue().equals(Values.ACE))
                && previous.equals(Values.TWO)
                && previous.isSameColor(allCards.get(0))) {

            kickers.add(allCards.get(0));
            adjacentNb = 5;
        }
        if (adjacentNb == 5) {
            result = new Figure(FigureType.QUINTFLUSH, kickers);
        }
        return result;

    }

    public Figure getStraight() {
        Figure result = null;
        List<Card> kickers = new ArrayList<Card>(5);
        int unadjacentNb = 0;
        int adjacentNb = 1;
        Card previous = allCards.get(0);
        kickers.add(previous);
        for (int i = 1; adjacentNb < 5 && unadjacentNb < 3 && i < allCards.size(); i++) {

            Card current = allCards.get(i);
            if (current.compareTo(previous) == 0) {
                //skip this card
                continue;
            }
            if (previous.isAdjacent(current)) {
                adjacentNb++;
                kickers.add(current);
            } else {
                unadjacentNb++;
                adjacentNb = 1;
                kickers.clear();
                kickers.add(current);
            }
            previous = current;
        }
        // Special case for ace:
        if ((adjacentNb == 4) && (allCards.get(0).getValue().equals(Values.ACE)) && previous.getValue().equals(Values.TWO)) {
            kickers.add(allCards.get(0));
            adjacentNb = 5;
        }
        if (adjacentNb == 5) {
            result = new Figure(FigureType.STRAIGHT, kickers);
        }
        return result;

    }

    public Figure getBestFigure() {
        Figure result = null;
        if (allCards.size() < 5) {
            return result;
        }
        result = getQuintFlush();
        if (result != null) {
            return result;

        }
        result = getFourOfAKind();
        if (result != null) {
            return result;

        }
        result = getFull();
        if (result != null) {
            return result;

        }
        result = getFlush();
        if (result != null) {
            return result;

        }
        result = getStraight();
        if (result != null) {
            return result;

        }
        result = getThreeOfAKind();
        if (result != null) {
            return result;

        }
        result = getDoublePair();
        if (result != null) {
            return result;

        }

        result = getPair();
        if (result != null) {
            return result;

        }

        return getNone();

    }

    public void dump() {
/*        for (Card c : allCards) {
            System.out.println(c.toString());
        }*/
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card c : this.allCards) {
            sb.append(c.toString());
            sb.append(" ");
        }
/*        Figure f = this.getBestFigure();
        if (f == null) {
            return "Empty";
        } else {
            return f.toString();
        }*/
        return sb.toString();
    }

}
