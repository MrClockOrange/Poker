package com.smougel.handparser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by sylvainmougel on 30/12/15.
 */
public class HandParser {

    private String path;
    private Map<String,Hand> handMap;

    public HandParser(String f) {
        handMap = new HashMap<>();
        path = f;
    }


    public void parse() throws FileNotFoundException {
        File file = new File(path);
        if (file.isDirectory()) {
            String[] files = file.list();
            for (String f : files) {
                System.out.println(f);
                analyseFile(new FileInputStream(path + f));
            }
        } else {
            analyseFile(new FileInputStream(path));
        }
    }

    private void analyseFile(FileInputStream f) throws FileNotFoundException {
        Scanner scanner = new Scanner(f);
        List<String> currentHand = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.contains("PokerStars Hand")) {
                Hand hand = currentHand == null ? new Hand() : new Hand(currentHand);
                if (!hand.isEmpty())
                    handMap.put(hand.getId(), hand);
                //System.out.println(hand.getPlayersName());
                currentHand = new ArrayList<>();
                //System.out.println(hand.getPlayerAction("MrClockOran"));

            }
            currentHand.add(line);
            //System.out.println(line);
        }
    }

    public Map<String, Hand> getHands() {
        return handMap;
    }
}
