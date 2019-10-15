import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
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
        timer = new Timer(400, this);
    }

    //timer calls actionPerformed every 400 ms
    // to spawn new piece
    @Override
    public void actionPerformed(ActionEvent e) {
        if (next.isAtBottom()) {
            newTetromino();
        } else {
            next.move('d');
            repaint();
            clearLine();
        }
    }

    public void start() {
        timer.start();
        newBoard();
        newTetromino();
    }

    public void pause() {

    }

    public void newTetromino() {
        if (next != null) {
            topMostRow = next.getTopRow();
        }
        if (checkGameOver()) {
            gameOver();
        } else {
            next = new Tetrominoes(numTetrominoes, random.nextInt(7) + 1, this);
            numTetrominoes++;
            repaint();
        }
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

    public boolean checkGameOver() {
        return topMostRow == 0;
    }

    public void gameOver() {
        System.out.print("GAME OVER!!!");
    }

    public void clearLine() {
        int thisHeight = next.getHeight();
        int thisTopRow = next.getTopRow();
        boolean repaint = false;
        for (int i = 0; i < thisHeight; i++) {
            if (shouldClearLine(thisTopRow + i)) {
                doClearLine(thisTopRow + i);
                moveRows(thisTopRow + i);
                repaint = true;
            }
        }
    }

    public boolean shouldClearLine(int line) {
        if (line >= ROWS || line < 0) {
            return false;
        }
        boolean clear = true;
        for (int i = 0; i < COLS; i++) {
            if (getTetrominoID(line, i) == 0) {
                clear = false;
                break;
            }
        }
        return clear;
    }

    public void doClearLine(int line) {
        for (int i = 0; i < COLS; i++) {
            clearTetromino(line, i);
        }
        next.adjustTop();
    }

    public void moveRows(int removed) {
        int[][] cleared;
        while (removed > 0) {
            cleared = _board[removed];
            _board[removed] = _board[removed - 1];
            _board[removed - 1] = cleared;
            removed--;
        }
    }

    /* Create and draw a new board. */
    public void newBoard() {
        _board = new int[ROWS][COLS][2];
        _gameOver = false;
        numTetrominoes = 1;
        topMostRow = 19;
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
    private int topMostRow;
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
            }
            if (pressed == KeyEvent.VK_LEFT) {
                next.move('l');
                repaint();
            } else if (pressed == KeyEvent.VK_RIGHT) {
                next.move('r');
                repaint();
            } else if (pressed == KeyEvent.VK_DOWN) {
                next.move('d');
                repaint();
                clearLine();
            } else if (pressed == KeyEvent.VK_UP) {
                next.rotate();
                repaint();
            } else if (pressed == KeyEvent.VK_ENTER || pressed == KeyEvent.VK_SPACE) {
                next.hardDrop();
                repaint();
                clearLine();
            } else if (pressed == 'p' || pressed == 'P') {
                pause();
            }
        }
    }
}