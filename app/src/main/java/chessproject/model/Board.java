package chessproject.model;

import chessproject.pieces.Piece;
import chessproject.pieces.Pawn;
import chessproject.pieces.Rook;
import chessproject.pieces.Knight;
import chessproject.pieces.Bishop;
import chessproject.pieces.Queen;
import chessproject.pieces.King;

/**
 * Represents the chessboard and stores its pieces.
 */
public class Board {

    private final Piece[][] squares;

    /** Creates a new chessboard with all 32 pieces. */
    public Board() {
        squares = new Piece[8][8];
        setupPieces();
    }

    /** Places all pieces in their starting positions. */
    private void setupPieces() {
        // Black pieces: row 0 (rank 8).
        setupBackRow(0, PieceColor.BLACK);

        // Black pawns: row 1 (rank 7).
        for (int col = 0; col < 8; col++) {
            squares[1][col] = new Pawn(PieceColor.BLACK);
        }

        // White pawns: row 6 (rank 2).
        for (int col = 0; col < 8; col++) {
            squares[6][col] = new Pawn(PieceColor.WHITE);
        }

        // White pieces: row 7 (rank 1).
        setupBackRow(7, PieceColor.WHITE);
    }

    /** Places the major pieces on the first or last rank. */
    private void setupBackRow(int row, PieceColor color) {
        squares[row][0] = new Rook(color);
        squares[row][1] = new Knight(color);
        squares[row][2] = new Bishop(color);
        squares[row][3] = new Queen(color);
        squares[row][4] = new King(color);
        squares[row][5] = new Bishop(color);
        squares[row][6] = new Knight(color);
        squares[row][7] = new Rook(color);
    }

    /** Returns the piece at a position, or null if empty. */
    public Piece getPiece(Position position) {
        return squares[position.getRow()][position.getColumn()];
    }

    /**
     * Moves a piece using the basic movement pattern.
     * Step 5.1 version: destination must be empty.
     */
    public boolean movePiece(Position from, Position to) {
        Piece piece = getPiece(from);
        if (piece == null) {
            System.out.println("No piece at " + from);
            return false;
        }
        if (!piece.isValidMove(from, to)) {
            System.out.println("Invalid movement pattern.");
            return false;
        }
        if (getPiece(to) != null) {
            System.out.println("Destination is occupied.");
            return false;
        }
        if (piece instanceof Pawn && from.getColumn() != to.getColumn()) {
            System.out.println("Pawn cannot move diagonally without a capture.");
            return false;
        }
        if (!(piece instanceof Knight) && !isPathClear(from, to)) {
            System.out.println("Another piece blocks the path.");
            return false;
        }
        squares[to.getRow()][to.getColumn()] = piece;
        squares[from.getRow()][from.getColumn()] = null;
        System.out.println(piece.getName() + " moved from " + from + " to " + to);
        return true;
    }

    /**
     * Places a piece on a square. Used by the game rules and simulated moves.
     * A null piece makes the square empty.
     */
    public void setPiece(Position position, Piece piece) {
        squares[position.getRow()][position.getColumn()] = piece;
    }

    /**
     * Creates an independent 8x8 array of the current board positions.
     * Piece objects are immutable, so they can safely be shared.
     */
    public Board copy() {
        Board result = new Board();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                result.squares[row][col] = squares[row][col];
            }
        }
        return result;
    }

    /**
     * Returns true when every square strictly between from and to is empty.
     * Call this only for horizontal, vertical, or diagonal movement.
     */
    public boolean isPathClear(Position from, Position to) {
        int rowStep = Integer.compare(to.getRow(), from.getRow());
        int colStep = Integer.compare(to.getColumn(), from.getColumn());
        int row = from.getRow() + rowStep;
        int col = from.getColumn() + colStep;
        while (row != to.getRow() || col != to.getColumn()) {
            if (squares[row][col] != null) {
                return false;
            }
            row += rowStep;
            col += colStep;
        }
        return true;
    }

    /** Displays the chessboard in the console. */
    public void display() {
        System.out.println();
        System.out.println("    A  B  C  D  E  F  G  H");
        for (int row = 0; row < 8; row++) {
            System.out.print((8 - row) + "   ");
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece == null) {
                    System.out.print(".  ");
                } else {
                    System.out.print(piece.getSymbol() + "  ");
                }
            }
            System.out.println(" " + (8 - row));
        }
        System.out.println("    A  B  C  D  E  F  G  H");
        System.out.println();
    }
}
