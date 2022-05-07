import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scaner = new Scanner(System.in);
        String color = "white";
        Game game = new Game(color);
        boolean am_primit_force = false;

        while (true) {
            String comand = scaner.nextLine();
            System.out.println("||||||||||||||||||||||" + comand);
            if (!comand.startsWith("result")) {
                if (!comand.startsWith("result 0-1")) {
                    if (!comand.startsWith("time")) {
                        if (!comand.startsWith("otim")) {
                            //aici mpunem switch
                            switch (comand) {
                                case "xboard":
                                    break;
                                case "protover 2":
                                    System.out.println("feature sigint=0");
                                    System.out.println("feature san=0");
                                    System.out.println("feature usermove=0");
                                    break;
                                case "accepted sigint":
                                    break;
                                case "accepted san":
                                    break;
                                case "accepted usermove":
                                    break;
                                case "new":
                                    color = "black";
                                    game = null;
                                    game = new Game(color);
                                    am_primit_force = false;
                                    break;
                                case "random":
                                    break;
                                case "level 40 5 0":
                                    break;
                                case "post":
                                    break;
                                case "hard":
                                    break;
                                case "easy":
                                    break;
                                case "black":
                                    color = "black";
                                    game.setColor("black");
                                    break;
                                case "force":
                                    color = "black";
                                    am_primit_force = true;
                                    break;
                                case "white":
                                    color = "white";
                                    game.setColor("white");
                                    break;
                                //de adagat timpe
                                case "quit":
                                    break;
                                case "go":
                                    am_primit_force = false;
                                    System.out.println(game.thinkAndMove(color));
                                    break;
                                case "book hit = (NULL)":
                                    break;

                                case "undo":  // dupa ce primesc force si vreau sa editez jocul
                                    break;
                                case "edit":
                                    break;
                                case "result 1-0 {Black resigns}":
                                    break;
                                case "result 0-1 {White resigns}":
                                    break;
                                case "computer":
                                    break;
                                case "draw":
                                    break;
                                default:
                                    if (am_primit_force) {
                                        game.move = comand;
                                        game.interpretMove(comand);
                                        game.conditiile_pt_rocada_negru(comand);
                                        game.conditiile_pt_rocada_alba(comand);
                                        game.set_last_command(comand);
                                    } else {
                                        game.move = comand;
                                        game.interpretMove(comand);
                                        game.conditiile_pt_rocada_negru(comand);
                                        game.conditiile_pt_rocada_alba(comand);
                                        System.out.println(game.thinkAndMove(color));
                                    }
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }
}
