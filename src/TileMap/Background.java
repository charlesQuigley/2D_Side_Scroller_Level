/* *
 * Background.java
 *
 * Puts a scrolling background image into the panel.
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */
package TileMap;

import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Background {

	// Variable Declaration

	private BufferedImage bi;

	// x values
	private double x;
	private double dx;

	// y values
	private double y;
	private double dy;

	// Rate at which the background scrolls
	private double scrollSpeed;

	// Constructor
	public Background(String s, double ms) {
		try {	// Open the background image
			bi = ImageIO.read(getClass().getResourceAsStream(s));
			scrollSpeed = ms;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	// Maps the background's coordinates to the panel coords
	public void setPosition(double x, double y) {
		this.x = (x * scrollSpeed) % GamePanel.WIDTH;
		this.y = (y * scrollSpeed) % GamePanel.HEIGHT;
	}

	// Sets the rate of change for x and y
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	// Updates the background's position to the next position
	public void update() {
		x += dx;
		y += dy;
	}

	// Draws the background onto the panel
	public void draw(Graphics2D g) {
		g.drawImage(bi, (int)x, (int)y, null);
		if(x < 0) {
			g.drawImage(bi, (int)x + GamePanel.WIDTH, (int)y, null);
		}
		if(x > 0) {
			g.drawImage(bi, (int)x - GamePanel.WIDTH, (int)y, null);
		}
	}
}
