
package Handle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;


public class SudokuBoard extends JFrame implements ActionListener{
private JTextField[][] board;
    SudokuSolver solve =new SudokuSolver();
    private JButton solveButton;
    private JButton clearButton;
    private JButton CheckButton;
    private JButton newButton;
    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;
    private JLabel timerLabel;
    private int timeRemaining;
    private Timer gameTimer;
    private int[][] values;
    private static final int[] GAME_TIMES = {1000, 600, 300}; // Time limits in seconds

    private int currentDifficulty;
    private static final int NUM_EMPTY_CELLS = 50; // Change to adjust difficulty

    public SudokuBoard() {
        setTitle("Sudoku Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel TimePanel = new JPanel();
        timerLabel = new JLabel("Time: " + timeRemaining + " seconds");
        timerLabel.setForeground(Color.BLACK); // Set foreground color
        TimePanel.add(timerLabel);
// Create a new JLabel with a background image
        JLabel backgroundLabel = new JLabel(new ImageIcon("/Pictures/background.jpg"));
        backgroundLabel.setLayout(new BorderLayout());
        this.setContentPane(backgroundLabel);
        
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(9, 9, 2, 2));
        boardPanel.setBackground(Color.BLACK); // Set background color
        boardPanel.setSize(500, 500);
        
        board = new JTextField[9][9];
        this.setLocationRelativeTo(null);
        Font font = new Font("Arial", Font.BOLD, 20); // Customize font for better visibility
        boardPanel.setLayout(new GridLayout(3, 3, 5, 5)); // Add spacing between blocks
        boardPanel.setBackground(Color.BLACK); // Set background color
        currentDifficulty = 0;
        timeRemaining = GAME_TIMES[currentDifficulty];
        gameTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (timeRemaining > 0) {
                    timeRemaining--;
                    timerLabel.setText("Time: " + timeRemaining + " seconds");
                    if (timeRemaining == 10) {
                        timerLabel.setForeground(Color.red);
                    }
                } else {
                    gameTimer.stop();
                    JOptionPane.showMessageDialog(SudokuBoard.this, "Time's up! Game over.");
                    for (int row = 0; row < 9; row++) {
                        for (int col = 0; col < 9; col++) {
                            board[row][col].setEditable(false);
                        }
                    }
                }
            }
        });
        
        board = new JTextField[9][9];
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                JPanel blockPanel = new JPanel();
                blockPanel.setLayout(new GridLayout(3, 3, 2, 2)); // Add spacing between cells
                //blockPanel.setBackground(Color.WHITE); // Set background color
                for (int row = blockRow * 3; row < blockRow * 3 + 3; row++) {
                    for (int col = blockCol * 3; col < blockCol * 3 + 3; col++) {
                        board[row][col] = new JTextField(1);
                        board[row][col].setFont(font); // Set font for text fields
                        board[row][col].setHorizontalAlignment(JTextField.CENTER);
                        blockPanel.add(board[row][col]);
                    }
                }
                boardPanel.add(blockPanel);
            }
        }
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK); // Set background color
        CheckButton = new JButton("Check");
        CheckButton.setBackground(Color.GREEN); // Set background color
        CheckButton.setForeground(Color.BLACK); // Set foreground color
        CheckButton.setFont(font);
        CheckButton.addActionListener(this);
        buttonPanel.add(CheckButton);
        
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        clearButton.setBackground(Color.RED); // Set background color
        clearButton.setForeground(Color.WHITE); // Set foreground color
        clearButton.setFont(font); // Set font for button
        buttonPanel.add(clearButton);
        
        solveButton = new JButton("Solve");
        solveButton.setBackground(Color.YELLOW); // Set background color
        solveButton.setForeground(Color.BLACK); // Set foreground color
        solveButton.setFont(font);
        solveButton.addActionListener(this);
        buttonPanel.add(solveButton);
        
        newButton = new JButton("New Game");
        newButton.addActionListener(this);
        newButton.setBackground(Color.BLUE); // Set background color
        newButton.setForeground(Color.WHITE); // Set foreground color
        newButton.setFont(font); // Set font for button
        buttonPanel.add(newButton);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Setting");
        file.setBackground(Color.blue);
        file.setForeground(Color.red);
        
        easyButton = new JButton("Easy");
        easyButton.addActionListener(this);
        easyButton.setMaximumSize(new Dimension(300, 1000));
        file.add(easyButton);
        
        mediumButton = new JButton("Medium");
        mediumButton.addActionListener(this);
        mediumButton.setSize(200, 100);
        file.add(mediumButton);
        
        hardButton = new JButton("Hard");
        hardButton.addActionListener(this);
        hardButton.setMaximumSize(new Dimension(300, 1000));
        file.add(hardButton);
        
        menuBar.add(file);
        menuBar.add(TimePanel, BorderLayout.WEST);
        
        add(menuBar, BorderLayout.PAGE_START);
        add(boardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == CheckButton) {
            if (checkSolution()) {
                JOptionPane.showMessageDialog(this, "Congratulations! You completed the Sudoku game.");
                gameTimer.stop();
            } else {
                JOptionPane.showMessageDialog(this, "The Sudoku board is not complete yet.");
            }
        } else if (e.getSource() == solveButton) {
            solveSudoku();
            gameTimer.stop();
        } else if (e.getSource() == clearButton) {
            clearBoard();
        } else if (e.getSource() == newButton) {
            timeRemaining = GAME_TIMES[currentDifficulty];
            newGame();
        } else if (e.getSource() == easyButton) {
            currentDifficulty = 0;
            timeRemaining = GAME_TIMES[currentDifficulty];
            newGame();
        } else if (e.getSource() == mediumButton) {
            currentDifficulty = 1;
            timeRemaining = GAME_TIMES[currentDifficulty];
            newGame();
        } else if (e.getSource() == hardButton) {
            currentDifficulty = 2;
            timeRemaining = GAME_TIMES[currentDifficulty];
            newGame();
        }
    }
    
    private boolean checkSolution() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String value = board[row][col].getText();
                if (value.isEmpty()) {
                    return false;
                }
                int number = Integer.parseInt(value);
                if (number < 1 || number > 9) {
                    return false;
                }
                if (!isValidValue(row, col, number)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isValidValue(int row, int col, int value) {
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
    
    private void solveSudoku() {
        values = new int[9][9];
        boolean isCorrect = true;
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                String valueString = board[row][col].getText();
                if (valueString.equals("")) {
                    values[row][col] = 0;
                } else {
                    values[row][col] = Integer.parseInt(valueString);
                }
            }
            if (solveSudokuHelper(values, 0, 0)) {
                for (row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        board[row][col].setText(Integer.toString(values[row][col]));
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cannot solve Sudoku puzzle.");
            }
            // Code to check if input is correct goes here
            // Display message if input is correct
            if (isCorrect) {
                JOptionPane.showMessageDialog(this, "Congratulations, you have solved the puzzle!");
            }
        }
    }
    
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
    
    private void newGame() {
        clearBoard();
        Random rand = new Random();
        int[][] values = generateRandomSudoku();
        for (int i = 0; i < NUM_EMPTY_CELLS; i++) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);
            while (values[row][col] == 0) {
                row = rand.nextInt(9);
                col = rand.nextInt(9);
            }
            values[row][col] = 0;
            board[row][col].setEditable(true);
            board[row][col].setBackground(Color.WHITE); // Reset background color
        }
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (values[row][col] != 0) {
                    board[row][col].setText(Integer.toString(values[row][col]));
                    board[row][col].setEditable(false);
                    board[row][col].setBackground(Color.GRAY); // Set background color
                }
            }
        }
        gameTimer.restart();
    }
    
    private int[][] generateRandomSudoku() {
        values = new int[9][9];
        solveSudokuHelper(values, 0, 0);
        return values;
    }
    
    private void clearBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board[row][col].setText("");
                board[row][col].setEditable(true);
            }
        }
    }

}
