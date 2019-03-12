package application.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import application.model.Card.Rank;

public class Winner {

	/***
	 * returns the index position of winner in ArrayList by
	 * comparing ordinals of the HandTypes 
	 */
	public static int evaluateWinner(ArrayList<Player> players) {
		
		ArrayList<HandType> hands = new ArrayList<HandType>();
		for (Player p : players) {
			hands.add(p.getHand());
		}
		Player winner = players.get(0);
    	
    	for(int i = 1; i < players.size(); i++) {
    		if (winner.getHand().ordinal() < players.get(i).getHand().ordinal()) {
    			winner = players.get(i);
    		} else if (winner.getHand().ordinal() == players.get(i).getHand().ordinal()) {
    			winner = tiebreak(winner, players.get(i));
    		}
    	} 
    	return players.indexOf(winner);	
    }
	
	
	
	
	private static Player tiebreak(Player p1, Player p2) {
		
		ArrayList<Card> hand1 = (ArrayList<Card>) p1.getCards().clone();
		ArrayList<Card> hand2 = (ArrayList<Card>) p2.getCards().clone();
		Collections.sort(hand1);
		Collections.sort(hand2);
		
		switch (p1.getHand()) {
		
		
			case HighCard:
				if(hand1.get(4).compareTo(hand2.get(4)) == 0) {
					if(hand1.get(4).getSuit().ordinal() > hand2.get(4).getSuit().ordinal()) {
						return p1;
					} else {
						return p2;
					}
				} else if(hand1.get(4).compareTo(hand2.get(4)) > 0) {
					return p1;
				} else {
					return p2;
				}
				
				
			case OnePair:
				Card.Rank pair1Rank = Rank.Two;
				Card.Rank pair2Rank = Rank.Two;
				boolean found = false;
				for(int i = 0; i < hand1.size() && !found; i++) {
					if (hand1.get(i).getRank() == hand1.get(i+1).getRank()) {
						pair1Rank = hand1.get(i).getRank();
						found = true;
					}
				}
				found= false;
				for(int i = 0; i < hand2.size() && !found; i++) {
					if (hand2.get(i).getRank() == hand2.get(i+1).getRank()) {
						pair2Rank = hand2.get(i).getRank();
						found = true;
					}
				}
				if (pair1Rank.ordinal() == pair2Rank.ordinal()) {
					/* TODO: need to figure out how to check for suit*/ 
					return p1;
				} else if (pair1Rank.ordinal() > pair2Rank.ordinal()){
					return p1;
				} else {
					return p2;
				}
				
			
			case TwoPair:
				break;
				
				
			case ThreeOfAKind:
				/*
				 * Since the ArrayList is sorted, the third Card (position 2) will always
				 * be part of the ThreeOfAKind. so we only need to compare the ranks of 
				 * the two cards on position 2.
				 */
				if(hand1.get(2).getRank().ordinal() == hand2.get(2).getRank().ordinal()) {
					// if they match we need to compare ordinal of Suits
					if(hand1.get(2).getSuit().ordinal() > hand2.get(2).getSuit().ordinal()) {
						return p1;
					} else {
						return p2;
					}		
				} else if(hand1.get(2).getRank().ordinal() > hand2.get(2).getRank().ordinal()){
					return p1;
				} else {
					return p2;
				}
				
				
			case Straight:
				/*
				 * Since the ArrayList is sorted, we only need to check which hand has the highest 
				 * card at the end.
				 */
				if (hand1.get(4).getRank().ordinal() == hand2.get(4).getRank().ordinal()) {
					if (hand1.get(4).getSuit().ordinal() > hand2.get(4).getSuit().ordinal()) {
						return p1;
					} else {
						return p2;
					}
				} else if (hand1.get(4).getRank().ordinal() > hand2.get(4).getRank().ordinal()){
					return p1;
				} else {
					return p2;
				}
				
				
			case Flush:
				break;
			case FullHouse:
				break;
			case FourOfAKind:
				break;
			case StraightFlush:
				break;
			case RoyalFlush:
				break;
		}
		return p1;
	}
}
