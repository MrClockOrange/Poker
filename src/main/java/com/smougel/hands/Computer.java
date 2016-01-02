package com.smougel.hands;

import com.smougel.cards.Card;

/**
 * Created by sylvainmougel on 01/04/15.
 */
public class Computer {


    public static void main(String[] args) {

        Proba proba = new Proba(Deck.CARDS.get(args[0]), Deck.CARDS.get(args[1]));
        System.out.println(proba.compute(10000 , 1));


    }
}
