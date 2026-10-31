
package chessproject.pieces;

import chessproject.model.PieceColor;
import chessproject.model.Position;

/**
 * Represents a knight in the chess game.
 *
 * Knights move in an L-shaped pattern:
 * two squares in one direction and
 * one square in the other direction.
 */
public class Knight extends Piece {

    /**
     * Creates a knight with the specified color.
     *
     * @param color WHITE or BLACK
     */
    public Knight(PieceColor color) {
        super(color);
    }

    /**
     * Returns the name of this piece.
     *
     * @return Knight
     */
    @Override
    public String getName() {
        return "Knight";
    }

    /**
     * Returns the symbol for this knight.
     *
     * @return N for white, n for black
     */
    @Override
    public char getSymbol() {
        return getColor() == PieceColor.WHITE ? 'N' : 'n';
    }

    /**
     * Checks whether a move follows the knight's
     * L-shaped movement pattern.
     *
     * This does not check whether the destination
     * contains a friendly or enemy piece.
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

        return (rowDifference == 2 && colDifference == 1)
            || (rowDifference == 1 && colDifference == 2);
    }
}
