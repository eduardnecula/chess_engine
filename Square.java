public class Square {
    public javaPiece piece;

    public Square(javaPiece piece) {
        this.piece = piece;
    }

    public Square() {
        this.piece = null;
    }

    public javaPiece getPiece() {
        return piece;
    }

    public void setPiece(javaPiece piece) {
        this.piece = piece;
    }

    public void clearSquare() {
        this.piece = null;
    }

    public boolean isColor(String color) {
        if (this.getPiece() != null) {
            if (this.getPiece().getColor() != null) {
                if (this.getPiece().getColor().equals(color)) {
                    return true;
                }
            }
        }
        return false;
    }
}
