package chessproject.console;

import chessproject.model.Board;
import chessproject.model.PieceColor;
import chessproject.model.Position;
import chessproject.pieces.Piece;
import chessproject.pieces.Pawn;
import chessproject.pieces.Rook;
import chessproject.pieces.Knight;
import chessproject.pieces.Bishop;
import chessproject.pieces.Queen;
import chessproject.pieces.King;
import chessproject.service.Game;
import java.util.Scanner;

/** Console entry point and lesson-by-lesson tests. */
public class ChessApp {

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("--play")) {
            playChess();
            return;
        }
        System.out.println("Welcome to the Java Chess Project!");

        System.out.println("=== PIECE COLOR TESTS ===");
        System.out.println("White opposite: " + PieceColor.WHITE.opposite());
        System.out.println("Black opposite: " + PieceColor.BLACK.opposite());

        System.out.println("=== POSITION TESTS ===");
        Position e2 = new Position(6, 4);
        Position e4 = new Position(4, 4);
        System.out.println("Starting position: " + e2);
        System.out.println("Destination position: " + e4);

        System.out.println("=== BOARD SETUP TESTS ===");
        Board board = new Board();
        board.display();
        check("E1 is white King", isPiece(board, 7, 4, "King", PieceColor.WHITE));
        check("D8 is black Queen", isPiece(board, 0, 3, "Queen", PieceColor.BLACK));
        check("E2 is white Pawn", isPiece(board, 6, 4, "Pawn", PieceColor.WHITE));
        check("E7 is black Pawn", isPiece(board, 1, 4, "Pawn", PieceColor.BLACK));
        check("E4 is empty", board.getPiece(new Position(4, 4)) == null);

        int whiteCount = 0;
        int blackCount = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(new Position(row, col));
                if (piece != null && piece.getColor() == PieceColor.WHITE) whiteCount++;
                if (piece != null && piece.getColor() == PieceColor.BLACK) blackCount++;
            }
        }
        check("16 white pieces", whiteCount == 16);
        check("16 black pieces", blackCount == 16);
        check("32 pieces total", whiteCount + blackCount == 32);

        System.out.println("=== PIECE MOVEMENT PATTERN TESTS ===");
        Piece whitePawn = new Pawn(PieceColor.WHITE);
        Piece blackPawn = new Pawn(PieceColor.BLACK);
        check("White Pawn E2 to E4", whitePawn.isValidMove(new Position(6, 4), new Position(4, 4)));
        check("White Pawn cannot move backward", !whitePawn.isValidMove(new Position(6, 4), new Position(7, 4)));
        check("Black Pawn E7 to E5", blackPawn.isValidMove(new Position(1, 4), new Position(3, 4)));

        Piece rook = new Rook(PieceColor.WHITE);
        check("Rook straight", rook.isValidMove(new Position(4, 0), new Position(4, 5)));
        check("Rook not diagonal", !rook.isValidMove(new Position(4, 0), new Position(3, 1)));

        Piece knight = new Knight(PieceColor.WHITE);
        check("Knight L shape", knight.isValidMove(new Position(7, 1), new Position(5, 2)));
        check("Knight not straight", !knight.isValidMove(new Position(7, 1), new Position(5, 1)));

        Piece bishop = new Bishop(PieceColor.WHITE);
        check("Bishop diagonal", bishop.isValidMove(new Position(4, 4), new Position(1, 1)));
        check("Bishop not straight", !bishop.isValidMove(new Position(4, 4), new Position(4, 1)));

        Piece queen = new Queen(PieceColor.WHITE);
        check("Queen diagonal", queen.isValidMove(new Position(4, 4), new Position(2, 2)));
        check("Queen straight", queen.isValidMove(new Position(4, 4), new Position(4, 7)));
        check("Queen not Knight move", !queen.isValidMove(new Position(4, 4), new Position(2, 5)));

        Piece king = new King(PieceColor.WHITE);
        check("King one square", king.isValidMove(new Position(4, 4), new Position(3, 5)));
        check("King not two squares", !king.isValidMove(new Position(4, 4), new Position(2, 4)));

        System.out.println("=== FIRST CHESS MOVE TEST ===");
        Position moveFrom = new Position(6, 4);
        Position moveTo = new Position(4, 4);
        System.out.println("Board before the move:");
        board.display();
        boolean moveSuccessful = board.movePiece(moveFrom, moveTo);
        check("Move successful", moveSuccessful);
        System.out.println("Board after the move:");
        board.display();
        check("E2 is empty", board.getPiece(moveFrom) == null);
        check("E4 contains white Pawn", isPiece(board, 4, 4, "Pawn", PieceColor.WHITE));

        System.out.println("=== INVALID MOVE TESTS ===");
        Board testBoard = new Board();
        boolean emptySquareMove = testBoard.movePiece(new Position(4, 4), new Position(3, 4));
        check("Empty square rejected", !emptySquareMove);
        boolean backwardMove = testBoard.movePiece(new Position(6, 4), new Position(7, 4));
        check("Backward move rejected", !backwardMove);
        boolean occupiedMove = testBoard.movePiece(new Position(7, 0), new Position(6, 0));
        check("Occupied destination rejected", !occupiedMove);
        boolean diagonalRookMove = testBoard.movePiece(new Position(7, 0), new Position(6, 1));
        check("Invalid Rook movement rejected", !diagonalRookMove);
        check("White Pawn remains on E2", isPiece(testBoard, 6, 4, "Pawn", PieceColor.WHITE));
        System.out.println("Board after invalid move tests:");
        testBoard.display();
    }

    /**
     * Launches the playable two-person chess game.
     * The original lesson tests above remain available with no arguments.
     */
    private static void playChess() {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== CONSOLE CHESS ===");
        System.out.println("Enter moves like E2 E4 or E2E4.");
        System.out.println("Promotion: E7 E8 Q (Q, R, B, N). Type help or quit.");
        while (true) {
            game.getBoard().display();
            System.out.println(game.getMessage());
            if (game.isOver()) break;
            System.out.print(game.getTurn() + " move > ");
            if (!scanner.hasNextLine()) break;
            String command = scanner.nextLine().trim();
            if (command.equalsIgnoreCase("quit") || command.equalsIgnoreCase("exit")) break;
            if (command.equalsIgnoreCase("help")) {
                System.out.println("Move: E2 E4; castling: E1 G1 or E1 C1 when legal.");
                System.out.println("Promotion: E7 E8 Q (or R, B, N). Type quit to exit.");
                continue;
            }
            String move = command.toUpperCase().replaceAll("\\s+", "");
            if (!move.matches("[A-H][1-8][A-H][1-8][QRBN]?")) {
                System.out.println("Invalid input. Example: E2 E4");
                continue;
            }
            Position from = Position.parse(move.substring(0, 2));
            Position to = Position.parse(move.substring(2, 4));
            char promotion = move.length() == 5 ? move.charAt(4) : 'Q';
            game.move(from, to, promotion);
        }
        System.out.println("Thanks for playing!");
    }

    /** Print the result of a single lesson test. */
    private static void check(String description, boolean passed) {
        System.out.println(description + ": " + passed);
        if (!passed) throw new AssertionError("Failed test: " + description);
    }

    /** Verify a board square contains a particular piece and color. */
    private static boolean isPiece(Board board, int row, int col,
                                   String name, PieceColor color) {
        Piece piece = board.getPiece(new Position(row, col));
        return piece != null && piece.getName().equals(name) && piece.getColor() == color;
    }
}
