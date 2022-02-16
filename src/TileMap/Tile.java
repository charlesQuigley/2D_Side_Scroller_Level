/* *
 * Tile.java
 *
 * Creates a tile to be mapped into the frame to create
 * the map of a level.
 *
 * @author   Jacob Lamb, Mark Gaza, Charlie Quigley, Jacob Brown
 * @version  1.0
 * @date     05/04/2020
 * */
package TileMap;

import java.awt.image.BufferedImage;

public class Tile {

	// Variable declaration
	private BufferedImage bi;
	private int tileType;
	
	// Tile types
	public static final int BLOCKED = 1;	// Solid tiles
	public static final int DETAIL = 0;		// Passable tiles

	// Constructor
	public Tile(BufferedImage image, int type) {
		this.bi = image;
		this.tileType = type;
	}

	// Return the tile image
	public BufferedImage getImage() { return bi; }

	// Return the tile type
	public int getType() { return tileType; }
	
}
