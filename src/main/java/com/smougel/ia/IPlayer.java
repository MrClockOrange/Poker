package com.smougel.ia;

import com.smougel.datamodel.IHandState;
import com.smougel.datamodel.IPlayersState;

import java.util.List;

/**
 * Created by sylvainmougel on 02/01/16.
 */
public interface IPlayer {
    EAction play(IHandState iHandState, List<IPlayersState> playersStateHistory);
}
