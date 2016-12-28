package myTetris;

class Board {

    public Board(int height, int width) {
        BOARDHEIGHT = height;
        BOARDWIDTH = width;
        _board = new int[height][width];
        _gameOver = false;
    }

    public int getWidth() {
        return BOARDWIDTH;
    }

    public int getHeight() {
        return BOARDHEIGHT;
    }

    private int[][] _board;
    private final int BOARDHEIGHT;
    private final int BOARDWIDTH;
    private boolean _gameOver;

}