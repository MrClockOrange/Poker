package com.smougel.handparser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sylvainmougel on 30/12/15.
 */
public class Hand {

    private final List<String> rawHand;
    private Set<String> players;
    private String id;

    public Hand(List<String> h) {
        rawHand = h;
    }

    public Hand() {
        rawHand = new ArrayList<>();
    }

    public Set<String> getPlayersName () {
        if (players == null) {
            Set<String> result = new HashSet<>();
            for (String line : rawHand) {
                if (line.startsWith("Seat") && line.contains("in chips")) {
                    result.add(getName(line));
                }
            }
            players = result;
        }

        return players;
    }

    public String getPlayerAction(String player) {
        String action = "";
        for (String line : rawHand) {
            if (line.startsWith("Seat") && !line.contains("in chips") && line.contains(player)) {
                action = line;
            }
        }
        return action;
    }

    private String getName(String line) {
        return line.split(":")[1].trim().split(" ")[0].trim();
    }

    public String getId() {
        if (id == null) {
            id = rawHand.isEmpty() ? null : rawHand.get(0).split("#")[1].split(":")[0];
        }
        return id;
    }

    public boolean isEmpty() {
        return rawHand.isEmpty();
    }
}
