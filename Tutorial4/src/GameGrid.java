import java.util.Objects;

public class GameGrid {
	private final int[][] grid;
	public static final int GRID_DIM = 9;
	
	public GameGrid(int[][] grid) {
		this.grid = Objects.requireNonNull(grid);
	}
	
	public GameGrid(String filename) {
		grid = IOUtils.loadFromFile(filename);
		Objects.requireNonNull(grid);
	}
	
	public int getField(int c, int r) {
		if(c < 0 || c >8 || r < 0 || r > 8) {
			throw new IllegalArgumentException("Invalid Input");
		}
		
		return grid[r][c];
	}
	
	public boolean setField(int c, int r, int v) {
		if(c < 0 || c >8 || r < 0 || r > 8 || v < 1 || v > 9) {
			throw new IllegalArgumentException("Invalid Input");
		}
		
		if(isValid(c, r, v)) {
			grid[r][c] = v;
			return true;
		}
		System.out.println("This value is not allowed at the specified position");
		return false;
	}
	
	public void clearField(int c, int r) {
		if(c < 0 || c >8 || r < 0 || r > 8) {
			throw new IllegalArgumentException("Invalid Input");
		}
		
		grid[r][c] = 0;
	}
	
	private boolean checkRow(int x, int y, int value) {
    	for(int c = 0; c < grid[y].length; c++) {
    		if(grid[y][c] == value)
    			return false;
    	}
    	return true;
    }
    
    private boolean checkColumn(int x, int y, int value) {
    	for(int r = 0; r < grid.length; r++) {
    		if(grid[r][x] == value)
    			return false;
    	}
    	return true;
    }
    
    private boolean checkSubGrid(int x, int y, int value) {
    	int xSub = (int) (x / 3.0) * 3;
    	int ySub = (int) (y / 3.0) * 3;
    	
    	
    	for(int r = ySub; r < ySub+3; r++) {
    		for(int c = xSub; c < xSub+3; c++) {
    			if(grid[r][c] == value)
    				return false;
    		}
    	}
    	return true;
    }
    
    private boolean isValid(int x, int y, int value) {
    	return checkRow(x, y, value) && checkColumn(x, y, value) && checkSubGrid(x, y, value);
    }
    
    public String toSaveString() {
    	StringBuilder disp = new StringBuilder("");
    	for(int r = 0; r < grid.length; r++) {
    		for(int c = 0; c < grid[r].length; c++) {
    			disp.append(grid[r][c] + " ");
    		}
    		disp.append("\n");
    	}
    	return disp.toString();
    }
    
    public String toString() {
    	StringBuilder disp = new StringBuilder("");

    	String hLine = "  -------------";
    	disp.append("  ");
    	for(int i = 0; i < grid.length; i++) {
    		if(i % 3 == 0) {
				disp.append(" ");
			}
    		disp.append(i+1);
    	}
    	disp.append("\n");
    	for(int r = 0; r < grid.length; r++) {
    		if(r % 3 == 0) {
    			disp.append(hLine + "\n");
    		}
    		disp.append(r+1 + " ");
    		for(int c = 0; c < grid[r].length; c++) {
    			if(c % 3 == 0) {
    				disp.append("|");
    			}
    			disp.append(grid[r][c]);
    			if(c == 8) {
    				disp.append("|");
    			}
    		}
    		disp.append("\n");
    		if(r == 8) {
    			disp.append(hLine);
    		}
    	}
    	
    	return disp.toString();
    }
}
