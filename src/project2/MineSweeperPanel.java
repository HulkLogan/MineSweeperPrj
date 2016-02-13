package project2;

/**
 /**
 * Created by Jake Young on 2/11/2016.
 */
    import javax.swing.*;
    import java.awt.*;
    import java.awt.event.*;

public class MineSweeperPanel extends JPanel {

    private JButton[][] board;
    private JButton butQuit;
    private Cell iCell;

    private MineSweeperGame game;  // model

    public MineSweeperPanel() {

        JPanel bottom = new JPanel();
        JPanel center = new JPanel();

        game = new MineSweeperGame();

        // create the board
        center.setLayout(new GridLayout(game.getDiffValue(), game.getDiffValue()));
        board = new JButton[game.getDiffValue()][game.getDiffValue()];

        for (int row = 0; row < game.getDiffValue(); row++)
            for (int col = 0; col < game.getDiffValue(); col++) {
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



    private void displayBoard() {
        for (int r = 0; r < game.getDiffValue(); r++)
            for (int c = 0; c < game.getDiffValue(); c++) {
                iCell = game.getCell(r, c);
                String output = Integer.toString(iCell.getCellValue());
                board[r][c].setText("");

                // sets symbols on board
                if (iCell.isMarked()) {
                    //board[r][c].setIcon(flags);
                    board[r][c].setText("*");
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
            for (int row = 0; row < game.getDiffValue(); row++) {
                for (int col = 0; col < game.getDiffValue(); col++) {
                    if(e.getSource() == board[row][col]) {
                        if(e.getButton() == MouseEvent.BUTTON1) {
                            game.select(row, col);
                            displayBoard();
                        }
                        if(e.getButton() == MouseEvent.BUTTON3) {
                            game.flag(row, col);
                            displayBoard();
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
                JOptionPane.showMessageDialog(null, "You Win: all mines have been flagged!\n The game will reset");
                game.reset();
                displayBoard();
            }
        }
    };


}