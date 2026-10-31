package chessproject.service;
import chessproject.model.*;
import chessproject.pieces.*;
/** Manages turns, legal moves, check, checkmate, and stalemate. */
public class Game {
    private final Board board = new Board();
    private PieceColor turn = PieceColor.WHITE;
    private String message = "White starts.";
    private Position enPassantTarget;
    private final boolean[][] rookMoved = new boolean[2][2];
    private final boolean[] kingMoved = new boolean[2];
    public Board getBoard() { return board; }
    public PieceColor getTurn() { return turn; }
    public String getMessage() { return message; }
    private int side(PieceColor color) { return color == PieceColor.WHITE ? 0 : 1; }
    /** Returns true if the named color's king is attacked. */
    public boolean inCheck(PieceColor color) { return inCheck(board,color); }
    private boolean inCheck(Board b, PieceColor color) {
        Position king = null;
        for (int r=0;r<8;r++) for(int c=0;c<8;c++) {
            Position p=new Position(r,c); Piece piece=b.getPiece(p);
            if (piece instanceof King && piece.getColor()==color) king=p;
        }
        if (king==null) return true;
        return attacked(b,king,color.opposite());
    }
    /** Tests whether a square is attacked by a particular player. */
    private boolean attacked(Board b, Position target, PieceColor attacker) {
        for (int r=0;r<8;r++) for(int c=0;c<8;c++) {
            Position from=new Position(r,c); Piece p=b.getPiece(from);
            if (p==null || p.getColor()!=attacker) continue;
            if (p instanceof Pawn) {
                int direction=attacker==PieceColor.WHITE?-1:1;
                if (target.getRow()-r==direction && Math.abs(target.getColumn()-c)==1) return true;
            } else if (p.isValidMove(from,target) &&
                (p instanceof Knight || p instanceof King || b.isPathClear(from,target))) return true;
        }
        return false;
    }
    private boolean isCastle(Board b, Position from, Position to, Piece p) {
        if (!(p instanceof King) || from.getRow()!=to.getRow() ||
            Math.abs(to.getColumn()-from.getColumn())!=2 || from.getColumn()!=4) return false;
        int row=p.getColor()==PieceColor.WHITE?7:0;
        if(from.getRow()!=row || kingMoved[side(p.getColor())] || inCheck(b,p.getColor())) return false;
        int direction=to.getColumn()>from.getColumn()?1:-1;
        int rookCol=direction==1?7:0;
        if(rookMoved[side(p.getColor())][direction==1?1:0]) return false;
        Piece rook=b.getPiece(new Position(row,rookCol));
        if (!(rook instanceof Rook) || rook.getColor()!=p.getColor()) return false;
        for(int c=4+direction;c!=rookCol;c+=direction)
            if(b.getPiece(new Position(row,c))!=null) return false;
        return !attacked(b,new Position(row,4+direction),p.getColor().opposite()) &&
            !attacked(b,to,p.getColor().opposite());
    }
    private boolean isEnPassant(Board b,Position from,Position to,Piece p) {
        return p instanceof Pawn && enPassantTarget!=null && to.equals(enPassantTarget) &&
            Math.abs(to.getColumn()-from.getColumn())==1 && b.getPiece(to)==null &&
            b.getPiece(new Position(from.getRow(),to.getColumn())) instanceof Pawn &&
            b.getPiece(new Position(from.getRow(),to.getColumn())).getColor()!=p.getColor();
    }
    /** Validates a move without modifying the live board. */
    public boolean isLegal(Position from, Position to) {
        if(from==null || to==null || from.equals(to)) return false;
        Piece p=board.getPiece(from), dest=board.getPiece(to);
        if(p==null || p.getColor()!=turn || (dest!=null && dest.getColor()==turn) || dest instanceof King) return false;
        boolean castle=isCastle(board,from,to,p);
        boolean ep=isEnPassant(board,from,to,p);
        if (!castle && !p.isValidMove(from,to)) return false;
        if (p instanceof Pawn && !castle) {
            int dc=to.getColumn()-from.getColumn();
            if(dc==0) {
                if(dest!=null || !board.isPathClear(from,to)) return false;
            } else if(dest==null && !ep) return false;
        } else if (!castle && !(p instanceof Knight) && !board.isPathClear(from,to)) return false;
        Board simulation=board.copy();
        simulation.setPiece(to,p); simulation.setPiece(from,null);
        if(ep) simulation.setPiece(new Position(from.getRow(),to.getColumn()),null);
        if(castle) {
            int row=from.getRow(), rookCol=to.getColumn()>from.getColumn()?7:0;
            Piece rook=simulation.getPiece(new Position(row,rookCol));
            simulation.setPiece(new Position(row,rookCol),null);
            simulation.setPiece(new Position(row,to.getColumn()>from.getColumn()?5:3),rook);
        }
        return !inCheck(simulation,turn);
    }
    /** Performs a legal move. Promotion defaults to Queen. */
    public boolean move(Position from,Position to) { return move(from,to,'Q'); }
    /** Promotion accepts Q, R, B, or N. */
    public boolean move(Position from,Position to,char promotion) {
        if (!isLegal(from,to)) { message="Illegal move. Try again."; return false; }
        Piece p=board.getPiece(from), target=board.getPiece(to);
        boolean castle=p instanceof King && Math.abs(to.getColumn()-from.getColumn())==2;
        boolean ep=isEnPassant(board,from,to,p);
        int player=side(turn);
        if(p instanceof King) kingMoved[player]=true;
        if(p instanceof Rook && from.getRow()==(player==0?7:0)) {
            if(from.getColumn()==0) rookMoved[player][0]=true;
            if(from.getColumn()==7) rookMoved[player][1]=true;
        }
        if(target instanceof Rook && to.getRow()==(turn==PieceColor.WHITE?0:7)) {
            int other=1-player;
            if(to.getColumn()==0) rookMoved[other][0]=true;
            if(to.getColumn()==7) rookMoved[other][1]=true;
        }
        board.setPiece(from,null);
        if(ep) board.setPiece(new Position(from.getRow(),to.getColumn()),null);
        if(p instanceof Pawn && (to.getRow()==0 || to.getRow()==7)) {
            switch(Character.toUpperCase(promotion)) {
                case 'R': p=new Rook(turn); break;
                case 'B': p=new Bishop(turn); break;
                case 'N': p=new Knight(turn); break;
                default: p=new Queen(turn);
            }
        }
        board.setPiece(to,p);
        if(castle) {
            int row=from.getRow(), oldCol=to.getColumn()>from.getColumn()?7:0;
            Piece rook=board.getPiece(new Position(row,oldCol));
            board.setPiece(new Position(row,oldCol),null);
            board.setPiece(new Position(row,to.getColumn()>from.getColumn()?5:3),rook);
        }
        enPassantTarget=null;
        if(p instanceof Pawn && Math.abs(to.getRow()-from.getRow())==2)
            enPassantTarget=new Position((to.getRow()+from.getRow())/2,from.getColumn());
        PieceColor moved=turn;
        turn=turn.opposite();
        message=moved+" moved "+from+" to "+to+".";
        if (!hasLegalMoves()) message+=inCheck(turn)?" Checkmate! "+moved+" wins.":" Stalemate! Draw.";
        else if(inCheck(turn)) message+=" "+turn+" is in check!";
        return true;
    }
    public boolean hasLegalMoves() {
        for(int r=0;r<8;r++) for(int c=0;c<8;c++) {
            Position from=new Position(r,c);
            Piece p=board.getPiece(from);
            if(p==null || p.getColor()!=turn) continue;
            for(int tr=0;tr<8;tr++) for(int tc=0;tc<8;tc++)
                if(isLegal(from,new Position(tr,tc))) return true;
        }
        return false;
    }
    public boolean isOver() { return !hasLegalMoves(); }
}
