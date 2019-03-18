package Logic;

import Components.Card;
import Components.Hand;
import java.util.ArrayList;

public class Casino_Logic {
    
    public Casino_Logic() {
        
    }
    
    public int mostCards(ArrayList<ArrayList<Card>> otettavat) {
        ArrayList<Card> pisin = otettavat.get(0);
        int i = 0;
        int pisimmanIndeksi = 0;
        while (i < otettavat.size()) {
            if (otettavat.get(i).size() > pisin.size()) {
                pisimmanIndeksi = i;
            }
            i++;
        }
        return pisimmanIndeksi + 1;
    }
    
    public void otettavat(ArrayList<ArrayList<Card>> sumCombinations) {
        System.out.println("Mitä otetaan?");
        String otettavat = "";
        for (ArrayList<Card> combination : sumCombinations) {
            otettavat = otettavat.concat(combination.toString() + " ");
        }
        System.out.print(otettavat + "\n>");
    }
    
    public int valueInHand(Card card) {
        if (card.getName().equals("A")) {
            return 14;
        } else if (card.toString().equals("2_of_spades")) {
            return 15;
        } else if (card.toString().equals("10_of_diamonds")) {
            return 16;
        } else {
            return card.getCardValue();
        }
    }
    
    public int points(Card card) {
        if (card.isAceCard() || card.toString().equals("2_of_spades")) {
            return 1;
        } else if (card.toString().equals("10_of_diamonds")) {
            return 2;
        } else {
            return 0;
        }
    }
    
    public Card highestPoint(ArrayList<Card> cards) {
        Card korkein = cards.get(0);
        for (Card card : cards) {
            if (this.points(card) > this.points(korkein)) {
                korkein = card;
            }
        }
        return korkein;
    }
    
    public Card smallestValue(ArrayList<Card> cards) {
        if (cards.isEmpty()) {
            return null;
        }
        Card pienin = cards.get(0);
        for (Card card : cards) {
            if (this.valueInHand(card) < this.valueInHand(pienin)) {
                pienin = card;
            }
        }
        return pienin;
    }
    
    public int sumOfElements(ArrayList<Card> cards) {
        int summa = 0;
        for (Card card : cards) {
            summa += card.getCardValue();
        }
        return summa;
    }
    
    public ArrayList<ArrayList<Card>> combinations(Hand hand) {
        ArrayList<Card> tableCards = hand.getHand();
        ArrayList<ArrayList<Card>> combinations = new ArrayList();
        for (int index = 0; index < tableCards.size(); index++) {

            ArrayList<Card> oneCardCombination = new ArrayList();
            oneCardCombination.add(tableCards.get(index));
            combinations.add(oneCardCombination);

            for (int i = 1; i < tableCards.size() - index; i++) {
                ArrayList<Card> twoCardCombination = new ArrayList();
                twoCardCombination.add(tableCards.get(index));
                twoCardCombination.add(tableCards.get(index + i));
                combinations.add(twoCardCombination);
            }

            for (int i = 1; i < tableCards.size() - index; i++) {
                for (int j = 1; j < tableCards.size() - i - index; j++) {
                    ArrayList<Card> threeCardCombination = new ArrayList();
                    threeCardCombination.add(tableCards.get(index));
                    threeCardCombination.add(tableCards.get(index + i));
                    threeCardCombination.add(tableCards.get(index + i + j));
                    combinations.add(threeCardCombination);
                }
            }

            for (int i = 1; i < tableCards.size() - index; i++) {
                for (int j = 1; j < tableCards.size() - i - index; j++) {
                    for (int k = 1; k < tableCards.size() - i - j - index; k++) {
                        ArrayList<Card> fourCardCombination = new ArrayList();
                        fourCardCombination.add(tableCards.get(index));
                        fourCardCombination.add(tableCards.get(index + i));
                        fourCardCombination.add(tableCards.get(index + i + j));
                        fourCardCombination.add(tableCards.get(index + i + j + k));
                        combinations.add(fourCardCombination);
                    }
                }
            }

            for (int i = 1; i < tableCards.size() - index; i++) {
                for (int j = 1; j < tableCards.size() - i - index; j++) {
                    for (int k = 1; k < tableCards.size() - i - j - index; k++) {
                        for (int v = 1; v < tableCards.size() - i - j - k - index; v++) {
                            ArrayList<Card> fiveCardCombination = new ArrayList();
                            fiveCardCombination.add(tableCards.get(index));
                            fiveCardCombination.add(tableCards.get(index + i));
                            fiveCardCombination.add(tableCards.get(index + i + j));
                            fiveCardCombination.add(tableCards.get(index + i + j + k));
                            fiveCardCombination.add(tableCards.get(index + i + j + k + v));
                            combinations.add(fiveCardCombination);
                        }
                    }
                }
            }

            for (int i = 1; i < tableCards.size() - index; i++) {
                for (int j = 1; j < tableCards.size() - i - index; j++) {
                    for (int k = 1; k < tableCards.size() - i - j - index; k++) {
                        for (int v = 1; v < tableCards.size() - i - j - k - index; v++) {
                            for (int w = 1; w < tableCards.size() - i - j - k - v - index; w++) {
                                ArrayList<Card> sixCardCombination = new ArrayList();
                                sixCardCombination.add(tableCards.get(index));
                                sixCardCombination.add(tableCards.get(index + i));
                                sixCardCombination.add(tableCards.get(index + i + j));
                                sixCardCombination.add(tableCards.get(index + i + j + k));
                                sixCardCombination.add(tableCards.get(index + i + j + k + v));
                                sixCardCombination.add(tableCards.get(index + i + j + k + v + w));
                                combinations.add(sixCardCombination);
                            }
                        }
                    }
                }
            }

        }
        return combinations;
    }   
}
