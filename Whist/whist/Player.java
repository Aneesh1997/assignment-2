import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    int playerID;
    private Hand playingHand;
    private int score;

    public Player(int playerID, Hand playingHand, int score) {
        this.playerID = playerID;
        this.playingHand = playingHand;
        this.score = score;
    }

    public void setPlayingHand() { this.playingHand.setTouchEnabled((true)); }

    public Hand getPlayingHand() {return this.playingHand; }


}
