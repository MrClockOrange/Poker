package com.smougel;

/**
 * Created by sylvainmougel on 23/03/15.
 */
public class Player {
    int position = 1; // Smal blind egal one
    int totalCash = 0;
    int bet = 0;

    public void update(String strBet) {
        if (strBet.equals("")) {
            bet = 0;
        } else {
            bet = Integer.parseInt(strBet);
        }
    }

    public int getBet() {
        return bet;
    }

    @Override
    public String toString() {
        return "Bet : " + bet;
    }
}
