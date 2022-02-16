package Main;

/* *
 * Game.java
 *
 * The main method of the program; creates a
 * frame that runs the game panels.
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 *
 * Updated version: Charlie Quigley
 * @version 1.5
 * @date 02/15/2022
 * */

import javax.swing.JFrame;

public class Game {
	
	public static void main(String[] args) {

		// Create the frame
		JFrame window = new JFrame("Attack of the Killer Spiders!");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set frame size and location
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);

		// Make the frame visible
		window.setVisible(true);
	}
}
