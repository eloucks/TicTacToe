package tictactoe;

public class Exe {
    public static void main(String[] args) { //Comp is O
        Board board = new Board();
        Scanner scanner = new Scanner(System.in);
        board.print();
        System.out.println("Let's play Tic-Tac-Toe!\nYou can go first, but I get to be O :)");
        System.out.println("Your move. Please enter row, then col");
        int row = scanner.nextInt();
        int col = scanner.nextInt();
        board.putX(row, col);
        while (board.winner() == Winner.NONE){
            Ai ai = new Ai(board.openSquares(), board);
            Integer[] move = ai.giveMove();
            board.putO(move[0], move[1]);
            board.print();
            if (board.winner() != Winner.NONE) {break;}
            if (board.openSquares().size() == 0) {break;}
            //persons turn
            board.print();
            System.out.println("Your move. Please enter row, then col");
            row = scanner.nextInt();
            col = scanner.nextInt();
            if (board.winner() != Winner.NONE) {break;}
            if (board.openSquares().size() == 0) {break;}
            board.print();
        }
        if (board.winner() == Winner.X){
            System.out.println("X Wins!");
            board.print();
        } else if (board.winner() == Winner.O){
            System.out.println("O Wins!");
            board.print();
        } else {
            System.out.println("Cat's Game, yo");
        }
    }
}
