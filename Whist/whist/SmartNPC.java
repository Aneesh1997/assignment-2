import java.awt.Component;
import java.util.ArrayList;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public class SmartNPC extends Player{
    private int ThinkingTime = 2000;

    public SmartNPC (int playerID, Hand hand) {
        super(playerID, hand);

    }

    public Card playCard (Whist.Suit lead, Card winningCard) {
        GUI.getInstance().setStatus("Player " + this.playerID + " thinking...");
        GUI.getInstance().delay(ThinkingTime);
        ArrayList<Card> cons = Whist.arraycards;
        Hand current = getPlayingHand();
        if (current.getNumberOfCardsWithSuit(lead) > 0){

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
        }

        else{
            return current.get(Whist.random.nextInt(current.getNumberOfCards()));
        }
        return null;




    }

    @Override
    public Card playCard() {
        GUI.getInstance().setStatus("Player " + this.playerID + " thinking...");
        GUI.getInstance().delay(ThinkingTime);
        // TODO Auto-generated method stub
        ArrayList<Card> current = getPlayingHand().getCardList();

        Card highest = current.get(0);
        for (int i = 1;i<current.size();++i)
        {
            if(Whist.rankGreater(highest, current.get(i))==false) {
                highest=current.get(i);
            }

        }
        return highest;
    }

}