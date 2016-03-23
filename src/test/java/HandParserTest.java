
import com.smougel.datamodel.States;
import com.smougel.handparser.Actions;
import com.smougel.handparser.DataBase;
import com.smougel.handparser.Hand;
import com.smougel.handparser.HandParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by sylvainmougel on 30/12/15.
 */
public class HandParserTest {

    private HandParser hpTest;
    private HandParser hpReal;

    @Before
    public void init() throws FileNotFoundException {
          hpTest = new HandParser(getClass().getResource("/handhistory/hand1.txt").getFile());
          hpReal = new HandParser("/Users/sylvainmougel/" +
                  "Library/Application Support/PokerStarsFR/HandHistory/MrCLockOran/");;
    }

    @Test
    public void testParse() throws FileNotFoundException {
        hpReal.parse();

    }

    @Test
    public void testParse2() throws FileNotFoundException {

        Map<String, Hand> handMap = hpTest.getHands();
        String[] res = {"146161243906", "146161206205", "146161179049", "146161338428",
                "146161159241", "146161285799"};
        for (String s : res) {
            Assert.assertTrue(handMap.containsKey(s));
        }


    }

    @Test
    public void testNbOfActions() throws FileNotFoundException {
        SortedMap<String, Hand> handMap = hpTest.getHands();
        Hand h1 = handMap.get("146161243906");
        Assert.assertEquals("ManMg", h1.getDealerName());
        Assert.assertEquals(7, h1.getPosition("MrClockOran"));

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
        Assert.assertEquals("MrClockOran", h2.getDealerName());
        Assert.assertEquals(8,h2.getPosition(h2.getHero()));

        Hand h3 = handMap.get("146161338428");
        Assert.assertEquals(5, h3.getPosition("MrClockOran"));


        Hand h6 = handMap.get("146161159241");
        Assert.assertEquals(2,h6.getPosition(h6.getHero()));

        String[] res = {"146161243906", "146161206205", "146161179049", "146161159241", "146161285799"};


    }

    @Test
    public void testGetHoleCard() throws FileNotFoundException {
        SortedMap<String, Hand> handMap = hpTest.getHands();
        Hand h4 = handMap.get("146161159241");
        Assert.assertEquals("4c 6d", h4.getHoleCards());
        Assert.assertEquals("MrClockOran", h4.getHero());
        Hand h5 = handMap.get("146161285799");
        Assert.assertEquals("4d 9c", h5.getHoleCards());
        Assert.assertEquals("MrClockOran", h5.getHero());
    }



    @Test
    public void testStatistics() throws FileNotFoundException {
        DataBase db = new DataBase(hpTest.getHands());
        Assert.assertEquals(0.5, db.computeVpip("MrClockOran"), 0.001);
        Assert.assertEquals(6, db.handsPlayed("MrClockOran"));
    }


    @Test
    public void testStatsComputation() throws FileNotFoundException {

        DataBase db = new DataBase(hpReal.getHands());
        String player1 = "MrClockOran";
        String player2 = "chefamo59";
        System.out.println(db.handsPlayed(player2));
        System.out.println(db.computeVpip(player2));
        System.out.println(db.computePfr(player2));
    }

    @Test
    public void testParseOrdering() throws FileNotFoundException {

        SortedMap<String, Hand> handMap = hpReal.getHands();
        //Assert.assertEquals( handMap.lastKey(), "146576579257");


    }

    @Test
    public void testFlopState() throws FileNotFoundException {

        SortedMap<String, Hand> handMap = hpReal.getHands();
        //Assert.assertEquals( handMap.lastKey(), "146576579257");


    }
}
