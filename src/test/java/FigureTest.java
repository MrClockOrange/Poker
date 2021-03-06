import com.smougel.cards.Card;
import com.smougel.cards.Color;
import com.smougel.cards.ICard;
import com.smougel.cards.Values;
import com.smougel.hands.Figure;
import com.smougel.hands.FigureType;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sylvainmougel on 24/03/15.
 */
public class FigureTest {


    @Test
    public void testCompare() {
        ICard c1 = new Card(Color.CLUBS, Values.ACE);
        ICard c2 = new Card(Color.CLUBS, Values.QUEEN);
        ICard c3 = new Card(Color.DIAMONDS, Values.KING);
        ICard c4 = new Card(Color.DIAMONDS, Values.ACE);

        List<ICard> l1 = new ArrayList<ICard>();
        List<ICard> l2 = new ArrayList<ICard>();
        l1.add(c1);
        l1.add(c2);
        l2.add(c4);
        l2.add(c3);
        Figure f1 = new Figure(FigureType.FLUSH, l1);
        Figure f2 = new Figure(FigureType.FLUSH, l2);
        Assert.assertTrue(f1.compareTo(f2) < 0);
    }



}
