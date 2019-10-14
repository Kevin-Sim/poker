package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;

import cards.PokerChecker.Hand;

public class ProbabilityChecker extends Observable implements Runnable{

	boolean running = false;
	long cash = -1;
	long seed = System.currentTimeMillis();
	Random rnd = null;
	ArrayList<Card> deck = null;
	String msg;
	
	public ProbabilityChecker(ArrayList<Card> deck, Random rnd) {
		this.deck = deck;
		this.rnd = rnd;
	}
	
	@Override
	public void run() {
		cash = Long.MAX_VALUE / 2;
		int stake = 1;
		running = true;
		while(running) {	
			cash -= stake;
			Collections.shuffle(deck, rnd);
			ArrayList<Card> hand = new ArrayList<>();
			for(int i = 0; i < 5; i++) {
				hand.add(deck.get(i));
			}
			PokerChecker.check(hand);	
			int win = stake * PokerChecker.reward();
			cash += win; 
			msg = PokerChecker.stats();
			if(cash <= 0) {
				running = false;
				PokerGui.lastUpdate = 0;
			}
//			if(PokerChecker.lastHand == Hand.CONSECUTIVE_ROYAL_FLUSH) {
//				running = false;
//				PokerGui.lastUpdate = 0;
//			}
			setChanged();
			notifyObservers();
		}
	}

}
