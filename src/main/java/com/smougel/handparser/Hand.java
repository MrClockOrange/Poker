package com.smougel.handparser;

import com.smougel.context.States;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sylvainmougel on 30/12/15.
 */
public class Hand implements Comparable<Hand> {

    private final List<String> rawHand;
    private final List<String> preflopAction;
    private final List<String> flopAction;
    private Set<String> players;
    private Map<String, Map<States,Actions>> playersAction;
    private String id;
    private Set<String> vPip;
    private Set<String> rpf;
    private States status = States.NONE;
    private SimpleDateFormat sdf;



    public Hand(List<String> h) {
        rawHand = h;
        preflopAction = new ArrayList<>();
        flopAction = new ArrayList<>();
        /* The date format is 2015/12/30 12:01:09*/
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        init();
    }

    public Hand() {
        this(new ArrayList<>());
    }

    private void init() {
        players = new HashSet<>();
        playersAction = new HashMap<>();
        vPip = new HashSet<String>();
        rpf = new HashSet<String>();
        for (String line : rawHand) {
            if (line.startsWith("Seat") && line.contains("in chips")) {
                String playerName = getName(line);
                //System.out.println(playerName);
                players.add(playerName);
                playersAction.put(playerName,initialActionMap());
            }

            if (line.contains("HOLE")) {
                status = States.PREFLOP;
            } else if (line.contains("FLOP")) {
                status = States.FLOP;
            } else if (line.contains("SHOW DOWN") || line.contains("Uncalled bet")) {
                status = States.NONE;
            }

            if (!status.equals(States.NONE) && line.contains(":") && !line.contains("said")) {
                String[] split = line.split(":");
                playersAction.get(split[0]).get(status).updateActions(split[1]);

            }


        }
    }

    private  HashMap<States,Actions> initialActionMap() {
        HashMap<States,Actions> res = new HashMap<>();
        for (States state: States.values()) {
            res.put(state, new Actions());
        }
        return res;
    }


    private String getName2(String line) {
        return line.split(":")[1].trim().split(" ")[0].trim();
    }


    public Set<String> getPlayersName () {
        return players;
    }

    public Actions getPlayerAction(String player, States state) {
        return playersAction.get(player).get(state);
    }

    /**
     * @param line
     * @return the player name in the line
     */
    private String getName(String line) {
        return line.split(":")[1].trim().split("\\(\\d+ in chips\\)")[0].trim();
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

    public boolean vPip(String player) {

        return false;
    }

    /**
     * The date format is 2015/12/30 12:01:09
     * @return
     */
    public Date getDate() {
        return  sdf.parse(rawHand.get(0), new ParsePosition(58));
    }

    @Override
    public int compareTo(Hand o) {
        return this.getDate().compareTo(o.getDate());
    }
}
