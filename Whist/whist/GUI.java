import ch.aplu.jcardgame.CardGame;
import ch.aplu.jcardgame.Hand;
import ch.aplu.jcardgame.RowLayout;
import ch.aplu.jcardgame.TargetArea;
import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;
import ch.aplu.jgamegrid.TextActor;


import java.awt.*;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GUI extends CardGame {
    Font bigFont = new Font("Serif", Font.BOLD, 36);

    private final Location[] handLocations = {
            new Location(350, 625),
            new Location(75, 350),
            new Location(350, 75),
            new Location(625, 350)
    };
    private final Location[] scoreLocations = {
            new Location(575, 675),
            new Location(25, 575),
            new Location(575, 25),
            new Location(650, 575)
    };

    private Actor[] scoreActors = {null, null, null, null };
    private final Location trickLocation = new Location(350, 350);
    private final Location textLocation = new Location(350, 450);
    private final int thinkingTime = 2000;
    private Hand[] hands;
    private Location hideLocation = new Location(-500, - 500);
    private Location trumpsActorLocation = new Location(50, 50);
    private final int handWidth = 400;
    private final int trickWidth = 40;

    private final int nbPlayers = 4;
    private int[] scores = new int[nbPlayers];

    final String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};
    private Actor trumpsActor;

    static final Random random = ThreadLocalRandom.current();

    private static GUI gui;

    private void initScore() {
        for (int i = 0; i < nbPlayers; i++) {
            scores[i] = 0;
            scoreActors[i] = new TextActor("0", Color.WHITE, bgColor, bigFont);
            addActor(scoreActors[i], scoreLocations[i]);
        }
    }




    public GUI(String version,int nBplayers) {
        super(700, 700, 300);
        gui = this;
        setTitle("Whist (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
        setStatusText("Initializing...");
        initScore();
    }

    public static GUI getInstance() {
        if (gui == null) {
            System.out.println("GUI failed to load");
        }
        return gui;
    }

    public void updateScore(int player) {
        removeActor(scoreActors[player]);
        scoreActors[player] = new TextActor(String.valueOf(scores[player]), Color.WHITE, bgColor, bigFont);
        addActor(scoreActors[player], scoreLocations[player]);
    }

    public void setStatus(String string) { setStatusText(string); }

    public void initRound(Hand[] hands) {
        // graphics
        RowLayout[] layouts = new RowLayout[nbPlayers];
        for (int i = 0; i < nbPlayers; i++) {
            layouts[i] = new RowLayout(handLocations[i], handWidth);
            layouts[i].setRotationAngle(90 * i);
            // layouts[i].setStepDelay(10);

            hands[i].setView(this, layouts[i]);
            hands[i].setTargetArea(new TargetArea(trickLocation));
            hands[i].draw();
        }
//	    for (int i = 1; i < nbPlayers; i++)  // This code can be used to visually hide the cards in a hand (make them face down)
//	      hands[i].setVerso(true);
        // End graphics
    }

    public void updateScore(int player, int[] scores) {
        removeActor(scoreActors[player]);
        scoreActors[player] = new TextActor(String.valueOf(scores[player]), Color.WHITE, bgColor, bigFont);
        addActor(scoreActors[player], scoreLocations[player]);
    }

    public void showTrumpsSuit(Whist.Suit trumps) {
        final Actor trumpsActor = new Actor("sprites/"+trumpImage[trumps.ordinal()]);
        addActor(trumpsActor, trumpsActorLocation);
    }

    public void removeTrumpsSuit() {
        removeActor(trumpsActor);
    }

    public void updateTrick(Hand trick){
        trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
        trick.draw();
    }

    public void endTrick(Hand trick, int winner){
        delay(600);
        trick.setView(this, new RowLayout(hideLocation, 0));
        trick.draw();
        setStatusText("Player " + winner + " wins trick.");
    }

    public void endGame(Optional<Integer> winner){
        addActor(new Actor("sprites/gameover.gif"), textLocation);
        setStatus("Game over. Winner is player: " + winner.get());
        refresh();
    }
}
