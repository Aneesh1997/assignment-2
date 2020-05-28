import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class BasicNPCAdaptor implements IPlayerAdaptor{
    private Card selected;
    private NPC theNPC;

    public BasicNPCAdaptor(Hand hand, int playerID, int score) {
        theNPC = new BasicNPC(hand, playerID, score);
    }


    @Override
    public Hand getHand() {
        return theNPC.getPlayingHand();
    }

    @Override
    public Card play() {
        selected = theNPC.getCard();
        return selected;
    }
}
