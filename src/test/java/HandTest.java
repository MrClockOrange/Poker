import com.smougel.cards.Values;
import com.smougel.hands.Figure;
import com.smougel.hands.FigureType;
import com.smougel.hands.Hand;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by sylvainmougel on 24/03/15.
 */
public class HandTest {


    @Test
    public void getFlush() {

        Hand hand = new Hand("Ac", "Qc", "Kc", "Ad", "Qh", "4c", "2c");

        Figure figure = hand.getFlush();
        Assert.assertTrue(figure.getType().equals(FigureType.FLUSH));

        Hand hand2 = new Hand("Ac", "Qc", "Kd", "Ad", "Qh", "4h", "2s");

        figure = hand2.getFlush();

        Assert.assertNull(figure);


    }

    @Test
    public void getStraight() {

        Hand hand1 = new Hand("Ac", "Qc", "Kc", "Ad", "Jh", "4c", "10c");
        Figure figure = hand1.getStraight();
        Assert.assertTrue(figure.getType().equals(FigureType.STRAIGHT));

        Hand hand2 = new Hand("Ac", "Qc", "5c", "Ad", "2d", "4c", "3s");
        figure = hand2.getStraight();
        Assert.assertTrue(figure.getType().equals(FigureType.STRAIGHT));

        Hand hand3 = new Hand("Ac", "Qc", "5c", "6d", "4d", "3c", "As");
        figure = hand3.getStraight();
        Assert.assertNull(figure);


    }

    @Test
    public void getQuintFlush() {

        Hand hand1 = new Hand("Ac", "Qc", "Kc", "Ad", "10c", "4c", "Jc");
        Figure figure = hand1.getQuintFlush();
        Assert.assertTrue(figure.getType().equals(FigureType.QUINTFLUSH));

        Hand hand2 = new Hand("Ac", "Qc", "Kc", "Ad", "10c", "4c", "Js");
        figure = hand2.getQuintFlush();
        Assert.assertNull(figure);

        Hand hand3 = new Hand("Ac", "Qc", "Kc", "Ad", "10c", "4c", "10c");
        figure = hand3.getQuintFlush();
        Assert.assertNull(figure);


    }


    @Test
         public void getThreeOfAKind() {

        Hand hand1 = new Hand("Ac", "2c", "Kc", "Ad", "10c", "Ah", "Jc");
        Figure figure = hand1.getThreeOfAKind();
        Assert.assertTrue(figure.getType().equals(FigureType.THREE_OF_A_KIND));
        Assert.assertTrue(figure.getKickers().get(1).toString().equals("Kc"));
        Assert.assertTrue(figure.getKickers().get(2).toString().equals("Jc"));

        Hand hand2 = new Hand("Ac", "2c", "2h", "Ad", "10c", "Ah", "2s");
        figure = hand2.getThreeOfAKind();
        Assert.assertTrue(figure.getType().equals(FigureType.THREE_OF_A_KIND));
        Assert.assertTrue(figure.getKickers().get(0).getValue().equals(Values.ACE));

        Hand hand3 = new Hand("Kc", "2c", "2h", "Ad", "10c", "Ah", "Qs");
        figure = hand3.getThreeOfAKind();
        Assert.assertNull(figure);


    }


    @Test
    public void getFourOfAKind() {

        Hand hand1 = new Hand("Ac", "2c", "Kc", "Ad", "10c", "Ah", "As");
        Figure figure = hand1.getFourOfAKind();
        Assert.assertTrue(figure.getType().equals(FigureType.FOUR_OF_A_KIND));
        Assert.assertTrue(figure.getKickers().get(0).getValue().equals(Values.ACE));
        Assert.assertTrue(figure.getKickers().get(1).toString().equals("Kc"));


        Hand hand3 = new Hand("Kc", "2c", "2h", "Ad", "10c", "Ah", "Qs");
        figure = hand3.getFourOfAKind();
        Assert.assertNull(figure);


    }

    @Test
    public void getDoublePair() {

        Hand hand1 = new Hand("Ac", "2c", "Kc", "Jd", "10c", "2h", "As");
        Figure figure = hand1.getDoublePair();
        Assert.assertTrue(figure.getType().equals(FigureType.DOUBLE_PAIR));
        Assert.assertTrue(figure.getKickers().get(0).getValue().equals(Values.ACE));
        Assert.assertTrue(figure.getKickers().get(1).getValue().equals(Values.TWO));
        Assert.assertTrue(figure.getKickers().get(2).getValue().equals(Values.KING));

        Hand hand3 = new Hand("Kc", "2c", "3h", "Ad", "10c", "Ah", "Qs");
        figure = hand3.getFourOfAKind();
        Assert.assertNull(figure);


    }

    @Test
    public void getFull() {

        Hand hand1 = new Hand("Ac", "2c", "Kc", "2d", "10c", "2h", "As");
        Figure figure = hand1.getFull();
        Assert.assertTrue(figure.getType().equals(FigureType.FULL));
        Assert.assertTrue(figure.getKickers().get(0).getValue().equals(Values.TWO));
        Assert.assertTrue(figure.getKickers().get(1).getValue().equals(Values.ACE));


        Hand hand3 = new Hand("Ac", "2c", "3h", "Ad", "10c", "Ah", "Qs");
        figure = hand3.getFull();
        Assert.assertNull(figure);


    }

    @Test
    public void getPair() {

        Hand hand1 = new Hand("4c", "5c", "Kc", "2d", "10c", "2h", "As");
        Figure figure = hand1.getPair();
        Assert.assertTrue(figure.getType().equals(FigureType.PAIR));
        Assert.assertTrue(figure.getKickers().get(0).getValue().equals(Values.TWO));
        Assert.assertTrue(figure.getKickers().get(1).getValue().equals(Values.ACE));
        Assert.assertTrue(figure.getKickers().get(2).getValue().equals(Values.KING));
        Assert.assertTrue(figure.getKickers().get(3).getValue().equals(Values.TEN));


        Hand hand3 = new Hand("Ac", "2c", "3h", "9d", "10c", "8h", "Qs");
        figure = hand3.getFull();
        Assert.assertNull(figure);


    }

    @Test
    public void getNone() {

        Hand hand1 = new Hand("4c", "5c", "Kc", "8d", "10c", "2h", "As");
        Figure figure = hand1.getNone();
        Assert.assertTrue(figure.getType().equals(FigureType.NOTHING));
        Assert.assertTrue(figure.getKickers().get(0).getValue().equals(Values.ACE));
        Assert.assertTrue(figure.getKickers().get(1).getValue().equals(Values.KING));
        Assert.assertTrue(figure.getKickers().get(2).getValue().equals(Values.TEN));
        Assert.assertTrue(figure.getKickers().get(3).getValue().equals(Values.EIGHT));
        Assert.assertTrue(figure.getKickers().get(4).getValue().equals(Values.FIVE));



    }


    @Test
    public void findFigure() {

        Hand hand1 = new Hand("4c", "5c", "Kc", "Kd", "10c", "2h", "As");
        Figure figure = hand1.getBestFigure();
        Assert.assertTrue(figure.getType().equals(FigureType.PAIR));
        Assert.assertTrue(figure.getKickers().get(0).getValue().equals(Values.KING));
        Assert.assertTrue(figure.getKickers().get(1).getValue().equals(Values.ACE));
        Assert.assertTrue(figure.getKickers().get(2).getValue().equals(Values.TEN));
        Assert.assertTrue(figure.getKickers().get(3).getValue().equals(Values.FIVE));

        Hand hand2 = new Hand("4c", "5c", "Kc", "Ad", "10c", "4h", "As");
        figure = hand2.getBestFigure();
        Assert.assertTrue(figure.getType().equals(FigureType.DOUBLE_PAIR));
        Assert.assertTrue(figure.getKickers().get(0).getValue().equals(Values.ACE));
        Assert.assertTrue(figure.getKickers().get(1).getValue().equals(Values.FOUR));
        Assert.assertTrue(figure.getKickers().get(2).getValue().equals(Values.KING));

        Hand hand3 = new Hand("4c", "5c", "Kc", "Ad", "10c", "4h", "4s");
        figure = hand3.getBestFigure();
        Assert.assertTrue(figure.getType().equals(FigureType.THREE_OF_A_KIND));


        Hand hand4 = new Hand("2h", "Kc", "7h", "Jd", "Jc");
        figure = hand4.getBestFigure();
        Assert.assertTrue(figure.getType().equals(FigureType.PAIR));

        Hand hand5 = new Hand("Ac", "Kh", "Qh", "10c", "5c");
        figure = hand5.getBestFigure();
        Assert.assertTrue(figure.getType().equals(FigureType.NOTHING));


        Hand hand6 = new Hand("Qh", "2h");
        figure = hand5.getBestFigure();
        Assert.assertTrue(figure.getType().equals(FigureType.NOTHING));

    }

}
