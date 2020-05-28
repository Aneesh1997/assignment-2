import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class NPC {
    private Hand playingHand;
    private int playerID;
    private int score;
    static final Random random = ThreadLocalRandom.current();

    public NPC(Hand hand, int playerID, int score) {
        this.playingHand = hand;
        this.playerID = playerID;
        this.score = score;
    }

    public void setPlayingHand() {this.playingHand.setTouchEnabled(true); }

    public Hand getPlayingHand() {return this.playingHand; }

    public abstract Card getCard();
}
