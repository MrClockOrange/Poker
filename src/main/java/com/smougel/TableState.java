package com.smougel;

import com.github.axet.lookup.OCR;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by sylvainmougel on 23/03/15.
 */
public class TableState {
    private final Properties playersProp;
    private final OCR ocr;
    private final Player[] players;
    int numberOfPlayers;
    int subSizeX;
    int subSizeY;
    int remainingPlayers = 0;
    int dealerPosition = 0;

    States state = States.PREFLOP;
    int pot = 0;
    private BufferedImage[] playerImages;
    private BufferedImage currentTableImage;
    private States currentState;
    boolean newGame = false;
    private int firstPos;


    public TableState(Properties tableProp) throws IOException {
        playersProp = tableProp;
        numberOfPlayers = Integer.parseInt(playersProp.getProperty("players.nb"));
        subSizeX = Integer.parseInt(playersProp.getProperty("subImageSize.bet.X"));
        subSizeY = Integer.parseInt(playersProp.getProperty("subImageSize.bet.Y"));

        playerImages = new BufferedImage[numberOfPlayers];
        players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player();
        }
        ocr = new OCR(0.9f);
        ocr.loadFont(TableState.class, new File("fonts", "font_2"));


    }

    public void update(BufferedImage imageTable) {
        newGame = false;

        currentTableImage = imageTable;
        for (int i = 0; i < playerImages.length; i++) {
            boolean isDealer = false;

            // Extract the player images and update the bet
            String index = String.valueOf(i);
            playerImages[i] = imageTable.getSubimage(
                    Integer.valueOf(playersProp.getProperty("players." + index + ".bet.X")),
                    Integer.valueOf(playersProp.getProperty("players." + index + ".bet.Y")),
                    subSizeX,
                    subSizeY);
            String betStr = ocr.recognize(playerImages[i]);

            // find the dealer

            BufferedImage dealerImg = imageTable.getSubimage(
                    Integer.valueOf(playersProp.getProperty("players." + index + ".dealer.X")),
                    Integer.valueOf(playersProp.getProperty("players." + index + ".dealer.Y")),
                    subSizeX,
                    subSizeY);

            String dealer = ocr.recognize(dealerImg);
            if (dealer.equals("D")) {
                isDealer = true;
                if (i != dealerPosition) {
                    dealerPosition = i;
                    newGame = true;
                }
            }

            //Find player state
            int iRGB = imageTable.getRGB(
                    Integer.valueOf(playersProp.getProperty("players." + index + ".cards.X")),
                    Integer.valueOf(playersProp.getProperty("players." + index + ".cards.Y")));

            Color col = new Color(iRGB);
            //System.out.println("R : " + col.getRed() + " G : " + col.getGreen() + " B : " + col.getGreen());

            players[i].update(betStr, isDealer, col.getRed() > 80);
        }

        // Update pot
        BufferedImage potImg = imageTable.getSubimage(
                Integer.valueOf(playersProp.getProperty("pot.X")),
                Integer.valueOf(playersProp.getProperty("pot.Y")),
                subSizeX,
                subSizeY);
        String strPot = ocr.recognize(potImg);
        if (!strPot.isEmpty()) {
            pot = Integer.parseInt(strPot);
        } else {
            pot = 0;
        }
        firstPos = (dealerPosition + 1) % numberOfPlayers;

    }

    private int getMaxBetPos() {
        int max = 0;
        int pos = 0;
        for (int i = 0; i < players.length; i++) {
            if (players[i].getBet() > max) {
                max = players[i].getBet();
                pos = i;
            }
        }
        return pos;
    }


    public void dump() {
        System.out.println("Pot : " + pot);
        for (int i = firstPos; i < players.length; i++) {
            System.out.println("P" + i + " " + players[i].toString());

        }

        for (int i = 0; i < firstPos; i++) {
            System.out.println("P" + i + " " + players[i].toString());

        }

    }

    public void save() {
        File table = new File(com.smougel.Properties.HOME + "table.png");

        try {

            ImageIO.write(currentTableImage, "png", table);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] getBets() {
        int[] result = new int[numberOfPlayers];
        for (int i = 0; i < result.length; i++) {
            result[i] = players[i].getBet();
        }
        return result;
    }

    public int getPot() {
        return pot;
    }

    public int getDealerPosition() {
        return dealerPosition;
    }

    public boolean isNewGame() {
        return newGame;
    }

    public int getNumberOfPlayersIn() {
        int res = 0;
        for (Player p : players) {
            if (p.isIn()) {
                res++;
            }
        }
        return res;
    }
}
