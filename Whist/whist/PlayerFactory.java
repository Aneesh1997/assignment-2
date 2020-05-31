import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public class PlayerFactory {
    private ArrayList<Player> players;

    public PlayerFactory() {
        players = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers(Hand[] hands, int basicNPCs, int legalNPCs, int smartNPCs,int interactiveplayers, int nBplayers) {
        int handCount = 0;
        // Add human player
        

        // Add robot players
        for (int i = 0; i < nBplayers; i++) {
        	if(interactiveplayers>0)
        	{
        		 players.add(new HumanPlayer(handCount, hands[handCount]));
        	     handCount++;
        	     
        	}
            // Add basicNPCs
            if (basicNPCs > 0) {
                players.add(new BasicNPC(handCount, hands[handCount]));
                handCount++;
                
            } else if (legalNPCs > 0) {
                // DO SOMETHING
                players.add(new LegalNPC(handCount, hands[handCount]));
                handCount++;
            } else if (smartNPCs > 0) {
                // DO SOMETHING
                players.add(new SmartNPC(handCount, hands[handCount]));
                
                handCount++;
            }
        }

        return players;
    }
}
