package com.smougel.hands;

import com.smougel.cards.Card;
import com.smougel.cards.ICard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sylvainmougel on 26/03/15.
 */
public class Proba {

    private final Deck deck;
    private final ICard card1;
    private final List<ICard> tableCards;
    private final ICard card2;


    public Proba(ICard c1, ICard c2, ICard... cardsArg) {
        deck = new Deck();
        card1 = c1;
        card2 = c2;
        tableCards = new ArrayList<ICard>();
        tableCards.addAll(Arrays.asList(cardsArg));
        //System.out.println("Proba init with "+ c1 + c2 + cardsArg );


    }

    public float compute(int iter, int playerNb) {
        int wins = 0;

        for (int i = 0; i < iter; i++) {
            deck.reset();
            deck.remove(card1.toString());
            deck.remove(card2.toString());
            for (ICard c : tableCards) {
                deck.remove(c.toString());
            }
            deck.shuffle();

            Hand myHand = new Hand();
            myHand.add(card1);
            myHand.add(card2);
            for (ICard c : tableCards) {
                myHand.add(c);
            }

            List<Hand> otherHands = new ArrayList<Hand>(playerNb);
            for (int playerIndex = 0; playerIndex < playerNb; playerIndex++) {
                Hand hand = new Hand();
                hand.add(deck.giveCard());
                hand.add(deck.giveCard());
                for (ICard c : tableCards) {
                    hand.add(c);
                }
                otherHands.add(hand);
            }
            for (int j = 0; j < 5-tableCards.size(); j++) {
                Card c = deck.giveCard();
                myHand.add(c);
                for (Hand hand : otherHands) {
                    hand.add(c);
                }
            }

            myHand.updateMap();
            for (Hand hand : otherHands) {
                hand.updateMap();
            }
            Figure myFigure = myHand.getBestFigure();
            boolean win = true;
            for (Hand hand : otherHands) {

                Figure otherFigure = hand.getBestFigure();
                win = win && (myFigure.compareTo(otherFigure) >= 0);
                if (!win) {

                    break;
                } else {
                    //System.out.println(myHand + " vs " + hand);
                    //System.out.println(myFigure + " vs " + otherFigure);
                    //System.out.println("==============================");
                }

            }
            if (win) {
                wins++;
            }

        }
        return wins / (float) iter;
    }
}
