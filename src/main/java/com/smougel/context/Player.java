package com.smougel.context;

/**
 * Created by sylvainmougel on 23/03/15.
 */
public class Player {
    int position = 1; // Smal blind egal one
    int totalCash = 0;
    int bet = 0;
    boolean isDealer =false;
    boolean in = true;

    public void update(String strBet, boolean dealer, boolean isIn) {
        if (strBet.equals("")) {
            bet = 0;
        } else {
            bet = Integer.parseInt(strBet);
        }
        isDealer = dealer;
        in = isIn;
    }

    public int getBet() {
        return bet;
    }

    @Override
    public String toString() {
        String dealer = isDealer ? "D " : "  ";
        String betStr = in ? "Bet : " + bet : "Folded";
        return dealer + betStr;
    }

    public void setDealer() {
        isDealer = true;
    }

    public void fold() {
        in = false;
    }

    public boolean isIn() {
        return in;
    }
}
