package Handle;

import java.util.ArrayList;

public class SudokuSolver {

    private int[][] values;
     private boolean solveSudokuHelper(int[][] values, int row, int col) {
        if (row == 9) {
            row = 0;
            if (++col == 9) {
                return true; // Puzzle solved
            }
        }
        
        if (values[row][col] != 0) { // Skip filled cells
            return solveSudokuHelper(values, row + 1, col);
        }
        
        ArrayList<Integer> candidates = getCandidateValues(row, col);
        for (int candidate : candidates) {
            values[row][col] = candidate;
            if (solveSudokuHelper(values, row + 1, col)) {
                return true;
            }
        }
        values[row][col] = 0; // Backtrack
        return false;
    }
    
    private ArrayList<Integer> getCandidateValues(int row, int col) {
        ArrayList<Integer> candidates = new ArrayList<Integer>();
        for (int val = 1; val <= 9; val++) {
            if (isValidValue(row, col, val)) {
                candidates.add(val);
            }
        }
        return candidates;
    }

    public boolean isValidValue(int row, int col, int value) {
        // Check if the value is valid for the given cell
        for (int i = 0; i < 9; i++) {
            if (values[row][i] == value && i != col) {
                return false;
            }

            if (values[i][col] == value && i != row) {
                return false;
            }
        }

        int subBoardRow = row / 3 * 3;
        int subBoardCol = col / 3 * 3;

        for (int i = subBoardRow; i < subBoardRow + 3; i++) {
            for (int j = subBoardCol; j < subBoardCol + 3; j++) {
                if (values[i][j] == value && i != row && j != col) {
                    return false;
                }
            }
        }

        return true;
    }

    public int[][] generateRandomSudoku() {
        values = new int[9][9];
        solveSudokuHelper(values, 0, 0);
        return values;
    }
}
