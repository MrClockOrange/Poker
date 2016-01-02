package com.smougel.ia;

import com.smougel.context.IHandState;
import com.smougel.context.IPlayersState;

/**
 * Created by sylvainmougel on 02/01/16.
 */
public interface IPlayer {
    EAction play(IHandState iHandState, IPlayersState iPlayersState);
}
