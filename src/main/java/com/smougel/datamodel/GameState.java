package com.smougel.datamodel;

import com.smougel.cards.Card;
import com.smougel.hands.Deck;
import com.smougel.hands.Proba;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * All the usefull information necessary to make a decision are gathered in this class
 *
 * Created by sylvainmougel on 18/02/2016.
 */
public class GameState {
    private final String hero;
    private final String dealer;
    private String boardCards;
    private States state;
    private final String holeCards;
    private double heroAction = 0;
    private int moneyAtStake;
    private int moneyBid = 0;
    private List<String> players;
    private int lastBet = 0;

    /* Position of the hero Pos 1  = SB, 2 =BB etc.. */
    private int position;
    Map<String, Integer> lastActions;
    private Proba prob;

    public GameState(List<String> p, String hCards, int pos, String h, String d) {
        players = p;
        dealer = d;
        hero = h;
        state = States.PREFLOP;
        position = pos;
        holeCards = hCards;
        lastActions = new HashMap<>();
        moneyAtStake = 15;
        moneyBid += (position == 1 || position == 2) ? 5 * pos : 0;
        for (String player : players) {
            lastActions.put(player, 0);
        }


    }
    public void update(
            States status,
            String tbc,
            List<String> currentGameStateBlock) {
        state = status;
        boardCards = tbc;

        //compute probability
        String[] bcs = boardCards.split(" ");
        Card[]  bc = new Card[bcs.length];

        for (int i = 0; i < bcs.length; i++) {
            bc[i] = Deck.CARDS.get(bcs[i].trim());
        }
        String[] hcs = holeCards.split(" ");
        prob = boardCards.isEmpty() ?
                new Proba(
                    Deck.CARDS.get(hcs[0]),
                    Deck.CARDS.get(hcs[1])
                ):
                new Proba(
                    Deck.CARDS.get(hcs[0]),
                    Deck.CARDS.get(hcs[1]),
                    bc
                );


        for (String line : currentGameStateBlock) {
            String player = line.split(":")[0];
            double action = 0;
            int bet = 0;
            if (line.contains("checks")) {
                action = 2;
            } else if (line.contains("calls")) {
                action = 2;
                String callString = line.split("calls")[1].trim();
                bet = callString.contains("all") ?
                        Integer.valueOf(callString.split(" ")[0].trim()) :
                        Integer.valueOf(callString);
            } else if (line.contains("raises")) {
                action = 3;
                lastActions.put(player, lastActions.get(player) + 1);
                bet = Integer.valueOf(line.split("raises")[1].split("to")[0].trim());
            } else if (line.contains("bets")) {
                action = 3;
                lastActions.put(player, lastActions.get(player) + 1);
                String betString = line.split("bets")[1].trim();
                bet = betString.contains("all") ?
                        Integer.valueOf(betString.split(" ")[0].trim()) :
                        Integer.valueOf(betString);
            } else if (line.contains("folds")){
                lastActions.put(player, -1);
                action = 1;
            }
            moneyAtStake += bet;
            if (player.equals(hero)) {
                heroAction = action;
                moneyBid += bet;
                lastBet = bet;
            }

        }
    }


    /**
     * Generate a vector representing the game state
     * @return
     */
    public String vetorizeGameState() {
        int remainingNbOfPlayers = remainingNbOfPlayer();
        StringBuilder sb = new StringBuilder();
        sb.append(state.ordinal());
        sb.append(" ");
        sb.append(prob.compute(1000, remainingNbOfPlayers));
        sb.append(" ");
        sb.append(remainingNbOfPlayers);
        sb.append(" ");
        sb.append(moneyAtStake - lastBet);
        sb.append(" ");
        sb.append(moneyBid -lastBet);
        sb.append(" ");
        sb.append(position);
        for (Map.Entry<String,Integer> entry : lastActions.entrySet()) {
            if (!entry.getKey().equals(hero)) {
                sb.append(" ");
                sb.append(entry.getValue());
            }
        }
        //padding
        for (int i= 0; i< 9- lastActions.size(); i++) {
            sb.append(" -1");
        }
        return sb.toString();
    }

    public String actualAction() {
        return String.valueOf(heroAction);
    }

    private int remainingNbOfPlayer() {
        int result = 0;
        for (Map.Entry<String,Integer> entry : lastActions.entrySet()) {
            if (entry.getValue() >= 0 && !entry.getKey().equals(hero)) {
                result++;
            }
        }
        return result;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int remainingPlayers = remainingNbOfPlayer();
        sb.append(state.ordinal());
        sb.append(" ");
        sb.append(holeCards);
        sb.append(" ");
        sb.append(boardCards);
        sb.append(" ");
        sb.append(prob.compute(1000, remainingPlayers));
        sb.append(" ");
        sb.append(remainingPlayers);
        sb.append(" ");
        sb.append(position);
        sb.append("|");
        for (Map.Entry<String,Integer> entry : lastActions.entrySet()) {
            if (!entry.getKey().equals(hero)) {
                sb.append(entry.getValue());
                sb.append(" ");
            }
        }
        sb.append("| ");
        sb.append(lastActions.get(hero));

        return sb.toString();
    }


}
