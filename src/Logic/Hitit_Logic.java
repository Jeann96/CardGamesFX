package Logic;

import Components.Card;
import Components.Hand;
import Main.UI;

public class Hitit_Logic {


    public Hitit_Logic() {
        
    }

    public boolean checkFaceCard(Card card) {
        return card.isAceCard() || card.isFaceCard();
    }

    public int cardValue(Card card) {
        if (card.isFaceCard()) {
            return card.getCardValue() - 10;
        }
        if (card.isAceCard()) {
            return 4;
        }
        return 0;
    }
    
    public boolean checkTopTwo(Hand table) {
        if (table.handSize() <= 1) {
            return false;
        }
        if (table.getCard(table.handSize() - 1).getCardValue() == table.getCard(table.handSize() - 2).getCardValue()) {
            return true;
        }
        return false;
    }
}

