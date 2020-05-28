import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.io.StreamCorruptedException;

import static ch.aplu.jgamegrid.GameGrid.delay;


public class HumanAdaptor implements IPlayerAdaptor {
    private Player thePlayer;
    private Card selected;

    public HumanAdaptor(int playerID, Hand playingHand, int score) {
        thePlayer = new Player(playerID, playingHand, score);
    }

    public void setPlayerHand() {
        thePlayer.setPlayingHand();
    }
    public Card play() {
        // TODO: implement this
        thePlayer.setPlayingHand();
        CardListener cardListener = new CardAdapter()  // Human Player plays card
        {
            public void leftDoubleClicked(Card card) { selected = card; thePlayer.getPlayingHand().setTouchEnabled(false); }
        };
        thePlayer.getPlayingHand().addCardListener(cardListener);

        return selected;
    }

    public void setHand() {
        thePlayer.setPlayingHand();
    }

    public Hand getHand() {
        return thePlayer.getPlayingHand();
    }
}
