import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameLogic implements PlayableLogic{
    private  Disc[][] board; //8x8

    private int[][] directions = {{-1, 0}, // Up
            {1, 0},  // Down
            {0, -1}, // Left
            {0, 1},  // Right
            {-1, -1}, // Up-Left
            {-1, 1},  // Up-Right
            {1, -1},  // Down-Left
            {1, 1}    // Down-Right
    };

    private Player player1;

    private Player player2;

    private boolean isPlayer1Turn; //return true if its player1's turn

    private int boardSize = 8 ;

    private Stack<Move> moveStack = new Stack<>();

    public GameLogic()
    {
        board = new Disc[boardSize][boardSize];
        initializeBoard();
        isPlayer1Turn =true;
    }

    private void initializeBoard()
    {
        player1 = new HumanPlayer(true);
        player2 = new HumanPlayer(false);
        board[3][3] = new SimpleDisc(player1);
        board[3][4] = new SimpleDisc(player2);
        board[4][3] = new SimpleDisc(player2);
        board[4][4] = new SimpleDisc(player1);
    }

    private void flipDisc (Position p , Disc disc) {
        int row = p.row();
        int col = p.col();
        Player currPlayer = disc.getOwner();

        for (int[] direction : directions) {
            List<Position> discsFlips = new ArrayList<>();
            int drow = direction[0];
            int dcol = direction[1];
            int currRow = row + drow;
            int currCol = col + dcol;

            while (isInBounds(currCol, currRow)) {
                Disc currDisc = board[currRow][currCol];

                if (currDisc == null) {
                    break;
                }

                if (currDisc.getOwner().isPlayerOne() == currPlayer.isPlayerOne()) {
                    for (Position pos : discsFlips) {
                        board[pos.row()][pos.col()] = disc;
                    }
                    break;
                } else {
                    discsFlips.add((new Position(currRow, currCol)));
                }
                currCol += dcol;
                currRow += drow;
            }
        }

        if (disc instanceof BombDisc) {
            for (int[] direction : directions) {
                List<Position> discsFlips = new ArrayList<>();
                int drow = direction[0];
                int dcol = direction[1];
                int currRow = row + drow;
                int currCol = col + dcol;

                while (isInBounds(currCol, currRow)) {
                    Disc currDisc = board[currRow][currCol];

                    if (currDisc == null) {
                        break;
                    }

                    if (currDisc.getOwner().isPlayerOne() == currPlayer.isPlayerOne()) {
                        for (Position pos : discsFlips) {
                            board[pos.row()][pos.col()] = disc;
                        }
                    } else {
                        discsFlips.add((new Position(currRow, currCol)));
                    }
                    currCol += dcol;
                    currRow += drow;
                }
            }

        }
        if (disc instanceof UnflippableDis)
        {
            return;
        }
    }
    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[row].length;
    }


    private boolean isMoveValid(Position position) {
        return position.row() >= 0 && position.row() < boardSize &&
                position.col() >= 0 && position.col() < boardSize &&
                board[position.row()][position.col()] == null;
    }

    public boolean checkMoveValidity(Position position) {
        return isMoveValid(position);
    }

    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if(!isMoveValid(a))
            return false ;

        board[a.row()][a.col()] = disc;

        flipDisc(a,disc);
        isPlayer1Turn = !isPlayer1Turn;
        return true;
    }

    @Override
    public Disc getDiscAtPosition(Position position)
    {
        return board[position.row()][position.col()];
    }

    @Override
    public int getBoardSize() {
        return boardSize;
    }

    @Override
    public List<Position> ValidMoves()
    {
        List <Position> validMOves = new ArrayList<>();

        for (int i = 0 ; i < boardSize; i++)
        {
            for (int j = 0 ; j < boardSize ; j++)
            {
                if (isMoveValid(new Position(i,j))) // new position (row,col)
                {
                    validMOves.add(new Position(i,j));
                }
            }
        }
        return validMOves;
    }

    @Override
    public int countFlips(Position a)
    {
        int cFlips = 0;
        Player currentPlayer = isPlayer1Turn ? player1 : player2;
        int col = a.col();
        int row = a.row();

        for (int [] direction: directions)
        {
            int direcRow = direction[0];
            int direcCol = direction[1];

            int currRow = row+direcRow;
            int currCol = col+direcCol;


        }

        return cFlips;
    }

    @Override
    public Player getFirstPlayer() {
        return player1;
    }

    @Override
    public Player getSecondPlayer() {
        return player2;
    }

    @Override
    public void setPlayers(Player player1, Player player2) {
        this.player1 =player1;
        this.player2=player2;
    }

    @Override
    public boolean isFirstPlayerTurn() {
        return isPlayer1Turn;
    }

    @Override
    public boolean isGameFinished() {
        return ValidMoves().isEmpty();
    }

    @Override
    public void reset() {
        board = new Disc[boardSize][boardSize];
        initializeBoard();

        isPlayer1Turn = true;
    }

    @Override
    public void undoLastMove() {
        if(!moveStack.isEmpty())
        {
            Move lastMove = moveStack.pop();
            Position lastPosition = lastMove.position();
            Disc lastDisc = lastMove.disc();

            board[lastPosition.row()][lastPosition.col()] = null;
            flipDisc(lastPosition,lastDisc);
            changeTurn();

        }
    }

    public void changeTurn()
    {
        isPlayer1Turn = !isPlayer1Turn;
    }


}
