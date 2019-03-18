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
}
