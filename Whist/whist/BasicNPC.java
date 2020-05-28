import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.Random;

public class BasicNPC extends NPC{

    public BasicNPC(Hand hand, int playerID, int score) {
        super(hand, playerID, score);
    }


    public  Card getCard() {
        int x = random.nextInt(this.getPlayingHand().getNumberOfCards());
        return this.getPlayingHand().get(x);
    }
}
