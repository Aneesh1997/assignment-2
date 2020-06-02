import ch.aplu.jcardgame.Card;

public interface ICardStrategy {
    int ThinkingTime = 2000;
    public Card playCard(NPC npc);
    public Card playCard(NPC npc, Whist.Suit lead, Card winningCard, Whist.Suit trumps);
}
