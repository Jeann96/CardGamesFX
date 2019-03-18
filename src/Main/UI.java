package Main;

import Components.ImageStore;
import Games.Blackjack;
import Games.Casino;
import Games.HitIt;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UI extends Application {
    public static Scene start;
    public static Background background;

    public UI() {
        background = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));
    }
    
    @Override
    public void start(Stage stage) {
        //Card images to HashMap
        ImageStore.loadImages();
        //Buttons
        Button quitButton = new Button("Quit");
        Button blackjackButton = new Button("Blackjack");
        Button casinoButton = new Button("Casino");
        Button hitItButton = new Button("Hit it!");       
        quitButton.setOnAction(e -> {
            stage.close();
        });
        blackjackButton.setOnAction(e -> {
            Blackjack blackjack = new Blackjack(stage);
            stage.setTitle("Blackjack");
            blackjack.startGame();
        });
        casinoButton.setOnAction(e -> {
            Casino casino = new Casino(stage);
            stage.setTitle("Casino");
            casino.startGame();
        });
        hitItButton.setOnAction(e -> {
            HitIt hitIt = new HitIt(stage);
            stage.setTitle("Hit it");
            hitIt.startGame();
        });
        VBox layout = new VBox();
        layout.setPrefSize(1000, 500);
        layout.setBackground(UI.background);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        Label welcome = new Label("Welcome to card games!");
        welcome.setAlignment(Pos.CENTER);
        welcome.setFont(Font.font(20));
        welcome.setStyle("-fx-font-weight: bold");
        layout.getChildren().add(welcome);
        
        HBox blackjack = new HBox();
        blackjack.setAlignment(Pos.CENTER);
        Label playBlackjack = new Label("Press to play blackjack: ");
        playBlackjack.setFont(Font.font(20));
        playBlackjack.setStyle("-fx-font-weight: bold");
        blackjack.getChildren().addAll(playBlackjack, blackjackButton);
        layout.getChildren().add(blackjack);
        
        HBox casino = new HBox();
        casino.setAlignment(Pos.CENTER);
        Label playCasino = new Label("Press to play casino: ");
        playCasino.setFont(Font.font(20));
        playCasino.setStyle("-fx-font-weight: bold");
        casino.getChildren().addAll(playCasino, casinoButton);
        layout.getChildren().add(casino);
        
        HBox hitIt = new HBox();
        hitIt.setAlignment(Pos.CENTER);
        Label playHitIt = new Label("Press to play hit it: ");
        playHitIt.setFont(Font.font(20));
        playHitIt.setStyle("-fx-font-weight: bold");
        hitIt.getChildren().addAll(playHitIt, hitItButton);
        layout.getChildren().add(hitIt);
        
        HBox quit = new HBox();
        quit.setAlignment(Pos.CENTER);
        Label quitText = new Label("Press to quit app: ");
        quitText.setFont(Font.font(20));
        quitText.setStyle("-fx-font-weight: bold");
        quit.getChildren().addAll(quitText, quitButton);
        layout.getChildren().add(quit);
        
        Scene scene = new Scene(layout);
        
        start = scene;
        stage.setScene(start);
        stage.setTitle("Card games");
        stage.show();
    }
    
    public static void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    //Main class
    public static void main(String[] args) {
        Application.launch(args);
    }
}
