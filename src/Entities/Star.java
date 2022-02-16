/* *
 * Star.java
 *
 * Draws and updates a star projectile
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */
package Entities;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import TileMap.TileMap;

/**
 * The Star Class
 * This class is used to create the projectiles of this game.
 * Since it extends Entity, this class can utilize all of Entity's methods.
 */
public class Star extends Entity {
	
	protected boolean landed; //If the star collided with something
	private BufferedImage[] starSprites; //Holds the star images
	private BufferedImage[] landedSprites; //Holds the star images for when the star collides with something.
	protected boolean delete; //Used for star objects in Star Array Lists

	/*
	 * This constructor method sets creates the star object
	 * and loads its sprite sheet
	 * @param level - TileMap object the star is drawn on
	 * @param right - Boolean determining which way it's facing
	 * @return nothing
	 */
	public Star(TileMap level, boolean right) {
		super(level);

		//Star sprite dimensions.
		spriteBoxWidth = 30;
		spriteBoxHeight = 30;
		spriteWidth = 14;
		spriteHeight = 14;

		//Direction the star is facing.
		face = "Right";

		//Determine star speed to the
		//right or left
		speed = 3.8;
		if(right)
		{
			edgeX = speed;
		}
		else
		{
			edgeX = -speed;
		}

		//Load star sprite sheet
		BufferedImage starSheet =
				SpriteSheetLoader.SpriteSheetLoad(new File("Resources/Sprites/Player/starAttack.png"));
				starSprites = new BufferedImage[3];

		//Read sprite sheet for moving stars
		for(int i = 0; i <= starSprites.length -1; i++)
		{
			starSprites[i] = starSheet.getSubimage(i * spriteBoxWidth, 0, spriteBoxWidth, spriteBoxHeight);
		}

		//Read sprite sheet for stopped stars
		landedSprites = new BufferedImage[2];
		landedSprites[0] = starSheet.getSubimage(3*spriteBoxWidth, 0, spriteBoxWidth, spriteBoxHeight);
		landedSprites[1] = starSheet.getSubimage(4*spriteBoxWidth, 0, spriteBoxWidth, spriteBoxHeight);

		//Get the star sprites ready for a sprite cycle.
		animation = new Animation();
		animation.setFrames(starSprites);
		animation.setDelay(100);
		}


	/*
	 * This method sets the star's new position within the level, and
	 * cycles through 1 of the stars's sprite images to make it look
	 * like it's moving every time this method is called.
	 * @param args unused
	 * @return nothing
	 */
	public void update()
	{
		//Check for collision
		checkTileMapCollision();
		setPosition(nextX, nextY); //Star moves to the next position after we have done collision detection.
		
		if(edgeX == 0 && !landed) {
			setLandedProjectile();  //The star has collided with something and should not dissapear from the map.
		}

		//Once our disappearing star images have cycled though, we delete the star from the array.
		animation.update();
		if( animation.hasPlayedOnce() && landed) {
			delete = true;
		}
	}

	/*
	 *This method draws the star's current sprite image on the map using its x and y coordinates
	 * in conjunction with the positioning of the level's tiles.
	 * @param g - Graphics object used to display images.
	 * @return nothing
	 */
	public void draw(Graphics2D g) {
		setMapPosition();
		
		super.draw(g);
	}

	/*
	 * Once a projectile has landed/collided with something, this method is called.
	 * This method makes the star object cycle through its disappearing sprites
	 * and sets landed to true in order to get the star object ready to be discarded from the Array List of stars.
	 * @param args unused
	 * @return nothing
	 */
	public void setLandedProjectile() {
		if(landed)
		{
			return;
		}
		landed = true;
		animation.setFrames(landedSprites);
		animation.setDelay(100);
		edgeX = 0;
	}

	/*
	 * Once a star object has landed/collided with something else,
	 * it is time to delete that star object from the Array List of stars.
	 * @param args unused
	 * @return delete - Boolean determining whether the star needs to be deleted
	 */
	public boolean deleteStar() {
		return delete;
	}
}
