package View;
import java.awt.*;
import java.net.URL;

public class Sudoku {
    
    public static void main(String[] args) {
        SudokuFrame frame = new SudokuFrame();
        frame.setLocationRelativeTo(null);
        URL urlIconNotepad = Sudoku.class
                .getResource("/Pictures/images.png");
        Image img = Toolkit.getDefaultToolkit().createImage(urlIconNotepad);
        frame.setIconImage(img);
        frame.setVisible(true);
    }
}
