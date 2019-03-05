
public class XGameGrid extends GameGrid {

	public XGameGrid(GameGrid grid) {
		super(grid);
	}
	
	public XGameGrid(int[][] grid) {
		super(grid);
	}

	public XGameGrid(String filename) {
		super(filename);
	}
	
	private boolean checkDiagonal(int row, int col, int value) {
		if(row == col) {
			for(int i = 0; i < grid.length; i++) {
				if(grid[i][i].getValue() == value) {
					return false;
				}
			}
		}
		if(row + col == GameGrid.GRID_DIM - 1) {
			for(int i = 0; i < grid.length; i++) {
				if(grid[i][(GameGrid.GRID_DIM - 1) - i].getValue() == value) {
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean isValid(int r, int c, int value) {
		return super.isValid(r, c, value) && checkDiagonal(r, c, value);
	}
	
	@Override
	public String toString() {
		StringBuilder disp = new StringBuilder("");

    	String hLine = "  -----------------------------------------------------------------------------------------------";
    	String vLine = "|\t";
    	disp.append("  ");
    	for(int i = 0; i < grid.length; i++) {
    		if(i % 3 == 0) {
				disp.append("\t");
			}
    		disp.append(i+1 + "\t");
    	}
    	disp.append("\n");
    	for(int r = 0; r < grid.length; r++) {
    		if(r % 3 == 0) {
    			disp.append(hLine + "\n");
    		}
    		disp.append(r+1 + " ");
    		for(int c = 0; c < grid[r].length; c++) {
    			if(c % 3 == 0) {
    				disp.append(vLine);
    			}
    			
    			String value = " " + grid[r][c].toString();
    			if(r == c || r + c == GameGrid.GRID_DIM - 1) {
    				value = "[" + grid[r][c] + "]";
    			}
    			
    			disp.append(value + "\t");
    			if(c == 8) {
    				disp.append(vLine);
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
