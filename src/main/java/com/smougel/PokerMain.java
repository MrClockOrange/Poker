package com.smougel;

import com.smougel.utils.Position;

import java.awt.*;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by sylvainmougel on 11/01/16.
 */
public class PokerMain {


    public static void main(String[] args) throws IOException, AWTException {
        Properties tableProp = new java.util.Properties();
        //Read the properties and launch one thread per table
        tableProp.load(ScreenScrapper.class.getResourceAsStream("/tables.properties"));
        for (int i = 0; i < Integer.valueOf(tableProp.getProperty("windows.nb")); i++) {
            Position windowPos = new Position(
                    Integer.valueOf(tableProp.getProperty("windows." + i + ".ori.X")),
                    Integer.valueOf(tableProp.getProperty("windows." + i + ".ori.Y"))
            );
            ScreenScrapper sc = new ScreenScrapper(windowPos, i);
            System.out.println("Starting table " + i);
            sc.start();
        }
    }
}
