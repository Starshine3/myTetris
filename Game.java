package myTetris;

import java.util.Random;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

class Game extends JFrame {

    public static void main(String[] args) {
        _board = new Board();
        _random = new Random();
        while (!_board.gameOver) {
            next = new Tetrominoes(_random.nextInt(7) + 1,
                _board);

        }
    }

        /* Draw a brand new board. */
    public void newBoard() {

    }

    /* Clear the current board and restart. */
    public void clearBoard() {
    }

    /* The current board being played. */
    private Board _board;

    /* A random number generator used to create
    /* a random tetromino. */
    private Random _random;

    /* The next tetromino to fall on the board. */
    private Tetrominoes _next;
}