import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Objects;

public class Sudoku01 {

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
    			System.out.print(grid[r][c]);
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

        // TODO print the grid here
        printGrid(grid);

        // TODO implement the game loop here
        boolean exit = false;
        while(!exit) {
        	printMenu();
        	int s = requestInt("selection", 1, 4);
        	switch(s) {
        		case 1: //set
        			int c = requestInt("column index", 1, 9);
        			int r = requestInt("row index", 1, 9);
        			int x = requestInt("value", 1, 9);
        			grid[r-1][c-1] = x;
        			printGrid(grid);
        			break;
        		
        		case 2: //clear
        			c = requestInt("column index", 1, 9);
        			r = requestInt("row index", 1, 9);
        			grid[r-1][c-1] = 0;
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
