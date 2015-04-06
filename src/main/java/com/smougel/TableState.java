package com.smougel;

import com.github.axet.lookup.OCR;

import javax.imageio.ImageIO;
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
    States state = States.PREFLOP;
    int pot = 0;
    private BufferedImage[] playerImages;


    public TableState() throws IOException {
        playersProp = new Properties();
        playersProp.load(new FileInputStream("table.properties"));
        numberOfPlayers = Integer.parseInt(playersProp.getProperty("players.nb"));
        subSizeX = Integer.parseInt(playersProp.getProperty("subImage.X"));
        subSizeY = Integer.parseInt(playersProp.getProperty("subImage.Y"));
        playerImages = new BufferedImage[numberOfPlayers];
        players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            players[i] = new Player();
        }
        ocr = new OCR(0.9f);
        ocr.loadFont(TableState.class, new File("fonts", "font_2"));


    }

    public void update(BufferedImage imageTable) {

        for (int i = 0; i < playerImages.length; i++) {

            // Extract the player images
            String index = String.valueOf(i);
            playerImages[i] = imageTable.getSubimage(
                    Integer.valueOf(playersProp.getProperty("players." + index + ".X")),
                    Integer.valueOf(playersProp.getProperty("players." + index + ".Y")), subSizeX, subSizeY);


            String str = ocr.recognize(playerImages[i]);
            players[i].update(str);
        }
        BufferedImage potImg = imageTable.getSubimage(
                Integer.valueOf(playersProp.getProperty("pot.X")),
                Integer.valueOf(playersProp.getProperty("pot.Y")),
                subSizeX,
                subSizeY);
        String strPot = ocr.recognize(potImg);
        if(!strPot.isEmpty()) {
            pot = Integer.parseInt(strPot);
        } else {
            pot = 0;
        }

    }




    public void dump() {
        System.out.println("Pot : " + pot);
        for (Player p : players) {
            System.out.println(p.toString());
        }
    }

    public void save() {
        File table = new File(com.smougel.Properties.HOME + "player.png");

        try {

            ImageIO.write(playerImages[4], "png", table);

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
}
