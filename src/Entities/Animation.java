/* *
 * Animation.java
 *
 * Sets the standard Animation for Sprite Characters
 * and determines the necessary iFrames for them
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */

package Entities;

import java.awt.image.BufferedImage;

public class Animation {
	// Variable Declaration
	private BufferedImage[] frames;
	private int currentFrame;
	
	private long startTime;
	private long delay;
	
	// this boolean is a key for stopping 
	// sprites from animating infinitely
	private boolean playedOnce;
	
	// Constructor sets the boolean to false
	public Animation() {
		playedOnce = false;
	}
	
	/**
	 * 	This Method is what determines how many frames an Animation is supposed
	 *  to last. It keeps all entities visually performing at a set limit
	 **/
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	/**
	 * Sets the Delay of each nano second between frames
	 * 
	 * @param d
	 */
	public void setDelay(long d) { delay = d; }
	
	/**
	 * Sets the current Frame to a specific number when called
	 * 
	 * @param i
	 */
	public void setFram(int i) { currentFrame = i; }
	
	
	/**
	 * The update call is what keep the sprites moving
	 * between frames. By calling this method, you are essentially
	 * telling the sprite to move to the next coordinate.
	 */
	public void update() {
		if(delay == -1) return;
		
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == frames.length) {
			currentFrame = 0;
			playedOnce = true;
		}
	}
	
	/**
	 * @return currentFrame
	 */
	public int getFrame() { return currentFrame; }
	
	/**
	 * @return array space of currentFrame
	 */
	public BufferedImage getImage() { return frames[currentFrame]; }
	
	/**
	 * @return boolean
	 */
	public boolean hasPlayedOnce() { return playedOnce; }
	
	
	
}
