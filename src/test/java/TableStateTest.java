import com.smougel.context.TableState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by sylvainmougel on 01/04/15.
 */
public class TableStateTest {
    private TableState tbs;
    private BufferedImage bf1;
    private BufferedImage bf2;
    private BufferedImage bf3;
    private BufferedImage bf4;
    private BufferedImage bf5;
    private BufferedImage bf6;
    private BufferedImage bf7;
    private BufferedImage bf8;
    private BufferedImage bf9;
    private BufferedImage bf10;

    @Before
    public void init() {
        try {
            tbs = new TableState(new Properties());
            bf1 = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table0" + ".png"));
            bf2 = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table1" + ".png"));
            bf3 = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table2" + ".png"));
            bf4 = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table4" + ".png"));
            bf5 = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table5" + ".png"));
            bf6 = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table6" + ".png"));
            bf7 = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table7" + ".png"));
            bf8 = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table8" + ".png"));
            bf9 = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table9" + ".png"));
            bf10 = ImageIO.read(getClass().getResourceAsStream("/tables/" + "table10" + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }

    }

    @Test
    public void test1() {

        tbs.update(bf1);
        int[] expectedBets = {0, 10, 10, 0, 0, 0, 10, 0, 5};
        Assert.assertArrayEquals(expectedBets, tbs.getBets());
        Assert.assertEquals(0, tbs.getPot());
        Assert.assertEquals(7, tbs.getDealerPosition());
    }

    @Test
    public void test2() {
        tbs.update(bf2);
        int[] expectedBets2 = {0, 10, 10, 0, 20, 20, 10, 0, 5};
        Assert.assertArrayEquals(expectedBets2, tbs.getBets());
        Assert.assertEquals(0, tbs.getPot());
        Assert.assertEquals(7, tbs.getDealerPosition());

    }

    @Test
    public void test3() {
        tbs.update(bf3);
        int[] expectedBets3 = {0, 10, 10, 0, 20, 20, 20, 20,5};
        Assert.assertArrayEquals(expectedBets3, tbs.getBets());
        Assert.assertEquals(0, tbs.getPot());
        Assert.assertEquals(7, tbs.getDealerPosition());
    }

    @Test
    public void test4() {
        tbs.update(bf4);
        int[] expectedBets4 = {0, 0, 0, 0, 0, 0, 0, 0,0};
        Assert.assertArrayEquals(expectedBets4, tbs.getBets());
        Assert.assertEquals(119, tbs.getPot());
        Assert.assertEquals(7, tbs.getDealerPosition());
    }

    @Test
    public void test5() throws IOException {
        tbs.update(bf5);
        int[] expectedBets5 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        tbs.save();
        Assert.assertArrayEquals(expectedBets5, tbs.getBets());
        Assert.assertEquals(67, tbs.getPot());
        Assert.assertEquals(2 , tbs.getDealerPosition());
    }


    @Test
    public void test6() {
        tbs.update(bf6);
        int[] expectedBets6 = {0, 0, 0, 5, 10, 10, 0, 0, 0};
        Assert.assertArrayEquals(expectedBets6, tbs.getBets());
        Assert.assertEquals(0, tbs.getPot());
        Assert.assertEquals(2, tbs.getDealerPosition());
    }

    @Test
    public void test7() {
        tbs.update(bf7);
        int[] expectedBets6 = {0, 0, 0, 5, 10, 10, 0, 502, 0};
        Assert.assertArrayEquals(expectedBets6, tbs.getBets());
        Assert.assertEquals(0, tbs.getPot());
        Assert.assertEquals(2, tbs.getDealerPosition());
    }


    @Test
    public void test8() {
        tbs.update(bf8);
        int[] expectedBets = {0, 0, 0, 0, 0, 130, 0, 0, 0};
        Assert.assertArrayEquals(expectedBets, tbs.getBets());
        Assert.assertEquals(883, tbs.getPot());
        Assert.assertEquals(1, tbs.getDealerPosition());
    }

    @Test
    public void test9() {
        tbs.update(bf9);
        int[] expectedBets = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        Assert.assertArrayEquals(expectedBets, tbs.getBets());
        Assert.assertEquals(435, tbs.getPot());
    }

    @Test
    public void test10() {
        tbs.update(bf10);
        int[] expectedBets = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        Assert.assertArrayEquals(expectedBets, tbs.getBets());
        Assert.assertEquals(12, tbs.getPot());
        Assert.assertEquals(4, tbs.getDealerPosition());
    }

}
