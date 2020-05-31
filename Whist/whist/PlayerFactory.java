import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public class PlayerFactory {
    private ArrayList<Player> players;

    public PlayerFactory() {
        players = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers(Hand[] hands, int basicNPCs,int legalNPCs,int smartNPCs,int humanPlayers,int nbPlayers) {
        int handCount = 0;

        // Add robot players
        for (int i = 0; i < nbPlayers; i++) {
            // Add human player
            if (humanPlayers > 0) {
                players.add(new HumanPlayer(handCount, hands[handCount]));
                handCount++;
                humanPlayers--;
            // Add basicNPCs
            } else if (basicNPCs > 0) {
                players.add(new BasicNPC(handCount, hands[handCount]));
                handCount++;
                basicNPCs--;
            } else if (legalNPCs > 0) {
                // DO SOMETHING
                players.add(new LegalNPC(handCount, hands[handCount]));
                
                handCount++;
                legalNPCs--;
            } else if (smartNPCs > 0) {
                // DO SOMETHING
                players.add(new SmartNPC(handCount, hands[handCount]));
                handCount++;
                smartNPCs--;
            }
        }

        return players;
    }
}
