import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    int playerID;
    private Hand playingHand;
    private Actor scoreActor;
    private Card selected;
    private CardListener cardListener = new CardAdapter() {
        public void leftDoubleClicked(Card card) { selected = card; playingHand.setTouchEnabled(false); }
    };

    public Player(int playerID, Hand playingHand, Actor scoreActor) {
        this.playerID = playerID;
        this.playingHand = playingHand;
        this.scoreActor = scoreActor;
        this.playingHand.addCardListener(cardListener);
    }

    public void setPlayingHand() {
        this.playingHand.setTouchEnabled((true));
    }

    public Card getSelected() {
        return this.selected;
    }

    public Hand getPlayingHand() {return this.playingHand; }


}
