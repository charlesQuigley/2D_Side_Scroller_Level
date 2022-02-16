package Entities.Enemies;

import Entities.*;
import TileMap.TileMap;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

/**
 * Objects of this Spider Class represent the only enemies in the game besides the boss.
 * This class holds the spider sprite images. This class also has the means of moving the specific spider around
 * the level (update() occurs here for spiders). This class extends Enemy, and so also has
 * all methods available to the Enemy class.
 */
public class Spider extends Enemy{
	
	private BufferedImage[] spiderSprites;
	
	public Spider(TileMap level) {
		super(level);

		spriteBoxWidth = 30; //Width of the image's sprite box
		spriteBoxHeight = 30; //Height of the image's sprite box
		spriteWidth = 20; //Width of the actual sprite's image.
		spriteHeight = 20; //Height of the actual sprite's image.

		//Get Spider Sprites
		BufferedImage spritesheet =
				SpriteSheetLoader.SpriteSheetLoad(new File("Resources/Sprites/Enemies/redSpider.png"));
		spiderSprites = new BufferedImage[10];

		//Each individual sprite within the Spider's sprite sheet goes in its own index within the array.
		for(int i = 0; i < spiderSprites.length; i++) {
			assert spritesheet != null;
			spiderSprites[i] = spritesheet.getSubimage(i*spriteBoxWidth, spriteBoxHeight*3, spriteBoxWidth, spriteBoxHeight);
		}

		animation = new Animation();
		animation.setFrames(spiderSprites);
		animation.setDelay(300);


		fullHealth = 1; //Spider has 1 health maximum
		health = 1; //Spider has 1 health


		atk = 1; //Spider does 1 damage to the player.


		speed = 0.3; //Spider's movement speed in the level.
		maxSpeed = 0.3; //Spider's maximum speed.
		fallSpeed = 0.2; //how fast the spider falls if no collision
		maxFallSpeed = 10.0; //maximum speed the spider falls if no collision.


		right = true; //Spider moves to the right
		face = "Right"; //Spider faces right.
	}


	/**
	 * This method handles the projected movement of the Spider on the screen and helps determine
	 * whether or not the boss will collide with something.
	 */
	private void getNextPosition() {
		if(left) {
			edgeX -= speed;
			if(edgeX < -maxSpeed) {
				edgeX = -maxSpeed;
			}
		}
		else if(right) {
			edgeX += speed;
			if(edgeX > maxSpeed) {
				edgeX = maxSpeed;
			}
		}
		
		if(falling) {
			edgeY += fallSpeed;
		}
	}


	/**
	 * This method sets the Spider's new position within the level, and
	 * cycles through 1 of the spider's sprite images to make it look like he's moving
	 * everytime this method is called.
	 */
	public void update() {
		getNextPosition();
		checkTileMapCollision();
		setPosition(nextX, nextY); //Spider moves to the next position after we have done collision detection.

		
		//When the spider collides with something on the right, it goes left.
		if(right && edgeX == 0) {
			face = "Left";
			left = true;
			right = false;

		}
		else if(left && edgeX == 0) { //Vice-versa if the spider collides with something on the left.
			face = "Right";
			left = false;
			right = true;

		}

		//Get the invincibility Frames
		super.update();
		
		//As the spider moves, this is what shows the spider "walking"
		animation.update();
	}

	/**
	 *This method draws the spider's current sprite image on the map using its x and y coordinates
	 * in conjunction with the positioning of the level's tiles.
	 * @param g - Graphics object used to display images.
	 */
	public void draw(Graphics2D g) {
		setMapPosition();
		super.draw(g);
	}
	
	
	
	
}
