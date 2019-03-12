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
    	HandType winningHand = hands.get(0);
    	for(int i = 1; i < hands.size(); i++) {
    		if (winningHand.ordinal() < hands.get(i).ordinal()) {
    			winningHand = hands.get(i);
    		} else if (winningHand.ordinal() == hands.get(i).ordinal()) {
    			
    		}
    	} 
    	return hands.indexOf(winningHand);
    	
    }
	
	private static void tiebreak() {
		
	}
}
