
import com.smougel.context.States;
import com.smougel.handparser.Actions;
import com.smougel.handparser.DataBase;
import com.smougel.handparser.Hand;
import com.smougel.handparser.HandParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by sylvainmougel on 30/12/15.
 */
public class HandParserTest {



    @Test
    public void testParse() throws FileNotFoundException {
        HandParser  hp = new HandParser("/Users/sylvainmougel/" +
                "Library/Application Support/PokerStarsFR/HandHistory/MrCLockOran/");
        hp.parse();


    }

    @Test
    public void testParse2() throws FileNotFoundException {

        HandParser  hp = new HandParser(getClass().getResource("/handhistory/hand1.txt").getFile());
        hp.parse();
        Map<String, Hand> handMap = hp.getHands();
        String[] res = {"146161243906", "146161206205", "146161179049", "146161338428", "146161159241", "146161285799"};
        Assert.assertArrayEquals(res, handMap.keySet().toArray());


        System.out.println(handMap.keySet());


    }

    @Test
    public void testParse3() throws FileNotFoundException {

        HandParser  hp = new HandParser(getClass().getResource("/handhistory/hand1.txt").getFile());
        hp.parse();

        SortedMap<String, Hand> handMap = hp.getHands();

        Hand h1 = handMap.get("146161243906");
        Actions a1 = h1.getPlayerAction("MrClockOran", States.PREFLOP);
        Assert.assertEquals(a1.getNbOfCall(), 1);
        Assert.assertEquals(a1.getNbOfRaise(), 0);
        Assert.assertEquals(a1.getNbOfCheck(), 0);
        Actions a2 = h1.getPlayerAction("joseG655", States.PREFLOP);
        Assert.assertEquals(a2.getNbOfRaise(), 1);
        Actions a3 = h1.getPlayerAction("maxiflop06", States.PREFLOP);
        Assert.assertTrue(a3.isFolded());
        Actions a4 = h1.getPlayerAction("maxiflop06", States.FLOP);
        Assert.assertTrue(a4.isFolded());


        Hand h2 = handMap.get("146161206205");


        Hand h3 = handMap.get("146161179049");
        Hand h4 = handMap.get("146161159241");
        Hand h5 = handMap.get("146161285799");
        Hand h6 = handMap.get("146161159241");

        String[] res = {"146161243906", "146161206205", "146161179049", "146161159241", "146161285799"};


    }



    @Test
    public void testParse4() throws FileNotFoundException {

        HandParser  hp = new HandParser(getClass().getResource("/handhistory/hand1.txt").getFile());
        hp.parse();
        DataBase db = new DataBase(hp.getHands());
        Assert.assertEquals(0.5, db.computeVpip("MrClockOran"), 0.001);
        Assert.assertEquals(6, db.handsPlayed("MrClockOran"));
    }


    @Test
    public void testParse5() throws FileNotFoundException {

        HandParser  hp = new HandParser("/Users/sylvainmougel/" +
                "Library/Application Support/PokerStarsFR/HandHistory/MrCLockOran/");
        hp.parse();
        DataBase db = new DataBase(hp.getHands());
        String player1 = "MrClockOran";
        String player2 = "chefamo59";
        System.out.println(db.handsPlayed(player2));
        System.out.println(db.computeVpip(player2));
        System.out.println(db.computePfr(player2));
    }

    @Test
    public void testParseOrdering() throws FileNotFoundException {

        HandParser  hp = new HandParser("/Users/sylvainmougel/" +
                "Library/Application Support/PokerStarsFR/HandHistory/MrCLockOran/");
        hp.parse();

        SortedMap<String, Hand> handMap = hp.getHands();
        Assert.assertEquals( handMap.lastKey(), "146576579257");


    }
}
