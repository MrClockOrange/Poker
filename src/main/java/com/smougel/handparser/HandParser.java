package com.smougel.handparser;

import java.io.*;
import java.util.*;

/**
 * Created by sylvainmougel on 30/12/15.
 */
public class HandParser {

    private String path;
    private  PrintWriter pw;
    private SortedMap<String,Hand> handMap;
    public HandParser(String f) throws FileNotFoundException {
        path = f;
        pw = new PrintWriter("/Users/sylvainmougel/hands.mat");
    }


    public void parse() throws FileNotFoundException {
        handMap = new TreeMap<>();
        File file = new File(path);
        System.out.println(file.getAbsolutePath());
        if (file.isDirectory()) {
            String[] files = file.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith("HH");
                }
            });
            for (String f : files) {
                analyseFile(new FileInputStream(path + f));
            }
        } else {
            analyseFile(new FileInputStream(path));
        }
        pw.close();
    }

    private void analyseFile(FileInputStream f) throws FileNotFoundException {
        Scanner scanner = new Scanner(f);
        List<String> currentHand = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.contains("PokerStars Hand")) {
                Hand hand = currentHand == null ? new Hand(pw) : new Hand(currentHand, pw);
                if (!hand.isEmpty())
                    handMap.put(hand.getId(), hand);
                currentHand = new ArrayList<>();

            }
            currentHand.add(line);
        }
        Hand hand = currentHand == null ? new Hand(pw) : new Hand(currentHand, pw);
        if (!hand.isEmpty())
            handMap.put(hand.getId(), hand);


    }

    public SortedMap<String, Hand> getHands() throws FileNotFoundException {
        if (handMap == null) {
            parse();
        }
        return handMap;
    }
}
