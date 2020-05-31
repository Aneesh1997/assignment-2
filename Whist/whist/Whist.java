// Whist.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.LogManager;

@SuppressWarnings("serial")
public class Whist extends CardGame {
	
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    // Reverse order of rank importance (see rankGreater() below)
	// Order of cards is tied to card images
	ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
  }
  
  final String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};

  static Random random = new Random();
  
  // return random Enum value
  public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
      int x = random.nextInt(clazz.getEnumConstants().length);
      return clazz.getEnumConstants()[x];
  }
  
  // return random Card from Hand
  public static Card randomCard(Hand hand){
	 
      int x = random.nextInt(hand.getNumberOfCards());
      System.out.println(hand.getNumberOfCards()+"  "+x);
      return hand.get(x);
  }
 
  // return random Card from ArrayList
  public static Card randomCard(ArrayList<Card> list){
      int x = random.nextInt(list.size());
      return list.get(x);
  }
  
  public static boolean rankGreater(Card card1, Card card2) {
	  return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
  }

  // Unchanged variables
  private final String version = "1.0";
  public final int nbPlayers = 4;
  public  int nbStartCards = 13;
  public static ArrayList<Card> arraycards= new ArrayList<Card>();

  // Load in properties
  Properties whistProperties = new Properties();


  // Alterable properties?
  public int winningScore;
  public int basicPlayers;
  public int smartPlayers;
  public int legalPlayers;
  private boolean enforceRules=false;

  // Randomisation Controller idea: 30006, first digit represents trump suit?

  private final int handWidth = 400;
  private final int trickWidth = 40;
  private Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
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
  private Hand[] hands= new Hand[nbPlayers];
  private Location hideLocation = new Location(-500, - 500);
  private Location trumpsActorLocation = new Location(50, 50);

  private ArrayList<Player> players;
  private PlayerFactory playerFactory = new PlayerFactory();

  public void setStatus(String string) { setStatusText	(string); }
  
private int[] scores = new int[nbPlayers];

Font bigFont = new Font("Serif", Font.BOLD, 36);

private int seed;

private int interactiveplayers;

private void createWhistProperties(String seed,String winningScore, String basicPlayers, String legalPlayers, String smartPlayers, String enforceRules) {
	whistProperties.setProperty("Seed", seed);
	whistProperties.setProperty("WinningScore", winningScore);
	whistProperties.setProperty("BasicNPCs", basicPlayers);
	whistProperties.setProperty("LegalNPCs", legalPlayers);
	whistProperties.setProperty("SmartNPCs", smartPlayers);
	whistProperties.setProperty("enforceRules", enforceRules);
}

private void setWhistProperties() {
	this.seed=Integer.parseInt(whistProperties.getProperty("seed"));
	this.winningScore = Integer.parseInt(whistProperties.getProperty("WinningScore"));
	this.basicPlayers = Integer.parseInt(whistProperties.getProperty("BasicNPCs"));
	this.legalPlayers = Integer.parseInt(whistProperties.getProperty("LegalNPCs"));
	this.smartPlayers = Integer.parseInt(whistProperties.getProperty("SmartNPCs"));
	this.enforceRules = Boolean.parseBoolean(whistProperties.getProperty("enforceRules"));
	this.enforceRules = Boolean.parseBoolean(whistProperties.getProperty("enforceRules"));
	this.nbStartCards= Integer.parseInt(whistProperties.getProperty("nbStartCards"));
	this.interactiveplayers = Integer.parseInt(whistProperties.getProperty("interactiveplayers"));
}

private void initScore() {
	 for (int i = 0; i < nbPlayers; i++) {
		 scores[i] = 0;
		 scoreActors[i] = new TextActor("0", Color.WHITE, bgColor, bigFont);
		 addActor(scoreActors[i], scoreLocations[i]);
	 }
  }

private void updateScore(int player) {
	removeActor(scoreActors[player]);
	scoreActors[player] = new TextActor(String.valueOf(scores[player]), Color.WHITE, bgColor, bigFont);
	addActor(scoreActors[player], scoreLocations[player]);
}

private Card selected;

private boolean samegame=false;

// Change this?
private void initRound() {
		 if(seed!=0)
		 {
		deck = new Deck(Suit.values(), Rank.values(), "cover");
		Hand deck1 = deck.toHand(false);
		 
		 random.setSeed(seed);
		 
		
		 for (int i = 0; i < 4; i++) {
			 
			 Card b=randomCard(deck1);
			 hands[i]=new Hand(b.getDeck());
			 deck1.remove(b, false);
			 hands[i].insert(b, true);
			
			 for (int j=2;j<=nbStartCards;++j ) {
			  
			  b=randomCard(deck1);
			 
			 
			 {hands[i].insert(b,true);}
			 deck1.remove(b, false);
			 
			 }
 
			 
		 }
		 }
		 else
		 {	
			 hands=deck.dealingOut(nbPlayers, nbStartCards,true);
		 }
		 
		 
		 for (int i = 0; i < nbPlayers; i++) {
			   
			   hands[i].sort(Hand.SortType.SUITPRIORITY, true);
		 }
		 if (samegame==false)
		 {
		 players = playerFactory.getPlayers(hands, basicPlayers,legalPlayers,smartPlayers,interactiveplayers,nbPlayers);
		 System.out.println(players);
		 samegame=true;
		 }
		 else
		 {
			 for (int i = 0; i < nbPlayers; i++) {
				 players.get(i).setHand(hands[i]);
			 }
		 }
		 
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

 // Refactor this
private Optional<Integer> playRound() {  // Returns winner, if any
	
	// Select and display trump suit
		final Suit trumps = randomEnum(Suit.class);
		final Actor trumpsActor = new Actor("sprites/"+trumpImage[trumps.ordinal()]);
	    addActor(trumpsActor, trumpsActorLocation);
	// End trump suit
	Hand trick;
	int winner;
	Card winningCard;
	Suit lead;
	int nextPlayer = random.nextInt(nbPlayers); // randomly select player to lead for this round
	
	
	for (int i = 0; i < nbStartCards; i++) {
		trick = new Hand(deck);
    	selected = null;
    	if (0 == nextPlayer) {
			setStatus("Player 0 double-click on card to lead.");
			selected = players.get(nextPlayer).playCard();
		} else {
			setStatusText("Player " + nextPlayer + " thinking...");
			delay(thinkingTime);
			selected = players.get(nextPlayer).playCard();
		}
        // Lead with selected card
	        trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
			trick.draw();
			selected.setVerso(false);
			// No restrictions on the card being lead
			lead = (Suit) selected.getSuit();
			selected.transfer(trick, true); // transfer to trick (includes graphic effect)
			winner = nextPlayer;
			winningCard = selected;
			arraycards.add(selected);



		// End Lead
		for (int j = 1; j < nbPlayers; j++) {
			if (++nextPlayer >= nbPlayers) nextPlayer = 0;  // From last back to first
			selected = null;

			if (0 == nextPlayer) {
				setStatus("Player 0 double-click on card to lead.");
				selected = players.get(nextPlayer).playCard();
			} else {
				setStatusText("Player " + nextPlayer + " thinking...");
				delay(thinkingTime);
				selected = players.get(nextPlayer).playCard(lead, winningCard);
			}
	        // Follow with selected card
		        trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
				trick.draw();
				selected.setVerso(false);  // In case it is upside down
				// Check: Following card must follow suit if possible
					if (selected.getSuit() != lead && hands[nextPlayer].getNumberOfCardsWithSuit(lead) > 0) {
						 // Rule violation
						 String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
						 System.out.println(violation);
						 if (enforceRules) 
							 try {
								 throw(new BrokeRuleException(violation));
								} catch (BrokeRuleException e) {
									e.printStackTrace();
									System.out.println("A cheating player spoiled the game!");
									System.exit(0);
								}  
					 }
				// End Check
				 selected.transfer(trick, true); // transfer to trick (includes graphic effect)
				 System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + winningCard.getRankId());
				 System.out.println(" played: suit = " +    selected.getSuit() + ", rank = " +    selected.getRankId());
				 arraycards.add(selected);
				 


				 if ( // beat current winner with higher card
					 (selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
					  // trumped when non-trump was winning
					 (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
					 System.out.println("NEW WINNER");
					 winner = nextPlayer;
					 winningCard = selected;
				 }
			// End Follow
		}
		delay(600);
		trick.setView(this, new RowLayout(hideLocation, 0));
		trick.draw();		
		nextPlayer = winner;
		setStatusText("Player " + nextPlayer + " wins trick.");
		scores[nextPlayer]++;
		arraycards.removeAll(arraycards);
		updateScore(nextPlayer);
		if (winningScore == scores[nextPlayer]) return Optional.of(nextPlayer);
	}
	removeActor(trumpsActor);
	return Optional.empty();
}
  // Don't touch this
  public Whist() throws IOException {
    super(700, 700, 30);
    
    setTitle("Whist (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
    setStatusText("Initializing...");

	// Set basic properties
  	
	FileReader inStream = null;
	try {
	  inStream = new FileReader("legal.properties");
	  whistProperties.load(inStream);
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
	  if (inStream != null) {
		  inStream.close();
	  }
	}
	setWhistProperties();
	

    initScore();
    Optional<Integer> winner;
    do { 
      initRound();
      winner = playRound();
    } while (!winner.isPresent());
    addActor(new Actor("sprites/gameover.gif"), textLocation);
    setStatusText("Game over. Winner is player: " + winner.get());
    refresh();
  }
  // Main function
  public static void main(String[] args) throws IOException {
	// System.out.println("Working Directory = " + System.getProperty("user.dir"));
    new Whist();
  }

}
