package com.smougel.context;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by sylvainmougel on 15/03/15.
 */
public class Table {

    private BufferedImage tablePicture;
    private final HandStateComputer handStateComputer;
    private PlayerStateComputer tableStateComputer;
    private int updateNb;
    private final Properties tableProperties;
    private IPlayersState playersState;
    private IHandState handState;
    private final List<IPlayersState> playersStateHistory;


    public Table(Properties properties) throws IOException {
        tableProperties = properties;
        tableStateComputer = new PlayerStateComputer(tableProperties);
        handStateComputer = new HandStateComputer(tableProperties);
        updateNb = 0;
        playersStateHistory = new ArrayList<>();
    }


    public void update(BufferedImage tableScreenShot) throws IOException {

        tablePicture = tableScreenShot;

        // Update the state from the new image
        playersState = tableStateComputer.compute(tablePicture);
        handState = handStateComputer.compute(tablePicture);

        // Save the table for debug purposes
        saveTable();

        if (tableStateComputer.isNewGame()) {
            System.out.println("====== NEW GAME =====");
            playersStateHistory.clear();

        }
        playersStateHistory.add(playersState);
        System.out.println("======" + handState.retrieveState() + "=====");
        tableStateComputer.dump();
        System.out.println("======" + "=====");
        handStateComputer.dump();
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

    public IPlayersState getPlayersState() {
        return playersState;
    }

    public IHandState getHandState() {
        return handState;
    }

    public List<IPlayersState> getPlayersStateHistory() {
        return playersStateHistory;
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
