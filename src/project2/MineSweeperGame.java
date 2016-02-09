package project2;

import java.awt.Panel;
import java.util.Random;

public class MineSweeperGame {
	private Cell[][] board;
	private GameStatus status;
	
	//holds the difficulty value as an int**/
//	public int diffValue;
	public int mineCount;
	//public int mineNum;
	public int neighborMineCount = 0;
	//holds the amount of covered, marked, mines (CMM)
	private int CMM = 0;
	private Cell currentCell;
	private Cell[] neighbors;
	
	public int getDiffVal() {
		int diffValue = MineSweeperPanel.getDiffValue();
		return diffValue;
	}

	public int getMineNum() {
		int mineNum = MineSweeperPanel.getMineNum();
		return mineNum;
	}

	public MineSweeperGame() {
		status = GameStatus.NotOverYet;
		board = new Cell[getDiffVal()][getDiffVal()];
		setEmpty();
		layMines(getMineNum());
	}
	
	private void setEmpty() {
		for (int r = 0; r < getDiffVal(); r++) {
			for(int c=0; c < getDiffVal(); c++) {
				board[r][c] = new Cell(getDiffVal()); //totally clean.
			}
		}

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
	public int getNeighborMineCount() {
		return neighborMineCount;
	}
	public void select(int row, int col) {
		if(board[row][col].isUncovered() || board[row][col].isFlagged()) {
			
		}
		else {
		board[row][col].setUncovered();
		
		if(board[row][col].isUncoveredMine()) {
			status = GameStatus.Lost;
		}
		else {
			status = GameStatus.Won;
			for (int r = 0; r < getDiffVal(); r++) {
				for (int c = 0; c < getDiffVal(); c++) {
					if(board[r][c].isCovered() && board[r][c].isFlaggedMine()) {
						if(CMM == getMineNum()) {
							status = GameStatus.Won;
						}
					}
						else {
							status = GameStatus.NotOverYet;
						}
					}
				}
			}
			}
		}
	public void flag(int row, int col) {
		if(board[row][col].isFlagged()) {
			board[row][col].unFlag();
			CMM--;
		}
		else {
		board[row][col].setFlagged();
		CMM++;
		for (int r = 0; r < getDiffVal(); r++) {
			for (int c = 0; c < getDiffVal(); c++) {
				if(board[r][c].isCovered() && board[r][c].isFlaggedMine()) {
					//if(CMM == mineNum) {
						status = GameStatus.Won;
					//}
				}
				else {
					status = GameStatus.NotOverYet;
				}
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
		layMines(getMineNum());
	}
	public void layMines(int mineNum) {
		int i = 0;		// ensure all mines are set in place

		Random random = new Random();
		while (i < mineNum) {			// perhaps the loop will never end :)
			int c = random.nextInt(getDiffVal());
			int r = random.nextInt(getDiffVal());

			if (board[r][c].getCellValue() == 10) {
				board[r][c].setMine();
				i++;
			}
		}
	}
}



