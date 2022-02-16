package Main;

/* *
 * GamePanel.java
 *
 * Updates the current image in the frame and reads
 * key presses.
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import GameState.GameStateManager;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener{

	// Panel dimensions
	public static final int WIDTH = 320;
	public static final int HEIGHT = 240;
	public static final int SCALE = 2;

	// Game thread
	private Thread game;
	private boolean start;

	// Time, in ms, the panel waits before updating
	// 60 frames per second = ~16 ms per frame
	private int frameWait = 16;
	
	// Panel background
	private BufferedImage image;
	private Graphics2D g;
	
	// GameStateManager object to update the
	// panel's image
	private GameStateManager gsm;

	// Constructor
	public GamePanel() {

		super();

		// Start a new thread
		if(game == null) {
			game = new Thread(this);
			addKeyListener(this);
			game.start();
		}

		// Set panel dimensions
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		// Get focus for keyPresses
		setFocusable(true);
		requestFocus();
	}

	// Runnable method
	public void run() {

		// Initialize the panel
		init();

		// Frame Rate variables
		long frameStart;
		long wait;

		// Loop to update panel image
		// Loop until system exits
		while(start) {

			// Get frame start time
			frameStart = System.currentTimeMillis();

			// Update the frame
			update();
			draw();
			drawToScreen();

			// Wait before updating again
			wait = frameWait - (System.currentTimeMillis() - frameStart) % System.currentTimeMillis();
			if(wait < 0) {wait = 1;}
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	// Initialize the panel image variables
	private void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		gsm = new GameStateManager(this);

		start = true;
	}

	// Update the panel image
	private void update() {
		gsm.update();
	}

	// Draw the new image into the panel
	private void draw() {
		gsm.draw(g);
	}

	// Draw the new image into the frame
	// to make it visible
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		g2.dispose();
	}

	// KeyListener implementations
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}
}
