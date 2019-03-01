import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

public class Ranker {
	public static void main(String[] args) {
		HashMap<String, GameGrid> sudokus = IOUtils.loadFromFolder("games");
		Iterator<String> sudokuIt = sudokus.keySet().iterator();
		ArrayList<Float> ranks = new ArrayList<Float>();
		ArrayList<String> filePaths = new ArrayList<String>();
		
		HashMap<Float, String> rankedSudokus = new HashMap<Float, String>();
		while(sudokuIt.hasNext()) {
			String currKey = sudokuIt.next();
			float rank = rankSudoku(sudokus.get(currKey));
			rankedSudokus.put(rank, currKey);
			ranks.add(rank);
			filePaths.add(currKey);
		}
		
		//sort
		for(int i = 0; i < ranks.size(); i++) {
			for(int j = i; j < ranks.size(); j++) {
				if(ranks.get(i) > ranks.get(j)) {
					float tempRank = ranks.get(i);
					ranks.set(i, ranks.get(j));
					ranks.set(j, tempRank);
					
					String tempPath = filePaths.get(i);
					filePaths.set(i, filePaths.get(j));
					filePaths.set(j, tempPath);
				}
			}
		}
		
		for(int i = 0; i < ranks.size(); i++) {
			float rank = ranks.get(i);
			String path = filePaths.get(i);
			System.out.println(path + " " + rank);
		}
		
		GameGrid generate = generateSudoku("games/sudoku7.sd", 0.68f, false);
		System.out.println(generate);
		System.out.println(rankSudoku(generate));
	}
	
	public static float rankSudoku(GameGrid grid) {
		int numFree = grid.getNumFreeFields();
		int numSolutions = Solver.findAllSolutions(grid).size();
		if(numSolutions == 0) {
			return 0f;
		}
		double rank = (numFree/81.0) * (1.0/numSolutions/numSolutions);
		return (float) rank;
	}
	
	//unfinished
	public static GameGrid generateSudoku(String filePath, float minimumRank, boolean silent) {
		GameGrid grid = new GameGrid(filePath);
		Solver.solve(grid);
		
		int iteration = 0;
		while(iteration < 1000) {
			int randR = (int) (Math.random() * GameGrid.GRID_DIM);
			int randC = (int) (Math.random() * GameGrid.GRID_DIM);
			GameGrid neighbor = new GameGrid(grid);
			neighbor.clearField(randR, randC);
			
			float neighborRank = rankSudoku(neighbor);
			float currRank = rankSudoku(grid);
			if(neighborRank > currRank) {
				grid = new GameGrid(neighbor);
				if(!silent)
					System.out.println(iteration + ": " + neighborRank);
			}
			if(currRank >= minimumRank)
				break;
			
			iteration++;
		}
		
		for(int r = 0; r < GameGrid.GRID_DIM; r++) {
			for(int c = 0; c < GameGrid.GRID_DIM; c++) {
				if(grid.getField(r, c) == 0) {
					grid.setInitial(r, c, false);
				} else {
					grid.setInitial(r, c, true);
				}
			}
		}
		
		return grid;
	}
	
	//private static ArrayList<GameGrid> generateSudoku(GameGrid grid, float minimumRank, boolean silent) {}
}
