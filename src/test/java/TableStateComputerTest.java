import com.smougel.context.IPlayersState;
import com.smougel.context.PlayerStateComputer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by sylvainmougel on 01/04/15.
 */
public class TableStateComputerTest {
    private PlayerStateComputer tableStateComputer;
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
            Properties p = new Properties();
            p.load(new FileInputStream("table.properties"));
            tableStateComputer = new PlayerStateComputer(p);
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

        IPlayersState tbs = tableStateComputer.compute(bf1);
        int[] expectedBets = {0, 10, 10, 0, 0, 0, 10, 0, 5};
        Assert.assertArrayEquals(expectedBets, tbs.getBets());
        Assert.assertEquals(0, tbs.getPot());
        Assert.assertEquals(1, tbs.getPos());
    }

    @Test
    public void test2() {
        IPlayersState tbs = tableStateComputer.compute(bf2);
        int[] expectedBets2 = {0, 10, 10, 0, 20, 20, 10, 0, 5};
        Assert.assertArrayEquals(expectedBets2, tbs.getBets());
        Assert.assertEquals(0, tbs.getPot());
        Assert.assertEquals(1, tbs.getPos());

    }

    @Test
    public void test3() {
        IPlayersState tbs = tableStateComputer.compute(bf3);
        int[] expectedBets3 = {0, 10, 10, 0, 20, 20, 20, 20,5};
        Assert.assertArrayEquals(expectedBets3, tbs.getBets());
        Assert.assertEquals(0, tbs.getPot());
        Assert.assertEquals(7, tbs.getDealerPosition());
    }

    @Test
    public void test4() {
        IPlayersState tbs = tableStateComputer.compute(bf4);
        int[] expectedBets4 = {0, 0, 0, 0, 0, 0, 0, 0,0};
        Assert.assertArrayEquals(expectedBets4, tbs.getBets());
        Assert.assertEquals(119, tbs.getPot());
        Assert.assertEquals(7, tbs.getDealerPosition());
    }

    @Test
    public void test5() throws IOException {
        IPlayersState tbs = tableStateComputer.compute(bf5);
        int[] expectedBets5 = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        tableStateComputer.save();
        Assert.assertArrayEquals(expectedBets5, tbs.getBets());
        Assert.assertEquals(67, tbs.getPot());
        Assert.assertEquals(2, tbs.getDealerPosition());
    }


    @Test
    public void test6() {
        IPlayersState tbs = tableStateComputer.compute(bf6);
        int[] expectedBets6 = {0, 0, 0, 5, 10, 10, 0, 0, 0};
        Assert.assertArrayEquals(expectedBets6, tbs.getBets());
        Assert.assertEquals(0, tbs.getPot());
        Assert.assertEquals(2, tbs.getDealerPosition());
    }

    @Test
    public void test7() {
        IPlayersState tbs = tableStateComputer.compute(bf7);
        int[] expectedBets6 = {0, 0, 0, 5, 10, 10, 0, 502, 0};
        Assert.assertArrayEquals(expectedBets6, tbs.getBets());
        Assert.assertEquals(0, tbs.getPot());
        Assert.assertEquals(2, tbs.getDealerPosition());
    }


    @Test
    public void test8() {
        IPlayersState tbs = tableStateComputer.compute(bf8);
        int[] expectedBets = {0, 0, 0, 0, 0, 130, 0, 0, 0};
        Assert.assertArrayEquals(expectedBets, tbs.getBets());
        Assert.assertEquals(883, tbs.getPot());
        Assert.assertEquals(1, tbs.getDealerPosition());
    }

    @Test
    public void test9() {
        IPlayersState tbs = tableStateComputer.compute(bf9);
        int[] expectedBets = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        Assert.assertArrayEquals(expectedBets, tbs.getBets());
        Assert.assertEquals(435, tbs.getPot());
    }

    @Test
    public void test10() {
        IPlayersState tbs = tableStateComputer.compute(bf10);
        int[] expectedBets = {0, 0, 0, 0, 0, 0, 0, 0, 0};
        Assert.assertArrayEquals(expectedBets, tbs.getBets());
        Assert.assertEquals(12, tbs.getPot());
        Assert.assertEquals(4, tbs.getDealerPosition());
    }

}
