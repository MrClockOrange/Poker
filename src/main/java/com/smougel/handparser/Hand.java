package com.smougel.handparser;

import com.smougel.datamodel.GameState;
import com.smougel.datamodel.States;

import java.io.PrintWriter;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sylvainmougel on 30/12/15.
 */
public class Hand implements Comparable<Hand> {

    /** The raw hand extracted from the log*/
    private final List<String> rawHand;

    private List<String> players;
    private Map<String, Map<States,Actions>> playersAction;
    private String id;
    private States status = States.NONE;
    private SimpleDateFormat sdf;
    private String holeCards;
    private String boardCards;
    private String hero;
    private String dealerName;
    private List<String> currentGameStateBlock;
    private GameState currentGameState;


    public Hand(List<String> h, PrintWriter pw) {
        rawHand = h;
        currentGameStateBlock = new ArrayList<>();
        /* The date format is 2015/12/30 12:01:09*/
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        init(pw);
    }

    public Hand(PrintWriter pw) {
        this(new ArrayList<>(), pw);
    }

    private void init(PrintWriter pw) {
        players = new ArrayList<>();
        playersAction = new HashMap<>();
        currentGameStateBlock = new ArrayList<>();
        String dealer = "";
        for (String line : rawHand) {
            if (line.contains("is the button")) {
                dealer = line.split("#")[1].split(" ")[0];
            } else if (line.startsWith("Seat") && line.contains("in chips")) {
                String playerName = getName(line);
                //System.out.println(playerName);
                currentGameStateBlock.add(line);
                if (line.startsWith("Seat " + dealer)) {
                    dealerName = playerName;
                }
                players.add(playerName);
                playersAction.put(playerName,initialActionMap());
            } else if (line.contains("HOLE")) {
                boardCards = "";
                status = States.PREFLOP;
            } else if (line.contains("FLOP")) {
                boardCards = line.split("\\[")[1].split("\\]")[0];
                status = States.FLOP;
            } else if (line.contains("TURN")) {
                String flop = line.split("\\[")[1].split("\\]")[0];
                String turn = line.split("\\[")[2].split("\\]")[0];
                boardCards = flop + " " + turn;
                status = States.TURN;
            } else if (line.contains("RIVER")) {
                String turn = line.split("\\[")[1].split("\\]")[0];
                String river = line.split("\\[")[2].split("\\]")[0];
                boardCards = turn + " " + river;
                status = States.RIVER;
            } else if (line.contains("SHOW DOWN") || line.contains("Uncalled bet")) {
                status = States.NONE;
            }
            if (status.equals(States.PREFLOP)) {
                if (line.contains("Dealt to")) {
                    holeCards = line.split("\\[")[1].split("\\]")[0];
                    hero = line.split(" ")[2];
                    // Initialize the game state
                    currentGameState = new GameState(
                            players,
                            holeCards,
                            getPosition(hero),
                            hero,
                            dealerName);
                    currentGameStateBlock = new ArrayList<>();
                }
            }

            if (!status.equals(States.NONE) && line.contains(":") && !line.contains("said")) {
                String[] split = line.split(":");
                String playerName = split[0];
                currentGameStateBlock.add(line);
                playersAction.get(playerName).get(status).updateActions(split[1]);
                if (playerName.equals(hero)) {
                    // Update the game state
                    currentGameState.update(
                            status,
                            boardCards,
                            currentGameStateBlock
                    );
                    //System.out.println(currentGameState.toString());
                    pw.println(currentGameState.vetorizeGameState());
                    currentGameStateBlock = new ArrayList<>();
                }
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


    public List<String> getPlayersName () {
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


    public String getHoleCards() {
        return holeCards;
    }

    public int getPosition(String playerName) {
        int i = 1;
        int playerPos = 1;
        int dealerPos = 1;
        for (String s : players) {
            if (s.equals(playerName)) {
                playerPos = i;
            }
            if (s.equals(dealerName)) {
                dealerPos = i;
            }
            i ++;
        }
        if (playerPos > dealerPos) {
            return playerPos - dealerPos;
        } else {
            return players.size() - dealerPos + playerPos;
        }
    }


    /**
     * The date format is 2015/12/30 12:01:09
     * @return
     */
    public Date getDate() {
        return  sdf.parse(rawHand.get(0), new ParsePosition(58));
    }

    public String getHero() {
        return hero;
    }

    public String getDealerName() {
        return dealerName;
    }


    @Override
    public int compareTo(Hand o) {
        return this.getDate().compareTo(o.getDate());
    }
}
