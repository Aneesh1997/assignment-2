import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class LegalNPC extends Player{

    public LegalNPC(int playerID, Hand hand) {
        super(playerID, hand);

    }


    public Card playCard(Whist.Suit lead, Card winningCard, Whist.Suit trumps) {
        GUI.getInstance().setStatus("Player " + this.playerID + " thinking...");
        GUI.getInstance().delay(ThinkingTime);

        Hand current = getPlayingHand();
        if (current.getNumberOfCardsWithSuit(lead) > 0){
            return current.getCardsWithSuit(lead).get(Whist.random.nextInt(current.getNumberOfCardsWithSuit(lead)));
        }
        else{
            return current.get(Whist.random.nextInt(current.getNumberOfCards()));
        }
    }

    @Override
    public Card playCard() {
        GUI.getInstance().setStatus("Player " + this.playerID + " thinking...");
        GUI.getInstance().delay(ThinkingTime);
        // TODO Auto-generated method stub
        return getPlayingHand().get(Whist.random.nextInt(getPlayingHand().getNumberOfCards()));
    }

}