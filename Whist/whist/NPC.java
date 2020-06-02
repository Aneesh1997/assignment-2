import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class NPC extends Player{
    private ICardStrategy strategy;

    public NPC(int playerID, Hand playingHand, ICardStrategy strategy) {
        super(playerID, playingHand);
        this.strategy = strategy;
    }

    @Override
    public Card playCard() {
        GUI.getInstance().setStatus("Player " + this.playerID + " thinking...");
        GUI.getInstance().delay(ThinkingTime);
        return strategy.playCard(this);
    }

    @Override
    public Card playCard(Whist.Suit lead, Card winningCard, Whist.Suit trumps) {
        GUI.getInstance().setStatus("Player " + this.playerID + " thinking...");
        GUI.getInstance().delay(ThinkingTime);
        return strategy.playCard(this, lead, winningCard, trumps);
    }
}
