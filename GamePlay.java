// Brick Breaker

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Java implementation of Brick Breaker using inheritance and interfaces.
 * Alpha version 1.2.0
 */
public class GamePlay extends JComponent implements KeyListener, Runnable {
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Rectangle pad;
	
	private static final int FRAME_WIDTH = 750;
	private static final int PAD_WIDTH = 120;
	private static final int PAD_HEIGHT = 25;
	
	private static final int POS_BALL_SPEED = 12;
	private static final int NEG_BALL_SPEED = -12;

	private Ball ball;

	private boolean[] keyState = new boolean[256];

	static JFrame frame = new JFrame("Brick Breaker");
	
	private static ArrayList<Brick> bricks1 = new ArrayList<Brick>();
	private static ArrayList<Brick> bricks2 = new ArrayList<Brick>();
	private static ArrayList<Brick> bricks3 = new ArrayList<Brick>();
	private static ArrayList<Brick> bricks4 = new ArrayList<Brick>();
	private static ArrayList<Brick> bricks5 = new ArrayList<Brick>();

	static int score = 0;
	static int lives = 7;
	static int level = 1;
	
	/**
	 * Initialize the game.
	 */
	public GamePlay() {
		pad = new Rectangle(FRAME_WIDTH/2-PAD_WIDTH/2-10, screenSize.height-160, PAD_WIDTH, PAD_HEIGHT);

		ball = new Ball(360,screenSize.height - 300,28,28);
		ball.setXVelocity(POS_BALL_SPEED);
		ball.setYVelocity(NEG_BALL_SPEED);

		this.setFocusable(true);
		this.addKeyListener(this);
	}

	/**
	 * Paints the level map and initializes all ther graphics.
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.BLUE);
		g2.fill(pad);

		g2.setColor(Color.BLACK);
		g2.fill(ball);

		g2.draw(pad);
		g2.draw(ball);

		if (level == 1) {
			for (int i = 0; i < bricks1.size() ; i++) {
				Color col = bricks1.get(i).getColor();
				g2.setColor(col);
				g2.fill(bricks1.get(i));
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(5));
				g2.draw(bricks1.get(i));
			}
		} 
		else if (level == 2) {
			for (int i = 0; i < bricks2.size() ; i++) {
				Color col = bricks2.get(i).getColor();
				g2.setColor(col);
				g2.fill(bricks2.get(i));
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(5));
				g2.draw(bricks2.get(i));
			}
		} 
		else if (level == 3) {
			for (int i = 0; i < bricks3.size() ; i++) {
				Color col = bricks3.get(i).getColor();
				g2.setColor(col);
				g2.fill(bricks3.get(i));
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(5));
				g2.draw(bricks3.get(i));
			}
		} 
		else if (level == 4) {
			for (int i = 0; i < bricks4.size() ; i++) {
				Color col = bricks4.get(i).getColor();
				g2.setColor(col);
				g2.fill(bricks4.get(i));
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(5));
				g2.draw(bricks4.get(i));
			}
		}
		else if (level == 5) {
			for (int i = 0; i < bricks5.size() ; i++) {
				Color col = bricks5.get(i).getColor();
				g2.setColor(col);
				g2.fill(bricks5.get(i));
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(5));
				g2.draw(bricks5.get(i));
			}
		} 
		
		// Displays information for player to see and keep track of score
		g2.setFont(new Font("Calibri", Font.BOLD, 35));
		g2.drawString("Score: " + score, 300, 40);
		g2.drawString("Lives: " + lives, 20, 40);
	}
	
	// Methods for keyboard user input.
	@Override
	public void keyPressed(KeyEvent e) {
		keyState[e.getKeyCode()] = true;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		keyState[e.getKeyCode()] = false;
	}
	
	/**
	 * Base method of the game. Manages ball movement, brick deletion, and level progression.
	 */
	public void run() {

		boolean isPlaying = true;

		while (isPlaying) {
			if (keyState[KeyEvent.VK_RIGHT] || keyState[KeyEvent.VK_D]) {
				if (pad.x + PAD_WIDTH + 10 < FRAME_WIDTH)
					pad.setLocation(pad.x + 10,pad.y);
			} if (keyState[KeyEvent.VK_LEFT] || keyState[KeyEvent.VK_A]) {
				if (pad.x - 10 > 0)
					pad.setLocation(pad.x - 10,pad.y);
			}
			repaint();

			ball.x += ball.getXVelocity();
			ball.y += ball.getYVelocity();

			if (ball.x + ball.width >= FRAME_WIDTH || ball.x <= 0) {
				ball.setXVelocity(ball.getXVelocity() * -1);
			} if (ball.y <= 0) {
				ball.setYVelocity(ball.getYVelocity() * -1);
			} if (ball.intersects(pad)) {
				ball.setYVelocity(ball.getYVelocity() * -1);
			} if (ball.y + ball.height >= screenSize.height - 125) {
				isPlaying = false;
				lives--;
			}

			repaint();

			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// Collision detection
			if (level == 1)
				collision(bricks1);
			else if (level == 2)
				collision(bricks2);
			else if (level == 3)
				collision(bricks3);
			else if (level == 4)
				collision(bricks4);
			else if (level == 5)
				collision(bricks5);
			
			// Level progression
			if (level == 1 && bricks1.size() == 0) {
				int nextLevel = JOptionPane.showConfirmDialog(frame,"Your score is: " + score + "\nProceed to next level?","Level " + level + " complete!",JOptionPane.YES_NO_OPTION);
				if (nextLevel == JOptionPane.YES_OPTION) {
					level++;

					ball.x = 360;
					ball.y = screenSize.height - 300;
					ball.setXVelocity(POS_BALL_SPEED);
					ball.setYVelocity(NEG_BALL_SPEED);

					for (int i = 0 ; i < keyState.length ; i++)
						keyState[i] = false;

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					System.exit(0);
				}
			}
			else if(level == 2 && bricks2.size() == 0) {

				int nextLevel = JOptionPane.showConfirmDialog(frame,"Your score is: " + score + "\nProceed to next level?","Level " + level + " complete!",JOptionPane.YES_NO_OPTION);
				if (nextLevel == JOptionPane.YES_OPTION) {
					level++;

					ball.x = 360;
					ball.y = screenSize.height - 300;
					ball.setXVelocity(POS_BALL_SPEED);
					ball.setYVelocity(NEG_BALL_SPEED);

					for (int i = 0 ; i < keyState.length ; i++)
						keyState[i] = false;

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					System.exit(0);
				}
			}
			else if(level == 3 && bricks3.size() == 0) {

				int nextLevel = JOptionPane.showConfirmDialog(frame,"Your score is: " + score + "\nProceed to next level?","Level " + level + " complete!",JOptionPane.YES_NO_OPTION);
				if (nextLevel == JOptionPane.YES_OPTION) {
					level++;

					ball.x = 360;
					ball.y = screenSize.height - 300;
					ball.setXVelocity(POS_BALL_SPEED);
					ball.setYVelocity(NEG_BALL_SPEED);

					for (int i = 0 ; i < keyState.length ; i++)
						keyState[i] = false;

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					System.exit(0);
				}
			}
			else if(level == 4 && bricks4.size() == 0) {

				int nextLevel = JOptionPane.showConfirmDialog(frame,"Your score is: " + score + "\nProceed to next level?","Level " + level + " complete!",JOptionPane.YES_NO_OPTION);
				if (nextLevel == JOptionPane.YES_OPTION) {
					level++;

					ball.x = 360;
					ball.y = screenSize.height - 300;
					ball.setXVelocity(POS_BALL_SPEED);
					ball.setYVelocity(NEG_BALL_SPEED);

					for (int i = 0 ; i < keyState.length ; i++)
						keyState[i] = false;

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else {
					System.exit(0);
				}
			}
			else if(level == 5 && bricks5.size() == 0) {
				JOptionPane.showMessageDialog(frame, "You win! Thanks for playing!");
				System.exit(0);
			}

			if (lives == 0) {
				JOptionPane.showMessageDialog(frame, "Sorry, you ran out of lives! \nThanks for playing!");
				System.exit(0);
			}
		}
		
		// Provides user with an option to continue if they lose
		int pause = JOptionPane.showConfirmDialog(frame,"Continue?","Life Lost!",JOptionPane.YES_NO_OPTION);
		if (pause == JOptionPane.YES_OPTION) {
			ball.x = 360;
			ball.y = screenSize.height - 300;
			ball.setXVelocity(POS_BALL_SPEED);
			ball.setYVelocity(NEG_BALL_SPEED);

			for (int i = 0 ; i < keyState.length ; i++)
				keyState[i] = false;

			run();
		} else {
			System.exit(0);
		}
	}
	
	/**
	 * Method to recognize and handle collisions between the ball and the bricks.
	 */
	public void collision(ArrayList<Brick> bricks) {
		for (int i = 0; i < bricks.size(); i++) {
			if (ball.intersects(bricks.get(i)) && ball.y <= bricks.get(i).y - (bricks.get(i).getHeight()/2)) { // Hits from below
				ball.setYVelocity(ball.getYVelocity() * -1);
				ball.setXVelocity(ball.getXVelocity() - 0.005);
				score++;
				bricks.get(i).hit();
				if (bricks.get(i).getLives() == 0) {
					bricks.remove(i);
				}
			}
			else if (ball.intersects(bricks.get(i)) && ball.y >= bricks.get(i).y + (bricks.get(i).getHeight()/2)) { // Hits from above
				ball.setYVelocity(ball.getYVelocity() * -1);
				score++;
				bricks.get(i).hit();
				if (bricks.get(i).getLives() == 0) {
					bricks.remove(i);
				}
			}
			else if (ball.intersects(bricks.get(i)) && ball.x < bricks.get(i).x) { // Hits on left side
				ball.setXVelocity(ball.getXVelocity() * -1);
				score++;
				bricks.get(i).hit();
				if (bricks.get(i).getLives() == 0) {
					bricks.remove(i);
				}
			}
			else if (ball.intersects(bricks.get(i)) && ball.x > bricks.get(i).x) { // Hits on right side
				ball.setXVelocity(ball.getXVelocity() * -1);
				score++;
				bricks.get(i).hit();
				if (bricks.get(i).getLives() == 0) {
					bricks.remove(i);
				}
			}
		}
	}

	/**
	 * Game panel and level framework.
	 */
	public static void main(String[] args) {
		frame.setSize(FRAME_WIDTH, (screenSize.height - 80));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		GamePlay game = new GamePlay();

		frame.add(game, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setResizable(false);

		// Bricks for Level 1

		for (int i = 0; i < 5; i++) {
			Red_Brick r = new Red_Brick((70 + 40 * i), (50 + 60 * i), 100, 50);
			bricks1.add(r);
		}

		for (int i = 0; i < 5; i++) {
			Red_Brick r = new Red_Brick(390 + (40 * i), 290 - (60 * i), 100, 50);
			bricks1.add(r);
		}

		for (int i = 0; i < 1; i++) {
			Red_Brick r = new Red_Brick(310, 350, 100, 50);
			bricks1.add(r);
		}

		for (int i = 0; i < 3; i++) {
			Orange_Brick oran = new Orange_Brick(310, 108 + 60 * i, 100, 50);
			bricks1.add(oran);
		}

		// Bricks for Level 2

		for (int j = 0; j < 5; j++) {
			Red_Brick r = new Red_Brick(110 + 100 * j, 50, 100, 50);
			bricks2.add(r);
		}

		for (int j = 0; j < 4; j++) {
			Orange_Brick oran = new Orange_Brick(160 + 100 * j, 100, 100, 50);
			bricks2.add(oran);
		}

		for (int j = 0; j < 3; j++) {
			Green_Brick green = new Green_Brick(310, 200 + (70 * j), 100, 50);
			bricks2.add(green);
		}

		for (int j = 0; j < 2; j++) {
			Green_Brick green = new Green_Brick(190 + 240 * j, 270, 100, 50);
			bricks2.add(green);
		}

		for (int j = 0; j < 5; j++) {
			Red_Brick r = new Red_Brick(110 + 100 * j, 495, 100, 50);
			bricks2.add(r);
		}

		for (int j = 0; j < 4; j++) {
			Orange_Brick oran = new Orange_Brick(160 + 100 * j, 445, 100, 50);
			bricks2.add(oran);
		}

		// Bricks for Level 3

		for (int i = 0; i < 3; i++) {
			Red_Brick r = new Red_Brick(110, 50 + 50 * i, 100, 50);
			bricks3.add(r);
		}

		for (int i = 0; i < 3; i++) {
			Orange_Brick oran = new Orange_Brick(315, 100 + 50 * i, 100, 50);
			bricks3.add(oran);
		}

		for (int i = 0; i < 3; i++) {
			Green_Brick green = new Green_Brick(520, 150 + 50 * i, 100, 50);
			bricks3.add(green);
		}

		for (int i = 0; i < 3; i++) {
			Red_Brick r = new Red_Brick(110, 450 + 50 * i, 100, 50);
			bricks3.add(r);
		}

		for (int i = 0; i < 3; i++) {
			Orange_Brick oran = new Orange_Brick(315, 400 + 50 * i, 100, 50);
			bricks3.add(oran);
		}

		for (int i = 0; i < 3; i++) {
			Green_Brick green = new Green_Brick(520, 350 + 50 * i, 100, 50);
			bricks3.add(green);
		}

		for (int i = 0; i < 1; i++) {
			Red_Brick r = new Red_Brick(315, 300, 100, 50);
			bricks3.add(r);
		}

		for (int i = 0; i < 1; i++) {
			Orange_Brick oran = new Orange_Brick(110, 300, 100, 50);
			bricks3.add(oran);
		}

		// Bricks for Level 4

		for (int i = 0; i < 1; i++) {
			Gray_Brick gray = new Gray_Brick(310, 400, 100, 50);
			bricks4.add(gray);
		}

		for (int i = 0; i < 2; i++) {
			Gray_Brick gray = new Gray_Brick(310 + 120 * i, 330 + 70 * i, 100, 50);
			bricks4.add(gray);
		}

		for (int i = 0; i < 2; i++) {
			Gray_Brick gray = new Gray_Brick(310 - 120 * i, 470 - 70 * i, 100, 50);
			bricks4.add(gray);
		}

		for (int i = 0; i < 7; i++) {
			Green_Brick green = new Green_Brick(65, 610 - 70 * i, 100, 50);
			bricks4.add(green);
		}

		for (int i = 0; i < 7; i++) {
			Green_Brick green = new Green_Brick(560, 610 - 70 * i, 100, 50);
			bricks4.add(green);
		}

		// Bricks for Level 5

		for (int i = 0; i < 7; i++) {
			Gray_Brick gray = new Gray_Brick(13 + 100 * i, 600, 100, 50);
			bricks5.add(gray);
		}
		
		for (int i = 0; i < 6; i++) {
			Orange_Brick orange = new Orange_Brick(13, 530 - 70 * i, 100, 50);
			bricks5.add(orange);
		}
		
		for (int i = 0; i < 6; i++) {
			Green_Brick green = new Green_Brick(313, 530 - 70 * i, 100, 50);
			bricks5.add(green);
		}
		
		for (int i = 0; i < 6; i++) {
			Orange_Brick orange = new Orange_Brick(613, 530 - 70 * i, 100, 50);
			bricks5.add(orange);
		}
		
		for (int i = 0; i < 7; i++) {
			Red_Brick red = new Red_Brick(13 + 100 * i, 110, 100, 50);
			bricks5.add(red);
		}
		
		// User input to play/start game
		int startGame = JOptionPane.showConfirmDialog(frame, "Select Yes to play", "Brick Breaker", JOptionPane.YES_NO_OPTION);
		if (startGame == JOptionPane.YES_OPTION) {
			Thread t = new Thread(game);
			t.start();
		} else {
			System.exit(0);
		}

	}
}
