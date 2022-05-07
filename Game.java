import java.util.ArrayList;
import java.util.List;

/**
 *
 * [7 6 5]
 * [8 9 4]
 * [1 2 3]
 *
 * Aria de cautare pentru rege, ca sa aflu unde pot muta, cand intra in sah
 *
 * 1 -> fata stanga
 * 2 -> fata
 * 3 -> fata dreapta
 * 4 -> dreapta
 * 5 -> spate dreapta
 * 6 -> spate
 * 7 -> spate stanga
 * 8 -> stanga
 *
 * 9 -> locul unde se afla regele
 */

public class Game {
    public Board chessBoard;  // tabla de sah
    String color;  // culoarea engine-ului
    public String move;  // comanda primita de la xboard
    public static String lastMove;  // ultima comanda copy
    public static String beforeLastMove;  // penultima comanda
    public static int nr_comenzi_primite = 0;

    // variabile ajutatoate pentru a stii cand pot sa fac rocada
    public boolean am_facut_rocada = false;
    public boolean am_mutat_regele = false;
    public boolean am_mutat_regele_alb = false;
    public static boolean mut_tura_stanga_neagra = false;
    public static boolean mut_tura_dreapta_neagra = false;
    public static boolean mut_tura_stanga_alba = false;
    public static boolean mut_tura_dreapta_alba = false;

     public Game(String color){
         this.chessBoard = new Board(color);
         this.color = color;
         this.move = move;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void set_last_command(String command) {
      //  System.out.println("nr comenzi primite: " + nr_comenzi_primite);
        if (nr_comenzi_primite == 0) {
            lastMove = command;
            beforeLastMove = command;
            nr_comenzi_primite++;
        } else {
            lastMove = beforeLastMove;
            beforeLastMove = command;
            nr_comenzi_primite++;
        }
    }

    /**
     * linia sursa a comenii
     * @return
     */
    public int last_move_started_line() {
        int lineDest = 0;
        lineDest = 8 - Character.getNumericValue(move.charAt(1));

        if (lineDest != 0) {
            return lineDest;
        }
        return  -1;
    }

    /**
     * linia destinatia  a ultimei comenzi
     * @return
     */
    public int lastMoveLine() {
        int lineDest = 0;
        lineDest = 8 - Character.getNumericValue(move.charAt(3));

        if (lineDest != 0) {
            return lineDest;
        }
        return  -1;
    }

    /**
     *
     * functia care intoarce pozitia ultimei comenzi pe coloana
     */

    public int lastMoveCol() {
        int colDest;
        switch(move.charAt(2)){
            case 'a':
                colDest = 0;
                break;
            case 'b':
                colDest = 1;
                break;
            case 'c':
                colDest= 2;
                break;
            case 'd':
                colDest = 3;
                break;
            case 'e':
                colDest = 4;
                break;
            case 'f':
                colDest = 5;
                break;
            case 'g':
                colDest = 6;
                break;
            case 'h':
                colDest = 7;
                break;
            default:
                System.out.println("ERROR! interpretMove -> dest, not in a-h");
                colDest = 8;
                break;
        }
        if (colDest != 8) {
            return  colDest;
        }
        return -1;
    }


    /**
     * functie care primeste e2e4 si muta piesa care se afla in e2 pe e4;
     * aceasta o folosim pt mutarile adversarului(robotului)
     * */

    public void interpretMove(String move){
        //move format: "e2e4", e2 = source, e4 = dest
        int lineSource, lineDest, colSource, colDest;
        //transform char '2' in int 2 (e2 = mat[6][4] => 6 = 8 - 2)
        lineSource = 8 - Character.getNumericValue(move.charAt(1));
        lineDest = 8 - Character.getNumericValue(move.charAt(3));

        switch(move.charAt(0)){
            case 'a':
                colSource = 0;
                break;
            case 'b':
                colSource = 1;
                break;
            case 'c':
                colSource = 2;
                break;
            case 'd':
                colSource = 3;
                break;
            case 'e':
                colSource = 4;
                break;
            case 'f':
                colSource = 5;
                break;
            case 'g':
                colSource = 6;
                break;
            case 'h':
                colSource = 7;
                break;
            default:
                System.out.println("ERROR! interpretMove -> source, not in a-h");
                colSource = 10;
                break;
        }
        switch(move.charAt(2)){
            case 'a':
                colDest = 0;
                break;
            case 'b':
                colDest = 1;
                break;
            case 'c':
                colDest= 2;
                break;
            case 'd':
                colDest = 3;
                break;
            case 'e':
                colDest = 4;
                break;
            case 'f':
                colDest = 5;
                break;
            case 'g':
                colDest = 6;
                break;
            case 'h':
                colDest = 7;
                break;
            default:
                System.out.println("ERROR! interpretMove -> dest, not in a-h");
                colDest = 8;
                break;
        }
        //System.out.println("!!!!!!" + lineSource + " " + colSource + " " +  lineDest + " " + colDest);
        this.chessBoard.move(lineSource,colSource,lineDest,colDest);
    }

    /**
     * daca mut oricare din regi, sau ture, nu am voie sa mai fac rocada,
     * functia seteaza variabilele care decid daca se poate face rocada
     * @param move
     */
    public void conditiile_pt_rocada_negru(String move) {
        //move format: "e2e4", e2 = source, e4 = dest
        int lineSource, lineDest, colSource, colDest;
        //transform char '2' in int 2 (e2 = mat[6][4] => 6 = 8 - 2)
        lineSource = 8 - Character.getNumericValue(move.charAt(1));
        lineDest = 8 - Character.getNumericValue(move.charAt(3));

        switch(move.charAt(0)){
            case 'a':
                colSource = 0;
                break;
            case 'b':
                colSource = 1;
                break;
            case 'c':
                colSource = 2;
                break;
            case 'd':
                colSource = 3;
                break;
            case 'e':
                colSource = 4;
                break;
            case 'f':
                colSource = 5;
                break;
            case 'g':
                colSource = 6;
                break;
            case 'h':
                colSource = 7;
                break;
            default:
                System.out.println("ERROR! interpretMove -> source, not in a-h");
                colSource = 10;
                break;
        }
        switch(move.charAt(2)){
            case 'a':
                colDest = 0;
                break;
            case 'b':
                colDest = 1;
                break;
            case 'c':
                colDest= 2;
                break;
            case 'd':
                colDest = 3;
                break;
            case 'e':
                colDest = 4;
                break;
            case 'f':
                colDest = 5;
                break;
            case 'g':
                colDest = 6;
                break;
            case 'h':
                colDest = 7;
                break;
            default:
                System.out.println("ERROR! interpretMove -> dest, not in a-h");
                colDest = 8;
                break;
        }

        // NEGRU
        // pentru rege negru
        // tura dreapta (0, 4)
        if (lineSource == 0 && colSource == 4) {
            if (chessBoard.getSquare(0, 4).getPiece() != null) {
                if (chessBoard.getSquare(0, 4).isColor(color)) {
                    if (chessBoard.getSquare(0, 4).getPiece().getIndex() == 5) {
                        this.am_mutat_regele = true;
                    }
                }
            }
        }

        // tura stanga negru
        // daca mut piesa de la (0, 0)
        if (lineSource == 0 && colSource == 0) {
            if (chessBoard.getSquare(0, 0).getPiece() != null) {
                if (chessBoard.getSquare(0, 0).isColor(color)) {
                    if (chessBoard.getSquare(0, 0).getPiece().getIndex() == 3) {
                        this.mut_tura_stanga_neagra = true;
                    }
                }
            }
        }
        // tura dreapta negru (0, 7)
        if (lineSource == 0 && colSource == 7) {
            if (chessBoard.getSquare(0, 7).getPiece() != null) {
                if (chessBoard.getSquare(0, 7).isColor(color)) {
                    if (chessBoard.getSquare(0, 7).getPiece().getIndex() == 3) {
                        this.mut_tura_dreapta_neagra = true;
                    }
                }
            }
        }
    }

    public void conditiile_pt_rocada_alba(String move) {
        int lineSource, lineDest, colSource, colDest;
        lineSource = 8 - Character.getNumericValue(move.charAt(1));

        switch(move.charAt(0)){
            case 'a':
                colSource = 0;
                break;
            case 'b':
                colSource = 1;
                break;
            case 'c':
                colSource = 2;
                break;
            case 'd':
                colSource = 3;
                break;
            case 'e':
                colSource = 4;
                break;
            case 'f':
                colSource = 5;
                break;
            case 'g':
                colSource = 6;
                break;
            case 'h':
                colSource = 7;
                break;
            default:
                System.out.println("ERROR! interpretMove -> source, not in a-h");
                colSource = 10;
                break;
        }
        switch(move.charAt(2)){
            case 'a':
                colDest = 0;
                break;
            case 'b':
                colDest = 1;
                break;
            case 'c':
                colDest= 2;
                break;
            case 'd':
                colDest = 3;
                break;
            case 'e':
                colDest = 4;
                break;
            case 'f':
                colDest = 5;
                break;
            case 'g':
                colDest = 6;
                break;
            case 'h':
                colDest = 7;
                break;
            default:
                System.out.println("ERROR! interpretMove -> dest, not in a-h");
                colDest = 8;
                break;
        }
        // ALB
        // TURA STANGA ALBA
        if (lineSource == 7 && colSource == 7) {
            mut_tura_stanga_alba = true;
        }

        // TURA DREAPTA ALBA
        if (lineSource == 7 && colSource == 0) {
            mut_tura_dreapta_alba = true;
        }

        // pentru rege alb
        if (lineSource == 7 && colSource == 4) {
            this.am_mutat_regele_alb = true;
        }
    }

    /**
     * UNIVERSAL
     * functia returneaza o lista cu pozitia pe linie si pe coloana a regelui din engine
     * @return
     */
    public List<Integer> king_position_engine() {
        List<Integer> position_king = new ArrayList<Integer>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard.getSquare(i, j).getPiece() != null) {
                    if (chessBoard.getSquare(i, j).getPiece().getIndex() == 5) {
                        if (chessBoard.getSquare(i, j).isColor(color)) {
                            position_king.add(i);
                            position_king.add(j);
                            return position_king;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * pozitia pe linie si coloana a regelui adversar
     * e utila pentru a nu-l lasa pe regele engine-ului sa se apropie de regele adversar
     * @return
     */
    public List<Integer> position_king_adversar() {
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        List<Integer> position_king = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard.getSquare(i, j).getPiece() != null) {
                    if (chessBoard.getSquare(i, j).getPiece().getIndex() == 5) {
                        if (chessBoard.getSquare(i, j).isColor(enemy)) {
                            position_king.add(i);
                            position_king.add(j);
                            return position_king;
                        }
                    }
                }
            }
        }
        return null;
    }
    /**
     * functia principala apelata, care verifica daca sunt in sah
     * Daca sunt in sah, returneaza urmatoarea mutare
     * Daca returneaza invalid, engine-ul cedeaza partida
     * @return
     */
    public List<Integer> verifica_daca_sunt_in_sah() {
        List<Integer> positions = new ArrayList<>();
        // daca sunt in sah
        int poz_rege_line = -1;
        int poz_rege_col = -1;
        // in caz ca intru in sah pe alb
        if (king_position_engine() != null) {
            poz_rege_line = king_position_engine().get(0);  // poz rege pe linie
            poz_rege_col = king_position_engine().get(1);  // poz rege pe coloana
            if (i_am_in_chess(poz_rege_line, poz_rege_col)) {
                List<Integer> position_king = resolve_chess(poz_rege_line, poz_rege_col);
                if (position_king != null) {
                    int move_to_line = position_king.get(0);
                    int move_to_col = position_king.get(1);
                    chessBoard.move(poz_rege_line, poz_rege_col, move_to_line, move_to_col);
                    positions.add(poz_rege_line);
                    positions.add(poz_rege_col);
                    positions.add(move_to_line);
                    positions.add(move_to_col);
                    return positions;
                } else {
                    positions.add(-1);  // trimit in caz ca nu am mutari disponibile
                    positions.add(-1);
                    positions.add(-1);
                    positions.add(-1);
                    return positions;
                }
            }
        }
        return null;  // daca nu sunt in sah
    }

    /**
     * ALB
     * functia care verifica daca o anumita pozitie din tabla este in sah sau nu
     * pentru a stii daca pot face o mutare cu regele in acel loc
     * sau daca pot muta o piesa, ca sa nu pun regele in pericol
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean i_am_in_chess(int poz_rege_line, int poz_rege_col) {
        if (color.equals("white")){
            // 1. pion/regina
            if (chess_from_pawn_alb(poz_rege_line, poz_rege_col)) {
                System.out.println("sah de la pion negru");
                return true;
            } else {
                System.out.println("nu e sah de la reg / pion");
            }
        } else {
            // 1. pion/regina
            if (chess_from_pawn_negru(poz_rege_line, poz_rege_col)) {
                System.out.println("sah de la pion alb");
                return true;
            } else {
                System.out.println("nu e sah de la reg / pion");
            }
        }

        // 2. tura / regina
        if (chess_from_tura(poz_rege_line, poz_rege_col)) {
            System.out.println("sah de la tura");
            return true;
        } else {
            System.out.println("nu e sah de la tura / reg");
        }

        // 3. nebun / regina
        if (chess_from_nebun(poz_rege_line, poz_rege_col)) {
            System.out.println("sah de la nebun");
            return true;
        } else {
            System.out.println("nu e sah de la neb / reg");
        }

        // 4. cal
        if (chess_from_cal(poz_rege_line, poz_rege_col)) {
            System.out.println("sah de la cal");
            return true;
        } else {
            System.out.println("nu e sah de la cal");
        }

        return false;
    }

    /**
     * ALB
     * Verifica daca primesc sah de la pioni sau regina alb
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean chess_from_pawn_alb(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        int pawn_left_line = poz_rege_line - 1;
        int pawn_left_col = poz_rege_col + 1;

        if (poz_rege_col >= 0 && poz_rege_col < 8 ) {
            if (poz_rege_line  >= 0 && poz_rege_line < 8) {  // atac din stanga
                if (pawn_left_line >= 0 && pawn_left_line < 8) {
                    if (pawn_left_col >= 0 && pawn_left_col < 8) {
                        if (chessBoard.getSquare(pawn_left_line, pawn_left_col).getPiece() != null) {
                            if (chessBoard.getSquare(pawn_left_line, pawn_left_col).isColor(enemy)) {
                                return chessBoard.getSquare(pawn_left_line, pawn_left_col).getPiece().getIndex() == 4 // regina
                                        || chessBoard.getSquare(pawn_left_line, pawn_left_col).getPiece().getIndex() == 0;
                            }
                        }
                    }
                }
            }
        }

        if (poz_rege_col >= 0 && poz_rege_col < 8 ) {
            if (poz_rege_line  >= 0 && poz_rege_line < 8) {  // atac din dreapta
                pawn_left_line = poz_rege_line - 1;
                pawn_left_col = poz_rege_col - 1;
                if (pawn_left_line >= 0 && pawn_left_line < 8) {
                    if (pawn_left_col >= 0 && pawn_left_col < 8) {
                        if (chessBoard.getSquare(pawn_left_line, pawn_left_col).getPiece() != null) {
                            if (chessBoard.getSquare(pawn_left_line, pawn_left_col).isColor(enemy)) {
                                // pion
                                return chessBoard.getSquare(pawn_left_line, pawn_left_col).getPiece().getIndex() == 4 // regina
                                        || chessBoard.getSquare(pawn_left_line, pawn_left_col).getPiece().getIndex() == 0;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * daca primesc sah de la pion sau regina alba
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean chess_from_pawn_negru(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        int pawn_left_line = poz_rege_line + 1;
        int pawn_left_col = poz_rege_col + 1;

        if (poz_rege_col >= 0 && poz_rege_col < 8 ) {
            if (poz_rege_line  >= 0 && poz_rege_line < 8) {  // atac din stanga
                if (pawn_left_line >= 0 && pawn_left_line < 8) {
                    if (pawn_left_col >= 0 && pawn_left_col < 8) {
                        if (chessBoard.getSquare(pawn_left_line, pawn_left_col).getPiece() != null) {
                            if (chessBoard.getSquare(pawn_left_line, pawn_left_col).isColor(enemy)) {
                                return chessBoard.getSquare(pawn_left_line, pawn_left_col).getPiece().getIndex() == 4 // regina
                                        || chessBoard.getSquare(pawn_left_line, pawn_left_col).getPiece().getIndex() == 0;
                            }
                        }
                    }
                }
            }
        }

        if (poz_rege_col >= 0 && poz_rege_col < 8 ) {
            if (poz_rege_line  >= 0 && poz_rege_line < 8) {  // atac din dreapta
                pawn_left_line = poz_rege_line + 1;
                pawn_left_col = poz_rege_col - 1;
                if (pawn_left_line >= 0 && pawn_left_line < 8) {
                    if (pawn_left_col >= 0 && pawn_left_col < 8) {
                        if (chessBoard.getSquare(pawn_left_line, pawn_left_col).getPiece() != null) {
                            if (chessBoard.getSquare(pawn_left_line, pawn_left_col).isColor(enemy)) {
                                // pion
                                return chessBoard.getSquare(pawn_left_line, pawn_left_col).getPiece().getIndex() == 4 // regina
                                        || chessBoard.getSquare(pawn_left_line, pawn_left_col).getPiece().getIndex() == 0;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * ALB
     * verifica daca intru in sah de la tura sau regina
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean chess_from_tura(int poz_rege_line, int poz_rege_col) {
        // tura fata
        if (tura_fata(poz_rege_line, poz_rege_col)) {
            return true;
        }
        // tura spate
        if (tura_spate(poz_rege_line, poz_rege_col)) {
            return true;
        }
        // tura stanga
        if (tura_stanga(poz_rege_line, poz_rege_col)) {
            return true;
        }
        // tura dreapta
        if (tura_dreapta(poz_rege_line, poz_rege_col)) {
            return true;
        }
        return false;
    }

    /**
     * ALB
     * verifica daca intru in sah de la tura/regina din fata
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean tura_fata(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        int poz_tura_i_fata  = poz_rege_line - 1;  // poz urmatoare pt fata
        if (poz_rege_col >= 0 && poz_rege_col < 8 ) {
            if (poz_rege_line  >= 0 && poz_rege_line < 8) {
                if (poz_tura_i_fata >= 0 && poz_tura_i_fata < 8) {
                    for (int k = poz_tura_i_fata; k >= 0; k--) {
                        if (chessBoard.getSquare(k, poz_rege_col).getPiece() == null) {
                            continue; // daca e null ma opresc
                        }
                        if (chessBoard.getSquare(k, poz_rege_col).isColor(color)) {  // daca dau de rege alb, ignor
                            if (chessBoard.getSquare(k, poz_rege_col).getPiece().getIndex() == 5) {
                                continue;
                            }
                        }
                        if (chessBoard.getSquare(k, poz_rege_col).isColor(color)) {
                            break;
                        }
                        if (chessBoard.getSquare(k, poz_rege_col).isColor(enemy)) {
                            if (chessBoard.getSquare(k, poz_rege_col).getPiece().getIndex() == 4 || // regina
                                    chessBoard.getSquare(k, poz_rege_col).getPiece().getIndex() == 3 // tura
                            ) {
                                return true;
                            }
                            break;
                        }

                    }
                }
            }
        }
        return false;
    }

    /**
     * ALB
     * verifica daca intru in sah de la tura/regina din spate
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean tura_spate(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        System.out.println("sunt la tura spate" + " enemy is " + enemy);
        if (poz_rege_col >= 0 && poz_rege_col < 8 ) {
            if (poz_rege_line  >= 0 && poz_rege_line < 8) {
                int poz_tura_spate = poz_rege_line + 1;
                if (poz_tura_spate < 8) {
                    for (int k = poz_tura_spate; k < 8; k++) {
                        if (chessBoard.getSquare(k, poz_rege_col).getPiece() == null) {
                            continue; // daca e null continui
                        }
                        System.out.println("nu e null " + k + " " + poz_rege_col);
                        if (chessBoard.getSquare(k, poz_rege_col).isColor(color)) {  // daca dau de rege negru, ignor
                            if (chessBoard.getSquare(k, poz_rege_col).getPiece().getIndex() == 5) {
                                continue;
                            }
                        }
                        if (chessBoard.getSquare(k, poz_rege_col).isColor(color)) {
                            break;
                        }
                        if (chessBoard.getSquare(k, poz_rege_col).isColor(enemy)) {

                            if (chessBoard.getSquare(k, poz_rege_col).getPiece().getIndex() == 4 || // regina
                                    chessBoard.getSquare(k, poz_rege_col).getPiece().getIndex() == 3 // tura
                            ) {
                                return true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * ALB
     * verifica daca intru in sah de la tura/regina din stanga
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean tura_stanga(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (poz_rege_col >= 0 && poz_rege_col < 8 ) {
            if (poz_rege_line  >= 0 && poz_rege_line < 8) {
                int poz_tura_j  = poz_rege_col + 1;  // poz la care sunt acum
                if (poz_tura_j < 8) {
                    for (int k = poz_tura_j; k < 8; k++) {
                        if (chessBoard.getSquare(poz_rege_line, k).getPiece() == null) {
                            continue; // daca e null ma opresc
                        }
                        if (chessBoard.getSquare(poz_rege_line, k).isColor(color)) {  // daca dau de rege alb, ignor
                            if (chessBoard.getSquare(poz_rege_line, k).getPiece().getIndex() == 5) {
                                continue;
                            }
                        }
                        if (chessBoard.getSquare(poz_rege_line, k).isColor(color)) {
                            break;
                        }
                        if (chessBoard.getSquare(poz_rege_line, k).isColor(enemy)) {
                            if (chessBoard.getSquare(poz_rege_line, k).getPiece().getIndex() == 4 || // regina
                                    chessBoard.getSquare(poz_rege_line, k).getPiece().getIndex() == 3 // tura
                            ) {
                                return true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * ALB
     * verifica daca intru in sah de la tura/regina din dreapta
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean tura_dreapta(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (poz_rege_col >= 0 && poz_rege_col < 8 ) {
            if (poz_rege_line  >= 0 && poz_rege_line < 8) {
                int aux = poz_rege_col - 1;
                if (aux >= 0) {
                    for (int k = aux; k >= 0; k--) {
                        if (chessBoard.getSquare(poz_rege_line, k).getPiece() == null) {
                            continue; // daca e null ma opresc
                        }
                        if (chessBoard.getSquare(poz_rege_line, k).isColor(color)) {  // daca dau de rege alb, ignor
                            if (chessBoard.getSquare(poz_rege_line, k).getPiece().getIndex() == 5) {
                                continue;
                            }
                        }
                        if (chessBoard.getSquare(poz_rege_line, k).isColor(color)) {
                            break;
                        }
                        if (chessBoard.getSquare(poz_rege_line, k).isColor(enemy)) {
                            if (chessBoard.getSquare(poz_rege_line, k).getPiece().getIndex() == 4 || // regina
                                    chessBoard.getSquare(poz_rege_line, k).getPiece().getIndex() == 3 // tura
                            ) {
                                return true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * ALB
     * verifica daca daca intru in sah de la nebun sau regina
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean chess_from_nebun(int poz_rege_line, int poz_rege_col) {
        if (nebun_fata_stanga(poz_rege_line, poz_rege_col)) {
            return true;
        }
        // nebun spate
        if (nebun_fata_dreapta(poz_rege_line, poz_rege_col)) {
            return true;
        }
        // nebun stanga
        if (nebun_spate_stanga(poz_rege_line, poz_rege_col)) {
            return true;
        }
        // nebun dreapta
        if (nebun_spate_dreapta(poz_rege_line, poz_rege_col)) {
            return true;
        }
        return false;
    }
    /**
     * ALB
     * verifica daca regina sau nebun care ataca din fata stanga
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean nebun_fata_stanga(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (poz_rege_col >= 0 && poz_rege_col < 8 ) {
            if (poz_rege_line  >= 0 && poz_rege_line < 8) {
                int poz_j = poz_rege_col;
                if (poz_rege_line > 0) {
                    for(int k = poz_rege_line - 1; k >= 0; k--) {
                        poz_j++;
                        if (poz_j >= 0 && poz_j < 8) {
                            if (chessBoard.getSquare(k, poz_j) == null) {
                                continue;
                            }
                            if (chessBoard.getSquare(k, poz_j).isColor(color)) {  // daca dau de rege alb, ignor
                                if (chessBoard.getSquare(k, poz_j).getPiece().getIndex() == 5) {
                                    continue;
                                }
                            }
                            if (chessBoard.getSquare(k, poz_j).isColor(color)) {
                                break;
                            }
                            if (chessBoard.getSquare(k, poz_j).isColor(enemy)) {
                                if (k < 8) {
                                    if (chessBoard.getSquare(k, poz_j).getPiece().getIndex() == 4 || // regina
                                            chessBoard.getSquare(k, poz_j).getPiece().getIndex() == 1 // nebunul
                                    ) {
                                        return true;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        return  false;
    }

    /**
     * ALB
     * verifica daca regina sau nebun care ataca din fata dreapta
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean nebun_fata_dreapta(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (poz_rege_col >= 0 && poz_rege_col < 8 ) {
            if (poz_rege_line  >= 0 && poz_rege_line < 8) {
                int aux = poz_rege_line - 1;
                if (aux >= 0 && aux < 8) {
                    int poz_j = poz_rege_col;
                    for (int k = aux; k >= 0; k--) {
                        poz_j--;
                        if (poz_j >= 0 && poz_j < 8) {
                            if (chessBoard.getSquare(k, poz_j).getPiece() == null) {
                                continue;
                            }
                            if (chessBoard.getSquare(k, poz_j).isColor(color)) {  // daca dau de rege alb, ignor
                                if (chessBoard.getSquare(k, poz_j).getPiece().getIndex() == 5) {
                                    continue;
                                }
                            }
                            if (chessBoard.getSquare(k, poz_j).isColor(color)) {
                                break;
                            }
                            if (chessBoard.getSquare(k, poz_j).isColor(enemy)) {
                                if (k < 8) {
                                    if (chessBoard.getSquare(k, poz_j).getPiece().getIndex() == 4 || // regina
                                            chessBoard.getSquare(k, poz_j).getPiece().getIndex() == 1 // nebunul
                                    ) {
                                        return true;
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }

        return  false;
    }

    /**
     * ALB
     * verifica daca regina sau nebun care ataca din spate stanga
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean nebun_spate_stanga(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        int aux = poz_rege_line + 1;
        if (poz_rege_col >= 0 && poz_rege_col < 8 ) {
            if (poz_rege_line  >= 0 && poz_rege_line < 8 && aux >= 0 && aux < 8) {
                int poz_j = poz_rege_col;
                for(int k = aux; k < 8; k++) {
                    poz_j++;
                    if (poz_j >= 0 && poz_j < 8) {
                        if(chessBoard.getSquare(k, poz_j) == null) {
                            continue;
                        }
                        if (chessBoard.getSquare(k, poz_j).isColor(color)) {  // daca dau de rege alb, ignor
                            if (chessBoard.getSquare(k, poz_j).getPiece().getIndex() == 5) {
                                continue;
                            }
                        }
                        if (chessBoard.getSquare(k, poz_j).isColor(color)) {
                            break;
                        }
                        if (chessBoard.getSquare(k, poz_j).isColor(enemy)) {
                            if (chessBoard.getSquare(k, poz_j).getPiece().getIndex() == 4 || // regina
                                    chessBoard.getSquare(k, poz_j).getPiece().getIndex() == 1 // nebunul
                            ) {
                                return true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return  false;
    }

    /**
     * ALB
     * verifica daca regina sau nebun care ataca din spate dreapta
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean nebun_spate_dreapta(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        int aux = poz_rege_line + 1;
        if (poz_rege_col >= 0 && poz_rege_col < 8 ) {
            if (poz_rege_line  >= 0 && poz_rege_line < 8 && aux >= 0 && aux < 8) {
                int poz_j = poz_rege_col;
                // if (i > 0) {
                for(int k = aux; k < 8; k++) {
                    poz_j--;
                    if (poz_j >= 0 && poz_j < 8) {
                        if(chessBoard.getSquare(k, poz_j) == null) {
                            continue;
                        }
                        if (chessBoard.getSquare(k, poz_j).isColor(color)) {  // daca dau de rege alb, ignor
                            if (chessBoard.getSquare(k, poz_j).getPiece().getIndex() == 5) {
                                continue;
                            }
                        }
                        if (chessBoard.getSquare(k, poz_j).isColor(color)) {
                            break;
                        }
                        if (chessBoard.getSquare(k, poz_j).isColor(enemy)) {
                            if (chessBoard.getSquare(k, poz_j).getPiece().getIndex() == 4 || // regina
                                    chessBoard.getSquare(k, poz_j).getPiece().getIndex() == 1 // nebunul
                            ) {
                                return true;
                            }
                            break;
                        }
                    }
                }
            }
        }
        return  false;
    }

    /**
     * functie care verifica daca regele poate sa fie atacat de cal
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean chess_from_cal(int poz_rege_line, int poz_rege_col) {
        // drept
        if (cal_fata_stanga_drept(poz_rege_line, poz_rege_col)) {
            return true;
        }
        // cal
        if (cal_fata_dreapta_drept(poz_rege_line, poz_rege_col)) {
            return true;
        }
        // cal
        if (cal_spate_stanga_drept(poz_rege_line, poz_rege_col)) {
            return true;
        }
        // cal
        if (cal_spate_dreapta_drept(poz_rege_line, poz_rege_col)) {
            return true;
        }

        // lateral
        if (cal_fata_stanga_lat(poz_rege_line, poz_rege_col)) {
            return true;
        }
        // cal
        if (cal_fata_dreapta_lat(poz_rege_line, poz_rege_col)) {
            return true;
        }
        // cal
        if (cal_spate_stanga_lat(poz_rege_line, poz_rege_col)) {
            return true;
        }
        // cal
        if (cal_spate_dreapta_lat(poz_rege_line, poz_rege_col)) {
            return true;
        }
        return false;
    }

    /**
     * functie aux pentru sah cal
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean cal_fata_stanga_lat(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")) {
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (poz_rege_col >= 0 && poz_rege_col < 8) {
            if (poz_rege_line >= 0 && poz_rege_line < 8) {
                int cal_linie = poz_rege_line - 1;
                int cal_col = poz_rege_col + 2;
                if (cal_linie >= 0 && cal_linie < 8) {
                    if (cal_col >= 0 && cal_col < 8) {  // daca la aceasta pozitie se afla un cal, sunt in sah
                        if (chessBoard.getSquare(cal_linie, cal_col).getPiece() != null) {
                            if (chessBoard.getSquare(cal_linie, cal_col).isColor(enemy)) {
                                if (chessBoard.getSquare(cal_linie, cal_col).getPiece().getIndex() == 2) { // cavaler
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * functie aux pentru sah cal
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean cal_fata_dreapta_lat(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")) {
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (poz_rege_col >= 0 && poz_rege_col < 8) {
            if (poz_rege_line >= 0 && poz_rege_line < 8) {
                int cal_linie = poz_rege_line - 1;
                int cal_col = poz_rege_col - 2;
                if (cal_linie >= 0 && cal_linie < 8) {
                    if (cal_col >= 0 && cal_col < 8) {  // daca la aceasta pozitie se afla un cal, sunt in sah
                        if (chessBoard.getSquare(cal_linie, cal_col).getPiece() != null) {
                            if (chessBoard.getSquare(cal_linie, cal_col).isColor(enemy)) {
                                if (chessBoard.getSquare(cal_linie, cal_col).getPiece().getIndex() == 2) { // cavaler
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * functie aux pentru sah cal
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean cal_spate_stanga_lat(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")) {
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (poz_rege_col >= 0 && poz_rege_col < 8) {
            if (poz_rege_line >= 0 && poz_rege_line < 8) {
                int cal_linie = poz_rege_line + 1;
                int cal_col = poz_rege_col + 2;
                if (cal_linie >= 0 && cal_linie < 8) {
                    if (cal_col >= 0 && cal_col < 8) {  // daca la aceasta pozitie se afla un cal, sunt in sah
                        if (chessBoard.getSquare(cal_linie, cal_col).getPiece() != null) {
                            if (chessBoard.getSquare(cal_linie, cal_col).isColor(enemy)) {
                                if (chessBoard.getSquare(cal_linie, cal_col).getPiece().getIndex() == 2) { // cavaler
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * functie aux pentru sah cal
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean cal_spate_dreapta_lat(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")) {
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (poz_rege_col >= 0 && poz_rege_col < 8) {
            if (poz_rege_line >= 0 && poz_rege_line < 8) {
                int cal_linie = poz_rege_line - 1;
                int cal_col = poz_rege_col - 2;
                if (cal_linie >= 0 && cal_linie < 8) {
                    if (cal_col >= 0 && cal_col < 8) {  // daca la aceasta pozitie se afla un cal, sunt in sah
                        if (chessBoard.getSquare(cal_linie, cal_col).getPiece() != null) {
                            if (chessBoard.getSquare(cal_linie, cal_col).isColor(enemy)) {
                                if (chessBoard.getSquare(cal_linie, cal_col).getPiece().getIndex() == 2) { // cavaler
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * functie aux pentru sah cal
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean cal_fata_stanga_drept(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")) {
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (poz_rege_col >= 0 && poz_rege_col < 8) {
            if (poz_rege_line >= 0 && poz_rege_line < 8) {
                int cal_linie = poz_rege_line - 2;
                int cal_col = poz_rege_col + 1;
                if (cal_linie >= 0) {
                    if (cal_col < 8) {  // daca la aceasta pozitie se afla un cal, sunt in sah
                        if (chessBoard.getSquare(cal_linie, cal_col).getPiece() != null) {
                            if (chessBoard.getSquare(cal_linie, cal_col).isColor(enemy)) {
                                if (chessBoard.getSquare(cal_linie, cal_col).getPiece().getIndex() == 2) { // cavaler
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * functie aux pentru sah cal
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean cal_spate_stanga_drept(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")) {
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (poz_rege_col >= 0 && poz_rege_col < 8) {
            if (poz_rege_line >= 0 && poz_rege_line < 8) {
                int cal_linie = poz_rege_line + 2;
                int cal_col = poz_rege_col + 1;
                if (cal_linie < 8) {
                    if (cal_col < 8) {  // daca la aceasta pozitie se afla un cal, sunt in sah
                        if (chessBoard.getSquare(cal_linie, cal_col).getPiece() != null) {
                            if (chessBoard.getSquare(cal_linie, cal_col).isColor(enemy)) {
                                if (chessBoard.getSquare(cal_linie, cal_col).getPiece().getIndex() == 2) { // cavaler
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * functie aux pentru sah cal
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean cal_fata_dreapta_drept(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")) {
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (poz_rege_col >= 0 && poz_rege_col < 8) {
            if (poz_rege_line >= 0 && poz_rege_line < 8) {
                int cal_linie = poz_rege_line - 2;
                int cal_col = poz_rege_col - 1;
                if (cal_linie >= 0) {
                    if (cal_col >= 0) {  // daca la aceasta pozitie se afla un cal, sunt in sah
                        if (chessBoard.getSquare(cal_linie, cal_col).getPiece() != null) {
                            if (chessBoard.getSquare(cal_linie, cal_col).isColor(enemy)) {
                                if (chessBoard.getSquare(cal_linie, cal_col).getPiece().getIndex() == 2) { // cavaler
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * functie aux pentru sah cal
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public boolean cal_spate_dreapta_drept(int poz_rege_line, int poz_rege_col) {
        String enemy;
        if (color.equals("white")) {
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (poz_rege_col >= 0 && poz_rege_col < 8) {
            if (poz_rege_line >= 0 && poz_rege_line < 8) {
                int cal_linie = poz_rege_line + 2;
                int cal_col = poz_rege_col - 1;
                if (cal_linie < 8) {
                    if (cal_col >= 0) {  // daca la aceasta pozitie se afla un cal, sunt in sah
                        if (chessBoard.getSquare(cal_linie, cal_col).getPiece() != null) {
                            if (chessBoard.getSquare(cal_linie, cal_col).isColor(enemy)) {
                                if (chessBoard.getSquare(cal_linie, cal_col).getPiece().getIndex() == 2) { // cavaler
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * ALB
     * functia apelata in caz ca regele se afla in sah
     * el cauta in jurul lui prima pozitie, ori goala, ori unde poate ataca, fara ca el sa intre in sah
     * @param poz_rege_line
     * @param poz_rege_col
     * @return
     */
    public List<Integer> resolve_chess(int poz_rege_line, int poz_rege_col) {
        // nu am voie sa ma apropii de regele adversar
        List<Integer> position_king = new ArrayList<>();
        if (position_king == null) {
            return null;
        }
        List<Integer> position_king_adversar = position_king_adversar();
        if (position_king_adversar == null) {
            return null;
        }
        int poz_rege_adversar_line = position_king_adversar.get(0);
        int poz_rege_adversar_col = position_king_adversar.get(1);


        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }

        // 8. stanga
        int  move_to_line = poz_rege_line;
        int move_to_col = poz_rege_col + 1;
        int distance_kings = Math.abs(move_to_line - poz_rege_adversar_line);
        int distance_kings_col = Math.abs(move_to_col - poz_rege_adversar_col);
        if (move_to_line >= 0 && move_to_line < 8) {
            if (move_to_col >= 0 && move_to_col < 8) {
                if (distance_kings >= 2 || distance_kings_col >= 2) {
                    if (!i_am_in_chess(move_to_line, move_to_col)) {
                        System.out.println("stanga  " + move_to_line + " " + move_to_col);
                        if (chessBoard.getSquare(move_to_line, move_to_col).getPiece() == null) {
                            position_king.add(move_to_line);
                            position_king.add(move_to_col);
                            return position_king;
                        }
                        else {
                            if (chessBoard.getSquare(move_to_line, move_to_col).isColor(enemy)) {
                                position_king.add(move_to_line);
                                position_king.add(move_to_col);
                                return position_king;
                            }
                        }
                    }
                }
            }
        }

        // 1. stanga fata
        move_to_line = poz_rege_line - 1;
        move_to_col = poz_rege_col + 1;
        distance_kings = Math.abs(move_to_line - poz_rege_adversar_line);
        distance_kings_col = Math.abs(move_to_col - poz_rege_adversar_col);
        if (move_to_line >= 0 && move_to_line < 8) {
            if (move_to_col >= 0 && move_to_col < 8) {
                if (distance_kings >= 2 || distance_kings_col >= 2) {
                    if (!i_am_in_chess(move_to_line, move_to_col)) {
                        System.out.println("stanga fata " + move_to_line + " " + move_to_col);
                        if (chessBoard.getSquare(move_to_line, move_to_col).getPiece() == null) {
                            position_king.add(move_to_line);
                            position_king.add(move_to_col);
                            return position_king;
                        }
                        else {
                            if (chessBoard.getSquare(move_to_line, move_to_col).isColor(enemy)) {
                                position_king.add(move_to_line);
                                position_king.add(move_to_col);
                                return position_king;
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("nu se poate stanga fata");
        }

        // 2. fata
        move_to_line = poz_rege_line - 1;
        move_to_col = poz_rege_col;
        distance_kings = Math.abs(move_to_line - poz_rege_adversar_line);
        distance_kings_col = Math.abs(move_to_col - poz_rege_adversar_col);
        if (move_to_line >= 0 && move_to_line < 8) {
            if (move_to_col >= 0 && move_to_col < 8) {
                if (distance_kings >= 2 || distance_kings_col >= 2) {
                    if (!i_am_in_chess(move_to_line, move_to_col)) {
                        System.out.println(" fata " + move_to_line + " " + move_to_col);
                        if (chessBoard.getSquare(move_to_line, move_to_col).getPiece() == null) {
                            position_king.add(move_to_line);
                            position_king.add(move_to_col);
                            return position_king;
                        }
                        else {
                            if (chessBoard.getSquare(move_to_line, move_to_col).isColor(enemy)) {
                                position_king.add(move_to_line);
                                position_king.add(move_to_col);
                                return position_king;
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("nu se poate fata");
        }

        // 3. dreapta fata
        move_to_line = poz_rege_line - 1;
        move_to_col = poz_rege_col - 1;
        distance_kings = Math.abs(move_to_line - poz_rege_adversar_line);
        distance_kings_col = Math.abs(move_to_col - poz_rege_adversar_col);
        if (move_to_line >= 0 && move_to_line < 8) {
            if (move_to_col >= 0 && move_to_col < 8) {
                if (distance_kings >= 2 || distance_kings_col >= 2) {
                    if (!i_am_in_chess(move_to_line, move_to_col)) {
                        System.out.println("dreapta fata " + move_to_line + " " + move_to_col);
                        if (chessBoard.getSquare(move_to_line, move_to_col).getPiece() == null) {
                            position_king.add(move_to_line);
                            position_king.add(move_to_col);
                            return position_king;
                        }
                        else {
                            if (chessBoard.getSquare(move_to_line, move_to_col).isColor(enemy)) {
                                position_king.add(move_to_line);
                                position_king.add(move_to_col);
                                return position_king;
                            }
                        }
                    }
                }
            }
        } else {
            System.out.println("nu se poate dreapta fata");
        }
        // 4. dreapta
        move_to_line = poz_rege_line;
        move_to_col = poz_rege_col - 1;
        distance_kings = Math.abs(move_to_line - poz_rege_adversar_line);
        distance_kings_col = Math.abs(move_to_col - poz_rege_adversar_col);
        if (move_to_line >= 0 && move_to_line < 8) {
            if (move_to_col >= 0 && move_to_col < 8) {
                if (distance_kings >= 2 || distance_kings_col >= 2) {
                    if (!i_am_in_chess(move_to_line, move_to_col)) {
                        System.out.println("dreapta " + move_to_line + " " + move_to_col);
                        if (chessBoard.getSquare(move_to_line, move_to_col).getPiece() == null) {
                            position_king.add(move_to_line);
                            position_king.add(move_to_col);
                            return position_king;
                        }
                        else {
                            if (chessBoard.getSquare(move_to_line, move_to_col).isColor(enemy)) {
                                position_king.add(move_to_line);
                                position_king.add(move_to_col);
                                return position_king;
                            }
                        }
                    }

                }
            }
        } else {
            System.out.println("nu se poate dreapta");
        }

        // 5. dreapta spate
        move_to_line = poz_rege_line + 1;
        move_to_col = poz_rege_col - 1;
        distance_kings = Math.abs(move_to_line - poz_rege_adversar_line);
        distance_kings_col = Math.abs(move_to_col - poz_rege_adversar_col);
        if (move_to_line >= 0 && move_to_line < 8) {
            if (move_to_col >= 0 && move_to_col < 8) {
                if (distance_kings >= 2 || distance_kings_col >= 2) {

                    if (!i_am_in_chess(move_to_line, move_to_col)) {
                        System.out.println("dreapta spate " + move_to_line + " " + move_to_col);
                        if (chessBoard.getSquare(move_to_line, move_to_col).getPiece() == null) {
                            position_king.add(move_to_line);
                            position_king.add(move_to_col);
                            return position_king;
                        }
                        else {
                            if (chessBoard.getSquare(move_to_line, move_to_col).isColor(enemy)) {
                                position_king.add(move_to_line);
                                position_king.add(move_to_col);
                                return position_king;
                            }
                        }
                    }
                }
            }
        }

        // 6. spate
        move_to_line = poz_rege_line + 1;
        move_to_col = poz_rege_col;
        distance_kings = Math.abs(move_to_line - poz_rege_adversar_line);
        distance_kings_col = Math.abs(move_to_col - poz_rege_adversar_col);
        if (move_to_line >= 0 && move_to_line < 8) {
            if (move_to_col >= 0 && move_to_col < 8) {
                if (distance_kings >= 2 || distance_kings_col >= 2) {
                    if (!i_am_in_chess(move_to_line, move_to_col)) {
                        System.out.println("spate " + move_to_line + " " + move_to_col);
                        if (chessBoard.getSquare(move_to_line, move_to_col).getPiece() == null) {
                            position_king.add(move_to_line);
                            position_king.add(move_to_col);
                            return position_king;
                        }
                        else {
                            if (chessBoard.getSquare(move_to_line, move_to_col).isColor(enemy)) {
                                position_king.add(move_to_line);
                                position_king.add(move_to_col);
                                return position_king;
                            }
                        }
                    }
                }
            }
        }

        // 7. spate stanga
        move_to_line = poz_rege_line + 1;
        move_to_col = poz_rege_col + 1;
        distance_kings = Math.abs(move_to_line - poz_rege_adversar_line);
        distance_kings_col = Math.abs(move_to_col - poz_rege_adversar_col);
        if (move_to_line >= 0 && move_to_line < 8) {
            if (move_to_col >= 0 && move_to_col < 8) {
                if (distance_kings >= 2 || distance_kings_col >= 2) {
                    //chessBoard.move(poz_rege_line, poz_rege_col, move_to_line, move_to_col);
                    if (!i_am_in_chess(move_to_line, move_to_col)) {
                        System.out.println("spate stanga " + move_to_line + " " + move_to_col);
                        if (chessBoard.getSquare(move_to_line, move_to_col).getPiece() == null) {
                            position_king.add(move_to_line);
                            position_king.add(move_to_col);
                            return position_king;
                        }
                        else {
                            if (chessBoard.getSquare(move_to_line, move_to_col).isColor(enemy)) {
                                position_king.add(move_to_line);
                                position_king.add(move_to_col);
                                return position_king;
                            }
                        }
                    }
                }
            }
        }
        return null; // in cazul in care nu am o mutare in care pot sa-mi mut regele
    }

    /**
     *functia care ataca agresic cu pioni
     * @param i
     * @param j
     * @return
     */
    public List<Integer> atac_pioni_alb(int i, int j) {
        List<Integer> move_to = new ArrayList<>();
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }

        //mutare cu pioni (pion are index 0)
        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 0 && i != 0) { //aici ar fi i!= 0
            // cazul in care fac en passat
            if (i == 3) {  // pozitia unde se poate face en passat
                int stg_j = j + 1;  // daca gasesc un pion in stanga sau in dreapta
                int dr_j = j - 1;
                if (stg_j >= 0 && stg_j < 8) {  // daca este bun
                    if (chessBoard.getSquare(i, stg_j).getPiece() != null) {
                        if (chessBoard.getSquare(i, stg_j).getPiece().getIndex() == 0) {
                            if (lastMoveLine() == i && lastMoveCol() == j + 1 && last_move_started_line() == 1) {
                                //if (!i_am_in_chess(i - 1, stg_j)) {
                                    chessBoard.move(i, j, i - 1, stg_j);
                                    chessBoard.getSquare(i, stg_j).clearSquare();
                                    move_to.add(i - 1);
                                    move_to.add(stg_j);
                                    return move_to;
                                }
                            //}
                        }
                    }
                }
                if (dr_j >= 0 && dr_j < 8) {  // daca este bun
                    if (chessBoard.getSquare(i, dr_j).getPiece() != null) {
                        if (chessBoard.getSquare(i, dr_j).getPiece().getIndex() == 0) {
                            if (lastMoveLine() == i && lastMoveCol() == j - 1 && last_move_started_line() == 1) {
                               // if (!i_am_in_chess(i - 1, dr_j)) {
                                    chessBoard.move(i, j, i - 1, dr_j);
                                    chessBoard.getSquare(i, dr_j).clearSquare();
                                    move_to.add(i - 1);
                                    move_to.add(dr_j);
                                    return move_to;
                                }
                            //}
                        }
                    }
                }
            }

            // verifica daca se afla pe ultima pozitie si devine regina
            if (i == 0) {
                // curata patratel si pune regina pe el
                chessBoard.getSquare(i, j).clearSquare();
                chessBoard.getSquare(i,j).setPiece(new Queen("Queen", 4, color));
                System.out.println("index " + chessBoard.getSquare(i,j).getPiece().getIndex());
                move_to.add(i);
                move_to.add(j);
                return move_to;
            }

            //atac pe diagonala piesa din margine stanga
            if ((j == 0) && (chessBoard.getSquare(i - 1, j + 1).isColor(enemy))) {
                //daca are adversar pe diagonala dreapta ataca
               // if (!i_am_in_chess(i - 1, j + 1)) {
                    chessBoard.move(i, j, i - 1, j + 1);
                    move_to.add(i - 1);
                    move_to.add(j + 1);
                    return move_to;
                //}
            }

            //atac pe diagonala piesa margine dreapta
            if ((j == 7) && (chessBoard.getSquare(i - 1, j - 1).isColor(enemy))) {
                //daca are adversar pe stanga diagonala, atunci ataca
                //if (!i_am_in_chess(i - 1, j - 1)) {
                    chessBoard.move(i, j, i - 1, j - 1);
                    move_to.add(i - 1);
                    move_to.add(j - 1);
                    return move_to;
                //}
            }

            //atac pe diagonala piese mijlocii
            if (j != 0 && j != 7 && (chessBoard.getSquare(i - 1, j - 1).isColor(enemy))) {
                //daca are adversar pe stanga diagonala, atunci ataca
               // if (!i_am_in_chess(i - 1, j - 1)) {
                    chessBoard.move(i, j, i - 1, j - 1);
                    move_to.add(i - 1);
                    move_to.add(j - 1);
                    return move_to;
               // }

            }
            if (j != 0 && j != 7 && (chessBoard.getSquare(i - 1, j + 1).isColor(enemy))) {
                //daca are adversar pe dreapta diagonala, atunci ataca
               // if (!i_am_in_chess(i - 1, j + 1)) {
                    chessBoard.move(i, j, i - 1, j + 1);
                    move_to.add(i - 1);
                    move_to.add(j + 1);
                    return move_to;
               // }
            }
        }
    return null;
    }

    /**
     * functia care arata unde jocul de sah poate sa atace agresiv
     * @param i
     * @param j
     * @return
     */

     // 2. NEBUNI
     public List<Integer> atac_nebuni_alb(int i, int j) {
         List<Integer> move_to = new ArrayList<>();
         String enemy;
         if (color.equals("white")) {
             enemy = "black";
         } else {
             enemy = "white";
         }
         // nebun stanga fata
         if (chessBoard.getSquare(i, j).getPiece() != null) {
             if (chessBoard.getSquare(i, j).getPiece().getIndex() == 1) {
                 int poz_viitoare_i = -1;
                 int poz_viitoare_j = -1;
                 int poz_j = j;
                 if (i > 0) {
                     for (int k = i - 1; k >= 0; k--) {
                         poz_j++;
                         if (poz_j >= 0 && poz_j < 8) {
                             if (chessBoard.getSquare(k, poz_j) == null) {
                                 continue;
                             }
                             if (chessBoard.getSquare(k, poz_j).isColor(color)) {
                                 break;
                             }
                             if (chessBoard.getSquare(k, poz_j).isColor(enemy)) {
                                 if (k < 8) {
                                     poz_viitoare_i = k;
                                     poz_viitoare_j = poz_j;
                                 }
                                 break;
                             }
                         }
                     }
                 }
                 if (poz_viitoare_i != -1) {

                    // if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                         chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                         move_to.add(poz_viitoare_i);
                         move_to.add(poz_viitoare_j);
                         return move_to;
                    // }
                 }
             }

             // nebun la dreapta fata
             if (chessBoard.getSquare(i, j).getPiece().getIndex() == 1) {
                 int poz_viitoare_i = -1;
                 int poz_viitoare_j = -1;
                 int poz_j = j;
                 for (int k = i - 1; k >= 0; k--) {
                     poz_j--;
                     if (poz_j >= 0 && poz_j < 8) {
                         if (chessBoard.getSquare(k, poz_j) == null) {
                             continue;
                         }
                         if (chessBoard.getSquare(k, poz_j).isColor(color)) {
                             break;
                         }
                         if (chessBoard.getSquare(k, poz_j).isColor(enemy)) {
                             if (k < 8) {
                                 poz_viitoare_i = k;
                                 poz_viitoare_j = poz_j;
                             }
                             break;
                         }
                     }
                 }
                 if (poz_viitoare_i != -1) {

                    // if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                         chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                         move_to.add(poz_viitoare_i);
                         move_to.add(poz_viitoare_j);
                         return move_to;
                    // }
                 }
             }

             // nebun stanga spate
             if (chessBoard.getSquare(i, j).getPiece() != null) {
                 if (chessBoard.getSquare(i, j) != null && chessBoard.getSquare(i, j).getPiece().getIndex() == 1) {
                     int poz_viitoare_i = -1;
                     int poz_viitoare_j = -1;
                     int poz_j = j;
                     // if (i > 0) {
                     for (int k = i + 1; k < 8; k++) {
                         poz_j++;
                         if (poz_j >= 0 && poz_j < 8) {
                             if (chessBoard.getSquare(k, poz_j) == null) {
                                 continue;
                             }
                             if (chessBoard.getSquare(k, poz_j).isColor(color)) {
                                 break;
                             }
                             if (chessBoard.getSquare(k, poz_j).isColor(enemy)) {
                                 poz_viitoare_i = k;
                                 poz_viitoare_j = poz_j;
                                 break;
                             }
                         }
                     }
                     //  }
                     if (poz_viitoare_i != -1) {
                        // if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                             chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                             move_to.add(poz_viitoare_i);
                             move_to.add(poz_viitoare_j);
                             return move_to;
                        // }
                     }
                 }
             }

             // nebun dreapta spate
             if (chessBoard.getSquare(i, j).getPiece() != null) {
                 if (chessBoard.getSquare(i, j).getPiece().getIndex() == 1) {
                     int poz_viitoare_i = -1;
                     int poz_viitoare_j = -1;
                     int poz_j = j;
                     // if (i > 0) {
                     for (int k = i + 1; k < 8; k++) {
                         poz_j--;
                         if (poz_j >= 0 && poz_j < 8) {
                             if (chessBoard.getSquare(k, poz_j) == null) {
                                 continue;
                             }
                             if (chessBoard.getSquare(k, poz_j).isColor(color)) {
                                 break;
                             }
                             if (chessBoard.getSquare(k, poz_j).isColor(enemy)) {
                                 poz_viitoare_i = k;
                                 poz_viitoare_j = poz_j;
                                 break;
                             }
                         }
                     }
                     if (poz_viitoare_i != -1) {
                        // if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                             chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                             move_to.add(poz_viitoare_i);
                             move_to.add(poz_viitoare_j);
                             return move_to;
                        // }
                     }
                 }
             }
         }

         return null;
     }

    /**
     * functie care returneza pozitia unde tura poate sa atace agresiv
     * @param i
     * @param j
     * @return
     */
     // 3. TURE
    public List<Integer> atac_ture_alb(int i, int j) {
        List<Integer> move_to = new ArrayList<>();
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (chessBoard.getSquare(i, j).getPiece() != null) {
            if (chessBoard.getSquare(i, j).getPiece().getIndex() == 3) {
                // daca pot sa atac in fata
                // ca sa atac in fata trebuie sa nu am o piesa de aceeasi culoare in fata mea
                int poz_tura_i_fata  = i - 1;  // poz urmatoare pt fata
                int poz_gasita_fata = -1;
                // tura fata
                if (poz_tura_i_fata >= 0 && poz_tura_i_fata < 8) {
                    for (int k = poz_tura_i_fata; k >= 0; k--) {
                        if (chessBoard.getSquare(k, j).getPiece() == null) {
                            continue; // daca e null ma opresc
                        }
                        if (chessBoard.getSquare(k, j).isColor(color)) {
                            break;
                        }
                        if (chessBoard.getSquare(k, j).isColor(enemy)) {
                            poz_gasita_fata = k;
                            break;
                        }
                    }
                    if (poz_gasita_fata != -1) {
                       // if (!i_am_in_chess(poz_gasita_fata, j)) {
                            move_to.add(poz_gasita_fata);
                            move_to.add(j);
                            return move_to;
                       // }
                    }
                }


                // tura spate

                int poz_tura_spate = i + 1;
                int poz_gasita_spate = -1;
                if (poz_tura_spate >= 0 && poz_tura_spate < 8) {
                    for (int k = poz_tura_spate; k < 7; k++) {
                        if (chessBoard.getSquare(k, j).getPiece() == null) {
                            continue; // daca e null ma opresc
                        }
                        if (chessBoard.getSquare(k, j).isColor(color)) {
                            break;
                        }
                        if (chessBoard.getSquare(k, j).isColor(enemy)) {
                            poz_gasita_spate = k;
                            break;
                        }
                    }

                    if (poz_gasita_spate != -1) {
                        //chessBoard.move(i, j, poz_gasita_spate, j);
                       // if (!i_am_in_chess(poz_gasita_spate, j)) {
                            move_to.add(poz_gasita_spate);
                            move_to.add(j);
                            return move_to;
                        //}
                    }
                }


                // tura stanga
                int poz_tura_j  = j + 1;  // poz la care sunt acum
                int poz_gasita_stanga = -1;
                if (poz_tura_j >= 0 && poz_tura_j < 8) {
                    for (int k = poz_tura_j; k < 8; k++) {
                        if (chessBoard.getSquare(i, k).getPiece() == null) {
                            continue; // daca e null ma opresc
                        }
                        if (chessBoard.getSquare(i, k).isColor(color)) {
                            break;
                        }
                        if (chessBoard.getSquare(i, k).isColor(enemy)) {
                            poz_gasita_stanga = k;
                            break;
                        }
                    }

                    if (poz_gasita_stanga != -1) {
                       // chessBoard.move(i, j, i, poz_gasita_stanga);
                       // if (!i_am_in_chess(i, poz_gasita_stanga)) {
                            move_to.add(i);
                            move_to.add(poz_gasita_stanga);
                            return move_to;
                       // }
                    }
                }

                // tura dreapta
                int poz_gasita_dr = -1;
                for (int k = j - 1; k >= 0; k--) {
                    if (chessBoard.getSquare(i, k).getPiece() == null) {
                        continue; // daca e null ma opresc
                    }
                    if (chessBoard.getSquare(i, k).isColor(color)) {
                        break;
                    }
                    if (chessBoard.getSquare(i, k).isColor(enemy)) {
                        poz_gasita_dr = k;
                        break;
                    }
                }

                if (poz_gasita_dr != -1) {
                   // if (!i_am_in_chess(i, poz_gasita_dr)) {
                        move_to.add(i);
                        move_to.add(poz_gasita_dr);
                        return move_to;
                   // }
                }
            }
        }

        return null;
    }

    /**
     * atac agresic cu regina
     * @param i
     * @param j
     * @return
     */
     // 5. REGINA
    public List<Integer> atac_regina_alb(int i, int j) {
        List<Integer> position = new ArrayList<>();
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 4) {
            // regina fata
            int poz_tura_i_fata  = i - 1;  // poz urmatoare pt fata
            int poz_gasita_fata = -1;

            for (int k = poz_tura_i_fata; k >= 0; k--) {
                if (chessBoard.getSquare(k, j).getPiece() == null) {
                    continue; // daca e null ma opresc
                }
                if (chessBoard.getSquare(k, j).isColor(color)) {
                    break;
                }
                if (chessBoard.getSquare(k, j).isColor(enemy)) {
                    poz_gasita_fata = k;
                    break;
                }
            }
            if (poz_gasita_fata != -1) {
               // if (!i_am_in_chess(poz_gasita_fata, j)) {
                    chessBoard.move(i, j, poz_gasita_fata, j);
                    position.add(poz_gasita_fata);
                    position.add(j);
                    return position;
              //  }
            }

            // regina spate
            int poz_tura_spate = i + 1;
            int poz_gasita_spate = -1;
            for (int k = poz_tura_spate; k < 7; k++) {
                if (chessBoard.getSquare(k, j).getPiece() == null) {
                    continue; // daca e null ma opresc
                }
                if (chessBoard.getSquare(k, j).isColor(color)) {
                    break;
                }
                if (chessBoard.getSquare(k, j).isColor(enemy)) {
                    poz_gasita_spate = k;
                    break;
                }
            }

            if (poz_gasita_spate != -1) {
               // if (!i_am_in_chess(poz_gasita_spate, j)) {
                    chessBoard.move(i, j, poz_gasita_spate, j);
                    position.add(poz_gasita_spate);
                    position.add(j);
                    return position;
               // }
            }

            // regina stanga
            int poz_tura_j  = j + 1;  // poz la care sunt acum
            int poz_gasita_stanga = -1;

            for (int k = poz_tura_j; k < 8; k++) {
                if (chessBoard.getSquare(i, k).getPiece() == null) {
                    continue; // daca e null ma opresc
                }
                if (chessBoard.getSquare(i, k).isColor(color)) {
                    break;
                }
                if (chessBoard.getSquare(i, k).isColor(enemy)) {
                    poz_gasita_stanga = k;
                    break;
                }
            }

            if (poz_gasita_stanga != -1) {
               // if (!i_am_in_chess(i, poz_gasita_stanga)) {
                    chessBoard.move(i, j, i, poz_gasita_stanga);
                    position.add(i);
                    position.add(poz_gasita_stanga);
                    return position;
               // }
            }

            // regina dreapta
            int poz_gasita_dr = -1;

            for (int k = j - 1; k >= 0; k--) {
                if (chessBoard.getSquare(i, k).getPiece() == null) {
                    continue; // daca e null ma opresc
                }
                if (chessBoard.getSquare(i, k).isColor(color)) {
                    break;
                }
                if (chessBoard.getSquare(i, k).isColor(enemy)) {
                    poz_gasita_dr = k;
                    break;
                }
            }

            if (poz_gasita_dr != -1) {
             //   if (!i_am_in_chess(i, poz_gasita_dr)) {
                    chessBoard.move(i, j, i, poz_gasita_dr);
                    position.add(i);
                    position.add(poz_gasita_dr);
                    return position;
              //  }
            }

            int poz_viitoare_i = -1;
            int poz_viitoare_j = -1;
            int poz_j = j;
            if (i > 0) {
                for(int k = i - 1; k >= 0; k--) {
                    poz_j++;
                    if (poz_j >= 0 && poz_j < 8) {
                        if (chessBoard.getSquare(k, poz_j) == null) {
                            continue;
                        }
                        if (chessBoard.getSquare(k, poz_j).isColor(color)) {
                            break;
                        }
                        if (chessBoard.getSquare(k, poz_j).isColor(enemy)) {
                            if (k < 8) {
                                poz_viitoare_i = k;
                                poz_viitoare_j = poz_j;
                            }
                            break;
                        }
                    }
                }
            }

            if (poz_viitoare_i != -1) {
               // if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                    chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                    position.add(poz_viitoare_i);
                    position.add(poz_viitoare_j);
                    return position;
               // }
            }

            // regina la dreapta fata
            poz_viitoare_i = -1;
            poz_viitoare_j = -1;
            poz_j = j;
            for (int k = i - 1; k >= 0; k--) {
                poz_j--;
                if (poz_j >= 0 && poz_j < 8) {
                    if (chessBoard.getSquare(k, poz_j) == null) {
                        continue;
                    }
                    if (chessBoard.getSquare(k, poz_j).isColor(color)) {
                        break;
                    }
                    if (chessBoard.getSquare(k, poz_j).isColor(enemy)) {
                        if (k < 8) {
                            poz_viitoare_i = k;
                            poz_viitoare_j = poz_j;
                        }
                        break;
                    }
                }
            }
            if (poz_viitoare_i != -1) {
              //  if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                    chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                    position.add(poz_viitoare_i);
                    position.add(poz_viitoare_j);
                    return position;
               // }
            }
            // nebun stanga spate
            if (chessBoard.getSquare(i, j) != null && chessBoard.getSquare(i, j).getPiece().getIndex() == 1) {
                poz_viitoare_i = -1;
                poz_viitoare_j = -1;
                poz_j = j;
                for (int k = i + 1; k < 8; k++) {
                    poz_j++;
                    if (poz_j >= 0 && poz_j < 8) {
                        if (chessBoard.getSquare(k, poz_j) == null) {
                            continue;
                        }
                        if (chessBoard.getSquare(k, poz_j).isColor(color)) {
                            break;
                        }
                        if (chessBoard.getSquare(k, poz_j).isColor(enemy)) {
                            poz_viitoare_i = k;
                            poz_viitoare_j = poz_j;
                            break;
                        }
                    }
                }
                if (poz_viitoare_i != -1) {
                //    if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                        chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                        position.add(poz_viitoare_i);
                        position.add(poz_viitoare_j);
                        return position;
                    }
               // }
            }
        }
        return null;
    }

    /**
     * atac agresiv cu cai
     * @param i
     * @param j
     * @return
     */
     // 6. CAI
     public List<Integer> atac_cai_alb(int i, int j) {
         List<Integer> move_to = new ArrayList<>();
         String enemy;
         if (color.equals("white")) {
             enemy = "black";
         } else {
             enemy = "white";
         }

        // cal in fata stanga drept
         if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
             int poz_viitoare_i = i - 2;
             int poz_viitoare_j = j + 1;
             if (poz_viitoare_i >= 0 && poz_viitoare_i < 8 && poz_viitoare_j >= 0 && poz_viitoare_j < 8) {
                 // daca am un inamic aici mut
                 if (chessBoard.getSquare(poz_viitoare_i, poz_viitoare_j).isColor(enemy)) {
                  //   if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                         chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                         move_to.add(poz_viitoare_i);
                         move_to.add(poz_viitoare_j);
                         return move_to;
                     }
                 //}
             }
         }

         // cal in fata dreapta drept
         if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
             int poz_viitoare_i = i - 2;
             int poz_viitoare_j = j - 1;
             if (poz_viitoare_i >= 0 && poz_viitoare_i < 8 && poz_viitoare_j >= 0 && poz_viitoare_j < 8) {
                 // daca am un inamic aici mut
                 if (chessBoard.getSquare(poz_viitoare_i, poz_viitoare_j).isColor(enemy)) {
                    // if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                         chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                         move_to.add(poz_viitoare_i);
                         move_to.add(poz_viitoare_j);
                         return move_to;
                     }
                // }
             }
         }

         // cal in fata stanga lateral
         if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
             int poz_viitoare_i = i - 1;
             int poz_viitoare_j = j + 2;
             if (poz_viitoare_i >= 0 && poz_viitoare_i < 8 && poz_viitoare_j >= 0 && poz_viitoare_j < 8) {
                 // daca am un inamic aici mut
                 if (chessBoard.getSquare(poz_viitoare_i, poz_viitoare_j).isColor(enemy)) {
                    // if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                         chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                         move_to.add(poz_viitoare_i);
                         move_to.add(poz_viitoare_j);
                         return move_to;
                     }
                // }
             }
         }

         // cal in fata dreapta lateral
         if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
             int poz_viitoare_i = i - 1;
             int poz_viitoare_j = j - 2;
             if (poz_viitoare_i >= 0 && poz_viitoare_i < 8 && poz_viitoare_j >= 0 && poz_viitoare_j < 8) {
                 // daca am un inamic aici mut
                 if (chessBoard.getSquare(poz_viitoare_i, poz_viitoare_j).isColor(enemy)) {
                  //   if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                         chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                         move_to.add(poz_viitoare_i);
                         move_to.add(poz_viitoare_j);
                         return move_to;
                     }
                 //}
             }
         }

         // cal in spata stanga drept
         if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
             int poz_viitoare_i = i + 2;
             int poz_viitoare_j = j + 1;
             if (poz_viitoare_i >= 0 && poz_viitoare_i < 8 && poz_viitoare_j >= 0 && poz_viitoare_j < 8) {
                 // daca am un inamic aici mut
                 if (chessBoard.getSquare(poz_viitoare_i, poz_viitoare_j).isColor(enemy)) {
                 //    if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                         chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                         move_to.add(poz_viitoare_i);
                         move_to.add(poz_viitoare_j);
                         return move_to;
                     }
                 //}
             }
         }

         // cal in spate dreapta drept
         if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
             int poz_viitoare_i = i + 2;
             int poz_viitoare_j = j - 1;
             if (poz_viitoare_i >= 0 && poz_viitoare_i < 8 && poz_viitoare_j >= 0 && poz_viitoare_j < 8) {
                 // daca am un inamic aici mut
                 if (chessBoard.getSquare(poz_viitoare_i, poz_viitoare_j).isColor(enemy)) {
                  //   if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                         chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                         move_to.add(poz_viitoare_i);
                         move_to.add(poz_viitoare_j);
                         return move_to;
                     }
                 //}
             }
         }

         // cal in spata stanga lateral
         if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
             int poz_viitoare_i = i + 1;
             int poz_viitoare_j = j + 2;
             if (poz_viitoare_i >= 0 && poz_viitoare_i < 8 && poz_viitoare_j >= 0 && poz_viitoare_j < 8) {
                 // daca am un inamic aici mut
                 if (chessBoard.getSquare(poz_viitoare_i, poz_viitoare_j).isColor(enemy)) {
                   //  if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                         chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                         move_to.add(poz_viitoare_i);
                         move_to.add(poz_viitoare_j);
                         return move_to;
                     }
                // }
             }
         }

         // cal in spata dreapta lateral
         if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
             int poz_viitoare_i = i + 1;
             int poz_viitoare_j = j - 2;
             if (poz_viitoare_i >= 0 && poz_viitoare_i < 8 && poz_viitoare_j >= 0 && poz_viitoare_j < 8) {
                 // daca am un inamic aici mut
                 if (chessBoard.getSquare(poz_viitoare_i, poz_viitoare_j).isColor(enemy)) {
                 //    if (!i_am_in_chess(poz_viitoare_i, poz_viitoare_j)) {
                         chessBoard.move(i, j, poz_viitoare_i, poz_viitoare_j);
                         move_to.add(poz_viitoare_i);
                         move_to.add(poz_viitoare_j);
                         return move_to;
                     }
                 //}
             }
         }
         return null;
     }

    /**
     * functia care inlatura piesele pt ca rocada sa se poata face
     * @return
     */
    public List<Integer> rocada_alb() {
        String enemy;
        if (color.equals("white")) {
            enemy = "black";
        } else {
            enemy = "white";
        }
        List<Integer> positions = new ArrayList<>();
        //mutare nebuni pentru a face ROCADA
        // daca am pioni albi in fata lui, incerc sa ii mut pe acestia
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard.getSquare(i, j) != null) {
                    if (chessBoard.getSquare(i, j).isColor(color)) {
                        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 1) {
                            int poz_pion_i = i - 1;
                            int poz_pion_j = j - 1;
                            // verific daca am un pion pe pozitia din dreapta
                            // daca am inaintez cu pionul
                            if (poz_pion_i >= 0 && poz_pion_j >= 0 && poz_pion_i < 7 && poz_pion_j < 7) {
                                if (chessBoard.getSquare(poz_pion_i, poz_pion_j).isColor(color) && i == 7) {
                                    // daca am, inaintez cu pionul
                                    if (chessBoard.getSquare(poz_pion_i, poz_pion_j).getPiece().getIndex() == 0) {
                                       // if (!i_am_in_chess(poz_pion_i - 1, poz_pion_j)) {
                                            chessBoard.move(poz_pion_i, poz_pion_j, poz_pion_i - 1, poz_pion_j);
                                            positions.add(poz_pion_i);
                                            positions.add(poz_pion_j);
                                            positions.add(poz_pion_i - 1);
                                            positions.add(poz_pion_j);
                                            return positions;
                                       // }
                                    }
                                } else {
                                    if (i == 7) { // doar prima data voi muta diagonala
                                        int move_diag_i = i - 3;
                                        int move_diag_j = j - 3;
                                        if (move_diag_i >= 0 && move_diag_i <= 7 && move_diag_j >= 0 && move_diag_j <= 7) {
                                           // if (!i_am_in_chess(move_diag_i, move_diag_j)) {
                                                chessBoard.move(i, j, move_diag_i, move_diag_j);
                                                positions.add(i);
                                                positions.add(j);
                                                positions.add(move_diag_i);
                                                positions.add(move_diag_j);
                                                return positions;
                                            }
                                        //}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // mutare cal dreapta fata pt ROCADA
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //muta doar piesele noastre
                if (chessBoard.getSquare(i, j) != null) {
                    if (chessBoard.getSquare(i, j).isColor(color)) {
                        //pentru cai(index == 2)
                        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
                            int poz_stg_i = i - 2;
                            int poz_stg_j = j - 1;
                            if (poz_stg_i >= 0 && poz_stg_i <= 7 && poz_stg_j >= 0 && poz_stg_j <= 7) {
                                //prima mutare piesele pe patrat impar avanseaza cate doua patratele
                                if (chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(enemy) ||
                                        !chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(color)) {
                                   // if (!i_am_in_chess(poz_stg_i, poz_stg_j)) {
                                        chessBoard.move(i, j, poz_stg_i, poz_stg_j);
                                        positions.add(i);
                                        positions.add(j);
                                        positions.add(poz_stg_i);
                                        positions.add(poz_stg_j);
                                        return positions;
                                    }
                                //}
                            }
                        }
                    }
                }
            }
        }

        ///  mutare cal la stanga fata pt ROCADA
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //muta doar piesele noastre
                if (chessBoard.getSquare(i, j) != null) {
                    if (chessBoard.getSquare(i, j).isColor(color)) {
                        //pentru cai(index == 2)
                        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
                            int poz_stg_i = i - 2;
                            int poz_stg_j = j + 1;
                            if (poz_stg_i >= 0 && poz_stg_i <= 7 && poz_stg_j >= 0 && poz_stg_j <= 7) {
                                //prima mutare piesele pe patrat impar avanseaza cate doua patratele
                                if (chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(enemy) ||
                                        !chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(color)) {
                                   // if (!i_am_in_chess(poz_stg_i, poz_stg_j)) {
                                        chessBoard.move(i, j, poz_stg_i, poz_stg_j);
                                        positions.add(i);
                                        positions.add(j);
                                        positions.add(poz_stg_i);
                                        positions.add(poz_stg_j);
                                        return positions;
                                    }
                                //}
                            }
                        }

                    }
                }
            }
        }

        // mutare cal dreapta lateral
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //muta doar piesele noastre
                if (chessBoard.getSquare(i, j) != null) {
                    if (chessBoard.getSquare(i, j).isColor(color)) {
                        //pentru cai(index == 2)
                        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
                            int poz_stg_i = i - 1;
                            int poz_stg_j = j - 2;
                            if (poz_stg_i >= 0 && poz_stg_i <= 7 && poz_stg_j >= 0 && poz_stg_j <= 7) {
                                //prima mutare piesele pe patrat impar avanseaza cate doua patratele
                                if (chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(enemy) ||
                                        !chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(color)) {
                                   // if (!i_am_in_chess(poz_stg_i, poz_stg_j)) {
                                        chessBoard.move(i, j, poz_stg_i, poz_stg_j);
                                        positions.add(i);
                                        positions.add(j);
                                        positions.add(poz_stg_i);
                                        positions.add(poz_stg_j);
                                        return positions;
                                    }
                               // }
                            }
                        }
                    }
                }
            }
        }

        //  mutare cal la stanga lateral
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //muta doar piesele noastre
                if (chessBoard.getSquare(i, j) != null) {
                    if (chessBoard.getSquare(i, j).isColor(color)) {
                        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
                            int poz_stg_i = i -1;
                            int poz_stg_j = j + 2;

                            if (poz_stg_i >= 0 && poz_stg_i <= 7 && poz_stg_j >= 0 && poz_stg_j <= 7) {
                                //prima mutare piesele pe patrat impar avanseaza cate doua patratele
                                if (chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(enemy) ||
                                        !chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(color)) {
                                   // if (!i_am_in_chess(poz_stg_i, poz_stg_j)) {
                                        chessBoard.move(i, j, poz_stg_i, poz_stg_j);
                                        positions.add(i);
                                        positions.add(j);
                                        positions.add(poz_stg_i);
                                        positions.add(poz_stg_j);
                                        return positions;
                                    }
                               // }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * functia care inlatura piesele pentru a putea face rocada
     * pentru negru
     * @return
     */
    public List<Integer> rocada_negru() {
        String enemy;
        if (color.equals("white")) {
            enemy = "black";
        } else {
            enemy = "white";
        }
        List<Integer> positions = new ArrayList<>();
        //mutare nebuni pentru a face ROCADA
        // daca am pioni albi in fata lui, incerc sa ii mut pe acestia
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard.getSquare(i, j) != null) {
                    if (chessBoard.getSquare(i, j).isColor(color)) {
                        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 1) {
                            int poz_pion_i = 1;
                            int poz_pion_j = 6;
                            // verific daca am un pion pe pozitia din dreapta
                            // daca am inaintez cu pionul
                            if (chessBoard.getSquare(poz_pion_i, poz_pion_j).isColor(color) && i == 0) {
                                if (chessBoard.getSquare(poz_pion_i + 1, poz_pion_j).getPiece() == null) {
                                    // daca am, inaintez cu pionul
                                    if (chessBoard.getSquare(poz_pion_i, poz_pion_j).getPiece().getIndex() == 0) {
                                       // if (!i_am_in_chess(poz_pion_i + 1, poz_pion_j)) {
                                            chessBoard.move(poz_pion_i, poz_pion_j, poz_pion_i + 1, poz_pion_j);
                                            positions.add(poz_pion_i);
                                            positions.add(poz_pion_j);
                                            positions.add(poz_pion_i + 1);
                                            positions.add(poz_pion_j);
                                            return positions;
                                        }
                                    //}
                                }

                            } else {
                                if (i == 0 && j == 5) { // doar prima data voi muta diagonala
                                    int move_diag_i = i + 1;
                                    int move_diag_j = j + 1;
                                    if (move_diag_i >= 0 && move_diag_i < 8 && move_diag_j >= 0 && move_diag_j < 8) {
                                        if (chessBoard.getSquare(move_diag_i, move_diag_j).getPiece() == null) {
                                          //  if (!i_am_in_chess(move_diag_i, move_diag_j)) {
                                                chessBoard.move(i, j, move_diag_i, move_diag_j);
                                                positions.add(i);
                                                positions.add(j);
                                                positions.add(move_diag_i);
                                                positions.add(move_diag_j);
                                                return positions;
                                            }
                                        //}
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // mutare cal dreapta fata pt ROCADA
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //muta doar piesele noastre
                if (chessBoard.getSquare(i, j) != null && i == 7) {
                    if (chessBoard.getSquare(i, j).isColor(color)) {
                        //pentru cai(index == 2)
                        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
                            int poz_stg_i = i + 2;
                            int poz_stg_j = j - 1;
                            if (poz_stg_i >= 0 && poz_stg_i < 8 && poz_stg_j >= 0 && poz_stg_j < 8) {
                                //prima mutare piesele pe patrat impar avanseaza cate doua patratele
                                if (chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(enemy) ||
                                        !chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(color)) {
                                    //if (!i_am_in_chess(poz_stg_i, poz_stg_j)) {
                                        chessBoard.move(i, j, poz_stg_i, poz_stg_j);
                                        positions.add(i);
                                        positions.add(j);
                                        positions.add(poz_stg_i);
                                        positions.add(poz_stg_j);
                                        return positions;
                                    //}
                                }
                            }
                        }
                    }
                }
            }
        }

        ///  mutare cal la stanga fata pt ROCADA
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //muta doar piesele noastre
                if (chessBoard.getSquare(i, j) != null && i == 7) {
                    if (chessBoard.getSquare(i, j).isColor(color)) {
                        //pentru cai(index == 2)
                        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
                            int poz_stg_i = i -2;
                            int poz_stg_j = j + 1;
                            if (poz_stg_i >= 0 && poz_stg_i <= 7 && poz_stg_j >= 0 && poz_stg_j <= 7) {
                                //prima mutare piesele pe patrat impar avanseaza cate doua patratele
                                if (chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(enemy) ||
                                        !chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(color)) {
                                    //if (!i_am_in_chess(poz_stg_i, poz_stg_j)) {
                                        chessBoard.move(i, j, poz_stg_i, poz_stg_j);
                                        positions.add(i);
                                        positions.add(j);
                                        positions.add(poz_stg_i);
                                        positions.add(poz_stg_j);
                                        return positions;
                                    //}
                                }
                            }
                        }

                    }
                }
            }
        }

        // mutare cal dreapta lateral
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //muta doar piesele noastre
                if (chessBoard.getSquare(i, j) != null && i == 7) {
                    if (chessBoard.getSquare(i, j).isColor(color)) {
                        //pentru cai(index == 2)
                        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
                            int poz_stg_i = i + 1;
                            int poz_stg_j = j - 2;
                            if (poz_stg_i >= 0 && poz_stg_i <= 7 && poz_stg_j >= 0 && poz_stg_j <= 7) {
                                //prima mutare piesele pe patrat impar avanseaza cate doua patratele
                                if (chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(enemy) ||
                                        !chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(color)) {
                                    //if (!i_am_in_chess(poz_stg_i, poz_stg_j)) {
                                        chessBoard.move(i, j, poz_stg_i, poz_stg_j);
                                        positions.add(i);
                                        positions.add(j);
                                        positions.add(poz_stg_i);
                                        positions.add(poz_stg_j);
                                        return positions;
                                    //}
                                }
                            }
                        }
                    }
                }
            }
        }

        //  mutare cal la stanga lateral
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //muta doar piesele noastre
                if (chessBoard.getSquare(i, j) != null && i == 7) {
                    if (chessBoard.getSquare(i, j).isColor(color)) {
                        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
                            int poz_stg_i = i + 1;
                            int poz_stg_j = j + 2;

                            if (poz_stg_i >= 0 && poz_stg_i <= 7 && poz_stg_j >= 0 && poz_stg_j <= 7) {
                                //prima mutare piesele pe patrat impar avanseaza cate doua patratele
                                if (chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(enemy) ||
                                        !chessBoard.getSquare(poz_stg_i, poz_stg_j).isColor(color)) {
                                   // if (!i_am_in_chess(poz_stg_i, poz_stg_j)) {
                                        chessBoard.move(i, j, poz_stg_i, poz_stg_j);
                                        positions.add(i);
                                        positions.add(j);
                                        positions.add(poz_stg_i);
                                        positions.add(poz_stg_j);
                                        return positions;
                                   // }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * miscari banele cu pioni
     * @return
     */
    public List<Integer> miscari_banale_alb() {
        List<Integer> positions = new ArrayList<>();
        // daca nu s-au facut mutari agresive, se fac mutari banale cu pionii
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //muta doar piesele noastre
                if (chessBoard.getSquare(i, j) != null) {
                    if (chessBoard.getSquare(i, j).isColor(color)) {
                        //pentru pioni(index == 0)
                        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 0 && i!= 0) {
                            //prima mutare piesele pe patrat impar avanseaza cate doua patratele
                            if ((i == 6) && (j % 2 == 1)) {
                                //daca e liber fa mutare
                                if (chessBoard.getSquare(i - 2, j).getPiece() == null) {
                                    //if (!i_am_in_chess(i - 2, j)) {
                                        chessBoard.move(i, j, i - 2, j);
                                        positions.add(i);
                                        positions.add(j);
                                        positions.add(i - 2);
                                        positions.add(j);
                                        return positions;
                                    //}
                                }
                            }
                            // ceilalti pioni muta doar un patratel daca e liber
                            if (chessBoard.getSquare(i - 1, j).getPiece() == null) {
                               // if (!i_am_in_chess(i - 1, j)) {
                                    chessBoard.move(i, j, i - 1, j);
                                    positions.add(i);
                                    positions.add(j);
                                    positions.add(i - 1);
                                    positions.add(j);
                                    return positions;
                                }
                            //}
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * daca pot sa fac rocada alba o s-o fac
     * @param i
     * @param j
     * @return
     */
    public List<Integer> fa_rocada_alb(int i, int j) {
        List<Integer> positions = new ArrayList<>();

        // cazul in care fac rocada mica alba
        if (chessBoard.getSquare(i, j).isColor(color) && !am_mutat_regele_alb) {


            // tura din stanga si regele trebuie sa fie pusi pe pozitie
            if (chessBoard.getSquare(7, 7).getPiece() != null &&
                    chessBoard.getSquare(7, 4).getPiece() != null && !mut_tura_stanga_alba) {
                // daca piesele sunt puse pe pozitie
                // verific ca intre ele sa nu fie nimic
                if (chessBoard.getSquare(7, 6).getPiece() == null
                        && chessBoard.getSquare(7, 5).getPiece() == null) {
                    if (!i_am_in_chess(7, 6)) {
                        chessBoard.move(7, 4, 7, 6);  // regele mutat
                        chessBoard.move(7, 7, 7, 5);  //tura mutata
                        positions.add(7);
                        positions.add(4);
                        positions.add(7);
                        positions.add(6);
                        return positions;
                    }
                }
            }

            // rocada mare alba
            if (chessBoard.getSquare(7, 4).getPiece() != null && chessBoard.getSquare(7, 0) != null
                    && !mut_tura_dreapta_alba) {
                if (chessBoard.getSquare(7, 3).getPiece() == null
                        && chessBoard.getSquare(7, 2).getPiece() == null
                        && chessBoard.getSquare(7, 1).getPiece() == null ) {
                    if (!i_am_in_chess(7, 2)) {
                        chessBoard.move(7, 4, 7, 2);  // regele mutat
                        chessBoard.move(7, 0, 7, 3);  //tura mutata
                        positions.add(7);
                        positions.add(4);
                        positions.add(7);
                        positions.add(2);
                        return positions;
                    }
                }
            }
        }
        return null;
    }

    /**
     * daca pot sa fac rocada neagra o s-o fac
     * @param i
     * @param j
     * @return
     */
    public List<Integer> fa_rocada_neagra(int i, int j) {
        List<Integer> positions = new ArrayList<>();

        // cazul in care fac rocada mica neagra
        if (chessBoard.getSquare(i, j).isColor(color) && !am_mutat_regele) {
            // tura din stanga si regele trebuie sa fie pusi pe pozitie
            if (chessBoard.getSquare(0, 4).getPiece() != null && chessBoard.getSquare(0, 7).getPiece() != null && !mut_tura_dreapta_neagra) {
                // daca piesele sunt puse pe pozitie
                // verific ca intre ele sa nu fie nimic
                if (chessBoard.getSquare(0, 6).getPiece() == null && chessBoard.getSquare(0, 5).getPiece() == null) {
                    if (!i_am_in_chess(0, 6)) {
                        chessBoard.move(0, 4, 0, 6);  // regele mutat
                        chessBoard.move(0, 7, 0, 5);  //tura mutata
                        positions.add(0);
                        positions.add(4);
                        positions.add(0);
                        positions.add(6);
                        am_facut_rocada = true;
                        return positions;
                    }
                }
            }
            // rocada mare neagra
            if (chessBoard.getSquare(0, 4).getPiece() != null && chessBoard.getSquare(0, 0) != null && !mut_tura_stanga_neagra) {
                if (chessBoard.getSquare(0, 3).getPiece() == null && chessBoard.getSquare(0, 2).getPiece() == null
                        && chessBoard.getSquare(0, 1).getPiece() == null ) {
                    if (!i_am_in_chess(0, 2)) {
                        chessBoard.move(0, 4, 0, 2);  // regele mutat
                        chessBoard.move(0, 0, 0, 3);  //tura mutata
                        positions.add(0);
                        positions.add(4);
                        positions.add(0);
                        positions.add(2);
                        am_facut_rocada = true;
                        return positions;
                    }
                }
            }
        }
        return null;
    }

    /******************************************************************************************
     /* *****************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     /******************************************************************************************
     ****************************PENTRU NEGRU***************************************************



     /********************************************************************************************************

     /**
     * atac agresic cu pioni negri
     * @param i
     * @param j
     * @return
     */

    public List<Integer> atac_pioni_negru(int i, int j) {
        List<Integer> positions = new ArrayList<>();

        String enemy;
        if (color.equals("white")) {
            enemy = "black";
        } else {
            enemy = "white";
        }

        if (chessBoard.getSquare(i, j).isColor(color)) {  // era black
            // mutare cu pioni (pion are index 0)
            //mutare cu pioni (pion are index 0)
            if (i == 4) {  // pozitia unde se poate face en passat
                int stg_j = j + 1;  // daca gasesc un pion in stanga sau in dreapta
                int dr_j = j - 1;
                if (stg_j >= 0 && stg_j < 8) {  // daca este bun
                    if (chessBoard.getSquare(i, stg_j).getPiece() != null) {
                        if (chessBoard.getSquare(i, stg_j).getPiece().getIndex() == 0) {
                            if (lastMoveLine() == i && lastMoveCol() == j + 1 && last_move_started_line() == 6) {
                             //   if (!i_am_in_chess(i + 1, stg_j)) {
                                    chessBoard.move(i, j, i + 1, stg_j);
                                    chessBoard.getSquare(i, stg_j).clearSquare();
                                    positions.add(i + 1);
                                    positions.add(stg_j);
                                    return positions;
                                }
                            //}
                        }
                    }
                }
                if (dr_j >= 0 && dr_j < 8) {  // daca este bun
                    if (chessBoard.getSquare(i, dr_j).getPiece() != null) {
                        if (chessBoard.getSquare(i, dr_j).getPiece().getIndex() == 0) {
                            if (lastMoveLine() == i && lastMoveCol() == j - 1 && last_move_started_line() == 6) {
                               // if (!i_am_in_chess(i + 1, dr_j)) {
                                    chessBoard.move(i, j, i + 1, dr_j);
                                    chessBoard.getSquare(i, dr_j).clearSquare();
                                    positions.add(i + 1);
                                    positions.add(dr_j);
                                    return positions;
                               // }
                            }
                        }
                    }
                }
            }

                if (chessBoard.getSquare(i, j).getPiece().getIndex() == 0 && i != 7) {
                    // verifica daca se afla pe ultima pozitie si devine regina
                    if (i == 7) {
                        // curata patratel si pune regina pe el
                        chessBoard.getSquare(i, j).clearSquare();
                        chessBoard.getSquare(i, j).setPiece(new Queen("Queen", 4, color)); //black
                        return null;
                        // return sendMove(i, j, i, j);
                    }
                    // atac pe diagonala piesa din margine stanga
                    if ((j == 0) && (chessBoard.getSquare(i + 1, j + 1).isColor(enemy))) {
                        //daca are adversar pe diagonala dreapta ataca
                       // if (!i_am_in_chess(i + 1, j + 1)) {
                            chessBoard.move(i, j, i + 1, j + 1);
                            positions.add(i);
                            positions.add(j);
                            positions.add(i + 1);
                            positions.add(j + 1);
                            return positions;
                       // }
                    }

                    //atac pe diagonala piesa margine dreapta
                    if ((j == 7) && (chessBoard.getSquare(i + 1, j - 1).isColor(enemy))) {
                        //daca are adversar pe stanga diagonala, atunci ataca
                       // if (!i_am_in_chess(i + 1, j - 1)) {
                            chessBoard.move(i, j, i + 1, j - 1);
                            positions.add(i);
                            positions.add(j);
                            positions.add(i + 1);
                            positions.add(j - 1);
                            return positions;
                        //}
                    }

                    //atac pe diagonala piese mijlocii
                    if (j != 7 && j != 0 && (chessBoard.getSquare(i + 1, j - 1).isColor(enemy))) {
                        //daca are adversar pe stanga diagonala, atunci ataca
                       // if (!i_am_in_chess(i + 1, j - 1)) {
                            chessBoard.move(i, j, i + 1, j - 1);
                            positions.add(i);
                            positions.add(j);
                            positions.add(i + 1);
                            positions.add(j - 1);
                            return positions;
                       // }

                    }

                    if (j != 7 && j != 0 && chessBoard.getSquare(i + 1, j + 1).isColor(enemy)) {
                        //daca are adversar pe dreapta diagonala, atunci ataca
                       // if (!i_am_in_chess(i + 1, j + 1)) {
                            chessBoard.move(i, j, i + 1, j + 1);
                            positions.add(i);
                            positions.add(j);
                            positions.add(i + 1);
                            positions.add(j + 1);
                            return positions;
                      //  }
                    }
                }
            }
        return null;
    }

    /**
     * mutari banale cu pioni negri
     * @return
     */
    public List<Integer> mutari_banale_negru() {
        List<Integer> positions = new ArrayList<>();
        // daca nu s-au facut mutari agresive, se fac mutari banale cu pioni
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessBoard.getSquare(i, j) != null) {
                    if (chessBoard.getSquare(i, j).isColor(color)) { //black
                        //pentru pioni(index == 0)
                        if (chessBoard.getSquare(i, j).getPiece().getIndex() == 0 && i != 7) {
                            //prima mutare piesele pe patrat impar avanseaza cate doua patratele
                            if (i == 1 && (j % 2 == 1)) {
                                //daca e liber fa mutare
                                if (chessBoard.getSquare(i + 2, j).getPiece() == null) {
                                   // if (!i_am_in_chess(i + 2, j)) {
                                        chessBoard.move(i, j, i + 2, j);
                                        positions.add(i);
                                        positions.add(j);
                                        positions.add(i + 2);
                                        positions.add(j);
                                        return positions;
                                  //  }
                                }
                            }

                            // ceilalti pioni muta doar un patratel daca e liber
                            if (chessBoard.getSquare(i + 1, j).getPiece() == null) {
                             //   if (!i_am_in_chess(i + 1, j)) {
                                    chessBoard.move(i, j, i + 1, j);
                                    positions.add(i);
                                    positions.add(j);
                                    positions.add(i + 1);
                                    positions.add(j);
                                    return positions;
                               // }
                            }
                        }
                    }
                }

            }
        }
         return null;
    }

    /**
     * mutari banale cu miscarile regelui
     * @param linie_rege
     * @param col_rege
     * @return
     */
    public List<Integer> mutare_banala_rege(int linie_rege, int col_rege) {
        List<Integer> positions = new ArrayList<>();

        int line = linie_rege;
        int col = col_rege;

        if (line >= 0 && line < 8) {
            if (col >= 0 && col < 8) {

            // 1. fata
                line = linie_rege + 1;
                col = col_rege;
                if (line < 8) {
                    if (chessBoard.getSquare(line, col).getPiece() == null) {
                       // if (!i_am_in_chess(line, col)) {
                            positions.add(line);
                            positions.add(col);
                            return positions;
                        //}
                    }
                }

            // 2. fata stanga
                line = linie_rege + 1;
                col = col_rege + 1;
                if (line < 8) {
                    if (col < 8) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                           // if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                           // }
                        }
                    }
                }

            // 3. fata dreapta
                line = linie_rege + 1;
                col = col_rege - 1;
                if (line < 8) {
                    if (chessBoard.getSquare(line, col).getPiece() == null) {
                        if (col >= 0) {
                          //  if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                           // }
                        }
                    }
                }

                // 4. dreapta
                line = linie_rege;
                col = col_rege + 1;
                if (col < 8) {
                    if (chessBoard.getSquare(line, col).getPiece() == null) {
                        // if (!i_am_in_chess(line, col)) {
                        positions.add(line);
                        positions.add(col);
                        return positions;
                        // }
                    }
                }
                // 5. spate
                line = linie_rege - 1;
                col = col_rege;
                if (line >= 0) {
                    if (chessBoard.getSquare(line, col).getPiece() == null) {
                        //if (!i_am_in_chess(line, col)) {
                            positions.add(line);
                            positions.add(col);
                            return positions;
                        //}
                    }
                }

                // 6. spate stanga
                line = linie_rege - 1;
                col = col_rege + 1;
                if (line >= 0) {
                    if (col < 8) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                           // if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                           // }
                        }
                    }
                }

                // 7. sptate dreapta
                line = linie_rege - 1;
                col = col_rege - 1;
                if (line >= 0) {
                    if (col >= 0) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                           // if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                           // }
                        }
                    }
                }

                // 8. stanga
                line = linie_rege;
                col = col_rege - 1;
                if (col >= 0) {
                    if (chessBoard.getSquare(line, col).getPiece() == null) {
                       // if (!i_am_in_chess(line, col)) {
                            positions.add(line);
                            positions.add(col);
                            return positions;
                        //}
                    }
                }
            }
        }

        return null;
    }

    /**
     * mutari banale cu cai
     * @param linie_rege
     * @param col_rege
     * @return
     */
    public List<Integer> mutare_banala_cal(int linie_rege, int col_rege) {
        List<Integer> positions = new ArrayList<>();

        int line = linie_rege;
        int col = col_rege;

        if (line >= 0 && line < 8) {
            if (col >= 0 && col < 8) {
                // 1. fata drept stanga
                line = linie_rege + 2;
                col = col_rege - 1;
                if (line < 8) {
                    if (col >= 0) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                        //    if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                           // }
                        }
                    }
                }

                // 2. fata drept dreapta
                line = linie_rege - 2;
                col = col_rege + 1;
                if (line >= 0) {
                    if (col < 8) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                            //if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                           // }
                        }
                    }
                }

                // 3. spate drept stanga
                line = linie_rege + 2;
                col = col_rege + 1;
                if (line < 8) {
                    if (chessBoard.getSquare(line, col).getPiece() == null) {
                        if (col < 8) {
                           // if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                           // }
                        }
                    }
                }

                // 4. dreapta
                line = linie_rege + 2;
                col = col_rege - 1;
                if (line < 8) {
                    if (chessBoard.getSquare(line, col).getPiece() == null) {
                        if (col >= 0) {
                          //  if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                          //  }
                        }
                    }
                }

                // 5. fata stanga lat
                line = linie_rege - 1;
                col = col_rege - 2;
                if (line >= 0) {
                    if (col >= 0) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                          //  if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                           // }
                        }
                    }
                }

                // 6. spate stanga
                line = linie_rege - 1;
                col = col_rege + 2;
                if (line >= 0) {
                    if (col < 8) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                          //  if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                          //  }
                        }
                    }
                }

                // 7. sptate dreapta
                line = linie_rege + 1;
                col = col_rege - 2;
                if (line < 8) {
                    if (col >= 0) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                          //  if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                            //}
                        }
                    }
                }

                // 8. stanga
                line = linie_rege + 1;
                col = col_rege + 2;
                if (line < 8) {
                    if (col < 8) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                          //  if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                           // }
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * mutari banele cu nebuni
     * @param linie_rege
     * @param col_rege
     * @return
     */
    public List<Integer> mutare_banala_nebun(int linie_rege, int col_rege) {
        List<Integer> positions = new ArrayList<>();

        int line = linie_rege;
        int col = col_rege;

        if (line >= 0 && line < 8) {
            if (col >= 0 && col < 8) {
                // 2. fata stanga
                line = linie_rege + 1;
                col = col_rege + 1;
                if (line < 8) {
                    if (col < 8) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                           // if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                           // }
                        }
                    }
                }

                // 3. fata dreapta
                line = linie_rege + 1;
                col = col_rege - 1;
                if (line < 8) {
                    if (col >= 0) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                          //  if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                            //}
                        }
                    }
                }

                // 6. spate stanga
                line = linie_rege - 1;
                col = col_rege + 1;
                if (line >= 0) {
                    if (col < 8) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                            //if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                          //  }
                        }
                    }
                }

                // 7. sptate dreapta
                line = linie_rege - 1;
                col = col_rege - 1;
                if (line >= 0) {
                    if (col >= 0) {
                        if (chessBoard.getSquare(line, col).getPiece() == null) {
                           // if (!i_am_in_chess(line, col)) {
                                positions.add(line);
                                positions.add(col);
                                return positions;
                            //}
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * functia principala care foloseste toate functiile de mai sus pentru a putea transmite engine-ului ce mutare
     * valida poate face
     * @param color
     * @return
     */
    public String thinkAndMove(String color) {
        String enemy;
        if (color.equals("white")){
            enemy = "black";
        } else {
            enemy = "white";
        }
        List<Integer> sunt_in_sah = verifica_daca_sunt_in_sah();
        if (sunt_in_sah != null) {
            int source_line = sunt_in_sah.get(0);
            int source_dest = sunt_in_sah.get(1);
            int dest_line = sunt_in_sah.get(2);
            int dest_col = sunt_in_sah.get(3);
            if (source_line == -1 && source_dest == -1 && dest_col == -1 && dest_line == -1) {
                return "resign";
            } else {
                //am_mutat_regele = true;
                return sendMove(source_line, source_dest, dest_line, dest_col);
            }
        }
        // botul trebuie sa stie cu ce culoare joaca
        if (color.equals("white")) {
            // trec prin toata tabla si ma uit la piesele albe
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessBoard.getSquare(i, j).getPiece() != null) {
                        if (chessBoard.getSquare(i, j).isColor(color)) {
                            // rocada mare / rocada mica
                                List<Integer> rocada_misc = fa_rocada_alb(i, j);
                                if (rocada_misc != null) {
                                    int i1 = rocada_misc.get(0);
                                    int j1 = rocada_misc.get(1);
                                    int i2 = rocada_misc.get(2);
                                    int j2 = rocada_misc.get(3);
                                    am_facut_rocada = true;
                                    return  sendMove(i1, j1, i2, j2);
                                }

                            // mutari agresive cu pioni + en passant
                            List<Integer> move_pioni = atac_pioni_alb(i, j);
                            if (move_pioni != null) {
                                return sendMove(i, j, move_pioni.get(0), move_pioni.get(1));
                            }

                            // mutari agresive cu tura (index = 3)
                            List<Integer> move_ture = atac_ture_alb(i, j);
                            if (move_ture != null) {
                                chessBoard.move(i, j, move_ture.get(0), move_ture.get(1));
                                return sendMove(i, j, move_ture.get(0), move_ture.get(1));
                            }

                            // mutari agresive cu nebunul
                            List<Integer> move_nebuni = atac_nebuni_alb(i, j);
                            if (move_nebuni != null) {
                                return sendMove(i, j, move_nebuni.get(0), move_nebuni.get(1));
                            }

                            // mutari agresive cu calul
                            List<Integer> move_cai = atac_cai_alb(i, j);
                            if (move_cai != null) {
                                return sendMove(i, j, move_cai.get(0), move_cai.get(1));
                            }

                            // mutari agresive cu regina
                            List<Integer> move_regina = atac_regina_alb(i, j);
                            if (move_regina != null) {
                                return sendMove(i, j, move_regina.get(0), move_regina.get(1));
                            }
                        }
                    }
                }
            }
            // mut piesele din loc pentru a face rocada
            List<Integer> rocada_misc = rocada_alb();
            if (rocada_misc != null) {
                int i1 = rocada_misc.get(0);
                int j1 = rocada_misc.get(1);
                int i2 = rocada_misc.get(2);
                int j2 = rocada_misc.get(3);
                return sendMove(i1, j1, i2, j2);
            }

            // mutari banale pioni
            List<Integer> miscare_banala = miscari_banale_alb();
            if (miscare_banala != null) {
                int i1 = miscare_banala.get(0);
                int j1 = miscare_banala.get(1);
                int i2 = miscare_banala.get(2);
                int j2 = miscare_banala.get(3);
                return sendMove(i1, j1, i2, j2);
            }

            // mutari banale cu piesele albe
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessBoard.getSquare(i, j).getPiece() != null) {
                        if (chessBoard.getSquare(i, j).isColor(color)) {
                            // mutare banala cal
                            if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
                                List<Integer> atac_banal_cal = mutare_banala_cal(i, j);
                                if (atac_banal_cal != null) {
                                    chessBoard.move(i, j, atac_banal_cal.get(0), atac_banal_cal.get(1));
                                    return sendMove(i, j, atac_banal_cal.get(0), atac_banal_cal.get(1));
                                }
                            }
                            // mutare banala regina
                            if (chessBoard.getSquare(i, j).getPiece().getIndex() == 4) {
                                List<Integer> atac_banal_rege = mutare_banala_rege(i, j);
                                if (atac_banal_rege != null) {
                                    chessBoard.move(i, j, atac_banal_rege.get(0), atac_banal_rege.get(1));
                                    return sendMove(i, j, atac_banal_rege.get(0), atac_banal_rege.get(1));
                                }
                            }
                            // mutare banala nebun
                            if (chessBoard.getSquare(i, j).getPiece().getIndex() == 1) {
                                List<Integer> atac_banal_nebun = mutare_banala_nebun(i, j);
                                if (atac_banal_nebun != null) {
                                    chessBoard.move(i, j, atac_banal_nebun.get(0), atac_banal_nebun.get(1));
                                    return sendMove(i, j, atac_banal_nebun.get(0), atac_banal_nebun.get(1));
                                }
                            }

                            // mutare banala rege
                            if (chessBoard.getSquare(i, j).getPiece().getIndex() == 5) {
                                List<Integer> atac_banal_rege = resolve_chess(i, j);
                                if (atac_banal_rege != null) {
                                    chessBoard.move(i, j, atac_banal_rege.get(0), atac_banal_rege.get(1));
                                    return sendMove(i, j, atac_banal_rege.get(0), atac_banal_rege.get(1));
                                }
                            }
                        }
                    }
                }
            }
        } else {
            //cod pt culoare neagra, in mod analog ca cel pt alb, doar ca piesele se duc in jos in matrice
            //trece prin toate patratelele si verifica daca poate ataca
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    // muta doar piesele noastre(negre)
                    if (chessBoard.getSquare(i, j).getPiece() != null) {
                        if (chessBoard.getSquare(i, j).isColor(color)) {
                            // rocada mare / rocada mica
                            if (!am_facut_rocada) {
                                List<Integer> rocada_misc = fa_rocada_neagra(i, j);
                                if (rocada_misc != null) {
                                    int i1 = rocada_misc.get(0);
                                    int j1 = rocada_misc.get(1);
                                    int i2 = rocada_misc.get(2);
                                    int j2 = rocada_misc.get(3);
                                    return  sendMove(i1, j1, i2, j2);
                                }
                            }

                            // mutari agresive cu pioni + en passant
                            List<Integer> move_pioni = atac_pioni_negru(i, j);
                            if (move_pioni != null) {
                                return sendMove(i, j, move_pioni.get(2), move_pioni.get(3));
                            }

                            // mutari agresive cu tura (index = 3)
                            List<Integer> move_ture = atac_ture_alb(i, j);
                            if (move_ture != null) {
                                chessBoard.move(i, j, move_ture.get(0), move_ture.get(1));
                                return sendMove(i, j, move_ture.get(0), move_ture.get(1));
                            }

                            // mutari agresive cu nebunul
                            List<Integer> move_nebuni = atac_nebuni_alb(i, j);
                            if (move_nebuni != null) {
                                return sendMove(i, j, move_nebuni.get(0), move_nebuni.get(1));
                            }

                            // mutari agresive cu calul
                            List<Integer> move_cai = atac_cai_alb(i, j);
                            if (move_cai != null) {
                                return sendMove(i, j, move_cai.get(0), move_cai.get(1));
                            }

                            // mutari agresive cu regina
                            List<Integer> move_regina = atac_regina_alb(i, j);
                            if (move_regina != null) {
                                return sendMove(i, j, move_regina.get(0), move_regina.get(1));
                            }
                        }
                    }
                }
            }

            // mut piesele negre din loc pentru a face rocada
            List<Integer> rocada_misc = rocada_negru();
            if (rocada_misc != null) {
                int i1 = rocada_misc.get(0);
                int j1 = rocada_misc.get(1);
                int i2 = rocada_misc.get(2);
                int j2 = rocada_misc.get(3);
                return sendMove(i1, j1, i2, j2);
            }

                // mutari banele pioni negru
                List<Integer> banal = mutari_banale_negru();
                if (banal != null) {
                    int line_source = banal.get(0);
                    int col_source = banal.get(1);
                    int line_dest = banal.get(2);
                    int col_dest = banal.get(3);
                    return sendMove(line_source, col_source, line_dest, col_dest);
                }
                // mutari banale cu piesele negre
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessBoard.getSquare(i, j).getPiece() != null) {
                        if (chessBoard.getSquare(i, j).isColor(color)) {
                            // mutare banala cal
                            if (chessBoard.getSquare(i, j).getPiece().getIndex() == 2) {
                                List<Integer> atac_banal_cal = mutare_banala_cal(i, j);
                                if (atac_banal_cal != null) {
                                    chessBoard.move(i, j, atac_banal_cal.get(0), atac_banal_cal.get(1));
                                    return sendMove(i, j, atac_banal_cal.get(0), atac_banal_cal.get(1));
                                }
                            }

                            // mutare banala regina
                            if (chessBoard.getSquare(i, j).getPiece().getIndex() == 4) {
                                List<Integer> atac_banal_rege = mutare_banala_rege(i, j);
                                if (atac_banal_rege != null) {
                                    chessBoard.move(i, j, atac_banal_rege.get(0), atac_banal_rege.get(1));
                                    return sendMove(i, j, atac_banal_rege.get(0), atac_banal_rege.get(1));
                                }
                            }

                            // mutare banala nebun
                            if (chessBoard.getSquare(i, j).getPiece().getIndex() == 1) {
                                List<Integer> atac_banal_nebun = mutare_banala_nebun(i, j);
                                if (atac_banal_nebun != null) {
                                    chessBoard.move(i, j, atac_banal_nebun.get(0), atac_banal_nebun.get(1));
                                    return sendMove(i, j, atac_banal_nebun.get(0), atac_banal_nebun.get(1));
                                }
                            }

                            // mutare banala rege
                            if (chessBoard.getSquare(i, j).getPiece().getIndex() == 5) {
                                List<Integer> atac_banal_rege = resolve_chess(i, j);
                                if (atac_banal_rege != null) {
                                    chessBoard.move(i, j, atac_banal_rege.get(0), atac_banal_rege.get(1));
                                    return sendMove(i, j, atac_banal_rege.get(0), atac_banal_rege.get(1));
                                }
                            }
                        }
                    }
                }
            }
        }
        return "resign";
    }

    /**
     *Aceasta functie primeste coordonate pt sursa si destinatie si intoarce "move miscare"
     * unde miscare este transpunerea in model de sah. (exp e2e6)
     */
    public String sendMove(int lineSource, int colSource, int lineDest, int colDest)
    {
        String command = "move ";
        //transform coloana in litera

        lineSource = 8 - lineSource;
        lineDest = 8 - lineDest;

        switch (colSource) {
            case 0: {
                command = command + "a";
                break;
            }
            case 1: {
                command = command + "b";
                break;
            }
            case 2: {
                command = command + "c";
                break;
            }
            case 3: {
                command = command + "d";
                break;
            }
            case 4: {
                command = command + "e";
                break;
            }
            case 5: {
                command = command + "f";
                break;
            }
            case 6: {
                command = command + "g";
                break;
            }
            case 7: {
                command = command + "h";
                break;
            }
            default: {
                System.out.println("invalid move");
            }
        }
        //adaug linia
        command = command + lineSource;

        switch (colDest) {
            case 0: {
                command = command + "a";
                break;
            }
            case 1: {
                command = command + "b";
                break;
            }
            case 2: {
                command = command + "c";
                break;
            }
            case 3: {
                command = command + "d";
                break;
            }
            case 4: {
                command = command + "e";
                break;
            }
            case 5: {
                command = command + "f";
                break;
            }
            case 6: {
                command = command + "g";
                break;
            }
            case 7: {
                command = command + "h";
                break;
            }
            default:
                System.out.println("invalid move 2 final");
                break;
        }
        command = command + lineDest;
        return command;
    }
}
