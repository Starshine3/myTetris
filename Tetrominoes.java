import java.awt.*;

public class Tetrominoes {

    /* Create a new tetromino with the given id
    /* on the given board . */
    public Tetrominoes(int id, int type, Board board) {
        _id = id;
        _type = type;
        _board = board;
        getBoardInfo();
        newTetromino(id, type);
        _side = Math.max(_height, _width);
        _currTopRow = 0;
        _atBottom = false;
    }

    public void newTetromino(int id, int type) {
        switch (type) {
            case 1:
                _height = 1;
                _width = 4;
                _currColLeft = _boardCols / 2 - 2;
                for (int i = 0; i < _width; i++) {
                    _board.placeTetromino(id, type, _currTopRow, _currColLeft + i);
                }
                break;
            case 2:
                _height = 2;
                _width = 3;
                _currColLeft = _boardCols / 2 - 1;
                _board.placeTetromino(id, type, _currTopRow, _currColLeft);
                for (int i = 0; i < _width; i++) {
                    _board.placeTetromino(id, type, _currTopRow + 1, _currColLeft + i);
                }
                break;
            case 3:
                _height = 2;
                _width = 3;
                _currColLeft = _boardCols / 2 - 1;
                _board.placeTetromino(id, type, _currTopRow, _currColLeft + 2);
                for (int i = 0; i < _width; i++) {
                    _board.placeTetromino(id, type, _currTopRow + 1, _currColLeft + i);
                }
                break;
            case 4:
                _height = 2;
                _width = 2;
                _currColLeft = _boardCols / 2 - 1;
                for (int i = 0; i < _height; i++) {
                    for (int j = 0; j < _width; j++) {
                        _board.placeTetromino(id, type, _currTopRow + i, _currColLeft + j);
                    }
                }
                break;
            case 5:
                _height = 2;
                _width = 3;
                _currColLeft = _boardCols / 2 - 1;
                _board.placeTetromino(id, type, _currTopRow, _currColLeft + 1);
                _board.placeTetromino(id, type, _currTopRow, _currColLeft + 2);
                _board.placeTetromino(id, type, _currTopRow + 1, _currColLeft);
                _board.placeTetromino(id, type, _currTopRow + 1, _currColLeft + 1);
                break;
            case 6:
                _height = 2;
                _width = 3;
                _currColLeft = _boardCols / 2 - 1;
                _board.placeTetromino(id, type, _currTopRow, _currColLeft + 1);
                for (int i = 0; i < _width; i++) {
                    _board.placeTetromino(id, type, _currTopRow + 1, _currColLeft + i);
                }
                break;
            case 7:
                _height = 2;
                _width = 3;
                _currColLeft = _boardCols / 2 - 1;
                _board.placeTetromino(id, type, _currTopRow, _currColLeft);
                _board.placeTetromino(id, type, _currTopRow, _currColLeft + 1);
                _board.placeTetromino(id, type, _currTopRow + 1, _currColLeft + 1);
                _board.placeTetromino(id, type, _currTopRow + 1, _currColLeft + 2);
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
        if (canRotate()) {
            int[][] temp = new int[_side][_side];
            for (int i = _side - 1; i >= 0; i--) {
                for (int j = 0; j < _side; j++) {
                    if (_board.getTetrominoID(_currTopRow + j, _currColLeft + i) == _id) {
                        temp[_side - i - 1][j] = _id;
                    }
                }
            }
            temp = fixEmpty(temp);
            for (int i = 0; i < _side; i++) {
                for (int j = 0; j < _side; j++) {
                    _board.placeTetromino(temp[i][j], _type, _currTopRow + i, _currColLeft + j);
                }
            }
            swapHW();
        }
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
        if (checkIfAtBottom()) {
            _atBottom = true;
        } else {
            _atBottom = false;
        }
        if (direction == 'l' && _currColLeft - 1 >= 0) {
            return checkIfEmptyNeighbor(_currColLeft, -1);
        } else if (direction == 'r' && _currColLeft + _width < _boardCols) {
            return checkIfEmptyNeighbor(_currColLeft + _width - 1, 1);
        } else if (direction == 'd') {
            return !isAtBottom();
        } else {
            return false;
        }
    }

    // thisCol: index of this col       //canMoveHorizontal
    // colAway: how far is neighborCol away from thisCol
    public boolean checkIfEmptyNeighbor(int thisCol, int colAway) {
        int toMove = 0;
        for (int i = 0; i < _height; i++) {
            if (_board.getTetrominoID(_currTopRow + i, thisCol) == _id) {
                toMove++;
                if (_board.getTetrominoID(_currTopRow + i, thisCol + colAway) == 0) {
                    toMove--;
                }
            } else if (_board.getTetrominoID(_currTopRow + i, thisCol - colAway) == _id) {
                toMove++;
                if (_board.getTetrominoID(_currTopRow + i, thisCol) == 0) {
                    toMove--;
                }
            }
        }
        return toMove == 0;
    }

    public boolean checkIfAtBottom() {
        if (_currTopRow + _height >= _boardRows) {
            return true;
        }
        int toMove = 0;
        for (int j = 0; j < _height; j++) {
            for (int i = 0; i < _width; i++) {
                if (_board.getTetrominoID(_currTopRow + _height - 1, _currColLeft + i) != 0) {
                    toMove++;
                    if (_board.getTetrominoID(_currTopRow + _height, _currColLeft + i) == 0) {
                        toMove--;
                    }
                } else if (_board.getTetrominoID(_currTopRow + _height - 2, _currColLeft + i) != 0) {
                    toMove++;
                    if (_board.getTetrominoID(_currTopRow + _height - 1, _currColLeft + i) == 0) {
                        toMove--;
                    }
                }
            }
        }
        return toMove != 0;
    }

    /* Move this tetromino 1 block to the left, right,
    /* or down, if possible. */
    public void move(char direction) {
        if (canMove(direction)) {
            if (direction == 'l') {
                moveHorizontal(true);
            } else if (direction == 'r') {
                moveHorizontal(false);
            } else if (direction == 'd') {
                moveDown();
            }
        }
    }

    public void moveHorizontal(boolean left) {
        int direction = 1;
        if (left) {
            direction = -1;
        }
        for (int i = 0; i < _height; i++) {
            int start = _width - 1;
            int end = -1;
            if (left) {
                start = 0;
                end = _width;
            }
            while (start != end) {
                int copy = _board.getTetrominoID(_currTopRow + i, _currColLeft + start);
                int paste = _board.getTetrominoID(_currTopRow + i, _currColLeft + start + direction);
                if ((copy == _id && paste == 0)) {
                    _board.placeTetromino(copy, _type, _currTopRow + i, _currColLeft + start + direction);
                    _board.clearTetromino(_currTopRow + i, _currColLeft + start);

                } else if ((copy == _id && paste == _id)) {
                    _board.clearTetromino(_currTopRow + i, _currColLeft + start);
                }
                start -= direction;
            }
        }
        _currColLeft = _currColLeft + direction;
    }

    public void moveDown() {
        for (int i = _height - 1; i >= 0; i--) {
            for (int j = 0; j < _width; j++) {
                int copy = _board.getTetrominoID(_currTopRow + i, _currColLeft + j);
                int paste = _board.getTetrominoID(_currTopRow + i + 1, _currColLeft + j);
                if ((copy == _id && paste == 0)) {
                    _board.placeTetromino(copy, _type, _currTopRow + i + 1, _currColLeft + j);
                    _board.clearTetromino(_currTopRow + i, _currColLeft + j);
                } else if ((copy == _id && paste == _id)) {
                    _board.clearTetromino(_currTopRow + i, _currColLeft + j);
                }
            }
        }
    _currTopRow++;
    }

    public void hardDrop() {
        while (!_atBottom) {
            move('d');
        }
    }

    public boolean isAtBottom() {
        return _atBottom;
    }

    public int getID() {return _id;}

    public int getType() {return _type;}

    private int _id;
    private int _type;
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