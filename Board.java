import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Random;

public class Board extends JPanel {

    public Board(int row, int col) {
        ROWS = row;
        COLS = col;
        newBoard();
        addKeyListener(new KeyboardControl());
        //timer = new Timer();
    }

    public int getRows() {
        return ROWS;
    }

    public int getCols() {
        return COLS;
    }

    public boolean isGameOver() {
        return _gameOver;
    }

    public void clearLine() {

    }


    public void pause() {

    }

    /* Create and draw a new board. */
    public void newBoard() {
        _board = new int[ROWS][COLS];
        _gameOver = false;
        drawEmptyBoard(g);
    }

    public void drawEmptyBoard(Graphics g) {
        super.paint(g);
        g.setColor(BORDERCOLOR);
        g.fillRect(0, 0, SQUARESIZE * COLS, SQUARESIZE * ROWS);
    }

    private int[][] _board;
    private final int ROWS;
    private final int COLS;
    private final int SQUARESIZE = 5;
    private final Color BORDERCOLOR = Color.BLACK;
    private boolean _gameOver;
    private Timer timer;
    private Tetrominoes next;
    private Graphics g;

    private final Color[] tetrominoColors = {Color.cyan, Color.blue, Color.orange, Color.yellow, Color.green, Color.pink, Color.red};

    private class KeyboardControl extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int pressed = e.getKeyCode();
            switch (pressed) {
                case KeyEvent.VK_LEFT:
                    System.out.print("left");
                    //next.move('l');
                    break;
                case KeyEvent.VK_RIGHT:
                    System.out.print("right");
                    //next.move('r');
                    break;
                case KeyEvent.VK_DOWN:
                    next.move('d');
                    break;
                case KeyEvent.VK_ENTER:
                case KeyEvent.VK_SPACE:
                    next.hardDrop();
                    break;
                case 'p':
                case 'P':
                    pause();
            }
        }
    }
}