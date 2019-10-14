package cards;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class CardPanel extends JPanel {

	private Card card;

	/**
	 * Create the panel.
	 */
	public CardPanel(Card card) {
		this.card = card;
		setBackground(Color.BLACK);
		
	}
	
	public void setCard(Card card) {
		this.card = card;
		repaint();
	}
	@Override
	public void paint(Graphics g) {
		if(card == null) {
			return;
		}
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		RenderingHints rh = new RenderingHints(
		             RenderingHints.KEY_TEXT_ANTIALIASING,
		             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHints(rh);
		super.paintComponent(g2);		
		//g2.scale(0.5, 0.5);
		card.draw(g2);
	}

}
