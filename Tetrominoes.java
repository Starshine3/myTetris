import java.awt.*;

public class Tetrominoes {

    /* Create a new tetromino with the given id
    /* on the given board . */
    public Tetrominoes(int id, Board board) {
        _id = id;
        _board = board;
        getBoardInfo();
        newTetromino(_id);
        _side = Math.max(_height, _width);
        _currTopRow = 0;
        _atBottom = false;
    }

    public void newTetromino(int id) {
        switch (id) {
            case 1:
                _height = 1;
                _width = 4;
                _currColLeft = _boardCols / 2 - 2;
                for (int i = 0; i < _width; i++) {
                    _board.placeTetromino(id, _currTopRow, _currColLeft + i);
                }
                break;
            case 2:
                _height = 2;
                _width = 3;
                _currColLeft = _boardCols / 2 - 1;
                _board.placeTetromino(id, _currTopRow, _currColLeft);
                for (int i = 0; i < _width; i++) {
                    _board.placeTetromino(id, _currTopRow + 1, _currColLeft + i);
                }
                break;
            case 3:
                _height = 2;
                _width = 3;
                _currColLeft = _boardCols / 2 - 1;
                _board.placeTetromino(id, _currTopRow, _currColLeft + 2);
                for (int i = 0; i < _width; i++) {
                    _board.placeTetromino(id, _currTopRow + 1, _currColLeft + i);
                }
                break;
            case 4:
                _height = 2;
                _width = 2;
                _currColLeft = _boardCols / 2 - 1;
                for (int i = 0; i < _height; i++) {
                    for (int j = 0; j < _width; j++) {
                        _board.placeTetromino(id, _currTopRow + i, _currColLeft + j);
                    }
                }
                break;
            case 5:
                _height = 2;
                _width = 3;
                _currColLeft = _boardCols / 2 - 1;
                _board.placeTetromino(id, _currTopRow, _currColLeft + 1);
                _board.placeTetromino(id, _currTopRow, _currColLeft + 2);
                _board.placeTetromino(id, _currTopRow + 1, _currColLeft);
                _board.placeTetromino(id, _currTopRow + 1, _currColLeft + 1);
                break;
            case 6:
                _height = 2;
                _width = 3;
                _currColLeft = _boardCols / 2 - 1;
                _board.placeTetromino(id, _currTopRow, _currColLeft + 1);
                for (int i = 0; i < _width; i++) {
                    _board.placeTetromino(id, _currTopRow + 1, _currColLeft + i);
                }
                break;
            case 7:
                _height = 2;
                _width = 3;
                _currColLeft = _boardCols / 2 - 1;
                _board.placeTetromino(id, _currTopRow, _currColLeft);
                _board.placeTetromino(id, _currTopRow, _currColLeft + 1);
                _board.placeTetromino(id, _currTopRow + 1, _currColLeft + 1);
                _board.placeTetromino(id, _currTopRow + 1, _currColLeft + 2);
                break;
        }
    }

    public void getBoardInfo() {
        _boardRows = _board.getRows();
        _boardCols = _board.getCols();
    }

    /* Check whether this tetromino can rotate
    /* in clockwise orientation. */
    public boolean canRotate() {
        return true;
    }

    /* Rotate this tetromino in clockwise orientation,
    /* if possible. */
    public void rotate() {
        int[][] temp = new int[_side][_side];
        for (int i = _side - 1; i >= 0; i--) {
            for (int j = 0; j < _side; j++) {
                temp[_side - i - 1][j]
                        = _board.getTetrominoID(_currTopRow + j, _currColLeft + i);
            }
        }
        temp = fixEmpty(temp);
        for (int i = 0; i < _side; i++) {
            for (int j = 0; j < _side; j++) {
                _board.placeTetromino(temp[i][j], _currTopRow + i, _currColLeft + j);
            }
        }
        swapHW();

    }

    public int[][] fixEmpty(int[][] grid) {
        empty = new int[_side];
        if (emptyRow(grid)) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (i >= grid.length - 1) {
                        grid[i][j] = 0;
                    } else {
                        grid[i][j] = grid[i + 1][j];
                    }
                }
            }
            return grid;
        } else if (_id == 1) {
            int col = 0;
            for (int i = 0; i < grid[0].length; i++) {
                if (grid[0][i] != 0) {
                    col = i;
                    break;
                }
            }
            for (int j = 0; j < grid.length; j++) {
                grid[j][col] = 0;
                grid[j][_side/2] = _id;
            }
            return grid;
        } else {
            return grid;
        }
    }

    public boolean emptyRow(int[][] grid) {
        boolean isEmpty = true;
        for (int i = 0; i < _side; i++) {
            if (empty[i] != grid[0][i]) {
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    public void swapHW(){
        int temp = _height;
        _height = _width;
        _width = temp;
    }

    /* Check whether this tetromino can move in the
    /* given direction. */
    public boolean canMove(char direction) {
        if (atBottom()) {
            return false;
        }
        if (direction == 'l' && _currColLeft - 1 >= 0) {
            if (_currColLeft - 1 == 0) {
                return true;
            }
            return checkIfEmptyCol(_currColLeft - 1, _height, _currTopRow);
        } else if (direction == 'r' && _currColLeft + _width + 1 <= _boardCols) {
            if (_currColLeft + _width + 1 == _boardCols) {
                return true;
            }
            return checkIfEmptyCol(_currColLeft + _width + 1, _height, _currTopRow);
        } else if (direction == 'd') {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfEmptyCol(int col, int height, int row) {
        for (int i = 0; i < height; i++) {
            if (_board.getTetrominoID(row + i, col) != 0) {
                return false;
            }
        }
        return true;
    }

    public void swapCol(int c1, int c2, int height, int row) {
        int[] temp = new int[height];
        for (int i = 0; i < height; i ++) {
            temp[i] = _board.getTetrominoID(row + i, c1);
            int replacement = _board.getTetrominoID(row + i, c2);
            _board.placeTetromino(replacement, row + i, c1);
        }
        for (int i = 0; i < height; i ++) {
            _board.placeTetromino(temp[i], row + i, c2);
        }
    }

    public void moveHorizontal(boolean left) {
        int direction = 1;
        if (left) {
            direction = -1;
            for (int i = 0; i < _height; i++) {
                for (int j = 0; j <= _width; j++) {
                    if (j == _width) {
                        _board.placeTetromino(0, _currTopRow + i, _currColLeft + j + direction);
                    } else {
                        int neighbor = _board.getTetrominoID(_currTopRow + i, _currColLeft + j);
                        _board.placeTetromino(neighbor, _currTopRow + i, _currColLeft + j + direction);
                    }
                }
            }
        } else {
            for (int i = 0; i < _height; i++) {
                for (int j = _width - 1; j >= -1; j--) {
                    if (j == -1) {
                        _board.placeTetromino(0, _currTopRow + i, _currColLeft + j + direction);
                    } else {
                        int neighbor = _board.getTetrominoID(_currTopRow + i, _currColLeft + j);
                        _board.placeTetromino(neighbor, _currTopRow + i, _currColLeft + j + direction);
                    }
                }
            }
        }
        _currColLeft = _currColLeft + direction;
    }

    /* Move this tetromino 1 block to the left, right,
    /* or down, if possible. */
    public void move(char direction) {
        if (canMove(direction)) {
            if (direction == 'l') {
                moveHorizontal(true);
            } else if (direction == 'r') {
                moveHorizontal(false);
            }
        }
    }

    public void hardDrop() {
        _atBottom = true;
    }

    public boolean atBottom() {
        return _atBottom;
    }

    public int getID() {return _id;}

    /* This tetromino's ID number.*/
    private int _id;
    private int _currTopRow;
    private int _currColLeft;
    private int _height;
    private int _width;
    private int _side;
    private boolean _atBottom;

    public static final Color[] tetrominoColors
            = {Color.gray, Color.cyan, Color.blue,
            Color.orange, Color.yellow, Color.green,
            Color.pink, Color.red};
    private int[] empty;

    private final Board _board;
    private int _boardRows;
    private int _boardCols;
}