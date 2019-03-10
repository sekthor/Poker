package application.model;

import java.util.ArrayList;
import java.util.Collections;

import application.model.Card.Suit;

public enum HandType {
    HighCard, OnePair, TwoPair, ThreeOfAKind, Straight, Flush, FullHouse, FourOfAKind, StraightFlush;
    
    /**
     * Determine the value of this hand. Note that this does not
     * account for any tie-breaking
     */
    public static HandType evaluateHand(ArrayList<Card> cards) {
        HandType currentEval = HighCard;
        
        if (isOnePair(cards)) currentEval = OnePair;
        if (isTwoPair(cards)) currentEval = TwoPair;
        if (isThreeOfAKind(cards)) currentEval = ThreeOfAKind;
        if (isStraight(cards)) currentEval = Straight;
        if (isFlush(cards)) currentEval = Flush;
        if (isFullHouse(cards)) currentEval = FullHouse;
        if (isFourOfAKind(cards)) currentEval = FourOfAKind;
        if (isStraightFlush(cards)) currentEval = StraightFlush;
        
        return currentEval;
    }
    
    public static boolean isOnePair(ArrayList<Card> cards) {
    	// iteriert durch die arrayList bis zum Zweitletzten
    	// zweiter loop vom zweiten bis zum letzten, vergleicht
    	// Werte vom ersten und zweiten Loop
        boolean found = false;
        for (int i = 0; i < cards.size() - 1 && !found; i++) {
            for (int j = i+1; j < cards.size() && !found; j++) {
                if (cards.get(i).getRank() == cards.get(j).getRank()) found = true;
            }
        }
        return found;
    }
    
    public static boolean isTwoPair(ArrayList<Card> cards) {
        // Clone the cards, because we will be altering the list
        ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();
        
        // Same as isOnePair() but if a pair is found, values will be 
        // deleted, and isOnePair() is executed.
        // Find the first pair; if found, remove the cards from the list
        boolean firstPairFound = false;
        for (int i = 0; i < clonedCards.size() - 1 && !firstPairFound; i++) {
            for (int j = i+1; j < clonedCards.size() && !firstPairFound; j++) {
                if (clonedCards.get(i).getRank() == clonedCards.get(j).getRank()) {
                    firstPairFound = true;
                    clonedCards.remove(j);  // Remove the later card
                    clonedCards.remove(i);  // Before the earlier one
                }
            }
        }
        // If a first pair was found, see if there is a second pair
        return firstPairFound && isOnePair(clonedCards);
    }
    
    
    public static boolean isThreeOfAKind(ArrayList<Card> cards) {
    	/***
         * first for loop goes from first car to third last
         * second from second card to second last
         * third from third card to last 
         * if cards from first and second loop match, they will be compared
         * to the card from third loop
         * if they match as well, we've got a Three of a Kind Hand
         * ***/
    	boolean found = false;
        for (int i = 0; i < cards.size() - 2 && !found; i++) {
            for (int j = i+1; j < cards.size() - 1  && !found; j++) {
                if (cards.get(i).getRank() == cards.get(j).getRank()) {
                	for (int k = j+1; k < cards.size() && !found; k++) {
                		if(cards.get(j).getRank() == cards.get(k).getRank()) {
                			found = true;
                		}
                	}
                }
            }
        }
        return found;        
    }
    
    
    
    public static boolean isStraight(ArrayList<Card> cards) {
    	/***
         * At fist we clone and then sort the cards using the sort() method
         * we can now assume in order to check a straight hand each card 
         * must have a rank exactly 1 ordinal higher than the previous one.
         * so compareTo() has to return a -1 every time. Otherwise we return 
         * false immediately.
         * ***/
        ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();
        
        Collections.sort(clonedCards);
        boolean stillPossible = true;
        for(int i = 0; i < clonedCards.size() - 1 && stillPossible; i++) {
        	if (clonedCards.get(i).compareTo(clonedCards.get(i+1)) != -1) {
        		stillPossible = false;
        	} 
        } 
        return stillPossible;    
    }
    
    public static boolean isFlush(ArrayList<Card> cards) {
    	/***
    	 * checks if all the cards have the same suit***/
        Suit suit = cards.get(0).getSuit();
        boolean stillPossible = true;
        for (int i = 1; i < cards.size() && stillPossible; i++) {
        	if (cards.get(i).getSuit() != suit) {
        		stillPossible = false;
        	}
        }
        return stillPossible;
    }
    
    public static boolean isFullHouse(ArrayList<Card> cards) {
        // TODO        
        return false;
    }
    
    public static boolean isFourOfAKind(ArrayList<Card> cards) {
        // TODO        
        return false;
    }
    
    public static boolean isStraightFlush(ArrayList<Card> cards) {
        // TODO        
        return false;
    }
}
