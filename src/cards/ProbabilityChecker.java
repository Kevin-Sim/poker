package cards;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class ProbabilityChecker extends Observable implements Runnable, Observer{

	int id = 0;
	boolean running = false;
	long cash = -1;
	long seed = -1;
	Random rnd = null;
	ArrayList<Card> deck = null;
	String msg;
	
	public static void main(String[] args) {
		long seed = System.currentTimeMillis();
		if(args  != null && args.length > 0) {
			seed = Long.parseLong(args[0]);
		}
		Random rnd = new Random(seed);
		ArrayList<Card> deck = Card.getShuffledDeck(rnd, false);		
		ProbabilityChecker probabilityChecker = new ProbabilityChecker(deck, rnd);
		probabilityChecker.seed = seed;
		probabilityChecker.id = (int) (seed);
		probabilityChecker.addObserver(probabilityChecker);		
		Thread t = new Thread(probabilityChecker);
		t.start();
	}
	
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

	long lastWrite = System.currentTimeMillis();
	@Override
	public void update(Observable o, Object arg) {
		long time = System.currentTimeMillis();
		
		if(time - lastWrite < 10000) {			
			return;
		}
		lastWrite = time;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("" + id + ".txt")));
			writer.write(msg);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(msg);
		
	}

}
