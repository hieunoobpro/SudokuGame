package Handle;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Time {

    private JTextField[][] board;
    private JLabel timerLabel;
    private static final int[] GAME_TIMES = {1000, 600, 300}; // Time limits in seconds
    private int currentDifficulty = 0;
    private int timeRemaining = GAME_TIMES[currentDifficulty];

    private Timer gameTimer = new Timer(1000, new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            JPanel TimePanel = new JPanel();
            timerLabel = new JLabel("Time: " + timeRemaining + " seconds");
            timerLabel.setForeground(Color.BLACK); // Set foreground color
            TimePanel.add(timerLabel);
            if (timeRemaining > 0) {
                timeRemaining--;
                timerLabel.setText("Time: " + timeRemaining + " seconds");
                if (timeRemaining == 10) {
                    timerLabel.setForeground(Color.red);
                }
            } else {
                gameTimer.stop();
       
                JOptionPane.showMessageDialog(null, "Time's up! Game over.");
                for (int row = 0; row < 9; row++) {
                    for (int col = 0; col < 9; col++) {
                        board[row][col].setEditable(false);
                    }
                }
            }
        }
    });

    public JTextField[][] getBoard() {
        return board;
    }

    public void setBoard(JTextField[][] board) {
        this.board = board;
    }

    public JLabel getTimerLabel() {
        return timerLabel;
    }

    public void setTimerLabel(JLabel timerLabel) {
        this.timerLabel = timerLabel;
    }

    public int getCurrentDifficulty() {
        return currentDifficulty;
    }

    public void setCurrentDifficulty(int currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public Timer getGameTimer() {
        return gameTimer;
    }

    public void setGameTimer(Timer gameTimer) {
        this.gameTimer = gameTimer;
    }

}
