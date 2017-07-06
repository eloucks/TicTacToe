package tictactoe;


import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/*
* Here is the theoretical order of operations for an optimal winning scenario.
* 1.) Check if the opponent made a move that is about to let them win. If they are, block them. Optionally, you can insert
*     logic to prevent them from having a two way win scenario here.
* 2.) Check if we can win this turn, if we can, win.
* 3.) Check if we have a 3-set (row, col, or diag) with one of our pieces in it and two remaining open squares. Move there.
* 4.) Check if we have a 3-set with all open squares. Move there if we do.
* 5.) If none of the above apply, move randomly.
*/


public class Ai {
    List<Integer[]> moves;
    Board board;
    Boolean imX;
    Square me;
    Square them;

    public Ai(Boolean imX){
        this.imX = imX;
        if (imX){
            me = Square.X;
            them = Square.O;
        } else {
            me = Square.O;
            them = Square.X;
        }
    }

    public Integer[] giveMove(Board board){ //assumes Ai is O, not X
        this.board = board;
        this.moves = board.openSquares();
        boolean boardisEmpty = true;
        for (int row = 0; row <3; row++){ // if board is empty always move 0,0
            for (int col = 0; col<3; col++){
                if (!board.isEmpty(row, col)){
                    boardisEmpty = false;
                }
            }
        }
        if (boardisEmpty) { return new Integer[]{0,0};}
        ListIterator<Integer[]> itr = moves.listIterator(); //get available squares
        Integer[] move = new Integer[2];

        while (itr.hasNext()){ //if there is a winning move, take it
            Integer[] ints = itr.next();
            putMe(ints[0], ints[1]); //here is issue. still specifying piece
            if (board.isWinner(me)){
                board.empty(ints[0], ints[1]);
                move[0] = ints[0]; move[1] = ints[1];
                return move;
            }
            board.empty(ints[0], ints[1]);
        }
        itr = moves.listIterator(); //reset iterator
        while (itr.hasNext()){ //if the opponent is about to win, stop them.
            Integer[] ints = itr.next();
            putThem(ints[0], ints[1]);
            if (board.isWinner(them)){
                board.empty(ints[0], ints[1]);
                move[0] = ints[0]; move[1] = ints[1];
                return move;
            }
            board.empty(ints[0], ints[1]);
        }
        //If inserting logic to prevent opponents 2 way win scenario, put it here
        moves = board.openSquares();
        itr = moves.listIterator(); //reset iterator
        while (itr.hasNext()){ //Take any move that gives the comp 2 out of three in a line, the last being free
            Integer[] ints = itr.next();
            if (!isGreatMove(ints[0], ints[1])){
                itr.remove();
            }
        }
        List<Integer[]> temp = new ArrayList<>();
        while (itr.hasPrevious()){
            temp.add(itr.previous());
        }
        if (!temp.isEmpty()) {
            Random random = new Random();
            Integer[] ints = temp.get(random.nextInt(temp.size()));
            move[0] = ints[0];
            move[1] = ints[1];
            return move;
        }
        moves = board.openSquares();
        itr = moves.listIterator(); //reset iterator
        while (itr.hasNext()){ //Take any move that gives the comp 1 out of three in a line, the other two being free
            Integer[] ints = itr.next();
            if (!isGoodMove(ints[0], ints[1])){
                itr.remove();
            }
        }
        temp = new ArrayList<>();
        while (itr.hasPrevious()){
            temp.add(itr.previous());
        }
        if (!temp.isEmpty()) {
            Random random = new Random();
            Integer[] ints = temp.get(random.nextInt(temp.size()));
            move[0] = ints[0];
            move[1] = ints[1];
            return move;
        }
        // If there are no great moves, and no good moves, just make a move. Probably cats game
        Random random = new Random();
        moves = board.openSquares();
        Integer[] ints = moves.get(random.nextInt(moves.size()));
        move[0] = ints[0];
        move[1] = ints[1];
        return move;
    }

    private boolean isGreatMove(int row, int col){
        if (row == 0){
            boolean rowClear = (board.board[0][0] != them && board.board[0][1] != them && board.board[0][2] != them)
                    && (board.board[0][0] == me || board.board[0][1] == me || board.board[0][2] == me);
            if (rowClear){
                return true;
            }
        }
        if (row == 1){
            boolean rowClear = (board.board[1][0] != them && board.board[1][1] != them && board.board[1][2] != them)
                    && (board.board[1][0] == me || board.board[1][1] == me || board.board[1][2] == me);
            if (rowClear){
                return true;
            }
        }
        if (row == 2){
            boolean rowClear = (board.board[2][0] != them && board.board[2][1] != them && board.board[2][2] != them)
                    && (board.board[2][0] == me || board.board[2][1] == me || board.board[2][2] == me);
            if (rowClear){
                return true;
            }
        }
        if (col == 0){
            boolean colClear = (board.board[0][0] != them && board.board[1][0] != them && board.board[2][0] != them)
                    && (board.board[0][0] == me || board.board[1][0] == me || board.board[2][0] == me);
            if (colClear){
                return true;
            }
        }
        if (col == 1){
            boolean colClear = (board.board[0][1] != them && board.board[1][1] != them && board.board[2][1] != them)
                    && (board.board[0][1] == me || board.board[1][1] == me || board.board[2][1] == me);
            if (colClear){
                return true;
            }
        }
        if (col == 2){
            boolean colClear = (board.board[0][2] != them && board.board[1][2] != them && board.board[2][2] != them)
                    && (board.board[0][2] == me || board.board[1][2] == me || board.board[2][2] == me);
            if (colClear){
                return true;
            }
        }
        if (row == col){
            boolean diagClear = (board.board[0][0] != them && board.board[1][1] != them && board.board[2][2] != them)
                    && (board.board[0][0] == me || board.board[1][1] == me || board.board[2][2] == me);
            if (diagClear){
                return true;
            }
        }
        if (row +col == 2){
            boolean diagClear = (board.board[2][0] != them && board.board[1][1] != them && board.board[0][2] != them)
                    && (board.board[2][0] == me || board.board[1][1] == me || board.board[0][2] == me);
            if (diagClear){
                return true;
            }
        }
        return false;
    }

    private boolean isGoodMove(int row, int col){
        if (row == 0){
            boolean rowClear = board.board[0][0] != them && board.board[0][1] != them && board.board[0][2] != them;
            if (rowClear){
                return true;
            }
        }
        if (row == 1){
            boolean rowClear = board.board[1][0] != them && board.board[1][1] != them && board.board[1][2] != them;
            if (rowClear){
                return true;
            }
        }
        if (row == 2){
            boolean rowClear = board.board[2][0] != them && board.board[2][1] != them && board.board[2][2] != them;
            if (rowClear){
                return true;
            }
        }
        if (col == 0){
            boolean colClear = board.board[0][0] != them && board.board[1][0] != them && board.board[2][0] != them;
            if (colClear){
                return true;
            }
        }
        if (col == 1){
            boolean colClear = board.board[0][1] != them && board.board[1][1] != them && board.board[2][1] != them;
            if (colClear){
                return true;
            }
        }
        if (col == 2){
            boolean colClear = board.board[0][2] != them && board.board[1][2] != them && board.board[2][2] != them;
            if (colClear){
                return true;
            }
        }
        if (row == col){
            boolean diagClear = board.board[0][0] != them && board.board[1][1] != them && board.board[2][2] != them;
            if (diagClear){
                return true;
            }
        }
        if (row +col == 2){
            boolean diagClear = board.board[2][0] != them && board.board[1][1] != them && board.board[0][2] != them;
            if (diagClear){
                return true;
            }
        }
        return false;
    }

    private void putMe(int row, int col){
        if (me == Square.X){
            board.putX(row, col);
        } else {
            board.putO(row, col);
        }
    }

    private void putThem(int row, int col){
        if (them == Square.X){
            board.putX(row, col);
        } else {
            board.putO(row, col);
        }
    }
}
