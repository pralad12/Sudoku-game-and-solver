
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class sudokuMenu extends JPanel implements ActionListener {

	ArrayList<JButton> buttons;
	String[] names;
	sudoku sudoku = new sudoku();
	JFrame window;

	//constructor - makes opening window with buttons
	sudokuMenu() {
		window = new JFrame();
		window.add(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(500, 300, 400, 500);
		setLayout(null);
		setButtons();
		this.setBackground(new Color(240, 200, 220));
		window.setVisible(true);
		window.setTitle("Sudoku");
		
	}

	//set up the 3 buttons
	void setButtons() {
		buttons = new ArrayList<>();
		names = new String[] {"Easy", "Normal", "Hard"};
		for (int i = 0; i < 3; i++) {
			JButton tempButton = new JButton(names[i]);
			tempButton.setFont(new Font("comic sans ms", Font.BOLD, 18));
			tempButton.addActionListener(this);
			tempButton.setBounds(100, 80 + 60 * i, 180, 45);
			add(tempButton);
			buttons.add(tempButton);
		}

	}

	//when one of the buttons are clicked, generate the board with the correct 
	//number of empty spaces and show draw the board with gameBoard class
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int buttonIndex=buttons.indexOf(e.getSource());
		int numToRemove = 20 + 20 * buttonIndex;
		sudoku.fillBoard();
		sudoku.fixColRow();
		sudoku.removeNum(numToRemove);
		window.dispose();
		new sudokuBoard(sudoku, names[buttonIndex]);
	}

}


