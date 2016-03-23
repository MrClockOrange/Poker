package com.smougel.ia;

import com.smougel.datamodel.IHandState;
import com.smougel.datamodel.IPlayersState;

import java.util.List;

/**
 * Created by sylvainmougel on 02/01/16.
 */
public class EquityPlayer  implements IPlayer{



    @Override
    public EAction play(IHandState iHandState, List<IPlayersState> playersStateHistory) {
        IPlayersState lastPState = playersStateHistory.get(playersStateHistory.size() - 1);
        int nb = lastPState.getRemainingNbOPlayers();
        float winProba = iHandState.getWinProba(nb);
        //int stake = lastPState.getTotalMoneyAtStake();
        int stake = lastPState.getPot() + lastPState.getRemainingNbOPlayers() * lastPState.getLastBet();

        int toCall = lastPState.getAmountToCall();

        System.out.println("Win probability (" + nb + " players): " + winProba);
        System.out.println(stake + "$ at stake" );
        System.out.println("Amount to call : " + toCall);
        System.out.println("Call threshold : " + winProba * stake);

        if (toCall < winProba * stake){
            return EAction.CALL;
        } else {
            return EAction.FOLD;
        }

    }
}
