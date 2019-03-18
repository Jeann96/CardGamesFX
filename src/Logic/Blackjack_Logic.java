package Logic;

import Components.Card;
import Components.Hand;

public class Blackjack_Logic {

    public Blackjack_Logic() {
    }

    public boolean isBlackjack(Hand hand) {
        return this.sum(hand) == 21 && hand.handSize() == 2;
    }
    
    public boolean over21(Hand hand) {
        return this.sum(hand) > 21;
    }

    public int sum(Hand hand) {
        int summa = 0;
        int aceCounter = 0;
        for (int i = 0; i < hand.handSize(); i++) {
            summa = summa + this.points(hand.getHand().get(i));
        }
        while (summa > 21 && aceCounter < hand.numberOfCards("A")) {
            summa = summa - 10;
            aceCounter++;
        }
        return summa;
    }

    public int points(Card card) {
        if (card.isFaceCard()) {
            return 10;
        } else if (!(card.isFaceCard()) && !(card.isAceCard())) {
            return card.getCardValue();
        } else {
            return 11;
        }
    }
    
    public void play(Hand dealer) {
        
    }

}
