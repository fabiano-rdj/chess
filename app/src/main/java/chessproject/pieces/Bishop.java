
package chessproject.pieces;

import chessproject.model.PieceColor;
import chessproject.model.Position;

/**
 * Represents a bishop in the chess game.
 *
 * Bishops move diagonally across the board.
 */
public class Bishop extends Piece {

    /**
     * Creates a bishop with the specified color.
     *
     * @param color WHITE or BLACK
     */
    public Bishop(PieceColor color) {
        super(color);
    }

    /**
     * Returns the name of this piece.
     *
     * @return Bishop
     */
    @Override
    public String getName() {
        return "Bishop";
    }

    /**
     * Returns the symbol for this bishop.
     *
     * @return B for white, b for black
     */
    @Override
    public char getSymbol() {
        return getColor() == PieceColor.WHITE ? 'B' : 'b';
    }

    /**
     * Checks whether a move follows the bishop's
     * diagonal movement pattern.
     *
     * Does not check whether another piece blocks
     * the path or occupies the destination.
     *
     * @param from starting position
     * @param to destination position
     * @return true if the movement pattern is valid
     */
    @Override
    public boolean isValidMove(Position from, Position to) {

        if (from == null || to == null) {
            return false;
        }

        int rowDifference = Math.abs(
            to.getRow() - from.getRow()
        );

        int colDifference = Math.abs(
            to.getColumn() - from.getColumn()
        );

        // A piece cannot stay on the same square.
        if (rowDifference == 0 && colDifference == 0) {
            return false;
        }

        // A diagonal move changes rows and columns equally.
        return rowDifference == colDifference;
    }
}
