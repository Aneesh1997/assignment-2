import ch.aplu.jcardgame.Card;

public class BasicCardStrategy implements ICardStrategy {
    @Override
    public Card playCard(NPC npc) {
        return Whist.randomCard(npc.getPlayingHand());
    }

    @Override
    public Card playCard(NPC npc, Whist.Suit lead, Card winningCard, Whist.Suit trumps) {
        return Whist.randomCard(npc.getPlayingHand());
    }
}
