package project2;

public class Cell {
    /******************************************************************
     * A cell() is used to donate the blocks or positions within the
     * game. It has only one main variable, a value which ranges from 0
     * to 30. 0 represents an 'empty' and 'uncovered' block with no mine
     * neighbors, 1-8 represent the same but with mine neighbors
     * represented by the value of the cell. 9 is an uncovered mine
     * which should result in a loss for the player. 'Covering' a block
     * adds 10 to the value (ex. 9[mine]+10[cover]=19[covered mine]).
     * 'Marking' a block with a flag also adds 10
     * (ex. 19[covered mine] + 10[mark] = 29[marked covered mine]).
     * 
     * @author Logan Crowe, Jake Young
     *****************************************************************/
    /**int that holds the cell value**/
    private int cellValue = 0;

    /******************************************************************
     * Creates a Cell with one parameter, an integer
     * @param value - int that sets the cell value
     ******************************************************************/
    public Cell(int value) {
            cellValue = value;
        }

     /******************************************************************
      * Returns the cell value as an integer
      * @return cellValue - int that holds the cell value
      * ******************************************************************/
     public int getCellValue() {
            return cellValue;
        }

    /******************************************************************
     * Returns whether or not a Cell is 'uncovered'
     * @return true - if value is less than 10
     * @return false - if value is greater than or equal to 10
     ******************************************************************/
    public boolean isUncovered() {
        if(cellValue < 9){
            return true;
        }
        return false;
    }

    /******************************************************************
     * Returns whether or not a Cell is 'covered' and empty of mines
     * @return true - if value is 10
     *******************************************************************/
    public boolean isCoveredEmpty() {
        if(cellValue > 9 && cellValue < 19){
            return true;
        }
        return false;
    }

    /******************************************************************
     * Returns whether or not a Cell is 'marked'
     * @return true - if value is greater than 19
     * @return false - if value is less than or equal to 19
     ******************************************************************/
    public boolean isMarkedEmpty() {
        if(cellValue == 20){
            return true;
        }
        return false;
    }

    /******************************************************************
     * Returns whether a Cell is an uncovered mine, losing the game.
     * @return true - if cell value is 9
     ******************************************************************/
    public boolean isUncoveredMine() {
        if(this.cellValue == 9){
            return true;
        }
        return false;
    }

    public boolean isCoveredMine() {
        if(cellValue == 19) {
            return true;
        }
        return false;
    }
    /*****************************************************************
     * Returns whether a Cell is a covered, marked mine. Used to
     * determine the winning situation.
     * @return true - if Cell is a mine that is covered and marked
     *****************************************************************/
    public boolean isCoveredMarkedMine() {
        if(cellValue == 29){
            return true;
        }
        return false;
    }

    /******************************************************************
     * Sets the value of the Cell to the provide integer.
     * @param cellValue - int that provides new cell value
     ******************************************************************/
    public void setCellValue(int cellValue) {
            this.cellValue = cellValue;
        }

    /******************************************************************
     * Sets a Cell to uncovered. Can only be called if a cell is
     * covered and not marked.
     ******************************************************************/
    public void setUncovered() {
        if(isMarkedEmpty() || isCoveredMarkedMine() || isUncovered()) {

        }
        if(!isUncovered()){
            cellValue -= 10;
        }
    }

    /******************************************************************
     * Sets a Cell to uncovered.
     ******************************************************************/
    public void setCovered() {
        cellValue += 10;
    }

    /******************************************************************
     * Sets a Cell to marked if unmarked and vice versa. Can only be
     * called if a Cell is covered
     *****************************************************************/
    public void setMarked() {
        if(cellValue > 19) {
            unMark();
        }
        if(isUncovered()) {

        }
        else {
            cellValue += 10;
        }
    }

    public void unMark() {
        cellValue -= 10;
    }

    /******************************************************************
     * Adds a mine to a Cell. Can only be called if a cell is covered
     * but empty.
     ******************************************************************/
    public void setMine() {
        if(this.cellValue == 10){
            cellValue += 9;
        }
    }

    public void incrementCellValue() {
        cellValue++;
    }

    }