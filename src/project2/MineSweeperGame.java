package project2;

import javax.swing.*;
import java.util.Random;

/******************************************************************
 * The game uses four(4) variables, an array of Cell() type objects
 * that are the main components of the game, a GameStatus() object that
 * is constantly checked for Win, Lose, Keep Playing conditions,
 * an integer that hold the games difficulty value(i.e. how many board
 * spots to create), and another integer that holds the number of mines
 * to be placed.
 *
 * The game is won when all of the mines have been marked with a 'flag'
 * and no panel is left 'uncovered' except for flagged mines. Thus, the
 * game can be won with a final flag (right click) or a final
 * uncovering (left click).
 *
 * @author Logan Crowe, Jake Young
 * @version 1.0
 *****************************************************************/
public class MineSweeperGame {

    /**array of Cell() objects used as the main 'board' components*/
    private Cell[][] board;

    /**GameStatus() object that holds the current game status*/
    private GameStatus status;

    /**integer that holds the game's difficulty value*/
    public static int diffValue;

    /**integer that holds number of mines for the game*/
    public static int mineNum;

    /**String that holds the user input for the board size. Used to get
     * the difficulty value. */
    String x = JOptionPane.showInputDialog(null, "Enter board size: ");

    /**String that holds the number of mines the user wants placed*/
    String y = JOptionPane.showInputDialog(null, "Enter the amount of "
            + "mines: ");

    /*****************************************************************
     * Creates a MineSweeper Game() using user inputs for the
     * difficulty and the number of mines that are collected using
     * other methods.
     *****************************************************************/
    public MineSweeperGame() {
        getDiffValue();
        getMineNum();
        status = GameStatus.NotOverYet;
        board = new Cell[diffValue][diffValue];
        setEmpty();
        layMines (mineNum);
        getNeighbors();
    }

    /*****************************************************************
     * Collects the number of mine neighbors a non-mine Cell() has and
     * and adds that number to the cell's cellValue.
     *****************************************************************/
    private void getNeighbors() {
        for(int r = 0; r < diffValue; r++) {
            for (int c = 0; c < diffValue; c++) {
                if(!board[r][c].isCoveredMine()) {
                    for(int a = r-1; a <= r+1; a++) {
                        for (int b = c-1; b <= c+1; b++) {
                            if(!(a < 0 || a >= diffValue || b < 0
                                    || b >= diffValue)){
                                if(board[a][b].isCoveredMine()) {
                                    board[r][c].incrementCellValue();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

/* moved this function-ability into getNeighbors()
    private boolean isMine(int r, int c) {
        if((r < 0 || r >= diffValue) || (c < 0 || c >= diffValue)) {
            return false;
        }
        if(board[r][c].isCoveredMine()) {
            return true;
        }
        return false;
    }
*/
    /*****************************************************************
     * Creates a Cell() object to be placed the board with a starting
     * value of 0. The it 'covers' the cell giving it a cellValue of 10.
     * It repeats this for the entire board.
     *****************************************************************/
    private void setEmpty() {
        for (int r = 0; r < diffValue; r++) {
            for (int c = 0; c < diffValue; c++) {
                board[r][c] = new Cell(0);
                board[r][c].setCovered();
            }
        }
    }

    /*****************************************************************
     * The left click MouseEvent that does most of functions of the
     * game, most importantly, uncovering cells.
     * @param row - int that holds the selected row
     * @param col - int that holds the selected column
     *****************************************************************/
    public void select(int row, int col) {
        /**sets the current cell to 'uncovered'(-10 cellValue) and if
         * it was a mine, sets the GameStatus() to 'Lost'*/
        board[row][col].setUncovered();
        if (board[row][col].isUncoveredMine()){
            status = GameStatus.Lost;
        }
        /**for empty cells the uncover() recursive function is called*/
        else if (board[row][col].getCellValue() == 0){
            uncover(row, col);
        }
        else {
            status = GameStatus.Won;
            for (int r = 0; r < diffValue; r++) {
                for (int c = 0; c < diffValue; c++) {
                    if(board[r][c].isCoveredEmpty()) {
                        status = GameStatus.NotOverYet;
                    }
                }
            }
        }
    }

    /*****************************************************************
     * The right click MouseEvent that is used to 'flag' or 'mark'
     * a cell. This prevents a cell from being uncovered via the left
     * click event.
     * @param row - int that holds the selected row
     * @param col - int that holds the selected column
     *****************************************************************/
    public void flag(int row, int col) {
        board[row][col].setMarked();
        status = GameStatus.Won;
        for (int r = 0; r < diffValue; r++) {
            for (int c = 0; c < diffValue; c++) {
                if(board[r][c].isCoveredEmpty()) {
                    status = GameStatus.NotOverYet;
                }
            }
        }
    }

    /*****************************************************************
     * Uncovers (cellValue -10) the current Cell() object then runs
     * another method on any valid cells around it that calls uncover()
     * recursively.
     * @param r - int that holds the current row
     * @param c - int that holds the current column
     *****************************************************************/
    public void uncover(int r, int c) {
        board[r][c].setCellValue(0);
        for(int a = r-1; a <= r+1; a++) {
            for(int b = c-1; b <= c+1; b++) {
                if(!(a < 0 || a > diffValue-1 || b < 0
                        || b > diffValue-1) && !((a==r) && (b==c))) {
                    uncoverCell(a, b);
                }
            }
        }
    }

    /*****************************************************************
     * Used to check cells around an 'uncovered' Cell(). If any are
     * empty and without mine neighbors, uncover() is called. If
     * the cell has mine neighbors (10 < cellValue < 19) then ten(10)
     * is subtracted from its cellValue but uncover() is not called
     * stopping the recursion at blocks with mine neighbors.
     * @param r - int that holds the current row
     * @param c - int that holds the current column
     *****************************************************************/
    public void uncoverCell(int r, int c){
        if(board[r][c].getCellValue() == 10){
            uncover(r,c);
        }
        if(board[r][c].getCellValue() > 10
                && board[r][c].getCellValue() < 19){
            board[r][c].setCellValue(board[r][c].getCellValue()-10);
        }
        else {
            return;
        }
    }

    /*****************************************************************
     * Used to lay mines at the beggining of a new game.
     * @param mineCount - int that determines the number of mines to
     * be 'layed'
     *****************************************************************/
    private void layMines(int mineCount) {
        int i = 0;
        Random random = new Random();
        while (i < mineCount) {
            int c = random.nextInt(diffValue);
            int r = random.nextInt(diffValue);
            if (!board[r][c].isCoveredMine()) {
                board[r][c].setMine();
                i++;
            }
        }
    }

    /*****************************************************************
     * Reset button. Makes a new game.
     *****************************************************************/
    public void reset() {
        getDiffValue();
        getMineNum();
        status = GameStatus.NotOverYet;
        setEmpty();
        layMines (mineNum);
        getNeighbors();
    }

    /*****************************************************************
     * Runs through board and 'flags' all mines. Used for auto-win.
     *****************************************************************/
    public void flagAllMines(){
        for (int r = 0; r < diffValue; r++) {
            for (int c = 0; c < diffValue; c++) {
                if(board[r][c].isCoveredMine()){
                    flag(r,c);
                }
            }
        }
    }

    /*****************************************************************
     * Returns the current game status.
     * @return status - GameStatus representing the current game status
     *****************************************************************/
    public GameStatus getGameStatus() {
        return status;
    }

    /*****************************************************************
     * Parses the difficulty value from the user input and returns it
     * as an integer.
     * @return diffValue - int that represents the difficulty value
     *****************************************************************/
    public int getDiffValue() {
        try{
            diffValue = Integer.parseInt(x);
        }
        catch(NumberFormatException e){
            diffValue = 10;
        }
        return diffValue;
    }

    /*****************************************************************
     * Parses the number of mines to be placed from the user input and
     * returns it as an integer.
     * @return mineNum - int that represents the number of mines
     *****************************************************************/
    public int getMineNum() {
        try{
            mineNum = Integer.parseInt(y);
        }
        catch(NumberFormatException e){
            mineNum = 24;
            if(diffValue <= 5){
                mineNum = mineNum / 2;
            }
            if(diffValue >= 20){
                mineNum = mineNum * 2;
            }
        }
        return mineNum;
    }

    /*****************************************************************
     * Returns a cell from a specified point on the board, using an
     * integer for the row and column desired.
     * @param row - int with the desired row
     * @param col - int with the desired column
     * @return this.Cell() - the specified Cell() object
     *****************************************************************/
    public Cell getCell(int row, int col) {
        return board[row][col];
    }


}
