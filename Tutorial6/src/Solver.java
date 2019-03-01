import java.util.ArrayList;

public class Solver {
	public static GameGrid grid;
	
	public static ArrayList<GameGrid> findAllSolutions(GameGrid grid) {
		ArrayList<GameGrid> solutions = new ArrayList<GameGrid>();

		while(true) {
			for(int r = 0; r < GameGrid.GRID_DIM; r++) {
				col: for(int c = 0; c < GameGrid.GRID_DIM; c++) {
					if(grid.isInitial(r, c)) {
						if(!(r == 8 && c == 8)) {
							continue;
						}
						solutions.add(new GameGrid(grid));
					} else {
						int start = grid.getField(r, c) + 1;
						
						for(int v = start; v <= 9; v++) {
							if(grid.setField(r, c, v, true)) {
								if(r == 8 && c == 8) {
									solutions.add(new GameGrid(grid));
								} else {
									continue col;
								}
							}
						}
					}
					
					grid.clearField(r, c);
					int[] rc = backtrack(r, c, grid);
					r = rc[0];
					c = rc[1];
					if(r < 0) {
						return solutions;
					}
				}
			}
		}
	}
	
	private static int[] backtrack(int r, int c, GameGrid grid) {
		do {
			c--;
			if(c < 0) {
				c = 8;
				r--;
			}
			if(r < 0) {
				return new int[]{-1, -1};
			}
		} while(grid.isInitial(r, c));
		c--;
		
		return new int[]{r, c};
	}
	
	public static boolean solve(GameGrid grid) {
		for(int r = 0; r < GameGrid.GRID_DIM; r++) {
			col: for(int c = 0; c < GameGrid.GRID_DIM; c++) {
				if(grid.isInitial(r, c))
					continue;
				
				int start = grid.getField(r, c) + 1;
				
				for(int v = start; v <= 9; v++) {
					if(grid.setField(r, c, v, true)) {
						continue col;
					}
				}
				
				grid.clearField(r, c);
				int[] rc = backtrack(r, c, grid);
				r = rc[0];
				c = rc[1];
				if(r < 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean solveRecursive(GameGrid grid) {
		return solveRecursive(grid, 0, 0);
	}
	
	private static boolean solveRecursive(GameGrid grid, int r, int c) {
		if(grid.isInitial(r, c)) {
			return solveRecursiveNext(grid, r, c);
		}
		
		for(int v = 1; v <= 9; v++) {
			if(grid.setField(r, c, v, true)) {
				if(solveRecursiveNext(grid, r, c)) {
					return true;
				}
			}
		}
		
		grid.clearField(r, c);
		return false;
	}
	
	private static boolean solveRecursiveNext(GameGrid grid, int r, int c) {
		if(r == 8 && c == 8)
			return true;
		if(c == 8)
			return solveRecursive(grid, r+1, 0);
		return solveRecursive(grid, r, c+1);
	}
}
