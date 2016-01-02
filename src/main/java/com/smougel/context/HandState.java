package com.smougel.context;

import com.smougel.cards.Card;
import com.smougel.cards.ICard;
import com.smougel.cards.Values;
import com.smougel.hands.Proba;

/**
 * Created by sylvainmougel on 02/01/16.
 */
public class HandState implements IHandState{
    private ICard[] handCards;
    private ICard[] tableCards;
    private int winProba;

    public HandState(ICard[] tcs, ICard[] hcs) {
        handCards = hcs;
        tableCards = tcs;

    }


    @Override
    public ICard[] getHandCards() {
        return handCards;
    }

    @Override
    public ICard[] getTableCards() {
        return tableCards;
    }



    @Override
    public float getWinProba(int playersNb) {
        float result = 0;
        if (!tableCards[5].getValue().equals(Values.NONE) && !tableCards[6].getValue().equals(Values.NONE)) {

            Proba proba = new Proba(tableCards[5], tableCards[6], getTableCards());
            result = proba.compute(10000, playersNb);

        }
        return result;
    }

    @Override
    public States retrieveState() {
        States result = States.PREFLOP;
        if (tableCards.length == 0) {
            result = States.PREFLOP;
        } else if (tableCards.length == 3) {
            result = States.FLOP;
        } else if (tableCards.length == 4) {
            result = States.TURN;
        } else if (tableCards.length == 5) {
            result = States.RIVER;
        }
        return result;
    }
}
