package myTetris;

class Tetrominoes {

    /* Create a new tetromino with the given id. */
    public Tetrominoes(int id) {
        _id = id;

    }

    /* Check whether this tetromino can rotate
    /* in clockwise orientation. */
    public boolean canRotate() {

    }

    /* Rotate this tetromino in clockwise orientation,
    /* if possible. */
    public void rotate() {
        if (canRotate) {

        }
    }

    /* Check whether this tetromino can move in the
    /* given direction. */
    public boolean canMove(char direction) {

    }

    /* Move this tetromino 1 block to the left, right,
    /* or down, if possible. */
    public void move(char direction) {

    }

    public void hardDrop() {

    }

    /* This tetromino's ID number.*/
    private int _id;
    private Board _board;
    //private Color _color;
}