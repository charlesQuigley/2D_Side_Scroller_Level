package Entities;

import TileMap.TileMap;

/**
 * Enemies for the Player
 * This class is used as the superclass of all enemies the player encounters in the game.
 * Things that all enemies have in common, such as health points, attack points, etc., are found here.
 * All Enemies are Entities of the game, thus the Enemy class extends Entity and has access to its methods.
 */
public class Enemy extends Entity{

	protected int health; //Enemy's current health
	protected int fullHealth; //Enemy's full health
	protected int atk; //how much damage the enemy does to the player.
	
	protected boolean iFrames; //If the enemy is currently invincible because it was just damaged
	protected long invincibilityTimer; //How long this lasts for
	protected boolean dead = false;

	public Enemy(TileMap level) {

		super(level);
	}


	/**
	 * This class is called when an enemy is attacked by the player.
	 * @param atk - The player's attack points
	 */
	public void attack(int atk) {
		if(dead || iFrames) //If the enemy that the player is attacking is dead or is currently invincible
		{
			return; //Don't deal damage.
		}
		health = health - atk; //Enemy loses health points
		if(health <= 0) //When health points are zero or less, the enemy should die and be removed from the Level.
		{
			dead = true;
		}
		iFrames = true; //Set the enemy to be invincible for a brief period after being attacked.
		invincibilityTimer = System.nanoTime(); //Used to determine how long the enemy is invincible for.
	}


	public boolean getDead() { return dead; }

	//Returns the enemy's attack points.
	public int getAttackPoints() { return atk; }


	/**
	 * All classes that extend this class have their own update() method.
	 * They call this update() method within their update() method in order to determine
	 * if they should still have invincibility or not.
	 */
	public void update() {

		//invincibility frames
		if (iFrames) {
			long timePassedSinceHit = (System.nanoTime() - invincibilityTimer) / 100000000;
			if (timePassedSinceHit > 2) {
				iFrames = false;
			}
		}
	}
}
