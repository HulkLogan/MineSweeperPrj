package project2;

import javax.swing.*;
import java.awt.*;

public class MineSweeper {
	public static void main (String[] args) {
		JFrame frame = new JFrame ("Mine Sweeper");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        MineSweeperPanel panel = new MineSweeperPanel();
        JLabel label = new JLabel("Mine Sweeper");
        label.setHorizontalAlignment(JLabel.CENTER);
        frame.add(label, BorderLayout.NORTH);
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }
}