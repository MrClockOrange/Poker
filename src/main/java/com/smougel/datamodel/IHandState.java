package com.smougel.datamodel;

import com.smougel.cards.ICard;

/**
 * Created by sylvainmougel on 02/01/16.
 */
public interface IHandState {

    ICard[] getHandCards();
    ICard[] getTableCards();
    float getWinProba(int plNb);
    States retrieveState();
}
