package com.smougel.datamodel;

/**
 * Created by sylvainmougel on 02/01/16.
 */
public interface IPlayersState {
    /**
     *
     * @return the nb of players still in tha game
     */
    int getRemainingNbOPlayers();

    /**
     *
     * @return the amount to bet to stay in the game
     */
    int getBet();

    /**
     * @return the value in the pot
     */
    int getPot();

    /**
     *
     * @return the position after the dealer
     */
    int getPos();


    int[] getBets();

    int getDealerPosition();

    int getTotalMoneyAtStake();

    int getAmountToCall();

    int getLastBet();
}
