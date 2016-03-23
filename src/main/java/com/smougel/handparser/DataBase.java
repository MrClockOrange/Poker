package com.smougel.handparser;

import com.smougel.datamodel.States;

import java.util.Map;
import java.util.Set;

/**
 * Created by sylvainmougel on 30/12/15.
 */
public class DataBase {

    private final Map<String, Hand> handsMap;
    private Set<String> lastPlayers;

    public DataBase(Map<String, Hand> hm) {
        handsMap = hm;
    }

    public float computeVpip(String playerName) {
        int handsPlayed = 0;
        int handVpip = 0;
        for (Map.Entry<String, Hand> entry : handsMap.entrySet()) {
            Hand current = entry.getValue();
            if (current.getPlayersName().contains(playerName)) {
                handsPlayed ++;
                Actions a = current.getPlayerAction(playerName, States.PREFLOP);
                if (a.getNbOfCall() != 0 || a.getNbOfRaise() != 0) {
                    handVpip++;
                }
            }

        }
        return handVpip / (float) handsPlayed;
    }

    public float computePfr(String playerName) {
        int handsPlayed = 0;
        int handRpf = 0;
        for (Map.Entry<String, Hand> entry : handsMap.entrySet()) {
            Hand current = entry.getValue();
            if (current.getPlayersName().contains(playerName)) {
                handsPlayed ++;
                Actions a = current.getPlayerAction(playerName, States.PREFLOP);
                if (a.getNbOfRaise() != 0) {
                    handRpf++;
                }
            }

        }

        return handRpf / (float) handsPlayed;
    }

    public int handsPlayed(String playerName) {
        int handsPlayed = 0;
        for (Map.Entry<String, Hand> entry : handsMap.entrySet()) {
            Hand current = entry.getValue();
            if (current.getPlayersName().contains(playerName)) {
                handsPlayed ++;
            }

        }
        return handsPlayed;
    }

    public Set<String> getLastPlayers() {
        return lastPlayers;
    }
}
