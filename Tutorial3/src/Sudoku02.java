import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Objects;

public class Sudoku02 {

    /**
     * Print a game menu message to the console.
     */
    public static void printMenu() {
        System.out.print("\n" +
        		"1. Set field\n" +
        		"2. Clear field\n" +
                "3. Print game\n" +
                "4. Exit\n\n" +
                "Select an action [1-4]: ");
    }
    
    public static boolean checkRow(int x, int y, int value, int[][] grid) {
    	for(int c = 0; c < grid[y].length; c++) {
    		if(grid[y][c] == value)
    			return false;
    	}
    	return true;
    }
    
    public static boolean checkColumn(int x, int y, int value, int[][] grid) {
    	for(int r = 0; r < grid.length; r++) {
    		if(grid[r][x] == value)
    			return false;
    	}
    	return true;
    }
    
    public static boolean checkSubGrid(int x, int y, int value, int[][] grid) {
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
    
    public static boolean isValid(int x, int y, int value, int[][] grid) {
    	return checkRow(x, y, value, grid) && checkColumn(x, y, value, grid) && checkSubGrid(x, y, value, grid);
    }

    /**
     * Read a single integer value from the console and return it.
     * This function blocks the program's execution until a user
     * entered a value into the command line and confirmed by pressing
     * the Enter key.
     * @return The user's input as integer or -1 if the user's input was invalid.
     */
    public static int parseInput() {
        Scanner in = new Scanner(System.in);
        try {
            return in.nextInt();
        } catch (InputMismatchException missE) {
            in.next(); // discard invalid input
            return -1;
        }
    }   

    /**
     * Display a dialog requesting a single integer which is returned
     * upon completion.
     *
     * The dialog is repeated in an endless loop if the given input 
     * is not an integer or not within min and max bounds.
     *
     * @param msg: a name for the requested data.
     * @param min: minium accepted integer.
     * @param max: maximum accepted integer.
     * @return The user's input as integer.
     */
    public static int requestInt(String msg, int min, int max) {
        Objects.requireNonNull(msg);

        while(true) {
            System.out.print("Please provide " + msg + ": ");
            int input = parseInput();
            if (input >= min && input <= max) return input;
            else {
                System.out.println("Invalid input. Must be between " + min + " and " + max);
            }
        }
    }

    // TODO write a static function printGrid here 
    public static void printGrid(int[][] grid) {
    	printGrid(0, 0, -1, grid);
    }
    
    private static void printGrid(int y, int x, int value, int[][] grid) {
    	String startBoldItalics = "";
    	String endBoldItalics = "";
    	
    	String hLine = "  -------------";
    	System.out.print("  ");
    	for(int i = 0; i < grid.length; i++) {
    		if(i % 3 == 0) {
				System.out.print(" ");
			}
    		System.out.print(i+1);
    	}
    	System.out.println();
    	for(int r = 0; r < grid.length; r++) {
    		if(r % 3 == 0) {
    			System.out.println(hLine);
    		}
    		System.out.print(r+1 + " ");
    		for(int c = 0; c < grid[r].length; c++) {
    			if(c % 3 == 0) {
    				System.out.print("|");
    			}
    			startBoldItalics = "";
    			endBoldItalics = "";
    			if(grid[r][c] == value && !isValid(x, y, value, grid)) {
    				int xSub = (int) (x / 3.0) * 3;
        	    	int ySub = (int) (y / 3.0) * 3;
        	    	boolean inSub = (r >= ySub && r < ySub + 3) && (c >= xSub && c < xSub + 3);
        	    	if(c == x || r == y || inSub) {
	    	    		startBoldItalics = "\033[3m\033[4m";
	    	    		endBoldItalics = "\033[0m";
        	    	}
    	    	}
    			System.out.print(startBoldItalics + grid[r][c] + endBoldItalics);
    			if(c == 8) {
    				System.out.print("|");
    			}
    		}
    		System.out.println();
    		if(r == 8) {
    			System.out.println(hLine);
    		}
    	}
    }

    public static void main(String[] args) {
    	int[][] grid = {
                {9,4,0,1,0,2,0,5,8},
                {6,0,0,0,5,0,0,0,4},
                {0,0,2,4,0,3,1,0,0},
                {0,2,0,0,0,0,0,6,0},
                {5,0,8,0,2,0,4,0,1},
                {0,6,0,0,0,0,0,8,0},
                {0,0,1,6,0,8,7,0,0},
                {7,0,0,0,4,0,0,0,3},
                {4,3,0,5,0,9,0,1,2}
            };
    	
    	if(args.length == 81) {
    		int i = 0;
			for(int r = 0; r < grid.length; r++) {
				for(int c = 0; c < grid[r].length; c++) {
					grid[r][c] = Integer.parseInt(args[i]);
					i++;
				}
			}
    	}

        // TODO print the grid here
        printGrid(grid);

        // TODO implement the game loop here
        boolean exit = false;
        while(!exit) {
        	printMenu();
        	int s = requestInt("selection", 1, 4);
        	switch(s) {
        		case 1: //set
        			int c = requestInt("column index", 1, 9) - 1;
        			int r = requestInt("row index", 1, 9) - 1;
        			int x = requestInt("value", 1, 9);
        			if(isValid(r, c, x, grid)) {
        				grid[r][c] = x;
        				printGrid(grid);
        			}
        			else {
        				System.out.println("This value is not allowed for the specified position");
        				printGrid(r, c, x, grid);
        			}
        			break;
        		
        		case 2: //clear
        			c = requestInt("column index", 1, 9) - 1;
        			r = requestInt("row index", 1, 9) - 1;
        			grid[r][c] = 0;
        			printGrid(grid);
        			break;
        			
        		case 3: //print
        			printGrid(grid);
        			break;
        		
        		case 4: //exit
        			System.out.println("Bye");
        			exit = true;
        			break;
        			
        		default:
        			System.out.println("Invalid input");
        			break;
        	}
        }
        
    }
}
