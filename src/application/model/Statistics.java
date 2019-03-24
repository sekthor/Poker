package application.model;

import java.util.ArrayList;
import java.util.Collections;


public class Statistics {
	
	public static int handCount;
	public static ArrayList<Integer> counters;
	protected PokerGameModel model;
	
	public Statistics(PokerGameModel model) {
		handCount = 0;
		counters = new ArrayList<Integer>();
		this.model = model;
		for (int i = 0; i < HandType.values().length; i++) {
			int j = 0;
			counters.add(j);
		}
	}
	
	public static void addHand(HandType hand) {
		Integer count = counters.get(hand.ordinal());
		count++;
		counters.set(hand.ordinal(), count);
		handCount++;
	}
	
	public void resetStats() {
		handCount = 0;
		for (int i = 0; i < counters.size(); i++) {
			counters.set(i, 0);
		}
	}
	
	public static ArrayList<Double> getStats(){
		ArrayList<Double> percentage = new ArrayList<Double>();
		for (Integer count : counters) {
			double result = 100.0/handCount*count;
			percentage.add(result);
		}
		
		return percentage;
	}
	
	public int getTotal() {
		return handCount;
	}
	
	public String getRanking() {
		ArrayList<Player> players = (ArrayList<Player>) model.getPlayers().clone();
		Collections.sort(players);
		String string = "Rankings:";
		int count = 1;
		for (Player p : players) {
			string+= "\n"+count + ": " + p.getPlayerName();
			count++;
		}
		return string;
	}
	
}
