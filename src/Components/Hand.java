package Components;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class Hand extends ImageView {

    final private ArrayList<Card> cards;
    final private FlowPane handImage;

    public Hand() {
        this.cards = new ArrayList();
        this.handImage = new FlowPane();
        this.handImage.setAlignment(Pos.CENTER);
    }
    
    public void add(Card card) {
        this.cards.add(card);
        handImage.getChildren().add(card);
    }

    public void clear() {
        this.cards.clear();
        handImage.getChildren().clear();
    }

    public Card getCard(int index) {
        if (this.cards.isEmpty()) {
            return null;
        }
        return this.cards.get(index);
    }

    public void remove(Card card) {
        this.cards.remove(card);
    }

    public int handSize() {
        return this.cards.size();
    }

    public void draw(int many, Deck deck, boolean faceUp) {
        for (int i = 1; i <= many; i++) {
            Card card = deck.drawCard();
            if (card == null) {
                return;
            }
            if (faceUp == true) {
                card.flipCardFaceUp();
            }
            handImage.getChildren().add(card);
            this.cards.add(card);
        }
    }

    public ArrayList<Card> getHand() {
        return this.cards;
    }
    
    public FlowPane handImage() {
        return handImage;
    }
    
    public void flipCards() {
        this.cards.forEach((card) -> {
           card.flipCard();
        });
    }
    
    public void flipCardsFaceUp() {
        this.cards.forEach((card) -> {
           card.flipCardFaceUp();
        });
    }
    
    public void flipCardsFaceDown() {
        this.cards.forEach((card) -> {
           card.flipCardFaceDown();
        });
    }

    public boolean contains(Card card) {
        return this.cards.contains(card);
    }

    public int numberOfCards(String name) {
        int number = 0;
        for (Card card : this.cards) {
            if (card.getName().equals(name)) {
                number++;
            }
        }
        return number;
    }

    @Override
    public String toString() {
        return this.cards.toString();
    }
}
