import org.junit.Test;
import org.junit.Before;

public class Sudoku02BasicTest {

    private int[][] grid;
    @Before
    public void setUp() {
        grid = new int[][]{
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
    }

    @Test
    public void printGridTest() {
        Sudoku02.printGrid(grid);
    }

    @Test
    public void checkRowTest() {
       boolean result = Sudoku02.checkRow(0,0,1,grid);
    }

    @Test
    public void checkColumnTest() {
       boolean result = Sudoku02.checkColumn(0,0,1,grid);
    }

    @Test
    public void checkSubGridTest() {
       boolean result = Sudoku02.checkSubGrid(0,0,1,grid);
    }

    @Test
    public void isValidTest() {
       boolean result = Sudoku02.isValid(0,0,1,grid);
    }

}
