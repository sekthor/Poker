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
    			//winner = tiebreak(winner, players.get(i));
    		}
    	} 
    	return players.indexOf(winner);	
    }
	
	
	
	
	
	
	protected static Player tiebreak(Player p1, Player p2) {
		
		ArrayList<Card> hand1 = (ArrayList<Card>) p1.getCards().clone();
		ArrayList<Card> hand2 = (ArrayList<Card>) p2.getCards().clone();
		Collections.sort(hand1);
		Collections.sort(hand2);
		
		boolean player1wins = true;
		
		switch (p1.getHand()) {
		
			case HighCard:
				player1wins = hasHighestCard(hand1, hand2);
				
				
			case OnePair:
				int comp = getPairCard(hand1).compareTo(getPairCard(hand2));
				if(comp == 0) {
					player1wins = hasHighestCard(hand1,hand2);
				} else if( comp > 0) {
					player1wins = true;
				} else {
					player1wins = false;
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
		
		
		if (player1wins) {
			return p1;
		} else {
			return p2;
		}
	}
	
	protected static boolean hasHighestCard(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		/*
		 * Player with higher highest Card wins, if Cards match
		 * next lower Cards are compared
		 */
		for (int i=hand1.size(); i >= 0; i--) {
			if (hand1.get(4).compareTo(hand2.get(4))>0) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	
	
	protected static Card getPairCard(ArrayList<Card> hand) {
		boolean found=false;
		Card card = null;
		for (int i=0; i<hand.size()-1 && !found; i++) {
			if(hand.get(i).getRank().ordinal() == hand.get(i+1).getRank().ordinal()) {
				card= hand.get(i);
			}	
		}
		return card;
	}
	
	private static boolean hasHighestTriplet(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		return true;
	}
	
}
