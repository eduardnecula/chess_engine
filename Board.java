import java.awt.print.PrinterIOException;

public class Board {
    public Square[][] chessBoard;
    //public Square square = new Square(new javaPiece("a", 1, "a"));

    public Board(String color) {
        this.chessBoard = new Square[8][8];
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }

        for (int i=0; i<8; i++){
            for (int j =0; j <8; j++){
                chessBoard[i][j] = new Square();
            }
        }
        chessBoard[0][0].setPiece(new Rook("Rook", 3, color));
        chessBoard[0][7].setPiece(new Rook("Rook", 3, color));
        chessBoard[0][1].setPiece(new Knight("Knight", 2, color));
        chessBoard[0][6].setPiece(new Knight("Knight", 2, color));
        chessBoard[0][2].setPiece(new Bishop("Bishop", 1, color));
        chessBoard[0][5].setPiece(new Bishop("Bishop", 1, color));
        chessBoard[0][3].setPiece(new Queen("Queen", 4, color));
        chessBoard[0][4].setPiece(new King("King", 5, color));

        for (int i = 0; i < 8 ; i++){
            chessBoard[1][i].setPiece(new Pawn("Pawn", 0, color));
            chessBoard[6][i].setPiece(new Pawn("Pawn", 0,enemy));
        }
        chessBoard[7][0].setPiece(new Rook("Rook", 3, enemy));
        chessBoard[7][7].setPiece(new Rook("Rook", 3, enemy));
        chessBoard[7][1].setPiece(new Knight("Knight", 2, enemy));
        chessBoard[7][6].setPiece(new Knight("Knight", 2, enemy));
        chessBoard[7][2].setPiece(new Bishop("Bishop", 1, enemy));
        chessBoard[7][5].setPiece(new Bishop("Bishop", 1, enemy));
        chessBoard[7][3].setPiece(new Queen("Queen", 4, enemy));
        chessBoard[7][4].setPiece(new King("King", 5, enemy));
    }
    public void move(int lineSource, int colSource, int lineDest, int colDest){
        //dest = source
        chessBoard[lineDest][colDest].setPiece(chessBoard[lineSource][colSource].getPiece());
        //source empty
        chessBoard[lineSource][colSource].clearSquare();
    }
    public Square getSquare(int i, int j){
        return this.chessBoard[i][j];
    }
}
