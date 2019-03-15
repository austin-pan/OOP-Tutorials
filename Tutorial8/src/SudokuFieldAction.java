import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class SudokuFieldAction implements ActionListener {
	private final int r, c;
	private final GameGrid grid;
	
	public SudokuFieldAction(int r, int c, GameGrid grid) {
		this.r = r;
		this.c = c;
		this.grid = grid;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton source = (JButton) e.getSource();
			try {
				String input = JOptionPane.showInputDialog("Input Value [0-9]");
				if(input != null) {
					int value = Integer.parseInt(input);
					if(value < 0 || value > 9) {
						throw new IllegalArgumentException("Invalid Input");
					}
					
					if(value == 0) {
						source.setText("");
						grid.clearField(r, c);
					} else {
						if(grid.isValid(r, c, value)) {
							source.setText(input);
							grid.setField(r, c, value);
						} else {
							throw new IllegalArgumentException("Invalid Input");
						}
					}
				}
			} catch(Exception ex) {
				JOptionPane.showMessageDialog(null, "Invalid Input");
			}
		}
	}
}
