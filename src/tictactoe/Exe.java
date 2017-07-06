package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Exe {

    public static boolean aiManualMoves = false;

    public static void main(String[] args) { //Comp is O
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Boolean ericAIisX;
        System.out.println("Let's play Tic-Tac-Toe!\n");
        Integer rand = random.nextInt(2); // range exclusive, will give 1 or 0
        if (rand == 0) {ericAIisX = true;} else { ericAIisX = false;}
        Ai ai = new Ai(ericAIisX);
        if (ericAIisX) {
            System.out.println("EricAI is X, and gets to move first");
            Integer[] move = ai.giveMove(board);
            board.putX(move[0], move[1]);
            System.out.println("Board after Eric's AI's turn:");
            board.print();
        } else {
            System.out.println("EricAI is O, and you (or your AI) move move first");
        }

        if(aiManualMoves){ //if you want to make manual moves against your AI
            System.out.println("Your move. Please enter row, hit enter, then col, then enter");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            if (ericAIisX){
                board.putO(row, col);
            } else {
                board.putX(row, col);
            }
            while (board.winner() == Winner.NONE){
                Integer[] move = ai.giveMove(board);
                if (ericAIisX){
                    board.putX(move[0], move[1]);
                } else {
                    board.putO(move[0], move[1]);
                }
                if ((board.winner() != Winner.NONE) || (board.openSquares().size() == 0)) {break;}
                //persons turn
                board.print();
                System.out.println("Your move. Please enter row, hit enter, then col, then enter");
                row = scanner.nextInt();
                col = scanner.nextInt();
                if (ericAIisX){
                    board.putO(row, col);
                } else {
                    board.putX(row, col);
                }
                if ((board.winner() != Winner.NONE) || (board.openSquares().size() == 0)) {break;}
                System.out.println("Board after Eric's AI's turn:");
                board.print();
            }
            if (board.winner() == Winner.X){
                System.out.println("X Wins!");
                if (ericAIisX) { System.out.println("(Eric won)");} else {System.out.println("(You won)");}
                board.print();
            } else if (board.winner() == Winner.O){
                System.out.println("O Wins!");
                if (ericAIisX) { System.out.println("(You won)");} else {System.out.println("(Eric won)");}
                board.print();
            } else {
                System.out.println("Cat's Game, yo");
            }
        } else { //if you want your AI to compete with another
            AiRandom yourAI = new AiRandom(!ericAIisX); // INSERT YOUR AI HERE!!!!!!!!!
            System.out.println("Board after Your AI's turn:");
            Integer[] move = yourAI.giveMove(board);
            if (ericAIisX){
                board.putO(move[0], move[1]);
            } else {
                board.putX(move[0], move[1]);
            }
            board.print();
            while (board.winner() == Winner.NONE){
                move = ai.giveMove(board);
                if (ericAIisX){
                    board.putX(move[0], move[1]);
                } else {
                    board.putO(move[0], move[1]);
                }
                if ((board.winner() != Winner.NONE) || (board.openSquares().size() == 0)) {break;}
                System.out.println("Board after Eric's AI's turn:");
                board.print();
                //Competing AI turn
                System.out.println("Board after Your AI's turn:");
                move = yourAI.giveMove(board);
                if (ericAIisX){
                    board.putO(move[0], move[1]);
                } else {
                    board.putX(move[0], move[1]);
                }
                if ((board.winner() != Winner.NONE) || (board.openSquares().size() == 0)) {break;}
                board.print();
            }
            if (board.winner() == Winner.X){
                System.out.println("X Wins!");
                if (ericAIisX) { System.out.println("(Eric won)");} else {System.out.println("(You won)");}
                board.print();
            } else if (board.winner() == Winner.O){
                System.out.println("O Wins!");
                if (ericAIisX) { System.out.println("(You won)");} else {System.out.println("(Eric won)");}
                board.print();
            } else {
                System.out.println("Cat's Game, yo");
            }
        }
    }
}
