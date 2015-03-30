import com.smougel.cards.Card;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by sylvainmougel on 23/03/15.
 */
public class CardTest  {

    @Test
    public void card() {
        try {

            BufferedImage bf = ImageIO.read(getClass().getResourceAsStream("/cards/" + "5h" + ".png"));
            Card fiveHearts = new Card(bf);
            Assert.assertEquals(fiveHearts.toString(), "5h");

            bf = ImageIO.read(getClass().getResourceAsStream("/cards/" + "Ad" + ".png"));
            Card aceDiamonds = new Card(bf);
            Assert.assertEquals(aceDiamonds.toString(), "Ad");

            bf = ImageIO.read(getClass().getResourceAsStream("/cards/" + "Qc" + ".png"));
            Card queenClubs = new Card(bf);
            Assert.assertEquals(queenClubs.toString(), "Qc");

        } catch (IOException e) {
            System.out.println("exception !!");
            e.printStackTrace();
        }
    }

}
