
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


class sudokuSolver extends JPanel{ 

	sudoku sudoku;
	JFrame window;

	public void gameBoard(sudoku sudoku) {
		this.sudoku = sudoku;
		sudokuSolver so = new sudokuSolver();
		so.solve(sudoku.board);
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.setBounds(400, 100, 9 * 60 + 160, 9 * 60 + 200);
		window.setBackground(new Color(240, 200, 220));
		window.setVisible(true);
		setLayout(null);
		repaint();
		window.setResizable(false);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.setFont(new Font("comic sans ms", Font.BOLD, 30));
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				g2.drawRect(x * 60 + 60, y * 60 + 60, 60, 60);
				if (x % 3 == 0 && y % 3 == 0) {
					g2.setStroke(new BasicStroke(4));
					g2.drawRect(x * 60 + 60, y * 60 + 60, 180, 180);
				}
				g2.setStroke(new BasicStroke(2));
				if (sudoku.board[x][y] != 0) {
					g.drawString(String.valueOf(sudoku.board[x][y]), x * 60 + 85, y * 60 + 100);
				}
			}
		}
	}
	//check to see if placement is valid
	public boolean checkMove(int[][] board, int row, int col, int num) { 
		for (int i = 0; i < board.length; i++) { 
			//if the number already exists in the row
			if (board[row][i] == num) { 
				return false; 
			} 
		} 

		// if the number already exists in the column
		for (int j = 0; j < board.length; j++) { 
			if (board[j][col] == num) { 
				return false; 
			} 
		} 

		// if the number already exists in the small 3 by 3 box
		int sqrt = (int)Math.sqrt(board.length); 
		int rowStart = row - row % sqrt; 
		int colStart = col - col % sqrt; 

		for (int k = rowStart;k < rowStart + sqrt; k++) { 
			for (int l = colStart;l < colStart + sqrt; l++) { 
				if (board[k][l] == num) { 
					return false; 
				} 
			} 
		} 

		// if the number doesn't exist in the row, column, 
		//or 3 by 3 box, its valid
		return true; 
	} 

	//solve the sudoku and return boolean if it does
	public boolean solve(int[][] board) { 
		int row = -1; 
		int col = -1; 
		//boolean to check
		boolean isEmpty = true; 
		//check to see if board is filled(doesn't have zeros)
		//If it does, then set row and column equal to the indexes
		for (int i = 0; i < 9; i++) { 
			for (int j = 0; j < 9; j++) { 
				if (board[i][j] == 0) { 
					row = i; 
					col = j; 

					isEmpty = false; 
					break; 
				} 
			} 
			//breaks the outer for loop if it still needs to be solved
			if (!isEmpty) { 
				break; 
			} 
		} 

		//if all the squares have a number and no zeros are left
		if (isEmpty) { 
			return true; 
		} 
		// for each number in the row and column defined above
		for (int num = 1; num <= 9; num++) { 
			//if the number is valid, assign the square as the number
			if (checkMove(board, row, col, num)) { 
				board[row][col] = num; 
				//if doing so solves the board, return true
				if (solve(board)) { 
					return true; 
				} 
				else { 
					//if it doesn't, replace the index with zero
					board[row][col] = 0; 
				} 
			} 
		} 
		return false; 
	} 
} 
