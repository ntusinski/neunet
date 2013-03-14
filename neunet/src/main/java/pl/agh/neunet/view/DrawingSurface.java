package pl.agh.neunet.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawingSurface extends JPanel {
	private static final long serialVersionUID = 4932285533300191176L;

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(new Color(239, 233, 221));
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
