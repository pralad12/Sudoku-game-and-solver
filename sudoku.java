
import java.util.*;
import java.lang.*; 

public class sudoku 
{ 
	int[][] board;
	int num_to_remove; //How many values to get rid of
	int[][] filled; //full board

	sudoku() {
		//instantiate the board
		board = new int[9][9];
		filled = new int[9][9];


	} 

	//fill the whole board with numbers
	public void fillBoard() 
	{ 
		// Fill first 3 diagonal squares
		setDiagonalSquares(); 
		// Fill remaining blocks 
		setBoard(0, 3); 
	}
	
	//switch the columns and rows
	public void fixColRow() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int temp = board[i][j];
				board[i][j] = board[j][i];
				board[j][i] = temp;
				int temp2 = filled[i][j];
				filled[i][j] = filled[j][i];
				filled[j][i] = temp2;
			}
		}
	}

	//checks to see if game is complete. Returns true if none of the squares
	//equal zero
	public boolean isComplete(int[][] a, int[][] b) {
		boolean trueFalse = true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (a[i][j] == 0) {
					trueFalse = false;
				} else if (a[i][j] != b[i][j]) {
					trueFalse = false;
				}
			}

		}
		return trueFalse;
	}

	//remove a certain number of elements from the board to make a playable game
	public void removeNum(int num_to_remove) { 
		int max = num_to_remove; 
		int ct = 0;
		while (ct < max) { 
			int specificCell = randomNumber(80);
			int i =(int) Math.floor(specificCell/9); 
			int j = (int) Math.floor(specificCell%9);
			//keep going if no number is removed
			if (board[i][j] == 0) continue;
			board[i][j] = 0;
			filled[i][j] = 0;

			ct++;}

	} 	

	// first set the 3 diagonal squares
	void setDiagonalSquares(){ 
		for (int i = 0; i<9; i=i+3) 
			// call method to set 3 by 3 squares
			setSmallSquares(i, i); 
	} 

	// method to fill the squares
	void setSmallSquares(int row,int col) 
	{ 
		int num; 
		for (int i=0; i<3; i++) { 
			for (int j=0; j<3; j++) { 
				//while the numbers in the small box aren't duplicates,
				//keep generating random numbers and set board equal to it
				do
				{ 
					num = randomNumber(9); 
				} 
				while (!checkInputBox(row, col, num)); 
				board[row+i][col+j] = num; 
				filled[row + i][col + j] = num;

			} 
		} 
	} 

	//check to see if the numbers in the 3 by 3 square are the same
	//returns true if no duplicates occur and false if there are duplicates
	boolean checkInputBox(int row, int col, int num) 
	{ 
		for (int i = 0; i<3; i++) 
			for (int j = 0; j<3; j++) 
				if (board[row+i][col+j]==num) 
					return false; 
		return true; } 

	// Random number generator 
	int randomNumber(int num) { 
		return (int) Math.floor((Math.random()*num+1)); 
	} 

	// Check if the number can be placed in the square by checking the rows, columns, and small grids
	boolean validMove(int i,int j,int num) { 
		return (checkRow(i, num) && 
				checkColumn(j, num) && 
				checkInputBox(i-i%3, j-j%3, num)); 
	} 

	// check in the row for existence 
	boolean checkRow(int i,int num){ 
		for (int j = 0; j<9; j++) 
			if (board[i][j] == num) 
				return false; 
		return true; } 

	// check in the column for existence 
	boolean checkColumn(int j,int num) { 
		for (int i = 0; i<9; i++) 
			if (board[i][j] == num) 
				return false; 
		return true; } 

	//fill the rest of the board with numbers 
	boolean setBoard(int i, int j) { 
		//increment the rows
		if (j>=9 && i<9) { 
			i = i + 1; 
			j = 0; 
		} 
		//at the end of the board
		if (i>=9 && j>=9) {
			return true; }
		//set index to second boxes
		if (i < 3) { 
			if (j < 3) 
				j = 3; 
		} 
		//set index to third boxes
		else if (i < 6) { 
			if (j==(int)(i/3)*3) 
				j =  j + 3; 
		} 
		//keep traversing until the end of the board is reached
		else{ 
			if (j == 6) 
			{ 
				i = i + 1; 
				j = 0; 
				if (i>=9) 
					return true;} 
		} 
		//place numbers 1 through 9
		for (int num = 1; num<=9; num++) { 
			//if move is valid, set square equal to board
			if (validMove(i, j, num)) { 
				board[i][j] = num; 
				filled[i][j] = num;
				//if board is filled, return true
				if (setBoard(i, j+1)) { 
					return true; 
				}
				//replace the index otherwise
				board[i][j] = 0;
				filled[i][j] = 0;
			} 
		} 
		return false; 
	} 
} 

