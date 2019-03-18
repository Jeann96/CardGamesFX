package Games;

import Components.Card;
import Components.Deck;
import Components.Hand;
import Logic.Casino_Logic;
import Main.UI;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Casino {
    
    final private Casino_Logic logic;
    final private Hand player;
    final private Hand opponent;
    final private BorderPane playmat;
    final private Hand table;
    final private Deck deck;
    final private ArrayList<Card> cardsPelaaja;
    final private ArrayList<Card> cardsVastustaja;
    private int pointsPlayer;
    private int spadesPlayer; 
    private int pointsOpponent;
    private int spadesOpponent;
    private int round;
    private Scene scene;
    public Stage stage;

    public Casino(Stage stage) {
        this.logic = new Casino_Logic();
        this.player = new Hand();
        this.opponent = new Hand();
        this.table = new Hand();
        this.deck = new Deck();
        this.cardsPelaaja = new ArrayList();
        this.pointsPlayer = 0;
        this.spadesPlayer = 0;
        this.cardsVastustaja = new ArrayList();
        this.pointsOpponent = 0;
        this.spadesOpponent = 0;
        this.playmat = new BorderPane();
        this.playmat.setPrefSize(1000, 500);
        this.playmat.setBackground(UI.background);
        this.round = 1;
        this.stage = stage;
    }
    
    public void playRound() {
        deck.clear();
        player.clear();
        opponent.clear();
        deck.buildDeck();
        table.draw(4, deck, true);
        player.draw(4, deck, true);
        opponent.draw(4, deck, false);

        //Cards and points of dealer
        VBox opponent_and_points = new VBox();
        opponent_and_points.setAlignment(Pos.CENTER);
        Label opponentPoints = new Label("Points: " + String.valueOf(pointsOpponent));
        opponentPoints.setFont(Font.font(20));
        opponentPoints.setStyle("-fx-font-weight: bold");
        opponent_and_points.getChildren().add(opponent.handImage());
        opponent_and_points.getChildren().add(opponentPoints);
        playmat.setTop(opponent_and_points);

        //Cards and points of player
        VBox player_and_points = new VBox();
        player_and_points.setAlignment(Pos.CENTER);
        Label playerPoints = new Label("Points: " + String.valueOf(pointsPlayer));
        playerPoints.setFont(Font.font(20));
        playerPoints.setStyle("-fx-font-weight: bold");
        player_and_points.getChildren().add(player.handImage());
        player_and_points.getChildren().add(playerPoints);
        playmat.setBottom(player_and_points);
        
        //Tabel cards
        playmat.setCenter(table.handImage());
        //Deck
        Label decksize = new Label(String.valueOf(deck.deckSize()));
        decksize.setFont(Font.font(20));
        decksize.setStyle("-fx-font-weight: bold");
    }

    public void endGame() {
        System.out.println("Peli päättyi: ");
        if (pointsPlayer > pointsOpponent) {
            System.out.print("Voitit!");
        } else if (pointsPlayer < pointsOpponent) {
            System.out.print("Hävisit!");
        } else {
            System.out.println("Tasapeli!");
        }
    }
    
    public void startGame() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        Label welcome = new Label("Welcome to casino!");
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

    public void endRound() {
        if (player.handSize() > opponent.handSize()) {
            System.out.println("Keräsit enemmän kortteja, +1p");
            pointsPlayer++;
        } else if (player.handSize() < opponent.handSize()) {
            System.out.println("Keräsit vähemmän kortteja");
            pointsOpponent++;
        } else {
            System.out.println("Molemmilla on yhtä paljon kerättyjä kortteja");
        }
        if (spadesPlayer > spadesOpponent) {
            System.out.println("Keräsit enemmän patoja, +2p");
            pointsPlayer += 2;
        } else if (spadesPlayer < spadesOpponent) {
            System.out.println("Keräsit vähemmän patoja");
            pointsOpponent += 2;
        } else {
            System.out.println("Kerättyjä patoja on yhtä paljon");
        }
        round++;
        cardsPelaaja.clear();
        cardsVastustaja.clear();
        deck.buildDeck();
        table.clear();
        table.draw(4, deck, true);
    }

    public void hitCardPelaaja(Card card, int chosen) {
        player.remove(card);
        ArrayList<ArrayList<Card>> sumCombinations = this.sumCombinations(card);
        if (sumCombinations.isEmpty()) {
            table.add(card);
            return;
        }
        this.addForPelaaja(card);
        sumCombinations.get(chosen - 1).forEach((kortti) -> {
            this.addForPelaaja(kortti);
        });
    }

    public void hitCardVastustaja(Card card, int best) {
        opponent.remove(card);
        ArrayList<ArrayList<Card>> sumCombinations = this.sumCombinations(card);
        if (sumCombinations.isEmpty()) {
            table.add(card);
            return;
        }
        this.addForVastustaja(card);
        sumCombinations.get(best - 1).forEach((kortti) -> {
            this.addForVastustaja(kortti);
        });
    }

    public void addForPelaaja(Card card) {
        table.remove(card);
        cardsPelaaja.add(card);
        pointsPlayer += logic.points(card);
        if (card.getSuit().equals("spades")) {
            spadesPlayer++;
        }
    }

    public void addForVastustaja(Card card) {
        table.remove(card);
        cardsVastustaja.add(card);
        pointsOpponent += logic.points(card);
        if (card.getSuit().equals("spades")) {
            spadesOpponent++;
        }
    }

    public ArrayList<ArrayList<Card>> sumCombinations(Card handCard) {
        ArrayList<ArrayList<Card>> canTake = new ArrayList();
        ArrayList<ArrayList<Card>> combinations = logic.combinations(table);
        for (ArrayList<Card> combination : combinations) {
            if (logic.sumOfElements(combination) == logic.valueInHand(handCard)) {
                canTake.add(combination);
            }
        }
        return canTake;
    }
}
