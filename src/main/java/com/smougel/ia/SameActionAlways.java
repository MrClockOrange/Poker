package com.smougel.ia;

import com.smougel.datamodel.IHandState;
import com.smougel.datamodel.IPlayersState;

import java.util.List;

/**
 * Created by sylvainmougel on 03/01/16.
 */
public class SameActionAlways implements IPlayer {

    private final EAction action;

    public SameActionAlways(EAction a) {
        action = a;
    }

    @Override
    public EAction play(IHandState iHandState, List<IPlayersState> iPlayersState) {
        return action;
    }
}
