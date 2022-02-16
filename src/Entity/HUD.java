/* *
 * HUD.java
 *
 * Creates a HUD displaying player's health
 * and stars.
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */
package Entity;

import Entities.Player;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class HUD {
	
	private Player player;
	private BufferedImage image1, image2;
	private Font fontHeart, fontStar;

	/*
	 * This constructor method loads and reads the sprites
	 * for hearts and stars
	 * @param p - Player object with the star and heart values
	 * @return nothing
	 */
	public HUD(Player p) {
		player = p;
		
		try {	//Read images for hearts and stars display
			image1 = ImageIO.read(getClass().getResourceAsStream("/HUD/heartHud.png"));
			image2 = ImageIO.read(getClass().getResourceAsStream("/HUD/starHud.png"));

			//Set font for the numbers being displayed in the HUD
			fontHeart = new Font("Arial", Font.PLAIN, 14);
			fontStar = new Font("Arial", Font.PLAIN, 10);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method draws the HUD, Health value, and Star
	 * value onto the panel
	 * @param g - Graphics2D object drawing an image
	 * @return nothing
	 */
	public void draw(Graphics2D g) {
		//Draw health
		g.drawImage(image1, 5, 5, null);
		g.setFont(fontHeart);
		g.setColor(Color.WHITE);
		g.drawString(""+player.getHealth(), 37, 40);

		//Draw stars
		g.drawImage(image2, 5, 55, null);
		g.setColor(Color.WHITE);
		g.setFont(fontStar);
		//Format drawn text based off current star and maxStar values
		if(player.getStar() < 1000) {
			//If max fire is 10 or less
			g.drawString(player.getStar() / 100 + "/" + player.getStar() / 100, 33, 85);
		}
		else if(player.getStar() >= 1000 && player.getStar() >= 1000)
		{
			//If max fire is 10 or more and we have 10 or more fire points
			g.drawString(player.getStar() / 100 + "/" + player.getStar() / 100, 27, 85);
		}
		else if(player.getStar() >= 1000)
		{
			//If max fire is 10 or more and we have less than 10 fire points
			g.drawString(player.getStar() / 100 + "/" + player.getStar() / 100, 32, 85);
		}
	}
}
