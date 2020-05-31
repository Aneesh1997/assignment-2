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
public class Whist {
	
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

  //static final Random random = ThreadLocalRandom.current();
  static Random random = new Random();
  
  // return random Enum value
  public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
      int x = random.nextInt(clazz.getEnumConstants().length);
      return clazz.getEnumConstants()[x];
  }
  
  // return random Card from Hand
  public static Card randomCard(Hand hand){
      int x = random.nextInt(hand.getNumberOfCards());
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
  public int nbStartCards = 13;
  public static ArrayList<Card> arraycards= new ArrayList<Card>();

  // Load in properties
  Properties whistProperties = new Properties();

  // Init GUI
  private GUI gui;
  private boolean sameGame = false;

  // Alterable properties?
  private int seed;
  public int winningScore;
  public int humanPlayers;
  public int basicPlayers;
  public int smartPlayers;
  public int legalPlayers;
  private boolean enforceRules=false;

  // Randomisation Controller idea: 30006, first digit represents trump suit?
  private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
  private Hand[] hands = new Hand[nbPlayers];

  private ArrayList<Player> players;
  private PlayerFactory playerFactory = new PlayerFactory();
private int[] scores = new int[nbPlayers];


private void createWhistProperties(String seed,String winningScore,String humanPlayers, String basicPlayers, String legalPlayers, String smartPlayers, String enforceRules, String nbStartCards) {
	whistProperties.setProperty("Seed", seed);
	whistProperties.setProperty("WinningScore", winningScore);
	whistProperties.setProperty("HumanPlayers", humanPlayers);
	whistProperties.setProperty("BasicNPCs", basicPlayers);
	whistProperties.setProperty("LegalNPCs", legalPlayers);
	whistProperties.setProperty("SmartNPCs", smartPlayers);
	whistProperties.setProperty("StartingHand", nbStartCards);
	whistProperties.setProperty("enforceRules", enforceRules);
}

private void setWhistProperties() {
	this.seed = Integer.parseInt(whistProperties.getProperty("Seed"));
	this.winningScore = Integer.parseInt(whistProperties.getProperty("WinningScore"));
	this.humanPlayers = Integer.parseInt(whistProperties.getProperty("HumanPlayers"));
	this.basicPlayers = Integer.parseInt(whistProperties.getProperty("BasicNPCs"));
	this.legalPlayers = Integer.parseInt(whistProperties.getProperty("LegalNPCs"));
	this.smartPlayers = Integer.parseInt(whistProperties.getProperty("SmartNPCs"));
	this.nbStartCards = Integer.parseInt(whistProperties.getProperty("StartingHand"));
	this.enforceRules = Boolean.parseBoolean(whistProperties.getProperty("enforceRules"));
}

private void initScore() {
	 for (int i = 0; i < nbPlayers; i++) {
		 scores[i] = 0;
	 }
  }

private Card selected;

// Change this?
private void initRound() {
		if(seed!=0)
		{Hand deck1 = deck.toHand(false);
			random.setSeed(seed);


			for (int i = 0; i < 4; i++) {

				Card b=randomCard(deck1);
				hands[i] = new Hand(b.getDeck());
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
		 //hands = deck.dealingOut(nbPlayers, nbStartCards); // Last element of hands is leftover cards; these are ignored
		 for (int i = 0; i < nbPlayers; i++) {
			   hands[i].sort(Hand.SortType.SUITPRIORITY, true);
		 }
		 //players = playerFactory.getPlayers(hands, humanPlayers, basicPlayers,legalPlayers,smartPlayers,nbPlayers);
		if (sameGame==false)
		{
			players = playerFactory.getPlayers(hands, humanPlayers, basicPlayers,legalPlayers,smartPlayers,nbPlayers);
			System.out.println(players);
			sameGame=true;
		}
		else
		{
			for (int i = 0; i < nbPlayers; i++) {
				players.get(i).setHand(hands[i]);
			}
	}
		 // graphics
		gui.initRound(hands);
 }

 // Refactor this
private Optional<Integer> playRound() {  // Returns winner, if any
	// Select and display trump suit
		final Suit trumps = randomEnum(Suit.class);
		gui.showTrumpsSuit(trumps);
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
			selected = players.get(nextPlayer).playCard();
		} else {
			selected = players.get(nextPlayer).playCard();
		}
		// Lead with selected card
		gui.updateTrick(trick);
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
			selected = players.get(nextPlayer).playCard(lead, winningCard);

	        // Follow with selected card
			gui.updateTrick(trick);
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
				 System.out.println(arraycards);


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
		gui.endTrick(trick, winner);
		scores[nextPlayer]++;
		gui.updateScore(nextPlayer, scores);
		arraycards.removeAll(arraycards);
		//updateScore(nextPlayer);
		if (winningScore == scores[nextPlayer]) return Optional.of(nextPlayer);
	}
	gui.removeTrumpsSuit();
	//removeActor(trumpsActor);
	return Optional.empty();
}
  // Don't touch this
  public Whist() throws IOException {
    gui = new GUI(version, nbPlayers);

	// Set basic properties
  	createWhistProperties("0","20", "1","3", "0", "0","13", "false");
	FileReader inStream = null;
	try {
	  inStream = new FileReader("config.properties");
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
    gui.endGame(Optional.of(winner.get()));
  }


  // Main function
  public static void main(String[] args) throws IOException {
	// System.out.println("Working Directory = " + System.getProperty("user.dir"));
    new Whist();
  }

}
