package application.model;

import java.util.ArrayList;
import java.util.Collections;

import application.model.Card.Rank;
import application.model.Card.Suit;

public enum HandType {
    HighCard, OnePair, TwoPair, ThreeOfAKind, Straight, Flush, FullHouse, FourOfAKind, StraightFlush, RoyalFlush;
    
	
	
    /**
     * Determine the value of this hand. Note that this does not
     * account for any tie-breaking
     */	
    public static HandType evaluateHand(ArrayList<Card> cards) {
        HandType currentEval = HighCard;
        
        /***
    	 * These variable are used to remember whether either of these cases
    	 * have been proven true. So we don't have to check for it again when 
    	 * evaluating a straightFlush or a RoyalFlush
    	 * ***/
        boolean straight = false;
    	boolean flush = false;
    	boolean straightFlush = false;
    	
    	
    	/***
    	 * EXTREMELY IMPORTANT!!!
    	 * Certain methods require a sorted ArrayList. To cut down
    	 * the amount of lines of code we sort right at the beginning.
    	 * This might be the most crucial line of this file! 
    	 */
    	ArrayList<Card> clonedCards = (ArrayList<Card>) cards.clone();
    	Collections.sort(clonedCards);
    	
    	
        if (isOnePair(clonedCards)) currentEval = OnePair;
        if (isTwoPair(clonedCards)) currentEval = TwoPair;
        if (isThreeOfAKind(clonedCards)) currentEval = ThreeOfAKind;
        if (isStraight(clonedCards)) {currentEval = Straight; straight = true;}
        if (isFlush(clonedCards)) {currentEval = Flush; flush = true;}
        if (isFullHouse(clonedCards)) currentEval = FullHouse; 
        if (isFourOfAKind(clonedCards)) currentEval = FourOfAKind;
        if (isStraightFlush(straight, flush)) {currentEval = StraightFlush; straightFlush = true;}
        if (isRoyalFlush(clonedCards, straightFlush)) currentEval = RoyalFlush;
        
        
        return currentEval;
    }
    
    
    public static boolean isOnePair(ArrayList<Card> cards) {
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
        
        /*** Same as isOnePair() but if a pair is found, values will be 
         * deleted, and isOnePair() is executed.
         * Find the first pair; if found, remove the cards from the list
         * ***/
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
         * first for loop goes from first card to third last
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
        
        boolean stillPossible = true;
        for(int i = 0; i < cards.size() - 1 && stillPossible; i++) {
        	if (cards.get(i).compareTo(cards.get(i+1)) != -1) {
        		stillPossible = false;
        	} 
        } 
        return stillPossible;    
    }
    
    
    public static boolean isFlush(ArrayList<Card> cards) {
    	/***
    	 * checks if all the cards have the same suit
    	 * ***/
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
    	/***
    	 * This only works since we've sorted the list
    	 * if 1st card = 2nd card & 3rd card = 5th card OR
    	 * 1st = 3rd & 4th = 5th, we've got us a full House
    	 */
    	if((cards.get(0).getRank() == cards.get(1).getRank()  &&
    		cards.get(2).getRank() == cards.get(4).getRank()) ||
    	   (cards.get(0).getRank() == cards.get(2).getRank()  &&
    	    cards.get(3).getRank() == cards.get(4).getRank())) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    
    public static boolean isFourOfAKind(ArrayList<Card> cards) {
    	boolean found = false;
    	/***
    	 * Since we are dealing with a sorted ArrayList, we only have to 
    	 * check whether the fist and the second last, or the second and 
    	 * the last card have the same rank.
    	 */
    	if(cards.get(0).getRank() == cards.get(3).getRank() ||
    	   cards.get(1).getRank() == cards.get(4).getRank()) {
    		found = true;
    	}
        return found;    
    }
    
    
    public static boolean isStraightFlush(boolean straight, boolean flush) {
    	/***
    	 * if a hand has been proven to be both a straight and a flush,
    	 * we don't bother checking again and just return true
    	 * ***/
        if(straight && flush) {
        	return true;
        } else {
        	return false;
        }   
    }
    
    public static boolean isRoyalFlush(ArrayList<Card> cards, boolean straightFlush) {
    	/***
    	 * If the hand is a straight-flush we only need to check whether or
    	 * not there's an ace in it. Since it is a straight, the Ace being 
    	 * the highest Card would automatically make it a RoyalFlush.
    	 * Since the ArrayList is sorted, if there's an ace it will be at 
    	 * position 4.
    	 * ***/
    	if(straightFlush) {
    		if(cards.get(4).getRank() == Rank.Ace) {
    			return true;
    		} 
    	} return false;
    }
    
}
