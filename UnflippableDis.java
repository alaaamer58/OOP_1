public class UnflippableDis implements Disc{

    private Player owner;

    public UnflippableDis(Player owner)
    {
        this.owner= owner;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Player player) {
        this.owner = player;
    }

    @Override
    public String getType() {
        return "⭕";
    }
}
