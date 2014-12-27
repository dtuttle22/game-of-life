package gameoflife;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private static final int ALIVE = 1;
	private static final int DEAD = 0;
	
	private final int[][] board;
	private int numRows;
	private int numCols;
	
	public Game(int[][] board){
		this.board = board;
		this.numRows = board.length;
		this.numCols = board[0].length;
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	public Game nextGeneration(){
		int[][] newBoard = new int[numRows][numCols];
		for (int i = 0; i < numRows; i++){
			for (int j=0; j < numCols; j++){
				newBoard[i][j] = getCellValue(i, j);
			}
		}
		return new Game(newBoard);
	}
	
	private int getCellValue(int row, int col){		
		long neighboursAlive = getNeighbours(row, col).stream().filter(x -> x ==1).count();
		int cellValue = board[row][col];
		
		if (isAlive(cellValue) && neighboursAlive < 2){
			return DEAD;
		} else if (isAlive(cellValue) && (neighboursAlive == 2 || neighboursAlive == 3 )){
			return ALIVE;
		} else if (isAlive(cellValue) && neighboursAlive > 3 ){
			return DEAD; 
		} else if (!isAlive(cellValue) && neighboursAlive == 3){
			return ALIVE;
		}
		return DEAD;
	}
	
	private boolean isAlive(int cellValue){
		if (cellValue == ALIVE){
			return true;
		} else {
			return false;
		}
	}
	
	private List<Integer> getNeighbours(int row, int col){
		List<Integer> neighbourList = new ArrayList<>(8);

		addNeighbour(getUpLeftNeighbour(row, col), neighbourList);
		addNeighbour(getUpNeighbour(row, col), neighbourList);
		addNeighbour(getUpRightNeighbour(row, col), neighbourList);
		addNeighbour(getLeftNeighbour(row, col), neighbourList);
		addNeighbour(getRightNeighbour(row, col), neighbourList);
		addNeighbour(getDownLeftNeighbour(row, col), neighbourList);
		addNeighbour(getDownNeighbour(row, col), neighbourList);
		addNeighbour(getDownRightNeighbour(row, col), neighbourList);
		
		return neighbourList;
 	}
	
	private void addNeighbour(Integer neighbourVal, List<Integer> neighbourList){
		if (neighbourVal != null){
			neighbourList.add(neighbourVal);
		}
	}
	
	private Integer getUpLeftNeighbour(int row,int col){
		return getNeighbour(row > 0 && col > 0,	up(row), left(col));
	}
	
	private Integer getUpNeighbour(int row, int col){
		return getNeighbour(row > 0, up(row), col);
	}
	
	private Integer getUpRightNeighbour(int row, int col){
		return getNeighbour(row > 0 && col < numCols - 1, 
				up(row), right(col));
	}
	
	private Integer getLeftNeighbour(int row, int col){
		return getNeighbour(col > 0, row, left(col));
	}
	
	private Integer getRightNeighbour(int row, int col){
		return getNeighbour(col < numCols - 1, row, right(col));
	}
	
	private Integer getDownLeftNeighbour(int row, int col){
		return getNeighbour(row < numRows - 1 && col > 0, down(row), left(col));
	}
	
	private Integer getDownNeighbour(int row, int col){
		return getNeighbour(row < numRows - 1, down(row), col);
	}
	
	private Integer getDownRightNeighbour(int row, int col){
		return getNeighbour(row < numRows - 1 && col < numCols - 1, 
				down(row), right(col));
	}
	
	private Integer getNeighbour(boolean test, int rowIndex, int colIndex){
		return test ? board[rowIndex][colIndex] : null;
	}
	
	private static int up(int row){
		return row - 1;
	}
	
	private static int down(int row){
		return row + 1;
	}
	
	private static int left(int col){
		return col - 1;
	}
	
	private static int right(int col){
		return col + 1;
	}
	
}
