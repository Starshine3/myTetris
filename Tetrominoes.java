public class Tetrominoes {

    /* Create a new tetromino with the given id
    /* on the given board . */
    public Tetrominoes(int id, Board board) {
        _id = id;
        _board = board;
        _atBottom = false;
        getBoardInfo();
        setShape(_id);
        _currRow = 0;
    }

    public void setShape(int id) {
        switch (id) {
            case 1: _height = 1;
                    _width = 4;
                    _shape = new int[_height][_width];
                    _shape[0][0] = 1;
                    _shape[0][1] = 1;
                    _shape[0][2] = 1;
                    _shape[0][3] = 1;
                    _currColLeft = _boardWidth / 2 - 2;
                    break;
            case 2: _height = 2;
                    _width = 3;
                    _shape = new int[_height][_width];
                    _shape[0][0] = 1;
                    _shape[1][0] = 1;
                    _shape[1][1] = 1;
                    _shape[1][2] = 1;
                    _currColLeft = _boardWidth / 2 - 1;
                    break;
            case 3: _height = 2;
                    _width = 3;
                    _shape = new int[_height][_width];
                    _shape[0][2] = 1;
                    _shape[1][0] = 1;
                    _shape[1][1] = 1;
                    _shape[1][2] = 1;
                    _currColLeft = _boardWidth / 2 - 1;
                    break;
            case 4: _height = 2;
                    _width = 2;
                    _shape = new int[_height][_width];
                    _shape[0][0] = 1;
                    _shape[0][1] = 1;
                    _shape[1][0] = 1;
                    _shape[1][1] = 1;
                    _currColLeft = _boardWidth / 2 - 1;
                    break;
            case 5: _height = 2;
                    _width = 3;
                    _shape = new int[_height][_width];
                    _shape[0][1] = 1;
                    _shape[0][2] = 1;
                    _shape[1][0] = 1;
                    _shape[1][1] = 1;
                    _currColLeft = _boardWidth / 2 - 1;
                    break;
            case 6: _height = 2;
                    _width = 3;
                    _shape = new int[_height][_width];
                    _shape[0][1] = 1;
                    _shape[1][0] = 1;
                    _shape[1][1] = 1;
                    _shape[1][2] = 1;
                    _currColLeft = _boardWidth / 2 - 1;
                    break;
            case 7: _height = 2;
                    _width = 3;
                    _shape = new int[_height][_width];
                    _shape[0][0] = 1;
                    _shape[0][1] = 1;
                    _shape[1][1] = 1;
                    _shape[1][2] = 1;
                    _currColLeft = _boardWidth / 2 - 1;
                    break;
        }
    }

    public void getBoardInfo() {
        _boardWidth = _board.getWidth();
        _boardHeight = _board.getHeight();
    }

    /* Check whether this tetromino can rotate
    /* in clockwise orientation. */
    public boolean canRotate() {
        return true;
    }

    /* Rotate this tetromino in clockwise orientation,
    /* if possible. */
    public void rotate() {

    }

    /* Check whether this tetromino can move in the
    /* given direction. */
    public boolean canMove(char direction) {
        switch (direction) {
            case 'l': return _currColLeft - 1 >= 0;
            case 'r': return _currColLeft + 1 < _boardWidth;
            case 'd': return !_atBottom;
        }
        return false;
    }

    /* Move this tetromino 1 block to the left, right,
    /* or down, if possible. */
    public void move(char direction) {
        if (canMove(direction)) {

        }
    }

    public void hardDrop() {

    }

    /* This tetromino's ID number.*/
    private int _id;
    private boolean _atBottom;
    private int[][] _shape;
    private int _currRow;
    private int _currColLeft;
    private int _height;
    private int _width;

    private Board _board;
    private int _boardWidth;
    private int _boardHeight;
    //private Color _color;
}