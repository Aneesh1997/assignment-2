import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class LegalCardStrategy implements ICardStrategy {

    @Override
    public Card playCard(NPC npc) {
        return npc.getPlayingHand().get(Whist.random.nextInt(npc.getPlayingHand().getNumberOfCards()));
    }

    @Override
    public Card playCard(NPC npc,Whist.Suit lead, Card winningCard, Whist.Suit trumps) {
        Hand current = npc.getPlayingHand();
        System.out.println("helloworld");
        if (current.getNumberOfCardsWithSuit(lead) > 0){
            return current.getCardsWithSuit(lead).get(Whist.random.nextInt(current.getNumberOfCardsWithSuit(lead)));
        }
        else{
            return current.get(Whist.random.nextInt(current.getNumberOfCards()));
        }
    }
}
