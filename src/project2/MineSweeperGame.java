package project2;

import javax.swing.*;
import java.util.Random;

/**
 * Created by Jake Young on 2/11/2016.
 */
public class MineSweeperGame {
    private Cell[][] board;
    private GameStatus status;
    public static int diffValue;
    public static int mineNum;
    private Cell neighbors[];
    private Cell currentCell;

    String x = JOptionPane.showInputDialog(null, "Enter board size: ");
    String y = JOptionPane.showInputDialog(null, "Enter the amount of mines: ");

    public int getDiffValue() {
        //set up if statements on joptionpane use try catch
        diffValue = Integer.parseInt(x);
        return diffValue;
    }

    public int getMineNum() {
        //set up if statements
        mineNum = Integer.parseInt(y);
        return mineNum;
    }

    public MineSweeperGame() {
        getDiffValue();
        getMineNum();
        status = GameStatus.NotOverYet;
        board = new Cell[diffValue][diffValue];
        setEmpty();
        layMines (mineNum);
        getNeighbors();
    }

    private void getNeighbors() {
        for(int r = 0; r < diffValue; r++) {
            for (int c = 0; c < diffValue; c++) {
                if(!board[r][c].isCoveredMine()) {
                    for(int a = r-1; a <= r+1; a++) {
                        for (int b = c-1; b <= c+1; b++) {
                            if(isMine(a, b)) {
                                board[r][c].incrementCellValue();
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isMine(int r, int c) {
        if((r < 0 || r >= diffValue) || (c < 0 || c >= diffValue)) {
            return false;
        }
        if(board[r][c].isCoveredMine()) {
            return true;
        }
        return false;
    }

    private void setEmpty() {
        for (int r = 0; r < diffValue; r++) {
            for (int c = 0; c < diffValue; c++) {
                board[r][c] = new Cell(0);
                board[r][c].setCovered();
            }
        }
    }

    public Cell getCell(int row, int col) {
        return board[row][col];
    }

    public void select(int row, int col) {
        board[row][col].setUncovered();
        if (board[row][col].isUncoveredMine())   // did I lose
            status = GameStatus.Lost;
        else if (board[row][col].getCellValue() == 0)
            uncover(row, col);
        else {
            status = GameStatus.Won;
            for (int r = 0; r < diffValue; r++) {
                for (int c = 0; c < diffValue; c++) {
                    if (board[r][c].isCoveredMarkedMine()) { //checks for the win
                        status = GameStatus.Won;
                    }
                    else {
                        status = GameStatus.NotOverYet;
                    }
                }
            }

        }
    }

    public void flag(int row, int col) {
        board[row][col].setMarked();
            for (int r = 0; r < diffValue; r++) {
                for (int c = 0; c < diffValue; c++) {
                    if(board[r][c].isCoveredEmpty() && board[r][c].isMarked()) { //checks for the win
                        status = GameStatus.Won;
                    }
                    else {
                        status = GameStatus.NotOverYet;
                    }
                }
            }
        }

    public void uncover(int r, int c) {
        board[r][c].setCellValue(0);
        for(int a = r-1; a <= r+1; a++) {
            for(int b = c-1; b <= c+1; b++) {
                if(!(a < 0 || a > diffValue-1 || b < 0 || b > diffValue-1) && !((a==r) && (b==c))) {
                    uncoverCell(a, b);
                }
            }
        }
    }

    public void uncoverCell(int r, int c){
        /*if(board[r][c].isMarked()){
            return;
        }
        if(board[r][c].isUncovered()){
            return;
        }
        if(board[r][c].getCellValue() == 19){
            return;
        }*/
        if(board[r][c].getCellValue() == 10){
            uncover(r,c);
        }
        if(board[r][c].getCellValue() > 10 && board[r][c].getCellValue() < 19){
            board[r][c].setCellValue(board[r][c].getCellValue()-10);
        }
        else {

        }
    }


    public GameStatus getGameStatus() {
        return status;
    }

    public void reset() {
        getDiffValue();
        getMineNum();
        status = GameStatus.NotOverYet;
        setEmpty();
        layMines (mineNum);
        getNeighbors();
    }

    private void layMines(int mineCount) {
        int i = 0;		// ensure all mines are set in place

        Random random = new Random();
        while (i < mineCount) {			// perhaps the loop will never end :)
            int c = random.nextInt(diffValue);
            int r = random.nextInt(diffValue);

            if (!board[r][c].isCoveredMine()) {
                board[r][c].setMine();
                i++;
            }
        }
    }
}

