package project2;

/**
 * Created by Jake Young on 2/11/2016.
 */
import javax.swing.*;
import java.awt.*;

public class MineSweeper {
        public static void main (String[] args)
        {
            JFrame frame = new JFrame ("Mine Sweeper!");
            frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
            MineSweeperPanel panel = new MineSweeperPanel();
            frame.getContentPane().add(panel);
            frame.pack();
            //frame.setSize(550, 450);
            frame.setVisible(true);
        }


    }



