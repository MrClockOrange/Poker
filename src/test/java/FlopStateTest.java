import com.smougel.handparser.Hand;
import com.smougel.handparser.HandParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.SortedMap;

/**
 * Created by sylvainmougel on 06/02/2016.
 */
public class FlopStateTest {

    private HandParser hpTest;

    @Before
    public void init() throws FileNotFoundException {
        hpTest = new HandParser(getClass().getResource("/handhistory/hand1.txt").getFile());
    }

    @Test
    public void test1() throws FileNotFoundException {
        SortedMap<String, Hand> hands = hpTest.getHands();
        Hand h = hands.get(hands.lastKey());
    }

}


