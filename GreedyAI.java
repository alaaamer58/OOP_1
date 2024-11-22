import java.util.List;

public class GreedyAI extends AIPlayer{

    public GreedyAI (boolean isPlayer1)
    {
        super(isPlayer1);
    }

    @Override
    public Move makeMove(PlayableLogic gameLogic) {
        List<Position> validMove = gameLogic.ValidMoves();

        Position bestMove = null;
        int maxFlips = 0 ;

        for (Position position : validMove)
        {
            int flips = gameLogic.countFlips(position);
            if (maxFlips<flips)
            {
                maxFlips = flips;
                bestMove = position;
            }
        }
        Disc disc = new SimpleDisc(this);

        // if we found the best move , return it
        if (bestMove!= null)
        {
            Move move = new Move(bestMove,disc);
        }

        // no valid moves available
        return null;
    }


}
