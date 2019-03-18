package Games;

import Components.Deck;
import Components.Hand;
import Main.UI;
import javafx.scene.layout.BorderPane;

public class Game {
    
    final private BorderPane playmat;
    final private Deck deck;
    final private Hand player;
    final private Hand opponent;

    public Game() {
        this.deck = new Deck();
        this.player = new Hand();
        this.opponent = new Hand();
        this.playmat = new BorderPane();
        this.playmat.setPrefSize(1000, 500);
        this.playmat.setBackground(UI.background);
    }

    public BorderPane getPlaymat() {
        return playmat;
    }

    public Deck getDeck() {
        return deck;
    }

    public Hand getPlayerHand() {
        return player;
    }

    public Hand getOpponentHand() {
        return opponent;
    }
}
