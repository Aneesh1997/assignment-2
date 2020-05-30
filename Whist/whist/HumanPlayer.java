import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardListener;
import ch.aplu.jcardgame.Hand;

public class HumanPlayer extends Player {
    private Card selected;

    private CardListener cardListener = new CardAdapter() {
        public void leftDoubleClicked(Card card) { selected = card; getPlayingHand().setTouchEnabled(false); }
    };

    public HumanPlayer(int playerID, Hand playingHand) {
        super(playerID, playingHand);
        this.getPlayingHand().addCardListener(cardListener);
    }

    public Card playCard() {
        this.getPlayingHand().setTouchEnabled(true);
        return this.selected;
    }
}
