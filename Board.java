import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Random;

public class Board extends JPanel implements ActionListener {

    public Board(int row, int col) {
        setFocusable(true);
        ROWS = row;
        COLS = col;
        random = new Random();
        addKeyListener(new KeyboardControl());
        //timer = new Timer(400, this);
    }

    //timer calls actionPerformed every 400 ms
    // to spawn new piece
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void start() {
        //timer.start();
        newBoard();
        newTetromino();
    }

    public void pause() {

    }

    public void newTetromino() {
//        next = new Tetrominoes(numTetrominoes, 2, this);
        numTetrominoes++;
        next = new Tetrominoes(numTetrominoes, random.nextInt(7) + 1, this);
        repaint();
    }
    public int getRows() {
        return ROWS;
    }

    public int getCols() {
        return COLS;
    }

    public void placeTetromino(int id, int type, int row, int col) {
        _board[row][col][0] = id;
        _board[row][col][1] = type;
//        int here = getTetrominoID(row, col);
//        if (here == 0) {
//            _board[row][col] = id;
//        }
    }

    /* Return the ID number of the tetromino in this square. */
    public int getTetrominoID(int row, int col) {return _board[row][col][0];}

    /* Return the type of the tetromino in this square. */
    public int getTetrominoType(int row, int col) {return _board[row][col][1];}

    public void clearTetromino(int row, int col) {
        _board[row][col][0] = 0;
        _board[row][col][1] = 0;
    }

    public boolean isGameOver() {
        return _gameOver;
    }

    public void clearLine() {

    }

    /* Create and draw a new board. */
    public void newBoard() {
        _board = new int[ROWS][COLS][2];
        _gameOver = false;
        numTetrominoes = 1;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                int blockType = getTetrominoType(r, c);
                drawSquares(g, Tetrominoes.tetrominoColors[blockType], r, c);
            }
        }
    }

    public void drawSquares(Graphics g, Color color, int row, int col) {
        g.setColor(color);
        g.fillRect(col * SQUARESIZE, row * SQUARESIZE, SQUARESIZE, SQUARESIZE);
        g.setColor(GRIDCOLOR);
        g.drawRect(col * SQUARESIZE, row * SQUARESIZE, SQUARESIZE, SQUARESIZE);
    }

    private int numTetrominoes;
    private int[][][] _board;
    private final int ROWS;
    private final int COLS;
    private final int SQUARESIZE = 30;
    private final Color GRIDCOLOR = Color.black;
    private boolean _gameOver;
    private Timer timer;
    private Tetrominoes next;
    private Random random;

    private class KeyboardControl extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int pressed = e.getKeyCode();
            if (pressed == KeyEvent.VK_N) {
                newTetromino();
                System.out.print("newpiece");
            }
            if (pressed == KeyEvent.VK_LEFT) {
                next.move('l');
            } else if (pressed == KeyEvent.VK_RIGHT) {
                next.move('r');
            } else if (pressed == KeyEvent.VK_DOWN) {
                next.move('d');
            } else if (pressed == KeyEvent.VK_UP) {
                next.rotate();
            } else if (pressed == KeyEvent.VK_ENTER || pressed == KeyEvent.VK_SPACE) {
                next.hardDrop();
            } else if (pressed == 'p' || pressed == 'P') {
                pause();
            }
            repaint();
        }
    }
}