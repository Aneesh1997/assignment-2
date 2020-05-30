import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class LegalNPC extends NPC{

	public LegalNPC(Hand hand, int playerID, int score) {
		super(hand, playerID, score);
		
	}
	
	@Override
	public Card getCard (Whist.Suit lead) {
		
		Hand current = getPlayingHand();
		if (current.getNumberOfCardsWithSuit(lead) > 0){
			return current.getCardsWithSuit(lead).get(Whist.random.nextInt(current.getNumberOfCardsWithSuit(lead)));
		}
		else{
			return current.get(Whist.random.nextInt(current.getNumberOfCards()));
		}
		
			
	
		
	}
	@Override
	public Card getCard() {
		// TODO Auto-generated method stub
		return getPlayingHand().get(Whist.random.nextInt(getPlayingHand().getNumberOfCards()));
	}

	@Override
	public Card getCard(Whist.Suit lead, Card winningCard) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
