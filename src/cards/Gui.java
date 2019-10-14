package cards;


import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Desktop;

/**
 * Simple Gui that displays a single card and a pack of cards
 * 
 * @author K.Sim
 *
 */
public class Gui extends JFrame {
	
	private static final long serialVersionUID = 1L;
	Random rnd = new Random();
	ArrayList<Card> cards = Card.getShuffledDeck(rnd);
	Card currentCard = null;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 658, 612);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLUE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/**
		 * A panel to display a single card
		 */
		JPanel cardPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				if(currentCard == null) {
					return;
				}				
				super.paintComponent(g);
				currentCard.draw((Graphics2D) g);
			}
		};
		cardPanel.setBackground(Color.WHITE);
		cardPanel.setBounds(10, 10, 250, 363);
		contentPane.add(cardPanel);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cards.size() > 0) {
					currentCard = cards.remove(new Random().nextInt(cards.size()));
					if(currentCard.suit.equals("Spades") && currentCard.value == 1) {
						try {
							//easter egg
							Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=1iwC2QljLn4"));
						} catch (IOException e1) {							
							e1.printStackTrace();
						} catch (URISyntaxException e1) {							
							e1.printStackTrace();
						}
					}
					repaint();
				}else {
					currentCard = null;
					JOptionPane.showMessageDialog(Gui.this, "Empty Pack");
					repaint();
				}
			}
		});
		btnNext.setBounds(79, 403, 89, 23);
		contentPane.add(btnNext);
		
		/**
		 * A panel to display a pack of cards, rotated over 360 degrees
		 */
		JPanel packPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				if(cards == null) {
					return;
				}
				Graphics2D g2 = (Graphics2D)g;
				RenderingHints rh = new RenderingHints(
				             RenderingHints.KEY_TEXT_ANTIALIASING,
				             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
				g2.setRenderingHints(rh);
				super.paintComponent(g2);
				g2.translate(175, 175);
				g2.scale(0.35, 0.35);
				if(cards != null) {
					for(int i = 0; i < cards.size(); i++) {
						g2.rotate(Math.toRadians(360.0 / cards.size()));						
						cards.get(i).draw(g2);
					}
				}
			}
		};
		packPanel.setBounds(276, 10, 356, 363);
		contentPane.add(packPanel);
		
		JButton btnNewPack = new JButton("New Pack");
		btnNewPack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cards = Card.getShuffledDeck(rnd);
				currentCard = null;
				repaint();
			}
		});
		btnNewPack.setBounds(413, 403, 89, 23);
		contentPane.add(btnNewPack);
		
		JButton btnPokerProbability = new JButton("poker probability");
		btnPokerProbability.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int count = 0;
				int tries = 0;
				for(long l = Long.MIN_VALUE;;l++) {
//					System.out.println(l);
					tries++;
					rnd = new Random(l);
					Collections.sort(cards);
					Collections.shuffle(cards, rnd);
					ArrayList<Card> hand = new ArrayList<>();
					for(int i = 0; i < 5; i++) {
						hand.add(cards.get(i));
					}
					Collections.sort(hand);
					boolean found = true;
					String suit = null;
					int lastVal = -1;
					for(Card card : hand) {
						if(suit == null) {
							suit = card.suit;
						}
						if(!card.suit.equals(suit)) {
							found = false;
							break;
						}
						if (lastVal == -1) {
							lastVal = card.value;
							if(lastVal != 10) {
								found = false;
								break;
							}
						}else {
							if(card.value != lastVal + 1) {
								found = false;
								break;
							}else {
								lastVal = card.value;
//								System.out.println(hand);
							}
						}	
//						if(found) {
//							System.out.println(hand + "\t" + Math.abs((Long.MIN_VALUE - l)));
//						}
					}
					if(found) {						
						count++;
						System.out.println(hand + "\tseed " + l + " " + count  + " in " + tries);						
					}
				}
			}
				
		});
		btnPokerProbability.setBounds(71, 441, 97, 25);
		contentPane.add(btnPokerProbability);
	}
	static void reshuffleDeck(Random rnd, ArrayList<Card> cards) {
		Collections.sort(cards);
		
	}
}
