import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.Random;

public class BasicNPC extends Player{
    private int ThinkingTime = 2000;

    public BasicNPC(int playerID, Hand playingHand) {
        super(playerID, playingHand);
    }

    @Override
    public  Card playCard() {
        GUI.getInstance().setStatus("Player " + this.playerID + " thinking...");
        GUI.getInstance().delay(ThinkingTime);
        return Whist.randomCard(getPlayingHand());
    }

    public Card playCard(Whist.Suit lead, Card winningCard,Whist.Suit trumps) {
        GUI.getInstance().setStatus("Player " + this.playerID + " thinking...");
        GUI.getInstance().delay(ThinkingTime);
        return Whist.randomCard(getPlayingHand()); }
}
