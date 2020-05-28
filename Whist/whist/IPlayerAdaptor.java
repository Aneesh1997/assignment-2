import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public interface IPlayerAdaptor{
    public Card play();

    public Hand getHand();

    //public void updateScore();
}
