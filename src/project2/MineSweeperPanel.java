package project2;

import javax.swing.*;
import java.util.Scanner;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineSweeperPanel extends JPanel {

	private JButton[][] board;
	private JButton butQuit;
	private Cell iCell;
	public int mineCount;
	
	static String x = JOptionPane.showInputDialog (null, "Enter the size of the board: ");
	static int diffValue = Integer.parseInt(x);
	
	
	static String y = JOptionPane.showInputDialog (null, "Enter number of mines: ");
	static int mineNum = Integer.parseInt(y);
	
	private MineSweeperGame game;  // model

	ImageIcon image = new ImageIcon("flag.jpeg");
	ImageIcon image2 = new ImageIcon("bomb.jpg");

	public MineSweeperPanel() {

		JPanel bottom = new JPanel();
		JPanel center = new JPanel();


		game = new MineSweeperGame();

		// create the board
		center.setLayout(new GridLayout(diffValue, diffValue));
		board = new JButton[diffValue][diffValue];

		for (int row = 0; row < diffValue; row++)
			for (int col = 0; col < diffValue; col++) {
				board[row][col] = new JButton("");
				board[row][col].addMouseListener(listener);
				center.add(board[row][col]);
			}

		displayBoard();

		bottom.setLayout(new GridLayout(3, 2));

		// add all to contentPane
		add(new JLabel("Mine Sweeper"), BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}
	
	public static int getDiffValue() {
		return diffValue;
	}
	
	public static int getMineNum() {
		return mineNum;
	}

	private void displayBoard() {

		for (int r = 0; r < diffValue; r++)
			for (int c = 0; c < diffValue; c++) {
				iCell = game.getCell(r, c);
				board[r][c].setText("");

				// readable, ifs are verbose
				if(iCell.isFlagged() || iCell.isFlaggedMine()) {
					board[r][c].setText("*");
				}
				if (iCell.isCoveredMine()) {
					//board[r][c].setIcon(image2);
					board[r][c].setText("!");
				}
				if (iCell.isUncovered()) {
					board[r][c].setEnabled(false);
//					String output = Integer.toString(iCell.getCellValue());
//					board[r][c].setText(output);
			//changes with each click?
					String output = Integer.toString(game.getNeighborMineCount());
					board[r][c].setText(output);
				}
				else
					board[r][c].setEnabled(true);
			}
	}


	MouseListener listener = new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			for (int row = 0; row < diffValue; row++) {
				for (int col = 0; col < diffValue; col++) {
					if(e.getSource() == board[row][col]) {
					if(e.getButton() == MouseEvent.BUTTON1) {
						game.select(row, col);
						}
					if(e.getButton() == MouseEvent.BUTTON3) {
						game.flag(row, col);
						//board[row][col].setText("*");
					}
					}
					
				}
			}
			displayBoard();
	
	if (game.getGameStatus() == GameStatus.Lost) {
		displayBoard();
		JOptionPane.showMessageDialog(null, "You Lose \n The game will reset");
		//exposeMines = false;
		game.reset();
		displayBoard();

	}

	if (game.getGameStatus() == GameStatus.Won) {
		JOptionPane.showMessageDialog(null, "You Win: all mines have been found!\n The game will reset");
		game.reset();
		displayBoard();
	}
		}
	};
}