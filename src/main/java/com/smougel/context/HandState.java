package com.smougel.context;

import com.smougel.cards.Card;
import com.smougel.cards.Values;
import com.smougel.hands.Hand;
import com.smougel.hands.Proba;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by sylvainmougel on 20/06/15.
 */
public class HandState {

    private BufferedImage[] cardPictures;
    private Card[] tableCards;
    private final Properties handProp;
    private Hand hand;

    public HandState(Properties tableProp) throws IOException {
        handProp = tableProp;
        cardPictures = new BufferedImage[7];
        tableCards = new Card[7];

    }


    public void update(BufferedImage imageTable) {
        // Extract the sub images
        for (int i =0; i<7; i ++) {
            cardPictures[i] = imageTable.getSubimage(
                    Integer.valueOf(handProp.getProperty("card." + i + ".X")),
                    Integer.valueOf(handProp.getProperty("card." + i + ".Y")),
                    Integer.valueOf(handProp.getProperty("card.size.width")),
                    Integer.valueOf(handProp.getProperty("card.size.height")));

        }
        saveCards();

        // Recognize the different cards
        for (int i = 0; i < cardPictures.length; i++) {
            BufferedImage bf = null;
            try {
                bf = ImageIO.read(new File(handProp.getProperty("home") + "card" + i + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            tableCards[i] = new Card(bf);
        }

        // Build the hand from the recognized cards
        hand = new Hand(tableCards);
    }

    public void saveCards() {
        File[] outFiles = new File[7];
        for (int i = 0; i < cardPictures.length; i++) {
            outFiles[i] = new File(handProp.getProperty("home") + "card" + i + ".png");
        }

        try {
            for (int i = 0; i < cardPictures.length; i++) {
                ImageIO.write(cardPictures[i], "png", outFiles[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public States retrieveState() {
        States result = States.PREFLOP;
        if (hand.nbOfCard() == 2) {
            result = States.PREFLOP;
        } else if (hand.nbOfCard() == 5) {
            result = States.FLOP;
        } else if (hand.nbOfCard() == 6) {
            result = States.TURN;
        } else if (hand.nbOfCard() == 7) {
            result = States.RIVER;
        }
        return result;
    }

    public void dump() {
        hand.dump();
    }

    public float getWinningProba(int playersNb) {
        float result = 0;
        if (!tableCards[5].getValue().equals(Values.NONE) && !tableCards[6].getValue().equals(Values.NONE)) {

            Proba proba = new Proba(tableCards[5], tableCards[6], getTableCards());
            result = proba.compute(10000, playersNb);

        }
        return result;
    }

    public Card[] getTableCards() {
        List<Card> res = new ArrayList<Card>();
        for (int i = 0; i < 5; i++) {
            if (!tableCards[i].getValue().equals(Values.NONE)) {
                res.add(tableCards[i]);
            }

        }
        Card[] r = new Card[res.size()];
        return res.toArray(r);

    }
}
