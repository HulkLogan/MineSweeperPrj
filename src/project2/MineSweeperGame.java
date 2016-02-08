package project2;

import java.util.*;

public class MineSweeperGame {
	private Cell[][] board;
	private GameStatus status;
	
	/**holds the difficulty value as an int**/
	public int diffVal = 10;
	
	/**holds the amount of mines to be placed**/
	public int mineNum = 24;
	
   /**
	* holds the amount of covered, marked, mines (CMM), essential for
	* winning.
	*/
	private int CMM = 0;
	
	private int currentCell;
	

	public MineSweeperGame() {
		status = GameStatus.NotOverYet;
		board = new Cell[diffVal][diffVal];
		setEmpty();
		layMines (mineNum);
	}

	private void setEmpty() {
		for (int r = 0; r < diffVal; r++)
			for (int c = 0; c < diffVal; c++)
				board[r][c] = new Cell(10);  // totally clear.
	}
	
	public Cell getCell(int row, int col) {
		return board[row][col];
	}
	public void getNeighbors(int row, int col) {
		neighborMineCount = 0;
		neighbors = new Cell[8];
		neighbors[0] = board[row+1][col];
		neighbors[1] = board[row-1][col];
		neighbors[2] = board[row][col+1];
		neighbors[3] = board[row][col-1];
		neighbors[4] = board[row+1][col+1];
		neighbors[5] = board[row+1][col-1];
		neighbors[6] = board[row-1][col+1];
		neighbors[7] = board[row-1][col-1];
		for(int i=0; i<8; i++) {
			if(neighbors[i].isCoveredMine()){
				
				neighborMineCount++;
			}
		}
	}

	public void select(int row, int col) {
		board[row][col].setUncovered();

		if (board[row][col].isUncoveredMine())   // did I lose
			status = GameStatus.Lost;
		else {
			status = GameStatus.Won;    // did I win
			for (int r = 0; r < diffVal; r++)     // are only mines left
				for (int c = 0; c < diffVal; c++)
					if (!board[r][c].isUncovered()){
						if(board[r][c].isCoveredMarkedMine()){
							CMM += 1;
							if((CMM == mineNum)){
								//throw to win
							}
						}
						else{
							status = GameStatus.NotOverYet;
						}
					}	
		}
	}

	public GameStatus getGameStatus() {
		return status;
	}

	public void reset() {
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines (mineNum);
	}

	private void layMines(int mineCount) {
		int i = 0;		// ensure all mines are set in place

		Random random = new Random();
		while (i < mineCount) {			// perhaps the loop will never end :)
			int c = random.nextInt(diffVal);
			int r = random.nextInt(diffVal);

			if (!board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}
		}
	}

	public int getDiffVal() {
		return diffVal;
	}

	public void setDiffVal(int diffVal) {
		this.diffVal = diffVal;
	}

	public int getMineNum() {
		return mineNum;
	}

	public void setMineNum(int mineNum) {
		this.mineNum = mineNum;
	}
	
}
