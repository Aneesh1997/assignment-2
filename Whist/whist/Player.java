import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Player {
    int playerID;
    private Hand playingHand;

    public Player(int playerID, Hand playingHand) {
        this.playerID = playerID;
        this.playingHand = playingHand;
    }

    public void setPlayingHand() { this.playingHand.setTouchEnabled((true)); }

    public Hand getPlayingHand() {return this.playingHand; }

    public abstract Card playCard();

    public abstract Card playCard(Whist.Suit lead, Card winningCard);

	public void setHand(Hand hand) {
		this.playingHand=hand;
		
	}
}
