package application.model;

import java.util.ArrayList;

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
		switch(p1.getHand()) {
			case HighCard:
				break;
			case OnePair:
				break;
			case TwoPair:
				break;
			case ThreeOfAKind:
				break;
			case Straight:
				break;
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
