import java.util.Objects;

public class GameGrid {
	private Field[][] grid;
	public static final int GRID_DIM = 9;
	
	public static void main(String[] args) {
		for(int i = 0; i < 9; i++) {
			String filePath = "games/sudoku" + i + ".sd";
			GameGrid g = new GameGrid(filePath);
			System.out.println(Solver.findAllSolutions(g).size());
		}
		
		System.out.println();
		GameGrid g = new GameGrid("games/unsolvablesudoku2.sd");
		System.out.println(g);
		System.out.println(Solver.solve(g));
		System.out.println(g);
	}
	
	public GameGrid(int[][] grid) {
		initialiseGrid(Objects.requireNonNull(grid));
	}
	
	public GameGrid(String filename) {
		initialiseGrid(IOUtils.loadFromFile(filename));
		Objects.requireNonNull(grid);
	}
	
	public GameGrid(GameGrid grid) {
		this.grid = new Field[GameGrid.GRID_DIM][GameGrid.GRID_DIM];
		for(int r = 0; r < GameGrid.GRID_DIM; r++) {
			for(int c = 0; c < GameGrid.GRID_DIM; c++) {
				this.grid[r][c] = new Field(grid.getField(r, c), grid.isInitial(r, c));
			}
		}
	}
	
	private void initialiseGrid(int[][] grid) {
		this.grid = new Field[grid.length][grid[0].length];
		for(int r = 0; r < grid.length; r++) {
			for(int c = 0; c < grid[r].length; c++) {
				if(grid[r][c] == 0) {
					this.grid[r][c] = new Field();
					continue;
				}
				this.grid[r][c] = new Field(grid[r][c], true);
			}
		}
	}
	
    private boolean checkColumn(int x, int value) {
    	for(int r = 0; r < grid.length; r++) {
    		if(grid[r][x].getValue() == value)
    			return false;
    	}
    	return true;
    }
	
	private boolean checkRow(int y, int value) {
    	for(int c = 0; c < grid[y].length; c++) {
    		if(grid[y][c].getValue() == value)
    			return false;
    	}
    	return true;
    }
    
    private boolean checkSubGrid(int x, int y, int value) {
    	int xSub = (x / 3) * 3;
    	int ySub = (y / 3) * 3;
    	
    	
    	for(int r = ySub; r < ySub+3; r++) {
    		for(int c = xSub; c < xSub+3; c++) {
    			if(grid[r][c].getValue() == value)
    				return false;
    		}
    	}
    	return true;
    }
    
    public void clearField(int r, int c) {
		checkValidInput(r, c);
		
		grid[r][c].setValue(0);
	}
    
    @Override
    public boolean equals(Object o) {
    	GameGrid other = (GameGrid) o;
    	for(int r = 0; r < GRID_DIM; r++) {
    		for(int c = 0; c < GRID_DIM; c++) {
    			if(this.getField(r, c) != other.getField(r, c))
    				return false;
    		}
    	}
    	return true;
    }
    
    public int getNumFreeFields() {
    	int count = 0;
    	for(int r = 0; r < GRID_DIM; r++) {
    		for(int c = 0; c < GRID_DIM; c++) {
    			if(grid[r][c].getValue() == 0) {
    				count++;
    			}
    		}
    	}
    	return count;
    }
    
    public boolean isInitial(int r, int c) {
    	checkValidInput(r, c);
    	
    	return grid[r][c].isInitial();
    }
    
    public void setInitial(int r, int c, boolean initial) {
    	checkValidInput(r, c);
    	
    	grid[r][c].setInitial(initial);
    }
    
    private boolean isValid(int r, int c, int value) {
    	return checkRow(r, value) && checkColumn(c, value) && checkSubGrid(c, r, value) && !isInitial(r, c);
    }
    
    private void checkValidInput(int r, int c) {
    	if(c < 0 || c > 8 || r < 0 || r > 8) {
			throw new IllegalArgumentException("Invalid Index " + c + ", " + r);
		}
    }
	
	public int getField(int r, int c) {
		checkValidInput(r, c);
		
		return grid[r][c].getValue();
	}
	
	public boolean setField(int r, int c, int v) {
		return setField(r, c, v, false);
	}
	
	public boolean setField(int r, int c, int v, boolean silent) {
		checkValidInput(r, c);
				
		if(isValid(r, c, v)) {
			grid[r][c].setValue(v);
			if(!silent)
				System.out.println(v + " IS allowed at " + c + ", " + r);
			return true;
		}
		if(!silent)
			System.out.println(v + " is not allowed at " + c + ", " + r);
		return false;
	}
    
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
    			disp.append(grid[r][c] + "\t");
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
}
