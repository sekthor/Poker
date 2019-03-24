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
	
	
	
	
	
	
	protected static Player tiebreak(Player p1, Player p2) {
		
		ArrayList<Card> hand1 = (ArrayList<Card>) p1.getCards().clone();
		ArrayList<Card> hand2 = (ArrayList<Card>) p2.getCards().clone();
		Collections.sort(hand1);
		Collections.sort(hand2);
		int comp, comp2; //used to store compareTo return values
		
		boolean player1wins = true;
		
		switch (p1.getHand()) {
			
			
			case HighCard:
				/*
				 * The player with the highest card in his hand wins
				 */
				player1wins = hasHighestCard(hand1, hand2);
				break;
				
				
			case OnePair:
				/*
				 * In this case we get a Card from the pair of both Hand, and compare
				 * their ranks. If they match the player with the highest card wins.
				 */
				comp = getPairCard(hand1).compareTo(getPairCard(hand2));
				if(comp == 0) {
					player1wins = hasHighestCard(hand1,hand2);
				} else if( comp > 0) {
					player1wins = true;
				} else {
					player1wins = false;
				}
				break;
			
				
			
			case TwoPair:
				/*
				 * works the same way OnePair does however if the higher pairs match
				 * the lower pairs are compared. Only if those match as well, we evaluate
				 * highest card. 
				 */
				comp = getPairCard(hand1).compareTo(getPairCard(hand2));
				if(comp == 0) {
					boolean found = false;
					for(int i=hand1.size()-1; !found; i--) {
						// in reverse order so removal of cards won't affect ordinals 
						if (hand1.get(i).compareTo(hand1.get(i-1))==0) {
							hand1.remove(i);
							hand1.remove(i-1);
							
							found = true;
						}
					}
					found = false;
					for(int i=hand2.size()-1; !found; i--) {
						// in reverse order so removal of cards won't affect ordinals 
						if (hand2.get(i).compareTo(hand2.get(i-1))==0) {
							hand2.remove(i);
							hand2.remove(i-1);
							
							found = true;
						}
					}
					comp2 = getPairCard(hand1).compareTo(getPairCard(hand2));
					if(comp2 == 0) {
						player1wins = hasHighestCard(hand1,hand2);
					} else if( comp > 0) {
						player1wins = true;
					} else {
						player1wins = false;
					}
				} else if( comp > 0) {
					player1wins = true;
				} else {
					player1wins = false;
				}
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
						player1wins = true;
					} else {
						player1wins = false;
					}		
				} else if(hand1.get(2).getRank().ordinal() > hand2.get(2).getRank().ordinal()){
					player1wins = true;
				} else {
					player1wins = false;
				}
				
				
			case Straight:
				player1wins = hasHighestCard(hand1, hand2);
				break;
				
				
			case Flush:
				player1wins = hasHighestCard(hand1, hand2);
				break;
				
				
			case FullHouse:
				/*
				 * sorted list, so: the middle card will always be part of the threeOfAKind
				 * so we compare the two middle cards. Since there only a single deck, it is 
				 * impossible for those cards to match.
				 */
				comp = hand1.get(2).compareTo(hand2.get(2));
				if (comp > 0) {
					player1wins = true;
				} else {
					player1wins = false;
				}
				break;
				
				
			case FourOfAKind:
				/*
				 * Cards 1-3 will always be part of the FourOfAKind. Odd one is either in position 
				 * 0 or 4. So we can just compare Card no.2 of both decks.
				 * With one deck it impossible for them to match. 
				 * */
				comp = hand1.get(2).compareTo(hand2.get(2));
				if (comp > 0) {
					player1wins = true;
				} else {
					player1wins = false;
				}
				break;
				
				
			case StraightFlush:
				player1wins = hasHighestCard(hand1, hand2);
				break;
				
				
			case RoyalFlush:
				/*
				 * No rule
				 * */
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
			if (hand1.get(hand1.size()-1).compareTo(hand2.get(hand2.size()-1))>0) {
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
		for (int i= hand.size()-1; i>0 && !found; i--) {
			if(hand.get(i).getRank().ordinal() == hand.get(i-1).getRank().ordinal()) {
				card = hand.get(i);
				found = true;
			}	
		}
		return card;
	}
}