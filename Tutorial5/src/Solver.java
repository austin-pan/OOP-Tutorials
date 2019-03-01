
public class Solver {
	public static GameGrid grid;
	
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
				do {
					c--;
					if(c < 0) {
						c = 8;
						r--;
					}
					if(r < 0)
						return false;
				} while(grid.isInitial(r, c));
				c--;
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
