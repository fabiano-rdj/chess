
package chessproject.pieces;

import chessproject.model.PieceColor;
import chessproject.model.Position;

/**
 * Represents a general chess piece.
 *
 * This is the parent class for Pawn, Rook, Knight,
 * Bishop, Queen, and King.
 */
public abstract class Piece {

    private final PieceColor color;

    /**
     * Creates a chess piece with a specific color.
     *
     * @param color WHITE or BLACK
     */
    public Piece(PieceColor color) {
        if (color == null) {
            throw new IllegalArgumentException(
                "Piece color cannot be null."
            );
        }

        this.color = color;
    }

    /**
     * Returns the color of this piece.
     *
     * @return the piece color
     */
    public PieceColor getColor() {
        return color;
    }

    /**
     * Returns the name of this piece.
     *
     * @return piece name
     */
    public abstract String getName();

    /**
     * Returns the character used to display the piece.
     *
     * @return piece symbol
     */
    public abstract char getSymbol();

    /**
     * Checks whether a movement follows the piece's
     * basic movement pattern.
     *
     * Board occupancy, blocked paths, and captures
     * will be checked separately later.
     *
     * @param from starting position
     * @param to destination position
     * @return true if the movement pattern is valid
     */
    public abstract boolean isValidMove(
        Position from,
        Position to
    );

    /**
     * Returns the symbol as a String.
     *
     * @return piece symbol as text
     */
    @Override
    public String toString() {
        return String.valueOf(getSymbol());
    }
}
