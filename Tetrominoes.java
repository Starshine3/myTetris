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

    public int[][] getRotatedForm() {
        int[][] temp = new int[_width][_height];        //get rotated form
        for (int i = _width - 1; i >= 0; i--) {
            for (int j = 0; j < _height; j++) {
                int copy = _board.getTetrominoID(_currTopRow + j, _currColLeft + i);
                if (copy == _id || copy == 0) {
                    temp[_width - i - 1][j] = copy;
                }
            }
        }
        return temp;
    }

    public void clearOld() {
        for (int i = 0; i < _height; i++) {     //clear old
            for (int j = 0; j < _width; j++) {
                int paste = _board.getTetrominoID(_currTopRow + i, _currColLeft + j);
                if (paste == _id) {
                    _board.clearTetromino(_currTopRow + i, _currColLeft + j);
                }
            }
        }
    }

    /* Rotate this tetromino in clockwise orientation,  //get the rotated form -> clear original -> find way to fit copy -> place new
    /* if possible. */
    public void rotate() {
        int[][] temp = getRotatedForm(); //get rotated form
        clearOld();
        swapHW();
        int adjustL = 0, adjustU = 0;       //find way to fit (keep currtoprow)
        while (_currColLeft + adjustL < 0) {
            adjustL--;
        } while (_currColLeft + _width - adjustL > _boardCols) {
            adjustL++;
        }
        boolean cont = false;
        while (true) {
            for (int i = 0; i < _height; i++) {
                for (int j = 0; j < _width; j++) {
                    int paste = _board.getTetrominoID(_currTopRow - adjustU + i, _currColLeft - adjustL + j);
                    if (paste != _id && paste != 0){
                        cont = true;
                        continue;
                    }
                }
            }
            if (!cont) {
                break;
            } else {
                if (adjustU < _currTopRow) {
                    adjustU++;
                    cont = false;
                } else {
                    temp = getRotatedForm();
                    swapHW();
                    adjustU = 0;
                    adjustL = 0;
                    break;
                }

            }
        }
               //place new
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                int copy = temp[i][j];
                if (copy == _id) {
                    _board.placeTetromino(copy, _type, _currTopRow - adjustU + i, _currColLeft - adjustL + j);
                } else if (copy == 0) {
                    _board.clearTetromino(_currTopRow - adjustU + i, _currColLeft - adjustL + j);
                }
            }
        }
        _currTopRow -= adjustU;
        _currColLeft -= adjustL;
    }

    public void swapHW(){
        int temp = _height;
        _height = _width;
        _width = temp;
        if (_currTopRow != 0) {
            _currTopRow += _width - _height;
        }
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
        if ((thisCol == 0 && colAway == -1)
            || (thisCol == _boardCols -1 && colAway == 1)){
            return false;
        }
        int toMove = 0, rows = 0, col = 0;
        while (rows < _height && col < _width) {
            if (_board.getTetrominoID(_currTopRow + rows, thisCol - (col * colAway)) == _id) {
                toMove++;
                if (_board.getTetrominoID(_currTopRow + rows, thisCol - ((col - 1) * colAway)) == 0) {
                    toMove--;
                }
                rows++;
                col = 0;
            } else {
                col++;
            }
        }
        return toMove == 0;
    }

    public boolean checkIfAtBottom() {
        if (_currTopRow + _height >= _boardRows) {
            return true;
        }
        int toMove = 0, rows = 0, col = 0;
        while (rows < _height && col < _width) {
            if (_board.getTetrominoID(_currTopRow + _height - 1 - rows, _currColLeft + col) == _id) {
                toMove++;
                if (_board.getTetrominoID(_currTopRow + _height - rows, _currColLeft + col) == 0) {
                    toMove--;
                }

                col++;
                rows = 0;
            } else {
                rows++;
            }
        }
        return toMove != 0;
    }

    /* Move this tetromino 1 block to the left, right,
    /* or down, if possible. */
    public void move(char direction) {
        assert (canMove(direction));
        if (direction == 'l') {
            moveHorizontal(true);
        } else if (direction == 'r') {
            moveHorizontal(false);
        } else if (direction == 'd') {
            moveDown();
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
        while (!checkIfAtBottom()) {
            move('d');
        }
    }

    public boolean isAtBottom() {
        return _atBottom;
    }

    //Used for clearing lines; increase currTopRow by 1
    public void adjustTop() {
        _currTopRow += 1;
    }

    public int getID() {return _id;}

    public int getType() {return _type;}

    public int getTopRow() {return _currTopRow;}

    public int getHeight() {return _height;}

    public int getWidth() {return _width;}

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

    private final Board _board;
    private int _boardRows;
    private int _boardCols;
}