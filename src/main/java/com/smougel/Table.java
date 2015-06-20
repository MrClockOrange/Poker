package com.smougel;

import com.smougel.cards.Card;
import com.smougel.cards.Values;
import com.smougel.hands.Hand;
import com.smougel.hands.Proba;
import com.smougel.utils.Position;
import com.smougel.utils.Size;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sylvainmougel on 15/03/15.
 */
public class Table {

    private BufferedImage[] cardPictures;
    private BufferedImage tablePicture;
    private Card[] tableCards;

    private TableState tableState;
    private Hand hand;
    private int updateNb;
    private States state = States.PREFLOP;
    private final java.util.Properties tableProperties;



    Table() throws IOException {
        tableProperties = new java.util.Properties();
        tableProperties.load(new FileInputStream("table.properties"));
        tableCards = new Card[7];
        cardPictures = new BufferedImage[7];
        tableState = new TableState(tableProperties);
        updateNb = 0;
    }


    public void update(BufferedImage tableScreenShot) throws IOException {

        tablePicture = tableScreenShot;
        tableState.update(tablePicture);

        // Extract the sub images
        for (int i =0; i<7; i ++) {
            cardPictures[i] = tableScreenShot.getSubimage(
                    Integer.valueOf(tableProperties.getProperty("card." + i + ".X")),
                    Integer.valueOf(tableProperties.getProperty("card." + i + ".Y")),
                    Integer.valueOf(tableProperties.getProperty("card.size.width")),
                    Integer.valueOf(tableProperties.getProperty("card.size.height")));

        }
        saveCards();

        // Recognize the different cards
        for (int i = 0; i < cardPictures.length; i++) {
            BufferedImage bf = ImageIO.read(new File(Properties.HOME + "card" + i + ".png"));
            tableCards[i] = new Card(bf);
        }

        hand = new Hand(tableCards);

        if (tableState.isNewGame()) {
            System.out.println("====== NEW GAME =====");

        }
        state = retrieveState();
        System.out.println("======" + state + "=====");

        tableState.dump();

        System.out.println("======"  + "=====");

        int nb = tableState.getNumberOfPlayersIn();


        hand.dump();
        this.toString();
        if (!tableCards[5].getValue().equals(Values.NONE) && !tableCards[6].getValue().equals(Values.NONE)) {

            Proba proba = new Proba(tableCards[5], tableCards[6], getTableCards());
            System.out.println("Win probability (" + nb + " players): " + proba.compute(10000, nb));

        }


        updateNb++;
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

    public BufferedImage[] getCardPictures() {
        return cardPictures;
    }

    public void saveCards() {
        File[] outFiles = new File[7];
        for (int i = 0; i < cardPictures.length; i++) {
            outFiles[i] = new File(Properties.HOME + "card" + i + ".png");
        }

        File table = new File(Properties.HOME + "table" + updateNb + ".png");
        try {
            for (int i = 0; i < cardPictures.length; i++) {

                ImageIO.write(cardPictures[i], "png", outFiles[i]);
            }

            ImageIO.write(tablePicture, "png", table);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        frame.setSize(700, 700);

        frame.setLocation(1500, 0);
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
