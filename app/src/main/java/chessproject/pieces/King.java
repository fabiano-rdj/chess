
package chessproject.pieces;

import chessproject.model.PieceColor;
import chessproject.model.Position;

/**
 * Represents a king in the chess game.
 *
 * Kings normally move one square in any direction.
 */
public class King extends Piece {

    /**
     * Creates a king with the specified color.
     *
     * @param color WHITE or BLACK
     */
    public King(PieceColor color) {
        super(color);
    }

    /**
     * Returns the name of this piece.
     *
     * @return King
     */
    @Override
    public String getName() {
        return "King";
    }

    /**
     * Returns the symbol for this king.
     *
     * @return K for white, k for black
     */
    @Override
    public char getSymbol() {
        return getColor() == PieceColor.WHITE ? 'K' : 'k';
    }

    /**
     * Checks the king's normal movement pattern.
     *
     * This does not check whether the destination
     * is attacked, occupied by a friendly piece,
     * or part of a castling move.
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

        // The king cannot remain on the same square.
        if (rowDifference == 0 && colDifference == 0) {
            return false;
        }

        // The king can move at most one square
        // horizontally, vertically, or diagonally.
        return rowDifference <= 1 && colDifference <= 1;
    }
}
