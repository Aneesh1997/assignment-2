import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;

public class PlayerFactory {
    private ArrayList<Player> players;

    public PlayerFactory() {
        players = new ArrayList<>();
    }

    private StrategyFactory strategyFactory;

    public ArrayList<Player> getPlayers(Hand[] hands,int humanPlayers, int basicNPCs, int legalNPCs, int smartNPCs, int nBplayers) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        int handCount = 0;
        strategyFactory = StrategyFactory.getInstance();
        // Add robot players
        for (int i = 0; i < nBplayers; i++) {
            // Add human player
            if (humanPlayers > 0) {
                players.add(new HumanPlayer(handCount, hands[handCount]));
                handCount++;
                humanPlayers--;
            // Add basicNPCs
            } else if (basicNPCs > 0) {
                //players.add(new BasicNPC(handCount, hands[handCount]));

                // Testing for strategy
                players.add(new NPC(handCount, hands[handCount], strategyFactory.getStrategy("BasicCardStrategy")));
                handCount++;
                basicNPCs--;
            } else if (legalNPCs > 0) {
                // DO SOMETHING
                //players.add(new LegalNPC(handCount, hands[handCount]));

                // Testing for strategy
                players.add(new NPC(handCount, hands[handCount], strategyFactory.getStrategy("LegalCardStrategy")));
                handCount++;
                legalNPCs--;
            } else if (smartNPCs > 0) {
                // DO SOMETHING
                //players.add(new SmartNPC(handCount, hands[handCount]));

                // Testing for strategy
                players.add(new NPC(handCount, hands[handCount], strategyFactory.getStrategy("SmartCardStrategy")));
                handCount++;
                smartNPCs--;
            }
        }

        return players;
    }
}
