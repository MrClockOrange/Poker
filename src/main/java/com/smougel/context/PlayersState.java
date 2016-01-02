package com.smougel.context;

/**
 * Created by sylvainmougel on 02/01/16.
 */
public class PlayersState implements IPlayersState {

    private final Player[] players;
    private final int dealerPosition;
    private final int pot;
    private final int totalNbOfPlayers;

    public PlayersState(Player[] players_, int dealerPosition_, int pot_, int nbOfPlayers) {
        players = players_;
        dealerPosition = dealerPosition_;
        pot = pot_;
        totalNbOfPlayers = nbOfPlayers;
    }

    @Override
    public int getRemainingNbOPlayers() {
        int res = 0;
        for (Player p : players) {
            if (p.isIn()) {
                res++;
            }
        }
        return res;
    }

    @Override
    public int getBet() {
        int lastBet = 0;
        for (Player p : players) {
            if (p.isIn() && lastBet < p.getBet()) {
                lastBet = p.getBet();
            }
        }
        int lastPlayerPos = getPos() - 1 % totalNbOfPlayers;
        return players[lastPlayerPos].getBet();
    }

    @Override
    public int getPot() {
        return pot;
    }

    @Override
    public int getPos() {
        // Position 8 is my position
        // If I'm the dealer it returns 0
        return 8 - dealerPosition;
    }

    @Override
    public int[] getBets() {
        int[] result = new int[totalNbOfPlayers];
        for (int i = 0; i < result.length; i++) {
            result[i] = players[i].getBet();
        }
        return result;
    }

    @Override
    public int getDealerPosition() {
        return dealerPosition;
    }

    @Override
    public int getTotalMoneyAtStake() {
        int result = 0;
        for (int i = 0; i < players.length; i++) {
            result += players[i].getBet();
        }
        return result + pot;
    }

}
