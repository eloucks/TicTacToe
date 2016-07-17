package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;

public class Board {
    public Square[][] board;

    public Board(){
        board = new Square[3][3];
        for (int row = 0; row < 3; row++){
            for (int col = 0; col <3; col++){
                board[row][col] = Square.EMPTY;
            }
        }
    }

    public Board getBoard(){
        return this;
    }

    public void print(){
        System.out.println();
        for (int row = 0; row < 3; row++){
            System.out.print("|");
            for (int col = 0; col <3; col++){
                if (board[row][col] == Square.EMPTY){
                    System.out.print(" |");
                } else {
                    System.out.print(board[row][col]+ "|");
                }
            }
            System.out.println();
            System.out.println("-------");
        }
    }

    public void putX( int row, int col){
        board[row][col] = Square.X;
    }

    public void putO( int row, int col){
        board[row][col] = Square.O;
    }

    public void empty( int row, int col){
        board[row][col] = Square.EMPTY;
    }

    public void emptyBoard(){
        for (int row = 0; row < 3; row++){
            for (int col = 0; col <3; col++){
                board[row][col] = Square.EMPTY;
            }
        }
    }

    public boolean isEmpty(int row, int col){
        if (board[row][col] == Square.EMPTY){
            return true;
        }
        return false;
    }

    public List<Integer[]> openSquares(){
        List<Integer[]> list = new ArrayList<>();
        for (int row = 0; row < 3; row++){
            for (int col = 0; col <3; col++){
                if (board[row][col] == Square.EMPTY){
                    Integer[] entry = new Integer[2];
                    entry[0] = row; entry[1] = col;
                    list.add(entry);
                }
            }
        }
        return list;
    }

    public Winner winner(){
        if(isWinner(Square.O)){
            return Winner.O;
        }
        if(isWinner(Square.X)){
            return Winner.X;
        }
        return Winner.NONE;
    }

    public boolean isWinner(Square input){
        if (board[0][0] == input){
            if (board[0][1] == input){
                if (board[0][2] == input){
                    return true;
                }
            }
            if (board[1][0] == input){
                if (board[2][0] == input){
                    return true;
                }
            }
            if (board[1][1] == input){
                if (board[2][2] == input){
                    return true;
                }
            }
        }
        if (board[1][0] == input){
            if (board[1][1] == input){
                if (board[1][2] == input){
                    return true;
                }
            }
        }
        if (board[2][0] == input){
            if (board[2][1] == input){
                if (board[2][2] == input){
                    return true;
                }
            }
        }
        if (board[0][1] == input){
            if (board[1][1] == input){
                if (board[2][1] == input){
                    return true;
                }
            }
        }
        if (board[0][2] == input){
            if (board[1][2] == input){
                if (board[2][2] == input){
                    return true;
                }
            }
            if (board[1][1] == input){ //other diagonal
                if (board[2][0] == input){
                    return true;
                }
            }
        }
        return false;
    }
}
