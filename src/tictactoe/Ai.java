package tictactoe;


import java.util.*;

public class Ai {
    List<Integer[]> moves;
    Board board;

    public Ai(List<Integer[]> moves, Board board){
        this.moves = moves;
        this.board = board;
    }

    public Integer[] giveMove(){ //assumes comp is O, not X
        boolean isEmpty = true;
        for (int row = 0; row <3; row++){ // if board is empty always move 0,0
            for (int col = 0; col<3; col++){
                if (!board.isEmpty(row, col)){
                    isEmpty = false;
                }
            }
        }
        if (isEmpty) { return new Integer[]{0,0};}

        ListIterator<Integer[]> itr = moves.listIterator();
        Integer[] move = new Integer[2];
        while (itr.hasNext()){ //if there is a winning move, take it
            Integer[] ints = itr.next();
            board.putO(ints[0], ints[1]);
            if (board.isWinner(Square.O)){
                board.empty(ints[0], ints[1]);
                move[0] = ints[0]; move[1] = ints[1];
                return move;
            }
            board.empty(ints[0], ints[1]);
        }
        itr = moves.listIterator(); //reset iterator
        while (itr.hasNext()){ //if the opponent is about to win, stop them.
            Integer[] ints = itr.next();
            board.putX(ints[0], ints[1]);
            if (board.isWinner(Square.X)){
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
            boolean rowClear = (board.board[0][0] != Square.X && board.board[0][1] != Square.X && board.board[0][2] != Square.X)
                    && (board.board[0][0] == Square.O || board.board[0][1] == Square.O || board.board[0][2] == Square.O);
            if (rowClear){
                return true;
            }
        }
        if (row == 1){
            boolean rowClear = (board.board[1][0] != Square.X && board.board[1][1] != Square.X && board.board[1][2] != Square.X)
                    && (board.board[1][0] == Square.O || board.board[1][1] == Square.O || board.board[1][2] == Square.O);
            if (rowClear){
                return true;
            }
        }
        if (row == 2){
            boolean rowClear = (board.board[2][0] != Square.X && board.board[2][1] != Square.X && board.board[2][2] != Square.X)
                    && (board.board[2][0] == Square.O || board.board[2][1] == Square.O || board.board[2][2] == Square.O);
            if (rowClear){
                return true;
            }
        }
        if (col == 0){
            boolean colClear = (board.board[0][0] != Square.X && board.board[1][0] != Square.X && board.board[2][0] != Square.X)
                    && (board.board[0][0] == Square.O || board.board[1][0] == Square.O || board.board[2][0] == Square.O);
            if (colClear){
                return true;
            }
        }
        if (col == 1){
            boolean colClear = (board.board[0][1] != Square.X && board.board[1][1] != Square.X && board.board[2][1] != Square.X)
                    && (board.board[0][1] == Square.O || board.board[1][1] == Square.O || board.board[2][1] == Square.O);
            if (colClear){
                return true;
            }
        }
        if (col == 2){
            boolean colClear = (board.board[0][2] != Square.X && board.board[1][2] != Square.X && board.board[2][2] != Square.X)
                    && (board.board[0][2] == Square.O || board.board[1][2] == Square.O || board.board[2][2] == Square.O);
            if (colClear){
                return true;
            }
        }
        if (row == col){
            boolean diagClear = (board.board[0][0] != Square.X && board.board[1][1] != Square.X && board.board[2][2] != Square.X)
                    && (board.board[0][0] == Square.O || board.board[1][1] == Square.O || board.board[2][2] == Square.O);
            if (diagClear){
                return true;
            }
        }
        if (row +col == 2){
            boolean diagClear = (board.board[2][0] != Square.X && board.board[1][1] != Square.X && board.board[0][2] != Square.X)
                    && (board.board[2][0] == Square.O || board.board[1][1] == Square.O || board.board[0][2] == Square.O);
            if (diagClear){
                return true;
            }
        }
        return false;
    }

    private boolean isGoodMove(int row, int col){
        if (row == 0){
            boolean rowClear = board.board[0][0] != Square.X && board.board[0][1] != Square.X && board.board[0][2] != Square.X;
            if (rowClear){
                return true;
            }
        }
        if (row == 1){
            boolean rowClear = board.board[1][0] != Square.X && board.board[1][1] != Square.X && board.board[1][2] != Square.X;
            if (rowClear){
                return true;
            }
        }
        if (row == 2){
            boolean rowClear = board.board[2][0] != Square.X && board.board[2][1] != Square.X && board.board[2][2] != Square.X;
            if (rowClear){
                return true;
            }
        }
        if (col == 0){
            boolean colClear = board.board[0][0] != Square.X && board.board[1][0] != Square.X && board.board[2][0] != Square.X;
            if (colClear){
                return true;
            }
        }
        if (col == 1){
            boolean colClear = board.board[0][1] != Square.X && board.board[1][1] != Square.X && board.board[2][1] != Square.X;
            if (colClear){
                return true;
            }
        }
        if (col == 2){
            boolean colClear = board.board[0][2] != Square.X && board.board[1][2] != Square.X && board.board[2][2] != Square.X;
            if (colClear){
                return true;
            }
        }
        if (row == col){
            boolean diagClear = board.board[0][0] != Square.X && board.board[1][1] != Square.X && board.board[2][2] != Square.X;
            if (diagClear){
                return true;
            }
        }
        if (row +col == 2){
            boolean diagClear = board.board[2][0] != Square.X && board.board[1][1] != Square.X && board.board[0][2] != Square.X;
            if (diagClear){
                return true;
            }
        }
        return false;
    }
}
