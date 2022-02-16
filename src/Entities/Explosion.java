/* *
 * Explosion.java
 *
 * Handles all the events needed by the explosion class. This
 * includes the drawing, animation, and removal of the sprite itself.
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */
package Entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


public class Explosion {

	private int x;
	private int y;
	private int xmap;
	private int ymap;

	private int width;
	private int height;

	private Animation animation;
	private BufferedImage[] sprites;

	private boolean remove;

	/*
	 * This constructor method initializes the explosion's
	 * animation and size
	 * @param x - Integer value of the explosion's x coord
	 * @param y - Integer value of the explosion's y coord
	 * @return nothing
	 */
	public Explosion(int x, int y) {
		this.x = x;
		this.y = y;

		width = 30;
		height = 30;

		//Sets the asset for the explosion itself.
		try {
			BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/explosion.gif"));
			sprites = new BufferedImage[6];

			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(i*width, 0, width, height);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		animation = new Animation();
		animation.setFrames(sprites);
		animation.setDelay(70);
	}

	/*
	 * This method updates the explosion animation
	 * @param args unused
	 * @return nothing
	 */
	public void update() {
		animation.update();
		if(animation.hasPlayedOnce()) {
			remove = true;
		}
	}

	//called when the explosion finishes it's animation
	/*
	 * This method sets boolean remove to true to remove
	 * the explosion
	 * @param args unused
	 * @return remove - Boolean
	 */
	public boolean shouldRemove() { return remove; }

	/*
	 * This method determines the x and y map coords of
	 * the explosion object
	 * @param x - Integer value of the explosion's x coord
	 * @param y - Integer value of the explosion's y coord
	 * @return nothing
	 */
	public void setMapPosition(int x, int y) {
		xmap = x;
		ymap = y;
	}

	//draws the explosion at a point on the map
	/*
	 * This method draws the explosion animation
	 * @param g - Graphics2D object used to draw the sprites
	 * @return nothing
	 */
	public void draw(Graphics2D g) {
		g.drawImage(animation.getImage(), x + xmap - width / 2, y + ymap - height / 2, null);
	}
}
