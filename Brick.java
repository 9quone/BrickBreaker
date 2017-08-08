import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Brick extends Rectangle {
	protected int lives;
	
	private Color[] colors = {Color.RED, Color.ORANGE, Color.GREEN, Color.GRAY, Color.GRAY};
	
	public Brick(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public Color getColor() {
		return colors[lives-1];
	}
	
	public void hit() {
		lives--;
	}
	
	public int getLives() {
		return lives;
	}
}