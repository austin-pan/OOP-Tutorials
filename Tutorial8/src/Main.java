import javax.swing.JOptionPane;
import java.io.File;

public class Main {
	public static void main(String[] args) {
		String path = askForPath();
		if(path != null) {
			SudokuFrame frame = new SudokuFrame(path);
			frame.setVisible(true);
		}
	}
	
	private static String askForPath() {
		String path = JOptionPane.showInputDialog("Input Path");
		if(path == null)
			return "games/sudoku0.sd";
		File file = new File(path);
		if(file.exists())
			return path;
		return null;
	}
}
