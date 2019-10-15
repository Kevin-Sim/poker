package cards;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.imageio.ImageIO;

public class Card implements Comparable<Card>{
	int value;
	String suit;
	int suitValue;
	String rank;
	BufferedImage image;
	static boolean loadCards;
	static String[] suits = { "Clubs", "Diamonds", "Hearts", "Spades" };
	static String[] ranks = { "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack",
			"Queen", "King",  "Ace"};
	
	
	public Card (String suit, String rank){
		this.suit = suit;
		for(int i = 0; i < 4; i++) {
			if(suit.equals(suits[i])) {
				suitValue = i;
			}
		}
		this.rank = rank;
		int index = Arrays.asList(ranks).indexOf(rank);
		value = index + 2;		
		String filename = "./Images/";
		if(value > 10) {
			filename += rank.toLowerCase(); 
		}else { 
			filename += value;
		}
		filename += "_of_" + suit.toLowerCase() + ".png";
		if(loadCards) {
			try {
				image = ImageIO.read(new File(filename));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
	
	public void draw(Graphics2D g) {
		g.drawImage(image, 0, 0, 250, 363, null);
	}

	public static ArrayList<Card> getShuffledDeck(Random rnd) {
		loadCards = true;
		ArrayList<Card> cards = new ArrayList<>();		
		for(String suit : suits) {
			for(String rank : ranks) {
				Card card = new Card(suit, rank);
				cards.add(card);
			}
		}
		Collections.sort(cards);
		Collections.shuffle(cards, rnd);		
		return cards;
	}

	@Override
	public String toString() {
		return rank + " of " + suit;
	}

	@Override
	public int compareTo(Card o) {
		
		if(o.suitValue < this.suitValue){
			return 1;
		}
		else if(o.suitValue > this.suitValue){
			return -1;
		}
		
		if(o.value < this.value) {
			return 1;
		}else if (o.value > this.value){
			return -1;
		}
		//never happens with 1 deck
		return 0;
	}

	public static ArrayList<Card> getShuffledDeck(Random rnd, boolean b) {
		loadCards = false;
		return getShuffledDeck(rnd);
	}
}
