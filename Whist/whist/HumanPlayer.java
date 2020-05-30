import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardListener;
import ch.aplu.jcardgame.Hand;

import static ch.aplu.jgamegrid.GameGrid.delay;

public class HumanPlayer extends Player {
    private Card selected = null;

    private CardListener cardListener = new CardAdapter() {
        public void leftDoubleClicked(Card card) { selected = card; getPlayingHand().setTouchEnabled(false); }
    };

    public HumanPlayer(int playerID, Hand playingHand) {
        super(playerID, playingHand);
        this.getPlayingHand().addCardListener(cardListener);
    }

    @Override
    public Card playCard() {
        selected = null;
        this.getPlayingHand().setTouchEnabled(true);
        GUI.getInstance().setStatus("Player 0 double-click on card to lead.");
        while(null == selected) delay(100);
        return this.selected;
    }

    @Override
    public Card playCard(Whist.Suit lead, Card winningCard) {
        return null;
    }
}
