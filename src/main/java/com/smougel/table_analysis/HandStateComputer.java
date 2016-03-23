package com.smougel.table_analysis;

import com.smougel.cards.Card;
import com.smougel.cards.Values;
import com.smougel.datamodel.IHandState;
import com.smougel.hands.Hand;

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
public class HandStateComputer {

    private BufferedImage[] cardPictures;
    private Card[] tableCards;
    private final Properties handProp;
    private Hand hand;

    public HandStateComputer(Properties tableProp) throws IOException {
        handProp = tableProp;
        cardPictures = new BufferedImage[7];
        tableCards = new Card[7];

    }


    public IHandState compute(BufferedImage imageTable) {
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

        Card[] hcs= {tableCards[5], tableCards[6]};
        return new HandState(getTableCards(), hcs);
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


    public void dump() {
        hand.dump();
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
