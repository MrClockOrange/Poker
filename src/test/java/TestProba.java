import com.smougel.cards.Card;
import com.smougel.hands.Deck;
import com.smougel.hands.Proba;
import org.junit.Test;

/**
 * Created by sylvainmougel on 26/03/15.
 */
public class TestProba {

    @Test
    public void probAA() {
        Proba proba = new Proba(Deck.CARDS.get("As"), Deck.CARDS.get("Ad"));
        System.out.println(proba.compute(1000, 1));
    }

    @Test
    public void probAAMulti() {
        Proba proba = new Proba(Deck.CARDS.get("Js"), Deck.CARDS.get("3s"));
        System.out.println(proba.compute(1000, 5));
    }

    @Test
    public void probShittyHand() {
        Proba proba = new Proba(Deck.CARDS.get("2s"), Deck.CARDS.get("7d"));
        System.out.println(proba.compute(10000, 1));
    }

    @Test
    public void probTestFlop() {
        Card[] flop = {Deck.CARDS.get("2h"), Deck.CARDS.get("Ac"), Deck.CARDS.get("3s")};
        Proba proba = new Proba(Deck.CARDS.get("As"), Deck.CARDS.get("Ad"), flop);
        System.out.println(proba.compute(10000, 1));
    }

    @Test
    public void probTestFlopQueen() {
        Card[] flop = {Deck.CARDS.get("Qh"), Deck.CARDS.get("Qc"), Deck.CARDS.get("3s")};
        Proba proba = new Proba(Deck.CARDS.get("As"), Deck.CARDS.get("Ad"), flop);
        System.out.println(proba.compute(10000, 1));
    }
}
