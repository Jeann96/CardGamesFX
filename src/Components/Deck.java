package Components;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Deck extends ImageView {
     
    final private ArrayList<Card> cards;
    final Image deckImage;
    
    public Deck() {
        this.cards = new ArrayList();
        deckImage = ImageStore.cardBackImage;
    }
    
    public void clear() {
        this.cards.clear();
        super.setImage(null);
    }

    public void buildDeck() {
        this.cards.clear();
         for (Enum Suit: Suit.values()) {
            this.cards.add(new Card("A", 1, Suit));
            for (int i = 2; i <= 10; i++) {
                this.cards.add(new Card(String.valueOf(i), i, Suit));
            }
            this.cards.add(new Card("J", 11, Suit));
            this.cards.add(new Card("Q", 12, Suit));
            this.cards.add(new Card("K", 13, Suit));
        }
        super.setImage(deckImage);
    }

    public Card drawCard() {
        if (this.cards.isEmpty()) {
            //super.setImage(null);
            System.out.println("Out of deck!");
            return null;
        }
        int random = randomRange(0, this.cards.size() - 1);
        Card card = this.cards.remove(random);
        if (this.cards.isEmpty()) {
            super.setImage(null);
        }
        return card;
    }
    
    public int deckSize() {
        return this.cards.size();
    }
    
    //Return a random number between min and max, including them
    public static int randomRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }
    
    @Override
    public String toString() {
        String deck = "";
        for (Card card: this.cards) {
            deck = deck.concat(card.toString() + "\n");
        }
        return deck;
    }
}

