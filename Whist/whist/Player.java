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

    // Set up human player for interaction
    CardListener cardListener = new CardAdapter();

    public Player(int playerID, Hand playingHand, Actor scoreActor) {
        this.playerID = playerID;
        this.playingHand = playingHand;
        this.scoreActor = scoreActor;
        playingHand.setTouchEnabled(false);


        CardListener cardListener = new CardAdapter()  // Human Player plays card
        {
            public void leftDoubleClicked(Card card) { selected = card; playingHand.setTouchEnabled(false); }
        };
        playingHand.addCardListener(cardListener);
    }




}
