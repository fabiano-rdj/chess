
package chessproject.pieces;

import chessproject.model.PieceColor;
import chessproject.model.Position;

/**
 * Represents a rook in the chess game.
 *
 * Rooks move horizontally or vertically.
 */
public class Rook extends Piece {

    /**
     * Creates a rook with the specified color.
     *
     * @param color WHITE or BLACK
     */
    public Rook(PieceColor color) {
        super(color);
    }

    /**
     * Returns the name of this piece.
     *
     * @return Rook
     */
    @Override
    public String getName() {
        return "Rook";
    }

    /**
     * Returns the symbol for this rook.
     *
     * @return R for white, r for black
     */
    @Override
    public char getSymbol() {
        return getColor() == PieceColor.WHITE ? 'R' : 'r';
    }

    /**
     * Checks the rook's basic movement pattern.
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

        int rowDifference = to.getRow() - from.getRow();
        int colDifference = to.getColumn() - from.getColumn();

        // A rook cannot remain on the same square.
        if (rowDifference == 0 && colDifference == 0) {
            return false;
        }

        // Vertical movement: same column.
        if (colDifference == 0) {
            return true;
        }

        // Horizontal movement: same row.
        if (rowDifference == 0) {
            return true;
        }

        // Diagonal or other movement is invalid.
        return false;
    }
}
