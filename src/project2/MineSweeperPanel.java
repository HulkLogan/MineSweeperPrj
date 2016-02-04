package project2;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MineSweeperPanel extends JPanel {

	private JButton[][] board;
	private JButton butQuit;
	private Cell iCell;
	
	
	private MineSweeperGame game;  // model


	public MineSweeperPanel() {

		JPanel bottom = new JPanel();
		JPanel center = new JPanel();

		// create game, listeners
		ButtonListener listener = new ButtonListener();

		game = new MineSweeperGame();

		// create the board
		center.setLayout(new GridLayout(game.getDiffVal(), 
						game.getDiffVal()));
		board = new JButton[game.getDiffVal()][game.getDiffVal()];

		for (int row = 0; row < game.getDiffVal(); row++)
			for (int col = 0; col < game.getDiffVal(); col++) {
				board[row][col] = new JButton("");
				board[row][col].addActionListener(listener);
				center.add(board[row][col]);
			}

		displayBoard();

		bottom.setLayout(new GridLayout(3, 2));

		// add all to contentPane
		add(new JLabel("Mine Sweeper"), BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);

	}


	private void displayBoard() {

		for (int r = 0; r < game.getDiffVal(); r++)
			for (int c = 0; c < game.getDiffVal(); c++) {
				iCell = game.getCell(r, c);

				board[r][c].setText("");

				// readable, ifs are verbose
					
				if (iCell.getCellValue() == 19) {
					board[r][c].setText("!");
				}
				if (iCell.isExposed()) {
					board[r][c].setEnabled(false);
					String output = 
							Integer.toString(iCell.getCellValue());
					board[r][c].setText(output);
				}
				else
					board[r][c].setEnabled(true);
			}
	}


	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			for (int r = 0; r < game.getDiffVal(); r++)
				for (int c = 0; c < game.getDiffVal(); c++)
					if (board[r][c] == e.getSource())
						game.select(r, c);

			displayBoard();
								
			if (game.getGameStatus() == GameStatus.Lost) {
				displayBoard();
				JOptionPane.showMessageDialog(null, "You Lose \n The "
						+ "game will reset");
				//exposeMines = false;
				game.reset();
				displayBoard();

			}

			if (game.getGameStatus() == GameStatus.Won) {
				JOptionPane.showMessageDialog(null, "You Win: all mines"
						+ " have been found!\n The game will reset");
				game.reset();
				displayBoard();
			}

		}

	}

}