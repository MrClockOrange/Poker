package com.smougel.handparser;

/**
 * Created by sylvainmougel on 06/01/16.
 */
public class Actions {
    private int nbOfRaise;
    private int nbOfCall;
    private  int nbOfCheck;
    private boolean folded;

    public Actions() {
        nbOfRaise = 0;
        nbOfCall = 0;
        folded = true;

    }

    public void updateActions(String action) {
        if (action.contains("raises")) {
            nbOfRaise ++;
            folded = false;
        } else if (action.contains("calls")) {
            nbOfCall ++;
            folded = false;
        } else if (action.contains("folds")) {
            folded = true;
        }

    }

    public int getNbOfCall() {
        return nbOfCall;
    }

    public int getNbOfCheck() {
        return nbOfCheck;
    }

    public int getNbOfRaise() {
        return nbOfRaise;
    }

    public boolean isFolded() {
        return folded;
    }
}
