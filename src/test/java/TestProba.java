import com.smougel.cards.Card;
import com.smougel.hands.Deck;
import com.smougel.hands.Proba;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by sylvainmougel on 26/03/15.
 */
public class TestProba {

    @Test
    public void probAA() {
        Proba proba = new Proba(Deck.CARDS.get("As"), Deck.CARDS.get("Ad"));
        float prob = proba.compute(1000, 1);
        assertRange(0.83, 0.87, prob);
    }

    @Test
    public void probAAMulti() {
        Proba proba = new Proba(Deck.CARDS.get("As"), Deck.CARDS.get("Ad"));
        float prob = proba.compute(1000, 5);
        assertRange(0.48, 0.52, prob);

    }

    @Test
    public void probShittyHand() {
        Proba proba = new Proba(Deck.CARDS.get("2s"), Deck.CARDS.get("7d"));
        float prob = proba.compute(10000, 1);
        assertRange(0.35, 0.39, prob);
    }

    @Test
    public void probTestFlop() {
        Card[] flop = {Deck.CARDS.get("2h"), Deck.CARDS.get("Ac"), Deck.CARDS.get("3s")};
        Proba proba = new Proba(Deck.CARDS.get("As"), Deck.CARDS.get("Ad"), flop);
        float prob = proba.compute(10000, 1);
        assertRange(0.92, 0.96, prob);
    }

    @Test
    public void probTestFlopQueen() {
        Card[] flop = {Deck.CARDS.get("Qh"), Deck.CARDS.get("Qc"), Deck.CARDS.get("3s")};
        Proba proba = new Proba(Deck.CARDS.get("As"), Deck.CARDS.get("Ad"), flop);
        float prob = proba.compute(10000, 1);
        assertRange(0.86, 0.90, prob);
    }

    private void assertRange(double min, double max, double actual) {
        try {
            Assert.assertTrue(actual > min);
            Assert.assertTrue(actual < max);
        } catch (AssertionError e) {
            System.out.println("Min : " + min);
            System.out.println("Max : " + max);
            System.out.println("Actual : " + actual);
        }
    }
}
