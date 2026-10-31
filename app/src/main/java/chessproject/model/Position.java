package chessproject.model;

/** A square on the chessboard, using zero-based rows and columns. */
public class Position {
    private final int row;
    private final int column;

    public Position(int row, int column) {
        if (row < 0 || row > 7 || column < 0 || column > 7) {
            throw new IllegalArgumentException("Position must be between 0 and 7.");
        }
        this.row = row;
        this.column = column;
    }

    public int getRow() { return row; }
    public int getColumn() { return column; }

    @Override
    public String toString() {
        char file = (char) ('A' + column);
        int rank = 8 - row;
        return "" + file + rank;
    }
    /** Converts chess notation such as E2 into a board Position. */
    public static Position parse(String square) {
        if (square == null || !square.toUpperCase().matches("[A-H][1-8]")) {
            throw new IllegalArgumentException("Invalid square: " + square);
        }
        String text = square.toUpperCase();
        int column = text.charAt(0) - 'A';
        int row = 8 - (text.charAt(1) - '0');
        return new Position(row, column);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Position)) return false;
        Position position = (Position) other;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return 31 * row + column;
    }
}
