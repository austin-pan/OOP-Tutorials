import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class SudokuFrame extends JFrame {
	private final GameGrid grid;
	
	public SudokuFrame(String filepath) {
		super.setSize(500, 500);
		super.setTitle(filepath);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		grid = new RGameGrid(filepath);
		
		GridLayout layout = new GridLayout(9, 9);
		this.setLayout(layout);
		
		this.createButtonGrid();
	}
	
	private void createButtonGrid() {
		for(int r = 0; r < GameGrid.GRID_DIM; r++) {
			for(int c = 0; c < GameGrid.GRID_DIM; c++) {
				JButton curr = new JButton();
				if(grid.getField(r, c) != 0) {
					curr.setText(Integer.toString(grid.getField(r, c)));
					curr.setEnabled(false);
				}
				curr.addActionListener(new SudokuFieldAction(r, c, grid));
				this.add(curr);
			}
		}
	}
}
