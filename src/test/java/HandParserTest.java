
import com.smougel.handparser.Hand;
import com.smougel.handparser.HandParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        String[] res = {"146161243906", "146161206205", "146161179049", "146161159241", "146161285799"};
        Assert.assertArrayEquals(res, handMap.keySet().toArray());
        System.out.println(handMap.keySet());


    }
}
