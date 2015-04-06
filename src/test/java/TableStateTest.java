import com.smougel.TableState;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by sylvainmougel on 01/04/15.
 */
public class TableStateTest {

    @Test
    public void testPreFlop() {
        TableState tbs = null;
        try {
            tbs = new TableState();

            // Table 1
            BufferedImage bf = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table0" + ".png"));
            tbs.update(bf);
            int[] expectedBets = {0, 10, 10, 0, 0, 0, 10, 0};
            Assert.assertArrayEquals(expectedBets, tbs.getBets());
            Assert.assertEquals(0, tbs.getPot());

            // Table 2
            bf = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table1" + ".png"));
            tbs.update(bf);
            int [] expectedBets2 = {0, 10, 10, 0, 20, 20, 10, 0};
            Assert.assertArrayEquals(expectedBets2, tbs.getBets());
            Assert.assertEquals(0, tbs.getPot());

            // Table 3
            bf = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table2" + ".png"));
            tbs.update(bf);
            int [] expectedBets3 = {0, 10, 10, 0, 20, 20, 20, 20};
            Assert.assertArrayEquals(expectedBets3, tbs.getBets());
            Assert.assertEquals(0, tbs.getPot());

            // Table 4
            bf = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table4" + ".png"));
            tbs.update(bf);
            int [] expectedBets4 = {0, 0, 0, 0, 0, 0, 0, 0};
            Assert.assertArrayEquals(expectedBets4, tbs.getBets());
            Assert.assertEquals(119, tbs.getPot());

        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
