package Games;

import Components.Card;
import Components.Deck;
import Components.Hand;
import Components.ImageStore;
import Logic.Hitit_Logic;
import Main.UI;
import java.util.Iterator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HitIt extends Game {

    final private Hand player;
    final private Hand opponent;
    final private Hand table;
    final private BorderPane playmat;
    final private Hitit_Logic logic;
    private int time;
    private Scene scene;
    public Stage stage;

    public HitIt(Stage stage) {
        this.player = super.getPlayerHand();
        this.opponent = super.getOpponentHand();
        this.table = new Hand();
        this.logic = new Hitit_Logic();
        this.playmat = super.getPlaymat();
        this.stage = stage;
    }
    
    public void playRound() {
        player.clear();
        opponent.clear();
        Deck deck = new Deck();
        deck.buildDeck();
        player.draw(26, deck, false);
        opponent.draw(26, deck, false);
        //Deck image (center of the playmat)
        VBox center = new VBox();
        center.setAlignment(Pos.CENTER);
        Label tablesize = new Label(String.valueOf(deck.deckSize()));
        tablesize.setFont(Font.font(20));
        tablesize.setStyle("-fx-font-weight: bold");
        Label playerCards = new Label("Cards: " + String.valueOf(player.handSize()));
        playerCards.setFont(Font.font(20));
        playerCards.setStyle("-fx-font-weight: bold");
        
        Button hitit = new Button("Hit it!");
        hitit.setOnAction(e -> {
            this.hitCardPlayer();
            tablesize.setText("Cards: " + String.valueOf(table.handSize()));
            playerCards.setText("Cards: " + String.valueOf(player.handSize()));
        });
        center.getChildren().add(hitit);
        center.getChildren().add(new ImageView(ImageStore.cardBackImage));
        center.getChildren().add(tablesize);
        //Cards and points of dealer (top of the playmat)
        VBox top = new VBox();
        top.setAlignment(Pos.CENTER);
        Label opponentCards = new Label("Cards: " + String.valueOf(opponent.handSize()));
        opponentCards.setFont(Font.font(20));
        opponentCards.setStyle("-fx-font-weight: bold");
        top.getChildren().add(new ImageView(ImageStore.cardBackImage));
        top.getChildren().add(opponentCards);
        //Cards and points of player (bottom of the playmat)
        VBox bottom = new VBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.getChildren().add(new ImageView(ImageStore.cardBackImage));
        bottom.getChildren().add(playerCards);
        
        playmat.setTop(top);
        playmat.setBottom(bottom);
        playmat.setCenter(center);    
    }
    
    public void endGame() {
        VBox ending = new VBox();
        ending.setAlignment(Pos.CENTER);
        Label gameOver = new Label("Game over!");
        gameOver.setFont(Font.font(20));
        gameOver.setStyle("-fx-font-weight: bold");
        Label winner = new Label();
        winner.setFont(Font.font(20));
        winner.setStyle("-fx-font-weight: bold");
        if (player.handSize() == 0) {
            winner.setText("Opponent is out of cards!\n" + "You win!");
        } else {
            winner.setText("You are out of cards!\n" + "You lose!");
        }
        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(10);
        Button exit = new Button("Exit");
        Button newGame = new Button("New Game");
        exit.setOnAction(e -> {
            stage.setScene(UI.start);
        });
        newGame.setOnAction(e -> {
            this.playRound();
        });
        ending.getChildren().add(gameOver);
        ending.getChildren().add(winner);
        buttons.getChildren().addAll(newGame, exit);
        ending.getChildren().add(buttons);
        playmat.setCenter(ending);
    }

    public void startGame() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        Label welcome = new Label("Welcome to hit it!");
        welcome.setFont(Font.font(20));
        welcome.setStyle("-fx-font-weight: bold");
        Label difficulty = new Label("Choose difficulty:");
        difficulty.setFont(Font.font(20));
        difficulty.setStyle("-fx-font-weight: bold");
        Button difficulty1 = new Button("500ms");
        difficulty1.setOnAction(e -> {
            this.time = 500;
            this.playRound();
        });
        Button difficulty2 = new Button("1000ms");
        difficulty1.setOnAction(e -> {
            this.time = 1000;
            this.playRound();
        });
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(10);
        hbox.getChildren().addAll(difficulty1, difficulty2);
        
        vbox.getChildren().add(welcome);
        vbox.getChildren().add(difficulty);
        vbox.getChildren().add(hbox);
        playmat.setCenter(vbox);
        scene = new Scene(playmat);
        stage.setScene(scene);
    }

    public void addForPlayer() {
        Iterator<Card> iterator = table.getHand().iterator();
        while (iterator.hasNext()) {
            Card next = iterator.next();
            player.add(next);
            iterator.remove();
        }
    }

    public void addForOpponent() {
        Iterator<Card> iterator = table.getHand().iterator();
        while (iterator.hasNext()) {
            Card next = iterator.next();
            opponent.add(next);
            iterator.remove();
        }
    }
    
    public void hitCardsPelaaja(int montako) {
        for (int i = 0; i < montako; i++) {
            Card hitted = this.hitCardPlayer();
            if (logic.checkTopTwo(table)) {
                return;
            }
            if (logic.checkFaceCard(hitted)) {
                this.hitCardsVastustaja(logic.cardValue(hitted));
                return;
            }
        }
        this.addForOpponent();
    }

    public void hitCardsVastustaja(int montako) {
        for (int i = 0; i < montako; i++) {
            Card hitted = this.hitCardOpponent();
            if (logic.checkTopTwo(table)) {
                return;
            }
            if (logic.checkFaceCard(hitted)) {
                this.hitCardsPelaaja(logic.cardValue(hitted));
                return;
            }
        }
        this.addForPlayer();
    }

    public Card hitCardPlayer() {
        Card card = null;
        try {
            card = player.getCard(0);
        } catch (IndexOutOfBoundsException e) {
            return card;
        }
        table.add(card);
        player.remove(card);
        return card;
    }

    public Card hitCardOpponent() {
        Card card = null;
        try {
            card = opponent.getCard(0);
        } catch (IndexOutOfBoundsException e) {
            return card;
        }
        table.add(card);
        opponent.remove(card);
        UI.wait(time);
        return card;
    }
}
