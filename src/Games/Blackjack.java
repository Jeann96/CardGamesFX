package Games;

import Components.Card;
import Components.Deck;
import Components.Hand;
import Logic.Blackjack_Logic;
import Main.UI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Blackjack extends Game {

    final private Blackjack_Logic logic;
    final private Hand player;
    final private Hand dealer;
    final private Deck deck;
    final private BorderPane playmat;
    private Scene scene;
    public Stage stage;

    public Blackjack(Stage stage) {
        this.logic = new Blackjack_Logic();
        this.player = super.getPlayerHand();
        this.dealer = super.getOpponentHand();
        this.deck = super.getDeck();
        this.playmat = super.getPlaymat();
        this.stage = stage;
    }

    public void playRound() {
        deck.clear();
        player.clear();
        dealer.clear();
        deck.buildDeck();
        player.draw(2, deck, true);
        dealer.draw(2, deck, false);
        //Center of playmat
        VBox center = new VBox();
        center.setAlignment(Pos.CENTER);
        Label decksize = new Label(String.valueOf(deck.deckSize()));
        decksize.setFont(Font.font(20));
        decksize.setStyle("-fx-font-weight: bold");
        //Cards and points of dealer (top of the playmat)
        Card other = dealer.getCard(Deck.randomRange(0, 1));
        other.flipCard();
        VBox top = new VBox();
        top.setAlignment(Pos.CENTER);
        Label dealerPoints = new Label("Points: " + String.valueOf(logic.points(other)));
        dealerPoints.setFont(Font.font(20));
        dealerPoints.setStyle("-fx-font-weight: bold");
        top.getChildren().add(dealer.handImage());
        top.getChildren().add(dealerPoints);
        //Cards and points of player (bottom of the playmat)
        VBox bottom = new VBox();
        bottom.setAlignment(Pos.CENTER);
        Label playerPoints = new Label("Points: " + String.valueOf(logic.sum(player)));
        playerPoints.setFont(Font.font(20));
        playerPoints.setStyle("-fx-font-weight: bold");
        bottom.getChildren().add(player.handImage());
        bottom.getChildren().add(playerPoints);
        
        playmat.setTop(top);
        playmat.setBottom(bottom);

        //Buttons
        Button continueButton = new Button("Continue");
        continueButton.setOnAction(e -> {
            dealer.flipCardsFaceUp();
            dealer.draw(1, deck, true);
            decksize.setText(String.valueOf(this.deck.deckSize()));
            dealerPoints.setText("Points: " + String.valueOf(logic.sum(dealer)));
            if (logic.over21(dealer)) {
                dealerPoints.setText("Over 21!");
                this.endGame();
            }
        });
        Button hitme = new Button("Hit me!");
        hitme.setOnAction(e -> {
            this.player.draw(1, deck, true);
            decksize.setText(String.valueOf(this.deck.deckSize()));
            playerPoints.setText("Points: " + String.valueOf(logic.sum(player)));
            if (logic.over21(player)) {
                playerPoints.setText("Over 21!");
                center.getChildren().clear();
                center.getChildren().add(continueButton);
                center.getChildren().add(deck);
                center.getChildren().add(decksize);
            }
        });
        Button stop = new Button("Stop");
        stop.setOnAction(e -> {
            center.getChildren().clear();
            center.getChildren().add(continueButton);
            center.getChildren().add(deck);
            center.getChildren().add(decksize);
        });
        center.getChildren().add(hitme);
        center.getChildren().add(stop);
        center.getChildren().add(deck);
        center.getChildren().add(decksize);
        playmat.setCenter(center);
    }

    public void startGame() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        Label welcome = new Label("Welcome to blackjack!");
        welcome.setFont(Font.font(20));
        welcome.setStyle("-fx-font-weight: bold");
        Button start = new Button("Start");
        start.setOnAction(e -> {
            this.playRound();
        });
        vbox.getChildren().add(welcome);
        vbox.getChildren().add(start);
        playmat.setCenter(vbox);
        scene = new Scene(playmat);
        stage.setScene(scene);
    }

    public void endGame() {
        VBox ending = new VBox();
        ending.setAlignment(Pos.CENTER);
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        Label gameOver = new Label("Game over!\n"
                + "Your points: " + String.valueOf(logic.sum(player)) + "\n"
                + "Dealer's points: " + String.valueOf(logic.sum(dealer)));
        gameOver.setFont(Font.font(20));
        gameOver.setStyle("-fx-font-weight: bold");
        Button exit = new Button("Exit");
        Button newGame = new Button("New Game");
        exit.setOnAction(e -> {
            stage.setScene(UI.start);
        });
        newGame.setOnAction(e -> {
            this.playRound();
        });
        ending.getChildren().add(gameOver);
        buttons.getChildren().addAll(newGame, exit);
        ending.getChildren().add(buttons);
        playmat.setCenter(ending);
    }
}
