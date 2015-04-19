package com.smougel;

/**
 * Created by sylvainmougel on 23/03/15.
 */
public class Player {
    int position = 1; // Smal blind egal one
    int totalCash = 0;
    int bet = 0;
    boolean isDealer =false;

    public void update(String strBet, boolean dealer) {
        if (strBet.equals("")) {
            bet = 0;
        } else {
            bet = Integer.parseInt(strBet);
        }
        isDealer = dealer;
    }

    public int getBet() {
        return bet;
    }

    @Override
    public String toString() {
        String dealer = isDealer ? "D " : "  ";
        return dealer + "Bet : " + bet;
    }

    public void setDealer() {
        isDealer = true;
    }
}
