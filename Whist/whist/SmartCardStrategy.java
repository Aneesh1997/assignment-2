import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public class SmartCardStrategy implements ICardStrategy {

    @Override
    public Card playCard(NPC npc, Whist.Suit lead, Card winningCard, Whist.Suit trumps) {
        Hand current = npc.getPlayingHand();
        if (current.getNumberOfCardsWithSuit(lead) > 0){

            return bestPlay(current, lead, winningCard);
        }
        else if (current.getNumberOfCardsWithSuit(trumps) > 0) {

            return bestPlay(current, trumps, winningCard);
        }
        else{
            return current.get(Whist.random.nextInt(current.getNumberOfCards()));
        }
    }

    @Override
    public Card playCard(NPC npc) {
        ArrayList<Card> current = npc.getPlayingHand().getCardList();

        Card highest = current.get(0);
        for (int i = 1;i<current.size();++i)
        {
            if(Whist.rankGreater(highest, current.get(i))==false) {
                highest=current.get(i);
            }

        }
        return highest;
    }





    private Card bestPlay(Hand current, Whist.Suit lead, Card winningCard) {

        ArrayList<Card> b = current.getCardsWithSuit(lead);

        if(b!=null)
        {Card highest = b.get(0);
            Card lowest=b.get(0);
            for (int i = 1;i<b.size();++i)
            {
                if(Whist.rankGreater(highest, b.get(i))==false) {
                    highest=b.get(i);

                }
                if (Whist.rankGreater(lowest, b.get(i)))
                {
                    lowest=b.get(i);
                }

            }
            if(Whist.rankGreater(highest, winningCard ))
            {
                return highest;
            }
            else {
                return lowest;}

        }
        return null;




    }
}
