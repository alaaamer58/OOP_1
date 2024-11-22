import java.util.List;
import java.util.Random;

public class RandomAI extends AIPlayer{

    private final Random random;

    public RandomAI(boolean isPlayer1)
    {
        super(isPlayer1);

        this.random= new Random();
    }

    @Override
    public Move makeMove (PlayableLogic gameLogic)
    {
        List<Position> validMoves = gameLogic.ValidMoves();
        if (validMoves.isEmpty()) {
            return null;
        }


        Position randomPosition = validMoves.get(random.nextInt(validMoves.size()));
        return new Move(randomPosition, new SimpleDisc(this));
    }
}
