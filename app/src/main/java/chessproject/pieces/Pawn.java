
package chessproject.pieces;

import chessproject.model.PieceColor;
import chessproject.model.Position;

/**
 * Represents a pawn in the chess game.
 *
 * Pawns move forward and capture diagonally.
 */
public class Pawn extends Piece {

    /**
     * Creates a pawn with the specified color.
     *
     * @param color WHITE or BLACK
     */
    public Pawn(PieceColor color) {
        super(color);
    }

    /**
     * Returns the name of the piece.
     *
     * @return Pawn
     */
    @Override
    public String getName() {
        return "Pawn";
    }

    /**
     * Returns the symbol for the pawn.
     *
     * @return P for white, p for black
     */
    @Override
    public char getSymbol() {
        return getColor() == PieceColor.WHITE ? 'P' : 'p';
    }

    /**
     * Checks the pawn's basic movement pattern.
     *
     * This does not check whether squares are occupied.
     * Diagonal moves are only capture candidates.
     *
     * @param from starting position
     * @param to destination position
     * @return true for a permitted movement pattern
     */
    @Override
    public boolean isValidMove(Position from, Position to) {

        if (from == null || to == null) {
            return false;
        }

        int rowDifference = to.getRow() - from.getRow();
        int colDifference = to.getColumn() - from.getColumn();

        int direction;
        int startingRow;

        if (getColor() == PieceColor.WHITE) {
            direction = -1;
            startingRow = 6;
        } else {
            direction = 1;
            startingRow = 1;
        }

        // Move one square forward.
        if (colDifference == 0 &&
            rowDifference == direction) {
            return true;
        }

        // Move two squares from the starting row.
        if (colDifference == 0 &&
            from.getRow() == startingRow &&
            rowDifference == 2 * direction) {
            return true;
        }

        // Diagonal capture movement pattern.
        if (Math.abs(colDifference) == 1 &&
            rowDifference == direction) {
            return true;
        }

        return false;
    }
}
