import java.util.ArrayList;

public class Solver {
	public static GameGrid grid;
	
	public static ArrayList<GameGrid> findAllSolutions(GameGrid grid) {
		ArrayList<GameGrid> solutions = new ArrayList<GameGrid>();
		
		for(int r = 0; r < GameGrid.GRID_DIM; r++) {
			col: for(int c = 0; c < GameGrid.GRID_DIM; c++) {
				if(grid.isInitial(r, c)) {
					if(!(r == GameGrid.GRID_DIM - 1 && c == GameGrid.GRID_DIM - 1)) {
						continue;
					}
					solutions.add(Sudoku.copyGameGrid(grid));
				} else {
					int start = grid.getField(r, c) + 1;
					
					for(int v = start; v <= 9; v++) {
						if(grid.setField(r, c, v)) {
							if(r == GameGrid.GRID_DIM - 1 && c == GameGrid.GRID_DIM -1) {
								solutions.add(Sudoku.copyGameGrid(grid));
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
		return solutions;
	}
	
    public static ArrayList<GameGrid> findAllSolutions2(GameGrid game) {
    	ArrayList<GameGrid> solutions = new ArrayList<GameGrid>();
    	
       // start at top left
       int column = 0;
       int row = 0;

       // if true, we move backwards through the grid, forward otherwise
       boolean goBack = false;

       // while not iterated through all possible combinations yet
       while(!(column == GameGrid.GRID_DIM - 1 && row == -1)) {
           
           // try values on current field
           if(!game.isInitial(column,row)) {
               goBack = false; // go forward
               if(!tryIncrease(game,column,row)) {
                   game.clearField(column,row);
                   goBack = true; // track back
               }
           } else if(column == GameGrid.GRID_DIM - 1 && row == GameGrid.GRID_DIM - 1) {
        	   solutions.add(Sudoku.copyGameGrid(grid));
        	   goBack = true;
           }
           
           // move through grid
           if(goBack) { // backwards
               column--;
               if(column < 0) { // move up one row
                   column = GameGrid.GRID_DIM - 1;
                   row--;
               }
           } else { // forward
              column++;
              if(column >= GameGrid.GRID_DIM) { // move down one row
                  column = 0;
                  row++;
              }
           }

           // we reached the end, hence found a valid solution
           if (column == 0 && row == GameGrid.GRID_DIM) {
               solutions.add(Sudoku.copyGameGrid(grid));
               column = GameGrid.GRID_DIM - 1;
               row = GameGrid.GRID_DIM - 1;
           }
      }

      // we tried all possible combinations without reaching the end, hence no solution
      return solutions;
    }

    private static boolean tryIncrease(GameGrid game, int column, int row) {
        int val = game.getField(column, row);

        boolean success = false;
        for(int i = val + 1; i <= GameGrid.MAX_VALUE; i++) {
            if(game.setField(column, row, i)) {
                success = true;
                break;
            }
        }

        return success;
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
				
				for(int v = start; v <= GameGrid.MAX_VALUE; v++) {
					if(grid.setField(r, c, v)) {
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
		
		for(int v = GameGrid.MIN_VALUE; v <= GameGrid.MAX_VALUE; v++) {
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
		if(r == GameGrid.GRID_DIM - 1 && c == GameGrid.GRID_DIM - 1)
			return true;
		if(c == GameGrid.GRID_DIM - 1)
			return solveRecursive(grid, r+1, 0);
		return solveRecursive(grid, r, c+1);
	}
}
