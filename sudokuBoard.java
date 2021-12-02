
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;


public class sudokuBoard extends JPanel implements ActionListener {
	
	JFrame window;
	final int size = 9;
	sudoku sudoku;
	JTextField[][] input = new JTextField[size][size];
	JButton checkButton = new JButton("Check");
	JButton solveButton = new JButton("Solve");
	double seconds = 0;
	Timer timer = new Timer();
	sudokuSolver solver = new sudokuSolver();
	
	//count the seconds with timer
	TimerTask task = new TimerTask() {
		public void run() {
			seconds++;
		}
	};
	
	//sets up the window, starts the timer,
	//and draws the gameboard with repaint
	sudokuBoard(sudoku sudoku,String title) {
		timer.schedule(task, 0, 1000);
		this.sudoku = sudoku;
		window = new JFrame();
		window.add(this);
		window.setBounds(400, 100, size * 60 + 160, size * 60 + 200);
		window.setBackground(new Color(240, 200, 220));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		window.setTitle(title);
		setLayout(null);
		userInput();
		window.setResizable(false);
		repaint();
	}

	//Draw the squares 
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.setFont(new Font("serif", Font.BOLD, 30));
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				g2.drawRect(x * 60 + 60, y * 60 + 60, 60, 60);
				if (x % 3 == 0 && y % 3 == 0) {
					g2.setStroke(new BasicStroke(4));
					g2.drawRect(x * 60 + 60, y * 60 + 60, 180, 180);
				}
				g2.setStroke(new BasicStroke(2));
				if (sudoku.board[x][y] != 0) {
					//if square isn't zero, place down the number
					g.drawString(String.valueOf(sudoku.board[x][y]), x * 60 + 85, y * 60 + 100);
				}
			}
		}
	}

	//if the square is zero, make a jTextField so player can fill in the answer
	//Also, make the solve and check buttons
	private void userInput() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (sudoku.board[i][j] == 0) {
					input[i][j] = new JTextField(1);
					input[i][j].setFont(new Font("comic sans ms", Font.BOLD, 30));
					input[i][j].setHorizontalAlignment(SwingConstants.CENTER);
					input[i][j].setBounds(i * 60 + 72, j * 60 + 72, 40, 40);
					input[i][j].setVisible(true);
					this.add(input[i][j]);
				} else {
					input[i][j] = null;
				}
			}
		}
		//check button
		checkButton.setFont(new Font("comic sans ms", Font.PLAIN, 30));
		checkButton.setBounds(440, 640, 150, 40);
		checkButton.setVisible(true);
		checkButton.addActionListener(this);
		this.add(checkButton);
		//solve button
		solveButton.setFont(new Font("comic sans ms", Font.PLAIN, 30));
		solveButton.setBounds(70, 640, 150, 40);
		solveButton.setVisible(true);
		solveButton.addActionListener(this);
		this.add(solveButton);
	}

	//when check button or solve button is pressed
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == checkButton) {
			//if check button is pressed, set the inputs as the numbers on the board
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (input[i][j] != null) {
						try {
							int temp = Integer.parseInt(input[i][j].getText());
							if (temp < 1 || temp > 9) {
								throw new Exception();
							}
							sudoku.board[i][j] = temp;
						} catch (Exception e1) {
							input[i][j].setText("");
							sudoku.board[i][j] = 0;
						}
					}
				}   
			}
			solver.solve(sudoku.filled);
			//if board is complete and correct, display the timer saying how long it took to solve
			if (sudoku.isComplete(sudoku.board, sudoku.filled)) {
				timer.cancel();
				double min = seconds/60;
				JOptionPane.showMessageDialog(this, "You Win! It took you " +  min + " minutes to solve!");
			} else {
				//if incorrect, display try again message
				JOptionPane.showMessageDialog(this, "Try again!");
			}
		} else if (e.getSource() == solveButton) {
			//if solved button is pressed, call the solver and display 
			//the answer window
			this.window.dispose();
			solver.solve(sudoku.board);
			solver.gameBoard(sudoku);
		}
	}
}

