/* *
 * GameState.java
 *
 * Method headers for the GameStateManager file.
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */
package GameState;

public abstract class GameState {
	
	protected GameStateManager gsm;
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(java.awt.Graphics2D g);
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
}
