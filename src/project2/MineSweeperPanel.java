package project2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class MineSweeperPanel extends JPanel {

	//add amount of wins and loses on the board.
    //add Mine Sweeper JLabel at the top
    //add icons to the jpanel
    //change the size of the jbuttons on board

    private JButton[][] board;
    public JButton resetButton;
    public JButton quitButton;
    private Cell iCell;
    private int winCount;
    private int lossCount;
    private ImageIcon newIcon;
    private ImageIcon newFlagIcon;
    private JLabel winCounter;
    private JLabel lossCounter;

    private MineSweeperGame game;  // model

    public MineSweeperPanel() {

        JPanel bottom = new JPanel();
        JPanel center = new JPanel();
        //creates reset and quit button
        resetButton = new JButton("Reset");
        quitButton = new JButton("Quit");

        //fixes the size of the mine icon
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/mine.png"));
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(25, 25, 0);
        newIcon = new ImageIcon(newImg);

        //fixes the size of the flag
        //ImageIcon icon1 = new ImageIcon(getClass().getResource("/images/flag.png"));
        //Image img1 = icon1.getImage();
        //Image newImg1 = img1.getScaledInstance(25, 25, 0);
        //newFlagIcon = new ImageIcon(newImg1);

        game = new MineSweeperGame();

        // create the board
        center.setLayout(new GridLayout(game.getDiffValue(), game.getDiffValue()));
        board = new JButton[game.getDiffValue()][game.getDiffValue()];

        for (int row = 0; row < game.getDiffValue(); row++)
            for (int col = 0; col < game.getDiffValue(); col++) {
                board[row][col] = new JButton("");
                //board[row][col].setBorder(BorderFactory.createEmptyBorder());
                board[row][col].setPreferredSize(new Dimension(50 ,50));
                board[row][col].addMouseListener(listener);
                resetButton.addMouseListener(listener);
                quitButton.addMouseListener(listener);
                center.add(board[row][col]);
            }

        displayBoard();

        bottom.setLayout(new GridLayout(10, 2));
        winCounter = new JLabel("Number of wins:" + 0);
        lossCounter= new JLabel("Number of loses:" + 0);

        // add to bottom
        bottom.add(winCounter, BorderLayout.NORTH);
        bottom.add(lossCounter, BorderLayout.SOUTH);
        bottom.add(resetButton, BorderLayout.NORTH);
        bottom.add(quitButton, BorderLayout.SOUTH);
        // add to frame
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    //used to show mines after loss
    private void displayAllMines() {
        for (int r = 0; r < game.getDiffValue(); r++) {
            for (int c = 0; c < game.getDiffValue(); c++) {
                iCell = game.getCell(r, c);
                if(iCell.isCoveredMine()) {
                    board[r][c].setEnabled(false);
                    board[r][c].setIcon(newIcon);
                }
            }
        }
    }

    private int getWinCount() {
        return winCount;
    }

    private int getLossCount() {
        return lossCount;
    }

    private void displayBoard() {
        for (int r = 0; r < game.getDiffValue(); r++)
            for (int c = 0; c < game.getDiffValue(); c++) {
                iCell = game.getCell(r, c);
                String output = Integer.toString(iCell.getCellValue());
                board[r][c].setText("  ");
                //board[r][c].setText(output); for debugging purposes

                // sets symbols on board
                if (iCell.isMarkedEmpty() || iCell.isCoveredMarkedMine()) {
                    //board[r][c].setIcon(newFlagIcon); icon not showing up for some reason
                    board[r][c].setText("*");
                }
                if(board[r][c].isEnabled()) {
                    board[r][c].setIcon(null);
                }
                if (iCell.isCoveredMine()) {
                    board[r][c].setText("!");
                }
                if (iCell.isUncovered()) {
                    board[r][c].setEnabled(false);
                    board[r][c].setText(output);
                } else
                    board[r][c].setEnabled(true);
            }
        }

    MouseListener listener = new MouseListener() {

        @Override
        public void mouseReleased(MouseEvent e) {
            for (int row = 0; row < game.getDiffValue(); row++) {
                for (int col = 0; col < game.getDiffValue(); col++) {
                    if (e.getSource() == board[row][col]) {
                        if (e.getButton() == MouseEvent.BUTTON1) {
                            game.select(row, col);
                            displayBoard();
                        }
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            game.flag(row, col);
                            displayBoard();
                        }
                    }
                }
            }
            if(e.getSource() == resetButton) {
                game.reset();
                displayBoard();
            }
            if(e.getSource() == quitButton) {
                System.exit(0);
            }
            displayBoard();

            if (game.getGameStatus() == GameStatus.Lost) {
                lossCount++;
                lossCounter.setText("Number of loses:" + getLossCount());
                displayAllMines();
                JOptionPane.showMessageDialog(null, "You Lose \n The game will reset");
                displayBoard();
                game.reset();
                displayBoard();

            }

            if (game.getGameStatus() == GameStatus.Won) {
                winCount++;
                winCounter.setText("Number of wins:" + getWinCount());
                game.flagAllMines();
                displayBoard();
                JOptionPane.showMessageDialog(null, "You Win: all mines have been flagged!\n The game will reset");
                game.reset();
                displayBoard();
            }
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
        }
    };


}
