package Components;

import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView {

    final private Enum suit;
    final private int cardValue;
    final private String name;
    final private Image faceDownImage;
    final private Image faceUpImage;
    private boolean faceUp;

    public Card(String name, int value, Enum suit) {
        this.suit = suit;
        this.cardValue = value;
        this.name = name;
        this.faceDownImage = ImageStore.cardBackImage;
        this.faceUpImage = ImageStore.cardFaceImages.get(this.toString());
        this.faceUp = false;
        super.setImage(ImageStore.cardBackImage);  
    }
    
    public void flipCard() {
        if (faceUp) {
            super.setImage(faceDownImage);
            faceUp = false;
        } else {
            super.setImage(faceUpImage);
            faceUp = true;
        }
    }
    
    public void flipCardFaceUp() {
        if (!(faceUp)) {
            super.setImage(faceUpImage);
            faceUp = true;
        }
    }
    
    public void flipCardFaceDown() {
        if (faceUp) {
            super.setImage(faceUpImage);
            faceUp = false;
        }
    }
    
    public boolean isFaceUp() {
        return faceUp;
    }

    @Override
    public String toString() {
        return this.name + "_of_" + this.suit;
    }

    public int getCardValue() {
        return this.cardValue;
    }

    public String getSuit() {
        return String.valueOf(this.suit);
    }

    public String getName() {
        return this.name;
    }

    public boolean isAceCard() {
        return this.getName().equals("A");
    }

    public boolean isFaceCard() {
        String name = this.getName();
        return name.equals("J") || name.equals("Q") || name.equals("K");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.suit);
        hash = 97 * hash + this.cardValue;
        hash = 97 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.cardValue != other.cardValue) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (this.suit != other.suit) {
            return false;
        }
        return true;
    }
    
    public static Card nameToCard(String card) {
        String nameLetter = card.substring(0, 1);
        String suitLetter = card.substring(1, 2);
        int value;
        switch (nameLetter) {
            case "A":
                value = 1;
                break;
            case "J":
                value = 11;
                break;
            case "Q":
                value = 12;
                break;
            case "K":
                value = 13;
                break;
            default:
                value = Integer.valueOf(nameLetter);
                break;
        }
        Enum suit;
        switch (suitLetter) {
            case "s":
                suit = Suit.spades;
                break;
            case "h":
                suit = Suit.hearts;
                break;
            case "c":
                suit = Suit.clubs;
                break;
            case "d":
                suit = Suit.diamonds;
                break;
            default:
                suit = null;
                break;
        }
        return new Card(nameLetter, value, suit);
    }
}
