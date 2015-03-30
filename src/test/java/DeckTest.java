import com.smougel.cards.Card;
import com.smougel.hands.Deck;
import org.junit.Assert;
import org.junit.Test;


import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by sylvainmougel on 24/03/15.
 */
public class DeckTest {

    @Test
    public void creation () {
        Deck deck = new Deck();
        Assert.assertEquals(52, deck.nbOfCards());

        Card c1 = deck.giveCard();
        Card c2 = deck.giveCard();
        Assert.assertFalse(c1.toString().equals(c2.toString()));
        Assert.assertEquals(50, deck.nbOfCards());
        System.out.println(deck.lookCard(0));
        deck.shuffle();
        System.out.println(deck.lookCard(0));
        Assert.assertEquals(50, deck.nbOfCards());
        deck.reset();
        Assert.assertEquals(52, deck.nbOfCards());

    }

    @Test
    public void order () {
        Deck deck = new Deck();
        SortedSet<Card> cards = new TreeSet<Card>();
        deck.shuffle();
        cards.add(deck.giveCard());
        cards.add(deck.giveCard());
        cards.add(deck.giveCard());
        cards.add(deck.giveCard());
        cards.add(deck.giveCard());
        for (Card c : cards) {
            System.out.println(c.toString());
        }


    }

    @Test
    public void remove () {
        Deck deck = new Deck();
        SortedSet<Card> cards = new TreeSet<Card>();
        deck.shuffle();

        deck.remove("Ks");
        Assert.assertTrue(deck.nbOfCards() == 51);
    }
}
