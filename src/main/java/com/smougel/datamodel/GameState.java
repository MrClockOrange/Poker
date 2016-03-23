package com.smougel.datamodel;

import com.smougel.cards.Card;
import com.smougel.hands.Deck;
import com.smougel.hands.Proba;

import java.util.ArrayList;
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
    private String boardCards;
    private States state;
    private final String holeCards;

    /* Position of the hero */
    private int position;
    Map<String, Integer> lastActions;
    private Proba prob;

    public GameState(List<String> players, String hCards, int pos, String h) {
        hero = h;
        state = States.PREFLOP;
        position = pos;
        holeCards = hCards;
        lastActions = new HashMap<>();
        for (String p : players) {
            lastActions.put(p, 0);
        }

    }
    public void update(
            States status,
            String tbc,
            List<String> currentGameStateBlock
    ) {
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
            if (line.contains("checks") || line.contains("calls")) {

            } else if (line.contains("raises") || line.contains("bets")){
                lastActions.put(player, lastActions.get(player)+1);
            } else if (line.contains("folds")){
                lastActions.put(player, -1);
            }
        }
    }





    public String vetorize() {
        int remainingNbOfPlayers = remainingNbOfPlayer();
        StringBuilder sb = new StringBuilder();
        sb.append(state.ordinal());
        sb.append(" ");
        sb.append(prob.compute(1000, remainingNbOfPlayers));
        sb.append(" ");
        sb.append(remainingNbOfPlayers);
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
