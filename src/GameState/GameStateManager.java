/* *
 * GameStateManager.java
 *
 * Method definitions that handle various state changes for the
 * menu, level, and win states
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */
package GameState;

import Main.GamePanel;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;

	private GamePanel gamePanel; //I added this
	
	public static final int nGameStates = 3; //Changed to 3 for added GameWinState.
	public static final int stateMenu = 0;
	public static final int stateL1 = 1;
	public static final int GameWinState = 2; //State that we enter once we beat the level.
	
	public GameStateManager(GamePanel gamePanel) {
		gameStates = new GameState[nGameStates];

		this.gamePanel = gamePanel;
		
		currentState = stateMenu;
		loadState(currentState);
	}
	
	private void loadState(int state) {
		if(state == stateMenu) { gameStates[state] = new MenuState(this); }
		if(state == stateL1) { gameStates[state] = new Level1State(this); }
		if(state == GameWinState) { gameStates[state] = new GameWinState(this);}

	}
	
	private void unloadState(int state) { gameStates[state] = null; }
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		
		//gameStates[currentState].init();
	}

	
	public void update() {
		try{
			gameStates[currentState].update();
		}
		catch(Exception e) {}
	}
	
	public void draw(java.awt.Graphics2D g) {
		try{
			gameStates[currentState].draw(g);
		}
		catch(Exception e) {}
	}
	
	public void keyPressed(int k) {

		gameStates[currentState].keyPressed(k);


	}
	
	public void keyReleased(int k) {
		gameStates[currentState].keyReleased(k);
	}
}
