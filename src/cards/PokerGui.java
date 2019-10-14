package cards;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JTextField;

public class PokerGui extends JFrame implements Observer{

	private JPanel contentPane;
	protected ArrayList<Card> deck;
	protected ArrayList<CardPanel> cardPanels;
	ProbabilityChecker probabilityChecker = null;
	Random rnd = null;
	private JTextPane msgTextPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PokerGui frame = new PokerGui();
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
	public PokerGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 781);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		rnd = new Random();
		deck = Card.getShuffledDeck(rnd);
		cardPanels = new ArrayList<>();
		probabilityChecker = new ProbabilityChecker(deck, rnd);
		probabilityChecker.addObserver(PokerGui.this);
		for(int i = 0; i < 5; i++) {
			CardPanel cardPanel = new CardPanel((null));
			//cardPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(192, 192, 192), null));
			cardPanel.setBounds(20 + (i * 260), 20, 250, 400);
			contentPane.add(cardPanel);
			cardPanels.add(cardPanel);
		}
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				Thread t = new Thread(probabilityChecker);
				t.start();								
			}
		});
		btnNewButton.setBounds(118, 460, 97, 25);
		contentPane.add(btnNewButton);
		
		msgTextPane = new JTextPane();
		msgTextPane.setFont(new Font("Tahoma", Font.BOLD, 16));
		msgTextPane.setBackground(Color.BLACK);
		msgTextPane.setForeground(Color.GREEN);
		msgTextPane.setBounds(377, 425, 742, 296);
		contentPane.add(msgTextPane);
		
		btnNewButton_1 = new JButton("New button");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				probabilityChecker.running = false;
			}
		});
		btnNewButton_1.setBounds(118, 498, 97, 25);
		contentPane.add(btnNewButton_1);
		
		cashTextField = new JTextField();
		cashTextField.setFont(new Font("Segoe Print", Font.BOLD, 30));
		cashTextField.setBackground(Color.BLACK);
		cashTextField.setForeground(Color.YELLOW);
		cashTextField.setBounds(40, 536, 281, 64);
		contentPane.add(cashTextField);
		cashTextField.setColumns(10);
	}

	public static long lastUpdate = System.currentTimeMillis();
	private JButton btnNewButton_1;
	private JTextField cashTextField;
	@Override
	public void update(Observable o, Object arg) {
		long time = System.currentTimeMillis();
		if(time - lastUpdate > 100) {
			lastUpdate = time;
		}else {
			return;
		}
		for(int i = 0; i < 5; i++) {
			cardPanels.get(i).setCard(deck.get(i));
		}
		getMsgTextPane().setText(probabilityChecker.msg);
		DecimalFormat formatter = new DecimalFormat("#,###.00");
		getCashTextField().setText("£" + formatter.format(probabilityChecker.cash));
	}
	
	public JTextPane getMsgTextPane() {
		return msgTextPane;
	}
	public JTextField getCashTextField() {
		return cashTextField;
	}
}
