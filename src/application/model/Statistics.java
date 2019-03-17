package application.model;

import java.util.ArrayList;

public class Statistics {
	
	public static int handCount;
	public static ArrayList<Integer> counters;
	
	public Statistics() {
		handCount = 0;
		counters = new ArrayList<Integer>();
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
	
	public static ArrayList<Integer> getStats(){
		ArrayList<Integer> percentage = new ArrayList<Integer>();
		for (Integer count : counters) {
			percentage.add(100/handCount*count);
		}
		
		return percentage;
	}
}
