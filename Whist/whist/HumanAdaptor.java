import ch.aplu.jcardgame.Hand;
import ch.aplu.jgamegrid.Actor;

public class HumanAdaptor implements IPlayerAdaptor {
    private Player thePlayer;

    public HumanAdaptor(int playerID, Hand playingHand, Actor scoreActor) {
        thePlayer = new Player(playerID, playingHand, scoreActor);
    }

    public void play() {
        // TODO: implement this

    }
}
