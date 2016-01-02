package com.smougel.context;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by sylvainmougel on 15/03/15.
 */
public class Table {

    private BufferedImage tablePicture;
    private final HandStateComputer handStateComputer;
    private TableStateComputer tableStateComputer;
    private int updateNb;
    private final Properties tableProperties;



    public Table(Properties properties) throws IOException {
        tableProperties = properties;
        tableStateComputer = new TableStateComputer(tableProperties);
        handStateComputer = new HandStateComputer(tableProperties);
        updateNb = 0;
    }


    public void update(BufferedImage tableScreenShot) throws IOException {

        tablePicture = tableScreenShot;

        // Update the state from the new image
        ITableState tableState = tableStateComputer.compute(tablePicture);
        IHandState handState = handStateComputer.compute(tablePicture);

        // Save the table for debug purposes
        saveTable();

        if (tableStateComputer.isNewGame()) {
            System.out.println("====== NEW GAME =====");

        }
        System.out.println("======" + handState.retrieveState() + "=====");
        tableStateComputer.dump();
        System.out.println("======" + "=====");
        handStateComputer.dump();

        int nb = tableState.getRemainingNbOPlayers();
        float winProba = handState.getWinProba(nb);
        int stake = tableState.getTotalMoneyAtStake();

        System.out.println("Win probability (" + nb + " players): " + winProba);
        System.out.println(stake + "$ at stake" );
        System.out.println("Call threshold : " + winProba * stake);

        updateNb++;
    }




    public void saveTable() {

        File table = new File(tableProperties.getProperty("home") + "table" + updateNb + ".png");
        try {

            ImageIO.write(tablePicture, "png", table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
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
    */

}
