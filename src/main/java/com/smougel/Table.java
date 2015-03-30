package com.smougel;

import com.smougel.cards.Card;
import com.smougel.cards.Values;
import com.smougel.hands.Hand;
import com.smougel.hands.Proba;
import com.smougel.tablestate.TableState;
import com.smougel.utils.Position;
import com.smougel.utils.Size;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by sylvainmougel on 15/03/15.
 */
public class Table {

    private final static Size CARD_SIZE = new Size(11, 24);
    private final static Size COLOR_SIZE = new Size(11, 12);
    private final static Size VALUE_SIZE = new Size(11, 13);

    /** Position of the cards (upper left corner) from the upper left corner of the screenshot */
    private final static Position card1Pos = new Position(164,97);
    private final static Position card2Pos = new Position(196,97);
    private final static Position card3Pos = new Position(228,97);
    private final static Position card4Pos = new Position(260,97);
    private final static Position card5Pos = new Position(292,97);



    private final static Position myCard1 = new Position(223,208);
    private final static Position myCard2 = new Position(236,210);



    private static final String RESOURCES_CARDS = "/Users/sylvainmougel/git/Poker/src/main/resources/samples/";


    private BufferedImage[] cardPictures;
    private BufferedImage tablePicture;
    private Card[] tableCards;

    private TableState tableState;
    private Hand hand;
    private Hand previouHand;


    Table () {
        tableCards = new Card[7];
        cardPictures = new BufferedImage[7];
        tableState = new TableState();
    }


    public void update(BufferedImage tableScreenShot) throws IOException {

        tablePicture = tableScreenShot;

        cardPictures[0] = tableScreenShot.getSubimage(card1Pos.getLeft(), card1Pos.getUpper(), CARD_SIZE.getWidth(), CARD_SIZE.getHeight());
        cardPictures[1] = tableScreenShot.getSubimage(card2Pos.getLeft(), card2Pos.getUpper(), CARD_SIZE.getWidth(), CARD_SIZE.getHeight());
        cardPictures[2] = tableScreenShot.getSubimage(card3Pos.getLeft(), card3Pos.getUpper(), CARD_SIZE.getWidth(), CARD_SIZE.getHeight());
        cardPictures[3] = tableScreenShot.getSubimage(card4Pos.getLeft(), card4Pos.getUpper(), CARD_SIZE.getWidth(), CARD_SIZE.getHeight());
        cardPictures[4] = tableScreenShot.getSubimage(card5Pos.getLeft(), card5Pos.getUpper(), CARD_SIZE.getWidth(), CARD_SIZE.getHeight());

        // Hand Cards
        cardPictures[5] = tableScreenShot.getSubimage(myCard1.getLeft(), myCard1.getUpper(), CARD_SIZE.getWidth(), CARD_SIZE.getHeight());
        cardPictures[6] = tableScreenShot.getSubimage(myCard2.getLeft(), myCard2.getUpper(), CARD_SIZE.getWidth(), CARD_SIZE.getHeight());
        saveCards();
        for (int i = 0; i < cardPictures.length; i++) {


            BufferedImage bf = ImageIO.read(new File("src/main/resources/samples/card" + i + ".png"));
            tableCards[i] = new Card(bf);

        }
        Hand tmp = new Hand(tableCards);


        if (hand == null || tmp.nbOfCard() != hand.nbOfCard() ) {
            hand = tmp;
            hand.dump();
            this.toString();
            if (!tableCards[5].getValue().equals(Values.NONE) && !tableCards[6].getValue().equals(Values.NONE)) {

                Proba proba = new Proba(tableCards[5], tableCards[6], getTableCards());
                System.out.println("Win probability (1 player): " + proba.compute(10000, 1));
                System.out.println("Win probability (3 playerq): " + proba.compute(10000, 3));
                System.out.println("Win probability (5 players): " + proba.compute(10000, 5));
                //Proba proba = new Proba();
            }
        }









    }

    public Card[] getTableCards() {
        List<Card> res = new ArrayList<Card>();
        for (int i = 0; i < 5; i++) {
            if (!tableCards[i].getValue().equals(Values.NONE)) {
                res.add(tableCards[i]);
            }

        }
        Card[] r = new Card[res.size()];
        return  res.toArray(r);

    }

    public BufferedImage[] getCardPictures() {
        return cardPictures;
    }

    public void saveCards() {
        File[] outFiles = new File[7];
        for (int i = 0; i < cardPictures.length; i++) {
            outFiles[i] = new File(RESOURCES_CARDS + "card" + i + ".png");
        }

        File table = new File(RESOURCES_CARDS + "table.png");
        try {
            for (int i = 0; i < cardPictures.length; i++) {

                ImageIO.write(cardPictures[i], "png", outFiles[i]);
            }

            ImageIO.write(tablePicture, "png", table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

    }

    public void display() throws AWTException {

        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());

        frame.getContentPane().add(new JLabel(new ImageIcon(tablePicture)));
        frame.getContentPane().add(new JLabel(new ImageIcon(cardPictures[0].getScaledInstance(120, 200, Image.SCALE_SMOOTH))));
        frame.getContentPane().add(new JLabel(new ImageIcon(cardPictures[1].getScaledInstance(120, 200, Image.SCALE_SMOOTH))));
        frame.getContentPane().add(new JLabel(new ImageIcon(cardPictures[2].getScaledInstance(120, 200, Image.SCALE_SMOOTH))));
        frame.getContentPane().add(new JLabel(new ImageIcon(cardPictures[3].getScaledInstance(120, 200, Image.SCALE_SMOOTH))));
        frame.getContentPane().add(new JLabel(new ImageIcon(cardPictures[4].getScaledInstance(120, 200, Image.SCALE_SMOOTH))));

        frame.setSize(700,700);

        frame.setLocation(1500,0);
        frame.setVisible(true);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append(tableCards[i].toString());
            sb.append(" ");
        }
        sb.append("  |||| ");
        for (int i = 5; i < 7; i++) {
            sb.append(tableCards[i].toString());
            sb.append(" ");
        }


        return sb.toString();
    }
}
