package tictactoe;


import java.util.*;

public class AiRandom {
    List<Integer[]> moves;
    Board board;
    Boolean imX;
    Square me;
    Square them;

    public AiRandom(Boolean imX) {
        this.imX = imX;
        if (imX) {
            me = Square.X;
            them = Square.O;
        } else {
            me = Square.O;
            them = Square.X;
        }
    }

    public Integer[] giveMove(Board board) { //assumes Ai is O, not X
        this.board = board;
        this.moves = board.openSquares();
        Random random = new Random();
        return moves.get(random.nextInt(moves.size()));
    }
}
