
package chessproject.pieces;

import chessproject.model.PieceColor;
import chessproject.model.Position;

/**
 * Represents a queen in the chess game.
 *
 * Queens move horizontally, vertically,
 * or diagonally across the board.
 */
public class Queen extends Piece {

    /**
     * Creates a queen with the specified color.
     *
     * @param color WHITE or BLACK
     */
    public Queen(PieceColor color) {
        super(color);
    }

    /**
     * Returns the name of this piece.
     *
     * @return Queen
     */
    @Override
    public String getName() {
        return "Queen";
    }

    /**
     * Returns the symbol for this queen.
     *
     * @return Q for white, q for black
     */
    @Override
    public char getSymbol() {
        return getColor() == PieceColor.WHITE ? 'Q' : 'q';
    }

    /**
     * Checks the queen's basic movement pattern.
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

        // A queen cannot remain on the same square.
        if (rowDifference == 0 && colDifference == 0) {
            return false;
        }

        // Horizontal or vertical movement.
        boolean straightMove =
            rowDifference == 0 || colDifference == 0;

        // Diagonal movement.
        boolean diagonalMove =
            rowDifference == colDifference;

        // The queen can use either movement pattern.
        return straightMove || diagonalMove;
    }
}
