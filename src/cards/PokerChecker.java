package cards;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class PokerChecker {

	enum Hand {
		CONSECUTIVE_ROYAL_FLUSH, ROYAl_FLUSH, STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE, FLUSH, STRAIGHT, THREE_OF_A_KIND, TWO_PAIRS, ONE_PAIR, HIGH_CARD
	}

	static long consecutiveRoyalFlush = 0;
	static long royalFlush = 0;
	static long straightFlush = 0;
	static long fourOfAKind = 0;
	static long fullHouse = 0;
	static long flush = 0;
	static long straight = 0;
	static long threeOfAKind = 0;
	static long twoPairs = 0;
	static long onePair = 0;
	static long highCard = 0;
	static long tries = 0;
	static Hand lastHand;
	private static long win = 0;
	private static long loss = 0;

	static void check(ArrayList<Card> hand) {
		tries++;
		if (consecutiveRoyalFlush(hand)) {
			return;
		}
		if (royalFlush(hand)) {
			return;
		}
		if (straightFlush(hand)) {
			return;
		}
		if (fourOfAKind(hand)) {
			return;
		}
		if (fullHouse(hand)) {
			return;
		}
		if (flush(hand)) {
			return;
		}
		if (straight(hand)) {
			return;
		}
		if (threeOfAKind(hand)) {
			return;
		}
		if (twoPairs(hand)) {
			return;
		}
		if (onePair(hand)) {
			return;
		}
		highCard++;
		lastHand = PokerChecker.Hand.HIGH_CARD;
	}

	private static boolean consecutiveRoyalFlush(ArrayList<Card> hand) {
		boolean found = true;
		String suit = null;
		int lastVal = -1;
		for (Card card : hand) {
			if (suit == null) {
				suit = card.suit;
			}
			if (!card.suit.equals(suit)) {
				found = false;
				break;
			}
			if (lastVal == -1) {
				lastVal = card.value;
				if (lastVal != 10) {
					found = false;
					break;
				}
			} else {
				if (card.value != lastVal + 1) {
					found = false;
					break;
				} else {
					lastVal = card.value;

				}
			}
		}
		if (found) {
			consecutiveRoyalFlush++;
			lastHand = PokerChecker.Hand.CONSECUTIVE_ROYAL_FLUSH;
		}
		return found;

	}

	private static boolean royalFlush(ArrayList<Card> hand) {
		Collections.sort(hand);
		boolean found = true;
		String suit = null;
		int lastVal = -1;
		for (Card card : hand) {
			if (suit == null) {
				suit = card.suit;
			}
			if (!card.suit.equals(suit)) {
				found = false;
				break;
			}
			if (lastVal == -1) {
				lastVal = card.value;
				if (lastVal != 10) {
					found = false;
					break;
				}
			} else {
				if (card.value != lastVal + 1) {
					found = false;
					break;
				} else {
					lastVal = card.value;

				}
			}
		}
		if (found) {
			royalFlush++;
			lastHand = PokerChecker.Hand.ROYAl_FLUSH;
		}
		return found;
	}

	private static boolean straightFlush(ArrayList<Card> hand) {
		Collections.sort(hand);
		boolean found = true;
		String suit = null;
		int lastVal = -1;
		for (Card card : hand) {
			if (suit == null) {
				suit = card.suit;
			}
			if (!card.suit.equals(suit)) {
				found = false;
				break;
			}
			if (lastVal == -1) {
				lastVal = card.value;
			} else {
				if (card.value != lastVal + 1) {
					found = false;
					break;
				} else {
					lastVal = card.value;

				}
			}
		}

		if (found) {
			straightFlush++;
			lastHand = PokerChecker.Hand.STRAIGHT_FLUSH;
			return found;
		}
		found = true;
		lastVal = -1;
		suit = null;
		// ace low straight flush
		for (Card card : hand) {
			if (!card.suit.equals(hand.get(0).suit)) {
				found = false;
				break;
			}
		}
		if (found) {
			if (hand.get(0).value != 2) {
				found = false;
			}
			if (hand.get(1).value != 3) {
				found = false;
			}
			if (hand.get(2).value != 4) {
				found = false;
			}
			if (hand.get(3).value != 5) {
				found = false;
			}
			if (hand.get(4).value != 14) {
				found = false;
			}
		}

		if (found) {
			straightFlush++;
			lastHand = PokerChecker.Hand.STRAIGHT_FLUSH;
		}
		return found;
	}

	private static boolean fourOfAKind(ArrayList<Card> hand) {
		HashMap<Integer, Integer> counts = new HashMap<>();
		for (int i = 2; i < 15; i++) {
			counts.put(i, 0);
		}
		for (Card card : hand) {
			int c = counts.get(card.value);
			c++;
			counts.put(card.value, c);
		}
		for (int v : counts.values()) {
			if (v == 4) {
				fourOfAKind++;
				lastHand = PokerChecker.Hand.FOUR_OF_A_KIND;
				return true;
			}
		}
		return false;
	}

	private static boolean fullHouse(ArrayList<Card> hand) {
		Collections.sort(hand, new Comparator<Card>() {

			@Override
			public int compare(Card c1, Card c2) {
				if (c1.value < c2.value) {
					return -1;
				} else if (c1.value > c2.value) {
					return 1;
				}
				return 0;
			}
		});
		if (hand.get(0).value == hand.get(1).value && hand.get(0).value == hand.get(2).value) {
			if (hand.get(3).value != hand.get(4).value) {
				return false;
			}
			fullHouse++;
			lastHand = Hand.FULL_HOUSE;
			return true;
		}
		if (hand.get(2).value == hand.get(3).value && hand.get(2).value == hand.get(4).value) {
			if (hand.get(0).value != hand.get(1).value) {
				return false;
			}
			fullHouse++;
			lastHand = Hand.FULL_HOUSE;
			return true;
		}
		return false;
	}

	private static boolean flush(ArrayList<Card> hand) {
		String suit = null;
		for (Card card : hand) {
			if (suit == null) {
				suit = card.suit;
			} else if (!card.suit.equals(suit)) {
				return false;
			}
		}
		flush++;
		lastHand = PokerChecker.Hand.FLUSH;
		return true;
	}

	private static boolean straight(ArrayList<Card> hand) {
		Collections.sort(hand, new Comparator<Card>() {

			@Override
			public int compare(Card c1, Card c2) {
				if (c1.value < c2.value) {
					return -1;
				} else if (c1.value > c2.value) {
					return 1;
				}
				return 0;
			}
		});
		boolean found = true;
		int lastVal = -1;
		for (Card card : hand) {
			if (lastVal == -1) {
				lastVal = card.value;
			} else {
				if (card.value != lastVal + 1) {
					found = false;
					break;
				} else {
					lastVal = card.value;
				}
			}
		}

		if (found) {
			straight++;
			lastHand = PokerChecker.Hand.STRAIGHT;
			return found;
		}
		found = true;
		lastVal = -1;
		// ace low flush

		if (hand.get(0).value != 2) {
			found = false;
		}
		if (hand.get(1).value != 3) {
			found = false;
		}
		if (hand.get(2).value != 4) {
			found = false;
		}
		if (hand.get(3).value != 5) {
			found = false;
		}
		if (hand.get(4).value != 14) {
			found = false;
		}

		if (found) {
			straight++;
			lastHand = PokerChecker.Hand.STRAIGHT;
		}
		return found;
	}

	private static boolean threeOfAKind(ArrayList<Card> hand) {
		HashMap<Integer, Integer> counts = new HashMap<>();
		for (int i = 2; i < 15; i++) {
			counts.put(i, 0);
		}
		for (Card card : hand) {
			int c = counts.get(card.value);
			c++;
			counts.put(card.value, c);
		}
		for (int v : counts.values()) {
			if (v == 3) {
				threeOfAKind++;
				lastHand = PokerChecker.Hand.THREE_OF_A_KIND;
				return true;
			}
		}
		return false;
	}

	private static boolean twoPairs(ArrayList<Card> hand) {
		HashMap<Integer, Integer> counts = new HashMap<>();
		for (int i = 2; i < 15; i++) {
			counts.put(i, 0);
		}
		for (Card card : hand) {
			int c = counts.get(card.value);
			c++;
			counts.put(card.value, c);
		}
		int pairs = 0;
		for (int v : counts.values()) {
			if (v == 2) {
				pairs++;
			}
		}
		if (pairs == 2) {
			twoPairs++;
			lastHand = Hand.TWO_PAIRS;
			return true;
		}
		return false;
	}

	private static boolean onePair(ArrayList<Card> hand) {
		HashMap<Integer, Integer> counts = new HashMap<>();
		for (int i = 2; i < 15; i++) {
			counts.put(i, 0);
		}
		for (Card card : hand) {
			int c = counts.get(card.value);
			c++;
			counts.put(card.value, c);
		}
		for (int v : counts.values()) {
			if (v == 2) {
				onePair++;
				lastHand = Hand.ONE_PAIR;
				return true;
			}
		}
		return false;
	}

	static String stats() {
		DecimalFormat formatter = new DecimalFormat("0.0000000000");
		DecimalFormat formatter2 = new DecimalFormat("#,###");
		double prob = -1;
		int oneIn = -1;
		if (consecutiveRoyalFlush > 0) {
			prob = consecutiveRoyalFlush / new Double(tries);
			oneIn = (int) (tries / consecutiveRoyalFlush);
		}
		String str = "Consec Royal Flush\t" + consecutiveRoyalFlush + "\t\tProbability " + formatter.format(prob)
				+ " or 1 in " + formatter2.format(oneIn) + "\r\n";

		prob = -1;

		if (royalFlush > 0) {
			prob = royalFlush / new Double(tries);
			oneIn = (int) (tries / royalFlush);
		}
		str += "Royal Flush\t\t" + royalFlush + "\t\tProbability " + formatter.format(prob) + " or 1 in "
				+ formatter2.format(oneIn) + "\r\n";
		prob = -1;
		if (straightFlush > 0) {
			prob = straightFlush / new Double(tries);
			;
			oneIn = (int) (tries / straightFlush);
		}
		str += "Straight Flush\t\t" + straightFlush + "\t\tProbability " + formatter.format(prob) + " or 1 in "
				+ formatter2.format(oneIn) + "\r\n";
		prob = -1;
		if (fourOfAKind > 0) {
			prob = fourOfAKind / new Double(tries);
			oneIn = (int) (tries / fourOfAKind);
		}
		str += "Four of a Kind\t\t" + fourOfAKind + "\t\tProbability " + formatter.format(prob) + " or 1 in "
				+ formatter2.format(oneIn) + "\r\n";
		prob = -1;
		if (fullHouse > 0) {
			prob = fullHouse / new Double(tries);
			oneIn = (int) (tries / fullHouse);
		}
		str += "Full House\t\t" + fullHouse + "\t\tProbability " + formatter.format(prob) + " or 1 in "
				+ formatter2.format(oneIn) + "\r\n";
		prob = -1;
		if (flush > 0) {
			prob = flush / new Double(tries);
			oneIn = (int) (tries / flush);
		}
		str += "Flush\t\t\t" + flush + "\t\tProbability " + formatter.format(prob) + " or 1 in "
				+ formatter2.format(oneIn) + "\r\n";
		prob = -1;
		if (straight > 0) {
			prob = straight / new Double(tries);
			oneIn = (int) (tries / straight);
		}
		str += "Straight\t\t\t" + straight + "\t\tProbability " + formatter.format(prob) + " or 1 in "
				+ formatter2.format(oneIn) + "\r\n";
		prob = -1;
		if (threeOfAKind > 0) {
			prob = threeOfAKind / new Double(tries);
			oneIn = (int) (tries / threeOfAKind);
		}
		str += "Three of a Kind\t\t" + threeOfAKind + "\t\tProbability " + formatter.format(prob) + " or 1 in "
				+ formatter2.format(oneIn) + "\r\n";
		prob = -1;
		if (twoPairs > 0) {
			prob = twoPairs / new Double(tries);
			oneIn = (int) (tries / twoPairs);
		}
		str += "Two Pairs\t\t" + twoPairs + "\t\tProbability " + formatter.format(prob) + " or 1 in "
				+ formatter2.format(oneIn) + "\r\n";
		prob = -1;
		if (onePair > 0) {
			prob = onePair / new Double(tries);
			oneIn = (int) (tries / onePair);
		}
		str += "One Pair\t\t\t" + onePair + "\t\tProbability " + formatter.format(prob) + " or 1 in "
				+ formatter2.format(oneIn) + "\r\n";
		prob = -1;
		double oneInDouble = -1;
		if (highCard > 0) {
			prob = highCard / new Double(tries);
			;
			oneInDouble = new Double(tries) / highCard;
		}
		formatter = new DecimalFormat("0.00000");
		str += "High Card\t\t" + highCard + "\t\tProbability " + formatter.format(prob) + " or 1 in " + formatter.format(oneInDouble)
				+ "\r\n";

		str += "Tries\t\t\t" + formatter2.format(tries) + "\r\n";
		str += "Wins\t\t\t" + formatter2.format(win) + "\r\n";
		str += "Loss\t\t\t" + formatter2.format(loss) + "\r\n";
		str += "last Hand\t\t" + lastHand + "\r\n";
		return str;
	}

	static int reward() {
		int reward = 0;
		switch (lastHand) {
		case CONSECUTIVE_ROYAL_FLUSH:
			reward = 500000;
			break;
		case ROYAl_FLUSH:
			reward = 50000;
			break;
		case STRAIGHT_FLUSH:
			reward = 5000;
			break;
		case FOUR_OF_A_KIND:
			reward = 500;
			break;
		case FULL_HOUSE:
			reward = 20;
			break;
		case FLUSH:
			reward = 10;
			break;
		case STRAIGHT:
			reward = 5;
			break;
		case THREE_OF_A_KIND:
			reward = 3;
			break;
		case TWO_PAIRS:
			reward = 2;
			break;
		case ONE_PAIR:
			reward = 1;
			break;

		default:
			break;
		}
		if (reward > 0) {
			win++;
		} else {
			loss++;
		}
		return reward;
	}
}
