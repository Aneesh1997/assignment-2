import ch.aplu.jcardgame.Hand;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import static ch.aplu.jgamegrid.GameGrid.delay;


public class HumanAdaptor implements IPlayerAdaptor {
    private Player thePlayer;
    private Card selected;

    public HumanAdaptor(int playerID, Hand playingHand, Actor scoreActor) {
        thePlayer = new Player(playerID, playingHand, scoreActor);
    }


    public Card play() {
        // TODO: implement this
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
