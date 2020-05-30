import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.Random;

public class BasicNPC extends Player{

    public BasicNPC(int playerID, Hand playingHand) {
        super(playerID, playingHand);
    }

    @Override
    public  Card playCard() {
        return Whist.randomCard(getPlayingHand());
    }

    public Card playCard(Whist.Suit lead, Card winningCard) { return Whist.randomCard(getPlayingHand()); }
}
