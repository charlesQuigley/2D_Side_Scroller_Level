/* *
 * GameWinState.java
 *
 * Manages the game's menu state, such as the drawing of assets and the
 * menu itself. Also handles click events.
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */
package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;

import TileMap.Background;

public class MenuState extends GameState{
	
	private Background bg;
	private int currentChoice = 0;
	private String[] options = {"Press Enter to Start", "A Side-Scroller by", "Team 10"};
	private Color titleColor;
	private Font titleFont;
	private Font font;
	
	public MenuState(GameStateManager gsm) {
		this.gsm = gsm;
		
		try {
			bg = new Background("/Backgrounds/menu1.gif", 1);
			bg.setVector(-0.1, 0);
			
			titleColor = Color.YELLOW;
			titleFont = new Font("Comic Sans", Font.BOLD, 18);
			font = new Font("Arial", Font.BOLD, 12);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void init() {}
	public void update() {
		bg.update();
	}
	public void draw(Graphics2D g) {
		
		//draw bg
		bg.draw(g);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Attack of the Killer Spiders", 30, 70);
		
		//draw menu options
		g.setFont(font);
		for(int i = 0; i < options.length; i++) {
			if(i == currentChoice) {
				g.setColor(Color.YELLOW);
			}
			else {
				g.setColor(Color.RED);
			}
			g.drawString(options[i], 145, 140 + i * 15);
		}
	}
	
	private void select() {
		if(currentChoice == 0) {
			//start game
			gsm.setState(GameStateManager.stateL1);
		}
		if(currentChoice == 1) {
			//help
		}
		if(currentChoice == 2) {
			System.exit(0);
		}
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER) {
			select();
		}
		if(k == KeyEvent.VK_UP) {
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}

	}
	public void keyReleased(int k) {}
}
