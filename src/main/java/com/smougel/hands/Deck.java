package com.smougel.hands;

import com.smougel.cards.Card;
import com.smougel.cards.Color;
import com.smougel.cards.Values;

import java.util.*;

/**
 * Created by sylvainmougel on 24/03/15.
 */
public class Deck {

    public static Map<String, Card> CARDS = buildcards();
    private  static final Stack<Card> DEFAULT_DECK = init();


    public static Map<String, Card> buildcards() {
        Map<String, Card> map = new HashMap<String, Card>();
        Deck d = new Deck();

        for (Card c : d.getCards()) {
            map.put(c.toString(), c);

        }
        return map;

    }
    public final static int CARD_NB = 52;
    private Stack<Card> cards;

    public Deck() {
        cards = init();
    }

    public void reset() {
        cards = init();
    }

    public void remove(String card) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).toString().equals(card)) {
                cards.remove(i);

            }
        }
    }

    public void shuffle(){
        long seed = System.nanoTime();
        Collections.shuffle(cards);
    }

    public static Stack<Card> init() {
        Stack<Card> result  = new Stack<Card>();
        for (Color c : Color.trueValues()) {
            for (Values v : Values.trueValues()) {
                result.push(new Card(c, v));
            }
        }
        return result;
    }

    public Stack<Card> getCards() {
        return cards;
    }

    public int nbOfCards() {
        return cards.size();
    }

    public Card giveCard() {
        return  cards.pop();
    }

    public Card lookCard(int cardNb) {
        return cards.get(cardNb);
    }


}
