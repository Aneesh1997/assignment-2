import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public class PlayerFactory {
    private ArrayList<Player> players;

    public PlayerFactory() {
        players = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers(Hand[] hands, int basicNPCs, int legalNPCs, int smartNPCs, int nBplayers) {
        int handCount = 0;
        // Add human player
        players.add(new HumanPlayer(handCount, hands[handCount]));
        handCount++;

        // Add robot players
        for (int i = 1; i < nBplayers; i++) {
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
